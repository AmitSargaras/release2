/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/global/SRPGlobalForm.java,v 1.2 2003/08/21 08:37:47
sathish Exp $
 */

package com.integrosys.cms.ui.srp.global;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * 
 * ActionForm for SRPGlobalAction.
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/21 08:37:47 $ Tag: $Name: $
 */

public class SRPGlobalForm extends TrxContextForm implements Serializable {

	private String securityTypeCode = "";

	private String subTypeCodes[];

	private String subTypeDescs[];

	private String subTypeStandardisedApproach[];

	private String subTypeFoundationIRB[];

	private String subTypeAdvancedIRB[];

	private String subTypeNames[];

	private String maxValues[];

	public String[] getSubTypeCodes() {
		return subTypeCodes;
	}

	public void setSubTypeCodes(String[] subTypeCodes) {
		this.subTypeCodes = subTypeCodes;
	}

	public String[] getSubTypeDescs() {
		return subTypeDescs;
	}

	public void setSubTypeDescs(String[] subTypeDescs) {
		this.subTypeDescs = subTypeDescs;
	}

	public String[] getSubTypeStandardisedApproach() {
		return subTypeStandardisedApproach;
	}

	public void setSubTypeStandardisedApproach(String[] subTypeStandardisedApproach) {
		this.subTypeStandardisedApproach = subTypeStandardisedApproach;
	}

	public String[] getSubTypeFoundationIRB() {
		return subTypeFoundationIRB;
	}

	public void setSubTypeFoundationIRB(String[] subTypeFoundationIRB) {
		this.subTypeFoundationIRB = subTypeFoundationIRB;
	}

	public String[] getSubTypeAdvancedIRB() {
		return subTypeAdvancedIRB;
	}

	public void setSubTypeAdvancedIRB(String[] subTypeAdvancedIRB) {
		this.subTypeAdvancedIRB = subTypeAdvancedIRB;
	}

	public String[] getSubTypeNames() {
		return subTypeNames;
	}

	public void setSubTypeNames(String[] subTypeNames) {
		this.subTypeNames = subTypeNames;
	}

	public String[] getMaxValues() {
		return maxValues;
	}

	public void setMaxValues(String[] maxValues) {
		this.maxValues = maxValues;
	}

	public String getSecurityTypeCode() {
		return securityTypeCode;
	}

	public void setSecurityTypeCode(String securityTypeCode) {
		this.securityTypeCode = securityTypeCode;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String[][] getMapper() {
		String[][] input = { { "InitialCollateralSubType", "com.integrosys.cms.ui.srp.global.SRPGlobalMapper" },
				{ "CollateralSubTypes", "com.integrosys.cms.ui.srp.global.SRPGlobalMapper" },
				{ "CollateralSubTypeTrxValue", "com.integrosys.cms.ui.srp.global.SRPGlobalMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "mapper", "com.integrosys.cms.ui.srp.global.SRPGlobalMapper" } };
		return input;
	}
}
