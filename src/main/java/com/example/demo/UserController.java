package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@RequestMapping("/createUser/{firstName}/{lastName}/{age}" )
	public void createUser(@PathVariable String firstName, @PathVariable String lastName, @PathVariable int age)
	{
		repository.save(new User (firstName,lastName, age));
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
	public void deleteAll() 
	{
		repository.deleteAll();
	}

}
