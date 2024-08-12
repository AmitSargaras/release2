/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateralType.java,v 1.4 2003/06/20 10:20:55 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

/**
 * This interface represents collateral type, such as asset, property, document,
 * insurance, cash, etc.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/06/20 10:20:55 $ Tag: $Name: $
 */
public interface ICollateralType extends Serializable {
	/**
	 * Get collateral type code.
	 * 
	 * @return String
	 */
	public String getTypeCode();

	/**
	 * Set collateral type code.
	 * 
	 * @param typeCode is of type String
	 */
	public void setTypeCode(String typeCode);

	/**
	 * Get collateral type name.
	 * 
	 * @return String
	 */
	public String getTypeName();

	/**
	 * Set collateral type name.
	 * 
	 * @param typeName is of type String
	 */
	public void setTypeName(String typeName);
}
