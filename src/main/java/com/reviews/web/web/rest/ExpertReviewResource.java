package com.reviews.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.reviews.web.domain.ExpertReview;
import com.reviews.web.repository.ExpertReviewRepository;
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
 * REST controller for managing ExpertReview.
 */
@RestController
@RequestMapping("/app")
public class ExpertReviewResource {

    private final Logger log = LoggerFactory.getLogger(ExpertReviewResource.class);

    @Inject
    private ExpertReviewRepository expertReviewRepository;

    /**
     * POST  /rest/expertReviews -> Create a new expertReview.
     */
    @RequestMapping(value = "/rest/expertReviews",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody ExpertReview expertReview) {
        log.debug("REST request to save ExpertReview : {}", expertReview);
        expertReviewRepository.save(expertReview);
    }

    /**
     * GET  /rest/expertReviews -> get all the expertReviews.
     */
    @RequestMapping(value = "/rest/expertReviews",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ExpertReview> getAll() {
        log.debug("REST request to get all ExpertReviews");
        return expertReviewRepository.findAll();
    }

    /**
     * GET  /rest/expertReviews/:id -> get the "id" expertReview.
     */
    @RequestMapping(value = "/rest/expertReviews/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExpertReview> get(@PathVariable String id) {
        log.debug("REST request to get ExpertReview : {}", id);
        return Optional.ofNullable(expertReviewRepository.findOne(id))
            .map(expertReview -> new ResponseEntity<>(
                expertReview,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/expertReviews/:id -> delete the "id" expertReview.
     */
    @RequestMapping(value = "/rest/expertReviews/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete ExpertReview : {}", id);
        expertReviewRepository.delete(id);
    }
}
