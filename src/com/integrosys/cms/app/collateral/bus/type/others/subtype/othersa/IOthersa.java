/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/
 */
package com.integrosys.cms.app.collateral.bus.type.others.subtype.othersa;

import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;

/**
 * This interface represents others of type othersa.
 * 
 * @author $Author: lyng $<br>
 * @since $Date: 2005/08/15 09:20:24 $ Tag: $Name: $
 */

public interface IOthersa extends IOthersCollateral {
	public String getAssetValue();

	public void setAssetValue(String assetValue);

public String getScrapValue(); 
	


public void setScrapValue(String scrapValue); 
	
}


