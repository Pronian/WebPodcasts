<%@ page import="Model.User" %>
<div class="footer">
    <div class="footer-text">
        Site design and back-end: Ivan Georgiev (ivangeorgiev4360@gmail.com)
    </div>
    <% session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) { %>
            <div class="footer-text clickable">
                <a onclick="document.getElementById('LoginDialog').style.display='block'">Admin Login</a>
            </div>
        <% } else { %>
            <div class="footer-text clickable">
                <a onclick="document.getElementById('UploadDialog').style.display='block'">Upload</a>
            </div>
            <div class="footer-text clickable">
                <a onclick="postLogout();">Logout (<%=((User)session.getAttribute("currentUser")).getUserName()%>)</a>
            </div>
        <% } %>
</div>

<script type="text/javascript">
    function postLogout()
    {
        var http = new XMLHttpRequest();

        var params = "logout=logout";

        http.open("POST", "login", true);

        http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        http.onreadystatechange = function() {

            if (http.readyState == 4 && http.status == 200) {
                window.location = http.responseURL;
            }
        }

        http.send(params);
    }

    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    function removeURLParameter( parameter) {
        var url = window.location.href;
        var urlparts= url.split('?');
        if (urlparts.length>=2) {

            var prefix= encodeURIComponent(parameter)+'=';
            var pars= urlparts[1].split(/[&;]/g);

            //reverse iteration as may be destructive
            for (var i= pars.length; i-- > 0;) {
                //idiom for string.startsWith
                if (pars[i].lastIndexOf(prefix, 0) !== -1) {
                    pars.splice(i, 1);
                }
            }

            url= urlparts[0] + (pars.length > 0 ? '?' + pars.join('&') : "");
        }
        window.history.pushState("", "", url);
    }

    window.onload = function DisplayMessage() {
        var message = getParameterByName("message", null);
        if(message == null || message == '') return;
        else if(message == 'valid')
        {
            document.getElementById("MessageLabel").innerText = 'You have been logged in successfully!';
            document.getElementById("MessageDialog").style.display='block';
        }
        else if(message == 'invalid')
        {
            document.getElementById("MessageLabel").innerText = 'The login credentials you have entered are wrong!';
            document.getElementById("MessageDialog").style.display='block';
        }
        removeURLParameter("message");
    }
</script>

<div id="LoginDialog" class="modal">
    <span onclick="document.getElementById('LoginDialog').style.display='none'" class="close" title="Close">&times;</span>
    <form class="modal-content animate" action="login" method="post">
        <div class="form-container">
            <label><b>Username</b></label><br/>
            <input type="text" placeholder="Enter Username" name="uname" required><br/>
            <label><b>Password</b></label><br/>
            <input type="password" placeholder="Enter Password" name="pass" required><br/>
        </div>
        <div class="form-button-container">
            <button type="submit">Login</button>
            <button type="button" onclick="document.getElementById('LoginDialog').style.display='none'" class="cancelbtn">Cancel</button>
        </div>
    </form>
</div>

<div id="MessageDialog" class="modal">
    <span onclick="document.getElementById('MessageDialog').style.display='none'" class="close" title="Close">&times;</span>
    <div class="modal-content animate">
        <div class="form-button-container">
            <label id="MessageLabel"><b>Username</b></label>
        </div>
        <div class="form-button-container">
            <button type="button" onclick="document.getElementById('MessageDialog').style.display='none'" class="cancelbtn">OK</button>
        </div>
    </div>
</div>