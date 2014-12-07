'use strict';

reviewsApp.factory('ExpertReview', function ($resource) {
        return $resource('app/rest/expertReviews/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
