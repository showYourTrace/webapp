//package com.showyourtrace.repository.delete;
//
//import com.showyourtrace.model.DealOffer;
//import com.showyourtrace.object.request.DealOfferSearchRequest;
//import com.showyourtrace.repository.ModelRepository;
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Conjunction;
//import org.hibernate.criterion.Projections;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.sql.JoinType;
//import org.hibernate.transform.DistinctRootEntityResultTransformer;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class DealOfferRepository extends ModelRepository<DealOffer> {
//
//    private static final String ENTITY_ID_PROPERTY = "do.id";
//
//    private static final Map<String, String> orderPropertyMapping = new HashMap<String, String>(){
//        {
//            put("",""); //TODO: fill in order property mapping for Deal offer
//        }
//    };
//
//    public List<DealOffer> findByDealId(Long dealId) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "do");
//        c.createAlias("do.deal","d");
//        c.add(Restrictions.eq("do.isDeleted", false));
//        c.add(Restrictions.eq("d.id", dealId));
//        c.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
//        return c.list();
//    }
//
//    public Page<DealOffer> search(DealOfferSearchRequest request) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "do");
//        c.createAlias("do.deal", "d");
//        c.createAlias("d.category", "dc");
//        c.createAlias("dc.type", "dt");
//        c.createAlias("d.vendor", "v");
//        c.createAlias("d.tagSet", "t", JoinType.LEFT_OUTER_JOIN);
//
//        Conjunction and = new Conjunction();
//
//        safeAddRestrictionEq(and, "d.id", request.getId());
//        safeAddRestrictionIlikeAnyWhere(and, "d.name", request.getName());
//        safeAddRestrictionIlikeAnyWhere(and, "d.description", request.getDescription());
//        safeAddRestrictionEq(and, "dt.id", request.getTypeId());
//        safeAddRestrictionEq(and, "dc.id", request.getCategoryId());
//        safeAddRestrictionEq(and, "v.id", request.getVendor() != null ? request.getVendor().getId() : null);
//        safeAddRestrictionGe(and, "d.expireDate", request.getExpireDateAfter());
//        safeAddRestrictionLe(and, "d.expireDate", request.getExpireDateBefore());
//        safeAddRestrictionEq(and, "d.limit", request.getLimit());
//
//        safeAddRestrictionLe(and, "do.actualPrice", request.getPriceTo());
//        safeAddRestrictionGe(and, "do.actualPrice", request.getPriceFrom());
//        safeAddRestrictionLe(and, "do.discount", request.getDiscountTo());
//        safeAddRestrictionGe(and, "do.discount", request.getDiscountFrom());
//        safeAddRestrictionLe(and, "do.discountPercentage", request.getDiscountPercentageTo());
//        safeAddRestrictionGe(and, "do.discountPercentage", request.getDiscountPercentageFrom());
//
//        safeAddRestrictionEq(and, "do.isDeleted", false);
//        safeAddRestrictionEq(and, "d.isActive", request.isActive());
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
//        List<DealOffer> results = c.list();
//
//        return new PageImpl<>(results, request, total);
//    }
//}
