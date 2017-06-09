package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.entity.Activity;
import com.asdc.jbp.crm.entity.Dict;
import com.asdc.jbp.crm.entity.Sales;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalesService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Sales> query(Sales condition, Integer start, Integer limit) {
        String hql = "";
        hql += "  from Sales s";
        if (!StringUtils.isEmpty(condition.getName())) {
            hql += " where s.name like :name";
        }
        Query query = em.createQuery(hql);
        query.setFirstResult(start).setMaxResults(limit);
        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name","%" + condition.getName() + "%");
        }
        //noinspection unchecked
        return query.getResultList();
    }

    public Integer getCount(Sales condition) {
        String hql = "";
        hql += " select count(s)";
        hql += "  from Sales s";
        if (!StringUtils.isEmpty(condition.getName())) {
            hql += " where s.name like :name";
        }
        Query query = em.createQuery(hql);
        if (!StringUtils.isEmpty(condition.getName())) {
            query.setParameter("name","%" + condition.getName() + "%");
        }
        return ((Number)query.getSingleResult()).intValue();
    }

    public void create(Sales entity) {
        if (StringUtils.isEmpty(entity.getImg())) {
            entity.setImg("iVBORw0KGgoAAAANSUhEUgAAAC0AAAAtCAIAAAC1eHXNAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAxBpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MTc3OTZCQTEzQkMzMTFFMjgwNzY4QjU3RDAzQUNEQzYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MTc3OTZCQTAzQkMzMTFFMjgwNzY4QjU3RDAzQUNEQzYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiBXaW5kb3dzIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9IjMzQjFCRDAyNDI0MDkyMzY0NUEwQkVDOEY0NjZCN0MwIiBzdFJlZjpkb2N1bWVudElEPSIzM0IxQkQwMjQyNDA5MjM2NDVBMEJFQzhGNDY2QjdDMCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PnItSkUAAARmSURBVHjavFi5TiRBDJ0tmhsEQiCQQOJIkciISQlA4gP4Jv6DgAAyIsSVAAEJBCDEHXGIGxbYYd+OJa+xXdXTs9I6GE13V5dfPd+dzc3NlUqlHxXhP3xJd76+vuQdKfQUgv/8K/+w0J1yuawW029Ge8lfqU9isjgIhHxEl2qNfCQ3kYszdXRXq2JIbkQ3rQ6lzKqXL0ICk+ZyHkKImUNdumwpQKQIe/IlnzlzN1KWKkXEmsNagZUpO6rDZ8oxrZPK/4oAeSal3j61FMoDZLRU2SwWHe4Waa+UcZFwmqDItwjkERVhvyqCBaEiCei58tcu0sbpIKqrq3t5eXl/f29ubsblz4rgUVtbG9AAWY04pC10OH0/JV0+PDx0dHRMTEz09vZi5ePj483NzdnZ2cnJCda0trYiWSl2pfV9HNVD/hNdWXZ/f48TT09Pd3V10f2enp6RkZHx8fHT09Pl5eXb21s8+vj4KMRHKLT69fUVlMzMzEAThQMfHTI4ODg7O9vX13d3dwfEaQI0DhUpnP8VpSRwCxx9eHiYF1BSYmlpaZmcnCSnSVOrgqgqPrhWASWbIybgA8SAOflivp9WyRuBQKR0d3fnLu7s7Pz8/CzmH0yRNYcq6MABK1CsFhKVV6TpWUvmZjqXD7yGU8JFchUjktlvVAuS46e8SL0gL7E1cFxeXqZBIKqfn59hQT53LXHrvsl2we7IFukdsaa/vx/ZVtVYe8hvfirTeW7WA5SGhoY0Dqy5uroCGqlVmcYCCqqvlEuVdQAROo6Pj9PkXV9fHxwcwJ3ttm4FjeYPzmYsHEGoZLu7u/v7+xYEJVZ4xubmJsiAM1mUlgbGGr48sa5O4Orr65Ggtra2VIfAWA8PD/EUVZDj040XeyfE+hS3/Yf3tbe3Pz09gXz1FuFAZSEHsq6WThBBpZe0YBkogbKLiwu3XhwdHQFHuSKJ+mBbvszt4t0hRU1N1u/e3t6Q5QA0NlCpRCXBhdi6mItAN2opTOMiBg7VebjOYR8Fu84aiO/QoVHqhoaGLOimpqbR0VEbbjZeLPEhESmux4AMuOrAwIANdfyZmpoaGxtDm6iKpTvrSgmJcHJLLtpPOOnCwoI7DsEu6A5BjGrVci0V0q7gmhPZbGdnZ2lpSXouFdj19fXz83N0ZTRPyFLnzgMcJVliuI3VPKiEi6ysrEANelWOGjjv9vY2JTGbMdOFPcTSreKGREY/iurGxsbi4iIH8NraGkYKVBa5Mp0kdR7LTV92I7CCVnR1dRW2wE3ME8jocGGySKxgxQb34DbDMUBlIdCHHdE2gxKk+b29PUwuioxYtUr1H7ljsT0KoCA04Lbz8/P4j/HODpWxhOHgUPNtIYFi4KDGp7GxEZey4scyaYqP2BeSWA7g/2haqeshMtJ9V2p+UVDUp5U0Dlsg7efAanHYL272Q0/1DLmdZS3zXKH3/1F9Ld8datZRDIebr9ITYpXIYmH/v/moxs+yQuoTfPzLRzrIbwEGAPFYTpqUqkiNAAAAAElFTkSuQmCC");
        }
        entity.setLevel(em.find(Dict.class, entity.getLevel().getId()));
        em.persist(entity);
    }

    public void update(Sales entity) {
        entity.setLevel(em.find(Dict.class, entity.getLevel().getId()));
        em.merge(entity);

    }

    public void remove(Integer id) {
        em.remove(em.find(Sales.class, id));
    }
}
