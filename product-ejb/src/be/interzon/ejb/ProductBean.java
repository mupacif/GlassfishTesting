package be.interzon.ejb;

import be.interzon.model.Product;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

@Stateless
public class ProductBean implements ProductBeanRemote {

    @EJB
    private ProductDaoBean productDaoBean;



    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstruct " + this.toString());

    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("preDestroy " + this.toString());
    }

    public List<Product> getProducts() {


        return productDaoBean.findAll();

    }

    public Product getProduct(Long id) {

        try {
            return productDaoBean.findById(id);
        } catch (NoSuchElementException e) {
            System.err.println("Product not found : " + id);
            return null;
        }
    }

    @Override
    public void insert(Product project) {
        productDaoBean.insert(project);
    }
}
