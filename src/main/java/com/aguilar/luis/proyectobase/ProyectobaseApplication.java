package com.aguilar.luis.proyectobase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProyectobaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectobaseApplication.class, args);
	}

}
