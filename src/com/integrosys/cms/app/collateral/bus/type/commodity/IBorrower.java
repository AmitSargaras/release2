/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/IBorrower.java,v 1.4 2004/08/17 11:57:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * This interface represents loan agency borrower.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/17 11:57:46 $ Tag: $Name: $
 */
public interface IBorrower extends java.io.Serializable {
	/**
	 * Get borrower id.
	 * 
	 * @return long
	 */
	public long getBorrowerID();

	/**
	 * Set borrower id.
	 * 
	 * @param borrowerID of type long
	 */
	public void setBorrowerID(long borrowerID);

	/**
	 * Get borrower name.
	 * 
	 * @return String
	 */
	public String getName();

	/**
	 * Set borrower name.
	 * 
	 * @param name of type String
	 */
	public void setName(String name);

	/**
	 * Get borrower status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set borrower status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get common reference for actual and staging borrower.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set common reference for actual and staging borrower.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);
}
