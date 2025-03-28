package lv.visma.consulting.usercountriesapi.web.client;

import lombok.extern.log4j.Log4j2;
import lv.visma.consulting.usercountriesapi.web.client.dto.WebClientCountryDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Log4j2
@Component
public class CountryWebClient extends BaseWebclient{

    public CountryWebClient(WebClient webClient) {
        super(webClient);
    }

    public WebClientCountryDto fetchCountryByCode(String code) {
        String uri = "alpha/" + code;
        ResponseEntity<List<WebClientCountryDto>> response = getList(
                uri,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getBody() == null || response.getBody().isEmpty()) {
            log.warn("Country not found for code: {}", code);
            throw new RuntimeException("Country not found for code: " + code);
        }

        log.info("Country found for code: {}", code);
        return response.getBody().getFirst();
    }
}
