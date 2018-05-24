package be.interzon.ejb;

import be.interzon.model.Product;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ProductBeanRemote {

    List<Product> getProducts();

    Product getProduct(Long id);
    void insert(Product project);
}
