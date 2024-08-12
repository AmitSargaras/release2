/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/IConversionKey.java,v 1.1 2004/06/15 06:18:59 wltan Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.io.Serializable;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/15 06:18:59 $ Tag: $Name: $
 */
public interface IConversionKey extends Serializable {
	public String getFromUnit();

	public String getToUnit();
}
