/* Copyright Integro Technologies Pte Ltd
 * com.integrosys.cms.app.transaction.OBCMSTrxRouteInfo.java Created on May 21, 2004 5:04:40 PM
 *
 */

package com.integrosys.cms.app.transaction;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @since May 21, 2004
 * @author heju
 * @version 1.0.0
 */
public class OBCMSTrxRouteInfo implements java.io.Serializable {
	private String memberShipTypeID = Long.toString(ICMSConstant.LONG_INVALID_VALUE);

	private String memberShipName = "";

	private String teamID = Long.toString(ICMSConstant.LONG_INVALID_VALUE);

	private String teamName = "";

	private String teamTypeID = Long.toString(ICMSConstant.LONG_INVALID_VALUE);

	private String userID = Long.toString(ICMSConstant.LONG_INVALID_VALUE);

	private String userName = "";

	public OBCMSTrxRouteInfo() {

	}

	/**
	 * Parse tokenized String with format {memberShipTypeID}${teamID}${userID}
	 * @param tokenizedString
	 */
	public OBCMSTrxRouteInfo(String tokenizedString) {
		if ((tokenizedString != null) && (tokenizedString.length() > 0)) {
			int idx;
			String left = tokenizedString;
			try {
				idx = left.indexOf("$");
				memberShipTypeID = left.substring(0, idx);
				left = left.substring(idx + 1);

				idx = left.indexOf("$");
				teamID = left.substring(0, idx);
				left = left.substring(idx + 1);

				userID = left;
			}
			catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * @return Returns the memberShipTypeID.
	 */
	public String getMemberShipTypeID() {
		return memberShipTypeID;
	}

	/**
	 * @param memberShipTypeID The memberShipTypeID to set.
	 */
	public void setMemberShipTypeID(String memberShipTypeID) {
		this.memberShipTypeID = memberShipTypeID;
	}

	/**
	 * @return Returns the memberShipName.
	 */
	public String getMemberShipName() {
		return memberShipName;
	}

	/**
	 * @param memberShipName The memberShipName to set.
	 */
	public void setMemberShipName(String memberShipName) {
		this.memberShipName = memberShipName;
	}

	/**
	 * @return Returns the teamID.
	 */
	public String getTeamID() {
		return teamID;
	}

	/**
	 * @param teamID The teamID to set.
	 */
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	/**
	 * @return Returns the teamName.
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName The teamName to set.
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return Returns the teamTypeID.
	 */
	public String getTeamTypeID() {
		return teamTypeID;
	}

	/**
	 * @param teamTypeID The teamTypeID to set.
	 */
	public void setTeamTypeID(String teamTypeID) {
		this.teamTypeID = teamTypeID;
	}

	/**
	 * @return Returns the userID.
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID The userID to set.
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Add-in property method
	 * @return
	 */
	public String getLableOfUser() {
		if (((userName == null) || (userName.length() == 0)) && ((teamName == null) || (teamName.length() == 0))) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		if ((userName != null) && (userName.length() > 0)) {
			buf.append(userName);
		}
		if ((teamName != null) && (teamName.length() > 0)) {
			buf.append(" (");
			buf.append(teamName);
			buf.append(")");
		}

		return buf.toString();
	}

	public String getLableOfUserInfo() {
		if (((userName == null) || (userName.length() == 0))
				&& ((memberShipName == null) || (memberShipName.length() == 0))) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		if ((userName != null) && (userName.length() > 0)) {
			buf.append(userName);
		}
		if ((memberShipName != null) && (memberShipName.length() > 0)) {
			buf.append(" (");
			buf.append(memberShipName);
			buf.append(")");
		}

		return buf.toString();
	}

	public String getValueOfUser() {
		return memberShipTypeID + "$" + teamID + "$" + userID;
	}

	public String toString() {
		return "memberShipTypeID=" + memberShipTypeID + "\nmemberShipName=" + memberShipName + "\nteamID=" + teamID
				+ "\nteamName=" + teamName + "\nteamTypeID=" + teamTypeID + "\nuserID=" + userID + "\nuserName="
				+ userName;
	}

	public static void main(String argv[]) {
		OBCMSTrxRouteInfo info = new OBCMSTrxRouteInfo("1$2$3");
//		System.out.print(info);
	}
}
