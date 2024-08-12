/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/OBPartialSCCertificate.java,v 1.2 2006/05/30 10:19:38 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.util.Arrays;
import java.util.Collection;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class the that provides the implementation for ISCCertificate
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */
public class OBPartialSCCertificate extends OBSCCertificate implements IPartialSCCertificate {

	/**
	 * Set the list of Partial SCC items
	 * @return IPartialSCCertificateItem[] - the list of partial SCC items
	 */
	public IPartialSCCertificateItem[] getPartialSCCItemList() {
		ISCCertificateItem[] itemList = getSCCItemList();
		Collection itemCol = Arrays.asList(itemList);
		return (IPartialSCCertificateItem[]) itemCol.toArray(new IPartialSCCertificateItem[0]);
	}

	public IPartialSCCertificateItem[] getCleanPSCCItemList() {
		ISCCertificateItem[] itemList = getCleanSCCertificateItemList();
		if ((itemList != null) && (itemList.length != 0)) {
			Collection itemCol = Arrays.asList(itemList);
			return (IPartialSCCertificateItem[]) itemCol.toArray(new IPartialSCCertificateItem[0]);
		}

		return null;
	}

	public IPartialSCCertificateItem[] getNotCleanPSCCItemList() {
		ISCCertificateItem[] itemList = getNotCleanSCCertificateItemList();
		if ((itemList != null) && (itemList.length != 0)) {
			Collection itemCol = Arrays.asList(itemList);
			return (IPartialSCCertificateItem[]) itemCol.toArray(new IPartialSCCertificateItem[0]);
		}

		return null;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
