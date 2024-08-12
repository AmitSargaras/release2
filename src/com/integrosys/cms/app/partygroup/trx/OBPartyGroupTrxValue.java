package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author bharat.waghela
 */
public class OBPartyGroupTrxValue extends OBCMSTrxValue implements
		IPartyGroupTrxValue {

	public OBPartyGroupTrxValue() {
	}

	IPartyGroup partyGroup;
	IPartyGroup stagingPartyGroup;

	public OBPartyGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public IPartyGroup getPartyGroup() {
		return partyGroup; // To change body of implemented methods use File |
							// Settings | File Templates.
	}

	public IPartyGroup getStagingPartyGroup() {
		return stagingPartyGroup; // To change body of implemented methods use
									// File | Settings | File Templates.
	}

	public void setPartyGroup(IPartyGroup value) {
		this.partyGroup = value;
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void setStagingPartyGroup(IPartyGroup value) {
		this.stagingPartyGroup = value;
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

}
