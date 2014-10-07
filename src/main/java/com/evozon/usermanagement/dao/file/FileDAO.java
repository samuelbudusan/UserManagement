package com.evozon.usermanagement.dao.file;

import com.evozon.usermanagement.model.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository(value="fileDAO")
public class FileDAO  {

	private List<User> currentUsers;
	
	public FileDAO(){
		
	}

	public List<User> getAllUsers() {
		List<User> list = new ArrayList<User>();
		BufferedReader buffer = null;
		String line;

		try {
			buffer = new BufferedReader(new FileReader("file.csv"));
			while ((line = buffer.readLine()) != null) {
				if (!line.equals("")) {
					String[] parts = line.split(",");
					User user = new User(parts[1], parts[2], new SimpleDateFormat("dd/MM/yyyy").parse(parts[3]), parts[4], parts[5], parts[6], parts[7], Integer.valueOf(parts[8]));
					list.add(user);
				}
			}
		} catch (IOException e) {
			throw new FileDAOException("Wrong format file", e);
		} catch (ParseException e) {
			throw new ParseDateException("Wrong date file format", e);
		} finally {
			try {
				if (buffer != null)
					buffer.close();
			} catch (IOException ex) {
				throw new FileDAOException("File not found", ex);
			}
		}
		return list;
	}


	public void writeUsersToFile(List<User> usersList) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("file.csv"));
			for(User u : usersList){
				writer.write(u.toString());
			}
		} catch(IOException ex){
			throw new FileDAOException("File not found", ex);
		} finally {
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					throw new FileDAOException("Something wrong with the reader...");
				}
			}
		}
	}
	
	public void addUser(User user) {
		if(currentUsers == null) {
			currentUsers = getAllUsers();
		}

		if(currentUsers == null) {
			currentUsers = new ArrayList<User>();
		}

		currentUsers.add(user);
		writeUsersToFile(currentUsers);
	}
	
	public void updateUsers(List<User> usersList,int index) {
		writeUsersToFile(usersList);	
	}

	

}
