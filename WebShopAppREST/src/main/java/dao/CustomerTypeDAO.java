package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Cart;
import beans.Chocolate;
import beans.ChocolateFactory;
import beans.CustomerType;
import beans.Purchase.Status;
import dtos.PurchaseDTO;

public class CustomerTypeDAO {
	
	private HashMap<String, CustomerType> customerTypeMap = new HashMap<String, CustomerType>();
	private ObjectMapper objectMapper = new ObjectMapper();     
	private String fileName;

	public CustomerTypeDAO(String contextPath) {
		this.fileName = contextPath + "/customerTypes.json";
		loadCustomerTypes(fileName);
	}
	
	private void saveCustomerType(String fileName) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
    	    System.out.println("da li se pozivaaaaaaaaaa?");
            gson.toJson(customerTypeMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void loadCustomerTypes(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
            	customerTypeMap = objectMapper.readValue(file, new TypeReference<HashMap<String, CustomerType>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	public void addCustomerType(CustomerType customerType) {
	    String newId = generateNewId();
	    customerTypeMap.put(newId, customerType);
	    customerType.setId(newId);
	    saveCustomerType(fileName);
	}

	private String generateNewId() {
	    int maxId = 0;
	    for (String id : customerTypeMap.keySet()) {
	        int currentId = Integer.parseInt(id);
	        if (currentId > maxId) {
	            maxId = currentId;
	        }
	    }
	    return String.valueOf(maxId + 1);
	}
	
	public void addPoints(Cart cart) {
		Optional<CustomerType> result = customerTypeMap.values().stream().filter(c -> c.getUserId().equals(cart.getUser().getId())).findFirst();
		if(result.isPresent()){
            CustomerType oldCustomerType = result.get();
            oldCustomerType.setPoints(oldCustomerType.getPoints()+calculateGainedPoints(cart.getPrice()));
            oldCustomerType.setPointsUntilNextRank(oldCustomerType.getPointsUntilNextRank()-calculateGainedPoints(cart.getPrice()));
            if(oldCustomerType.getPointsUntilNextRank() <=0) {
            	upgradeRank(oldCustomerType);
            }
        	saveCustomerType(fileName);
		}
	}
	
	private double calculateGainedPoints(double price) {
		return (price/1000)*133;
	}
	
	private void upgradeRank(CustomerType oldCustomerType) {
		if(oldCustomerType.getType().equals("Bronze")) {
			oldCustomerType.setDiscount(0.05);
			oldCustomerType.setPointsUntilNextRank(1000+oldCustomerType.getPointsUntilNextRank());
			oldCustomerType.setType("Silver");
		}else if(oldCustomerType.getType().equals("Silver")) {
			oldCustomerType.setDiscount(0.1);
			oldCustomerType.setPointsUntilNextRank(0);
			oldCustomerType.setType("Golden");
		}
	}
	
	public CustomerType getByUserId(String userId) {
		loadCustomerTypes(fileName);
		Optional<CustomerType> result = customerTypeMap.values().stream().filter(c -> c.getUserId().equals(userId)).findFirst();
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	
	public void subtractPoints(PurchaseDTO purchase) {
		loadCustomerTypes(fileName);
		CustomerType customer = getByUserId(purchase.getUserId());
		if(customer == null) {
			return;
		}
		customer.setPoints(customer.getPoints()-calculateGainedPoints(purchase.getPrice())*4);
		if(customer.getPoints()<0) {
			customer.setPoints(0);
		}
		customer.setPointsUntilNextRank(customer.getPointsUntilNextRank() + calculateGainedPoints(purchase.getPrice())*4);
		if(customer.getPointsUntilNextRank() > 1000 && customer.getType().equals("Silver")) {
			customer.setType("Bronze");
			customer.setPointsUntilNextRank(customer.getPointsUntilNextRank()-1000);
			customer.setDiscount(0);
		}else if(customer.getType().equals("Golden")) {
			customer.setType("Silver");
			customer.setDiscount(0.05);
		}
		saveCustomerType(fileName);
	}
	


}
