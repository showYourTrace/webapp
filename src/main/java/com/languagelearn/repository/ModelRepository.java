package com.languagelearn.repository;

import com.languagelearn.object.PageableRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Sort;

import java.util.Map;

public abstract class ModelRepository<M> extends GenericModelRepository<M, Long> {

    protected void safeAddRestrictionIlikeAnyWhere(Conjunction and, String fieldName, String fieldValue) {
        if(fieldValue == null ||
                (fieldValue instanceof String && ((String) fieldValue).isEmpty())) {
            return;
        }
        and.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
    }

    protected void safeAddRestrictionIlikeAnyWhereOrNull(Conjunction and, String fieldName, String fieldValue) {
        if(fieldValue == null ||
                (fieldValue instanceof String && ((String) fieldValue).isEmpty())) {
            and.add(Restrictions.isNull(fieldName));
        }
        else {
            and.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        }
    }

    protected void safeAddRestrictionEq(Conjunction and, String fieldName, Object fieldValue) {
        if(fieldValue == null ||
                (fieldValue instanceof String && ((String) fieldValue).isEmpty())) {
            return;
        }
        and.add(Restrictions.eq(fieldName, fieldValue));
    }

    protected void safeAddRestrictionEqOrNull(Conjunction and, String fieldName, Object fieldValue) {
        if(fieldValue == null ||
                (fieldValue instanceof String && ((String) fieldValue).isEmpty())) {
            and.add(Restrictions.isNull(fieldName));
        }
        else {
            and.add(Restrictions.eq(fieldName, fieldValue));
        }
    }

    protected void safeAddRestrictionGe(Conjunction and, String fieldName, Object fieldValue) {
        if(fieldValue == null ||
                (fieldValue instanceof String && ((String) fieldValue).isEmpty())) {
            return;
        }
        and.add(Restrictions.ge(fieldName, fieldValue));
    }

    protected void safeAddRestrictionLe(Conjunction and, String fieldName, Object fieldValue) {
        if(fieldValue == null ||
                (fieldValue instanceof String && ((String) fieldValue).isEmpty())) {
            return;
        }
        and.add(Restrictions.le(fieldName, fieldValue));
    }

    protected void addOrdering(Criteria criteria, PageableRequest request, Map<String, String> orderingPropertyMapping, String defaultOrderingProperty ) {
        int orderings = 0;
        if(request.getSort() != null) {
            for (Sort.Order order : request.getSort()) {
                String property = order.getProperty();

                String orderProperty = orderingPropertyMapping.get(property);
                if(orderProperty != null) {
                    property = orderProperty;
                }

                orderings++;
                Order ord;
                if (order.isAscending()) {
                    ord = Order.asc(property);
                } else {
                    ord = Order.desc(property);
                }
                criteria.addOrder(ord);
            }
            if(orderings == 0) {
                criteria.addOrder(Order.asc(defaultOrderingProperty) );
            }
        }
    }

}
