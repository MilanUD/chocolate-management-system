package dao;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import beans.*;

public class ChocolateFactoryDAO {

	private HashMap<String, ChocolateFactory> chocolateFactoryMap = new HashMap<String, ChocolateFactory>();
	private ObjectMapper objectMapper = new ObjectMapper();     
	
	
	public ChocolateFactoryDAO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ChocolateFactoryDAO(String fileName) {
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
	
	public Collection<ChocolateFactory> getAll(){
		return chocolateFactoryMap.values();
	}
	
	public ChocolateFactory getFactoryDetails(String id){		
		return chocolateFactoryMap.get(id);
		
	}
	

}
