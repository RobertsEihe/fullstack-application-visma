package lv.visma.consulting.usercountriesapi.services;

import lombok.AllArgsConstructor;
import lv.visma.consulting.usercountriesapi.controllers.dto.CountryDto;
import lv.visma.consulting.usercountriesapi.converters.CountryConverter;
import lv.visma.consulting.usercountriesapi.web.client.CountryWebClient;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CountryService {

    private final CountryWebClient countryWebClient;

    public List<CountryDto> getCountryDetails(List<String> countryCodes) {

        return countryCodes.stream()
                .map(countryWebClient::fetchCountryByCode)
                .map(CountryConverter::toDto)
                .toList();
    }
}
