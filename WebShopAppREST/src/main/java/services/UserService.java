package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.User;
import dao.ChocolateFactoryDAO;
import dao.UserDAO;

@Path("/users")
public class UserService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {

		if (ctx.getAttribute("userDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAO());
		}
	}
	
	
	public UserService() {
		// TODO Auto-generated constructor stub
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void registerUser(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		dao.addUser(user);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.getAll();
	}

}
