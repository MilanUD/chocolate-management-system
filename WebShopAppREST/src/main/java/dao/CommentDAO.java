package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Chocolate;
import beans.Comments;

public class CommentDAO {
	
	private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private HashMap<String, Comments> commentMap = new HashMap<String, Comments>();
	private String fileName = "C:\\Users\\Milan\\Desktop\\Web Project\\WebShopAppREST\\src\\main\\webapp";
	
	public CommentDAO() {
		// TODO Auto-generated constructor stub
		loadComments(fileName);
	}
	
	private void loadComments(String fileName) {
        try {
            File file = new File(fileName + "/comments.json");
            if (file.exists()) {
            	commentMap = objectMapper.readValue(file, new TypeReference<HashMap<String, Comments>>() {});                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 private void saveComments(String fileName) {
	        try {
	            File file = new File(fileName + "/comments.json");
	            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
	            writer.writeValue(file, commentMap);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	

	public void addComment(Comments comment) {
		// TODO Auto-generated method stub
		 String newId = generateNewId();
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
		commentMap.put(newComment.getId(), newComment);
		saveComments(fileName);
		return newComment;
	}


}
