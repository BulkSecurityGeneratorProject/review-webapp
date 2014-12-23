'use strict';

reviewsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/product', {
                    templateUrl: 'views/products.html',
                    controller: 'ProductController',
                    resolve:{
                        resolvedProduct: ['Product', function (Product) {
                            return Product.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
                .when('/product/create-product', {
                    templateUrl : 'views/create-product.html',
                    controller : 'CreateProductController',
                    resolve:{
                        resolvedManufacturer: ['Manufacturer', function (Manufacturer) {
                            return Manufacturer.query().$promise;
                        }], 
                        resolvedCategories : ['Category', function(Category) {
                          return Category.query().$promise;  
                        }]
                    },
                    access : {
                        authorizedRoles: [USER_ROLES.all]
                    }
                });
        });
