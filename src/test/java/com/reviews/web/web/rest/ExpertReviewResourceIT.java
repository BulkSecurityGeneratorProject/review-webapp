package com.reviews.web.web.rest;

import org.assertj.core.api.Condition;
import org.joda.time.DateTime;
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
import com.reviews.web.domain.ExpertReview;
import com.reviews.web.repository.ExpertReviewRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * Test class for the ExpertReviewResource REST controller.
 *
 * @see ExpertReviewResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ExpertReviewResourceIT {

    private static final String DEFAULT_URL = "SAMPLE_TEXT";
    private static final String UPDATED_URL = "UPDATED_TEXT";
    
    private static final String DEFAULT_SUMMARY = "SAMPLE_TEXT";
    private static final String UPDATED_SUMMARY = "UPDATED_TEXT";
    
    private static final String DEFAULT_SOURCE = "SAMPLE_TEXT";
    private static final String UPDATED_SOURCE = "UPDATED_TEXT";
    

    @Inject
    private ExpertReviewRepository expertReviewRepository;

    private MockMvc restExpertReviewMockMvc;

    private ExpertReview expertReview;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExpertReviewResource expertReviewResource = new ExpertReviewResource();
        ReflectionTestUtils.setField(expertReviewResource, "expertReviewRepository", expertReviewRepository);
        this.restExpertReviewMockMvc = MockMvcBuilders.standaloneSetup(expertReviewResource).build();
    }

    @Before
    public void initTest() {
        expertReviewRepository.deleteAll();
        expertReview = new ExpertReview();
        expertReview.setUrl(DEFAULT_URL);
        expertReview.setSummary(DEFAULT_SUMMARY);
        expertReview.setSource(DEFAULT_SOURCE);
    }

    @Test
    public void createExpertReview() throws Exception {
        // Validate the database is empty
        assertThat(expertReviewRepository.findAll()).hasSize(0);

        // Create the ExpertReview
        restExpertReviewMockMvc.perform(post("/app/rest/expertReviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(expertReview)))
                .andExpect(status().isOk());

        // Validate the ExpertReview in the database
        List<ExpertReview> expertReviews = expertReviewRepository.findAll();
        assertThat(expertReviews).hasSize(1);
        ExpertReview testExpertReview = expertReviews.iterator().next();
        assertThat(testExpertReview.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testExpertReview.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testExpertReview.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    public void getAllExpertReviews() throws Exception {
        // Initialize the database
        expertReviewRepository.save(expertReview);

        // Get all the expertReviews
        restExpertReviewMockMvc.perform(get("/app/rest/expertReviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(expertReview.getId()))
                .andExpect(jsonPath("$.[0].url").value(DEFAULT_URL.toString()))
                .andExpect(jsonPath("$.[0].summary").value(DEFAULT_SUMMARY.toString()))
                .andExpect(jsonPath("$.[0].source").value(DEFAULT_SOURCE.toString()));
    }

    @Test
    public void getExpertReview() throws Exception {
        // Initialize the database
        expertReviewRepository.save(expertReview);

        // Get the expertReview
        restExpertReviewMockMvc.perform(get("/app/rest/expertReviews/{id}", expertReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(expertReview.getId()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()));
    }

    @Test
    public void getNonExistingExpertReview() throws Exception {
        // Get the expertReview
        restExpertReviewMockMvc.perform(get("/app/rest/expertReviews/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateExpertReview() throws Exception {
        // Initialize the database
        expertReviewRepository.save(expertReview);

        // Update the expertReview
        expertReview.setUrl(UPDATED_URL);
        expertReview.setSummary(UPDATED_SUMMARY);
        expertReview.setSource(UPDATED_SOURCE);
        restExpertReviewMockMvc.perform(post("/app/rest/expertReviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(expertReview)))
                .andDo(print())
                .andExpect(status().isOk());

        // Validate the ExpertReview in the database
        List<ExpertReview> expertReviews = expertReviewRepository.findAll();
        assertThat(expertReviews).hasSize(1);
        ExpertReview testExpertReview = expertReviews.iterator().next();
        assertThat(testExpertReview.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testExpertReview.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testExpertReview.getSource()).isEqualTo(UPDATED_SOURCE);;
        assertThat(testExpertReview.getLastModifiedDate()).is(new Condition<DateTime>() {

			@Override
			public boolean matches(DateTime dateTime) {
				return dateTime.isAfter(expertReview.getCreatedDate());
			}
		});
    }

    @Test
    public void deleteExpertReview() throws Exception {
        // Initialize the database
        expertReviewRepository.save(expertReview);

        // Get the expertReview
        restExpertReviewMockMvc.perform(delete("/app/rest/expertReviews/{id}", expertReview.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExpertReview> expertReviews = expertReviewRepository.findAll();
        assertThat(expertReviews).hasSize(0);
    }
}
