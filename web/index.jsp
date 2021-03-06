<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Model.Episode" %>
<%@ page import="Model.MySQLConn" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Episode episode = null;
    MySQLConn conn = new MySQLConn();
    String sParamEp = request.getParameter("ep");
    int id = -1;
    if (sParamEp != null)
    {
        try
        {
            id = Integer.parseInt(sParamEp);
            episode = conn.getEpisode(id);
        } catch (NumberFormatException e) { id = -1;}
    }
    if (episode == null)
    {
        episode = conn.getLatestEpisode();
        id = episode.getId();
    }
    ArrayList<Episode> episodes = conn.getLatestEpisode(4);
    conn.CloseConnection();
%>
<html>
<head>
    <title>The Crate and Crowbar Podcast: <%=episode.getName()%>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab" rel="stylesheet">
    <link rel="stylesheet" href="index.css">
    <script type="text/javascript">var switchTo5x = true;</script>
    <script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
    <script type="text/javascript">stLight.options({ publisher: "25c08668-72b3-4217-8706-d1d6df6afafd", doNotHash: false, doNotCopy: false, hashAddressBar: false });</script>
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
<div class="header-outer-container" style=<%=episode.getImageLinkString()%>>
    <div class="header-container">
        <%@include file="header.jsp"%>
        <div class="episode-container">
            <div class="episode-info">
                <div class="episode-row">
                    <div class="episode-title"><h2><%= episode.getName() %></h2></div>
                    <div class="episode-date"><%= episode.getFormatedPostedOn()%></div>
                </div>
                <div class="player-container">
                    <audio controls class="player">
                        <source src=<%= episode.getMP3Link() %> type="audio/mpeg">
                        Your browser does not support the audio element.
                    </audio>
                </div>
                <div class="episode-row">
                    <div class="episode-description"><%=episode.getDescription()%></div>
                    <div class="share-buttons">
                        <span class='st_reddit_large' displayText='Reddit'></span>
                        <span class='st_facebook_large' displayText='Facebook'></span>
                        <span class='st_twitter_large' displayText='Tweet'></span>
                        <span class='st_email_large' displayText='Email'></span>
                    </div>
                </div>
                <div class="other-buttons">
                    <a class="button" href=<%= episode.getMP3Link() %>>DOWNLOAD</a>
                    <a class="button" href="podfiles/rss.xml">RSS</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="episodes-list">
    <div><h3>Latest episodes</h3></div>
    <div class="episodes-list-container">
        <% for (Episode episode1 : episodes)
        {
            String addSelectedClass = " selected";
            if (episode1.getId() != id) { addSelectedClass = ""; }
        %>
        <a href=<%="\"index.jsp?ep=" + episode1.getId() + "\""%>>
            <div class=<%= ("\"episodes-list-item" + addSelectedClass + "\"") %>>
                <div class="small-title"> <%=episode1.getName()%> </div>
                <div class="small-date"> <%=episode1.getFormatedPostedOn()%> </div>
                <div class="small-description"> <%=episode1.getDescription()%> </div>
            </div>
        </a>
        <% } %>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
<%
//
%>
</html>
