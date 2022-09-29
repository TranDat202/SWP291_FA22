/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Control;

import dao.DAO;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author datbe
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//       String email = request.getParameter("email");
//       String password = request.getParameter("password");
//       DAO dao = new DAO();
//       User a = dao.login(email, password);
//        if (a == null) {
//            request.getRequestDispatcher("view/login.jsp").forward(request, response);
//        }else{
//            HttpSession session = request.getSession();
//            response.sendRedirect("home");
//        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        Cookie arr[] = request.getCookies();
        if (arr!=null) {// neu cookie bi xóa het khi dóng trình duyet
            for (Cookie o : arr) {
                if (o.getName().equals("emailC")) {
                    request.setAttribute("emailC", o.getValue());
                }
                if (o.getName().equals("passC")) {
                    request.setAttribute("passC", o.getValue());
                }
            }
        }
        request.getRequestDispatcher("view/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        DAO dao = new DAO();
        User a = dao.login(email, password);
        User acc = null;
        if (acc == null) {
            request.setAttribute("messAlert", "Wrong username or password");
            request.setAttribute("emailC", email);
            request.getRequestDispatcher("view/login.jsp").forward(request, response);
            
        }else{
            if (acc.getRole() != 1 && acc.getRole()!=2 && acc.getRole()!=3) {
                request.setAttribute("messAlert", "Account is being banned");
                request.setAttribute("emailC", email);
                request.getRequestDispatcher("view/login.jsp").forward(request, response);
            }else{
                HttpSession session = request.getSession();
                String userID = acc.getUserID() + "";
                session.setAttribute("acc", acc);
                Cookie userC = new Cookie("emailC", email);
                Cookie passC = new Cookie("passC", password);
                 if (remember != null) {
                    userC.setMaxAge(3600);
                    passC.setMaxAge(3600);
                } else {
                    userC.setMaxAge(0);
                    passC.setMaxAge(0);
                }

                response.addCookie(userC);
                response.addCookie(passC);
                request.getRequestDispatcher("view/home.jsp").forward(request, response);

            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
