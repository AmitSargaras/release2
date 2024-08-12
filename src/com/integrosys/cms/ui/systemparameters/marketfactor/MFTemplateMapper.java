/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplateSecSubType;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.OBMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.OBMFTemplateSecSubType;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MFTemplateMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "MFTemplateTrxObj", "com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		String event = (String) (inputs.get("event"));
		IMFTemplateTrxValue MFTemplateTrxObj = (IMFTemplateTrxValue) (inputs.get("MFTemplateTrxObj"));

		IMFTemplate templateObj = (IMFTemplate) obj;
		MFTemplateForm templateForm = (MFTemplateForm) commonForm;
		if (templateObj.getMFTemplateID() != ICMSConstant.LONG_INVALID_VALUE) {
			templateForm.setMFTemplateID(String.valueOf(templateObj.getMFTemplateID()));
		}

		templateForm.setMFTemplateName(templateObj.getMFTemplateName());
		if (templateObj.getMFTemplateStatus() == null) {
			templateForm.setMFTemplateStatus(ICMSConstant.MF_STATUS_ACTIVE);
		}
		else {
			templateForm.setMFTemplateStatus(templateObj.getMFTemplateStatus());
		}
		// templateForm.setSecType(templateObj.getSecurityTypeID());
		templateForm.setSecType(ICMSConstant.SECURITY_TYPE_PROPERTY);

		IMFTemplateSecSubType[] subTypeList = templateObj.getSecuritySubTypeList();
		if ((subTypeList != null) && (subTypeList.length != 0)) {
			String[] secSubTypeCode = new String[subTypeList.length];
			for (int i = 0; i < subTypeList.length; i++) {
				IMFTemplateSecSubType subtype = subTypeList[i];
				secSubTypeCode[i] = subtype.getSecSubTypeID();

				// System.out.println("mapOBToForm,setSecSubTypeID="+
				// secSubTypeCode[i]);
			}
			templateForm.setSecSubType(secSubTypeCode);
		}
		else {
			templateForm.setSecSubType(new String[0]);
		}

		if (!EventConstant.EVENT_PROCESS.equals(event) && !EventConstant.EVENT_PROCESS_RETURN.equals(event)) {

			IMFItem[] lmtRef = templateObj.getMFItemList();
			if ((lmtRef != null) && (lmtRef.length != 0)) {
				Arrays.sort(lmtRef);

				if (templateObj.getMFItemList() != null) {
					templateForm.setMFItemList(Arrays.asList(templateObj.getMFItemList()));
				}
				else {
					templateForm.setMFItemList(new ArrayList());
				}
			}

		}
		else {
			renderCompareItem(MFTemplateTrxObj.getMFTemplate(), MFTemplateTrxObj.getStagingMFTemplate(), templateForm);
		}

		templateForm.setDeletedItemList(new String[0]);

		setChangedInd(event, MFTemplateTrxObj.getMFTemplate(), MFTemplateTrxObj.getStagingMFTemplate(), templateForm);

		return templateForm;

	}

	private static void renderCompareItem(IMFTemplate origTemplate, IMFTemplate stagingTemplate,
			MFTemplateForm templateForm) {
		IMFItem[] oldLmtRef = null;
		IMFItem[] newLmtRef = null;
		if (origTemplate != null) {
			oldLmtRef = origTemplate.getMFItemList();
		}
		if (stagingTemplate != null) {
			newLmtRef = stagingTemplate.getMFItemList();
		}
		if (oldLmtRef == null) {
			oldLmtRef = new IMFItem[0];
		}

		if (newLmtRef == null) {
			newLmtRef = new IMFItem[0];
		}

		List compareList = CompareOBUtil.compOBArray(newLmtRef, oldLmtRef);
		Collections.sort(compareList, new SortCompareMFItem());

		templateForm.setMFItemList(compareList);
	}

	public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
		String event = (String) (inputs.get("event"));

		IMFTemplate templateObj = null;
		if (EventConstant.EVENT_CREATE.equals(event)) {
			templateObj = new OBMFTemplate();
		}
		else {
			IMFTemplateTrxValue trxValue = (IMFTemplateTrxValue) (inputs.get("MFTemplateTrxObj"));
			templateObj = trxValue.getStagingMFTemplate();
		}
		if (templateObj == null) {
			templateObj = new OBMFTemplate();
		}
		MFTemplateForm templateForm = (MFTemplateForm) commonForm;

		// templateObj.setMFTemplateID(String.valueOf(templateForm.
		// getMFTemplateID()));
		templateObj.setMFTemplateName(templateForm.getMFTemplateName());
		templateObj.setMFTemplateStatus(templateForm.getMFTemplateStatus());
		templateObj.setSecurityTypeID(templateForm.getSecType());

		String[] subTypeList = templateForm.getSecSubType();
		if ((subTypeList != null) && (subTypeList.length != 0)) {
			IMFTemplateSecSubType[] secSubType = new IMFTemplateSecSubType[subTypeList.length];
			for (int i = 0; i < subTypeList.length; i++) {
				String code = subTypeList[i];
				IMFTemplateSecSubType subtype = new OBMFTemplateSecSubType();
				subtype.setSecSubTypeID(code);
				// System.out.println("mapFormToOB,setSecSubTypeID="+code);
				secSubType[i] = subtype;
			}

			templateObj.setSecuritySubTypeList(secSubType);
		}
		else {
			templateObj.setSecuritySubTypeList(null);
		}

		templateObj.setLastUpdateDate(UIUtil.getDate());
		if (EventConstant.EVENT_DELETE_ITEM.equals(event)) {
			deleteItem(templateObj, templateForm);
		}
		return templateObj;

	}

	private void deleteItem(IMFTemplate templateObj, MFTemplateForm templateForm) {
		MFTemplateUIHelper helper = new MFTemplateUIHelper();
		IMFItem[] item = templateObj.getMFItemList();
		String[] deletedInd = templateForm.getDeletedItemList();
		if ((item != null) && (deletedInd != null) && (deletedInd.length > 0)) {
			List tempList = new ArrayList();
			for (int i = 0; i < item.length; i++) {
				tempList.add(item[i]);
			}
			List res = helper.deleteItem(tempList, deletedInd);
			templateObj.setMFItemList((IMFItem[]) (res.toArray(new IMFItem[0])));
		}
	}

	// for checker process submitted templateObj, we need to found what has been
	// updated and
	// display different color
	private void setChangedInd(String event, IMFTemplate origTemplate, IMFTemplate stagingTemplate,
			MFTemplateForm templateForm) {
		try {
			if (origTemplate == null) {
				origTemplate = new OBMFTemplate();
			}
			if (!EventConstant.EVENT_PROCESS.equals(event) && !EventConstant.EVENT_PROCESS_RETURN.equals(event)) {
				return;
			}
			if (!CompareOBUtil.compOB(stagingTemplate, origTemplate, "MFTemplateName")) {
				templateForm.setMFTemplateNameClass("fieldnamediff");
			}
			if (!CompareOBUtil.compOB(stagingTemplate, origTemplate, "MFTemplateStatus")) {
				templateForm.setMFTemplateStatusClass("fieldnamediff");
			}
			boolean same = isEqual(stagingTemplate.getSecuritySubTypeList(), origTemplate.getSecuritySubTypeList());

			if (!same) {
				templateForm.setSecSubTypeClass("fieldnamediff");
			}
			// if (!CompareOBUtil.compOB(stagingTemplate, origTemplate,
			// "SecTypeID"))
			// {
			// templateForm.setSecTypeClass("fieldnamediff");
			// }
			if (!CompareOBUtil.compOB(stagingTemplate, origTemplate, "SecurityTypeID")) {
				templateForm.setSecTypeClass("fieldnamediff");
			}

			if (!CompareOBUtil.compOB(stagingTemplate, origTemplate, "SecuritySubTypeList")) {
				// System.out.println("compOB for sec subtype is different");
				// templateForm.setSecTypeClass("fieldnamediff");
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean isEqual(IMFTemplateSecSubType[] stg, IMFTemplateSecSubType[] bus) {
		if ((stg == null) || (bus == null)) {
			return false;
		}
		if (stg.length != bus.length) {
			return false;
		}
		else {
			boolean isEqual = false;
			for (int i = 0; i < stg.length; i++) {
				isEqual = false;
				for (int j = 0; j < bus.length; j++) {
					if ((stg[i] != null) && (bus[j] != null)) {
						if (stg[i].getSecSubTypeID().equals(bus[j].getSecSubTypeID())) {
							isEqual = true;
							break;
						}
					}

				}
				if (!isEqual) {
					return false;
				}
				// else{
				// break;
				// }
			}
		}
		return true;
	}

	public static class SortCompareMFItem implements Comparator {
		public int compare(Object a, Object b) {
			int retValue = 0;
			if ((a != null) && (b != null)) {
				IMFItem obj1 = (IMFItem) ((CompareResult) a).getObj();
				IMFItem obj2 = (IMFItem) ((CompareResult) b).getObj();

				if ((obj1 != null) && (obj2 != null)) {
					retValue = obj1.getFactorDescription().compareTo(obj2.getFactorDescription());
					if (retValue != 0) {
						return retValue;
					}
				}
			}
			return retValue;
		}
	}
}
