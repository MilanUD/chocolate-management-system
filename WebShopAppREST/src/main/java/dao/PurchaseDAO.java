package dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.security.SecureRandom;
import java.util.Random;

import beans.Purchase;
import beans.User;

public class PurchaseDAO {

	private HashMap<String, Purchase> purchaseMap = new HashMap<String, Purchase>();
	private ObjectMapper objectMapper = new ObjectMapper()
	            .registerModule(new JavaTimeModule())
	            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);	
	private String fileName = "C:\\Users\\Milan\\Desktop\\Web Project\\WebShopAppREST\\src\\main\\webapp";
	
	public PurchaseDAO() {
		// TODO Auto-generated constructor stub
		loadPurchases(fileName);
	}
	
	private void loadPurchases(String fileName) {
		try {
            File file = new File(fileName + "/purchases.json");
            if (file.exists()) {
            	purchaseMap = objectMapper.readValue(file, new TypeReference<HashMap<String, Purchase>>() {});
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void addPurchase(Purchase purchase) {
	    String newId = generateNewId();
	    purchaseMap.put(newId, purchase);
	    purchase.setId(newId);
	    savePurchase(fileName);
	}


	private String generateNewId() {
		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    Integer IDENTIFIER_LENGTH = 10;
	    Random RANDOM = new SecureRandom();
	    StringBuilder identifier = new StringBuilder(IDENTIFIER_LENGTH);
	    for (int i = 0; i < IDENTIFIER_LENGTH; i++) {
	    	identifier.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
	    }
	    return identifier.toString();
	    }
	
	private void savePurchase(String fileName) {
		try {
            File file = new File(fileName + "/purchases.json");
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(file, purchaseMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
