package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dbService.DbException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aleksandr on 17.01.16.
 */
public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService){
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (login == null || pass == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            try {
                UserProfile profile = accountService.getUserByLogin(login);
                if (profile == null || !profile.getPass().equals(pass)){
                    response.setContentType("text/html;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
                else {
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().println("Authorized");
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            }
            catch (DbException e) {}
        }
    }

}
