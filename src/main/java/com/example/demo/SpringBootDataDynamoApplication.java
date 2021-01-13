package com.example.demo;


import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.exceptions.DynamoDBLocalServiceException;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.entity.User;
import com.example.repository.UserRepository;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableDynamoDBRepositories(basePackageClasses = UserRepository.class)
public class SpringBootDataDynamoApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringBootDataDynamoApplication.class);
	
	public static void main(String[] args) {
		
		System.setProperty("sqlite4java.library.path", "native-libs");
		
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootDataDynamoApplication.class, args);
		context.registerShutdownHook();
		
		try {
			DynamoDBProxyServer server = ServerRunner.createServerFromCommandLineArgs( new String[]{"-inMemory"});
			server.start();
			
			DynamoDBMapper mapper = context.getBean(DynamoDBMapper.class);
			AmazonDynamoDB amazonDynamoDB = context.getBean(AmazonDynamoDB.class);
			
			CreateTableRequest ctr = mapper.generateCreateTableRequest(User.class).withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
			
			boolean isTableCreated = TableUtils.createTableIfNotExists(amazonDynamoDB, ctr);
			
			if (isTableCreated)
				log.info("Created table {}", ctr.getTableName());
			
			TableUtils.waitUntilActive(amazonDynamoDB, ctr.getTableName());
			
			log.info("Table {} is active", ctr.getTableName());
			
		} catch (DynamoDBLocalServiceException e) {
			e.printStackTrace();
			context.stop();
			System.exit(-1);
		} catch (ParseException e) {
			e.printStackTrace();
			context.stop();
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
			context.stop();
			System.exit(-1);
		}
	}

}
