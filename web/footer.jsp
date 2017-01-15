<div class="footer">
    <div class="footer-text">
        Site design and back-end: Ivan Georgiev (ivangeorgiev4360@gmail.com)
    </div>
    <div class="footer-text clickable">
        <a onclick="document.getElementById('LoginDialog').style.display='block'">Admin Login</a>
    </div>
</div>

<div id="LoginDialog" class="modal">
    <span onclick="document.getElementById('LoginDialog').style.display='none'" class="close" title="Close">&times;</span>
    <form class="modal-content animate" action="login.jsp" method="post">
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