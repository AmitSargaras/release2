/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/commoditylimitlevel/OBCommodityLimitLevel.java,v 1.6 2004/07/15 11:42:06 jhe Exp $
 */

package com.integrosys.cms.app.eventmonitor.commoditylimitlevel;

import java.util.Collection;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean object to store the attributes required to prepare notification
 * message This message is for BFL Due notification
 */
public class OBCommodityLimitLevel extends OBEventInfo {

	private String limitProfileID;

	private String instrRefNo;

	private String arm;

	// private OBLimitInfo limitInfo[];
	private Collection limitInfo;

	public Collection getLimitInfo() {
		return limitInfo;
	}

	public OBLimitInfo[] getLimitInfoList() {
		return (OBLimitInfo[]) limitInfo.toArray(new OBLimitInfo[0]);
	}

	public void setLimitInfo(Collection limitInfo) {
		this.limitInfo = limitInfo;
	}

	public String getArm() {
		return arm;
	}

	public void setArm(String arm) {
		this.arm = arm;
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
		String result = limitProfileID + "\n" + instrRefNo + "\n" + arm + "\n";

		if (limitInfo != null) {
			java.util.Iterator e = limitInfo.iterator();
			while (e.hasNext()) {
				OBLimitInfo di = (OBLimitInfo) e.next();
				result += "-----" + di.toString() + "\n";
			}

		}

		return result;

	}
}
