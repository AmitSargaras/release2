/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ICommonCodeEntriesTrxValue.java
 *
 * Created on February 6, 2007, 11:25 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.trx;

import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * 
 * @author Eric
 */
public interface ICommonCodeEntriesTrxValue extends ICMSTrxValue {

	ICommonCodeEntries[] getCommonCodeEntriesList();

	ICommonCodeEntries getCommonCodeEntries();

	ICommonCodeEntries getStagingCommonCodeEntries();

	void setCommonCodeEntries(ICommonCodeEntries entries);

	void setStagingCommonCodeEntries(ICommonCodeEntries entries);

	void setCommonCodeEntriesList(ICommonCodeEntries[] entries);

	void setCodeDescription(String desc);
	
	void setCodeValue(String value);
	
    String getCodeDescription();
	
	String getCodeValue();

}
