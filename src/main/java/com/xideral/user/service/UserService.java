package com.xideral.user.service;

import com.xideral.user.dto.UserDto;
import com.xideral.user.dto.UserRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    UserDto createUser(UserRequestDto user);

    UserDto  getUserById(Long id);

    UserDto updateUser(Long id, UserRequestDto user);

    void deleteUser(Long id);
}
