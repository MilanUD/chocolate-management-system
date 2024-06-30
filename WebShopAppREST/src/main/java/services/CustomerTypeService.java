package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.CustomerType;
import dao.ChocolateDAO;
import dao.CustomerTypeDAO;

@Path("/customerTypes")
public class CustomerTypeService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("customerTypeDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("customerTypeDAO", new CustomerTypeDAO());
		}
	}

	public CustomerTypeService() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerType getCustomerTypeByUserId(@PathParam("id") String userId) {
		CustomerTypeDAO dao = (CustomerTypeDAO) ctx.getAttribute("customerTypeDAO");
		return dao.getByUserId(userId);
		
	}

}
