package be.interzon.web;

import be.interzon.ejb.ProductBeanRemote;
import be.interzon.model.Product;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/product/*")
public class DetailsServlet extends HttpServlet {

    @EJB
    private ProductBeanRemote productBeanRemote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idStr = req.getPathInfo().substring(1);
        // resp.getWriter().append("Page details for product : ").append(idStr);

        // Récupérer le produit via son ID

        Long id = Long.parseLong(idStr);
        Product product = productBeanRemote.getProduct(id);

        // Afficher une JSP avec les données du produit

        req.setAttribute("product", product);
        req.getRequestDispatcher("/WEB-INF/views/details.ftl").forward(req, resp);
    }

//    @Resource(mappedName = "jms/ConnectionFactory")
    private ConnectionFactory connectionFactory;

//    @Resource(mappedName = "jms/Queue")
    private Queue queue;

    private Session session;
    private MessageProducer messageProducer;

    @PostConstruct
    public void postConstruct() {

        try {
            Context context = new InitialContext();
            connectionFactory = (ConnectionFactory) context.lookup("jms/ConnectionFactory");
            queue = (Queue) context.lookup("jms/Queue");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idStr = req.getParameter("id");

        // Send message
        try {
            TextMessage message = session.createTextMessage();
            message.setText("Order product : " + idStr);
            messageProducer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
