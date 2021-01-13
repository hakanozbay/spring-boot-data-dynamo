package com.example.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import com.example.entity.User;

@EnableScan
public interface UserRepository extends DynamoDBCrudRepository<User, String> {

	List<User> findByLastName(String string);
 
}
