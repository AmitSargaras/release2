/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * OBCommonCodeEntriesTrxValue.java
 *
 * Created on February 6, 2007, 11:27 AM
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

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * 
 * @author Eric
 */
public class OBCommonCodeEntriesTrxValue extends OBCMSTrxValue implements ICommonCodeEntriesTrxValue {
	private ICommonCodeEntries[] commonCodeEntriesList;

	private ICommonCodeEntries commonCodeEntries;

	private ICommonCodeEntries stagingCommonEntries;
	
	private String  codeDescription;
	
	private String  codeValue;


	public OBCommonCodeEntriesTrxValue() {
		super();
	}

	public OBCommonCodeEntriesTrxValue(ICMSTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	public OBCommonCodeEntriesTrxValue(ICommonCodeEntriesTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	public OBCommonCodeEntriesTrxValue(ITrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	public ICommonCodeEntries[] getCommonCodeEntriesList() {
		return commonCodeEntriesList;
	}

	public void setCommonCodeEntriesList(ICommonCodeEntries[] commonCodeEntriesList) {
		this.commonCodeEntriesList = commonCodeEntriesList;
	}

	public ICommonCodeEntries getCommonCodeEntries() {
		return commonCodeEntries;
	}

	public void setCommonCodeEntries(ICommonCodeEntries commonCodeEntries) {
		this.commonCodeEntries = commonCodeEntries;
	}

	public ICommonCodeEntries getStagingCommonCodeEntries() {
		return stagingCommonEntries;
	}

	public void setStagingCommonCodeEntries(ICommonCodeEntries stagingCommonEntries) {
		this.stagingCommonEntries = stagingCommonEntries;
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

}
