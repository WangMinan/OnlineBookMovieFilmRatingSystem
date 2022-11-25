package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailContent implements Serializable {
    /**
     * 接收人
     */
    private String sendTo;

    /**
     *  邮件内容
     */
    private String text;
}
