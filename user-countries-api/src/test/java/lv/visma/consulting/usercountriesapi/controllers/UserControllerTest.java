package lv.visma.consulting.usercountriesapi.controllers;

import lv.visma.consulting.usercountriesapi.controllers.dto.CountryDto;
import lv.visma.consulting.usercountriesapi.controllers.dto.UserDto;
import lv.visma.consulting.usercountriesapi.services.CountryService;
import lv.visma.consulting.usercountriesapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @MockitoBean
    CountryService countryService;

    private final Long id = 1L;
    private final String url = "/api/v1/user/1/favorite-countries";

    CountryDto testCountryDtoOne = new CountryDto("Republic of Latvia", "LV", List.of("Riga"));
    CountryDto testCountryDtoTwo = new CountryDto("Republic of Estonia", "EE", List.of("Tallinn"));
    List<CountryDto> favoriteCountries = List.of(testCountryDtoOne, testCountryDtoTwo);

    UserDto testUserDtoOne = new UserDto(id, "userDtoOne", favoriteCountries);

    @Test
    public void getFavouriteCountry_successful_withId() throws Exception {
        when(userService.getUserById(id))
                .thenReturn(testUserDtoOne);

        when(countryService.getCountryDetails(anyList()))
                .thenReturn(favoriteCountries);

        RequestBuilder request = MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Republic of Latvia"))
                .andExpect(jsonPath("$[0].code").value("LV"))
                .andExpect(jsonPath("$[0].capitals[0]").value("Riga"))
                .andExpect(jsonPath("$[1].name").value("Republic of Estonia"))
                .andExpect(jsonPath("$[1].code").value("EE"))
                .andExpect(jsonPath("$[1].capitals[0]").value("Tallinn"))
                .andReturn();

    }
}
