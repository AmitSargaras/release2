package com.integrosys.cms.app.segmentWiseEmail.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.IProductMasterDao;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;

public class SegmentWiseEmailDaoImpl extends HibernateDaoSupport implements ISegmentWiseEmailDao{

	

	
	/**
	 * @return SegmentWiseEmail Object
	 * @param Entity Name
	 * @param SegmentWiseEmail Object  
	 * This method Creates SegmentWiseEmail Object
	 */
	public ISegmentWiseEmail createSegmentWiseEmail(String entityName,
			ISegmentWiseEmail segmentWiseEmail)
			throws SegmentWiseEmailException {
		if(!(entityName==null|| segmentWiseEmail==null)){
			
			segmentWiseEmail.setEmail("");
			
			Long key = (Long) getHibernateTemplate().save(entityName, segmentWiseEmail);
			segmentWiseEmail.setID(key.longValue());
			
			return segmentWiseEmail;
			}else{
				throw new SegmentWiseEmailException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	  * @return Particular SegmentWiseEmail according 
	  * to the id passed as parameter.  
	  * 
	  */

	public ISegmentWiseEmail getSegmentWiseEmail(String entityName, Serializable key)throws SegmentWiseEmailException {
		
		if(!(entityName==null|| key==null)){
		
		return (ISegmentWiseEmail) getHibernateTemplate().get(entityName, key);
		}else{
			throw new SegmentWiseEmailException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return SegmentWiseEmail Object
	 * @param Entity Name
	 * @param SegmentWiseEmail Object  
	 * This method Updates SegmentWiseEmail Object
	 */
	
	public ISegmentWiseEmail updateSegmentWiseEmail(String entityName, ISegmentWiseEmail item)throws SegmentWiseEmailException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ISegmentWiseEmail) getHibernateTemplate().load(entityName, new Long(item.getID()));
		}else{
			throw new SegmentWiseEmailException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	
	/**
	 * @return SegmentWiseEmail Object
	 * @param Entity Name
	 * @param SegmentWiseEmail Object  
	 * This method Creates Inactive SegmentWiseEmail Object
	 */
	public ISegmentWiseEmail deleteSegmentWiseEmail(String entityName, ISegmentWiseEmail segmentWiseEmail)
			throws SegmentWiseEmailException {
		if (!(entityName == null || segmentWiseEmail == null)) {
			Long key = (Long) getHibernateTemplate().save(entityName, segmentWiseEmail);
			segmentWiseEmail.setID(key.longValue());
			return segmentWiseEmail;
		} else {
			throw new SegmentWiseEmailException("ERROR- Entity name or key is null ");
		}
	}
}
