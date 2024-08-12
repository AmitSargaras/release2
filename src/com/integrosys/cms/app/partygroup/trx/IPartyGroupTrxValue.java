package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author bharat waghela
 */

public interface IPartyGroupTrxValue extends ICMSTrxValue {

	public IPartyGroup getPartyGroup();

	public IPartyGroup getStagingPartyGroup();

	public void setPartyGroup(IPartyGroup value);

	public void setStagingPartyGroup(IPartyGroup value);
}
