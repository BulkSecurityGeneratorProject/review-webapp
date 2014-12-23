'use strict';

reviewsApp.factory('Category', function ($resource) {
        return $resource('app/rest/categories/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
