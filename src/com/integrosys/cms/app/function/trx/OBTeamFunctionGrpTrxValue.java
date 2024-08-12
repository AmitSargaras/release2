package com.integrosys.cms.app.function.trx;

import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBTeamFunctionGrpTrxValue extends OBCMSTrxValue implements ITeamFunctionGrpTrxValue {

	private List actual;

	private List staging;

	public OBTeamFunctionGrpTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public OBTeamFunctionGrpTrxValue() {

	}

	public List getTeamFunctionGrps() {
		return actual;
	}

	public void setTeamFunctionGrps(List value) {
		actual = value;
	}

	public List getStagingTeamFunctionGrps() {
		return staging;
	}

	public void setStagingTeamFunctionGrps(List value) {
		staging = value;
	}
	
	
}
