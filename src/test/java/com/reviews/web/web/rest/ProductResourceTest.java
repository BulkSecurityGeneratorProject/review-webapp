package com.reviews.web.web.rest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import com.reviews.web.Application;
import com.reviews.web.domain.ExpertReview;
import com.reviews.web.domain.Manufacturer;
import com.reviews.web.domain.Product;
import com.reviews.web.domain.Review;
import com.reviews.web.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ProductResourceTest {
	
	private static final String YEAR_OF_MANUFACTURE = "2014";

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd'T'HH:mm:ss");

	private static final String DEFAULT_NAME = "SAMPLE_TEXT";
	private static final String UPDATED_NAME = "UPDATED_TEXT";

	private static final String DEFAULT_MANUFACTURER_ID = "SAMPLE_TEXT";

	private static final String DEFAULT_IMAGE = "SAMPLE_TEXT";
	private static final String UPDATED_IMAGE = "UPDATED_TEXT";

	private static final String DEFAULT_WIKI = "SAMPLE_TEXT";
	private static final String UPDATED_WIKI = "UPDATED_TEXT";

	private static final DateTime DEFAULT_CREATED_DATE = new DateTime(0L);
	private static final DateTime UPDATED_CREATED_DATE = new DateTime()
			.withMillisOfSecond(0);
	private static final String DEFAULT_CREATED_DATE_STR = dateTimeFormatter
			.print(DEFAULT_CREATED_DATE);

	private static final String DEFAULT_MANUFACTURER_NAME = "SAMPLE_TEXT";

	private static final String DEFAULT_MANUFACTURER_WEBSITE = "SAMPLE_TEXT";

	private static final String DEFAULT_MANUFACTURER_WIKI = "SAMPLE_TEXT";

	private static final String DEFAULT_MANUFACTURER_LOGO = "SAMPLE_TEXT";

	private static final String DEFAULT_REVIEWER_NAME = "Founder";

	private static final String DEFAULT_EXPERT_REVIEW_SOURCE = "GSM Arena";    

    @Inject
    private ProductRepository productRepository;

    private MockMvc restProductMockMvc;

    private Product product;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource();
        ReflectionTestUtils.setField(productResource, "productRepository", productRepository);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource).build();
    }

    @Before
    public void initTest() {
    	Manufacturer manufacturer = new Manufacturer();
    	manufacturer.setId(DEFAULT_MANUFACTURER_ID);
    	manufacturer.setName(DEFAULT_MANUFACTURER_NAME);
    	manufacturer.setWebsite(DEFAULT_MANUFACTURER_WEBSITE);
    	manufacturer.setWiki(DEFAULT_MANUFACTURER_WIKI);
    	manufacturer.setLogo(DEFAULT_MANUFACTURER_LOGO);
    	
    	JSONObject specs = new JSONObject();
    	try {
			specs.put("yearOfManufacture", YEAR_OF_MANUFACTURE);
		} catch (JSONException e) {
		}
		
		List<ExpertReview> expertReviews = new ArrayList<>();
		ExpertReview expertReview = new ExpertReview();
		expertReview.setSource(DEFAULT_EXPERT_REVIEW_SOURCE);
		expertReviews.add(expertReview);
		
		
		List<Review> reviews = new ArrayList<>();
		Review review = new Review();
		review.setReviewerName(DEFAULT_REVIEWER_NAME.toString());
		review.setText("Excellent Product");
		review.setRating(5);
		reviews.add(review);
		
        productRepository.deleteAll();
        
        product = new Product();
        product.setReviews(reviews);
		product.setExpertReviews(expertReviews);
		product.setSpecs(specs);
        product.setName(DEFAULT_NAME);
        product.setManufacturer(manufacturer);
        product.setImage(DEFAULT_IMAGE);
        product.setWiki(DEFAULT_WIKI);
        product.setCreatedDate(DEFAULT_CREATED_DATE);
    }

    @Test
    public void createProduct() throws Exception {
        // Validate the database is empty
        assertThat(productRepository.findAll()).hasSize(0);

        // Create the Product
        restProductMockMvc.perform(post("/app/rest/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        Product testProduct = products.iterator().next();
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProduct.getWiki()).isEqualTo(DEFAULT_WIKI);
        assertThat(testProduct.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        
    }

    @Test
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.save(product);

        // Get all the products
        restProductMockMvc.perform(get("/app/rest/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(product.getId()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].reviews.[0].reviewerName").value(DEFAULT_REVIEWER_NAME.toString()))
                .andExpect(jsonPath("$.[0].expertReviews.[0].source").value(DEFAULT_EXPERT_REVIEW_SOURCE.toString()))
                .andExpect(jsonPath("$.[0].image").value(DEFAULT_IMAGE.toString()))
                .andExpect(jsonPath("$.[0].wiki").value(DEFAULT_WIKI.toString()))
                .andExpect(jsonPath("$.[0].createdDate").value(DEFAULT_CREATED_DATE_STR))
                .andExpect(jsonPath("$.[0].specs.yearOfManufacture").value(YEAR_OF_MANUFACTURE.toString()));
    }

    @Test
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);

        // Get the product
        restProductMockMvc.perform(get("/app/rest/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(product.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.reviews.[0].reviewerName").value(DEFAULT_REVIEWER_NAME.toString()))
            .andExpect(jsonPath("$.expertReviews.[0].source").value(DEFAULT_EXPERT_REVIEW_SOURCE.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.wiki").value(DEFAULT_WIKI.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE_STR))
            .andExpect(jsonPath("$.specs.yearOfManufacture").value(YEAR_OF_MANUFACTURE.toString()));
    }

    @Test
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/app/rest/products/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);

        // Update the product
        product.setName(UPDATED_NAME);
        product.setImage(UPDATED_IMAGE);
        product.setWiki(UPDATED_WIKI);
        product.setCreatedDate(UPDATED_CREATED_DATE);
        restProductMockMvc.perform(post("/app/rest/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        Product testProduct = products.iterator().next();
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProduct.getWiki()).isEqualTo(UPDATED_WIKI);
        assertThat(testProduct.getExpertReviews().get(0).getSource()).isEqualTo(DEFAULT_EXPERT_REVIEW_SOURCE);
        assertThat(testProduct.getReviews().get(0).getReviewerName()).isEqualTo(DEFAULT_REVIEWER_NAME);
        assertThat(testProduct.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);

        // Get the product
        restProductMockMvc.perform(delete("/app/rest/products/{id}", product.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(0);
    }
}
