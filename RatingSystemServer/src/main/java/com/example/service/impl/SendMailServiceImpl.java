package com.example.service.impl;

import com.example.pojo.MailContent;
import com.example.service.SendMailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SendMailServiceImpl implements SendMailService {
    //注入邮件工具类
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    private static final int SUCCESS = 200;
    private static final int ERROR = 500;

    @Override
    public Integer sendSimpleMail(MailContent mailContent) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            //邮件发件人
            message.setFrom(sendMailer);
            //邮件收件人 1或多个
            message.setTo(mailContent.getSendTo().split(","));
            // 邮件主题
            message.setSubject("您有一条书影音评价平台的留言被删除了");
            //邮件内容
            message.setText(mailContent.getText());
            //邮件发送时间
            message.setSentDate(new Date());

            javaMailSender.send(message);
            return SUCCESS;
        } catch (Exception e){
            return ERROR;
        }
    }
}

