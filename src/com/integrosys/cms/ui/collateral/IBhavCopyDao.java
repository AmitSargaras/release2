package com.integrosys.cms.ui.collateral;

import com.integrosys.cms.app.collateral.bus.IBhavCopy;

/**
 * This interface defines the methods that will be available for
 * getting Bhav Copy Details
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/03/01 10:03:55 
 */

public interface IBhavCopyDao {
	
	public final static String ACTUAL_ENTITY_NAME = "actualBhavCopy";
	
	public IBhavCopy getBhavCopyDetails(long scCode) throws BhavCopyException;
}
