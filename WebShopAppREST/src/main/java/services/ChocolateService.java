package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Chocolate;
import beans.ChocolateFactory;
import dao.ChocolateDAO;
import dao.ChocolateFactoryDAO;

@Path("/chocolates")
public class ChocolateService {

	@Context
	ServletContext ctx;
	public ChocolateService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("chocolatesDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("chocolatesDAO", new ChocolateDAO(contextPath));
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Chocolate> getChocolates(@PathParam("id") String id) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		return dao.getByFabricId(id);
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void createChocolate(Chocolate chocolate) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		dao.addChocolate(chocolate);
	}
}
