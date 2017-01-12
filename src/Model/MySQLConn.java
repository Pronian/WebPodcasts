package Model;


import java.sql.*;
import java.util.*;
import java.util.Date;

public class MySQLConn
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/webpoddb";

    static final String USER = "root";
    static final String PASS = "5904360";

    private Connection conn;

    public MySQLConn()
    {
        try
        {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e)
        {
            System.out.println("Error when connecting to database:\n");
            e.printStackTrace();
        }
    }

    public void CloseConnection()
    {
        try
        {
            if (conn != null) conn.close();
        } catch (SQLException se)
        {
            se.printStackTrace();
        }
    }

    public Episode getLatestEpisode()
    {
        PreparedStatement stmt = null;
        Episode episode = null;
        try
        {
            System.out.println("Creating statement...");
            stmt = conn.prepareStatement("SELECT `id`, `name`, `description`, `posted_on` FROM `episodes` ORDER BY `posted_on` DESC LIMIT 1;");

            ResultSet rs = stmt.executeQuery();
            rs.next();

            int id = rs.getInt("id");
            String name = rs.getString("name");
            String desc = rs.getString("description");
            Date date = rs.getTimestamp("posted_on");

            episode = new Episode(id, name, desc, date);

            rs.close();
            stmt.close();
        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null) stmt.close();
            } catch (SQLException e)
            {
            }// nothing we can do
        }//end try
        System.out.println("Retrieved latest episode.");
        return episode;
    }

    public ArrayList<Episode> getLatestEpisode(int numberOfEpisodes)
    {
        PreparedStatement stmt = null;
        ArrayList<Episode> episodes = new ArrayList<Episode>();
        int i = 1;
        try
        {
            System.out.println("Creating statement...");
            stmt = conn.prepareStatement("SELECT `id`, `name`, `description`, `posted_on` FROM `episodes` ORDER BY `posted_on` DESC LIMIT ? ;");
            stmt.setInt(i, numberOfEpisodes);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                Date date = rs.getTimestamp("posted_on");

                Episode episode = new Episode(id, name, desc, date);
                episodes.add(episode);
            }

            rs.close();
            stmt.close();
        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null) stmt.close();
            } catch (SQLException e)
            {
            }// nothing we can do
        }//end try
        System.out.println("Retrieved latest "+ numberOfEpisodes +" episodes.");
        return episodes;
    }

    public Episode getEpisode(int id)
    {
        PreparedStatement stmt = null;
        Episode episode = null;
        try
        {
            System.out.println("Creating statement...");
            stmt = conn.prepareStatement("SELECT `id`, `name`, `description`, `posted_on` FROM `episodes` WHERE `id`=? ORDER BY `posted_on` DESC LIMIT 1;");

            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            String name = rs.getString("name");
            String desc = rs.getString("description");
            Date date = rs.getTimestamp("posted_on");

            episode = new Episode(id, name, desc, date);

            rs.close();
            stmt.close();
        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null) stmt.close();
            } catch (SQLException e)
            {
            }// nothing we can do
        }//end try
        System.out.println("Retrieved episode.");
        return episode;
    }
}
