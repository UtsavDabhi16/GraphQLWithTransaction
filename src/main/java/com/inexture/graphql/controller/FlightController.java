package com.inexture.graphql.controller;

import com.inexture.graphql.dto.FlightBookingAcknowledgement;
import com.inexture.graphql.dto.FlightBookingRequest;
import com.inexture.graphql.entity.PassengerInfo;
import com.inexture.graphql.entity.PaymentInfo;
import com.inexture.graphql.service.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.util.List;

@RestController
public class FlightController {

    @Autowired
    private FlightBookingService service;

    @PostMapping("/bookFlightTicket")
    public FlightBookingAcknowledgement bookFlightTicket(@RequestBody FlightBookingRequest request) throws ParseException {
        return service.bookFlightTicket(request);
    }

    @QueryMapping("getAllFlightTickets")
    public List<FlightBookingRequest> getAllFlightTickets() { return service.getAllFlightTickets(); }

    @QueryMapping("getAllPassengerInfo")
    public List<PassengerInfo> getAllPassengerInfo() throws ParseException {
        return service.getAllPassengerInfo();
    }

    @QueryMapping("getAllPaymentInfo")
    public List<PaymentInfo> getAllPaymentInfo(){
        return service.getAllPaymentInfo();
    }

}
