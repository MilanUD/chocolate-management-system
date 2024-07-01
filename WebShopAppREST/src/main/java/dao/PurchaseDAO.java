package dao;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


import beans.Purchase;
import beans.User;
import dtos.PurchaseDTO;

public class PurchaseDAO {

	
	private HashMap<String, Purchase> purchaseMap = new HashMap<String, Purchase>();
	private ObjectMapper objectMapper;
	private String fileName = "C:\\Users\\Milan\\Desktop\\Web Project\\WebShopAppREST\\src\\main\\webapp";
	
	public PurchaseDAO() {
		// TODO Auto-generated constructor stub
		 // Kreirajte prilagođeni format za LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.n");
        
        // Kreirajte i registrujte modul za serijalizaciju i de-serijalizaciju LocalDateTime sa prilagođenim formatom
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        
        this.objectMapper = new ObjectMapper()
            .registerModule(javaTimeModule)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
		loadPurchases(fileName);
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

	public Collection<Purchase> getByUserId(String userId) {
		// TODO Auto-generated method stub
		return purchaseMap.values().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList());
	}

	public Purchase updateStatus(PurchaseDTO purchaseDTO, String id) {
		// TODO Auto-generated method stub
		Purchase forChange = purchaseMap.get(id);
		if(forChange == null) {
			return null;
		}
		forChange.setStatus(purchaseDTO.getStatus());
		savePurchase(fileName);
		return forChange;
	}

}
