/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Data model holds Customer Shareholder.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBCustShareholder extends OBCommonCustRelationship implements ICustShareholder
{
		
	private Double percentageOwn;	   
	
    /**
	     * Default Constructor.
	     */
    public OBCustShareholder() {
        super();
    }

    /**
	     * Construct the object from its interface.
	     *
	     * @param obj is of type ICustShareholder
	     */
    public OBCustShareholder (ICustShareholder obj) {
        this();		
        AccessorUtil.copyValue (obj, this);
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#getPercentageOwn
	    */
    public Double getPercentageOwn() {
        return this.percentageOwn;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#setPercentageOwn
	    */
    public void setPercentageOwn(Double percentageOwn) {
        this.percentageOwn = percentageOwn;
    }
   
    /**
	     * Return a String representation of this object.
	     *
	     * @return String
	     */
    public String toString() {
        return AccessorUtil.printMethodValue (this);
    }

    /**
	     * Test for equality.
	     *
	     * @param obj is of type Object
	     * @return boolean
	     */
    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBCustShareholder))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }

}