package com.tkachuk.jobnetwork.service;

import com.tkachuk.common.dto.UserDto;
import com.tkachuk.jobnetwork.model.User;
import com.tkachuk.jobnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of {@link UserDetailsService} interface.
 * Wrapper for {@link UserRepository} + business logic.
 *
 * @author Svitlana Tkachuk
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username) // выбрасываем исключение
                );

        return UserPrinciple.build(user);
    }

    public User addData(UserDto userRequest) {
        User user = authService.checkAuth(userRequest);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        String cvName = UUID.randomUUID().toString();
        user.setCvUrl("test" + "/" + cvName);
        return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
    public Optional<User> check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return Optional.of(userRepository.findByUsername(currentPrincipalName).get());
    }

    public User updateData(UserDto userRequest) {
        Optional<User> user = check();
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setFirstName(userRequest.getFirstName());
            user1.setLastName(userRequest.getLastName());
            return userRepository.save(user1);
        }
        return null;
    }

    private User findUserByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByUsername(currentPrincipalName).get();
    }

    public void deleteUser() {
        User user = findUserByUsername();
        userRepository.delete(user);
    }
}
