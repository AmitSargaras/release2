package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

/**
 * This class represents the OBGenChargeMapEntry summary for the general charge
 * asset. It is mainly used for checker page to determined the status of
 * insurance object linkage
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/28 10:23:16 $ Tag: $Name: $
 */
public class OBInsuranceObjLinkage {
	String objID;

	String status;

	public String getObjID() {
		return objID;
	}

	public void setObjID(String objID) {
		this.objID = objID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}