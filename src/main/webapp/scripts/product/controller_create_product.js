'use strict';

reviewsApp.controller('CreateProductController', function ($scope, $location, resolvedManufacturer, resolvedCategories, Product) {

    $scope.manufacturers = resolvedManufacturer;
    $scope.categories = resolvedCategories;

    $scope.create = function () {
        Product.save($scope.product,
            function () {
                $location.path('/product');
            });
    };

    $scope.clear = function () {
        $scope.product = {
            name: null,
            manufacturer: null,
            category: null,
            tagList: null,
            image: null,
            wiki: null,
            createdDate: null,
            statId: null,
            id: null
        };
    };
});
