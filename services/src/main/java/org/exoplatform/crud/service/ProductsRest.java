package org.exoplatform.crud.service;


import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.crud.entity.Product;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 08/09/15
 */
@Path("/productdata")
public class ProductsRest implements ResourceContainer {
    GenericDAO genericDAO;
    public ProductsRest(){
        genericDAO=new GenericDAO<Product>(Product.class);

    }
    @RolesAllowed("users")
    @GET
    @Path("productlist")
    @Produces("application/json")
    public Response getProducts(/*@Context SecurityContext sc*/) {
        JSONObject jsonGlobal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            mockFillProducts();
            List<Product> products=genericDAO.findAll();
            for(Product product:products){
                JSONObject json = new JSONObject();
                json.put("category",product.getCategory());
                json.put("company",product.getCompany());
                json.put("label",product.getLabel());
                json.put("price",product.getPrice());
                jsonArray.put(json);
            }
            //String userId = sc.getUserPrincipal().getName();
            return Response.ok(jsonArray.toString(), MediaType.APPLICATION_JSON).build();
        } catch(Exception e) {
            return Response.status(HTTPStatus.INTERNAL_ERROR).build();
        }
    }

    public void mockFillProducts(){
        for(int i=0;i<10;i++){
            Product product=new Product();
            product.setLabel("Product "+i);
            product.setCompany("Company "+i);
            product.setCategory("Category "+i);
            product.setPrice(65.2);
            genericDAO.persist(product);
        }
    }


}
