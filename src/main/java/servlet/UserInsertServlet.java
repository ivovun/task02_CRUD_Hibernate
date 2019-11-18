package servlet;

import exception.DBException;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserInsertServlet",  urlPatterns = {"/insert"})
public class UserInsertServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        try {
            userService.insertUser(new User(req.getParameter("name")
                    , req.getParameter("email")
                    , req.getParameter("country")));
            resp.sendRedirect("list");
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
