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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import com.reviews.web.Application;
import com.reviews.web.domain.Review;
import com.reviews.web.repository.ReviewRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReviewResource REST controller.
 *
 * @see ReviewResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ReviewResourceIT {
   private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static final String DEFAULT_REVIEWER_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_REVIEWER_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_TEXT = "SAMPLE_TEXT";
    private static final String UPDATED_TEXT = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_RATING = 0;
    private static final Integer UPDATED_RATING = 1;
    
   private static final DateTime DEFAULT_DATE = new DateTime(0L);
   private static final DateTime UPDATED_DATE = new DateTime().withMillisOfSecond(0);
   private static final String DEFAULT_DATE_STR = dateTimeFormatter.print(DEFAULT_DATE);
    

    @Inject
    private ReviewRepository reviewRepository;

    private MockMvc restReviewMockMvc;

    private Review review;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReviewResource reviewResource = new ReviewResource();
        ReflectionTestUtils.setField(reviewResource, "reviewRepository", reviewRepository);
        this.restReviewMockMvc = MockMvcBuilders.standaloneSetup(reviewResource).build();
    }

    @Before
    public void initTest() {
        reviewRepository.deleteAll();
        review = new Review();
        review.setReviewerName(DEFAULT_REVIEWER_NAME);
        review.setText(DEFAULT_TEXT);
        review.setRating(DEFAULT_RATING);
        review.setDate(DEFAULT_DATE);
    }

    @Test
    public void createReview() throws Exception {
        // Validate the database is empty
        assertThat(reviewRepository.findAll()).hasSize(0);

        // Create the Review
        restReviewMockMvc.perform(post("/app/rest/reviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(review)))
                .andExpect(status().isOk());

        // Validate the Review in the database
        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(1);
        Review testReview = reviews.iterator().next();
        assertThat(testReview.getReviewerName()).isEqualTo(DEFAULT_REVIEWER_NAME);
        assertThat(testReview.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testReview.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testReview.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    public void getAllReviews() throws Exception {
        // Initialize the database
        reviewRepository.save(review);

        // Get all the reviews
        restReviewMockMvc.perform(get("/app/rest/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(review.getId()))
                .andExpect(jsonPath("$.[0].reviewerName").value(DEFAULT_REVIEWER_NAME.toString()))
                .andExpect(jsonPath("$.[0].text").value(DEFAULT_TEXT.toString()))
                .andExpect(jsonPath("$.[0].rating").value(DEFAULT_RATING))
                .andExpect(jsonPath("$.[0].date").value(DEFAULT_DATE_STR));
    }

    @Test
    public void getReview() throws Exception {
        // Initialize the database
        reviewRepository.save(review);

        // Get the review
        restReviewMockMvc.perform(get("/app/rest/reviews/{id}", review.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(review.getId()))
            .andExpect(jsonPath("$.reviewerName").value(DEFAULT_REVIEWER_NAME.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR));
    }

    @Test
    public void getNonExistingReview() throws Exception {
        // Get the review
        restReviewMockMvc.perform(get("/app/rest/reviews/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateReview() throws Exception {
        // Initialize the database
        reviewRepository.save(review);

        // Update the review
        review.setReviewerName(UPDATED_REVIEWER_NAME);
        review.setText(UPDATED_TEXT);
        review.setRating(UPDATED_RATING);
        review.setDate(UPDATED_DATE);
        restReviewMockMvc.perform(post("/app/rest/reviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(review)))
                .andExpect(status().isOk());

        // Validate the Review in the database
        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(1);
        Review testReview = reviews.iterator().next();
        assertThat(testReview.getReviewerName()).isEqualTo(UPDATED_REVIEWER_NAME);
        assertThat(testReview.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testReview.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testReview.getDate()).isEqualTo(UPDATED_DATE);;
    }

    @Test
    public void deleteReview() throws Exception {
        // Initialize the database
        reviewRepository.save(review);

        // Get the review
        restReviewMockMvc.perform(delete("/app/rest/reviews/{id}", review.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(0);
    }
}
