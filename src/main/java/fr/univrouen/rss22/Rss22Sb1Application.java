package fr.univrouen.rss22;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Rss22Sb1Application {

	public static void main(String[] args) {
		System.getProperties().put("server.port", 8100);
		SpringApplication.run(Rss22Sb1Application.class, args);
	}

}
