/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/documentlocation/DocumentLocationForm.java,v 1.6 2004/04/07 03:05:43 hltan Exp $
 */
package com.integrosys.cms.ui.documentlocation;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/04/07 03:05:43 $ Tag: $Name: $
 */

public class DocumentLocationForm extends TrxContextForm implements Serializable {

	private String customerCategory;

	private String leId;

	private String legalName;

	private String customerType;

	private String docOriginateLoc;

	private String orgCode;

	private String docRemarks;

	public String getCustomerCategory() {
		return this.customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public String getLeId() {
		return this.leId;
	}

	public void setLeId(String leId) {
		this.leId = leId;
	}

	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getDocOriginateLoc() {
		return this.docOriginateLoc;
	}

	public void setDocOriginateLoc(String docOriginateLoc) {
		this.docOriginateLoc = docOriginateLoc;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDocRemarks() {
		return this.docRemarks;
	}

	public void setDocRemarks(String docRemarks) {
		this.docRemarks = docRemarks;
	}

	public String[][] getMapper() {

		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "docLocationObj", "com.integrosys.cms.ui.documentlocation.DocumentLocationMapper" }, };
		return input;
	}
}
