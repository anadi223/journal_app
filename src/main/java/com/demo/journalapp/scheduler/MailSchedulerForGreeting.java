package com.demo.journalapp.scheduler;

import com.demo.journalapp.service.EmailService;
import com.demo.journalapp.service.QuoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailSchedulerForGreeting {

    @Value("${email.to.send}")
    private String to;

    private final EmailService emailService;
    private final QuoteService quoteService;
    public MailSchedulerForGreeting(EmailService emailService, QuoteService quoteService) {
        this.emailService = emailService;
        this.quoteService = quoteService;
    }


    @Scheduled(cron = "0 0 6 * * ?") //Send Email every day at 6 AM
    public void sendMailToUser(){
        String quote = quoteService.getRandomQuote().getQuote();
        emailService.sendEmail(to,"Quote for the day",quote);
    }
}
