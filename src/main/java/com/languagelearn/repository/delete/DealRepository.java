//package com.languagelearn.repository.delete;
//
//import com.languagelearn.model.Deal;
//import com.languagelearn.object.request.DealSearchRequest;
//import com.languagelearn.repository.ModelRepository;
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Conjunction;
//import org.hibernate.criterion.Projections;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.sql.JoinType;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//@Repository
//public class DealRepository extends ModelRepository<Deal> {
//
//    private static final String ENTITY_ID_PROPERTY = "d.id";
//
//    //    The key represents property name received from UI
//    //    The value represents column name for ordering
//    private static final Map<String, String> orderPropertyMapping = new HashMap<String, String>(){
//        {
//            put("type","dt.name");
//            put("category","dc.name");
//            put("daysLeft","expireDate");
//            put("vendor","v.name");
//        }
//    };
//
//
//    public PageImpl<Deal> search(DealSearchRequest request) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "d");
//        c.createAlias("d.category", "dc");
//        c.createAlias("dc.type", "ty");
//        c.createAlias("d.vendor", "v");
//        c.createAlias("d.offerSet", "do", JoinType.LEFT_OUTER_JOIN);
//        c.createAlias("d.tagSet", "t", JoinType.LEFT_OUTER_JOIN);
//
//        Conjunction and = new Conjunction();
//
//        safeAddRestrictionEq(and, "d.id", request.getId());
//        safeAddRestrictionIlikeAnyWhere(and, "d.name", request.getName());
//        safeAddRestrictionIlikeAnyWhere(and, "d.description", request.getDescription());
//        safeAddRestrictionEq(and, "dc.id", request.getCategoryId());
//        safeAddRestrictionEq(and, "ty.id", request.getTypeId());
//        safeAddRestrictionEq(and, "v.id", request.getVendor() != null ? request.getVendor().getId() : null);
//        safeAddRestrictionGe(and, "d.expireDate", request.getExpireDateAfter());
//        safeAddRestrictionLe(and, "d.expireDate", request.getExpireDateBefore());
//        safeAddRestrictionEq(and, "d.limit", request.getLimit());
//
//        safeAddRestrictionLe(and, "do.actualPrice", request.getPriceTo());
//        safeAddRestrictionGe(and, "do.actualPrice", request.getPriceFrom());
//
//        safeAddRestrictionEq(and, "d.isDeleted", false);
//        safeAddRestrictionEq(and, "d.isActive", request.isActiveOnly() != null && request.isActiveOnly() ? true : null);
//        safeAddRestrictionEq(and, "d.isPromo", request.isPromo());
//
//        c.add(and);
//
//
//        c.setProjection(Projections.countDistinct(ENTITY_ID_PROPERTY));
//        long total = (Long)c.uniqueResult();
//
//        c.setProjection(null);
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//
//        addOrdering(c, request, orderPropertyMapping, ENTITY_ID_PROPERTY);
//        c.setFirstResult(request.getOffset());
//        c.setMaxResults(request.getPageSize());
//        List<Deal> results = c.list();
//
//        return new PageImpl<>(results, request, total);
//    }
//
//    public List<Deal> getTodaysDeals(int count) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "d");
//        c.createAlias("d.category", "dc");
//        c.createAlias("dc.type", "ty");
//        c.createAlias("d.vendor", "v");
//        c.createAlias("d.offerSet", "do", JoinType.LEFT_OUTER_JOIN);
//        c.createAlias("d.tagSet", "t", JoinType.LEFT_OUTER_JOIN);
//
//        Conjunction and = new Conjunction();
//
//        safeAddRestrictionGe(and, "d.createdDateOnly", new Date());
//
//        safeAddRestrictionEq(and, "d.isDeleted", false);
//        safeAddRestrictionEq(and, "d.isActive", true);
//        c.add(and);
//
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//
//        c.setFirstResult(0);
//        c.setMaxResults(count);
//        return c.list();
//    }
//
//    public List<Deal> getList(List<Long> idList) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "d");
//        c.add(Restrictions.in("id", idList));
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        return c.list();
//    }
//}
