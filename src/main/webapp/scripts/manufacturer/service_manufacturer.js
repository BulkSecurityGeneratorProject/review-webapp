'use strict';

reviewsApp.factory('Manufacturer', function ($resource) {
        return $resource('app/rest/manufacturers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
