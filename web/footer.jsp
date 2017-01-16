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