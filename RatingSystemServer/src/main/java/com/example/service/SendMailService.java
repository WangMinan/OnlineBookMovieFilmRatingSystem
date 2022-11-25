package com.example.service;

import com.example.pojo.MailContent;

public interface SendMailService {
    /**
     * 发送简单文本邮件
     * @param mailContent 邮件内容
     */
    Integer sendSimpleMail(MailContent mailContent);
}
