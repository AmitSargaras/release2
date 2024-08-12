/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBValuationDetailsBean.java,v 1.1 2005/08/12 03:32:36 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EntityBean;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * For EBStockBean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/12 03:32:36 $ Tag: $Name: $
 */
public abstract class EBValuationDetailsBean implements EntityBean, IValuationDetails {

	public abstract Integer getEBRevalFreq();

	public abstract void setEBRevalFreq(Integer ebRevalFreq);

	public int getRevalFreq() {
		if (getEBRevalFreq() != null) {
			return getEBRevalFreq().intValue();
		}
		return ICMSConstant.INT_INVALID_VALUE;
	}

	public void setRevalFreq(int revalFreq) {
		setEBRevalFreq((revalFreq == ICMSConstant.INT_INVALID_VALUE) ? null : new Integer(revalFreq));
	}
}
