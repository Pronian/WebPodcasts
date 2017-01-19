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
        return getLatestEpisode(1).get(0);
    }

    public ArrayList<Episode> getLatestEpisode(int numberOfEpisodes)
    {
        PreparedStatement stmt = null;
        ArrayList<Episode> episodes = new ArrayList<Episode>();
        int i = 1;
        try
        {
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
            try
            {
                if (stmt != null) stmt.close();
            } catch (SQLException e){e.printStackTrace();}
        }
        return episodes;
    }

    public Episode getEpisode(int id)
    {
        PreparedStatement stmt = null;
        Episode episode = null;
        try
        {
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
            try
            {
                if (stmt != null) stmt.close();
            } catch (SQLException e){e.printStackTrace();}
        }
        return episode;
    }

    /**
     * Checks if the user exists and has correct passHash.
     * @param uToCheck The user that is to be checked.
     * @return null if no such user or wrong pass, User object with correct id otherwise.
     */
    public User CheckUser(User uToCheck)
    {
        PreparedStatement stmt = null;
        User result = null;

        try
        {
            System.out.println("Creating statement...");
            stmt = conn.prepareStatement("SELECT `id`, `name`, `passHash` FROM `users` WHERE `name` = ? ;");
            stmt.setString(1, uToCheck.getUserName());
            ResultSet rs = stmt.executeQuery();

            if(rs.next() == true) //Check if such a user exists.
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String passHash = rs.getString("passHash");

                if (name.equals(uToCheck.getUserName()) && passHash.equals(uToCheck.getPassHash()))
                {
                    result = new User(name, passHash, id);
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally
        {
            try
            {
                if (stmt != null) stmt.close();
            } catch (SQLException e){e.printStackTrace();}
        }

        return result;
    }

    /**
     * Adds podcast to episodes table.
     * @param episodeName Episode name.
     * @param description Podcast description.
     * @param uploader The user who's adding it.
     * @return The new podcast's id.
     */
    public int AddEpisode(String episodeName, String description, User uploader)
    {
        int resultPodId = 0;
        int affectedRows = 0;
        if(episodeName == null || description == null || uploader == null || episodeName.isEmpty() || description.isEmpty()) return resultPodId;

        PreparedStatement stmt = null;
        try
        {
            stmt = conn.prepareStatement("INSERT INTO `episodes` (`name`, `description`, `posted_by_user`) VALUES (?,?,?);");
            stmt.setString(1,episodeName);
            stmt.setString(2,description);
            stmt.setInt(3, uploader.getUserId());

            affectedRows = stmt.executeUpdate();

            stmt.close();
        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally
        {
            try
            {
                if (stmt != null) stmt.close();
            } catch (SQLException e){e.printStackTrace();}
        }
        if(affectedRows == 1)
        {
            resultPodId = getLatestEpisode().getId();
        }
        return resultPodId;
    }

    public Boolean DeleteLastEpisode()
    {
        Episode ep = getLatestEpisode();
        int affectedRows = 0;

        PreparedStatement stmt = null;
        try
        {
            stmt = conn.prepareStatement("DELETE FROM `episodes` WHERE `id`=?;");
            stmt.setInt(1, ep.getId());

            affectedRows = stmt.executeUpdate();

            stmt.close();
        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally
        {
            try
            {
                if (stmt != null) stmt.close();
            } catch (SQLException e){e.printStackTrace();}
        }
        if(affectedRows == 1)
        {
            return true;
        }
        return false;
    }
}
