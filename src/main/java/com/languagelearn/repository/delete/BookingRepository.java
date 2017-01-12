//package com.languagelearn.repository.delete;
//
//import com.languagelearn.model.Booking;
//import com.languagelearn.object.request.BookingSearchRequest;
//import com.languagelearn.repository.ModelRepository;
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Conjunction;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Projections;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.sql.JoinType;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class BookingRepository extends ModelRepository<Booking> {
//
//    private static final String ENTITY_ID_PROPERTY = "b.id";
//
////    The key represents property name received from UI
////    The value represents column name for ordering
//    private static final Map<String, String> orderPropertyMapping = new HashMap<String, String>(){
//        {
//            put("type","dt.name");
//            put("category","dc.name");
//            put("price","do.actualPrice");
//            put("discount","do.discount");
//            put("discountPercentage","do.discountPercentage");
//            put("email","u.email");
//            put("expireDate","d.expireDate");
//            put("vendor","v.name");
//        }
//    };
//
//    public long getDealBookCount(Long id) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "b");
//        c.createAlias("b.dealOffer", "do", JoinType.INNER_JOIN);
//        c.createAlias("do.deal", "d", JoinType.INNER_JOIN);
//        c.add(Restrictions.eq("d.id", id));
//        c.add(Restrictions.eq("b.isDeleted", false));
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//
//        List<Booking> result =  c.list();
//        long count = 0l;
//        for(Booking b : result) {
//            count += (b.getCount() != null ? b.getCount() : 0);
//        }
//        return count;
//    }
//
//    public Page<Booking> search(BookingSearchRequest request) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "b");
//        c.createAlias("b.dealOffer", "do", JoinType.INNER_JOIN);
//        c.createAlias("b.user", "u", JoinType.INNER_JOIN);
//        c.createAlias("do.deal", "d", JoinType.INNER_JOIN);
//        c.createAlias("d.vendor", "v");
//        c.createAlias("d.category", "dc");
//        c.createAlias("dc.type", "dt");
//
//        Conjunction and = new Conjunction();
//
//        safeAddRestrictionEq(and, "b.id", request.getId());
//        safeAddRestrictionEq(and, "dt.id", request.getTypeId());
//        safeAddRestrictionEq(and, "dc.id", request.getCategoryId());
//        safeAddRestrictionIlikeAnyWhere(and, "d.name", request.getName());
//        safeAddRestrictionEq(and, "v.id", request.getVendor().getId());
//        safeAddRestrictionEq(and, "b.active", request.isActiveOnly() != null && request.isActiveOnly() ? true : null);
//
//        safeAddRestrictionGe(and, "b.bookingDate", request.getBookingDateAfter());
//        safeAddRestrictionLe(and, "b.bookingDate", request.getBookingDateBefore());
//        safeAddRestrictionGe(and, "d.expireDate", request.getExpirationAfter());
//        safeAddRestrictionLe(and, "d.expireDate", request.getExpirationBefore());
//
//        safeAddRestrictionGe(and, "do.actualPrice", request.getPriceFrom());
//        safeAddRestrictionLe(and, "do.actualPrice", request.getPriceTo());
//        safeAddRestrictionGe(and, "do.discount", request.getDiscountFrom());
//        safeAddRestrictionLe(and, "do.discount", request.getDiscountTo());
//        safeAddRestrictionGe(and, "do.discountPercentage", request.getDiscountPercentageFrom());
//        safeAddRestrictionLe(and, "do.discountPercentage", request.getDiscountPercentageTo());
//        safeAddRestrictionGe(and, "b.count", request.getCountFrom());
//        safeAddRestrictionLe(and, "b.count", request.getCountTo());
//
//        safeAddRestrictionIlikeAnyWhere(and, "b.description", request.getDescription());
//        safeAddRestrictionIlikeAnyWhere(and, "u.email", request.getEmail());
//        safeAddRestrictionEq(and, "d.isDeleted", false);
//
//        c.add(and);
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
//        List<Booking> results = c.list();
//
//        return new PageImpl<>(results, request, total);
//    }
//
//    public List<Booking> findByUsername(String username) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "b");
//        c.createAlias("b.dealOffer", "do", JoinType.INNER_JOIN);
//        c.createAlias("b.user", "u", JoinType.INNER_JOIN);
//        c.createAlias("do.deal", "d", JoinType.INNER_JOIN);
//
//        Conjunction and = new Conjunction();
//
//        and.add(Restrictions.eq( "u.name", username));
//        safeAddRestrictionEq(and, "b.isDeleted", false);
//
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        c.addOrder(Order.desc("b.bookingDate"));
//
//        return c.list();
//    }
//
//    public Booking getByUuid(String bookingId) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "b");
//        Conjunction and = new Conjunction();
//
//        and.add(Restrictions.eq("b.id", bookingId));
//        safeAddRestrictionEq(and, "b.isDeleted", false);
//        c.add(and);
//
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//
//        List<Booking> result = c.list();
//        return result.size() > 0 ? result.get(0) : null;
//    }
//}
