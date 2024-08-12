/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealException
 *
 * Created on 3:23:40 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 3:23:40 PM
 */
public class PreDealException extends OFAException {

	public PreDealException() {
		super();
	}

	public PreDealException(String msg) {
		super(msg);
	}

	public PreDealException(java.lang.Throwable throwable) {
		super(throwable);
	}

}
