package com.languagelearn.repository;

import com.languagelearn.model.Tag;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepository extends ModelRepository<Tag> {

    public List<String> search(String term) {
        Criteria c = getSession().createCriteria(getEntityClass(), "t");

        c.add(Restrictions.ilike("t.name", term, MatchMode.START));
        c.setProjection(Projections.property("t.name"));
        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        c.addOrder(Order.asc("t.name"));
        return c.list();
    }

    public Tag find(String tag) {
        Criteria c = getSession().createCriteria(getEntityClass(), "t");

        c.add(Restrictions.eq("t.name", tag));
        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Tag> result = c.list();
        return result.size() > 0 ? result.get(0) : null;
    }
}
