package com.showyourtrace.util;

import com.showyourtrace.model.AbstractLoggableEntity;
import com.showyourtrace.model.User;
import com.showyourtrace.service.ContextUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TableLogger {

    @Autowired
    private ContextUser contextUser;

    public void setCreated(AbstractLoggableEntity entity) {
        entity.setCreatedBy(getContextUser());
        entity.setCreatedDate(new Date());
    }

    public void setChanged(AbstractLoggableEntity entity) {
        entity.setChangedBy(getContextUser());
        entity.setChangedDate(new Date());
    }

    public void setDeleted(AbstractLoggableEntity entity) {
        entity.setIsDeleted(true);
        entity.setDeletedBy(getContextUser());
        entity.setDeletedDate(new Date());
    }

    public void setRegistered(User user) {
        user.setCreatedBy("WEBREGISTER");
        user.setCreatedDate(new Date());
    }

    public void setRegisterChanged(User user) {
        user.setChangedBy("WEBREGISTER");
        user.setChangedDate(new Date());
    }

    private String getContextUser() {
        return contextUser != null ? contextUser.getCurrentUserName() : "";
    }
}
