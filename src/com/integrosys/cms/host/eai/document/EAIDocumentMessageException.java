package com.integrosys.cms.host.eai.document;

import com.integrosys.cms.host.eai.EAIMessageException;

/**
 * EAIDocumentMessageException.
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EAIDocumentMessageException extends EAIMessageException {

	private static final long serialVersionUID = -7240410323369176852L;

	public EAIDocumentMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public EAIDocumentMessageException(String msg) {
		super(msg);
	}
}
