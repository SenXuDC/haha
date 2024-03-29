package com.xusenme.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 发送qq邮件的工具类
 * Created by xusen on 2017/11/26.
 */
public class Email {

    //发送的邮箱 内部代码只适用qq邮箱
//    @Value("${email.serverEmailUser}")
    private String user;
    //授权密码 通过QQ邮箱设置->账户->POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务->开启POP3/SMTP服务获取
//    @Value("${email.serverEmailPassword}")
    private String pwd;

    private String[] to;
    private String[] cc;//抄送
    private String[] bcc;//密送
    private String[] fileList;//附件
    private String subject;//主题
    private String content;//内容，可以用html语言写


    public Email(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    public void sendMessageTo(String email, String subject, String content) {
        this.to = new String[]{email};
        this.subject = subject;
        this.content = content;
        try {
            sendMessage();
        } catch (Exception e) {
            System.out.println("发送邮件失败");
            e.printStackTrace();
        }
    }

    public void sendMessageToMe(String subject, String content) {
//        this.to=new String[]{"1587719742@qq.com"};
        this.to = new String[]{"956532577@qq.com"};
        this.subject = subject;
        this.content = content;
        try {
            sendMessage();
        } catch (Exception e) {
            System.out.println("发送邮件失败");
            e.printStackTrace();
        }
    }


    public void sendMessage() throws MessagingException, UnsupportedEncodingException {
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        //下面两段代码是设置ssl和端口，不设置发送不出去。
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        // 表示SMTP发送邮件，需要进行身份验证
        props.setProperty("mail.transport.protocol", "smtp");// 设置传输协议
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.qq.com");//QQ邮箱的服务器 如果是企业邮箱或者其他邮箱得更换该服务器地址
        // 发件人的账号
        props.put("mail.user", this.user);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", this.pwd);
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //用户名，密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        //发送
        if (to != null) {
            String toList = getMailList(to);
            InternetAddress[] iaToList = new InternetAddress().parse(toList);
            message.setRecipients(RecipientType.TO, iaToList); // 收件人
        }
        //抄送
        if (cc != null) {
            String toListcc = getMailList(cc);
            InternetAddress[] iaToListcc = new InternetAddress().parse(toListcc);
            message.setRecipients(RecipientType.CC, iaToListcc); // 抄送人
        }
        //密送
        if (bcc != null) {
            String toListbcc = getMailList(bcc);
            InternetAddress[] iaToListbcc = new InternetAddress().parse(toListbcc);
            message.setRecipients(RecipientType.BCC, iaToListbcc); // 密送人
        }
        message.setSentDate(new Date()); // 发送日期 该日期可以随意写,你可以写上昨天的日期（效果很特别,亲测,有兴趣可以试试）,或者抽象出来形成一个参数。
        message.setSubject(subject); // 主题
        message.setText(content); // 内容
        //显示以html格式的文本内容
        messageBodyPart.setContent(content, "text/html;charset=utf-8");
        multipart.addBodyPart(messageBodyPart);
        //保存多个附件
        if (fileList != null) {
            addTach(fileList, multipart);
        }
        message.setContent(multipart);
        // 发送邮件
        Transport.send(message);
    }

    private String getMailList(String[] mailArray) {
        StringBuffer toList = new StringBuffer();
        int length = mailArray.length;
        if (mailArray != null && length < 2) {
            toList.append(mailArray[0]);
        } else {
            for (int i = 0; i < length; i++) {
                toList.append(mailArray[i]);
                if (i != (length - 1)) {
                    toList.append(",");
                }
            }
        }
        return toList.toString();
    }

    //添加多个附件
    public void addTach(String fileList[], Multipart multipart) throws MessagingException, UnsupportedEncodingException {
        for (int index = 0; index < fileList.length; index++) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(fileList[index]);
            mailArchieve.setDataHandler(new DataHandler(fds));
            mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
            multipart.addBodyPart(mailArchieve);
        }
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFileList(String[] fileList) {
        this.fileList = fileList;
    }


}

