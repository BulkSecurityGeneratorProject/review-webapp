package com.reviews.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.reviews.web.domain.Manufacturer;
import com.reviews.web.repository.ManufacturerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Manufacturer.
 */
@RestController
@RequestMapping("/app")
public class ManufacturerResource {

    private final Logger log = LoggerFactory.getLogger(ManufacturerResource.class);

    @Inject
    private ManufacturerRepository manufacturerRepository;

    /**
     * POST  /rest/manufacturers -> Create a new manufacturer.
     */
    @RequestMapping(value = "/rest/manufacturers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Manufacturer manufacturer) {
        log.debug("REST request to save Manufacturer : {}", manufacturer);
        manufacturerRepository.save(manufacturer);
    }

    /**
     * GET  /rest/manufacturers -> get all the manufacturers.
     */
    @RequestMapping(value = "/rest/manufacturers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Manufacturer> getAll() {
        log.debug("REST request to get all Manufacturers");
        return manufacturerRepository.findAll();
    }

    /**
     * GET  /rest/manufacturers/:id -> get the "id" manufacturer.
     */
    @RequestMapping(value = "/rest/manufacturers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Manufacturer> get(@PathVariable String id) {
        log.debug("REST request to get Manufacturer : {}", id);
        return Optional.ofNullable(manufacturerRepository.findOne(id))
            .map(manufacturer -> new ResponseEntity<>(
                manufacturer,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/manufacturers/:id -> delete the "id" manufacturer.
     */
    @RequestMapping(value = "/rest/manufacturers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Manufacturer : {}", id);
        manufacturerRepository.delete(id);
    }
}
