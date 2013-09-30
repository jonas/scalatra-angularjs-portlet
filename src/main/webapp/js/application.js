angular.module('user-list-portlet', ['ngResource']);

angular.module('user-list-portlet').factory('Users', ['$resource', '$http', function($resource, $http) {
    return $resource("/delegate/services/users");
}]);

angular.module('user-list-portlet').controller('UsersListController', ['$scope', '$location', 'Users', function($scope, $location, Users) {
    $scope.users = Users.query();
}]);