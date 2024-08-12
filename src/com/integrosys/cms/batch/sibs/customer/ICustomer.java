
package com.integrosys.cms.batch.sibs.customer;

/**
 * 
 * @author $Author: gploh $
 * @version $Revision:  $
 * @since $Date: 08 sep08 $ Tag: $Name: $
 * 
 */
public interface ICustomer extends java.io.Serializable { 

    /** all the setter, getter record type methods ****/
	public void setRecordType(String valueType);
	public String getRecordType();

	/*
     * setter,getter for customer ID
     */
	public void setCustomerID(String value);
	public String getCustomerID();
}
