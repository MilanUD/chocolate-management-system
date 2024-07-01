package dao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Chocolate;
import dtos.PurchaseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChocolateDAO {

	private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private HashMap<String, Chocolate> chocolateMap = new HashMap<String, Chocolate>();
	private String fileName = "C:\\Users\\Milan\\Desktop\\Web Project\\WebShopAppREST\\src\\main\\webapp";
	
	public ChocolateDAO() {
		
		loadChocolates(fileName);
	}	
	
	private void loadChocolates(String fileName) {
        try {
            File file = new File(fileName + "/chocolate.json");
            if (file.exists()) {
                chocolateMap = objectMapper.readValue(file, new TypeReference<HashMap<String, Chocolate>>() {});
                System.out.println(fileName + "/chocolate.json");
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void saveChocolates(String fileName) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName + "/chocolate.json")) {
            gson.toJson(chocolateMap, writer);
            System.out.println("Data successfully written to file.");
            System.out.println(fileName + "chocolate.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public Collection<Chocolate> getByFabricId(String fabricId){
		loadChocolates(fabricId);
		return chocolateMap.values().stream().filter(c -> c.getFabricId().equals(fabricId) && !c.getIsDeleted()).collect(Collectors.toList());
	}
	
	public void addChocolate(Chocolate chocolate) {
	    String newId = generateNewId();
	    chocolateMap.put(newId, chocolate);
	    chocolate.setId(newId);
	    saveChocolates(fileName);
	}

	private String generateNewId() {
	    int maxId = 0;
	    for (String id : chocolateMap.keySet()) {
	        int currentId = Integer.parseInt(id);
	        if (currentId > maxId) {
	            maxId = currentId;
	        }
	    }
	    return String.valueOf(maxId + 1);
	}
	
	
	public Chocolate getById(String id) {
		return chocolateMap.get(id);
	}
	
	public Chocolate editChocolate(Chocolate chocolate) {
		Chocolate chocolateToEdit = chocolateMap.get(chocolate.getId());

	    if (chocolateToEdit != null) {
	        chocolateToEdit.setName(chocolate.getName());
	        chocolateToEdit.setPrice(chocolate.getPrice());
	        chocolateToEdit.setType(chocolate.getType());
	        chocolateToEdit.setFlavor(chocolate.getFlavor());
	        chocolateToEdit.setGrams(chocolate.getGrams());
	        chocolateToEdit.setDescription(chocolate.getDescription());
	        chocolateToEdit.setPicture(chocolate.getPicture());
	        chocolateToEdit.setStockQuantity(chocolate.getStockQuantity());
	        chocolateToEdit.setIsInStock(chocolate.getIsInStock());

	        // Ako želiš da sačuvaš promene u fajl, pozovi saveChocolates() ovde
	        saveChocolates(fileName);

	        return chocolateToEdit;
	    } else {
	        // Ako čokolada sa datim ID-jem ne postoji, možeš vratiti null ili baciti izuzetak
	        return null;
	    }
	}
	
	public Chocolate deleteChocolate(String id) {
		Chocolate chocolateToDelete = chocolateMap.get(id);
		if(chocolateToDelete == null) {
			return null;
		}		
		chocolateToDelete.setIsDeleted(true);
		saveChocolates(fileName);
		return chocolateToDelete;
		
	}
	
	public Collection<Chocolate> getAll(){
		return chocolateMap.values();
	}
	
	public void updateChocolatesQuantity(Collection<Chocolate> chocolates) {
		for(Chocolate chocolate: chocolates) {
			Chocolate chocolateToChange = chocolateMap.get(chocolate.getId());
			chocolateToChange.setStockQuantity(chocolateToChange.getStockQuantity()-1);
			if(chocolateToChange.getStockQuantity() == 0) {
				chocolateToChange.setIsInStock(false);
			}
		}
		saveChocolates(fileName);
		loadChocolates(fileName);
	}
	
	public List<Chocolate> getByIds(Set ids){
		return chocolateMap.values().stream().filter(choco -> ids.contains(choco.getId())).collect(Collectors.toList());
	}
	
	public void revertStockQuantities(PurchaseDTO purchaseDTO) {
		for(String id: purchaseDTO.getChocolateIds()) {
			Chocolate chocolateToUpdate = chocolateMap.get(id);
			chocolateToUpdate.setStockQuantity(chocolateToUpdate.getStockQuantity()+1);
			if(!chocolateToUpdate.getIsInStock()) {
				chocolateToUpdate.setIsInStock(true);
			}
		}
		saveChocolates(fileName);
	}
	

}
