/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ICommonCodeEntriesGroup.java
 *
 * Created on February 6, 2007, 11:22 AM
 *
 * Purpose:
 * Description: Currently only used OBCommonCodeEntriesGroup
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import java.io.Serializable;

/**
 * 
 * @author Eric
 */
public interface ICommonCodeEntriesGroup extends Serializable {

	ICommonCodeEntries[] getCommonCodeEntries();

	OBCommonCodeEntries getObCommonCode();

}
