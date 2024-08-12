//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class AssetBasedMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		IAssetBasedCollateral iAsset = (IAssetBasedCollateral) obj;
		AssetBasedForm aForm = (AssetBasedForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (aForm.getEvent().equals(AssetBasedAction.EVENT_DELETE_ITEM)) {
			if (CollateralConstant.LIMIT_CHARGE.equals(aForm.getItemType())) {
				if (aForm.getDeleteItem() != null) {
					String[] id = aForm.getDeleteItem();
					ILimitCharge[] oldList = iAsset.getLimitCharges();
					int numDelete = SecuritySubTypeUtil.getNumberOfDelete(id, oldList.length);
					if (numDelete != 0) {
						ILimitCharge[] newList = new OBLimitCharge[oldList.length - numDelete];
						newList = (ILimitCharge[]) SecuritySubTypeUtil.deleteObjByList(oldList, newList, id);
						iAsset.setLimitCharges(newList);
					}
				}
			}
			else if (CollateralConstant.INS_INFO.equals(aForm.getItemType())) {
				if (aForm.getDeleteInsItem() != null) {
					String[] id = aForm.getDeleteInsItem();
					IInsurancePolicy[] oldList = iAsset.getInsurancePolicies();
					int numDelete = SecuritySubTypeUtil.getNumberOfDelete(id, oldList.length);
					if (numDelete != 0) {
						IInsurancePolicy[] newList = new IInsurancePolicy[oldList.length - numDelete];
						newList = (IInsurancePolicy[]) SecuritySubTypeUtil.deleteObjByList(oldList, newList, id);
						iAsset.setInsurancePolicies(newList);
					}
				}
			}
		}
		return iAsset;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		IAssetBasedCollateral iObj = (IAssetBasedCollateral) obj;
		AssetBasedForm aForm = (AssetBasedForm) cForm;

		aForm.setDeleteItem(new String[0]);
		aForm.setDeleteInsItem(new String[0]);

		return aForm;
	}

}
