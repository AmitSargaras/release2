//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBPostDatedCheque;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;

public class AssetPostDatedChqsMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DefaultLogger.debug(
				"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsMapperHelper",
				"inside mapFormToOB");

		IAssetPostDatedCheque iAsset = (IAssetPostDatedCheque) obj;
		AssetPostDatedChqsForm aForm = (AssetPostDatedChqsForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		if (aForm.getEvent().equals(AssetPostDatedChqsAction.EVENT_DELETE_ITEM)) {
			if (aForm.getDeleteItem() != null) {
				String[] id = aForm.getDeleteItem();
				IPostDatedCheque[] oldList = iAsset.getPostDatedCheques();
				if (id.length <= oldList.length) {
					int numDelete = 0;
					for (int i = 0; i < id.length; i++) {
						if (Integer.parseInt(id[i]) < oldList.length) {
							numDelete++;
						}
					}
					if (numDelete != 0) {
						IPostDatedCheque[] newList = new OBPostDatedCheque[oldList.length - id.length];
						int i = 0, j = 0;
						DefaultLogger.debug("AssetPostDatedChqsMapperHelper", "id length: " + id.length);
						while (i < oldList.length) {
							if ((j < id.length) && (Integer.parseInt(id[j]) == i)) {
								j++;
							}
							else {
								newList[i - j] = oldList[i];
							}
							i++;
						}
						iAsset.setPostDatedCheques(newList);
					}
				}
			}
		}
		
		iAsset.setChequeDate(DateUtil.convertDate(locale, aForm.getChequeDate()));
		iAsset.setChequeRefNumber(aForm.getChequeRefNumber());
		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInterestRate())){
			iAsset.setInterestRate(Double.parseDouble(aForm.getInterestRate()));
		} 	
		else{
			iAsset.setInterestRate(ICMSConstant.DOUBLE_INVALID_VALUE);
		} 
		iAsset.setPriCaveatGuaranteeDate(DateUtil.convertDate(locale, aForm.getPriCaveatGuaranteeDate()));
		
		iAsset.setRemarks(aForm.getRemarks());
		
		return iAsset;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		IAssetPostDatedCheque iAsset = (IAssetPostDatedCheque) obj;
		AssetPostDatedChqsForm aForm = (AssetPostDatedChqsForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		DefaultLogger.debug(
				"com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs.AssetPostDatedChqsMapperHelper",
				"inside mapOBToForm");

		aForm.setDeleteItem(new String[0]);
		
		aForm.setChequeDate(DateUtil.formatDate(locale, iAsset.getChequeDate()));
		aForm.setChequeRefNumber(iAsset.getChequeRefNumber());
		if (iAsset.getInterestRate()!=ICMSConstant.DOUBLE_INVALID_VALUE) 
			aForm.setInterestRate(String.valueOf(iAsset.getInterestRate()));
		else aForm.setInterestRate("");
		aForm.setPriCaveatGuaranteeDate(DateUtil.formatDate(locale, iAsset.getPriCaveatGuaranteeDate()));
		
		aForm.setRemarks(iAsset.getRemarks());
		
		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IAssetPostDatedCheque) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
		 
	}

}
