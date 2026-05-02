package com.mockeval.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class AIService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    private final WebClient client = WebClient.builder()
            .baseUrl("https://openrouter.ai/api/v1")
            .build();

    public Map<String, Object> improve(String input) {

        String prompt = """
You are a practical technical interviewer.

Analyze the feedback and return ONLY valid JSON.

RULES:
- Use SIMPLE and CLEAR English
- Keep sentences SHORT
- Make it easy to understand
- Be practical, not theoretical
- No complicated words

Format:

{
 "improvedText": "...",
 "score": 0-10,
 "strengths": ["..."],
 "weaknesses": ["..."],
 "plan": ["..."]
}

Keep improvedText max 2-3 lines.

Feedback:
""" + input;

        Map<String, Object> body = new HashMap<>();
        body.put("model", "meta-llama/llama-3-8b-instruct"); // ✅ confirmed working free model

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of(
                "role", "user",
                "content", prompt
        ));

        body.put("messages", messages);

        Map response = WebClient.create()
                .post()
                .uri("https://openrouter.ai/api/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("HTTP-Referer", "http://localhost:3000")
                .header("X-Title", "MockEval")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String content = (String) ((Map) ((Map) ((List) response.get("choices")).get(0))
                .get("message")).get("content");

        ObjectMapper mapper = new ObjectMapper();

        try {
            // 🔥 Extract only JSON part
            int start = content.indexOf("{");
            int end = content.lastIndexOf("}");

            if (start != -1 && end != -1) {
                String json = content.substring(start, end + 1);
                return mapper.readValue(json, Map.class);
            } else {
                throw new RuntimeException("Invalid JSON format");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of(
                    "improvedText", content,
                    "score", "",
                    "strengths", List.of(),
                    "weaknesses", List.of(),
                    "plan", List.of()
            );
        }
    }
}