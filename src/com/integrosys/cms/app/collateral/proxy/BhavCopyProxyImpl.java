package com.integrosys.cms.app.collateral.proxy;

import com.integrosys.cms.app.collateral.bus.IBhavCopy;
import com.integrosys.cms.app.collateral.bus.IBhavCopyBusManager;
import com.integrosys.cms.ui.collateral.BhavCopyException;

/**
 * This class defines the methods that will be available for
 * getting Bhav Copy Details defined in IBhavCopyProxy
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/03/01 10:03:55 
 */


public class BhavCopyProxyImpl implements IBhavCopyProxy{
	
	IBhavCopyBusManager bhavCopyBusManager;

	/**
	 * @return the bhavCopyBusManager
	 */
	public IBhavCopyBusManager getBhavCopyBusManager() {
		return bhavCopyBusManager;
	}


	/**
	 * @param bhavCopyBusManager the bhavCopyBusManager to set
	 */
	public void setBhavCopyBusManager(IBhavCopyBusManager bhavCopyBusManager) {
		this.bhavCopyBusManager = bhavCopyBusManager;
	}


	/**
	 * @return BhavCopy Object for input scCode
	 */
	public IBhavCopy getBhavCopyDetails(long scCode) throws BhavCopyException{
		//IBhavCopyBusManager bhavCopyBusManager = (IBhavCopyBusManager) BeanHouse.get("bhavCopyBusManager");
		return getBhavCopyBusManager().getBhavCopyDetails(scCode);
	}
	
}
