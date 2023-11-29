package com.Intumit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Intumit.model.User;
import com.Intumit.repo.IUserRepo;

@Service
public class UserService {
	@Autowired
	private IUserRepo repo;

	public List<User> getAllUser() {
		ArrayList<User> userList = new ArrayList<>();
		repo.findAll().forEach(user -> userList.add(user));
		
		return userList;
	}
	
	public User getUserById(Long id) {
		return repo.findById(id).get();
	}
	
	public User getUserByName(String name) {
		return repo.findByName(name);
	}
	
	public boolean saveUser(User user) {
		User saveObj = repo.save(user);
		
		if (getUserById(saveObj.getUser_id()) != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasUser(String userName) {
		ArrayList<User> userList = new ArrayList<>();
		repo.findAll().forEach(user -> userList.add(user));
		boolean userExists = userList.stream().anyMatch(user -> user.getName().equals(userName));
		
        return userExists;
	}
	
	public boolean isPasswordCorrect(String name, String password) {
		User user = getUserByName(name);
		if(user.getPassword().equals(password)) {
			return true;
		}
		
        return false;
	}
}