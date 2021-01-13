package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.repository.UserRepository;

@RestController
public class UserController {
	
	//@PostMapping("/createUsers")
	
	@Autowired
	UserRepository repository;
	
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
