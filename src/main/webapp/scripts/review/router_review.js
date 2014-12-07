'use strict';

reviewsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/review', {
                    templateUrl: 'views/reviews.html',
                    controller: 'ReviewController',
                    resolve:{
                        resolvedReview: ['Review', function (Review) {
                            return Review.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
