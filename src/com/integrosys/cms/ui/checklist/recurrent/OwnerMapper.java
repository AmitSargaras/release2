/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrent;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.ui.checklist.security.SecurityCheckListForm;

import java.util.HashMap;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 06:08:34 $ Tag: $Name: $
 */

public class OwnerMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public OwnerMapper() {
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		SecurityCheckListForm aForm = (SecurityCheckListForm) cForm;
		String tCollateralID = aForm.getCollateralID();
		long collateralID = Long.parseLong(tCollateralID);
		String tLimitProfileID = aForm.getLimitProfileID();
		long limitProfileID = Long.parseLong(tLimitProfileID);
		DefaultLogger.debug(this, "limitProfileID------->" + limitProfileID);
		DefaultLogger.debug(this, "collateralID----------->" + collateralID);
		ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID);
		DefaultLogger.debug(this, "owner object in Mapper" + owner);
		return owner;
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
		DefaultLogger.debug(this, "inside mapOb to form ");
		SecurityCheckListForm aForm = (SecurityCheckListForm) cForm;
		try {
			if (obj != null) {
				ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) obj;
				ICollateralCheckListOwner owner = (ICollateralCheckListOwner) checkListTrxVal.getStagingCheckList()
						.getCheckListOwner();
				long collateralID = owner.getCollateralID();
				ICollateralProxy cProxy = CollateralProxyFactory.getProxy();
				ICollateral iCol = cProxy.getCollateral(collateralID, false);
				aForm.setCollateralID(String.valueOf(iCol.getCollateralID()));
				aForm.setSecType(iCol.getCollateralType().getTypeCode());
				aForm.setSecSubType(iCol.getCollateralSubType().getSubTypeCode());
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, e);
			throw new MapperException(e.getMessage());
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
