/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/CashMapperHelper.java,v 1.15 2005/08/24 08:39:21 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.cash;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2005/08/24 08:39:21 $ Tag: $Name: $
 */

public class CashMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {

		CashForm colForm = (CashForm) cForm;
		OBCashCollateral iCollateral = (OBCashCollateral) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (colForm.getEvent().equals(CashAction.EVENT_DELETE_ITEM)) {
			if (colForm.getDeleteItem() != null) {
				String[] id = colForm.getDeleteItem();

				ICashDeposit[] oldList = iCollateral.getDepositInfo();
				if (id.length <= oldList.length) {
					int numDelete = 0;
					for (int i = 0; i < id.length; i++) {
						if (Integer.parseInt(id[i]) < oldList.length) {
							numDelete++;
						}
					}
					if (numDelete != 0) {
						ICashDeposit[] newList = new OBCashDeposit[oldList.length - numDelete];
						int i = 0, j = 0;
						DefaultLogger.debug("CashMapperHelper", "id length: " + id.length);
						while (i < oldList.length) {
							if ((j < id.length) && (Integer.parseInt(id[j]) == i)) {
								j++;
							}
							else {
								newList[i - j] = oldList[i];
							}
							i++;
						}
						iCollateral.setDepositInfo(newList);
					}
				}

                //========= cz: not required - only perform valuation when add deposit or update deposit =======//
//                if (ICMSConstant.COLTYPE_CASH_SAMECCY.equals(iCollateral.getCollateralSubType().getSubTypeCode())) {
//					IValuation val = iCollateral.getValuation();
//					if (val == null) {
//						val = new OBValuation();
//					}
//					val.setValuationDate(DateUtil.getDate());
//					iCollateral.setValuation(val);
//				}

            }
		}

		if (!AbstractCommonMapper.isEmptyOrNull(colForm.getInterest())) {
			iCollateral.setIsInterestCapitalisation((Boolean.valueOf(colForm.getInterest())).booleanValue());
		}

		if (!AbstractCommonMapper.isEmptyOrNull(colForm.getPriCaveatGuaranteeDate())) {
			iCollateral.setPriCaveatGuaranteeDate(DateUtil.convertDate(locale,colForm.getPriCaveatGuaranteeDate()));
		}

		iCollateral.setDescription(colForm.getDescription());
		iCollateral.setCreditCardRefNumber(colForm.getCreditCardRefNumber());
		iCollateral.setIssuer(colForm.getIssuer());

		return iCollateral;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		CashForm aForm = (CashForm) cForm;
		OBCashCollateral objICol = (OBCashCollateral) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		aForm.setInterest(String.valueOf(objICol.getIsInterestCapitalisation()));
		aForm.setDeleteItem(new String[0]);
		
		if (objICol.getPriCaveatGuaranteeDate()!=null)
			aForm.setPriCaveatGuaranteeDate(DateUtil.formatDate(locale, objICol.getPriCaveatGuaranteeDate()));
		
		aForm.setDescription(objICol.getDescription());
		aForm.setCreditCardRefNumber(objICol.getCreditCardRefNumber());
		aForm.setIssuer(objICol.getIssuer());
		
		return aForm;

	}

}
