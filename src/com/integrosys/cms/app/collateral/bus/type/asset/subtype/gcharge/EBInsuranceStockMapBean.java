/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBInsuranceStockMapBean.java,v 1.1 2005/03/17 07:06:15 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for asset of type charge.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/17 07:06:15 $ Tag: $Name: $
 */
public abstract class EBInsuranceStockMapBean extends EBGenChargeMapEntryBean {
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_INS_STOCK_MAP;
	}

    public abstract long getRefID();

    public abstract void setRefID(long refID);

    public abstract String getInsuranceID();

    public abstract void setInsuranceID(String insuranceID);

    public abstract String getEntryValueID();

    public abstract void setEntryValueID(String entryValueID);

    public abstract String getStatus();
}