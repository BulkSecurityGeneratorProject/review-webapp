'use strict';

reviewsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/category', {
                    templateUrl: 'views/categories.html',
                    controller: 'CategoryController',
                    resolve:{
                        resolvedCategory: ['Category', function (Category) {
                            return Category.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
