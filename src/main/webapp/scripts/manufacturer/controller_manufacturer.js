'use strict';

reviewsApp.controller('ManufacturerController', function ($scope, resolvedManufacturer, Manufacturer) {

        $scope.manufacturers = resolvedManufacturer;

        $scope.create = function () {
            Manufacturer.save($scope.manufacturer,
                function () {
                    $scope.manufacturers = Manufacturer.query();
                    $('#saveManufacturerModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.manufacturer = Manufacturer.get({id: id});
            $('#saveManufacturerModal').modal('show');
        };

        $scope.delete = function (id) {
            Manufacturer.delete({id: id},
                function () {
                    $scope.manufacturers = Manufacturer.query();
                });
        };

        $scope.clear = function () {
            $scope.manufacturer = {name: null, website: null, wiki: null, logo: null, id: null};
        };
    });
