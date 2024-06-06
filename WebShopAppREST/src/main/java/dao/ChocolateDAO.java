package dao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Chocolate;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
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
    private String fileName;
	public ChocolateDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public ChocolateDAO(String contextPath) {
		fileName = contextPath;
		loadChocolates(contextPath);
	}	
	
	private void loadChocolates(String fileName) {
        try {
            File file = new File(fileName + "/chocolates.json");
            if (file.exists()) {
                chocolateMap = objectMapper.readValue(file, new TypeReference<HashMap<String, Chocolate>>() {});
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void saveChocolates(String fileName) {
	    try {
	        File file = new File(fileName + "/chocolates.json");
	        if (!file.exists()) {
                file.createNewFile(); // Kreiranje fajla ako ne postoji
                System.out.println("Dati fajl ne postoji");
            }
	        System.out.println("Chocolate map before saving: " + chocolateMap);
	        objectMapper.writeValue(file, chocolateMap);
	        System.out.println("Data successfully written to file.");
	        
	        // Provera sadržaja fajla nakon upisa
	        String jsonContent = new String(Files.readAllBytes(file.toPath()));
	        System.out.println("File content after writing: " + jsonContent);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public Collection<Chocolate> getByFabricId(String fabricId){
		return chocolateMap.values().stream().filter(c -> c.getFabricId().equals(fabricId)).collect(Collectors.toList());
	}
	
	public void addChocolate(Chocolate chocolate) {
	    // Generiši novi ID
	    String newId = generateNewId();
	    
	    // Kreiraj novi objekat Chocolate
	    //Chocolate newChocolate = new Chocolate(newId, name, type, weight);
	    
	    // Dodaj novi objekat u mapu
	    chocolateMap.put(newId, chocolate);
	    chocolate.setId(newId);
	    
	    // Sačuvaj mapu u JSON fajl
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
	
	

}
