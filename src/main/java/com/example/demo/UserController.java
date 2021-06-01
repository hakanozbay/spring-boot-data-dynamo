package com.example.demo;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@RequestMapping("/createUser/{firstName}/{lastName}/{age}")
	public ResponseEntity<User> createUser(@PathVariable String firstName, @PathVariable String lastName, @PathVariable int age)
	{
		return ResponseEntity.ok(repository.save(new User (firstName,lastName, age)));
	}
	
	@GetMapping("/count")
	public long countUsers()
	{
		return repository.count();
	}
	
	@GetMapping("/all")
	public List<User> getUsers()
	{
		return (List<User>) repository.findAll();
	}
	
	@RequestMapping("/delete")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteAll() 
	{
		repository.deleteAll();
	}

}
