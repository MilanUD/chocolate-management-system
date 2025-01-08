package services;

import java.util.ArrayList;
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
import dtos.ChocolateFactoryDTO;

@Path("/chocolateFactory")
public class ChocolateFactoryService {

	@Context
	ServletContext ctx;
		
	public ChocolateFactoryService() {
	}
	
	@PostConstruct
	public void init() {

		if (ctx.getAttribute("chocolateFactoryDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("chocolateFactoryDAO", new ChocolateFactoryDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ChocolateFactoryDTO> getAll(){
		ChocolateFactoryDAO dao = (ChocolateFactoryDAO) ctx.getAttribute("chocolateFactoryDAO");
		ChocolateDAO chocolateDAO = new ChocolateDAO(ctx.getRealPath(""));
		Collection<Chocolate> chocolates = chocolateDAO.getAll();
		for(Chocolate chocolate: chocolates) {
			dao.connectChocolateToFactory(chocolate);
		}
		Collection<ChocolateFactory> factories =  dao.getAll();
		Collection<ChocolateFactoryDTO> factoriesDTO = new ArrayList<>();
		for(ChocolateFactory factory: factories) {
			factoriesDTO.add(new ChocolateFactoryDTO(factory));
		}
		return factoriesDTO;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChocolateFactory getFactoryDetails(@PathParam("id") String id) {
		ChocolateFactoryDAO dao = (ChocolateFactoryDAO) ctx.getAttribute("chocolateFactoryDAO");
		return dao.getFactoryDetails(id);
	}
	
	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateFactoryScore(@PathParam("id") String id) {
		ChocolateFactoryDAO dao = (ChocolateFactoryDAO) ctx.getAttribute("chocolateFactoryDAO");
		CommentDAO commentDAO = new CommentDAO(ctx.getRealPath(""));
		List<Integer> scores = commentDAO.getScoresByFactoryId(id);
		dao.updateScore(id, scores);
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ChocolateFactory createFactory(ChocolateFactory factoryForCreation) {
		ChocolateFactoryDAO dao = (ChocolateFactoryDAO) ctx.getAttribute("chocolateFactoryDAO");
		System.out.println(factoryForCreation.getLocation().getAddress().getCity());
		 return dao.createFactory(factoryForCreation);
	}
	
	
	
	
		
}
