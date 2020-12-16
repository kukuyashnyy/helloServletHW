import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);

        if ("GET".equals(req.getMethod())) {
            resp.getWriter().println("<p>HelloServlet</p>");

            resp.getWriter().println("<form method='post'>\n" +
                    "<label>Name: <input name='login' /></label>\n" +
                    "<label>Password: <input name='password' /></label>\n" +
                    "<input type='submit'/>\n" +
                    "</form>");
        }
    }
}
