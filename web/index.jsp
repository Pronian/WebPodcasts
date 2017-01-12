<%@ page import="Model.Episode" %>
<%@ page import="Model.MySQLConn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Episode episode = null;
    MySQLConn conn = new MySQLConn();
    String sParamEp = request.getParameter("ep");
    if(sParamEp != null)
    {
        try
        {
            int id = Integer.parseInt(sParamEp);
            episode = conn.getEpisode(id);
        }
        catch (NumberFormatException e){}
    }
    if(episode == null)
    {
        episode = conn.getLatestEpisode();
    }
    conn.CloseConnection();
%>
<html>
<head>
    <title>The Crate and Crowbar Podcast: <%=episode.getName()%></title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<div class="header-outer-container" style=<%=episode.getImageLinkString()%>>
    <div class="header-container">
        <header class="header">
            <nav class="menu">
                <a href="index.jsp" class="menuitem">Home</a>
                <a class="menuitem">All Episodes</a>
                <a class="menuitem">About us</a>
            </nav>
        </header>
        <div class="episode-container">
            <div class="episode-info">
                <div class="episode-row">
                    <div class="episode-title"><h2><%= episode.getName() %></h2></div>
                    <div class="episode-date"><%= episode.getFormatedPostedOn() %></div>
                </div>
                <div class="player-container">
                    <audio controls class="player">
                        <source src=<%= episode.getMP3Link() %> type="audio/mpeg">
                        Your browser does not support the audio element.
                    </audio>
                </div>
                <div class="episode-row">
                    <div class="episode-description"> <%=episode.getDescription()%>
                    </div>
                    <div class="share-buttons">SHARE</div>
                </div>
                <div class="other-buttons">
                    <a class="download-button">DOWNLOAD</a>
                    <a class="download-button">RSS</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
