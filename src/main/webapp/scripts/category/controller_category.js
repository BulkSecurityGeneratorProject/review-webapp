'use strict';

reviewsApp.controller('CategoryController', function ($scope, resolvedCategory, Category, $modal) {

    $scope.categories = resolvedCategory;

    $scope.delete = function (id) {
        Category.delete({
                id: id
            },
            function () {
                $scope.categories = Category.query();
            });
    };

    $scope.open = function () {
        openModal();
    };

    $scope.update = function (id) {
        openModal(id);
    };

    function openModal(id) {
        var modalInstance = $modal.open({
            templateUrl: 'scripts/category/template_create_category.html',
            controller: 'CreateCategoryController',
            resolve: {
                resolvedCategory: function (Category) {
                    if (id) {
                        return Category.get({
                            id: id
                        }).$promise;
                    }
                    return;
                }
            }
        });
        modalInstance.result.then(function () {
            $scope.categories = Category.query();
        });
    }
});
