var app = angular.module("myApp",[]);

app.controller("clienteCtrl",function($scope){
	$scope.insertCliente= function() {
		 message = {
		   "cedula" : $scope.cedula,
		   "nombre" : $scope.nombre,
		   "celular" : $scope.celular,
		   "direccion" : $scope.direccion
		 };
		 gapi.client.clienteendpoint.insertCliente(message).execute();
		alert($scope.cedula);
	}

});