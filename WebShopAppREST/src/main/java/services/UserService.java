package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import dtos.ChocolateFactoryDTO;
import dtos.UserDTO;

@Path("/users")
public class UserService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {

		if (ctx.getAttribute("userDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}
	}
	
	
	public UserService() {
		
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void register(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		dao.addUser(user);
		if(user.getUserType().equals("Customer")) {
		CustomerTypeDAO customerTypeDAO = new CustomerTypeDAO(ctx.getRealPath(""));
		CustomerType customerType = new CustomerType(user.getId());
		customerTypeDAO.addCustomerType(customerType);
		}
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logIn(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
	    User foundUser = dao.logIn(user);

	    if (foundUser != null) {
	        System.out.println("User found: " + foundUser.getUsername());
	        String token = Jwts.builder()
	            .setSubject(foundUser.getUsername())
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 sat
	            .signWith(SignatureAlgorithm.HS256, "your_secret_key")
	            .compact();

	        
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
	public Collection<UserDTO> getAllUsers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		Collection<User> users =  dao.getAll();
		Collection<UserDTO> usersDTO = new ArrayList<>();
		CustomerTypeDAO customerTypeDAO = new CustomerTypeDAO(ctx.getRealPath(""));
		for(User user: users) {
			user.setCustomerType(customerTypeDAO.getByUserId(user.getId()));
			usersDTO.add(new UserDTO(user));
		}
		return usersDTO;
	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public User editUserInfo(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.editUserInfo(user);
	}
	
	@GET
	@Path("/managers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllFreeManagers(){
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.getAllFreeManagers();
	}
	
	@PATCH
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void assignToFactory(User manager) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		dao.assignToFactory(manager);
	}
	
	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void setToSuspicious(@PathParam("id") String userId) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		dao.setToSuspicious(userId);
	}
	
	@PATCH
	@Path("/block{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void block(@PathParam("id") String userId) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		dao.block(userId);
	}
}
