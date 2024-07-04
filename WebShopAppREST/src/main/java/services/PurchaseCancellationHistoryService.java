package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.PurchaseCancellationHistory;
import beans.User;
import dao.PurchaseCancellationHistoryDAO;
import dao.PurchaseDAO;

@Path("/purchaseCancellationHistory")
public class PurchaseCancellationHistoryService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {

		if (ctx.getAttribute("purchaseCancellationHistoryDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("purchaseCancellationHistoryDAO", new PurchaseCancellationHistoryDAO());
		}
	}
	
	public PurchaseCancellationHistoryService() {
		// TODO Auto-generated constructor stub
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public PurchaseCancellationHistory addToPurchaseHistory(User user) {
		PurchaseCancellationHistoryDAO dao = (PurchaseCancellationHistoryDAO) ctx.getAttribute("purchaseCancellationHistoryDAO");
		return dao.addToPurchaseHistory(user.getId());
	}

}
