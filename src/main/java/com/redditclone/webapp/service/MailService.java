package com.redditclone.webapp.service;

import com.redditclone.webapp.exception.RedditWebAppException;
import com.redditclone.webapp.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    private final MailContentBuilder mailContentBuilder;

    @Async
    void sendMail(NotificationEmail notificationEmail) throws RedditWebAppException {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("shubhamrathore@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Activation mail sent!!");
        } catch (MailException ex) {
            log.error("Error occurred while sending mail : " + ex.getMessage());
            throw new RedditWebAppException("Exception occurred while sending mail to : " + notificationEmail.getRecipient());
        }
    }
}
