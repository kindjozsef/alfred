package ro.msg4banking.gateway.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ModelResponse(List<Model> data) {

  public record Model(
      String id, String object, Long created, @JsonProperty("owned_by") String owned_by) {}
}
