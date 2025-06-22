package com.ToDoList2.ToDoList2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

// @SpringBootTest
// @Testcontainers
// class ToDoList2ApplicationTests {
// 	@SuppressWarnings("resource")
// 	@Container
// 	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.5")
// 		.withDatabaseName("testdb")
// 		.withUsername("postgres")
// 		.withPassword("1234");

// 	@DynamicPropertySource
// 	static void overrideProps(DynamicPropertyRegistry registry) {
// 		registry.add("spring.datasource.url", postgres::getJdbcUrl);
// 		registry.add("spring.datasource.username", postgres::getUsername);
// 		registry.add("spring.datasource.password", postgres::getPassword);
// 	}
	

// 	@Test
// 	void contextLoads() {
// 	}

// }
