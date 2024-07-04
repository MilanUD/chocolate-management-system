package dao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import beans.PurchaseCancellationHistory;
import beans.User;

public class PurchaseCancellationHistoryDAO {
	
	private HashMap<String, PurchaseCancellationHistory> purchaseCancellationHistoryMap = new HashMap<String, PurchaseCancellationHistory>();
	 private ObjectMapper objectMapper = new ObjectMapper()
	            .registerModule(new JavaTimeModule())
	            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);	
	 private String fileName = "C:\\Users\\Milan\\Desktop\\Web Project\\WebShopAppREST\\src\\main\\webapp";
	
	

	public PurchaseCancellationHistoryDAO() {
		// TODO Auto-generated constructor stub
		loadPurchaseCancellations(fileName);
	}
	
	
	private void loadPurchaseCancellations(String fileName) {
		try {
           File file = new File(fileName + "/purchaseCancellations.json");
           if (file.exists()) {
        	   purchaseCancellationHistoryMap = objectMapper.readValue(file, new TypeReference<HashMap<String, PurchaseCancellationHistory>>() {});
               
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
	}
	
	public PurchaseCancellationHistory add(PurchaseCancellationHistory cancellation) {
	    String newId = generateNewId();
	    purchaseCancellationHistoryMap.put(newId, cancellation);
	    cancellation.setId(newId);
	    save(fileName);
	    return cancellation;
	}


	private String generateNewId() {
	    int maxId = 0;
	    for (String id : purchaseCancellationHistoryMap.keySet()) {
	        int currentId = Integer.parseInt(id);
	        if (currentId > maxId) {
	            maxId = currentId;
	        }
	    }
	    return String.valueOf(maxId + 1);
	}
	
	private void save(String fileName) {
		try {
           File file = new File(fileName + "/purchaseCancellations.json");
           ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
           writer.writeValue(file, purchaseCancellationHistoryMap);
       } catch (IOException e) {
           e.printStackTrace();
       }
	}


	public PurchaseCancellationHistory addToPurchaseHistory(String userId) {
		// TODO Auto-generated method stub
		System.out.println("Test 1");
		Optional<PurchaseCancellationHistory> result =  purchaseCancellationHistoryMap.values().stream().filter(p -> p.getUserId().equals(userId) && p.getEndDateForRecording().isAfter(LocalDate.now())).findFirst();
		if(result.isPresent()) {
			System.out.println("Test 2a");
			PurchaseCancellationHistory cancellation = result.get();
			cancellation.setCancellationCounter(cancellation.getCancellationCounter()+1);
			save(fileName);
			System.out.println("Test 3a");
			return cancellation;
		}
		PurchaseCancellationHistory newCancellationTracker = new PurchaseCancellationHistory(userId);
		System.out.println("Test 2b");
		return add(newCancellationTracker);
	}
	
	public boolean isRelevantCancellationByUserId(String userId) {
		return purchaseCancellationHistoryMap.values().stream().anyMatch(p -> p.getUserId().equals(userId) && p.getEndDateForRecording().isAfter(LocalDate.now()));
	}

}
