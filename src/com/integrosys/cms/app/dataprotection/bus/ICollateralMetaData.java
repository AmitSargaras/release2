/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/bus/ICollateralMetaData.java,v 1.5 2005/10/27 06:34:17 lyng Exp $
 */
package com.integrosys.cms.app.dataprotection.bus;

import com.integrosys.cms.app.collateral.bus.ICollateralSubType;

/**
 * Purpose: Represents the interface for retrieving status on whether the fields
 * may be updated
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public interface ICollateralMetaData extends IDataAccessProfile {

	/**
	 * Checks the SCC Updatable status
	 * @return boolean - is update allowed?
	 */
	public boolean getSCCUpdatable();

	public ICollateralSubType getCollateralSubType();

	public String getFieldName();

	public String getApplicableCountry();
}
