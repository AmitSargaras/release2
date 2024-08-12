/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * OBCommonCodeEntriesGroup.java
 *
 * Created on February 6, 2007, 2:41 PM
 *
 * Purpose: The class which implements the ICommonCodeEntriesGroup interface
 * Description:   currently not used
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

public class OBCommonCodeEntriesGroup implements ICommonCodeEntriesGroup {

	private static final long serialVersionUID = 1L;

	private ICommonCodeEntries[] commonCodeEntries;

	private OBCommonCodeEntries obCommonCode;

	public OBCommonCodeEntriesGroup() {
	}

	public ICommonCodeEntries[] getCommonCodeEntries() {
		return commonCodeEntries;
	}

	public void setCommonCodeEntries(ICommonCodeEntries[] commonCodeEntries) {
		this.commonCodeEntries = commonCodeEntries;
	}

	public OBCommonCodeEntries getObCommonCode() {
		return obCommonCode;
	}

	public void setObCommonCode(OBCommonCodeEntries obCommonCode) {
		this.obCommonCode = obCommonCode;
	}

}
