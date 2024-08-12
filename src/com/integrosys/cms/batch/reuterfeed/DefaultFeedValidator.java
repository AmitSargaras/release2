/*
 * Created on Jun 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultFeedValidator implements IFeedValidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedValidator#validateFeed(java.
	 * util.Map, java.util.List)
	 * 
	 * check whether required field is null
	 */
	public boolean validateFeed(Map currentFeedLine, List errorList) throws Exception {
		// TODO Auto-generated method stub
		Iterator iter = currentFeedLine.values().iterator();
		boolean result = true;
		try {
			while (iter.hasNext()) {
				FeedFieldDef nextField = (FeedFieldDef) (iter.next());
				String fieldName = nextField.getFieldName();
				Object fieldValue = nextField.getFieldValue();
				boolean required = nextField.isRequired();
				if (required && FeedUtil.checkDataIsNull(fieldValue)) {
					errorList.add(fieldName + " is required");
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return result;
	}

}
