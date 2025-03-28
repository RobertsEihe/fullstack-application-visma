package lv.visma.consulting.usercountriesapi.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lv.visma.consulting.usercountriesapi.controllers.dto.UserDto;
import lv.visma.consulting.usercountriesapi.converters.UserConverter;
import lv.visma.consulting.usercountriesapi.db.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        var entityUsers = userRepository.findAll();
        return UserConverter.toDTOList(entityUsers);
    }

    public UserDto getUserById(Long userId) {
        var entityUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found with id: {}", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
        log.info("User found with id: {}", userId);
        return UserConverter.toDto(entityUser);
    }
}
