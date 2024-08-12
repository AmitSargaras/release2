/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IPropertyType;
import com.integrosys.cms.app.bridgingloan.bus.OBPropertyType;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class PropertyTypeMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public PropertyTypeMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "propertyTypeIndex", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "******************** Inside Map Form to OB (PropertyTypeMapper)");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		DefaultLogger.debug(this, "event=" + event);

		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();

		PropertyTypeForm aForm = (PropertyTypeForm) cForm;
		if (PropertyTypeAction.EVENT_CREATE.equals(event)) {
			IPropertyType[] oldPropertyType = (IPropertyType[]) objBridgingLoan.getPropertyTypeList();
			ArrayList propertyTypeList = new ArrayList();
			try {
				OBPropertyType newPropertyType = new OBPropertyType();
				newPropertyType.setPropertyType(aForm.getPropertyType());
				newPropertyType.setPropertyTypeOthers(aForm.getPropertyTypeOthers());
				if (!aForm.getNoOfUnits().equals("")) {
					newPropertyType.setNoOfUnits(Integer.parseInt(aForm.getNoOfUnits()));
				}
				newPropertyType.setRemarks(aForm.getRemarks());

				if ((oldPropertyType != null) && (oldPropertyType.length != 0)) {
					for (int i = 0; i < oldPropertyType.length; i++) {
						OBPropertyType objPropertyType = (OBPropertyType) oldPropertyType[i];
						propertyTypeList.add(objPropertyType);
					}
				}
				propertyTypeList.add(newPropertyType);
				objBridgingLoan.setPropertyTypeList((IPropertyType[]) propertyTypeList.toArray(new IPropertyType[0]));
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.toString());
			}
			return objBridgingLoan;
		}
		else if (PropertyTypeAction.EVENT_UPDATE.equals(event)) {
			IPropertyType[] newPropertyType = (IPropertyType[]) objBridgingLoan.getPropertyTypeList();
			int propertyTypeIndex = Integer.parseInt((String) map.get("propertyTypeIndex"));

			if (newPropertyType != null) {
				newPropertyType[propertyTypeIndex].setPropertyType(aForm.getPropertyType());
				newPropertyType[propertyTypeIndex].setPropertyTypeOthers(aForm.getPropertyTypeOthers());
				if (!aForm.getNoOfUnits().equals("")) {
					newPropertyType[propertyTypeIndex].setNoOfUnits(Integer.parseInt(aForm.getNoOfUnits()));
				}
				newPropertyType[propertyTypeIndex].setRemarks(aForm.getRemarks());

				objBridgingLoan.setPropertyTypeList(newPropertyType);
				return objBridgingLoan;
			}
		}
		else if (PropertyTypeAction.EVENT_DELETE.equals(event)) {
			ArrayList propertyTypeList = new ArrayList();
			IPropertyType[] oldPropertyType = objBridgingLoan.getPropertyTypeList();
			int propertyTypeIndex = Integer.parseInt((String) map.get("propertyTypeIndex"));

			if ((oldPropertyType != null) && (oldPropertyType.length != 0)) {
				try {
					if ((oldPropertyType != null) && (oldPropertyType.length != 0)) {
						for (int i = 0; i < oldPropertyType.length; i++) {
							OBPropertyType objPropertyType = (OBPropertyType) oldPropertyType[i];
							if (propertyTypeIndex == i) {
								if (objPropertyType.getPropertyTypeID() != ICMSConstant.LONG_INVALID_VALUE) {
									objPropertyType.setIsDeletedInd(true);
								}
								else {
									continue; // If record not available at db,
												// skip adding
								}
							}
							propertyTypeList.add(objPropertyType);
						}
					}
					objBridgingLoan.setPropertyTypeList((IPropertyType[]) propertyTypeList
							.toArray(new IPropertyType[0]));
				}
				catch (Exception e) {
					DefaultLogger.debug(this, e.toString());
				}
			}
			return objBridgingLoan;
		}
		return null;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		try {
			DefaultLogger.debug(this, "******************** inside mapOb to form (PropertyTypeMapper)");
			PropertyTypeForm aForm = (PropertyTypeForm) cForm;

			if (obj != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) obj;
				IPropertyType[] objPropertyTypeList = (IPropertyType[]) objBridgingLoan.getPropertyTypeList();
				int propertyTypeIndex = Integer.parseInt((String) map.get("propertyTypeIndex"));

				if (objPropertyTypeList != null) {
					aForm.setPropertyType(objPropertyTypeList[propertyTypeIndex].getPropertyType());
					aForm.setPropertyTypeOthers(objPropertyTypeList[propertyTypeIndex].getPropertyTypeOthers());
					if (objPropertyTypeList[propertyTypeIndex].getNoOfUnits() != ICMSConstant.INT_INVALID_VALUE) {
						aForm.setNoOfUnits(String.valueOf(objPropertyTypeList[propertyTypeIndex].getNoOfUnits()));
					}
					aForm.setRemarks(objPropertyTypeList[propertyTypeIndex].getRemarks());
				}
			}
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
		}
		return null;
	}
}