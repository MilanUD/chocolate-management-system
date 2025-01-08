package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import beans.Chocolate;
import beans.ChocolateFactory;
import beans.User;

public class UserDAO {
	
	private HashMap<String, User> userMap = new HashMap<String, User>();
	 private ObjectMapper objectMapper = new ObjectMapper()
	            .registerModule(new JavaTimeModule())
	            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);	
	 private String fileName;
	

	public UserDAO(String contextPath) {
		this.fileName = contextPath + "/users.json";
		loadUsers(fileName);
	}
	
	private void loadUsers(String fileName) {
		try {
            File file = new File(fileName);
            if (file.exists()) {
            	userMap = objectMapper.readValue(file, new TypeReference<HashMap<String, User>>() {});
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void addUser(User user) {
		if(userMap.containsValue(user.getUsername())) {
			return;
		}
	    String newId = generateNewId();
	    userMap.put(newId, user);
	    user.setId(newId);
	    saveUser(fileName);
	}


	private String generateNewId() {
	    int maxId = 0;
	    for (String id : userMap.keySet()) {
	        int currentId = Integer.parseInt(id);
	        if (currentId > maxId) {
	            maxId = currentId;
	        }
	    }
	    return String.valueOf(maxId + 1);
	}
	
	private void saveUser(String fileName) {
		try {
            File file = new File(fileName);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(file, userMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public Collection<User> getAll(){
		return userMap.values();
	}

	public User logIn(User user) {
			Optional<User> userToLogIn = userMap.values().stream().filter(u -> u.getUsername().equals(user.getUsername()) && !u.getIsBlocked() && u.getPassword().equals(user.getPassword())).findAny();
			if(userToLogIn.isPresent()) {
				return userToLogIn.get();
			}
		
		return null;
	}

	public User editUserInfo(User user) {
		User selectedUser = userMap.get(user.getId());
		if(selectedUser == null) {
			return null;
		}
		userMap.put(selectedUser.getId(), user);
		saveUser(fileName);
		return user;
	}
	
	public User getById(String id) {
		return userMap.get(id);
	}

	public Collection<User> getAllFreeManagers() {
		return userMap.values().stream().filter(u -> u.getUserType().equals("Manager") && u.getFactoryId().equals("-1")).collect(Collectors.toList());
	}

	public void assignToFactory(User manager) {
		User managerToAssign = userMap.get(manager.getId());
		if(managerToAssign == null) {
			return;
		}
		managerToAssign.setFactoryId(manager.getFactoryId());
		userMap.put(managerToAssign.getId(), managerToAssign);
		saveUser(fileName);
	}

	public void setToSuspicious(String userId) {
		User suspiciousUser = userMap.get(userId);
		if(suspiciousUser == null) {
			return;
		}
		suspiciousUser.setIsSuspicious(true);
		saveUser(fileName);
	}

	public void block(String userId) {
		User userToBlock = userMap.get(userId);
		if(userToBlock == null) {
			return;
		}
		userToBlock.setIsBlocked(true);
		saveUser(fileName);
	}
	
	

}
