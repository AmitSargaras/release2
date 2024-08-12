package com.integrosys.cms.app.collateral.bus.type.property;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class PropertyValuation2DaoImpl extends HibernateDaoSupport implements
IPropertyValuation2Dao { 

	public String getEntityNamePropertyVal2() {
		return IPropertyValuation2Dao.PROPERTY_VALUATION2;
	}

	public IPropertyValuation2 createPropertyValuation2(IPropertyValuation2 iPropertyValuation2) {
		DefaultLogger.debug(this, "start createPropertyValuation2");
		IPropertyValuation2 returnObj= new PropertyValuation2();
		DefaultLogger.debug(this, "returnObj:"+returnObj);
		try {
			getHibernateTemplate().save(getEntityNamePropertyVal2(), iPropertyValuation2);
			
			DefaultLogger.debug(this, "save createPropertyValuation2 completed.");
			returnObj = (IPropertyValuation2)getHibernateTemplate().load(getEntityNamePropertyVal2(), new Long(iPropertyValuation2.getId()));
			DefaultLogger.debug(this, "completed createPropertyValuation2");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in createPropertyValuation2:"+obe.getMessage());
			obe.printStackTrace();
		}
		
		DefaultLogger.debug(this, "returning returnObj.getId():"+returnObj.getId());
		return returnObj;
	}

	public void updatePropertyValuation2(IPropertyValuation2 iPropertyValuation2) {
		DefaultLogger.debug(this, "start updatePropertyValuation2");
		try {
			if(iPropertyValuation2!=null){
				DefaultLogger.debug(this, "updatePropertyValuation2 id before:"+iPropertyValuation2.getId());
				getHibernateTemplate().update(getEntityNamePropertyVal2(), iPropertyValuation2);
				DefaultLogger.debug(this, "updatePropertyValuation2 id after:"+iPropertyValuation2.getId());
			}
			
			DefaultLogger.debug(this, "completed updatePropertyValuation2");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in updatePropertyValuation2:"+obe.getMessage());
			obe.printStackTrace();
		}
		
	}
	
	public IPropertyValuation2 getPropertyValuation2(Long id) {
		DefaultLogger.debug(this, "start getPropertyValuation2 :"+id);
		try {
		if(id!=null){
			 IPropertyValuation2 iPropertyValuation2 = (IPropertyValuation2)getHibernateTemplate().get(getEntityNamePropertyVal2(), id);
			 return iPropertyValuation2;
			}
			DefaultLogger.debug(this, "completed getPropertyValuation2");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in getPropertyValuation2:"+obe.getMessage());
			obe.printStackTrace();
		}
		return null;
	}
}
