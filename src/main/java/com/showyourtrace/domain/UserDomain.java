package com.showyourtrace.domain;

import com.showyourtrace.exception.ApplicationRuntimeException;
import com.showyourtrace.model.User;
import com.showyourtrace.object.encode.UserEncode;
import com.showyourtrace.object.encode.UserSearchEncode;
import com.showyourtrace.object.request.RegisterUserRequest;
import com.showyourtrace.object.request.UserCreateRequest;
import com.showyourtrace.object.request.UserSearchRequest;
import com.showyourtrace.object.request.UserUpdateRequest;
import com.showyourtrace.object.response.ConfirmResultResponse;
import com.showyourtrace.object.response.UserResponse;
import com.showyourtrace.object.response.UserSearchResponse;
import com.showyourtrace.repository.UserRepository;
import com.showyourtrace.util.TableLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Service
public class UserDomain {

    private static final Logger log = LoggerFactory.getLogger(UserDomain.class);

    @Autowired
    private TableLogger tableLogger;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEncode userEncode;

    @Autowired
    private UserSearchEncode userSearchEncode;

    @Transactional(readOnly = true)
    public UserResponse get(Long id) {
        return userEncode.encode(userRepository.findById(id));
    }

    @Transactional(readOnly = true)
    public Page<UserSearchResponse> search(UserSearchRequest request) {
        Page<User> queryResult = userRepository.search(request);
        return new PageImpl<>(new ArrayList<>(userSearchEncode.encode(queryResult.getContent())), request, queryResult.getTotalElements());
    }

    public static void main(String args[]) {
    	System.out.println(BCrypt.hashpw("root", BCrypt.gensalt(10)));
    }
    
    @Transactional(readOnly = false)
    public UserResponse create(UserCreateRequest request) {
        User user = new User();
        user.setLogin(request.getLogin());

        String hashed = BCrypt.hashpw(request.getPwd(), BCrypt.gensalt(10));

        user.setPwd(hashed);
        user.setName(request.getName());
        user.setSecondName(request.getSecondName());
        user.setEmail(request.getEmail());
        user.setReceivePromo(request.isReceivePromo() == null ? false : request.isReceivePromo());
        user.setRegisteredUser(true);
        tableLogger.setCreated(user);
        userRepository.save(user);
        return userEncode.encode(user);
    }

    @Transactional(readOnly = false)
    public UserResponse update(UserUpdateRequest request) {
        User user = userRepository.findById(request.getId());
        user.setLogin(request.getLogin());

        String hashed = BCrypt.hashpw(request.getPwd(), BCrypt.gensalt(10));

        user.setPwd(hashed);
        user.setName(request.getName());
        user.setSecondName(request.getSecondName());
        user.setEmail(request.getEmail());
        user.setReceivePromo(request.isReceivePromo());
        tableLogger.setChanged(user);
        userRepository.save(user);
        return userEncode.encode(user);
    }

    @Transactional(readOnly = false)
    public UserResponse updateConfirm(UserUpdateRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        user.setReceivePromo(request.isReceivePromo());
        user.setConfirmId(request.getConfirmId());
        user.setConfirmIdCreated(new Date());

        tableLogger.setRegisterChanged(user);
        userRepository.save(user);
        return userEncode.encode(user);
    }

    @Transactional(readOnly = false)
    public boolean delete(Long userId) {
        userRepository.delete(userId);
        return true;
    }

    @Transactional(readOnly = false)
    public UserResponse registerUser(RegisterUserRequest request) {
        User user = userRepository.findByUserName(request.getUsername());

        if(user != null) {
            throw new ApplicationRuntimeException("User exists");
        }
        user = userRepository.findByEmail(request.getEmail());
        if(user != null) {
            throw new ApplicationRuntimeException("Email exists");
        }

        user = new User();
        user.setLogin(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRegisteredUser(true);
        user.setReceivePromo(false);

        String hashed = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(10));

        user.setPwd(hashed);
        tableLogger.setRegistered(user);
        userRepository.save(user);
        return userEncode.encode(user);
    }

    @Transactional(readOnly = false)
    public UserResponse registerSubscriber(UserCreateRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if(user == null) {
            user = new User();
            user.setLogin(request.getEmail());
            user.setPwd(request.getEmail());
            user.setEmail(request.getEmail());
        }

        user.setReceivePromo(request.isReceivePromo());
        user.setConfirmId(request.getConfirmId());
        user.setConfirmIdCreated(new Date());
        user.setRegisteredUser(false);
        tableLogger.setRegistered(user);
        userRepository.save(user);
        return userEncode.encode(user);
    }

    @Transactional(readOnly = false)
    public ConfirmResultResponse confirm(String uuid) {

        final long TEN_MINUTES = 600000l;
        ConfirmResultResponse response = new ConfirmResultResponse();

        User user = userRepository.findByConfirmId(uuid);

        Date startOfTimes = new Date();
        startOfTimes.setTime(1);

        Date createdConfirmDate = user != null ? user.getConfirmIdCreated() : startOfTimes;

        if ((new Date().getTime() - createdConfirmDate.getTime()) < TEN_MINUTES) {
            user.setReceivePromo(true);
            user.setConfirmId(null);
            userRepository.save(user);

            response.setUserMsg("Your email " + user.getEmail() + " was succeccfully confirmed");
            response.setSuccess(true);
        } else {
            response.setUserMsg("Confirm link unknown or outdated.");
            response.setSuccess(false);
        }

        return response;
    }
}
