/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ICommonCodeEntries.java
 *
 * Created on February 6, 2007, 11:19 AM
 *
 * Purpose: CommonCodeEntries interface
 * Description: implemented by class who wish to store data related to CommonCodeEntries -
 * only one class implements this interface, (OBCommonCodeEntries )
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import java.io.Serializable;
import java.util.Collection;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author Eric
 */
public interface ICommonCodeEntries extends Serializable, IValueObject {
	/**
	 * @return Collection - a collection of objects implementing the
	 *         ICommonCodeEntry interface
	 */
	public Collection getEntries();

	/**
	 * @param entries - ollection of objects implementing the ICommonCodeEntry
	 *        interface to be stored in this object
	 */
	public void setEntries(Collection entries);

	/**
	 * @return OBCommonCodeEntry[] - an array of OBCommonCodeEntry objects
	 */
	public OBCommonCodeEntry[] getObArray();

	/**
	 * @param obArray - the array of OBCommonCodeEntry object to be stored in
	 *        this object
	 */
	public void setObArray(OBCommonCodeEntry[] obArray);

	/**
	 * @return long - return the category code id that is associated with the
	 *         stored ICommonCodeEntry objects
	 */
	public long getCategoryCodeId();

	/**
	 * @param categoryCodeId - store the category code id that is associated
	 *        with the stored ICommonCodeEntry objects
	 */
	public void setCategoryCodeId(long categoryCodeId);
}
