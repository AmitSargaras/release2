package com.integrosys.cms.host.eai.covenant.handler;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.host.eai.covenant.bus.CovenantItem;

/**
 * 
 * @author Thurein
 * @since 20-Nov-2008
 */
public class CovenantRepublishHandlerHelper extends CovenantHandlerHelper {

	/**
	 * This will return a covenantItem that has the recurrentDocID equivalent to
	 * the variable parse in.
	 * @param covItems Vector of all the new incoming covenant items
	 * @param existingCovID existing CMSConvenantID 
	 */
	public CovenantItem getCovenantItem(Vector covItems, long existingCovID) {

		Iterator i = covItems.iterator();
		while (i.hasNext()) {
			CovenantItem covenantItem = (CovenantItem) i.next();
			
			if (covenantItem.getCMSCovenantItemID() == null) {
				continue;
			}
			
			if ((covenantItem.getCMSCovenantItemID().longValue() == existingCovID)) {
				return covenantItem;
			}
		}
		
		return null;
	}

}
