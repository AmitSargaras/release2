/**

 * Copyright Integro Technologies Pte Ltd

 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/MaintainSystemParametersForm.java,v 1.3 2003/09/17 08:36:56 pooja Exp $

 */

package com.integrosys.cms.ui.commoncode;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.component.commondata.app.Constants;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 * @since $Id$
 */

public class MaintainCommonCodeTypeForm extends TrxContextForm implements Serializable {

	long categoryId;

	String categoryCode = "";

	String categoryName = "";

	String categoryStatus = "";

	String categoryVersionTime = "";

	String[] parameterCodes;

	String[] parameterValues;

	String[] parameterNames;

	public String[] getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(String[] parameterNames) {
		this.parameterNames = parameterNames;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryCode() {

		return categoryCode;

	}

	public void setCategoryCode(String categoryCode) {

		this.categoryCode = categoryCode;

	}

	public String[] getParameterCodes() {

		return parameterCodes;

	}

	public void setParameterCodes(String[] parameterCodes) {

		this.parameterCodes = parameterCodes;

	}

	public String[] getParameterValues() {

		return parameterValues;

	}

	public void setParameterValues(String[] parameterValues) {

		this.parameterValues = parameterValues;

	}

	public String getCategoryName() {

		return categoryName;

	}

	public void setCategoryName(String categoryName) {

		this.categoryName = categoryName;

	}

	public String getCategoryStatus() {

		return categoryStatus;

	}

	public void setCategoryStatus(String categoryStatus) {

		if (categoryStatus == null) {
			categoryStatus = Constants.STATUS_INACTIVE;
		}
		this.categoryStatus = categoryStatus;

	}

	public String getCategoryVersionTime() {

		return categoryVersionTime;

	}

	public void setCategoryVersionTime(String categoryVersionTime) {

		this.categoryVersionTime = categoryVersionTime;

	}

	public void reset() {

	}

	public String[][] getMapper() {

		String[][] input = {

		{ "OBCommonCodeTypeTrxValue", "com.integrosys.cms.ui.commoncode.MaintainCommonCodeTypeMapper" },
				{ "commonCodeType", "com.integrosys.cms.ui.commoncode.MaintainCommonCodeTypeMapper" },
				{ "stgCommonCodeType", "com.integrosys.cms.ui.commoncode.StagingCommonCodeTypeMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		};

		return input;

	}

}
