/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/item/SubLimitItemMapper.java,v 1.4 2006/09/27 02:19:26 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.OBSubLimit;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitMapper.java
 */
public class SubLimitItemMapper extends AbstractCommonMapper {

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommonMapper#mapOBToForm(com
	 * .integrosys.base.uiinfra.common.CommonForm, java.lang.Object,
	 * java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonform, Object obj, HashMap hashmap) throws MapperException {
		DefaultLogger.debug(this, " mapOBToForm - Begin.");
		SubLimitItemForm sliForm = (SubLimitItemForm) commonform;
		ISubLimit obSL = (ISubLimit) obj;
		sliForm.setActiveAmount(obSL.getActiveAmount());
		sliForm.setSubLimitAmount(obSL.getSubLimitAmount());
		sliForm.setSubLimitCCY(obSL.getSubLimitCCY());
		sliForm.setSubLimitType(obSL.getSubLimitType());
		if (obSL.isInnerLimit()) {
			sliForm.setInnerFlag(ICMSConstant.TRUE_VALUE);
		}
		else {
			sliForm.setInnerFlag(ICMSConstant.FALSE_VALUE);
		}
		DefaultLogger.debug(this, " mapOBToForm - End.");
		return sliForm;
	}

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommonMapper#mapFormToOB(com
	 * .integrosys.base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm commonform, HashMap paramMap) throws MapperException {
		DefaultLogger.debug(this, "mapFormToOB - Begin.");
		SubLimitItemForm sliForm = (SubLimitItemForm) commonform;
		ISubLimit obSL = null;
		int index = Integer.parseInt((String) paramMap.get(SLUIConstants.FN_IDX_ID));
		DefaultLogger.debug(this, " Index : " + index);
		if (index == -1) {
			obSL = new OBSubLimit();
		}
		else {
			obSL = (OBSubLimit) cloneToUpdateSL(index, paramMap);
		}
		obSL.setActiveAmount(sliForm.getActiveAmount());
		obSL.setSubLimitAmount(sliForm.getSubLimitAmount());
		obSL.setSubLimitCCY(sliForm.getSubLimitCCY());
		obSL.setSubLimitType(sliForm.getSubLimitType());
		DefaultLogger.debug(this, "in Form : " + sliForm.getInnerFlag());
		obSL.setInnerLimit(ICMSConstant.TRUE_VALUE.equals(sliForm.getInnerFlag()));
		DefaultLogger.debug(this, "set in Obj : " + obSL.isInnerLimit());
		DefaultLogger.debug(this, "in Obj : " + obSL.isInnerLimit());
		DefaultLogger.debug(this, "mapFormToOB - End.");
		return obSL;
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { SLUIConstants.FN_IDX_ID, SLUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLUIConstants.FN_LIMIT_ID, SLUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } });
	}

	private ISubLimit cloneToUpdateSL(int index, HashMap paramMap) {
		String limitId = (String) paramMap.get(SLUIConstants.FN_LIMIT_ID);
		HashMap limitMap = (HashMap) paramMap.get(SLUIConstants.AN_CMDT_LIMIT_MAP);
		ICollateralLimitMap cLimitMap = (ICollateralLimitMap) limitMap.get(limitId);
		ISubLimit[] subLimitArray = cLimitMap.getSubLimit();
		try {
			return (ISubLimit) AccessorUtil.deepClone(subLimitArray[index]);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// private String formatAmtStr(String amtStr) {
	// String formatedStr = "";
	// try {
	// if (isNotEmpty(amtStr)) {
	// DecimalFormat df = new DecimalFormat("###############");
	// formatedStr = df.format(Double.valueOf(amtStr));
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return formatedStr;
	// }
}
