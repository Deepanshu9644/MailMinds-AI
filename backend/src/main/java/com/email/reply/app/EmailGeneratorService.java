package com.email.reply.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailGeneratorService {
    private final WebClient webClient;
    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public EmailGeneratorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String generateEmailReply(EmailRequest emailRequest){

        //Build prompt
        String prompt=buildPrompt(emailRequest);
        //Craft a Request
        Map<String, Object> requestBody= Map.of("contents",new Object[]{
                Map.of("parts",new Object[]{
                        Map.of("text",prompt)
                        })
        }
        );
        //Do request and Get Response
        String response =webClient.post()
                .uri(geminiApiUrl+geminiApiKey)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //Extract response and Return Response
        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        try{
            ObjectMapper mapper=new ObjectMapper();
            JsonNode rootnode= mapper.readTree(response);
            return rootnode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        }catch(Exception e){
            return "Error  processing request: "+ e.getMessage();
        }
    }
    private String buildPrompt(EmailRequest emailRequest) {
        String toneInstruction = "";
        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            toneInstruction = "Use a " + emailRequest.getTone() + " tone.";
        }

        return """
        You are an AI email assistant.

        Write a single, professional email reply to the message below.
        Do NOT include subject lines, explanations, or multiple versions.
        Only respond with the reply email content in a natural tone.

        %s

        Original email:
        "%s"

        Reply:
        """.formatted(toneInstruction, emailRequest.getEmailContent());
    }

//    private String buildPrompt(EmailRequest emailRequest) {
//        StringBuilder prompt=new StringBuilder();
//        if(emailRequest.getTone()!=null && !emailRequest.getTone().isEmpty()){
//            prompt.append("Use a ").append(emailRequest.getTone()).append("tone");
//        }
//        prompt.append("\n Original email: \n").append(emailRequest.getEmailContent());
//        return prompt.toString();
//    }

}
