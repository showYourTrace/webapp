//package com.languagelearn.repository.delete;
//
//import com.languagelearn.model.Vendor;
//import com.languagelearn.object.request.VendorSearchRequest;
//import com.languagelearn.repository.ModelRepository;
//import org.hibernate.Criteria;
//import org.hibernate.criterion.*;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class VendorRepository extends ModelRepository<Vendor> {
//
//    private static final String ENTITY_ID_PROPERTY = "v.id";
//
//    //    The key represents property name received from UI
//    //    The value represents column name for ordering
//    private static final Map<String, String> orderPropertyMapping = new HashMap<String, String>(){
//        private static final long serialVersionUID = -8353477906689726051L;
//        {
//            put("","");
//        }
//    };
//
//    public PageImpl<Vendor> search(VendorSearchRequest request) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "v");
//        Conjunction and = new Conjunction();
//
//        safeAddRestrictionEq(and, "v.id", request.getId());
//        safeAddRestrictionIlikeAnyWhere(and, "v.name", request.getName());
//        safeAddRestrictionEq(and, "v.zip", request.getZip());
//        safeAddRestrictionIlikeAnyWhere(and, "v.address", request.getAddress());
//        safeAddRestrictionIlikeAnyWhere(and, "v.phone", request.getPhone());
//        safeAddRestrictionIlikeAnyWhere(and, "v.email", request.getEmail());
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
//
//        c.setFirstResult(request.getOffset());
//        c.setMaxResults(request.getPageSize());
//        List<Vendor> results = c.list();
//
//        return new PageImpl<>(results, request, total);
//    }
//
//    public Vendor findByEmail(String email) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "v");
//        Conjunction and = new Conjunction();
//
//        safeAddRestrictionEq(and, "v.email", email);
//        c.add(and);
//
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//
//        List<Vendor> result = c.list();
//        return result.size() > 0 ? result.get(0) : null;
//    }
//}
