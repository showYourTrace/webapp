var app  = angular.module('showyourtraceAdmin.user.services', ['restangular', 'showyourtrace.configuration']);
app.factory('userAdminRepository', function ($q, Restangular) {
    var obj = {};

    obj.get = function (id) {
        return Restangular.one("user/getdetails", id).get();
    };
    obj.search = function (filters, sorts, page, size) {
        var search = angular.copy(filters);
        search['sorts'] = sorts ? sorts : [{property: 'id', direction: 'asc'}];
        search['page'] = page ? page : 1;
        search['size'] = size ? size : 25;

        return Restangular.all('user/search').post(search);
    };
    obj.create = function (model) {
        return Restangular.all("user/create").post(model);
    };
    obj.update = function (model) {
        return Restangular.all("user/update").post(model);
    };
    obj.delete = function (dealId) {
        return Restangular.one("user", dealId).remove({id: dealId});
    };

    return {
        get: obj.get,
        search: obj.search,
        create: obj.create,
        update: obj.update,
        delete: obj.delete
    }
})

.factory('userAdminModalServices', function ($modal, BASE_PATH) {
    return {
        edit: function (userId) {
            var modalInstance = $modal.open({
                templateUrl: BASE_PATH + '/page/admin/user/userEdit.html',
                controller: 'UserAdminEditCtrl',
                resolve: {
                    userId: function () {
                        return userId;
                    }
                },
                backdrop: 'static'
            });
            return modalInstance.result;
        },
        search: function () {
            var modalInstance = $modal.open({
                templateUrl: BASE_PATH + '/page/admin/user/dialogUserSearch.html',
                controller: 'dialogUserAdminSearchCtrl',
                resolve: {},
                backdrop: 'static',
                windowClass: 'modal-large-w940'
            });
            return modalInstance.result;
        }
    }
});