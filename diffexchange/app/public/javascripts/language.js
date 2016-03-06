

angular.module('diffexchange.language', ['ui.bootstrap', 'chieffancypants.loadingBar', 'ngAnimate', 'ui.ace'])

.config(function(cfpLoadingBarProvider) {
  cfpLoadingBarProvider.includeSpinner = true;
})

.controller('LanguageCtrl', function($scope) {
  $scope.stats = [
    { prop: 'Current Version', value: '2.11.7' },
    { prop: 'Foundation', value: '2003' },
    { prop: 'Creator(s)', value: 'Martin Odersky'},
    { prop: 'Homepage'  , value: 'scala-lang.org' },
    { prop: 'Executes On', value: 'JVM' },
    { prop: 'Online Execution', value: 'http://www.tutorialspoint.com/compile_scala_online.php' }
  ]
  $scope.keywords = ['abstract', 'case', 'catch', 'class', 'def', 'do', 'else', 'extends', 'false',
   'final', 'finally', 'for', 'forSome', 'if', 'implicit', 'import', 'lazy',
   'match', 'new', 'null', 'object', 'override', 'package', 'private',
   'protected', 'return', 'sealed', 'super', 'this', 'throw', 'trait', 'try',
   'true', 'type', 'val', 'var', 'while', 'with', 'yield'];
  $scope.helloworld = [
    'object HelloWorld extends App {',
    '  println("Hello world!")',
    '}'
  ].join('\n');
})

.controller('LoadingCtrl', function ($scope, $timeout, cfpLoadingBar) {
  $scope.isLoading = true;

  $scope.start = function() {
    cfpLoadingBar.start();
    $scope.isLoading = true;
  };

  $scope.complete = function () {
    cfpLoadingBar.complete();
    $scope.isLoading = false;
  }

  $scope.completeDelayed = function() {
    $timeout(function() {
      $scope.complete();
    }, 750);
  };

  $scope.start();
  $scope.completeDelayed();
})
;


