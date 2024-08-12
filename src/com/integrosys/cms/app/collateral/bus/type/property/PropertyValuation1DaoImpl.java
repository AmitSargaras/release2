package com.integrosys.cms.app.collateral.bus.type.property;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author uma.khot $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class PropertyValuation1DaoImpl extends HibernateDaoSupport implements
IPropertyValuation1Dao { 

	public String getEntityNamePropertyVal1() {
		return IPropertyValuation1Dao.PROPERTY_VALUATION1;
	}
	

	public IPropertyValuation1 createPropertyValuation1(IPropertyValuation1 iPropertyValuation1) {
		DefaultLogger.debug(this, "start createPropertyValuation1");
		IPropertyValuation1 returnObj= new PropertyValuation1();
		DefaultLogger.debug(this, "returnObj:"+returnObj);
		try {
			getHibernateTemplate().save(getEntityNamePropertyVal1(), iPropertyValuation1);
			
			DefaultLogger.debug(this, "save createPropertyValuation1 completed.");
			returnObj = (IPropertyValuation1)getHibernateTemplate().load(getEntityNamePropertyVal1(), new Long(iPropertyValuation1.getId()));
			DefaultLogger.debug(this, "completed createPropertyValuation1");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in createPropertyValuation1:"+obe.getMessage());
			obe.printStackTrace();
		}
		
		DefaultLogger.debug(this, "returning returnObj.getId():"+returnObj.getId());
		return returnObj;
	}

	public void updatePropertyValuation1(IPropertyValuation1 iPropertyValuation1) {
		DefaultLogger.debug(this, "start updatePropertyValuation1");
		
		try {
		
			
			if(iPropertyValuation1!=null){
				
				DefaultLogger.debug(this, "updatePropertyValuation1 id before:"+iPropertyValuation1.getId());
				getHibernateTemplate().update(getEntityNamePropertyVal1(), iPropertyValuation1);
				DefaultLogger.debug(this, "updatePropertyValuation1 id after:"+iPropertyValuation1.getId());
			//	return  (IPropertyValuation1) getHibernateTemplate().load(getEntityNamePropertyVal1(), new Long(iPropertyValuation1.getId()));
			}
			
			DefaultLogger.debug(this, "completed updatePropertyValuation1");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in updatePropertyValuation1:"+obe.getMessage());
			obe.printStackTrace();
		}
		
		//return null;
		
		
	}
	
	public IPropertyValuation1 getPropertyValuation1(Long id) {
		DefaultLogger.debug(this, "start getPropertyValuation1 :"+id);
		
		try {
		if(id!=null){
				
			 IPropertyValuation1 iPropertyValuation1 = (IPropertyValuation1)getHibernateTemplate().get(getEntityNamePropertyVal1(), id);
			 return iPropertyValuation1;
			}
			
			DefaultLogger.debug(this, "completed getPropertyValuation1");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in getPropertyValuation1:"+obe.getMessage());
			obe.printStackTrace();
		}
		return null;
		
		
	}

	
	
}
