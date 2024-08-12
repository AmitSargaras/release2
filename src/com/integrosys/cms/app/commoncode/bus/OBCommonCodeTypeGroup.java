package com.integrosys.cms.app.commoncode.bus;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class OBCommonCodeTypeGroup implements ICommonCodeTypeGroup {

	OBCommonCodeType[] obCommonCodeTypes;

	OBCommonCodeType obCommonCode;

	public ICommonCodeType[] getCommonCodeTypes() {
		return obCommonCodeTypes;
	}

	public void setObCommonCodeTypes(OBCommonCodeType[] obCommonCodeTypes) {
		this.obCommonCodeTypes = obCommonCodeTypes;
	}

	public OBCommonCodeType getObCommonCode() {
		return obCommonCode;
	}

	public void setObCommonCode(OBCommonCodeType obCommonCode) {
		this.obCommonCode = obCommonCode;
	}
}
