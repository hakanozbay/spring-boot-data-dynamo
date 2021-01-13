package com.example.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import com.example.entity.User;

@EnableScan
public interface UserRepository extends DynamoDBCrudRepository<User, String> {

	List<User> findByFirstName(String string);
	
	List<User> findByLastName(String string);
 
	List<User> findByAgeGreaterThan(int age);
	
	List<User> findByAgeLessThan(int age);
	
	List<User> findByAgeGreaterThanAndLastNameContaining(int age, String element);
	
	List<User> findByAgeNotNull();
	
	List<User> findByAgeAndFirstNameStartsWith(int age, String element);
	
}
