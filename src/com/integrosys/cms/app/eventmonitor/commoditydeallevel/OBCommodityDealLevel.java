/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/commoditydeallevel/OBCommodityDealLevel.java,v 1.5 2004/07/15 09:30:18 jhe Exp $
 */

package com.integrosys.cms.app.eventmonitor.commoditydeallevel;

import java.util.Collection;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean object to store the attributes required to prepare notification
 * message This message is for BFL Due notification
 */
public class OBCommodityDealLevel extends OBEventInfo {
	private String limitProfileID;

	private String instrRefNo;

	private String arm;

	// private OBDealInfo dealInfo[];
	private Collection dealInfo;

	public String getArm() {
		return arm;
	}

	public void setArm(String arm) {
		this.arm = arm;
	}

	public Collection getDealInfo() {
		return dealInfo;
	}

	public OBDealInfo[] getDealInfoList() {
		return (OBDealInfo[]) dealInfo.toArray(new OBDealInfo[0]);
	}

	public void setDealInfo(Collection dealInfo_) {
		this.dealInfo = dealInfo_;
	}

	public String getInstrRefNo() {
		return instrRefNo;
	}

	public void setInstrRefNo(String instrRefNo) {
		this.instrRefNo = instrRefNo;
	}

	/**
	 * @return Returns the limitProfileID.
	 */
	public String getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * @param limitProfileID The limitProfileID to set.
	 */
	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public String toString() {
		String result = getLeName() + "\n" + getLeID() + "\n" + getInstrRefNo() + "\n" + getArm() + "\n"
				+ getLimitProfileID() + "\n";
		if (dealInfo != null) {
			java.util.Iterator e = dealInfo.iterator();
			while (e.hasNext()) {
				OBDealInfo di = (OBDealInfo) e.next();
				result += di.getDealRefNo() + "-----" + di.getProductType() + "\n";
			}

		}
		return result;

	}
}
