'use strict';

reviewsApp.controller('ManufacturerController', function ($scope, resolvedManufacturer, Manufacturer, $modal) {

    $scope.manufacturers = resolvedManufacturer;

    function openModal(id) {
        var modalInstance = $modal.open({
            templateUrl: 'scripts/manufacturer/template_create_manufacturer.html',
            controller: 'CreateManufacturerController',
            resolve: {
                resolvedManufacturer: function (Manufacturer) {
                    if (id) {
                        return Manufacturer.get({
                            id: id
                        }).$promise;
                    }
                    return;
                }
            }
        });
        modalInstance.result.then(function () {
            $scope.manufacturers = Manufacturer.query();
        });
    };

    $scope.open = function () {
        openModal();
    };

    $scope.update = function (id) {
        openModal(id);
    };

    $scope.delete = function (id) {
        Manufacturer.delete({
                id: id
            },
            function () {
                $scope.manufacturers = Manufacturer.query();
            });
    };
});
