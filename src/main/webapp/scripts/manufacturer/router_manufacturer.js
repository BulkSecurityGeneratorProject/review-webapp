'use strict';

reviewsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/manufacturer', {
                    templateUrl: 'views/manufacturers.html',
                    controller: 'ManufacturerController',
                    resolve:{
                        resolvedManufacturer: ['Manufacturer', function (Manufacturer) {
                            return Manufacturer.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
