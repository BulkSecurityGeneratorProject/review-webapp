'use strict';

reviewsApp.controller('ProductController', function ($scope, resolvedProduct, Product) {

        $scope.products = resolvedProduct;

        $scope.update = function (id) {
            $scope.product = Product.get({id: id});
            $('#saveProductModal').modal('show');
        };

        $scope.delete = function (id) {
            Product.delete({id: id},
                function () {
                    $scope.products = Product.query();
                });
        };
    });
