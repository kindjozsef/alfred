package ro.msg4banking.gateway.vo;

import java.util.List;

public record ChatRequest(String model, List<Message> messages) {}
