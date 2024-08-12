/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/insswap/InsSwapMapperHelper.java,v 1.11 2005/09/29 09:41:08 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.insprotection.insswap;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem;
import com.integrosys.cms.app.collateral.bus.type.insurance.OBCDSItem;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditswaps.ICreditDefaultSwaps;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/09/29 09:41:08 $ Tag: $Name: $
 */

public class InsSwapMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		InsSwapForm aForm = (InsSwapForm) cForm;
		ICreditDefaultSwaps iObj = (ICreditDefaultSwaps) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ILimitCharge[] limit = iObj.getLimitCharges();
		ILimitCharge objLimit = null;
		if (limit!=null && limit.length>0) objLimit=limit[0];
		DefaultLogger.debug("InsSwapMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;
		try {
			iObj.setDescription(aForm.getDescription());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateISDAMasterAgmt())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getISDADate(), aForm.getDateISDAMasterAgmt());
				iObj.setISDADate(stageDate);
			}
			else {
				iObj.setISDADate(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateTreasury())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getTreasuryDocDate(), aForm.getDateTreasury());
				iObj.setTreasuryDocDate(stageDate);
			}
			else {
				iObj.setTreasuryDocDate(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getBankRiskAppConfirm())) {
				iObj.setIsBankRiskConfirmation(aForm.getBankRiskAppConfirm());
			}
			iObj.setArrInsurer(aForm.getArrInsurer());
			objLimit.setChargeType(aForm.getChargeType());
			limit[0] = objLimit;
			iObj.setLimitCharges(limit);
		}
		catch (Exception e) {
			DefaultLogger.debug("InsSwapMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		if (aForm.getEvent().equals(InsSwapAction.EVENT_DELETE_ITEM)) {
			if (aForm.getDeleteItem() != null) {
				String[] id = aForm.getDeleteItem();
				ICDSItem[] oldList = iObj.getCdsItems();
				if (id.length <= oldList.length) {
					int numDelete = 0;
					for (int i = 0; i < id.length; i++) {
						if (Integer.parseInt(id[i]) < oldList.length) {
							numDelete++;
						}
					}
					if (numDelete != 0) {
						ICDSItem[] newList = new OBCDSItem[oldList.length - id.length];
						int i = 0, j = 0;
						DefaultLogger.debug("InsSwapMapperHelper", "id length: " + id.length);
						while (i < oldList.length) {
							if ((j < id.length) && (Integer.parseInt(id[j]) == i)) {
								j++;
							}
							else {
								newList[i - j] = oldList[i];
							}
							i++;
						}
						iObj.setCdsItems(newList);
					}
				}
			}
		}
		return iObj;

	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		InsSwapForm aForm = (InsSwapForm) cForm;
		ICreditDefaultSwaps iObj = (ICreditDefaultSwaps) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        ILimitCharge[] limit = iObj.getLimitCharges();
        ILimitCharge objLimit = null;        
        if (limit != null && limit.length > 0) {
            objLimit = limit[0];
        }
		DefaultLogger.debug("InsSwapMapperHelper - mapOBToForm", "Locale is: " + locale);
		try {
			aForm.setDescription(iObj.getDescription());
			aForm.setDateISDAMasterAgmt(DateUtil.formatDate(locale, iObj.getISDADate()));
			aForm.setDateTreasury(DateUtil.formatDate(locale, iObj.getTreasuryDocDate()));
			aForm.setBankRiskAppConfirm(iObj.getIsBankRiskConfirmation());
			aForm.setArrInsurer(iObj.getArrInsurer());
			if (objLimit!=null) aForm.setChargeType(objLimit.getChargeType());
		}
		catch (Exception e) {
			DefaultLogger.debug("InsSwapMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		aForm.setDeleteItem(new String[0]);

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((ICreditDefaultSwaps) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
