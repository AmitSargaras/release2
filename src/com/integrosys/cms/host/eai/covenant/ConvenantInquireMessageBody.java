package com.integrosys.cms.host.eai.covenant;

import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.eai.covenant.bus.Covenant;

public class ConvenantInquireMessageBody extends EAIBody {

	private Covenant covenant;

	public Covenant getCovenant() {
		return covenant;
	}

	public void setCovenant(Covenant covenant) {
		this.covenant = covenant;
	}

}
