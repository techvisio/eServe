var EmailId = '';

function Trim(value) {
    return value.replace(/^\s*/, "").replace(/\s*$/, "");
}

function FirstFocus() {

    var username = document.getElementById("username"),
        password = document.getElementById("password");

    username.setAttribute("autocomplete", "off");
    password.setAttribute("autocomplete", "off");
    username.value = Trim(username.value);

    if (username.value.length < 1) {
        username.focus();
    } else {
        password.focus();
    }
}

function handleErrorMessage() {

    var locationUrl = window.location.href,
        error = document.getElementById("errorMesg"),
        errorMessage = '';

    error.innerHTML = "";
    if (locationUrl.indexOf('msg=') > -1) {
        errorMessage = decodeURIComponent(locationUrl.substr(locationUrl.indexOf('msg=') + 4));
    }

    if (errorMessage == 'BAD_CREDENTIALS') {
        errorMessage = 'Invalid username or password.'
    }

    if (errorMessage == 'COMPANY_EXPIRED') {
        errorMessage = 'Your company no longer has an active subscription,please contact <a href="http://www.markit.com/Contact-Us" target="_blank">Markit KY3P support</a> to for more information.';
    }

    error.innerHTML = errorMessage;
    document.getElementById("username").focus();
    document.getElementById("loginContainer").className = "";
    document.getElementById("loginMessage").style.display = errorMessage ? "block" : "none";
}

function fillLoginFromLocalStorage() {
    if (localStorage.getItem('checkboxRemMe', false)) {
        document.getElementById('checkboxRemMe').checked = localStorage.getItem('checkboxRemMe', false);

        if (localStorage.getItem('username', null)) {
            document.getElementById('username').value = localStorage.getItem('username', '');
        }
    } else {
        localStorage.removeItem('username');
    }
}

function showOne(who) {
    var elm = document.querySelectorAll('.view'),
        reg = new RegExp(who + '$');
    for (var i = 0; i < elm.length; i++) {
        elm[i].style.display = elm[i].className.match(reg) ? "block" : "none";
    }
}

function changeView() {
    var hash = location.hash;
    if (hash.match(/reset$/)) {
        showOne('reset');
    } else if (hash.match(/reset\-success$/)) {
        showOne('reset\-success');
    } else if (hash.indexOf('restore-success') != -1) {
        showOne('restore\-success');
    } else if (hash.indexOf('restore') != -1) {
        showOne('restore');
    } else {
        showOne('login');
    }
}

document.addEventListener("DOMContentLoaded", function(event) {

    var url = window.location + "",
        loginSubmit = document.getElementById("loginSubmit"),
        resetSubmit = document.getElementById("resetSubmit"),
        restoreSubmit = document.getElementById("restoreSubmit"),
        checkboxRemMe = document.getElementById('checkboxRemMe');

    fillLoginFromLocalStorage();
    handleErrorMessage();

    window.onhashchange = changeView;
    changeView();

    checkboxRemMe.onchange = function checkboxRemMeChange() {
        if (this.checked) {
            window.localStorage.setItem('checkboxRemMe', true);
        } else {
            window.localStorage.removeItem('checkboxRemMe');
        }
    }

    function styleError(iserror, div, form, name, error, errtxt) {
        div.className = "form-group " + name + (iserror ? " error" : "");
        form.className = "form-control " + name + (iserror ? " error" : "");
        if (typeof error != "undefined" && typeof errtxt != "undefined")
            if (typeof errtxt == "string") error.innerHTML = errtxt;
            else if (typeof errtxt.data == "string") error.innerHTML = errtxt.data;
        else if (typeof errtxt.data.message == "string") error.innerHTML = errtxt.data.message;
        else if (typeof errtxt.message == "string") error.innerHTML = errtxt.message;
    }

    loginSubmit.onclick = function() {
        document.login.action = "j_spring_security_check";

        var username = document.getElementById("username"),
            password = document.getElementById("password"),
            error = document.getElementById("errorMesg"),
            divUserName = username.parentNode,
            divPassword = password.parentNode,
            iserror = false;

        username.value = Trim(username.value);
        password.value = Trim(password.value);

        error.innerHTML = "";
        if (username.value.length < 1) {
            error.innerHTML = "MCS-31007 : Missing Username";
            username.focus();
            iserror = true;
            styleError(iserror, divUserName, username, "userName");
        }

        if (password.value.length < 1) {
            error.innerHTML = !iserror ? "MCS-31008 : Missing Password" : "MCS-31009 : Missing Username and Password";

            if (!iserror) {
                password.focus();
            }
            iserror = true;
            styleError(iserror, divPassword, password, "password");
        }

        if (!iserror) {
            document.getElementById("loginContainer")
                .className = "animated bounceOut";
            localStorage.setItem('username',
                checkboxRemMe.checked ? username.value : null);
            return true;
        }

        localStorage.removeItem('username');
        return false;
    }

    resetSubmit.onclick = function() {
        var username = document.getElementById("reset_username"),
            error = document.getElementById("rErrorMesg"),
            divUserName = username.parentNode,
            errtxt = "",
            iserror = false,
            errfun;
        username.value = Trim(username.value);
        error.innerHTML = "";
        if (username.value.length < 1) {
            error.innerHTML = "MCS-31007 : Missing Username";
            username.focus();
            iserror = true;
            styleError(iserror, divUserName, username, "userName");
        }
        errfun = styleError.bind(styleError, true, divUserName, username, "userName", error);
        if (!iserror) {
            httpPut("/kyv-web/ext/resetpasswordrequest?emailId=" + encodeURIComponent(username.value), function() {
                showOne('reset\-success');
            }, errfun);
            return true;
        }
        localStorage.removeItem('username');
        return false;
    }

    restoreSubmit.onclick = function() {
        var restore_password = document.getElementById("restore_password"),
            restore_passwordcopy = document.getElementById("restore_passwordcopy"),
            error = document.getElementById("rsErrorMesg"),
            divPassName = restore_password.parentNode,
            divPassCName = restore_passwordcopy.parentNode,
            errtxt = "MCS-31008 : Missing Password",
            iserror = false,
            errfun;
        restore_password.value = Trim(restore_password.value);
        restore_passwordcopy.value = Trim(restore_passwordcopy.value);
        error.innerHTML = "";
        if (restore_password.value.length < 1) {
            error.innerHTML = "MCS-31008 : Missing Password";
            restore_password.focus();
            iserror = true;
            styleError(iserror, divPassName, restore_password, "password");
        }
        if (restore_passwordcopy.value.length < 1) {
            error.innerHTML = "MCS-31008 : Missing Password";
            restore_passwordcopy.focus();
            iserror = true;
            styleError(iserror, divPassCName, restore_passwordcopy, "password");
        }
        if (restore_passwordcopy.value !== restore_password.value) {
            error.innerHTML = "The entered passwords does not match";
            restore_passwordcopy.focus();
            iserror = true;
            styleError(iserror, divPassCName, restore_passwordcopy, "password");
        }
        var hashcode = window.location.hash.split('resetcode=')[1];
        errfun = styleError.bind(styleError, true, divPassCName, restore_password, "password", error);
        if (!iserror) {
            httpPut("/kyv-web/ext/forgotpassword/", function() {
                window.location.hash = window.location.hash.split('restore')[0] + "restore\-success";
            }, errfun, {
                "hashCode": hashcode,
                "oldPassword": restore_password.value
            });
            return true;
        }
        return false;
    }

    function httpPut(theUrl, sucfun, errfun, bd) {
        var mimeType = bd ? "application/json" : "text/plain";
        var xmlHttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
        xmlHttp.open('PUT', theUrl, true);
        xmlHttp.setRequestHeader('Content-Type', mimeType);
        xmlHttp.send(JSON.stringify(bd));
        xmlHttp.onreadystatechange = function() {
            if (xmlHttp.readyState == 4) {
                if (xmlHttp.status == 200) {
                    sucfun();
                } else {
                    errfun(JSON.parse(xmlHttp.responseText));
                }
            }
        }
    }

    document.getElementById("_request_url_").value = window.location.href;

    // remove before release - for testing purposes
    if (document.referrer.match(/\/home\//)) {
        var xmlhttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");

        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4) {
                if (xmlhttp.status == 200) {
                    if ((typeof xmlhttp.responseURL != "undefined" && xmlhttp.responseURL.match(/\/user\//)) || xmlhttp.responseText.match(/\:\"Success\"/)) {
                        location.href = "./home/";
                    }
                }
            }
        }

        xmlhttp.open("GET", "/kyv-web/service/v1/user/LoggedInUserDetail", true);
        xmlhttp.send();
    }
});