/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MFItemMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "MFTemplateTrxObj", "com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue",
						SERVICE_SCOPE },
				{ "curItem", "com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem", SERVICE_SCOPE }, });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
		try {
			MFTemplateUIHelper helper = new MFTemplateUIHelper();
			IMFItem item = (IMFItem) obj;
			MFItemForm itemForm = (MFItemForm) commonForm;

			itemForm.setFactorDescription(item.getFactorDescription());
			itemForm.setWeightPercentage(String.valueOf(item.getWeightPercentage()));

			return itemForm;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapFormToOB(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
		// TODO Auto-generated method stub
		String event = (String) (inputs.get("event"));
		try {
			MFItemForm itemForm = (MFItemForm) commonForm;
			IMFItem item = (IMFItem) (inputs.get("curItem"));
			if (item != null) {
				item.setFactorDescription(itemForm.getFactorDescription());
				item.setWeightPercentage(Double.parseDouble(itemForm.getWeightPercentage()));
				item.setStatus(ICMSConstant.STATE_ACTIVE);
			}
			return item;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

}
