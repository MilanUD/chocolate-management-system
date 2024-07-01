package services;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
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
import dao.CommentDAO;

@Path("/chocolateFactory")
public class ChocolateFactoryService {

	@Context
	ServletContext ctx;
		
	public ChocolateFactoryService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {

		if (ctx.getAttribute("chocolateFactoryDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("chocolateFactoryDAO", new ChocolateFactoryDAO());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ChocolateFactory> getAll(){
		ChocolateFactoryDAO dao = (ChocolateFactoryDAO) ctx.getAttribute("chocolateFactoryDAO");
		ChocolateDAO chocolateDAO = new ChocolateDAO();
		Collection<Chocolate> chocolates = chocolateDAO.getAll();
		//Collection<ChocolateFactory> chocolateFactories = dao.getAll();
		for(Chocolate chocolate: chocolates) {
			dao.connectChocolateToFactory(chocolate);
		}
		return dao.getAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChocolateFactory getFactoryDetails(@PathParam("id") String id) {
		ChocolateFactoryDAO dao = (ChocolateFactoryDAO) ctx.getAttribute("chocolateFactoryDAO");
		return dao.getFactoryDetails(id);
		/*
		String contextPath = ctx.getRealPath("");
		ChocolateDAO chocolateDAO = new ChocolateDAO(contextPath);
		factory.setChocolates((List<Chocolate>) chocolateDAO.getByFabricId(id));
	    System.out.println(factory.getChocolates()); // Dodato za debugging
		return factory; */
	}
	
	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateFactoryScore(@PathParam("id") String id) {
		ChocolateFactoryDAO dao = (ChocolateFactoryDAO) ctx.getAttribute("chocolateFactoryDAO");
		CommentDAO commentDAO = new CommentDAO();
		List<Integer> scores = commentDAO.getScoresByFactoryId(id);
		dao.updateScore(id, scores);
	}
	
	
	
	
		
}
