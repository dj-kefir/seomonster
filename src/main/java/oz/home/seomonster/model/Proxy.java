package oz.home.seomonster.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"host", "login", "password", "schema", "port"})
public class Proxy {

    String host;
    String login;
    String password;
    String schema = "http";
    int port;

    @JsonIgnore
    LocalDateTime lastUse;
}
