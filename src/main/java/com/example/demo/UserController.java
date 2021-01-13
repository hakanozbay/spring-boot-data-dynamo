package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@GetMapping("/createUser")
	public void createUser()
	{
		repository.save(new User ("a","b"));
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

}
