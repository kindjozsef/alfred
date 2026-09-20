package ro.msg4banking.gateway.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatRequest(String model, List<Message> messages, List<Map<String, Object>> tools) {}
