package be.interzon.web;

import be.interzon.ejb.ProductBeanRemote;
import be.interzon.model.Product;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/")
public class ProductServlet extends HttpServlet {

    @EJB
    private ProductBeanRemote productBeanRemote;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        String url = req.getParameter("url");


        productBeanRemote.insert(new Product(0L,name,description,url,Long.parseLong(price)));
        System.out.println(name+description+price+url);
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Product> products = productBeanRemote.getProducts();

//        PrintWriter writer = resp.getWriter();
//        for (Product product : products) {
//            writer.append(product.getName()).append("\n");
//        }

        // Méthode permettant de passer des informations à la JSP
        req.setAttribute("productList", products);

        // Méthode permettant de choisir la JSP à afficher
        req.getRequestDispatcher("/WEB-INF/views/index.ftl").forward(req, resp);
    }
}
