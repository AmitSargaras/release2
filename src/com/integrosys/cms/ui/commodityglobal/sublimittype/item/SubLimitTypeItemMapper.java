/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/item/SubLimitTypeItemMapper.java,v 1.1 2005/10/06 06:03:37 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.OBSubLimitType;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-19
 * @Tag com.integrosys.cms.ui.commodityglobal.sublimittype.item.
 *      SubLimitTypeItemMapper.java
 */
public class SubLimitTypeItemMapper extends AbstractCommonMapper {

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommonMapper#mapOBToForm(com
	 * .integrosys.base.uiinfra.common.CommonForm, java.lang.Object,
	 * java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonform, Object obj, HashMap hashmap) throws MapperException {
		DefaultLogger.debug(this, " mapOBToForm - Begin.");
		SubLimitTypeItemForm sltForm = (SubLimitTypeItemForm) commonform;
		ISubLimitType obSLT = (ISubLimitType) obj;
		sltForm.setSubLimitType(obSLT.getSubLimitType());
		sltForm.setLimitType(obSLT.getLimitType());
		DefaultLogger.debug(this, "LimitType : " + sltForm.getLimitType());
		DefaultLogger.debug(this, "SubLimitType : " + sltForm.getSubLimitType());
		DefaultLogger.debug(this, " mapOBToForm - End.");
		return sltForm;
	}

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommonMapper#mapFormToOB(com
	 * .integrosys.base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm commonform, HashMap paramMap) throws MapperException {
		DefaultLogger.debug(this, "mapFormToOB - Begin.");
		SubLimitTypeItemForm sltForm = (SubLimitTypeItemForm) commonform;
		ISubLimitType obSLT = null;
		int index = Integer.parseInt((String) paramMap.get(SLTUIConstants.FN_IDX_ID));
		DefaultLogger.debug(this, " Index : " + index);
		if (index == -1) {
			obSLT = new OBSubLimitType();
		}
		else {
			ISubLimitTypeTrxValue trxValue = (ISubLimitTypeTrxValue) paramMap.get(SLTUIConstants.AN_SLT_TRX_VALUE);
			try {
				obSLT = (OBSubLimitType) AccessorUtil.deepClone(trxValue.getStagingSubLimitTypes()[index]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		obSLT.setLimitType(sltForm.getLimitType().trim());
		obSLT.setSubLimitType(sltForm.getSubLimitType().trim());
		DefaultLogger.debug(this, "mapFormToOB - End.");
		return obSLT;
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { SLTUIConstants.FN_IDX_ID, SLTUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLTUIConstants.AN_SLT_TRX_VALUE, SLTUIConstants.CN_I_SLT_TRX_VALUE, SERVICE_SCOPE } });
	}
}
