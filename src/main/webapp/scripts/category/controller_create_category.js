'use strict';

reviewsApp.controller('CreateCategoryController', function ($scope, resolvedCategory, $modalInstance, Category) {

    $scope.category = resolvedCategory;

    $scope.create = function () {
        Category.save($scope.category,
            function () {
                $modalInstance.close();
                $scope.clear();
            });
    };

    $scope.close = function () {
        $modalInstance.dismiss('cancel');
    }

    $scope.clear = function () {
        $scope.category = {
            name: null,
            createdDate: null,
            updatedDate: null,
            updatedBy: null,
            tags: null,
            id: null
        };
    };
});
