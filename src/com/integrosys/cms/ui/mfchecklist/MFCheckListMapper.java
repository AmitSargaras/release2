package com.integrosys.cms.ui.mfchecklist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklistItem;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.OBMFChecklist;
import com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MFCheckListMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "MFChecklistTrxObj", "com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		MFCheckListForm aForm = (MFCheckListForm) cForm;
		IMFChecklist checklist = null;

		IMFChecklistTrxValue MFChecklistTrxObj = (IMFChecklistTrxValue) (inputs.get("MFChecklistTrxObj"));
		if (MFChecklistTrxObj != null) {
			checklist = MFChecklistTrxObj.getStagingMFChecklist();
		}
		else {
			checklist = new OBMFChecklist();
		}
		checklist.setCollateralID(Long.parseLong(aForm.getColCollateralID()));

		if (aForm.getMFTemplateID().length() > 0) {
			checklist.setMFTemplateID(Long.parseLong(aForm.getMFTemplateID()));
		}
		checklist.setMFTemplateName(aForm.getMFTemplateName());
		checklist.setLastUpdateDate(DateUtil.convertDate(locale, aForm.getLastUpdateDate()));

		if (checklist.getMFChecklistItemList() != null) {

			List itemList = Arrays.asList(checklist.getMFChecklistItemList());
			List itemListTmp = new ArrayList();
			String[] valuerAssignFactorList = aForm.getValuerAssignFactorList();

			if ((valuerAssignFactorList != null) && (valuerAssignFactorList.length > 0)) {

				IMFChecklistItem nextLink;
				double weightScore = 0;
				double total = 0;

				for (int i = 0; i < itemList.size(); i++) {
					nextLink = (IMFChecklistItem) itemList.get(i);
					for (int j = 0; j < valuerAssignFactorList.length; j++) {
						String value = valuerAssignFactorList[j];

						if (i == j) {
							if ((value != null) && (value.length() > 0)) {
								try {
									nextLink.setValuerAssignFactor(Double.parseDouble(value));
									weightScore = nextLink.getWeightPercentage() * Double.parseDouble(value) / 100;
									BigDecimal weightScoreBD = new BigDecimal(weightScore);
									weightScoreBD = weightScoreBD.setScale(2, BigDecimal.ROUND_HALF_UP);
									nextLink.setWeightScore(weightScoreBD.doubleValue());
									total = total + weightScoreBD.doubleValue();
								}
								catch (NumberFormatException ne) {
									// ignore, error will return to UI
									nextLink.setWeightScore(0);
								}

							}
							else {
								nextLink.setValuerAssignFactor(ICMSConstant.DOUBLE_INVALID_VALUE);
								nextLink.setWeightScore(0);
							}
							break;
						} // end i==j

					} // end for
					itemListTmp.add(nextLink);
				}

				checklist.setMFChecklistItemList((IMFChecklistItem[]) itemListTmp.toArray(new IMFChecklistItem[0]));

			}
		}
		return checklist;

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		IMFChecklist checklist = (OBMFChecklist) obj;
		MFCheckListForm aForm = (MFCheckListForm) cForm;
		String event = (String) (inputs.get("event"));

		if (checklist.getMFTemplateID() != ICMSConstant.LONG_INVALID_VALUE) {
			aForm.setMFTemplateID(String.valueOf(checklist.getMFTemplateID()));
		}

		aForm.setMFTemplateName(checklist.getMFTemplateName());
		aForm.setLastUpdateDate(DateUtil.convertToDisplayDate(checklist.getLastUpdateDate()));

		if (checklist.getCollateralID() != ICMSConstant.LONG_INVALID_VALUE) {
			aForm.setColCollateralID(String.valueOf(checklist.getCollateralID()));
		}

		if (checklist.getMFChecklistItemList() != null) {
			aForm.setMFItemList(Arrays.asList(checklist.getMFChecklistItemList()));
		}
		else {
			aForm.setMFItemList(new ArrayList());
		}

		if (!event.equals(MFCheckListAction.EVENT_ERROR_RETURN)) {
			renderItemSummary(checklist, aForm);
		}

		return aForm;
	}

	public static void renderItemSummary(IMFChecklist checklist, MFCheckListForm aForm) {
		IMFChecklistItem[] item = checklist.getMFChecklistItemList();

		if ((item != null) && (item.length > 0)) {
			Arrays.sort(item);

			if (item != null) {
				String[] vList = new String[item.length];
				for (int i = 0; i < item.length; i++) {
					IMFChecklistItem nextLink = item[i];
					if (nextLink.getValuerAssignFactor() != ICMSConstant.DOUBLE_INVALID_VALUE) {
						vList[i] = String.valueOf(nextLink.getValuerAssignFactor());
					}

				}

				aForm.setValuerAssignFactorList(vList);

			}
		}

	}

}
