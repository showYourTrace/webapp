package com.languagelearn.service;

import com.languagelearn.model.User;
import com.languagelearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null) {
            Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
            dbAuthsSet.addAll(loadUserAuthorities(user.getLogin()));
            if (dbAuthsSet.size() == 0) {
                throw new UsernameNotFoundException("SecurityUser "+username+" has no GrantedAuthority");
            }
            List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
            return createUserDetails(username, user, dbAuths);
        }
        throw new UsernameNotFoundException("Username "+username+" not found");
    }

    protected UserDetails createUserDetails(String username, User userFromUserQuery,
                                            List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getLogin();

        return new org.springframework.security.core.userdetails.User(username, username, /*userFromUserQuery.isEnabled()*/true, true, true, true, combinedAuthorities);
    }

    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(2);
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(userRepository.isAdmin(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }

}
