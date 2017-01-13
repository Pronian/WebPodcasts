<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Model.Episode" %>
<%@ page import="Model.MySQLConn" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Episode episode = null;
    MySQLConn conn = new MySQLConn();
    String sParamEp = request.getParameter("ep");
    if (sParamEp != null)
    {
        try
        {
            int id = Integer.parseInt(sParamEp);
            episode = conn.getEpisode(id);
        } catch (NumberFormatException e)
        {
        }
    }
    if (episode == null)
    {
        episode = conn.getLatestEpisode();
    }
    ArrayList<Episode> episodes = conn.getLatestEpisode(6);
    conn.CloseConnection();
%>
<html>
    <head>
        <title>The Crate and Crowbar Podcast: <%=episode.getName()%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="index.css">
        <script type="text/javascript">var switchTo5x=true;</script>
        <script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
        <script type="text/javascript">stLight.options({publisher: "25c08668-72b3-4217-8706-d1d6df6afafd", doNotHash: false, doNotCopy: false, hashAddressBar: false});</script>
        <script type="text/javascript" src="clamp.js"></script>
        <script type="text/javascript">
            function clampSingle(item, index) {
                $clamp(item, {clamp: '133px'});
            }
            function clampDescriptions() {
                [].forEach.call(document.getElementsByClassName("small-description"), (clampSingle));
            };
        </script>
    </head>
    <body onload="clampDescriptions()" onresize="clampDescriptions()">
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
                            <div class="episode-title"><h2><%= episode.getName() %>
                            </h2></div>
                            <div class="episode-date"><%= episode.getFormatedPostedOn() %>
                            </div>
                        </div>
                        <div class="player-container">
                            <audio controls class="player">
                                <source src=<%= episode.getMP3Link() %> type="audio/mpeg">
                                Your browser does not support the audio element.
                            </audio>
                        </div>
                        <div class="episode-row">
                            <div class="episode-description"><%=episode.getDescription()%>
                            </div>
                            <div class="share-buttons">
                                <span class='st_reddit_large' displayText='Reddit'></span>
                                <span class='st_facebook_large' displayText='Facebook'></span>
                                <span class='st_twitter_large' displayText='Tweet'></span>
                                <span class='st_email_large' displayText='Email'></span>
                            </div>
                        </div>
                        <div class="other-buttons">
                            <a class="button" href=<%= episode.getMP3Link() %>>DOWNLOAD</a>
                            <a class="button">RSS</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="episodes-list">
            <div><h3>Latest episodes</h3></div>
            <div class="episodes-list-container">
                <% for (Episode episode1 : episodes) {%>
                <a href=<%="index.jsp?ep=" + episode1.getId()%>>
                    <div class="episodes-list-item">
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
    </body>
</html>
