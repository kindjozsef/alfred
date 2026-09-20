package ro.msg4banking.gateway.vo;

import java.util.List;

public record ChatResponse(List<Choice> choices) {

  public record Choice(Message message) {}
}
