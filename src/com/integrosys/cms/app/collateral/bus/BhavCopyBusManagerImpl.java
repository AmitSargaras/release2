package com.integrosys.cms.app.collateral.bus;

import com.integrosys.cms.ui.collateral.BhavCopyException;
import com.integrosys.cms.ui.collateral.IBhavCopyDao;

/**
 * This class defines the methods that will be available for
 * getting Bhav Copy Details defined in IBhavCopyBusManager
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/03/01 10:03:55 
 */

public class BhavCopyBusManagerImpl implements IBhavCopyBusManager{
	
	IBhavCopyDao bhavCopyDao;
	
	
	/**
	 * @return the bhavCopyDao
	 */
	public IBhavCopyDao getBhavCopyDao() {
		return bhavCopyDao;
	}


	/**
	 * @param bhavCopyDao the bhavCopyDao to set
	 */
	public void setBhavCopyDao(IBhavCopyDao bhavCopyDao) {
		this.bhavCopyDao = bhavCopyDao;
	}


	/**
	 * @return BhavCopy Object for input scCode
	 */
	public IBhavCopy getBhavCopyDetails(long scCode)throws BhavCopyException{
		//IBhavCopyDao bhavCopyDao = (IBhavCopyDao) BeanHouse.get("bhavCopyDao");
		return getBhavCopyDao().getBhavCopyDetails(scCode);
	}

}
