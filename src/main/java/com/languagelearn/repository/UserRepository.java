package com.languagelearn.repository;

import com.languagelearn.model.User;
import com.languagelearn.object.request.UserSearchRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository extends ModelRepository<User> {

    private static final String ENTITY_ID_PROPERTY = "u.id";

    //    The key represents property name received from UI
    //    The value represents column name for ordering
    private static final Map<String, String> userPropertyMapping = new HashMap<String, String>(){
        private static final long serialVersionUID = -8353477906689726051L;
        {
            put("","");
        }
    };

    public User findByUserName(String username) {
        Criteria c = getSession().createCriteria(getEntityClass(), "u");
        c.add(Restrictions.eq("u.login", username));
        c.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        List<User> result = c.list();
        return result.size() > 0 ? result.get(0) : null;
    }

    public User findByEmail(String email) {
        Criteria c = getSession().createCriteria(getEntityClass(), "u");
        c.add(Restrictions.eq("u.email", email));
        c.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        List<User> result = c.list();
        return result.size() > 0 ? result.get(0) : null;
    }

    public User findByConfirmId(String uuid) {
        Criteria c = getSession().createCriteria(getEntityClass(), "u");
        c.add(Restrictions.eq("u.confirmId", uuid));
        c.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        List<User> result = c.list();
        return result.size() > 0 ? result.get(0) : null;
    }

    public PageImpl<User> search(UserSearchRequest request) {
        Criteria c = getSession().createCriteria(getEntityClass(), "u");
        Conjunction and = new Conjunction();

        safeAddRestrictionEq(and, "u.id", request.getId());
        safeAddRestrictionIlikeAnyWhere(and, "u.login", request.getLogin());
        safeAddRestrictionIlikeAnyWhere(and, "u.name", request.getName());
        safeAddRestrictionIlikeAnyWhere(and, "u.secondName", request.getSecondName());
        safeAddRestrictionIlikeAnyWhere(and, "u.email", request.getEmail());
        safeAddRestrictionEq(and, "u.receivePromo", request.isReceivePromoOnly() != null && request.isReceivePromoOnly() ? true : null);


        and.add(Restrictions.eq("u.isDeleted", false));

        c.add(and);

        c.setProjection(Projections.countDistinct(ENTITY_ID_PROPERTY));
        long total = (Long)c.uniqueResult();

        c.setProjection(null);
        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        addOrdering(c, request, userPropertyMapping, ENTITY_ID_PROPERTY);

        c.setFirstResult(request.getOffset());
        c.setMaxResults(request.getPageSize());
        List<User> results = c.list();

        return new PageImpl<>(results, request, total);
    }

    public List<User> getAllSubscribers() {
        Criteria c = getSession().createCriteria(getEntityClass(), "u");
        c.add(Restrictions.eq("u.receivePromo", true));
        c.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        return c.list();
    }

    public boolean authorize(String username, String password, boolean b) {
        User user = findByUserName(username);
        if(user.getRegisteredUser() != true) {
            return false;
        }
        String existingHash = user.getPwd();
        return BCrypt.checkpw(password, existingHash);
    }

    public String findByHash(String hash) {
        return null;
    }

    public boolean isAdmin(String username) {
        User user = findByUserName(username);
        return user.getIsAdmin();
    }

}
