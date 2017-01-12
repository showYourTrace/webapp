package com.languagelearn.repository;

import com.languagelearn.exception.NotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public class GenericModelRepository<M, T extends Serializable> implements InternalRepository<M, T> {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Class getEntityClass() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Collection<M> getAll() {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        return criteria.list();
    }

    public <S extends M> void evict(S entity) {
        getSession().evict(entity);
    }

    public <S extends M> S initializeAndUnproxy(S entity) {
        if (entity == null) {
            throw new
                    NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (S) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }

    public Page<M> findAll(Pageable pageable) {
        Criteria c = getSession().createCriteria(getEntityClass());
        c.setProjection(Projections.rowCount());
        Long rowCount = (Long) c.list().get(0);
        Integer total = rowCount != null ? rowCount.intValue() : 0;

        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.setFirstResult(pageable.getOffset() * pageable.getPageNumber());
        criteria.setMaxResults(pageable.getPageSize());
        if (pageable.getSort() != null) {
            for (Sort.Order order : pageable.getSort()) {
                Order ord;
                if (order.isAscending()) {
                    ord = Order.asc(order.getProperty());
                } else {
                    ord = Order.desc(order.getProperty());
                }
                criteria.addOrder(ord);
            }
        }
        List results = criteria.list();

        PageImpl<M> page = new PageImpl<M>(results, pageable, total);
        return page;
    }

    public <S extends M> S save(S entity) {
        return save(entity, false);
    }

    public <S extends M> S save(S entity, boolean flush) {
        getSession().saveOrUpdate(entity);
        if (flush) getSession().flush();
        return entity;
    }

    public <S extends M> Collection<S> save(Collection<S> entities) {
        return save(entities, false);
    }

    public <S extends M> Collection<S> save(Collection<S> entities, boolean flush) {
        for (S entity : entities) {
            save(entity, flush);
        }
        if (flush) {
            getSession().flush();
        }
        return entities;
    }

    public M findById(T id) {

        M result = (M) getSession().get(getEntityClass(), id);

        if (result == null) {
            throw new NotFoundException(getEntityClass().getCanonicalName().concat(" id ").concat(id.toString()).concat(" not found!"));
        }
        return result;
    }

    public boolean exists(T id) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("id", id));
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.list().get(0)) > 0;
    }

    public void delete(T id) {
        getSession().delete(getSession().load(getEntityClass(), id));
    }

    public <S extends M> void delete(S entity) {
        getSession().delete(entity);
    }

    public void delete(Collection<? extends M> entities) {
        for (M entity : entities) {
            getSession().delete(entity);
        }
    }

    @Override
    public void executeUpdateSQL(String sql) {
        Session session = getSession();
        session.beginTransaction();
        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
