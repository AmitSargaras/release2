/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

import java.util.Date;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;

/**
 * Entity bean implementation for Customer Shareholder entity.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:$
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public abstract class EBCustShareholderBean implements ICustShareholder, EntityBean
{
    /** The container assigned reference to the entity. */
    private EntityContext context;

    /** A list of methods to be excluded during update to the Customer Shareholder. */
    protected static final String[] EXCLUDE_METHOD = new String[] {"getCustRelationshipID"};

     public abstract Long getCustRelationshipIDPK();
    public abstract void setCustRelationshipIDPK(Long value);

    public abstract Long getEBParentSubProfileID();
    public abstract void setEBParentSubProfileID(Long value);

    public abstract Long getEBSubProfileID();
    public abstract void setEBSubProfileID(Long value);

    public abstract String getRelationshipCat();
    public abstract void setRelationshipCat(String value);

    public abstract String getRelationshipValue();
    public abstract void setRelationshipValue(String value);

    public abstract Double getPercentageOwn();
    public abstract void setPercentageOwn(Double value);

	public abstract long getGroupID();
    public abstract void setGroupID (long value);
	
    public abstract long getRefID();
    public abstract void setRefID (long value);
	
	public abstract Date getLastUpdateDate();
    public abstract void setLastUpdateDate(Date value);
    
	public abstract String getLastUpdateUser();
    public abstract void setLastUpdateUser(String value);
	
    public abstract String getStatus();
    public abstract void setStatus(String value);

    public abstract long getVersionTime();
    public abstract void setVersionTime (long value);


    //************ Non-persistence method *************

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#getCustRelationshipID
	    */
    public long getCustRelationshipID() {
        if(null != getCustRelationshipIDPK()) {
            return getCustRelationshipIDPK().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#setCustRelationshipID
	    */
    public void setCustRelationshipID(long value) {
        setCustRelationshipIDPK(new Long(value));
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#getParentSubProfileID
	    */
    public long getParentSubProfileID() {
        if(null != getEBParentSubProfileID()) {
            return getEBParentSubProfileID().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }
	
    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#setParentSubProfileID
	    */
    public void setParentSubProfileID(long value) {
        setEBParentSubProfileID(new Long(value));
    }

	/**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#getSubProfileID
	    */
    public long getSubProfileID() {
        if(null != getEBSubProfileID()) {
            return getEBSubProfileID().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }
	
    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#setSubProfileID
	    */
    public void setSubProfileID(long value) {
        setEBSubProfileID(new Long(value));
    }     

   /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#getCustomerDetails
	    */
    public ICMSCustomer getCustomerDetails() {
		return null;
	}

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustShareholder#setCustomerDetails
	    */
    public void setCustomerDetails(ICMSCustomer value) {
		//do nothing
	}	 

    //************ Non-persistence method *************
   

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.EBCustShareholder#getValue
	    */
    public ICustShareholder getValue()
    {
        OBCustShareholder custShareholder = new OBCustShareholder();
        AccessorUtil.copyValue (this, custShareholder);
        return custShareholder;
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.EBCustShareholder#setValue
	    */
    public void setValue (ICustShareholder custShareholder)
        throws VersionMismatchException
    {
        checkVersionMismatch ( custShareholder);
        AccessorUtil.copyValue ( custShareholder, this, EXCLUDE_METHOD );
        setVersionTime ( VersionGenerator.getVersionNumber() );
    }   

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.EBCustShareholder#setStatusDeleted
	    */
    public void setStatusDeleted (ICustShareholder custShareholder)
        throws VersionMismatchException
    {
        checkVersionMismatch ( custShareholder );
        setStatus ( ICMSConstant.STATE_DELETED );
        setVersionTime ( VersionGenerator.getVersionNumber() );
    }

    /**
	     * Check the version of this Customer Shareholder.
	     *
	     * @param custShareholder of type ICustShareholder
	     * @throws VersionMismatchException if the entity version is invalid
	     */
    private void checkVersionMismatch (ICustShareholder custShareholder)
        throws VersionMismatchException
    {
        if (getVersionTime() != custShareholder.getVersionTime())
            throw new VersionMismatchException ("Mismatch timestamp! " + custShareholder.getVersionTime());
    }

    /**
     * Matching method of the create(...) method of the bean's home interface.
     * The container invokes an ejbCreate method to create entity bean instance.
     *
     * @param parentSubProfileID parent sub profile ID
     * @param custShareholder of type ICustShareholder
     * @throws CreateException on error creating the entity object
     */
    public Long ejbCreate (long parentSubProfileID, ICustShareholder custShareholder) throws CreateException
    {
		if(null == custShareholder) {
			throw new CreateException("ICustShareholder is null!");
		}
		else if(ICMSConstant.LONG_INVALID_VALUE == parentSubProfileID) {
			throw new CreateException("parentSubProfileID is uninitialised!");
		}

        try {

            long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
            pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CUST_RELNSHIP, true));
            DefaultLogger.debug(this, "Creating cust shareholder with ID: " + pk);
            setCustRelationshipIDPK( new Long( pk ) );

            AccessorUtil.copyValue(custShareholder, this, EXCLUDE_METHOD);
			setEBParentSubProfileID( new Long( parentSubProfileID ) );
			if (custShareholder.getRefID() == ICMSConstant.LONG_INVALID_VALUE) {
				setRefID ( pk );
			}
			if (custShareholder.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
				setGroupID ( pk );
			}
			setVersionTime( VersionGenerator.getVersionNumber() );
			setRelationshipCat( ICMSConstant.RELATIONSHIP_CATEGORY_CODE );
			setRelationshipValue( ICMSConstant.RELATIONSHIP_SHAREHOLDER_ENTRY_CODE );

            return new Long( pk );
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CreateException (e.toString());
        }		
    }

    /**
     * The container invokes this method after invoking the ejbCreate
     * method with the same arguments.
     * @param custShareholder of type ICustShareholder
     */
    public void ejbPostCreate (long parentSubProfileID, ICustShareholder custShareholder)
    {}



    /**
     * EJB callback method to set the context of the bean.
     * @param context the entity context.
     */
    public void setEntityContext (EntityContext context) {
        this.context = context;
    }

    /**
     * EJB callback method to clears the context of the bean.
     */
    public void unsetEntityContext() {
        this.context = null;
    }

    /**
     * This method is called when the container picks this entity object
     * and assigns it to a specific entity object. No implementation currently
     * acquires any additional resources that it needs when it is in the
     * ready state.
     */
    public void ejbActivate()
    {}

    /**
     * This method is called when the container diassociates the bean
     * from the entity object identity and puts the instance back into
     * the pool of available instances. No implementation is currently
     * provided to release resources that should not be held while the
     * instance is in the pool.
     */
    public void ejbPassivate()
    {}

    /**
     * The container invokes this method on the bean whenever it
     * becomes necessary to synchronize the bean's state with the
     * state in the database. This method is called after the container
     * has loaded the bean's state from the database.
     */
    public void ejbLoad()
    {}

    /**
     * The container invokes this method on the bean whenever it
     * becomes necessary to synchronize the state in the database
     * with the state of the bean. This method is called before the
     * container extracts the fields and writes them into the database.
     */
    public void ejbStore()
    {}

    /**
     * The container invokes this method in response to a client-invoked
     * remove request. No implementation is currently provided for taking
     * actions before the bean is removed from the database.
     */
    public void ejbRemove()
    {}
}