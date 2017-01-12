//package com.languagelearn.repository.delete;
//
//import com.languagelearn.model.Deal;
//import com.languagelearn.model.DealTag;
//import com.languagelearn.repository.ModelRepository;
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.sql.JoinType;
//import org.springframework.stereotype.Repository;
//
//import java.util.Collection;
//
//@Repository
//public class DealTagRepository extends ModelRepository<DealTag> {
//
//    public Collection<DealTag> findByDeal(Deal deal) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "dt");
//        c.createAlias("dt.deal", "d", JoinType.INNER_JOIN);
//        c.add(Restrictions.eq("d.id", deal.getId()));
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        return c.list();
//    }
//}
