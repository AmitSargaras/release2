package com.integrosys.cms.app.function.trx;

import java.util.List;

import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ITeamFunctionGrpTrxValue extends ICMSTrxValue {
	public List getTeamFunctionGrps();

	public void setTeamFunctionGrps(List value);

	public List getStagingTeamFunctionGrps();

	public void setStagingTeamFunctionGrps(List value);
}
