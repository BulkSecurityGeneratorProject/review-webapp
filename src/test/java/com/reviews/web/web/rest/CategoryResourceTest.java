package com.reviews.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

import com.reviews.web.Application;
import com.reviews.web.domain.Category;
import com.reviews.web.repository.CategoryRepository;

/**
 * Test class for the CategoryResource REST controller.
 *
 * @see CategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CategoryResourceTest {
   private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
   private static final DateTime DEFAULT_CREATED_DATE = new DateTime(0L);
   private static final DateTime UPDATED_CREATED_DATE = new DateTime().withMillisOfSecond(0);
   private static final String DEFAULT_CREATED_DATE_STR = dateTimeFormatter.print(DEFAULT_CREATED_DATE);
    
   private static final DateTime DEFAULT_UPDATED_DATE = new DateTime(0L);
   private static final DateTime UPDATED_UPDATED_DATE = new DateTime().withMillisOfSecond(0);
   private static final String DEFAULT_UPDATED_DATE_STR = dateTimeFormatter.print(DEFAULT_UPDATED_DATE);
    
    private static final String DEFAULT_UPDATED_BY = "SAMPLE_TEXT";
    private static final String UPDATED_UPDATED_BY = "UPDATED_TEXT";
    
    private static final String DEFAULT_TAGS = "SAMPLE_TEXT";
    private static final String UPDATED_TAGS = "UPDATED_TEXT";
    

    @Inject
    private CategoryRepository categoryRepository;

    private MockMvc restCategoryMockMvc;

    private Category category;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CategoryResource categoryResource = new CategoryResource();
        ReflectionTestUtils.setField(categoryResource, "categoryRepository", categoryRepository);
        this.restCategoryMockMvc = MockMvcBuilders.standaloneSetup(categoryResource).build();
    }

    @Before
    public void initTest() {
        categoryRepository.deleteAll();
        category = new Category();
        category.setName(DEFAULT_NAME);
        category.setCreatedDate(DEFAULT_CREATED_DATE);
        category.setLastModifiedDate(DEFAULT_UPDATED_DATE);
        category.setLastModifiedBy(DEFAULT_UPDATED_BY);
        category.setCreatedBy(DEFAULT_UPDATED_BY);
        Set<String> tags = new HashSet<>();
        tags.add(DEFAULT_TAGS);
        category.setTags(tags);
    }

    @Test
    public void createCategory() throws Exception {
        // Validate the database is empty
        assertThat(categoryRepository.findAll()).hasSize(0);

        // Create the Category
        restCategoryMockMvc.perform(post("/app/rest/categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(category)))
                .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).hasSize(1);
        Category testCategory = categories.iterator().next();
        assertThat(testCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCategory.getLastModifiedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testCategory.getLastModifiedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCategory.getTags()).containsExactly(DEFAULT_TAGS);
    }

    @Test
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.save(category);

        // Get all the categories
        restCategoryMockMvc.perform(get("/app/rest/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(category.getId()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].createdDate").value(DEFAULT_CREATED_DATE_STR))
                .andExpect(jsonPath("$.[0].updatedDate").value(DEFAULT_UPDATED_DATE_STR))
                .andExpect(jsonPath("$.[0].updatedBy").value(DEFAULT_UPDATED_BY.toString()))
                .andExpect(jsonPath("$.[0].tags").value(DEFAULT_TAGS.toString()));
    }

    @Test
    public void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.save(category);

        // Get the category
        restCategoryMockMvc.perform(get("/app/rest/categories/{id}", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(category.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE_STR))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE_STR))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()));
    }

    @Test
    public void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get("/app/rest/categories/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCategory() throws Exception {
        // Initialize the database
        categoryRepository.save(category);

        // Update the category
        category.setName(UPDATED_NAME);
        category.setCreatedDate(UPDATED_CREATED_DATE);
        category.setLastModifiedDate(UPDATED_UPDATED_DATE);
        category.setLastModifiedBy(UPDATED_UPDATED_BY);
        category.getTags().add(UPDATED_TAGS);
        restCategoryMockMvc.perform(post("/app/rest/categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(category)))
                .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).hasSize(1);
        Category testCategory = categories.iterator().next();
        assertThat(testCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCategory.getLastModifiedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCategory.getLastModifiedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCategory.getTags()).hasSize(2);
    }

    @Test
    public void deleteCategory() throws Exception {
        // Initialize the database
        categoryRepository.save(category);

        // Get the category
        restCategoryMockMvc.perform(delete("/app/rest/categories/{id}", category.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).hasSize(0);
    }
}
