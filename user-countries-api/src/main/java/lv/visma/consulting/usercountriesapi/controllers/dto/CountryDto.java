package lv.visma.consulting.usercountriesapi.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
    private String name;
    private String code;
    private List<String> capitals;
}
