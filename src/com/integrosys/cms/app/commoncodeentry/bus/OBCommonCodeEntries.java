/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * OBCommonCodeEntries.java
 *
 * Created on February 6, 2007, 11:22 AM
 *
 * Purpose: The OB object that implements ICommonCodeEntries and extends teh OBCMSTrxValue
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBCommonCodeEntries extends OBCMSTrxValue implements ICommonCodeEntries {
	private static final long serialVersionUID = 1L;

	private Collection entries;

	private OBCommonCodeEntry obArray[];

	private long categoryCodeId;

	private long versionTime;

	public OBCommonCodeEntries() {
	}

	public Collection getEntries() {
		return entries;
	}

	public void setEntries(Collection entries) {
		this.entries = entries;
		this.obArray = new OBCommonCodeEntry[entries.size()];
		Iterator iter = entries.iterator();

		for (int loop = 0; (loop < entries.size()) && iter.hasNext(); loop++) {
			this.obArray[loop] = (OBCommonCodeEntry) iter.next();
		}
	}

	public OBCommonCodeEntry[] getObArray() {
		return obArray;
	}

	public void setObArray(OBCommonCodeEntry[] obArray) {
		this.obArray = obArray;
		ArrayList list = new ArrayList();

		for (int i = 0; i < obArray.length; i++) {
			list.add(obArray[i]);
		}

		this.entries = list;
	}

	public long getCategoryCodeId() {
		return categoryCodeId;
	}

	public void setCategoryCodeId(long categoryCodeId) {
		this.categoryCodeId = categoryCodeId;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
}
