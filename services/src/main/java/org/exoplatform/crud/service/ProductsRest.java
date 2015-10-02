package org.exoplatform.crud.service;


import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.crud.entity.Product;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.ParseException;
import java.util.List;

/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 08/09/15
 */
@Path("/productdata")
public class ProductsRest implements ResourceContainer {
    GenericDAO genericDAO;


    private static final Log log = ExoLogger.getLogger(ProductsRest.class);

    public ProductsRest(){
        genericDAO=new GenericDAO<Product>(Product.class);

    }
    @RolesAllowed("users")
    @GET
    @Path("productlist")
    @Produces("application/json")
    public Response getProducts(/*@Context SecurityContext sc*/) {
        JSONArray jsonArray = new JSONArray();
        try {
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

    @RolesAllowed("users")
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postStudentRecord(String prod) {
        JSONObject json = null;
        Product product = new Product();

        try {
          json  = new JSONObject(prod);

            product.setLabel(json.getString("label"));
            product.setCompany(json.getString("company"));
            product.setCategory(json.getString("category"));
            product.setPrice(json.getDouble("price"));

            log.info("this is the received obj " + prod);
            log.info("this is the constructed obj "+product);
            genericDAO.persist(product);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String result = "Record entered: "+ prod;

        return Response.status(201).entity(result).build();
    }
/**    public void mockFillProducts(){
        for(int i=0;i<10;i++){
            Product product=new Product();
            product.setLabel("Product "+i);
            product.setCompany("Company "+i);
            product.setCategory("Category "+i);
            product.setPrice(65.2);
            genericDAO.persist(product);
        }
    }
*/

}
