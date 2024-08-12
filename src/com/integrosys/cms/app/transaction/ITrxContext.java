/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ITrxContext.java,v 1.2 2003/07/16 03:39:31 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import java.util.List;

import com.integrosys.cms.app.common.IContext;

/**
 * This interface represents the transaction context value object.
 * 
 * @author $Author: kllee $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/16 03:39:31 $ Tag: $Name: $
 */
public interface ITrxContext extends IContext {
	public List getFunctionGroupList();

	public boolean getStpAllowed();

	public long getTeamMembershipID();

	public void setFunctionGroupList(List functionGroupList);

	public void setStpAllowed(boolean stpAllowed);

	public void setTeamMembershipID(long value);
}