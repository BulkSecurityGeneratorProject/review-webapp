'use strict';

reviewsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/expertReview', {
                    templateUrl: 'views/expertReviews.html',
                    controller: 'ExpertReviewController',
                    resolve:{
                        resolvedExpertReview: ['ExpertReview', function (ExpertReview) {
                            return ExpertReview.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
