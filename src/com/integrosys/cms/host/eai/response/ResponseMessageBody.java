package com.integrosys.cms.host.eai.response;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class ResponseMessageBody extends EAIBody implements java.io.Serializable {

	private static final long serialVersionUID = -8803536858128272867L;

	private Response response = new Response();

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
