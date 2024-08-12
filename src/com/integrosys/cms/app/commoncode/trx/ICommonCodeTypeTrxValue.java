package com.integrosys.cms.app.commoncode.trx;

import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface ICommonCodeTypeTrxValue extends ICMSTrxValue {

	ICommonCodeType[] getCommonCodeTypeList();

	ICommonCodeType getCommonCodeType();

	ICommonCodeType getStagingCommonCodeType();

	void setCommonCodeType(ICommonCodeType iCommonCodeType);

	void setStagingCommonCodeType(ICommonCodeType iCommonCodeType);

	void setCommonCodeTypeList(ICommonCodeType[] iCommonCodeType);

}
