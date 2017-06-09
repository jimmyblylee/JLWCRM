package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.dto.RiskOfProject;
import com.asdc.jbp.crm.entity.Project;
import com.asdc.jbp.framework.utils.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RiskService {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    /**
     * 项目风险：1、项目产值；2、项目收款；3、项目成本；4、项目进度.
     */
    public List<RiskOfProject> getProjectRist() {
        //noinspection unchecked
        List<Project> projs = em.createQuery("from Project").getResultList();

        List<RiskOfProject> result = new LinkedList<>();
        for (Project proj : projs) {
            RiskOfProject risk = new RiskOfProject();
            risk.setProjId(proj.getId());
            risk.setProjName(proj.getPact().getName());
            risk.setProjSum(proj.getPact().getSum());
            // 1、项目产值
            risk.setOutputValue(new BigDecimal(proj.getPact().getSum())
                .multiply(new BigDecimal(proj.getProcess()))
                .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());

            // 2、项目收款
            String hql = "select sum(num) from RecPay where pact.id = :packId";
            try {
                risk.setRecPay(((Number) em.createQuery(hql)
                    .setParameter("packId", proj.getPact().getId()).getSingleResult()).intValue());
            } catch (NoResultException ex) {
                risk.setRecPay(0);
            }

            // 3、项目成本
            hql = "select sum(num) from Cost where pact.id = :packId";
            try {
                risk.setCost(((Number) em.createQuery(hql)
                    .setParameter("packId", proj.getPact().getId()).getSingleResult()).intValue());
            } catch (NoResultException ex) {
                risk.setCost(0);
            }

            // 4、项目进度
            risk.setProcess(proj.getProcess());

            // 实时利润
            risk.setGain(risk.getRecPay() - risk.getCost());

            // 实时利润率
            risk.setGainRate(new BigDecimal(risk.getGain()*100)
                .divide(new BigDecimal(risk.getRecPay()), 2, BigDecimal.ROUND_HALF_UP).doubleValue());

            result.add(risk);
        }

        return result;

    }

    /**
     * 销售风险：1、销售支出；2、合同；3、回款；4、商机.
     */
    public Map<String, List<Integer>> getSalesRisk(String year) {
        Map<String, List<Integer>> result = new HashMap<>();
        // 1、销售支出
        result.put("costSum", new LinkedList<>());
        // 2、合同额
        result.put("pactSum", new LinkedList<>());
        // 3、回款
        result.put("recPaySum", new LinkedList<>());
        // 4、商机
        result.put("clewSum", new LinkedList<>());
        for (int i = 0; i < 12; i++) {
            String hql = "";
            // 1、销售支出
            hql = "select sum(num) from Cost where type.code = '1' and time like :time";
            try {
                result.get("costSum").add(((Number) em.createQuery(hql)
                        .setParameter("time", getMonthStr(year, i) + "%").getSingleResult()).intValue());
            } catch (NoResultException | NullPointerException ex) {
                result.get("costSum").add(0);
            }
            // 2、合同额
            hql = "select sum(p.sum) from Pact p where p.date like :date";
            try {
                result.get("pactSum").add(((Number) em.createQuery(hql)
                    .setParameter("date", getMonthStr(year, i) + "%").getSingleResult()).intValue());
            } catch (NoResultException | NullPointerException ex) {
                result.get("pactSum").add(0);
            }
            // 3、回款
            hql = "select sum(r.num) from RecPay r where r.date like :date";
            try {
                result.get("recPaySum").add(((Number) em.createQuery(hql)
                    .setParameter("date", getMonthStr(year, i) + "%").getSingleResult()).intValue());
            } catch (NoResultException | NullPointerException ex) {
                result.get("recPaySum").add(0);
            }
            // 4、商机
            hql = "select sum(c.budget) from Clew c where c.proposal like :date";
            try {
                result.get("clewSum").add(((Number) em.createQuery(hql)
                    .setParameter("date", getMonthStr(year, i) + "%").getSingleResult()).intValue());
            } catch (NoResultException | NullPointerException ex) {
                result.get("clewSum").add(0);
            }
        }
        return result;
    }

    private String getMonthStr(String year, int i) {
        return year + "/" + StringUtils.leftPad((i + 1) + "", 2, '0');
    }

    /**
     * 经营风险：1、总支出；2、总收入；3、资金占用.
     */
    public Map<String, List<Integer>> getMgmtRisk(String year) {
        Map<String, List<Integer>> result = new HashMap<>();
        // 1、总支出；
        result.put("costSum", new LinkedList<>());
        // 2、总收入；
        result.put("recPaySum", new LinkedList<>());
        // 3、资金占用.
        result.put("costPossessionSum", new LinkedList<>());
        for (int i = 0; i < 12; i++) {
            String hql = "";
            // 1、总支出
            hql = "select sum(num) from Cost where time like :time";
            try {
                result.get("costSum").add(((Number) em.createQuery(hql)
                    .setParameter("time", getMonthStr(year, i) + "%")
                    .getSingleResult()).intValue());
            } catch (NoResultException | NullPointerException ex) {
                result.get("costSum").add(0);
            }
            // 3、总收入
            hql = "select sum(r.num) from RecPay r where r.date like :date";
            try {
                result.get("recPaySum").add(((Number) em.createQuery(hql)
                    .setParameter("date", getMonthStr(year, i) + "%")
                    .getSingleResult()).intValue());
            } catch (NoResultException | NullPointerException ex) {
                result.get("recPaySum").add(0);
            }
            // 3、资金占用
            result.get("costPossessionSum").add(result.get("recPaySum").get(i) - result.get("costSum").get(i));

        }
        return result;
    }
}
