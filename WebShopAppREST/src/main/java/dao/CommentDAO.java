package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Chocolate;
import beans.Comments;

public class CommentDAO {
	
	private ObjectMapper objectMapper = new ObjectMapper()
	            .registerModule(new JavaTimeModule())
	            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);	
	private HashMap<String, Comments> commentMap = new HashMap<String, Comments>();
	private String fileName;
	
	public CommentDAO(String contextPath) {
		// TODO Auto-generated constructor stub
		this.fileName = contextPath + "/comments.json";
		loadComments(fileName);
	}
	
	private void loadComments(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
            	commentMap = objectMapper.readValue(file, new TypeReference<HashMap<String, Comments>>() {});                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 private void saveComments(String fileName) {
	        try {
	            File file = new File(fileName);
	            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
	            writer.writeValue(file, commentMap);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	

	public void addComment(Comments comment) {
		// TODO Auto-generated method stub
		 String newId = generateNewId();
		 comment.setUser(null);
		    commentMap.put(newId, comment);
		    comment.setId(newId);
		    saveComments(fileName);	
	}
	
	private String generateNewId() {
	    int maxId = 0;
	    for (String id : commentMap.keySet()) {
	        int currentId = Integer.parseInt(id);
	        if (currentId > maxId) {
	            maxId = currentId;
	        }
	    }
	    return String.valueOf(maxId + 1);
	}
	
	public List<Integer> getScoresByFactoryId(String factoryId){
		return commentMap.values().stream().filter(c -> c.getFactoryId().equals(factoryId)).map(Comments::getRating).collect(Collectors.toList());
	}

	public Collection<Comments> getByFactoryId(String factoryId) {
		// TODO Auto-generated method stub
		return commentMap.values().stream().filter(c -> c.getFactoryId().equals(factoryId)).collect(Collectors.toList());
	}

	public Comments updateStatus(Comments newComment) {
		// TODO Auto-generated method stub
		Comments oldComment = commentMap.get(newComment.getId());
		if(oldComment == null) {
			return null;
		}
		newComment.setUser(null);
		commentMap.put(newComment.getId(), newComment);
		saveComments(fileName);
		return newComment;
	}

	public boolean hasCommented(String userId, String factoryId) {
		// TODO Auto-generated method stub
		return commentMap.values().stream().anyMatch(c -> c.getUserId().equals(userId) && c.getFactoryId().equals(factoryId));
	}


}
