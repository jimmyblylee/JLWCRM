package com.asdc.jbp.crm.service;

import com.asdc.jbp.crm.dto.RiskOfProject;
import com.asdc.jbp.crm.entity.Project;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

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
    public void getSalesRisk() {
        // 1、销售支出
        // 2、合同
        // 3、回款
        // 4、商机
    }

    /**
     * 经营风险：1、总支出；2、总收入；3、总产值.
     */
    public void getMgmtRisk() {

    }
}
