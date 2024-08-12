/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;

/**
 * Entity bean implementation for Exempted Institution entity.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:$
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public abstract class EBExemptedInstBean implements IExemptedInst, EntityBean
{
    /** The container assigned reference to the entity. */
    private EntityContext context;

    /** A list of methods to be excluded during update to the Exempted Institution. */
    protected static final String[] EXCLUDE_METHOD = new String[] {"getExemptedInstID"};
	
    public abstract Long getCMPExemptedInstID();
    public abstract void setCMPExemptedInstID(Long value);

    public abstract Long getCMPCustomerID();
    public abstract void setCMPCustomerID(Long value);
   	
    public abstract long getCommonRef();
    public abstract void setCommonRef (long value);    
	
	public abstract long getGroupID();
    public abstract void setGroupID (long value);
	
    public abstract String getStatus();
    public abstract void setStatus(String value);

    public abstract long getVersionTime();
    public abstract void setVersionTime (long value);


    //************ Non-persistence method *************

    /**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#getExemptedInstID
	    */
    public long getExemptedInstID() {
        if(null != getCMPExemptedInstID()) {
            return getCMPExemptedInstID().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }

    /**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#setExemptedInstID
	    */
    public void setExemptedInstID(long value) {
        setCMPExemptedInstID(new Long(value));
    }
   
	/**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#getCustomerID
	    */
    public long getCustomerID() {
        if(null != getCMPCustomerID()) {
            return getCMPCustomerID().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }
    
	/**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#setCustomerID
	    */
    public void setCustomerID(long value) {
        setCMPCustomerID(new Long(value));
    }
	
	/**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#getCustomerName
	    */
    public String getCustomerName() {
		//do nothing
		return null;
	}
	
	/**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#setCustomerName
	    */
	public void setCustomerName(String customerName) {
		//do nothing
	}
	/**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#getLEReference
	    */
	public String getLEReference() {
		//do nothing
		return null;
	}
	
	/**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#setLEReference
	    */
	public void setLEReference(String lEReference) {
		//do nothing
	}
	
	/**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#getCustIDSource
	    */
    public String getCustIDSource() {
		//do nothing
		return null;	
	}
	
	/**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst#setCustIDSource
	    */
	public void setCustIDSource(String custIDSource) {
		//do nothing
	}
	
    /**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.EBExemptedInst#getValue
	    */
    public IExemptedInst getValue()
    {
        OBExemptedInst exemptedInst = new OBExemptedInst();
        AccessorUtil.copyValue (this, exemptedInst);
        return exemptedInst;
    }

    /**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.EBExemptedInst#setValue
	    */
    public void setValue (IExemptedInst exemptedInst)
        throws VersionMismatchException
    {
        checkVersionMismatch ( exemptedInst);
        AccessorUtil.copyValue ( exemptedInst, this, EXCLUDE_METHOD );
        setVersionTime ( VersionGenerator.getVersionNumber() );
    }   

    /**
	    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.EBExemptedInst#setStatusDeleted
	    */
    public void setStatusDeleted (IExemptedInst exemptedInst)
        throws VersionMismatchException
    {
        checkVersionMismatch ( exemptedInst );
        setStatus ( ICMSConstant.STATE_DELETED );
        setVersionTime ( VersionGenerator.getVersionNumber() );
    }

    /**
	     * Check the version of this Exempted Institution.
	     *
	     * @param exemptedInst of type IExemptedInst
	     * @throws VersionMismatchException if the entity version is invalid
	     */
    private void checkVersionMismatch (IExemptedInst exemptedInst)
        throws VersionMismatchException
    {
        if (getVersionTime() != exemptedInst.getVersionTime())
            throw new VersionMismatchException ("Mismatch timestamp! " + exemptedInst.getVersionTime());
    }

    /**
     * Matching method of the create(...) method of the bean's home interface.
     * The container invokes an ejbCreate method to create entity bean instance.
     *
     * @param exemptedInst of type IExemptedInst
     * @throws CreateException on error creating the entity object
     */
    public Long ejbCreate (IExemptedInst exemptedInst) throws CreateException
    {
		if(null == exemptedInst) {
			throw new CreateException("IExemptedInst is null!");
		}
		
        try {

            long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
            pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_EXEMPT_INST, true));
            DefaultLogger.debug(this, "Creating Exempted Institution with ID: " + pk);
            setCMPExemptedInstID( new Long( pk ) );

            AccessorUtil.copyValue(exemptedInst, this, EXCLUDE_METHOD);
			if (exemptedInst.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonRef ( pk );
			}
			if (exemptedInst.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
				setGroupID ( pk );
			}
			setVersionTime( VersionGenerator.getVersionNumber() );

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
     * @param exemptedInst of type IExemptedInst
     */
    public void ejbPostCreate (IExemptedInst exemptedInst)
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