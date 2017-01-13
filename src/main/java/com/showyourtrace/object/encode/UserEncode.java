package com.languagelearn.object.encode;

import com.languagelearn.Util;
import com.languagelearn.model.User;
import com.languagelearn.object.EncodeEntity;
import com.languagelearn.object.response.UserResponse;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

@Component
public class UserEncode implements EncodeEntity<User, UserResponse>{

    @Override
    public UserResponse encode(User entity) {
        UserResponse response = new UserResponse();
        response.setId(entity.getId());
        response.setLogin(entity.getLogin());
        response.setName(entity.getName());
        response.setSecondName(entity.getSecondName());
        response.setEmail(entity.getEmail());
        response.setRegisteredUser(entity.getRegisteredUser());
        response.setReceivePromo(entity.isReceivePromo());

        String visibleName = Util.safeNotNullString(entity.getName()) + " " + Util.safeNotNullString(entity.getSecondName());
        response.setFullName(Util.safeString(visibleName).trim().isEmpty() ? entity.getLogin() : visibleName);
        return response;
    }

    @Override
    public Collection<UserResponse> encode(Collection<User> entityCollection) {
        throw new NotImplementedException();
    }
}
