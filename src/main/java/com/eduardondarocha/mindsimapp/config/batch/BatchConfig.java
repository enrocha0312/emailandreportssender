package com.eduardondarocha.mindsimapp.config.batch;

import com.eduardondarocha.mindsimapp.model.MensagemEmail;
import com.eduardondarocha.mindsimapp.pdfutils.PdfHtmlConverter;
import com.eduardondarocha.mindsimapp.service.EmailService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableBatchProcessing
@Configuration
public class BatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Bean
    public Job imprimeOlaJob(){
        return jobBuilderFactory
                .get("imprimeOlaOjb")
                .start(enviaEmailStep())
                .build();
    }
    public Step enviaEmailStep(){
        return stepBuilderFactory
                .get("enviaEmailStep").tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        String anexo = PdfHtmlConverter.converterHTMLparaPDF("arquivoteste");
                        System.out.println("Enviando e-mail");
                        MensagemEmail mensagemEmail = MensagemEmail.builder()
                                .texto("Esse e-mail foi enviado após converter um HTML para PDF")
                                .destinatarios(Stream.of("fmurad@mindsim.com.br",
                                        "enrocha0312@gmail.com").collect(Collectors.toList()))
                                .assunto("Teste Spring Batch de envio de arquivo")
                                .remetente("enrtecnologiaeconhecimento@gmail.com")
                                .anexo(anexo)
                                .build();
                        try {
                            emailService.sendEmailWithFile(mensagemEmail);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}

