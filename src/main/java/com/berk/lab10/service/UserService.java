/*
 Bu servis kullanıcılarla ilgili iş mantığını içerir.

 - Tüm kullanıcıları listeleme işlemini yapar
 - Entity → DTO dönüşümlerini burada gerçekleştirir

 Kısaca: Controller ile Repository arasındaki mantık katmanıdır.
*/
package com.berk.lab10.service;

import com.berk.lab10.dto.UserResponse;
import com.berk.lab10.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Admin tarafında tüm kullanıcıları listelemek için kullanılır
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole()))
                .toList();
    }
}
