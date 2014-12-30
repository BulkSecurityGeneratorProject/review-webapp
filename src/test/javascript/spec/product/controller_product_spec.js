describe("ProductController", function() {
    var $scope, ProductService;

    beforeEach(module('reviewsApp'));

    beforeEach(inject(function (_$rootScope_, _$controller_, _Product_) {
        $scope = _$rootScope_.$new();
        ProductService = _Product_;
        var resolvedProducts = [];
        _$controller_('ProductController', {
            $scope: $scope,
            resolvedProduct: resolvedProducts,
            Product: ProductService
        });
    }));

    it('should call the delete function on the Product factory', function() {
     spyOn(ProductService, "delete");
     spyOn(ProductService, "query");
     $scope.delete("123123");
     expect(ProductService.delete).toHaveBeenCalledWith({id: "123123"}, jasmine.any(Function));
     ProductService.delete.calls.mostRecent().args[1]();
     expect(ProductService.query).toHaveBeenCalled();
 });
});
