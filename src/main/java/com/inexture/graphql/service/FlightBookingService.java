package com.inexture.graphql.service;

import com.inexture.graphql.dto.FlightBookingAcknowledgement;
import com.inexture.graphql.dto.FlightBookingRequest;
import com.inexture.graphql.entity.PassengerInfo;
import com.inexture.graphql.entity.PaymentInfo;
import com.inexture.graphql.repository.PassengerInfoRepository;
import com.inexture.graphql.repository.PaymentInfoRepository;
import com.inexture.graphql.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FlightBookingService {

    @Autowired
    private PassengerInfoRepository passengerInfoRepository;
    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Transactional
    public FlightBookingAcknowledgement bookFlightTicket(FlightBookingRequest request) {

        PassengerInfo passengerInfo = request.getPassengerInfo();
        passengerInfo = passengerInfoRepository.save(passengerInfo);
        PaymentInfo paymentInfo = request.getPaymentInfo();
        PaymentUtils.validateCreditLimit(paymentInfo.getAccountNo(), passengerInfo.getFare());
        paymentInfo.setPassengerId(passengerInfo.getPId());
        paymentInfo.setAmount(passengerInfo.getFare());
        paymentInfoRepository.save(paymentInfo);
        return new FlightBookingAcknowledgement("SUCCESS", passengerInfo.getFare(), UUID.randomUUID().toString().split("-")[0], passengerInfo);
    }


    public List<FlightBookingRequest> getAllFlightTickets() {

        List<PassengerInfo> passengerInfoList = new ArrayList<>();
        passengerInfoRepository.findAll().forEach(passengerInfo -> passengerInfoList.add(passengerInfo));
        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        paymentInfoRepository.findAll().forEach(paymentInfo -> paymentInfoList.add(paymentInfo));
        List<FlightBookingRequest> flightBookingRequest =new ArrayList<>();
        for (int i = 0; i < passengerInfoList.size(); i++) {
            FlightBookingRequest flightBookingRequest1  = new FlightBookingRequest();
            flightBookingRequest1.setPassengerInfo(passengerInfoList.get(i));
            flightBookingRequest1.setPaymentInfo(paymentInfoList.get(i));
            flightBookingRequest.add(flightBookingRequest1);
        }
        return flightBookingRequest ;

    }

    public List<PassengerInfo> getAllPassengerInfo()  {
        return passengerInfoRepository.findAll();
    }

    public List<PaymentInfo> getAllPaymentInfo()
    {
        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        paymentInfoRepository.findAll().forEach(paymentInfo -> paymentInfoList.add(paymentInfo));
        System.out.println("Inside Payment Info Method" + paymentInfoList);
        return paymentInfoList;
    }

    
}
