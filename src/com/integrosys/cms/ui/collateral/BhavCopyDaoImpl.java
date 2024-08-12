package com.integrosys.cms.ui.collateral;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.IBhavCopy;
import com.integrosys.cms.app.collateral.bus.OBBhavCopy;

/**
 * This class defines the methods that will be available for
 * getting Bhav Copy Details defined in IBhavCopyDao
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/03/01 10:03:55 
 */

public class BhavCopyDaoImpl extends HibernateDaoSupport implements IBhavCopyDao{
	
	/**
	 * @return entity name
	 */
	public String getEntityName(){
		return IBhavCopyDao.ACTUAL_ENTITY_NAME; 
	}
	
	/**
	 * @return BhavCopy Object for input scCode
	 */
	public IBhavCopy getBhavCopyDetails(long scCode) throws BhavCopyException{
		IBhavCopy iBhavCopy = new OBBhavCopy();
		try{
			iBhavCopy = (IBhavCopy)getHibernateTemplate().load(getEntityName(), new Long(scCode));
		}catch (Exception e) {
			DefaultLogger.error(this, "Could not find bhavcopy with id = ["+scCode+"]");
			e.printStackTrace();
			throw new BhavCopyException("Unable to find bhavcopy with id = ["+scCode+"]");
		}	
		
		return iBhavCopy;
	}

}
