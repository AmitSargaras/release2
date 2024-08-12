/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/approvedcommodity/ApprovedCommForm.java,v 1.2 2004/06/04 05:22:08 hltan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.approvedcommodity;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:22:08 $ Tag: $Name: $
 */

public class ApprovedCommForm extends CommonForm implements Serializable {
	private String securityID = "";

	private String securitySubType = "";

	private String productType = "";

	private String productSubType = "";

	public String getSecurityID() {
		return this.securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public String getSecuritySubType() {
		return this.securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductSubType() {
		return this.productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public String[][] getMapper() {
		String[][] input = { { "approvedCommObj",
				"com.integrosys.cms.ui.collateral.commodity.approvedcommodity.ApprovedCommMapper" }, };
		return input;
	}
}
