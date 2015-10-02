(function(){
    ky3p
    .directive('modelDumper', ['$document', '$window', '$timeout', '$rootScope', 
        function ($document, $window, $timeout, $rootScope){
            return {
                restrict: 'AE',
                template: '<div id="container" ng-show="specials.show.value"><div unselectable="on" id="user" style="-moz-user-select: none; -khtml-user-select: none; -webkit-user-select: none; -o-user-select: none; height: 30px; cursor: all-scroll; word-break: break-word;"></div>'+
                '<button id="savebtn" style="width: 100px" class="btn btn-primary">Save</button>'+
                '<button id="loadbtn" class="btn btn-primary" style="width: 100px">Load</button>'+
                '<label class="auto"> <b>Global Save</b></label><input type="checkbox" name="canAuthorQuestions" ng-model="specials.globalSave.value"/></div>',
                link: function(scope, elem, attrs){
                    var jq = angular.element,
                        parentElem = jq('#container'),
                        savebtn = jq('#savebtn'),
                        loadbtn = jq('#loadbtn'),
                        LS = $window.localStorage,
                        isLocalhost = !document.location.hostname.match(/\./),
                        currentUser = null;

                    scope.specials = {
                        models: {
                            key: 'models', 
                            value: {}
                        },
                        show: {
                            key: 'show',
                            value: scope.show
                        },
                        globalSave: {
                            key: 'globalSave',
                            value: true
                        },
                        forceOpen:{
                            key: 'forceOpen',
                            value: false
                        }
                    };
                    //GUI
                    function bindDragTo(target, aim){
                        aim = aim || target;
                        var $dragging = null,
                            offset = {};
                        jq($document[0].body).on("mousemove", function(e) {
                            if ($dragging) {
                                var safe_h0 = jq($window).scrollTop() + offset.top,
                                    safe_w0 = offset.left,
                                    safe_h = jq($window).height() - $dragging.height() + jq($window).scrollTop() + offset.top,
                                    safe_w = jq($window).width() - $dragging.width() + offset.left;
                                if(e.pageY - safe_h < 0 && e.pageY - safe_h0 > 0){
                                    $dragging.offset({
                                        top: e.pageY - offset.top,
                                    });
                                }
                                if(e.pageX - safe_w < 0 && e.pageX - safe_w0 > 0){
                                    $dragging.offset({
                                        left: e.pageX - offset.left
                                    });
                                }
                            }
                        });
                        jq(aim).on("mousedown", function (e) {
                            $dragging = target;
                            offset['top'] = e.pageY - $dragging.offset().top;
                            offset['left'] = e.pageX - $dragging.offset().left;
                        });
                        jq($document[0].body).on("mouseup", function (e) {
                            $dragging = null;
                        });
                    }

                    function bindBtns(){
                        savebtn.on('click', function(e){
                            for(var skey in scope.specials.models.value){
                                if(!scope.specials.globalSave.value){
                                    serialize(skey, scope.specials.models.value, currentUser.split(' ').join(''));
                                }
                                else{
                                    serialize(skey, scope.specials.models.value);
                                }
                            }
                        });
                        loadbtn.on('click', function(e){
                            simulate();
                            scope.$apply(function(){
                                for(var dkey in scope.specials.models.value){
                                    if(!scope.specials.globalSave.value){
                                        deserialize(dkey, scope.specials.models.value, currentUser.split(' ').join(''));
                                    }
                                    else{
                                        deserialize(dkey, scope.specials.models.value);
                                    }
                                }
                            });
                        });
                    }

                    function bindHotkeys(){
                        //'ctrl + q' catches all keydowns =(
                        jq($document).on('keydown',null, 'ctrl+q', function(e) {
                            if(e.ctrlKey && e.keyCode == 81){
                                scope.$apply(function(){
                                    scope.specials.show.value = !scope.specials.show.value;
                                });
                            }
                        });
                    }

                    function bindResize(target){
                        jq($window).resize(function(e) {
                            if(jq($window).height() - (target.offset().top + target.height()) < 0){
                                target.offset({
                                    top: target.offset().top + (jq($window).height() - (target.offset().top + target.height()))
                                });
                            }
                            if(jq($window).width() - (target.offset().left + target.width()) < 0){
                                target.offset({
                                    left: target.offset().left + (jq($window).width() - (target.offset().left + target.width()))
                                });
                            }
                        });
                    }

                    function serialize(key, pool, phrase){
                        var skey = phrase ? key + phrase: key;
                        LS.setItem(skey, JSON.stringify(pool[key]));
                    }

                    function deserialize(key, pool, phrase){
                        var dkey = phrase ? key + phrase: key,
                            dump = LS[dkey];
                        if(dump){
                            dump = JSON.parse(dump);
                            angular.copy(dump, pool[key]);
                        }
                    }

                    function simulate(){
                        jq('input[type="radio"],input[type="checkbox"],select,input[type="text"],input[type="password"],input[type="hidden"],textarea', jq('form'))
                            .trigger('change')
                    }

                    function getUserName(){
                        currentUser = $rootScope.user.fullName;
                        currentUser = currentUser.toLowerCase();
                        jq('#user').text(currentUser);
                    }

                    function setStyle(elem){
                        elem[0].style.cssText = "z-index: 10;"+
                                                "max-width: 103px;"+
                                                "position: fixed;"+
                                                "top: 120px;"+
                                                "left: 74px;"+
                                                "background-color: #aaa;"+
                                                "border: 2px solid #c3c3c3;"+
                                                "border-radius: 2px;"+
                                                "background: #f0f0f0;"+
                                                "color: #594f4f;";
                    }

                    for(var spec in scope.specials){
                            if(scope.specials[spec].key in attrs.$attr){
                                if(attrs[scope.specials[spec].key].split(',').length > 1){
                                    scope.specials[spec].value = scope.$eval('{' + attrs[scope.specials[spec].key] + '}');
                                }
                                else{
                                    scope.specials[spec].value = scope.$eval(attrs[scope.specials[spec].key]);
                                }
                            }
                        }
                    if(isLocalhost || scope.specials.forceOpen.value){
                        setStyle(parentElem);
                        getUserName();
                        bindBtns();
                        bindDragTo(parentElem, jq('#user'));
                        bindHotkeys();
                        bindResize(parentElem);
                    }
                    


                    /*Object.defineProperty(Object.prototype, 'obj2arr', {
                        enumerable: false,
                        value: function(){
                                var self = this;
                                return Object.keys(self).reduce(function(prev,next){
                                    prev.push(self[next]); 
                                    return prev;
                                }, []);
                            }
                    });*/
                }
            };
    }]);
})();