package com.example.demo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.config.DynamoDBConfig;
import com.example.entity.User;
import com.example.repository.UserRepository;

@SpringBootTest(classes = {DynamoDBConfig.class})
@EnableDynamoDBRepositories(basePackageClasses = UserRepository.class)
class SpringBootDataDynamoApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(SpringBootDataDynamoApplicationTests.class);
	
	@Autowired
	private AmazonDynamoDB amazonDynamoDB;
	
	@Autowired
	private DynamoDBMapper mapper;
	
	@Autowired
	private UserRepository repository;

	DynamoDBProxyServer server;
	
	@BeforeEach
	public void init() throws Exception 
	{
		System.setProperty("sqlite4java.library.path", "native-libs");
		
		server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory"});
		server.start();
		
		CreateTableRequest ctr = mapper.generateCreateTableRequest(User.class).withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		
		boolean isTableCreated = TableUtils.createTableIfNotExists(amazonDynamoDB, ctr);
		
		if (isTableCreated)
			log.info("Created table {}", ctr.getTableName());
		
		TableUtils.waitUntilActive(amazonDynamoDB, ctr.getTableName());
		log.info("Table {} is active", ctr.getTableName());
	}

	@AfterEach
	public void destroy() throws Exception 
	{
		DeleteTableRequest dtr = mapper.generateDeleteTableRequest(User.class);
		TableUtils.deleteTableIfExists(amazonDynamoDB, dtr);
		log.info("Deleted table {}", dtr.getTableName());
	
		server.stop();
		
	}
		
	
	@Test
	public void sampleTestCase() {
		User gosling = new User("James", "Gosling",10);
		repository.save(gosling);

		User smith = new User("John", "Smith",10);
		repository.save(smith);

		User hoeller = new User("Juergen", "Hoeller",30);
		repository.save(hoeller);

		List<User> result;

		result = (List<User>) repository.findAll();
		assertThat(result.size(), equalTo(3));
		log.info("Found in table {}", result.toString());
		
		
		result = repository.findByLastName("Gosling");
		assertThat(result.size(), equalTo(1));
		log.info("Found in table: {}", result.get(0));
		
		result = repository.findByAgeGreaterThan(10);
		assertThat(result.size(), equalTo(1));
		log.info("Found in table: {}", result.get(0));
				
		result = repository.findByAgeGreaterThanAndLastNameContaining(10, "ell");
		assertThat(result.size(), equalTo(1));
		log.info("Found in table: {}", result.get(0));
		
		result = repository.findByAgeNotNull();
		assertThat(result.size(), equalTo(3));
		log.info("Found in table: {}", result.get(0));
		
		result = repository.findByAgeAndFirstNameStartsWith(10,"Jo");
		assertThat(result.size(), equalTo(1));
		log.info("Found in table: {}", result.get(0));
		
	}

	
}
