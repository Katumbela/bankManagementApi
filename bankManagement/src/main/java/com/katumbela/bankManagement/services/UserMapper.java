package com.katumbela.bankManagement.services;

import org.springframework.stereotype.Component;

import com.katumbela.bankManagement.dtos.UserDTO;
import com.katumbela.bankManagement.models.User;

@Component
public class UserMapper {

    /**
     * Convert UserDTO to User entity
     */
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setDocumentId(userDTO.getDocumentId());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    /**
     * Convert User entity to UserDTO
     */
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFullName(user.getFullName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setDocumentId(user.getDocumentId());
        // We don't set the password when converting to DTO for security reasons
        return userDTO;
    }
}
