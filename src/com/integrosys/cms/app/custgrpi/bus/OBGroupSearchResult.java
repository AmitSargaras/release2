/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBCustomerSearchResult.java,v 1.10 2005/10/14 09:30:02 lyng Exp $
 */
package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.cci.bus.OBCustomerAddress;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.base.businfra.currency.Amount;

import java.util.Date;

/**
 * This class represents a customer search result data.
 *
 * @author  $Author: lyng $
 * @version $Revision: 1.10 $
 * @since   $Date: 2005/10/14 09:30:02 $
 * Tag:     $Name:  $
 */
public class OBGroupSearchResult implements java.io.Serializable 
{
    
    private String grpNo;
    private long grpID;
    private String groupName;
    private Amount groupLmt;
    private Amount convLmt;
    private Amount islamLmt;
    private Amount invLmt;



	  /**
     * Default Constructor
     */
    public OBGroupSearchResult() {}

   
    public String getGrpNo() {
        return grpNo;
    }

    public void setGrpNo(String grpNo) {
        this.grpNo = grpNo;
    }


    public long getGrpID() {
        return grpID;
    }

    public void setGrpID(long grpID) {
        this.grpID = grpID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public Amount getGroupLmt() {
        return groupLmt;
    }

    public void setGroupLmt(Amount groupLmt) {
        this.groupLmt = groupLmt;
    }
	
	public Amount getConvLmt() {
        return convLmt;
    }

    public void setConvLmt(Amount convLmt) {
        this.convLmt = convLmt;
    }
	
	public Amount getIslamLmt() {
        return islamLmt;
    }

    public void setIslamLmt(Amount islamLmt) {
        this.islamLmt = islamLmt;
    }
	
	public Amount getInvLmt() {
        return invLmt;
    }

    public void setInvLmt(Amount invLmt) {
        this.invLmt = invLmt;
    }
	         
   
    /**
     * Return a String representation of the object
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }

   
}