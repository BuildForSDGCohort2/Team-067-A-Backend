package com.sdgcrm.application.email;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@Component
public class EmailJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;
    
    @Value("${spring.mail.emailsendername}")
    private String emailsendername;
    
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");
        String name = jobDataMap.getString("name");
        String recipientEmail = jobDataMap.getString("email");
        String emailtemplate = jobDataMap.getString("emailtemplate"); // email template
        
        
        List<String> receivers= Arrays.asList(recipientEmail);
			
			EmailTemplate template = new EmailTemplate(emailtemplate);
			Map<String, String> replacements = new HashMap<String, String>();
			
			
				replacements.put("body", body);
			

				replacements.put("name", name);
			
			
			
			replacements.put("maildate", String.valueOf(new Date()));
			
			String message = template.getTemplate(replacements);

			Email email2 = new Email();
			email2.setFrom(mailProperties.getUsername());
			email2.setMessage(message);
			email2.setSubject(subject);
			email2.setHtml(true);
			email2.setTo(receivers);
			

        send(email2);
    }


    
    public void send(Email eParams) {

		if (eParams.isHtml()) {
			try {
				sendHtmlMail(eParams);
			} catch (MessagingException | UnsupportedEncodingException e) {
				logger.error("Could not send email to : {} Error = {}", eParams.getToAsList(), e.getMessage());
			}
		} else {
			sendPlainTextMail(eParams);
		}

	}

	public void sendHtmlMail(Email eParams) throws MessagingException, UnsupportedEncodingException {

		boolean isHtml = true;

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
		
		helper.setReplyTo(eParams.getFrom());
		helper.setFrom(new InternetAddress(eParams.getFrom(), emailsendername));
		helper.setSubject(eParams.getSubject());
		helper.setText(eParams.getMessage(), isHtml);

		if (eParams.getCc().size() > 0) {
			helper.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
		}
		if(eParams.getBcc().size() > 0) {
			helper.setBcc(eParams.getBcc().toArray(new String[eParams.getBcc().size()]));
		}

		mailSender.send(message);
	}

	private void sendPlainTextMail(Email eParams) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		eParams.getTo().toArray(new String[eParams.getTo().size()]);
		mailMessage.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
		mailMessage.setReplyTo(eParams.getFrom());
		mailMessage.setFrom(eParams.getFrom());
		mailMessage.setSubject(eParams.getSubject());
		mailMessage.setText(eParams.getMessage());

		if (eParams.getCc().size() > 0) {
			mailMessage.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
		}
		if(eParams.getBcc().size() > 0) {
			mailMessage.setBcc(eParams.getBcc().toArray(new String[eParams.getBcc().size()]));
		}


		mailSender.send(mailMessage);

	}
}
