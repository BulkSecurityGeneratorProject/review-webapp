'use strict';

reviewsApp.controller('ProductController', function ($scope, resolvedProduct, Product) {

        $scope.products = resolvedProduct;

        $scope.delete = function (id) {
            Product.delete({id: id},
                function () {
                    $scope.products = Product.query();
                });
        };
    });
