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
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import beans.CustomerType;
import beans.User;
import dao.ChocolateFactoryDAO;
import dao.CustomerTypeDAO;
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
	public void register(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		dao.addUser(user);
		CustomerTypeDAO customerTypeDAO = new CustomerTypeDAO();
		CustomerType customerType = new CustomerType(user.getId());
		customerTypeDAO.addCustomerType(customerType);
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logIn(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
	    System.out.println("Login endpoint hit with username: " + user.getUsername());
	    User foundUser = dao.logIn(user);

	    if (foundUser != null) {
	        System.out.println("User found: " + foundUser.getUsername());
	        String token = Jwts.builder()
	            .setSubject(foundUser.getUsername())
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 sat
	            .signWith(SignatureAlgorithm.HS256, "your_secret_key")
	            .compact();

	        // Kreiraj objekat koji sadrži token i korisničke podatke
	        Map<String, Object> responseMap = new HashMap<>();
	        responseMap.put("token", token);
	        responseMap.put("user", foundUser);

	        return Response.ok(responseMap).build();
	    } else {
	        System.out.println("Unauthorized access");
	        return Response.status(Response.Status.UNAUTHORIZED).build();
	    }
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.getAll();
	}

}
