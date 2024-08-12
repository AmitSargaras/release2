/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/IGuarantor.java,v 1.4 2004/08/17 11:57:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * This interface represents loan agency guarantor.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/17 11:57:46 $ Tag: $Name: $
 */
public interface IGuarantor extends java.io.Serializable {
	/**
	 * Get guarantor id.
	 * 
	 * @return long
	 */
	public long getGuarantorID();

	/**
	 * Set guarantor id.
	 * 
	 * @param guarantorID of type long
	 */
	public void setGuarantorID(long guarantorID);

	/**
	 * Get guarantor name.
	 * 
	 * @return String
	 */
	public String getName();

	/**
	 * Set guarantor name.
	 * 
	 * @param name of type String
	 */
	public void setName(String name);

	/**
	 * Get guarantor status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set guarantor status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get common reference of actual and staging guarantor.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set common reference of actual and staging guarantor.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);
}
