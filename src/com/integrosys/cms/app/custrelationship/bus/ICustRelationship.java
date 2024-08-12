/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

/**
 * Interface of data model holds Customer Relationship.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface ICustRelationship extends ICommonCustRelationship
{	
	    
    /**
	     * Get RelationshipValue
	     *
	     * @return String
	     */
    public String getRelationshipValue();

   /**
	     * Set the RelationshipValue.
	     *
	     * @param relationshipValue is of type String
	     */
    public void setRelationshipValue(String relationshipValue);
   
   /**
	     * Get remarks
	     *
	     * @return String
	     */
	public String getRemarks();
	
	/**
	     * Set the remarks.
	     *
	     * @param remarks is of type String
	*/    
	 public void setRemarks(String remarks);
 

   
}