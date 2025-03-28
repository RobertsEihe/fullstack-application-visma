package lv.visma.consulting.usercountriesapi.controllers;

import lombok.RequiredArgsConstructor;
import lv.visma.consulting.usercountriesapi.controllers.dto.CountryDto;
import lv.visma.consulting.usercountriesapi.controllers.dto.UserDto;
import lv.visma.consulting.usercountriesapi.services.CountryService;
import lv.visma.consulting.usercountriesapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final CountryService countryService;


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() { return ResponseEntity.ok(userService.getAllUsers()); }

    @GetMapping("/{userId}/favorite-countries")
    public ResponseEntity<List<CountryDto>> getFavoriteCountries(@PathVariable Long userId) {
        UserDto user = userService.getUserById(userId);
        List<String> countryCodes = user.getFavoriteCountries().stream()
                .map(CountryDto::getCode)
                .toList();

        List<CountryDto> countries = countryService.getCountryDetails(countryCodes);
        return ResponseEntity.ok(countries);
    }
}
