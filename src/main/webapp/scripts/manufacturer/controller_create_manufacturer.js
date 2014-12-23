'use strict';

reviewsApp.controller('CreateManufacturerController', function ($scope, resolvedManufacturer, $modalInstance, Manufacturer) {

    $scope.manufacturer = resolvedManufacturer;

    $scope.create = function () {
        Manufacturer.save($scope.manufacturer,
            function () {
                $modalInstance.close();
                $scope.clear();
            });
    };

    $scope.close = function () {
        $modalInstance.dismiss('cancel');
    }

    $scope.clear = function () {
        $scope.manufacturer = {
            name: null,
            website: null,
            wiki: null,
            logo: null,
            id: null
        };
    };
});
