package dao;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import beans.*;
import dtos.PurchaseDTO;

public class ChocolateFactoryDAO {

	private HashMap<String, ChocolateFactory> chocolateFactoryMap = new HashMap<String, ChocolateFactory>();
	private ObjectMapper objectMapper = new ObjectMapper();     
	private String fileName;
	
	

	
	
	public ChocolateFactoryDAO(String contextPath) {
		this.fileName = contextPath + "/factories.json";
        loadChocolateFactories(fileName);
	}
	
	private void loadChocolateFactories(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                chocolateFactoryMap = objectMapper.readValue(file, new TypeReference<HashMap<String, ChocolateFactory>>() {});            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void saveChocolateFactory(String fileName) {
		try {
            File file = new File(fileName);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(file, chocolateFactoryMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public Collection<ChocolateFactory> getAll(){
		return chocolateFactoryMap.values();
	}
	
	public ChocolateFactory getFactoryDetails(String id){		
		return chocolateFactoryMap.get(id);
		
	}
	
	public void connectChocolateToFactory(Chocolate chocolate) {
		if(chocolateFactoryMap.get(chocolate.getFabricId()) == null) {
			return;
		}
		chocolateFactoryMap.get(chocolate.getFabricId()).getChocolates().add(chocolate);
	}

	public void updateScore(String id, List<Integer> scores) {
		ChocolateFactory factory = chocolateFactoryMap.get(id);
		if(factory == null) {
			return;
		}
		int sum = scores.stream()
                .mapToInt(Integer::intValue)
                .sum();
		factory.setRating((double)sum/(double)scores.size());
		saveChocolateFactory(fileName);
	}

	public ChocolateFactory createFactory(ChocolateFactory factory) {
		   String newId = generateNewId();
		    chocolateFactoryMap.put(newId, factory);
		    factory.setId(newId);
		    saveChocolateFactory(fileName);
		    return factory;
	}
	

	private String generateNewId() {
	    int maxId = 0;
	    for (String id : chocolateFactoryMap.keySet()) {
	        int currentId = Integer.parseInt(id);
	        if (currentId > maxId) {
	            maxId = currentId;
	        }
	    }
	    return String.valueOf(maxId + 1);
	}
	

}
