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

public class CustomerTypeDAO {
	
	private HashMap<String, CustomerType> customerTypeMap = new HashMap<String, CustomerType>();
	private ObjectMapper objectMapper = new ObjectMapper();     
	private String fileName = "C:\\Users\\Milan\\Desktop\\Web Project\\WebShopAppREST\\src\\main\\webapp";

	public CustomerTypeDAO() {
		// TODO Auto-generated constructor stub
		loadCustomerTypes(fileName);
	}
	
	private void saveCustomerType(String fileName) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName + "/customerTypes.json")) {
            gson.toJson(customerTypeMap, writer);
            System.out.println("Data successfully written to file.");
            System.out.println(fileName + "chocolate.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void loadCustomerTypes(String fileName) {
        try {
            File file = new File(fileName + "/customerTypes.json");
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
	
	public void updateCustomerType(Cart cart) {
		Optional<CustomerType> result = customerTypeMap.values().stream().filter(c -> c.getUserId().equals(cart.getUser().getId())).findFirst();
		if(result.isPresent()){
			System.out.println("User found");
            CustomerType oldCustomerType = result.get();
            oldCustomerType.setPointsUntilNextRank(oldCustomerType.getPointsUntilNextRank()-calculateGainedPoints(cart.getPrice()));
            if(oldCustomerType.getPointsUntilNextRank() <=0) {
            	updateRank(oldCustomerType);
            }
        	saveCustomerType(fileName);
		}
	}
	
	private double calculateGainedPoints(double price) {
		return (price/1000)*133;
	}
	
	private void updateRank(CustomerType oldCustomerType) {
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
		Optional<CustomerType> result = customerTypeMap.values().stream().filter(c -> c.getUserId().equals(userId)).findFirst();
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	


}
