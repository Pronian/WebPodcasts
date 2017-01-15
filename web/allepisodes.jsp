<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Episode" %>
<%@ page import="Model.MySQLConn" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MySQLConn conn = new MySQLConn();
    int id = -1;
    ArrayList<Episode> episodes = conn.getLatestEpisode(Integer.MAX_VALUE);
    conn.CloseConnection();
%>

<html>
<head>
    <title>The Crate and Crowbar - All Episodes</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab" rel="stylesheet">
    <link rel="stylesheet" href="index.css">
    <script type="text/javascript" src="clamp.js"></script>
    <script type="text/javascript">
        function clampSingleDesc(item, index) {
            $clamp(item, {clamp: '133px'});
        }
        function clampSingleTitl(item, index) {
            $clamp(item, {clamp: '45px'});
        }
        function clampDescriptions() {
            [].forEach.call(document.getElementsByClassName("small-title"), (clampSingleTitl));
            [].forEach.call(document.getElementsByClassName("small-description"), (clampSingleDesc));
        }
        ;
    </script>
</head>
<body onpageshow="clampDescriptions()" onresize="clampDescriptions()">
<%@include file="header.jsp"%>
<div class="episodes-list">
    <div><h3>All episodes</h3></div>
    <div class="episodes-list-container">
        <% for (Episode episode1 : episodes)
        {
            String addSelectedClass = " selected";
            if (episode1.getId() != id)
            {
                addSelectedClass = "";
            }
        %>
        <a href=<%="\"index.jsp?ep=" + episode1.getId() + "\""%>>
            <div class=<%= ("\"episodes-list-item" + addSelectedClass + "\"") %>>
                <div class="small-title">
                    <%=episode1.getName()%>
                </div>
                <div class="small-date">
                    <%=episode1.getFormatedPostedOn()%>
                </div>
                <div class="small-description">
                    <%=episode1.getDescription()%>
                </div>
            </div>
        </a>
        <% } %>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
