package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dbService.DbException;
import dbService.dataSets.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aleksandr on 17.01.16.
 */
public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService){
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
            UserProfile user = new UserProfile(login, pass);
            try {
                accountService.addNewUser(user);
            }
            catch (DbException e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
