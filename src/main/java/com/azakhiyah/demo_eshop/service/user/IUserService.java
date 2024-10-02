package com.azakhiyah.demo_eshop.service.user;

import com.azakhiyah.demo_eshop.dto.UserDto;
import com.azakhiyah.demo_eshop.model.User;
import com.azakhiyah.demo_eshop.request.CreateUserRequest;
import com.azakhiyah.demo_eshop.request.UserUpdateRequest;


public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
