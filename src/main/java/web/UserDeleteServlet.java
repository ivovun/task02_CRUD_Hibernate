package web;

import exception.DBException;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserDeleteServlet",  urlPatterns = {"/delete"})
public class UserDeleteServlet extends HttpServlet {
    private UserService instance = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        try {
            instance.deleteUser(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect("list");
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
