describe("CategoryController", function () {
    var $scope, $q, Category, $modal;

    beforeEach(module('reviewsApp'));

    beforeEach(inject(function (_$rootScope_, _$controller_, _Category_, _$q_, _$modal_) {
        $q = _$q_;
        $scope = _$rootScope_.$new();
        $modal = _$modal_;
        Category = _Category_;

        _$controller_('CategoryController', {
            $scope: $scope,
            Category: Category,
            resolvedCategory: [{
                name: 'Mobiles'
            }],
            $modal: $modal
        });
    }));

    it("should load Categories before controller is loaded", function () {
        expect($scope.categories).not.toBeNull();
    });

    it("should open modal when Category is being added", function () {
        spyOn($modal, "open").and.callFake(function () {
            var d = $q.defer();
            d.resolve('closed');
            return {
                result: d.promise
            };
        });

        spyOn(Category, "get");

        $scope.open();

        expect($modal.open).toHaveBeenCalledWith({
            controller: "CreateCategoryController",
            templateUrl: 'scripts/category/template_create_category.html',
            resolve: {
                resolvedCategory: jasmine.any(Function)
            }
        });
        $modal.open.calls.mostRecent().args[0].resolve.resolvedCategory();
        expect(Category.get).not.toHaveBeenCalled();
    });

    it("should open model and load details when a Category is being edited", function () {
        spyOn($modal, "open").and.callFake(function () {
            var d = $q.defer();
            d.resolve('closed');
            return {
                result: d.promise
            };
        });

        spyOn(Category, "get").and.returnValue({});

        $scope.update("123");

        expect($modal.open).toHaveBeenCalledWith({
            controller: "CreateCategoryController",
            templateUrl: 'scripts/category/template_create_category.html',
            resolve: {
                resolvedCategory: jasmine.any(Function)
            }
        });
        $modal.open.calls.mostRecent().args[0].resolve.resolvedCategory(Category);
        expect(Category.get).toHaveBeenCalledWith({
            id: "123"
        });
    });
});
