/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/document/subtype/fxisda/IFXISDA.java,v 1.4 2003/07/21 05:57:43 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.document.subtype.fxisda;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;

/**
 * This interface represents an FX/Derivative Document of type ISDA/FEMA.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/21 05:57:43 $ Tag: $Name: $
 */
public interface IFXISDA extends IDocumentCollateral {
	/**
	 * Get date of ISDA agreement.
	 * 
	 * @return Date
	 */
	public Date getISDADate();

	/**
	 * Set date of ISDA agreement.
	 * 
	 * @param iSDADate of type Date
	 */
	public void setISDADate(Date iSDADate);

	/**
	 * Get description of products in ISDA.
	 * 
	 * @return String
	 */
	public String getISDAProductDesc();

	/**
	 * Set description of products in ISDA.
	 * 
	 * @param iSDAProductDesc of type String
	 */
	public void setISDAProductDesc(String iSDAProductDesc);

	/**
	 * Get date of IFEMA agreement.
	 * 
	 * @return Date
	 */
	public Date getIFEMADate();

	/**
	 * Set date of IFEMA agreement.
	 * 
	 * @param iFEMADate of type Date
	 */
	public void setIFEMADate(Date iFEMADate);

	/**
	 * Get description of products in IFEMA.
	 * 
	 * @return String
	 */
	public String getIFEMAProductDesc();

	/**
	 * Set description of products in IFEMA.
	 * 
	 * @param iFEMAProductDesc of type String
	 */
	public void setIFEMAProductDesc(String iFEMAProductDesc);

	/**
	 * Get date of ICOM document.
	 * 
	 * @return Date
	 */
	public Date getICOMDate();

	/**
	 * Set date of ICOM document.
	 * 
	 * @param iCOMDate of type Date
	 */
	public void setICOMDate(Date iCOMDate);

	/**
	 * Get description of products in ICOM terms.
	 * 
	 * @return String
	 */
	public String getICOMProductDesc();

	/**
	 * Set description of products in ICOM terms.
	 * 
	 * @param iCOMProductDesc of type String
	 */
	public void setICOMProductDesc(String iCOMProductDesc);
}
