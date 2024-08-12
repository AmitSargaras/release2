package com.integrosys.cms.ui.function;

import java.util.List;

import com.integrosys.cms.ui.common.TrxContextForm;

public class TeamFunctionGrpForm extends TrxContextForm implements java.io.Serializable {
	public static final String MAPPER = "com.integrosys.cms.ui.function.TeamFunctionGrpMapper";

	private String teamId;

	private String teamTypeId;

	private String teamName;

	private String teamDesc;

	private List groupFunction;

	private boolean isPreDisb;

	private boolean isDisb;

	private boolean isPostDisb;

	private String targetOffset = "-1";

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamTypeId() {
		return teamTypeId;
	}

	public void setTeamTypeId(String teamTypeId) {
		this.teamTypeId = teamTypeId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamDesc() {
		return teamDesc;
	}

	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}

	public List getGroupFunction() {
		return groupFunction;
	}

	public void setGroupFunction(List groupFunction) {
		this.groupFunction = groupFunction;
	}

	public boolean getIsPreDisb() {
		return isPreDisb;
	}

	public void setIsPreDisb(boolean isPreDisb) {
		this.isPreDisb = isPreDisb;
	}

	public boolean getIsDisb() {
		return isDisb;
	}

	public void setIsDisb(boolean isDisb) {
		this.isDisb = isDisb;
	}

	public boolean getIsPostDisb() {
		return isPostDisb;
	}

	public void setIsPostDisb(boolean isPostDisb) {
		this.isPostDisb = isPostDisb;
	}

	public String getTargetOffset() {
		return targetOffset;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public String toString() {
		return "\nteamId = " + teamId + "\nteamTypeId = " + teamTypeId + "\nteamName = " + teamName + "\nteamDesc = "
				+ teamDesc + "\ngroupFunction = " + groupFunction + "\nisPreDisb = " + isPreDisb + "\nisDisb = "
				+ isDisb + "\nisPostDisb = " + isPostDisb + "\ntargetOffset = " + targetOffset;
	}
}
