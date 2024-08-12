package com.integrosys.cms.host.eai.covenant;

import java.util.Vector;

import com.integrosys.cms.host.eai.EAIBody;

public class CovenantResponseMsgBody extends EAIBody {

	private Vector convenantItem;

	public Vector getConvenantItem	() {
		return convenantItem;
	}

	public void setConvenantItem(Vector convenantItem) {
		this.convenantItem = convenantItem;
	}

}
