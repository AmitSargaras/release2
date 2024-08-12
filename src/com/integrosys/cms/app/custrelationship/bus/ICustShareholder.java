/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

/**
 * Interface of data model holds Customer Shareholder.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface ICustShareholder extends ICommonCustRelationship
{

   /**
	     * Get percentageOwn
	     *
	     * @return Double
	     */
    public Double getPercentageOwn();

    /**
	     * Set the percentageOwn.
	     *
	     * @param percentageOwn is of type Double
	     */
    public void setPercentageOwn(Double percentageOwn);
   
  

}