package com.paltecno.fintech.invoicing.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SmsUtils {
    public static final String FROM_NUMBER = "+1234567890"; // Replace with your Twilio phone number
    public static final String SID_KEY = "TWILIO_SID"; // Replace with your Twilio SID
    public static final String TOKEN_KEY = "TWILIO_TOKEN"; // Replace with your Twilio Auth Token


    public static void sendSMS(String to, String messageBody) {
        Twilio.init(SID_KEY, TOKEN_KEY);
        Message message = Message.creator(
            new PhoneNumber("+" + to),
            new PhoneNumber(FROM_NUMBER),
            messageBody
        ).create();
        System.out.println(message);
    }
}
