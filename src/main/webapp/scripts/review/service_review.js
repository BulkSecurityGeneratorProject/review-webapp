'use strict';

reviewsApp.factory('Review', function ($resource) {
        return $resource('app/rest/reviews/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
