package com.showyourtrace.service;

import com.showyourtrace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("myAuthorize")
public class MyAuthorize implements Authorize {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public boolean authorize(String username, String password) {
        return userRepository.authorize(username, password, false);
    }

    @Transactional(readOnly = true)
    @Override
    public String ticket(String hash) {
        return userRepository.findByHash(hash);
    }
}
