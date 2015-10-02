//if IE noconsole, do nothing - prevent errors
if (!window.console) console = {
    log: function() {}
};

function isIE9() {
    return document.all && !window.atob;
}

Array.prototype.indexOfAny = function (array) {
    return this.some(function(v) {
        return array.indexOf(v) != -1;
    });
}

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
};

Date.prototype.endOfWeek = function(){
    return new Date(
        this.getFullYear(),
        this.getMonth(),
        this.getDate() + 6 - this.getDay()
    );
};

function obj2string (obj, descName) {
    var descName = descName || "desc";
    return obj && obj[descName] ? obj[descName] : obj;
}

function $makePieChart(percent) {
    $('.progress-pie-chart').each(function(){
        var percent = parseInt($(this).data('percent')),
            deg = 360*percent/100;
        if (percent > 50) {
            $(this).addClass('gt-50');
        } else {
            $(this).removeClass('gt-50');
        }
        $(this)
            .find('.ppc-progress-fill')
            .css('transform','rotate('+ deg +'deg)');
    });
}

function $makeFileUpload(callback) {
    var fwrapper = $(".file_upload"),
        inp = fwrapper.find( "input" ),
        btn = fwrapper.find( "button" ),
        lbl = fwrapper.find( "i" ),
        file_api = ( window.File && window.FileReader 
            && window.FileList && window.Blob ) 
                ? true 
                : false;

    inp.change(function(){
        var file_name = file_api && inp[0].files[0] 
            ? inp[0].files[0].name
            : inp.val().replace( "C:\\fakepath\\", '' );
        if( !file_name.length ) {
            return;
        }
        lbl.text( file_name );
        callback(inp[0].files[0]);
    });
}

function convertIntToLetter (integer) {
    return String.fromCharCode(integer + 97);
};

function returnParams () {
    var queryDict = [];
    location.search.substr(1).split("&").forEach(function(item) {
        queryDict[item.split("=")[0]] = item.split("=")[1]
    });
    return queryDict;
}

function adjustTimeZone(date) {
    if(date) {
        date.setMinutes(date.getMinutes() - date.getTimezoneOffset());
        return date.toISOString();
    }
    return null;
};