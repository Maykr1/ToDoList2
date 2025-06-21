package com.ToDoList2.ToDoList2;

import org.springframework.boot.SpringApplication;

public class TestToDoList2Application {

	public static void main(String[] args) {
		SpringApplication.from(ToDoList2Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
