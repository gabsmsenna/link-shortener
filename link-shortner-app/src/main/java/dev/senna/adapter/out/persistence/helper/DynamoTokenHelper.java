package dev.senna.adapter.out.persistence.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Component
public class DynamoTokenHelper {

    private final ObjectMapper objectMapper;


    public DynamoTokenHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String encodeStartToken(Map<String, AttributeValue> key) {
        try {
            var dto = new TokenDto(key.get("link_id").s(), key.get("user_id").s());

            String json = objectMapper.writeValueAsString(dto);

            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Error encoding start token", e);
        }
    }

    public Map<String, AttributeValue> decodeStartToken(String token) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(token);

            String json = new String(decodedBytes, StandardCharsets.UTF_8);

            var dto = objectMapper.readValue(json, TokenDto.class);

            return Map.of(
                    "user_id", AttributeValue.fromS(dto.userId()),
                    "link_id", AttributeValue.fromS(dto.linkId())
            );
        } catch (Exception e) {
            throw new RuntimeException("Error decoding start token", e);
        }
    }
}
