package com.reviews.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reviews.web.domain.Product;
import com.reviews.web.repository.ProductRepository;
import com.reviews.web.web.exceptions.ProductSpecNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/app")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    private ProductRepository productRepository;

    /**
     * POST /rest/products -> Create a new product.
     */
    @RequestMapping(value = "/rest/products",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Product product) {
        log.debug("REST request to save Product : {}", product);
        productRepository.save(product);
    }

    /**
     * GET /rest/products -> get all the products.
     */
    @RequestMapping(value = "/rest/products",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Product> getAll() {
        log.debug("REST request to get all Products");
        return productRepository.findAll();
    }

    /**
     * GET /rest/products/:id -> get the "id" product.
     */
    @RequestMapping(value = "/rest/products/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Product> get(@PathVariable String id) {
        log.debug("REST request to get Product : {}", id);
        return Optional.ofNullable(productRepository.findOne(id))
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /rest/products/:id -> delete the "id" product.
     */
    @RequestMapping(value = "/rest/products/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Product : {}", id);
        productRepository.delete(id);
    }

    /**
     * Fetch the product specifications from the web
     * 
     * @param url
     *            The URL from where to fetch the product specifications
     * @return
     */
    @RequestMapping(value = "/rest/products/specs",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ObjectNode fetchSpecs(@RequestParam("url") String url) {
        log.debug("Fetching product specifications from the web");
        ObjectNode spec = new ObjectNode(JsonNodeFactory.instance);

        try {
            Document doc = Jsoup.connect(url).get();
            Elements tables = doc.select("#specs-list table");
            if (tables.isEmpty()) {
                throw new ProductSpecNotFoundException();
            }
            tables.forEach(table -> this.extractSpecs(table, spec));
        } catch (IOException e) {
            throw new ProductSpecNotFoundException();
        }
        return spec;
    }

    /**
     * For each table extract specs
     * 
     * @param table
     *            Table containing the details of a spec category like
     *            "Network", "Memory"
     * @param spec
     *            The spec into which the details will be stored
     */
    private void extractSpecs(Element table, ObjectNode spec) {
        try {
            String specCategory = table.select("tr:eq(0) th").text();
            ObjectNode specValues = new ObjectNode(JsonNodeFactory.instance);
            final StringBuilder specNameHolder = new StringBuilder();
            table.select("tr:gt(0)").forEach(
                    tr -> {
                        try {
                            String specName = tr.select("td:eq(0) a").text();
                            boolean isRepeat = specName.isEmpty();
                            if (!isRepeat) {
                                specNameHolder.delete(0, specNameHolder.length());
                            }
                            specNameHolder.append(specName);

                            Optional.ofNullable(tr.select("td:eq(1) a").text())
                                    .map(specValue -> setSpecValueWithLink(specValue, specValues, specNameHolder,
                                            isRepeat))
                                    .orElse(setSpecValueWithoutLink(specValues, specNameHolder, tr, isRepeat));

                        } catch (Exception e) {
                        }
                    });

            spec.put(StringUtils.capitalize(specCategory.toLowerCase()), specValues);
        } catch (Exception e) {
        }
    }

    /**
     * Extract data from a table row
     * @param specValue
     * @param specValues
     * @param specNameHolder
     * @param isRepeat
     * @return
     */
    private ObjectNode setSpecValueWithLink(String specValue, ObjectNode specValues, final StringBuilder specNameHolder, boolean isRepeat) {
        try {
            if (!isRepeat) {
                specValues.put(StringUtils.capitalize(specNameHolder.toString().toLowerCase()), specValue);
            } else {
                specValues.put(StringUtils.capitalize(specNameHolder.toString().toLowerCase()),
                        specValues.get(specNameHolder.toString()).asText().concat(", " + specValue));
            }
        } catch (Exception e) {
        }
        return specValues;
    }

    private ObjectNode setSpecValueWithoutLink(ObjectNode specValues, final StringBuilder specNameHolder, Element tr, boolean isRepeat) throws JSONException {
        if (!isRepeat) {
            specValues.put(StringUtils.capitalize(specNameHolder.toString().toLowerCase()), tr.select("td:eq(1)")
                    .text());
        } else {
            specValues.put(StringUtils.capitalize(specNameHolder.toString().toLowerCase()),
                    specValues.get(specNameHolder.toString()).asText().concat(", " + tr.select("td:eq(1)").text()));
        }
        return specValues;
    }

}
