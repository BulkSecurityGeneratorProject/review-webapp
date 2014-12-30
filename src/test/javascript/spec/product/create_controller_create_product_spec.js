describe("CreateProductController", function() {
    var $scope, Product, $location, $controller;
    beforeEach(module('reviewsApp'));

    beforeEach(inject(function(_$rootScope_, _$controller_, _Product_, _$location_) {
        $scope = _$rootScope_.$new();
        Product = _Product_;
        $location = _$location_;
        $controller = _$controller_;
    }));

    describe("Saving a new Product", function() {

        beforeEach(function() {
            $controller('CreateProductController', {
                $scope: $scope,
                resolvedManufacturer: [],
                resolvedCategories: [],
                resolvedProduct: null,
                Product: Product
            });
        });

        it("should not have a product loaded from before when a new product is being saved", function() {
            expect($scope.product).toBeNull();
        });

        it("should save the product", function() {
            spyOn(Product, "save");
            spyOn($location, "path");

            $scope.create();

            expect(Product.save).toHaveBeenCalledWith($scope.product, jasmine.any(Function));
            Product.save.calls.mostRecent().args[1]();
            expect($location.path).toHaveBeenCalledWith("/product");
        });
    });

    describe("Editing a product", function() {
        beforeEach(function() {
            $controller('CreateProductController', {
                $scope: $scope,
                resolvedManufacturer: [],
                resolvedCategories: [],
                resolvedProduct: {
                    name: '8x'
                },
                Product: Product
            });
        });

        it("should load the product before editing starts", function() {
            expect($scope.product).not.toBeNull();
        });

        it("should save the edited product", function() {
            spyOn(Product, "save");
            spyOn($location, "path");

            $scope.create();

            expect(Product.save).toHaveBeenCalledWith($scope.product, jasmine.any(Function));
            Product.save.calls.mostRecent().args[1]();
            expect($location.path).toHaveBeenCalledWith("/product");
        });

    });

});
