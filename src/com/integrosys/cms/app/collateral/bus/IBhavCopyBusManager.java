package com.integrosys.cms.app.collateral.bus;

import com.integrosys.cms.ui.collateral.BhavCopyException;

/**
 * This interface defines the methods that will be available for
 * getting Bhav Copy Details
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/03/01 10:03:55 
 */

public interface IBhavCopyBusManager {
	public IBhavCopy getBhavCopyDetails(long scCode)throws BhavCopyException;
}
