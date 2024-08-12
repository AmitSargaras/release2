package com.integrosys.cms.ui.collateral;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;


public class InsuranceGCDaoImpl extends HibernateDaoSupport implements IInsuranceGCDao{

	
	public IInsuranceGC createInsurance(String entityName,IInsuranceGC insurance) 
	throws InsuranceCGException {
		
			if(!(entityName==null|| insurance==null)){	
				
							
				
			Long key = (Long) getHibernateTemplate().save(entityName, insurance);
			insurance.setId(key.longValue());
			return insurance;
			}else{
				throw new ComponentException("ERROR- Entity name or key is null ");
			}
		
	}
	
	public IInsuranceGC updateInsurance(String entityName, IInsuranceGC item)throws InsuranceCGException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IInsuranceGC) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new ComponentException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public String getInsCode() {
		Query query = currentSession().createSQLQuery("SELECT INSURANCEGC_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String insCode = numberFormat.format(Long.parseLong(sequenceNumber));
				
		return insCode;
	}

	
	public IInsurancePolicy createInsurancePolicy(String entityName,IInsurancePolicy insurancePolicy) throws InsuranceCGException {
		
			if(!(entityName==null|| insurancePolicy==null)){	
				
			Long key = (Long) getHibernateTemplate().save(entityName, insurancePolicy);
			
			insurancePolicy.setRefID(String.valueOf(key.longValue()));
			insurancePolicy.setInsurancePolicyID(key.longValue());
			
			return insurancePolicy;
			}else{
				throw new ComponentException("ERROR- Entity name or key is null ");
			}
		
	}
	
	public IInsurancePolicy createAndUpdateInsurancePolicy(String entityName,IInsurancePolicy insurancePolicy) throws InsuranceCGException {
		
		if(!(entityName==null|| insurancePolicy==null)){	
			
		Long key = (Long) getHibernateTemplate().save(entityName, insurancePolicy);
		
		insurancePolicy.setRefID(String.valueOf(key.longValue()));
		insurancePolicy.setInsurancePolicyID(key.longValue());
		IInsurancePolicy updateInsurancePolicy = updateInsurancePolicy(entityName,insurancePolicy);
		
		return updateInsurancePolicy;
		}else{
			throw new ComponentException("ERROR- Entity name or key is null ");
		}
	
}
	public IInsurancePolicy updateInsurancePolicy(String entityName,IInsurancePolicy insurancePolicy)throws InsuranceCGException{
		if(!(entityName==null|| insurancePolicy==null)){
			getHibernateTemplate().update(entityName, insurancePolicy);
			return  (IInsurancePolicy) getHibernateTemplate().load(entityName, new Long(insurancePolicy.getInsurancePolicyID()));
		}else{
			throw new ComponentException("ERROR-- Entity Name Or Key is null");
		}
	}

	
}
