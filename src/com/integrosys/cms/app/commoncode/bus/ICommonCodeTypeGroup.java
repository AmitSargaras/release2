package com.integrosys.cms.app.commoncode.bus;

import java.io.Serializable;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface ICommonCodeTypeGroup extends Serializable {

	ICommonCodeType[] getCommonCodeTypes();

	OBCommonCodeType getObCommonCode();
}
