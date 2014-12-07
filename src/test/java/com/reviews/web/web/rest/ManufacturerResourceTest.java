package com.reviews.web.web.rest;

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
import java.util.List;

import com.reviews.web.Application;
import com.reviews.web.domain.Manufacturer;
import com.reviews.web.repository.ManufacturerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ManufacturerResource REST controller.
 *
 * @see ManufacturerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ManufacturerResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_WEBSITE = "SAMPLE_TEXT";
    private static final String UPDATED_WEBSITE = "UPDATED_TEXT";
    
    private static final String DEFAULT_WIKI = "SAMPLE_TEXT";
    private static final String UPDATED_WIKI = "UPDATED_TEXT";
    
    private static final String DEFAULT_LOGO = "SAMPLE_TEXT";
    private static final String UPDATED_LOGO = "UPDATED_TEXT";
    

    @Inject
    private ManufacturerRepository manufacturerRepository;

    private MockMvc restManufacturerMockMvc;

    private Manufacturer manufacturer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ManufacturerResource manufacturerResource = new ManufacturerResource();
        ReflectionTestUtils.setField(manufacturerResource, "manufacturerRepository", manufacturerRepository);
        this.restManufacturerMockMvc = MockMvcBuilders.standaloneSetup(manufacturerResource).build();
    }

    @Before
    public void initTest() {
        manufacturerRepository.deleteAll();
        manufacturer = new Manufacturer();
        manufacturer.setName(DEFAULT_NAME);
        manufacturer.setWebsite(DEFAULT_WEBSITE);
        manufacturer.setWiki(DEFAULT_WIKI);
        manufacturer.setLogo(DEFAULT_LOGO);
    }

    @Test
    public void createManufacturer() throws Exception {
        // Validate the database is empty
        assertThat(manufacturerRepository.findAll()).hasSize(0);

        // Create the Manufacturer
        restManufacturerMockMvc.perform(post("/app/rest/manufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
                .andExpect(status().isOk());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(1);
        Manufacturer testManufacturer = manufacturers.iterator().next();
        assertThat(testManufacturer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testManufacturer.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testManufacturer.getWiki()).isEqualTo(DEFAULT_WIKI);
        assertThat(testManufacturer.getLogo()).isEqualTo(DEFAULT_LOGO);
    }

    @Test
    public void getAllManufacturers() throws Exception {
        // Initialize the database
        manufacturerRepository.save(manufacturer);

        // Get all the manufacturers
        restManufacturerMockMvc.perform(get("/app/rest/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(manufacturer.getId()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].website").value(DEFAULT_WEBSITE.toString()))
                .andExpect(jsonPath("$.[0].wiki").value(DEFAULT_WIKI.toString()))
                .andExpect(jsonPath("$.[0].logo").value(DEFAULT_LOGO.toString()));
    }

    @Test
    public void getManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.save(manufacturer);

        // Get the manufacturer
        restManufacturerMockMvc.perform(get("/app/rest/manufacturers/{id}", manufacturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(manufacturer.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.wiki").value(DEFAULT_WIKI.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()));
    }

    @Test
    public void getNonExistingManufacturer() throws Exception {
        // Get the manufacturer
        restManufacturerMockMvc.perform(get("/app/rest/manufacturers/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.save(manufacturer);

        // Update the manufacturer
        manufacturer.setName(UPDATED_NAME);
        manufacturer.setWebsite(UPDATED_WEBSITE);
        manufacturer.setWiki(UPDATED_WIKI);
        manufacturer.setLogo(UPDATED_LOGO);
        restManufacturerMockMvc.perform(post("/app/rest/manufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
                .andExpect(status().isOk());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(1);
        Manufacturer testManufacturer = manufacturers.iterator().next();
        assertThat(testManufacturer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManufacturer.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testManufacturer.getWiki()).isEqualTo(UPDATED_WIKI);
        assertThat(testManufacturer.getLogo()).isEqualTo(UPDATED_LOGO);;
    }

    @Test
    public void deleteManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.save(manufacturer);

        // Get the manufacturer
        restManufacturerMockMvc.perform(delete("/app/rest/manufacturers/{id}", manufacturer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(0);
    }
}
