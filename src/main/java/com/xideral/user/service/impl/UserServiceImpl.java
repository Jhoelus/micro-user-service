package com.xideral.user.service.impl;

import com.xideral.user.dto.UserDto;
import com.xideral.user.dto.UserRequestDto;
import com.xideral.user.exception.UserEmailException;
import com.xideral.user.exception.UserNoFoundException;
import com.xideral.user.model.UserEntity;
import com.xideral.user.repository.UserRepository;
import com.xideral.user.service.UserService;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public UserDto createUser(UserRequestDto user) {
        log.info("Creating user {} ", Json.pretty(user));

        userRepository.findByEmail(user.getEmail()).ifPresent( userBDD -> {
                    throw new UserEmailException("Email " + userBDD.getEmail() + " already exist.");
                }
        );
        var userResult = userRepository.save(mapper.map(user, UserEntity.class));

        return mapper.map(userResult, UserDto.class);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user by id:: {} ", id);
        return userRepository.findById(id)
                .map(userBDD ->
                        mapper.map(userBDD, UserDto.class))
                .orElseThrow(() -> new UserNoFoundException("User not found"));
    }

    @Override
    public UserDto updateUser(Long id, UserRequestDto user) {
        log.info("Updating user with id:: {} and data:: {} ", id, Json.pretty(user));

        if(Strings.isNotBlank(user.getEmail())) {
            userRepository.findByEmail(user.getEmail()).ifPresent( userBDD -> {
                        throw new UserEmailException("Email " + userBDD.getEmail() + " already exist.");
                    }
            );
        }

        return userRepository.findById(id)
                .map(userBDD -> {
                    userBDD.setName(Strings.isNotBlank(user.getName()) ?  user.getName() : userBDD.getName());
                    userBDD.setEmail(Strings.isNotBlank(user.getEmail()) ? user.getEmail() : userBDD.getEmail());
                    var userResult = userRepository.save(userBDD);

                    return mapper.map(userResult, UserDto.class);
                }).orElseThrow(() -> new UserNoFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user by id:: {} ", id);
        userRepository.findById(id).ifPresentOrElse(userBDD -> {
            userRepository.delete(userBDD);
        }, () -> {
            throw new UserNoFoundException("User not found");
        });
    }
}
