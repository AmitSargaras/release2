package com.integrosys.cms.ui.generalparam;

import java.util.Date;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 * This class implements FormBean
 */
public class GeneralParamListForm extends TrxContextForm implements java.io.Serializable {

	public static final String MAPPER = "com.integrosys.cms.ui.generalparam.GeneralParamListMapper";

	private String[] updatedParamValue;

	private String[] chkDeletes;

	private String[] paramNames;
	
	private String[] lastUpdatedDate;

	public String[] getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String[] lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	private String targetOffset = "-1";

	public String[] getParamNames() {
		return paramNames;
	}

	public String[] getChkDeletes() {
		return chkDeletes;
	}
	
	public String[] getUpdatedParamValue() {
		return updatedParamValue;
	}

	public void setUpdatedParamValue(String[] updatedParamValue) {
		this.updatedParamValue = updatedParamValue;
	}

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public String getTargetOffset() {
		return targetOffset;
	}

	public void setParamNames(String[] paramNames) {
		this.paramNames = paramNames;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

}
