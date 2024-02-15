package com.eduardondarocha.mindsimapp.service;

import com.eduardondarocha.mindsimapp.model.MensagemEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.file.Files;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmailWithFile(MensagemEmail mensagemEmail){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true, "utf-8");
            mimeMessageHelper.setFrom(mensagemEmail.getRemetente());//sender
            mimeMessageHelper.setSubject(mensagemEmail.getAssunto());
            mimeMessageHelper.setText(mensagemEmail.getTexto(), true);
            mimeMessageHelper.setTo(mensagemEmail.getDestinatarios()
                    .toArray(new String[mensagemEmail.getDestinatarios().size()]));
            FileSystemResource fileSystemResource = new FileSystemResource(mensagemEmail.getAnexo());
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void sendEmailWithTestMessage(MensagemEmail mensagemEmail){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom(mensagemEmail.getRemetente());//sender
            mimeMessageHelper.setSubject(mensagemEmail.getAssunto());
            mimeMessageHelper.setText(mensagemEmail.getTexto(), true);
            mimeMessageHelper.setTo(mensagemEmail.getDestinatarios()
                    .toArray(new String[mensagemEmail.getDestinatarios().size()]));
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
