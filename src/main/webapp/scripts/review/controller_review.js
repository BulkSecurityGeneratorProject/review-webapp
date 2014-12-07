'use strict';

reviewsApp.controller('ReviewController', function ($scope, resolvedReview, Review) {

        $scope.reviews = resolvedReview;

        $scope.create = function () {
            Review.save($scope.review,
                function () {
                    $scope.reviews = Review.query();
                    $('#saveReviewModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.review = Review.get({id: id});
            $('#saveReviewModal').modal('show');
        };

        $scope.delete = function (id) {
            Review.delete({id: id},
                function () {
                    $scope.reviews = Review.query();
                });
        };

        $scope.clear = function () {
            $scope.review = {reviewerName: null, text: null, rating: null, date: null, id: null};
        };
    });
