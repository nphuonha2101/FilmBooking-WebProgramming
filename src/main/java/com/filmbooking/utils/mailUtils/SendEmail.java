package com.filmbooking.utils.mailUtils;

import com.filmbooking.configs.MailConfigs;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class SendEmail {
    private static SendEmail instance;

    private SendEmail() {
    }

    public static SendEmail getInstance() {
        if (instance == null) {
            instance = new SendEmail();
        }
        return instance;
    }


    private Session getSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", MailConfigs.EMAIL_HOST_NAME);
        properties.put("mail.smtp.socketFactory.port", MailConfigs.SSL_PORT);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port", MailConfigs.SSL_PORT);

        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailConfigs.APP_EMAIL, MailConfigs.APP_EMAIL_PASSWD);
            }
        });
    }

    public void sendEmailToUser(String userEmail, String subject, String content) {
        try {
            MimeMessage mimeMessage = new MimeMessage(getSession());
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(content, "text/html; charset=utf-8");

            Transport.send(mimeMessage);
            System.out.println("Sent mail successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String createResetPasswordEmail(String password) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@400;700&family=Merriweather:wght@700&display=swap\" rel=\"stylesheet\">\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Inter', sans-serif;\n" +
                "            color: #333;\n" +
                "            background-color: #f2f2f2;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        .card {\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);\n" +
                "            padding: 20px;\n" +
                "            width: 300px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            font-family: 'Merriweather', serif;\n" +
                "            color: #444;\n" +
                "        }\n" +
                "        p {\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .bold {\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            font-size: 1em;\n" +
                "            color: #555;\n" +
                "        }\n" +
                "        .icon {\n" +
                "            width: 50px;\n" +
                "            height: 50px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"card\">\n" +
                "        <img src=\"https://drive.google.com/file/d/1LmTGcED7isFpQ-0WJXVuVbP_6ANxJegL/\" alt=\"Film Booking\" class=\"icon\">\n" +
                "        <h1>Mật khẩu mới của bạn</h1>\n" +
                "        <p>Xin chào, có vẻ như là bạn đã quên mật khẩu cũ của mình nên chúng tôi đã tạo một mật khẩu mới cho bạn!</p>\n" +
                "        <p>Bạn hãy nhập mật khẩu sau để có thể đăng nhập vào tài khoản của mình: <span class=\"bold\">" + password + "</span></p>\n" +
                "        <p>Nếu không phải là bạn, hãy xem đổi mật khẩu của bạn ngay lập tức. Xin cảm ơn!</p>\n" +
                "        <p class=\"footer\">© 2023 FilmBooking - Doan Quoc Dang & Nguyen Phuong Nha - Mọi quyền được bảo lưu.</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public static void main(String[] args) {

    }
}
