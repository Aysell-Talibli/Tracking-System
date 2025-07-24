package org.practice.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.practice.notificationservice.dto.DeliveryEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    public void sendDeliveryStatusEmail(String toEmail, String trackingNumber, String recipientEmail){
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Delivery created");
            String emailBody= String.format(
                            "Your delivery has been successfully created!\n\n" +
                            "Tracking Number: %s\n" +
                            "Recipient: %s\n" +
                            "Status: CREATED\n\n" +
                            "You can track your delivery using the tracking number above",
                    trackingNumber,
                    recipientEmail
            );
            message.setText(emailBody);
            mailSender.send(message);

        }
        catch (Exception e){
            System.out.println("failed to send message");
        }
    }
}
