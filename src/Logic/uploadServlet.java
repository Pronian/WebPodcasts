package Logic;

import Model.Episode;
import Model.Feed;
import Model.MySQLConn;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@MultipartConfig(fileSizeThreshold=1024*1024*10, // 10 MB
                 maxFileSize=1024*1024*200,       // 50 MB
                 maxRequestSize=1024*1024*300)
public class uploadServlet extends HttpServlet
{
    public final String filePath = "E:\\Downloads\\podfiles\\podfiles\\podcast-";
    public final String rssFilePath = "E:\\Downloads\\podfiles\\podfiles\\rss.xml";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
    {
        try
        {
            HttpSession session = request.getSession(false);
            if(session == null) { return;}
            User user = (User) session.getAttribute("currentUser");
            if(user == null) {return;}
            MySQLConn conn = new MySQLConn();
            String deleteParam = request.getParameter("delete");
            if(deleteParam != null && deleteParam.equals("last") )
            {
                conn.DeleteLastEpisode();
                updateRSS(conn);
                conn.CloseConnection();
                response.sendRedirect("allepisodes.jsp");
                return;
            }
            String epName = request.getParameter("ename");
            String desc = request.getParameter("desc");
            Part imgPart = request.getPart("imgFile");
            Part mp3Part = request.getPart("mp3File");
            if( epName == null || desc == null || epName.isEmpty()
                    || desc.isEmpty() || imgPart == null || mp3Part == null) {conn.CloseConnection(); return;}

            int podId = conn.AddPodcast(epName, desc, user);
            if(podId == 0 ) {conn.CloseConnection(); return;}

            response.sendRedirect("allepisodes.jsp");

            InputStream imgStream = imgPart.getInputStream();
            File imgTarget = new File(filePath + podId + ".jpg");
            Files.copy(imgStream,imgTarget.toPath());
            if(imgStream != null) imgStream.close();

            InputStream mp3Stream = mp3Part.getInputStream();
            File mp3Target = new File(filePath + podId + ".mp3");
            Files.copy(mp3Stream, mp3Target.toPath());
            if(mp3Stream != null) mp3Stream.close();

            updateRSS(conn);
            conn.CloseConnection();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateRSS(MySQLConn conn)
    {
        List<Episode> eps = conn.getLatestEpisode(100);
        Date date = new Date();
        Feed feed = new Feed("The Crate and Crowbar", "", "A podcast about crates and especially about bars, crowbars.", "en-US", "Copyright 2017", Utilities.DateToPubDate(date),
                "Ivan Georgiev", "http://192.168.0.102:8080/Web_Podcasts_war_exploded/index.jsp?ep=", "http://192.168.0.102:8080/Web_Podcasts_war_exploded/");
        feed.setEntries(eps);
        PodcastRSSFeedWriter rssFeedWriter = new PodcastRSSFeedWriter(feed, rssFilePath);
        try
        {
            rssFeedWriter.write();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
