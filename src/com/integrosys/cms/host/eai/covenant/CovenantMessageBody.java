package com.integrosys.cms.host.eai.covenant;

import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.eai.covenant.bus.RecurrentDoc;

public class CovenantMessageBody extends EAIBody implements java.io.Serializable {

	private static final long serialVersionUID = 2480499024716980949L;

	private RecurrentDoc recurrentDoc;

	public RecurrentDoc getRecurrentDoc() {
		return recurrentDoc;
	}

	public void setRecurrentDoc(RecurrentDoc recurrentDoc) {
		this.recurrentDoc = recurrentDoc;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("CovenantMessageBody [");
		buf.append("recurrentDoc=");
		buf.append(recurrentDoc);
		buf.append("]");
		return buf.toString();
	}

}
