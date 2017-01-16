package Logic;

import Model.MySQLConn;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginServlet extends HttpServlet
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
    {
        try
        {
            HttpSession session;

            String logout = request.getParameter("logout");
            if(logout != null && logout.equals("logout"))
            {
                session = request.getSession(false);
                if(session != null) session.invalidate();
                response.sendRedirect("index.jsp?message=logout");
                return;
            }

            String uname = request.getParameter("uname");
            String pass = request.getParameter("pass");
            User user = new User(uname, Utilities.get_SHA_512_SecurePassword(pass, "WebPodcasts_Salt-73w56sZ1A4Qq6566CO1T8g54F1I6p5X"), 0);

            MySQLConn conn = new MySQLConn();
            User dbUser = conn.CheckUser(user);

            if(dbUser != null)
            {
                session = request.getSession(true);
                session.setAttribute("currentUser", dbUser);
                response.sendRedirect("index.jsp?message=valid");
            }
            else
            {
                response.sendRedirect("index.jsp?message=invalid");
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
