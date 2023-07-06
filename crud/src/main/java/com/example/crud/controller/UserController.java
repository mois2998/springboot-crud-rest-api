package com.example.crud.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.entity.User;
import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepository repo;
	
	@GetMapping
	public List<User> getAllUsers(){
		return this.repo.findAll();
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value="id") long userId) {
		return this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found in database with id : "+userId));
	}
	
	@PostMapping
	public User addUser(@RequestBody User user){
		return repo.save(user);
	}
	
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user , @PathVariable ("id") long userId) {
    	User exsistinguser = this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found in database with id : "+userId));
    	exsistinguser.setFirstName(user.getFirstName());
    	exsistinguser.setLastName(user.getLastName());
    	exsistinguser.setEmail(user.getEmail());
    	return this.repo.save(exsistinguser);
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable ("id") long userId){
    	User exsistinguser = this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found in database with id : "+userId));
    	this.repo.delete(exsistinguser);
    	return  ResponseEntity.ok().build();
    }
}







