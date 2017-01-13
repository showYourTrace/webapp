//package com.showyourtrace.repository.delete;
//
//import com.showyourtrace.model.DealCategory;
//import com.showyourtrace.repository.ModelRepository;
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.sql.JoinType;
//import org.springframework.stereotype.Repository;
//
//import java.util.Collection;
//
//@Repository
//public class DealCategoryRepository extends ModelRepository<DealCategory> {
//
//    public Collection<DealCategory> findByType(Long typeId) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "dc");
//        c.createAlias("dc.type", "dt", JoinType.INNER_JOIN);
//        c.add(Restrictions.eq("dt.id", typeId));
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        return c.list();
//    }
//}
