describe("ManufacturerController", function () {
    var $scope, $controller, Manufacturer, $modal, $q;
    beforeEach(module('reviewsApp'));

    beforeEach(inject(function (_$rootScope_, _$controller_, _Manufacturer_, _$modal_, _$q_) {
        $scope = _$rootScope_.$new();
        $modal = _$modal_;
        Manufacturer = _Manufacturer_;
        $q = _$q_;
        _$controller_('ManufacturerController', {
            $scope: $scope,
            resolvedManufacturer: [{
                name: 'HTC'
            }],
            Manufacturer: _Manufacturer_,
            $modal: $modal
        });
    }));

    it("should have a list of Manufacturers when controller is loaded", function () {
        expect($scope.manufacturers).not.toBeNull();
    });

    it("should open a modal when a new Manufacturer is being added", function () {
        spyOn($modal, "open").and.callFake(function () {
            var deferred = $q.defer();
            deferred.resolve('closed');
            return {
                result: deferred.promise
            };
        });
        spyOn(Manufacturer, "get");
        $scope.open();

        expect($modal.open).toHaveBeenCalledWith({
            controller: "CreateManufacturerController",
            templateUrl: 'scripts/manufacturer/template_create_manufacturer.html',
            resolve: {
                resolvedManufacturer: jasmine.any(Function)
            }
        });
        $modal.open.calls.mostRecent().args[0].resolve.resolvedManufacturer();
        expect(Manufacturer.get).not.toHaveBeenCalled();
    });

    it("should open a modal when a new Manufacturer is being edited", function () {
        spyOn($modal, "open").and.callFake(function () {
            var d = $q.defer();
            d.resolve('closed');
            return {
                result: d.promise
            };
        });
        spyOn(Manufacturer, "get").and.returnValue({});
        $scope.update("1234");

        expect($modal.open).toHaveBeenCalledWith({
            controller: "CreateManufacturerController",
            templateUrl: 'scripts/manufacturer/template_create_manufacturer.html',
            resolve: {
                resolvedManufacturer: jasmine.any(Function)
            }
        });
        $modal.open.calls.mostRecent().args[0].resolve.resolvedManufacturer(Manufacturer);

        expect(Manufacturer.get).toHaveBeenCalledWith({
            id: "1234"
        });
    });

    it("should call the delete method on the Manufacturer factory", function () {
        spyOn(Manufacturer, "delete");

        $scope.delete("123");

        expect(Manufacturer.delete).toHaveBeenCalledWith({
            id: "123"
        }, jasmine.any(Function));

    });
});
