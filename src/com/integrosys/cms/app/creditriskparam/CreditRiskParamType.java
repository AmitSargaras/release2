/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParam
 *
 * Created on 10:41:13 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 10:41:13 AM
 */
public final class CreditRiskParamType implements Serializable {
	public static final String SHARE_COUNTER_STR = "STOCK";

	public static final String UNIT_TRUST_STR = "UNIT_TRUST";

	public static final String CAP_POLICY_STR = "CAP_POLICY";

	public static final CreditRiskParamType SHARE_COUNTER = new CreditRiskParamType(SHARE_COUNTER_STR);

	public static final CreditRiskParamType UNIT_TRUST = new CreditRiskParamType(UNIT_TRUST_STR);

	public static final CreditRiskParamType CAP_POLICY = new CreditRiskParamType(CAP_POLICY_STR);

	private String type;

	private CreditRiskParamType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}

		final CreditRiskParamType that = (CreditRiskParamType) o;

		if (this.type != null ? !this.type.equals(that.type) : that.type != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		return (this.type != null ? this.type.hashCode() ^ 7 : 0);
	}

}
