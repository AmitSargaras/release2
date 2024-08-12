/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralDetailFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.manualinput.security.proxy.SBMISecProxy;
import com.integrosys.cms.app.manualinput.security.proxy.SBMISecProxyHome;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MISecurityUIHelper {
	public SBMISecProxy getSBMISecProxy() {
		return (SBMISecProxy) (BeanController.getEJB(ICMSJNDIConstant.SB_MI_SEC_PROXY_JNDI, SBMISecProxyHome.class
				.getName()));
	}

	public List deleteItem(List itemOrig, String[] deleteInd) {
		if ((itemOrig != null) && (deleteInd != null)) {
			List tempList = new ArrayList();
			for (int i = 0; i < deleteInd.length; i++) {
				int nextDelInd = Integer.parseInt(deleteInd[i]);
				itemOrig.remove(nextDelInd);
				// tempList.add(itemOrig.get(nextDelInd));
			}
			// tempList.clear();
		}
		return itemOrig;
	}

	public String getOrigBookingCtryDesc(String origBookingCtry) {
		return CountryList.getInstance().getCountryName(origBookingCtry);
	}

	public String getOrigBookingLocDesc(String origBookingCtry, String origBookingLoc) {
		try {
			return CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.CATEGORY_CODE_BKGLOC, null,
					origBookingCtry, origBookingLoc);
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getSecurityTypeDesc(String typeCode) {
		try {
			return SecurityTypeList.getInstance().getSecurityTypeValue(typeCode, null);
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getSecuritySubTypeDesc(String subtypeCode) {
		try {
			return SecuritySubTypeList.getInstance().getSecuritySubTypeValue(subtypeCode, null);
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getLeSystemDesc(String systemCode) {
		try {
			return CommonDataSingleton.getCodeCategoryLabelByValue("LE_ID_TYPE", systemCode);
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getPledgorRelationshipDesc(String value) {
		return CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.CATEGORY_CODE_PLEDGOR_RELNSHIP, value);
	}

	public String getIDTypeDesc(String value) {
		return CommonDataSingleton.getCodeCategoryLabelByValue("ID_TYPE", value);
	}

	public ICollateral getCollateralBySubtype(ICollateral colOld) throws CollateralException {
		ICollateral newCol = CollateralDetailFactory.getOB(colOld.getCollateralSubType());
		AccessorUtil.copyValue(colOld, newCol);
		return newCol;
	}

	public void setTrxLocation(OBTrxContext ctx, ICollateral col) {
		ctx.setTrxCountryOrigin(col.getCollateralLocation());
		ctx.setTrxOrganisationOrigin(col.getSecurityOrganization());
	}

	public void setPledgorLocation(ICollateral col) {
		ICollateralPledgor[] pledgors = col.getPledgors();
		if (pledgors != null) {
			for (int i = 0; i < pledgors.length; i++) {
				if (pledgors[i].getBookingLocation() == null) {
					IBookingLocation loc = new OBBookingLocation();
					loc.setCountryCode(col.getCollateralLocation());
					loc.setOrganisationCode(col.getSecurityOrganization());
					pledgors[i].setBookingLocation(loc);
				}
			}
		}
	}
}
