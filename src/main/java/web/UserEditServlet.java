package web;

import exception.DBException;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserEditServlet", urlPatterns = {"/edit"})
public class UserEditServlet extends HttpServlet {
    private UserService instance = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("user", instance.selectUser(Integer.parseInt(req.getParameter("id"))));
            req.getRequestDispatcher("user-form.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
