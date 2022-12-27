package com.inexture.graphql;

import com.inexture.graphql.dto.FlightBookingAcknowledgement;
import com.inexture.graphql.dto.FlightBookingRequest;
import com.inexture.graphql.service.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@EnableTransactionManagement
public class FlightServiceExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightServiceExampleApplication.class, args);
	}

}
