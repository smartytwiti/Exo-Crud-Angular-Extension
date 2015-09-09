package org.exoplatform.crud.service;

import junit.framework.TestCase;
import org.exoplatform.crud.entity.Product;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 03/09/15
 */
public class GenericDAOTest{

    @Test
    public void addProduct(){
        Product product=new Product();
        product.setCompany("company1");
        product.setCategory("category1");
        product.setLabel("product1");
        product.setPrice(50.2);
        GenericDAO<Product> genericDAO=new GenericDAO<Product>(Product.class);
        genericDAO.persist(product);
        List<Product> products=genericDAO.findAll();
        Assert.assertNotNull(products);
        Product product1=products.get(0);
        Assert.assertNotNull(genericDAO.findById(product1.getId()));
        Assert.assertEquals(product1.getLabel(), product.getLabel());
        genericDAO.delete(product1);
        Assert.assertEquals(0, genericDAO.findAll().size());
    }
}
