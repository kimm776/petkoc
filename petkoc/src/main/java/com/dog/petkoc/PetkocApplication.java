package com.dog.petkoc;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dog.petkoc.*"})
@EnableEncryptableProperties
public class PetkocApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetkocApplication.class, args);
	}

}
