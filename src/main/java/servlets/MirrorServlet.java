package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aleksandr on 16.01.16.
 */
public class MirrorServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        if (request == null || request.getParameterMap() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            String valueFromRequest = request.getParameter("key");
            response.getWriter().print(valueFromRequest);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        catch (NullPointerException e) {}
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    }
}
