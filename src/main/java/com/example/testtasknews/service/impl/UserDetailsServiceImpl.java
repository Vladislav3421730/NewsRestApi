package com.example.testtasknews.service.impl;

import com.example.testtasknews.repository.UserRepository;
import com.example.testtasknews.utils.wrapper.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).stream()
                .map(user -> {
                    log.info("User {} found", username);
                    return new CustomUserDetails(user);
                })
                .findFirst()
                .orElseThrow(() -> {
                    log.error("User with username {} wasn't found", username);
                    return new UsernameNotFoundException("Username " + username + " wasn't found");
                });
    }

}
