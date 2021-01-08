import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.*;

public class DoneServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        System.out.println("Deserialization json:\n" + (String) req.getAttribute("userJson"));

        final Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, String[]>>(){}.getType();
        HashMap<Integer, String[]> tasks = gson.fromJson((String) req.getAttribute("userJson"), type);
        System.out.println("Deserialization:\n" + tasks.toString());

        PrintWriter writer = resp.getWriter();

        writer.println("<table border=\"1\">");
        writer.println("<tr>");
        writer.println("<th>Id</th>");
        writer.println("<th width=\"450px\">Name</th>");
        writer.println("<th width=\"450px\">Description</th>");
        writer.println("</tr>");
        if (!tasks.isEmpty()){
            Integer[] keys = tasks.keySet().toArray(new Integer[0]);
            Arrays.sort(keys, Collections.<Integer>reverseOrder());
            for (Integer key : keys){
                writer.println("<tr>");
                writer.println("<td>" + key + "</td>");
                for (String str : tasks.get(key)) {
                    writer.println("<td>" + str + "</td>");
                }
            }
        }
        writer.println("</table>");
        resp.getWriter().println("<a href='/'>Task list</a>");
    }
}
