package com.showyourtrace.object.encode;

import com.showyourtrace.model.User;
import com.showyourtrace.object.EncodeEntity;
import com.showyourtrace.object.response.UserSearchResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserSearchEncode implements EncodeEntity<User, UserSearchResponse> {

    @Override
    public UserSearchResponse encode(User entity) {
        UserSearchResponse response = new UserSearchResponse();
        response.setId(entity.getId());
        response.setLogin(entity.getLogin());
        response.setName(entity.getName());
        response.setSecondName(entity.getSecondName());
        response.setEmail(entity.getEmail());
        response.setReceivePromo(entity.isReceivePromo());
        response.setRegisteredUser(entity.getRegisteredUser());
        return response;
    }

    @Override
    public Collection<UserSearchResponse> encode(Collection<User> entityCollection) {
        return entityCollection.stream()
                    .map(this::encode)
                    .collect(Collectors.toSet());
    }
}
