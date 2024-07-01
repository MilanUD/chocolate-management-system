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
	private String fileName = "C:\\Users\\Milan\\Desktop\\Web Project\\WebShopAppREST\\src\\main\\webapp";
	
	

	
	
	public ChocolateFactoryDAO() {
        loadChocolateFactories(fileName);
	}
	
	private void loadChocolateFactories(String fileName) {
        try {
            File file = new File(fileName + "/factories.json");
            if (file.exists()) {
                chocolateFactoryMap = objectMapper.readValue(file, new TypeReference<HashMap<String, ChocolateFactory>>() {});
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void saveChocolateFactory(String fileName) {
		try {
            File file = new File(fileName + "/factories.json");
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
		// TODO Auto-generated method stub
		ChocolateFactory factory = chocolateFactoryMap.get(id);
		if(factory == null) {
			return;
		}
		int sum = scores.stream()
                .mapToInt(Integer::intValue)
                .sum();
		factory.setRating(sum/scores.size());
		saveChocolateFactory(fileName);
	}
	
	

}
