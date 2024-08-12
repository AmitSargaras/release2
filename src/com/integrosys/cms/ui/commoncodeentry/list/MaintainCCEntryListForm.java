/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntries.java
 *
 * Created on February 5, 2007, 6:02 PM
 *
 * Purpose:
 * Description:
 *
 * @Author: BaoHongMan
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.list;

import java.io.Serializable;
import java.util.Arrays;

import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.ui.common.TrxContextForm;

public class MaintainCCEntryListForm extends TrxContextForm implements Serializable {

	private static final long serialVersionUID = -2663395333759238672L;

	private String entryName[];

	private String entryCode[];
	
	private String codeDescription;
	
	private String codeValue;
	
	private String go="";

	private String country[];

	private String refCategoryCode[];

	private String activeStatus[];

	public String[] getActiveStatus() {
		return activeStatus;
	}

	public String[] getCountry() {
		return country;
	}

	public String[] getEntryCode() {
		return entryCode;
	}

	public String[] getEntryName() {
		return entryName;
	}



	public String[] getRefCategoryCode() {
		return refCategoryCode;
	}


	public void setActiveStatus(String[] activeStatus) {
		this.activeStatus = activeStatus;
	}

	public void setCountry(String[] country) {
		this.country = country;
	}

	public void setEntryCode(String[] entryCode) {
		this.entryCode = entryCode;
	}

	public void setEntryName(String[] entryName) {
		this.entryName = entryName;
	}

	public void setRefCategoryCode(String[] refCategoryCode) {
		this.refCategoryCode = refCategoryCode;
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
	
	public String getGo() {
		return go;
	}

	public void setGo(String go) {
		this.go = go;
	}

	public String[][] getMapper() {
		return new String[][] {
				{ CommonCodeEntryConstant.ENTRY_LIST,
						"com.integrosys.cms.ui.commoncodeentry.list.MaintainCCEntryListMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}
	
	public void reset() {
		this.entryName = null;
		this.entryCode = null;
		this.country = null;
		this.refCategoryCode = null;
		this.activeStatus = null;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("MaintainCCEntryListForm [");
		buf.append("entryCode=");
		buf.append(entryCode != null ? Arrays.asList(entryCode) : null);
		buf.append(", entryName=");
		buf.append(entryName != null ? Arrays.asList(entryName) : null);
		buf.append(", country=");
		buf.append(country != null ? Arrays.asList(country) : null);
		buf.append(", refCategoryCode=");
		buf.append(refCategoryCode != null ? Arrays.asList(refCategoryCode) : null);
		buf.append(", activeStatus=");
		buf.append(activeStatus != null ? Arrays.asList(activeStatus) : null);
		buf.append("]");
		return buf.toString();
	}

}
