'use strict';

reviewsApp.controller('ExpertReviewController', function ($scope, resolvedExpertReview, ExpertReview) {

        $scope.expertReviews = resolvedExpertReview;

        $scope.create = function () {
            ExpertReview.save($scope.expertReview,
                function () {
                    $scope.expertReviews = ExpertReview.query();
                    $('#saveExpertReviewModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.expertReview = ExpertReview.get({id: id});
            $('#saveExpertReviewModal').modal('show');
        };

        $scope.delete = function (id) {
            ExpertReview.delete({id: id},
                function () {
                    $scope.expertReviews = ExpertReview.query();
                });
        };

        $scope.clear = function () {
            $scope.expertReview = {url: null, summary: null, source: null, id: null};
        };
    });
