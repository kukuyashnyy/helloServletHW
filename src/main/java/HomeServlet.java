import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class HomeServlet extends HttpServlet {

    private final Gson gson = new Gson();
    Integer id = 0;
    HashMap<Integer, String[]> tasks = new HashMap<>();
    HashMap<Integer, String[]> doneTasks = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        PrintWriter writer = resp.getWriter();

        writer.println("<form method='post'>\n" +
                "<label>Task name: <input name='name' /></label>\n" +
                "<label>Description: <input name='description' /></label>\n" +
                "<input type='submit'/>\n" +
                "</form>");
        writer.println("<table border=\"1\">");
        writer.println("<tr>");
        writer.println("<th>Id</th>");
        writer.println("<th width=\"450px\">Name</th>");
        writer.println("<th width=\"450px\">Description</th>");
        writer.println("<th>Action</th>");
        writer.println("</tr>");
        if (!tasks.isEmpty()){
            for (Integer key : tasks.keySet()){
                writer.println("<tr>");
                writer.println("<td>" + key + "</td>");
                for (String str : tasks.get(key)) {
                    writer.println("<td>" + str + "</td>");
                }
                writer.println("<td>");
                writer.println("<form method='post'>\n");
                writer.println("<input type='hidden' name='id' value='" + key + "' />\n");
                writer.println("<button type='submit'>Done</button>\n");
                writer.println("</form>");
                writer.println("</td>");
                writer.println("</tr>");
            }
        }
        writer.println("</table>");
        writer.println("<form method='post'>\n");
        writer.println("<input type='hidden' name='forward' value='DoneTasks' />\n");
        writer.println("<button type='submit'>Done tasks</button>\n");
        writer.println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
//        System.out.println("name: " + req.getParameter("name"));
//        System.out.println("id: " + req.getParameter("id"));
        if (req.getParameter("name") != null) {
            if (!req.getParameter("name").isEmpty()) {
                String[] data = {req.getParameter("name"), req.getParameter("description")};
                tasks.put(id++, data);
//            System.out.println(req.getParameter("name"));
//            System.out.println(req.getParameter("description"));
            }
        }
        if (req.getParameter("id") != null) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            doneTasks.put(id, tasks.get(id));
            tasks.remove(id);
        }
        if (req.getParameter("forward") != null){
//            System.out.println(gson.toJson(tasks));
            req.setAttribute("userJson", gson.toJson(doneTasks));
//            System.out.println("Serialization json:\n" + gson.toJson(tasks));
            req.getRequestDispatcher("/done").forward(req, resp);
        }
        doGet(req, resp);
    }
}
