ky3p
.filter('isArray', function () {
    return function (input) {
        return angular.isArray(input);
    };
})
.filter('unique', function () {

    return function (items, filterOn) {
        if (filterOn === false) {
            return items;
        }
        if ((filterOn || angular.isUndefined(filterOn)) && angular.isArray(items)) {
            var hashCheck = {},
                newItems = [];

            var extractValueToCompare = function (item) {
                if (angular.isObject(item) && angular.isString(filterOn)) {
                    return item[filterOn];
                } else {
                    return item;
                }
            };

            angular.forEach(items, function (item) {
                var valueToCheck, isDuplicate = false;

                for (var i = 0; i < newItems.length; i++) {
                    if (angular.equals(extractValueToCompare(newItems[i]), extractValueToCompare(item))) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    newItems.push(item);
                }

            });
            items = newItems;
        }
        return items;
    };
})
/**
 * Usage in html
 * | propsFilter: {fname: searchQuery, lname: searchUser, ...}
 */
.filter('propsFilter', function () {
    return function (items, props) {
        var out = [];

        if (angular.isArray(items)) {
            items.forEach(function (item) {

                var itemMatches = false;

                var keys = Object.keys(props);
                for (var i = 0; i < keys.length; i++) {

                    var prop = keys[i];
                    var text = props[prop] ? props[prop].toLowerCase() : "";
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = items;
        }
        return out;
    };
})
.filter('reverse', function() {
    return function(items) {
        return items.slice().reverse();
    };
})
.filter('unsafe', function($sce) { return $sce.trustAsHtml; })
.filter('higlight', function ($sce) {
    return function (text, search) {
        return !search ? $sce.trustAsHtml(text) : $sce.trustAsHtml(
            text.replace(
                new RegExp(search
                    .replace(/(\\|\+|\?|\*|\(|\)|\.|\[|\])/, "\\$1"), 'gi'),
                '<span class="highlight">$&</span>'
            )
        );
    }
})
.filter('cut', function () {
    return function (value, wordwise, max, tail) {
        if (!value) return '';

        max = parseInt(max, 10);
        if (!max) return value;
        if (value.length <= max) return value;

        value = value.substr(0, max);
        if (wordwise) {
            var lastspace = value.lastIndexOf(' ');
            if (lastspace != -1) {
                value = value.substr(0, lastspace);
            }
        }

        return value + (tail || ' …');
    };
})
.filter('property', function ($parse) {
    return function (items, props, equals) {
        var out = [];

        if (angular.isArray(items) && Object.keys(props).length) {
            items.forEach(function (item) {

                var itemMatches = false;

                var keys = Object.keys(props);
                for (var i = 0; i < keys.length; i++) {

                    var prop = keys[i];
                    var text = props[prop] ? props[prop].toLowerCase() : "";

                    var itemVal = ('' + $parse(prop)(item)).toString().toLowerCase();
                    if(equals) {
                        if (itemVal == text) {
                            itemMatches = true;
                            break;
                        }
                    }
                    else {
                        if (itemVal.indexOf(text) !== -1) {
                            itemMatches = true;
                            break;
                        }
                    }

                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = items;
        }
        return out;
    };
})
.filter('nl2br', function($sce){
    return function(msg) { 
        return $sce.trustAsHtml((msg + '').replace(/([^>\r\n]?)(\r\n|\n\r|\r|\n)/g, '$1<br />$2'));
    }
})



/*.filter('timezone', function($sce){
    var timezones = {
        '+0000': ['Coordinated Universal Time', 'UTC'],
        '+0100': ['Central European Time', 'CET'],
        '+0200': ['Eastern European Time', 'EET'],
        '+0300': ['Eastern European Daylight Time', 'EEDT'],
        '+0330': ['Iran Standard Time', 'IRST'],
        '+0400': ['Gulf Standard Time', 'GST'],
        '+0430': ['Iran Daylight Time', 'IRDT'],
        '+0500': ['Indian/Kerguelen', 'TFT'],
        '+0530': ['Indian Standard Time', 'IST'],
        '+0545': ['Nepal Time', 'NPT'],
        '+0600': ['Vostok Station Time', 'VOST'],
        '+0630': ['Myanmar Standard Time', 'MST'],
        '+0700': ['Indochina Time', 'ICT'],
        '+0800': ['ASEAN Common Time', 'ACT'],
        '+0845': ['Central Western Standard Time', 'CWST'],
        '+0900': ['Japan Standard Time', 'JST'],
        '+0930': ['Central Standard Time (Australia)', 'CST'],
        '+1000': ['Eastern Standard Time (Australia)', 'EST'],
        '+1030': ['Central Summer Time (Australia)', 'CST'],
        '+1100': ['Australian Eastern Daylight Savings Time', 'AEDT'],
        '+1130': ['Norfolk Time', 'NFT'],
        '+1200': ['New Zealand Standard Time', 'NZST'],
        '+1245': ['Chatham Standard Time', 'CHAST'],
        '+1300': ['New Zealand Daylight Time', 'NZDT'],
        '+1300': ['Phoenix Island Time', 'PHOT'],
        '+1345': ['Chatham Daylight Time', 'CHADT'],
        '+1400': ['Line Islands Time', 'LINT'],
        '-0100': ['Eastern Greenland Time', 'EGT'],
        '-0200': ['Brasilia Summer Time', 'BRST'],
        '-0230': ['Newfoundland Daylight Time', 'NDT'],
        '-0300': ['Atlantic Daylight Time', 'ADT'],
        '-0330': ['Newfoundland Standard Time', 'NST'],
        '-0400': ['Atlantic Standard Time', 'AST'],
        '-0430': ['Venezuelan Standard Time', 'VET'],
        '-0500': ['Acre Time', 'ACT'],
        '-0600': ['Central Standard Time (North America)', 'CST'],
        '-0700': ['Pacific Daylight Time (North America)', 'PDT'],
        '-0800': ['Pacific Standard Time (North America)', 'PST'],
        '-0900': ['Alaska Standard Time', 'AKST'],
        '-0930': ['Marquesas Islands Time', 'MIT'],
        '-1000': ['Hawaii Standard Time', 'HST'],
        '-1100': ['Niue Time', 'NUT'],
        '-1200': ['Baker Island Time', 'BIT']
    }
    return function(date) {
        return 
    }
})*/


// no info - show dash
.filter('nisd', function(){
    return function(msg) { 
        return msg || "-";
    }
})
// no info - show em dash
.filter('nismd', function($sce){
    return function(msg) { 
        return $sce.trustAsHtml(msg || "&mdash;");
    }
})

.filter('short3dot', function () {
    return function (input, limit) {
        var limit = limit || 30;
        if(!input) return input;
        return input.length > limit+1 ? input.substr(0, limit)+"…" : input;
    };
})

// Financials - handle negative numbers 
.filter('fnumbers', function($sce){
    return function(msg) { 
        if (!msg) {
            return $sce.trustAsHtml("&mdash;");
        }
        var mtchs = null, bows = false;
        if (mtchs = msg.match(/\((\d{1,}\.\d{1,})\)/)) {
            msg = mtchs[1];
            bows = true;
        }
        return (bows ? "(" : "")+msg.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+(bows ? ")" : "");
    }
});