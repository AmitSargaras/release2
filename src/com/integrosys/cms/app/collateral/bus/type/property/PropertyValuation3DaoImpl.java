package com.integrosys.cms.app.collateral.bus.type.property;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author uma.khot $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class PropertyValuation3DaoImpl extends HibernateDaoSupport implements
IPropertyValuation3Dao { 

	public String getEntityNamePropertyVal3() {
		return IPropertyValuation3Dao.PROPERTY_VALUATION3;
	}
	

	public IPropertyValuation3 createPropertyValuation3(IPropertyValuation3 iPropertyValuation3) {
		DefaultLogger.debug(this, "start createPropertyValuation3");
		IPropertyValuation3 returnObj= new PropertyValuation3();
		DefaultLogger.debug(this, "returnObj:"+returnObj);
		try {
			getHibernateTemplate().save(getEntityNamePropertyVal3(), iPropertyValuation3);
			
			DefaultLogger.debug(this, "save createPropertyValuation3 completed.");
			returnObj = (IPropertyValuation3)getHibernateTemplate().load(getEntityNamePropertyVal3(), new Long(iPropertyValuation3.getId()));
			DefaultLogger.debug(this, "completed createPropertyValuation3");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in createPropertyValuation3:"+obe.getMessage());
			obe.printStackTrace();
		}
		
		DefaultLogger.debug(this, "returning returnObj.getId():"+returnObj.getId());
		return returnObj;
	}

	public void updatePropertyValuation3(IPropertyValuation3 iPropertyValuation3) {
		DefaultLogger.debug(this, "start updatePropertyValuation3");
		
		try {
		
			
			if(iPropertyValuation3!=null){
				
				DefaultLogger.debug(this, "updatePropertyValuation3 id before:"+iPropertyValuation3.getId());
				getHibernateTemplate().update(getEntityNamePropertyVal3(), iPropertyValuation3);
				DefaultLogger.debug(this, "updatePropertyValuation3 id after:"+iPropertyValuation3.getId());
			//	return  (IPropertyValuation3) getHibernateTemplate().load(getEntityNamePropertyVal3(), new Long(iPropertyValuation3.getId()));
			}
			
			DefaultLogger.debug(this, "completed updatePropertyValuation3");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in updatePropertyValuation3:"+obe.getMessage());
			obe.printStackTrace();
		}
		
		//return null;
		
		
	}
	
	public IPropertyValuation3 getPropertyValuation3(Long id) {
		DefaultLogger.debug(this, "start getPropertyValuation3 :"+id);
		
		try {
		if(id!=null){
				
			 IPropertyValuation3 iPropertyValuation3 = (IPropertyValuation3)getHibernateTemplate().get(getEntityNamePropertyVal3(), id);
			 return iPropertyValuation3;
			}
			
			DefaultLogger.debug(this, "completed getPropertyValuation3");
		}catch(Exception obe) {
			DefaultLogger.debug(this,"Exception in getPropertyValuation3:"+obe.getMessage());
			obe.printStackTrace();
		}
		return null;
		
		
	}

	
	
}
