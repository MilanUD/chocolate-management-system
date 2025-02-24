package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Comments;
import dao.ChocolateDAO;
import dao.CommentDAO;
import dao.UserDAO;

@Path("/comments")
public class CommentsService {
	
	@Context
	ServletContext ctx;
	
	public CommentsService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("commentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("commentDAO", new CommentDAO(contextPath));
		}
	}
	
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void addComment(Comments comment) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		comment.setUser(null);
		dao.addComment(comment);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comments> getAllByFactoryId(@PathParam("id") String id){
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		Collection<Comments> comments = dao.getByFactoryId(id);
		UserDAO userDAO = new UserDAO(ctx.getRealPath(""));
		for(Comments comment: comments) {
			comment.setUser(userDAO.getById(comment.getUserId()));
		}
		return comments;
	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Comments updateStatus(Comments comment) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.updateStatus(comment);
	}
	
	@GET
	@Path("/{userId}/{factoryId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response hasCommented(@PathParam("userId") String userId, @PathParam("factoryId") String factoryId) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		 boolean exists =  dao.hasCommented(userId, factoryId);
	     return Response.ok(exists).build();
		 
	}

	

}
