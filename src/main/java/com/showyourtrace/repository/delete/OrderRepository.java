//package com.showyourtrace.repository.delete;
//
//import com.showyourtrace.model.Order;
//import com.showyourtrace.model.User;
//import com.showyourtrace.repository.ModelRepository;
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.transform.DistinctRootEntityResultTransformer;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class OrderRepository extends ModelRepository<Order> {
//
//    public Order getLastOrderData(User user) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "ord");
//
//        c.add(Restrictions.eq("createdBy", user.getLogin()));
//        c.addOrder(org.hibernate.criterion.Order.desc("createdDate"));
//        c.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
//
//        c.setMaxResults(1);
//        List<Order> result = c.list();
//        return result.size() > 0 ? result.get(0) : null;
//    }
//
//    public List<Order> getOrders(User user, Integer pageSize) {
//        Criteria c = getSession().createCriteria(getEntityClass(), "ord");
//        c.add(Restrictions.eq("createdBy", user.getLogin()));
//        c.addOrder(org.hibernate.criterion.Order.desc("createdDate"));
//        c.setMaxResults(pageSize);
//        c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        return c.list();
//    }
//}
