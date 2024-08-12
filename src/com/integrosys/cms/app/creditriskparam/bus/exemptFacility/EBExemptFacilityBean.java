/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/

package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;

/**
 * EBExemptFacilityBean
 * Purpose:
 * Description:
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
public abstract class EBExemptFacilityBean implements EntityBean, IExemptFacility{

    protected EntityContext _context = null;
    private static final String[] EXCLUDE_METHOD = new String[] {"getExemptFacilityID"};

    public abstract void setCmpExemptFacilityID(Long cmpExemptFacilityID);
    public abstract Long getCmpExemptFacilityID();
    public abstract void setCmpGroupID(Long cmpGroupID);
    public abstract Long getCmpGroupID();
    public abstract long getCmsRef();
    public abstract void setCmsRef (long value);

    public long getExemptFacilityID() {
        if(null != getCmpExemptFacilityID()) {
            return getCmpExemptFacilityID().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }

    public void setExemptFacilityID(long exemptFacilityID) {
        setCmpExemptFacilityID (new Long (exemptFacilityID));
    }

    public long getGroupID() {
        return getCmpGroupID().longValue();
    }

    public void setGroupID(long groupID) {
        setCmpGroupID (new Long (groupID));
    }

    public abstract long getVersionTime();
    
    public abstract void setVersionTime(long versionTime);

    /**
     * EJB callback method to set the context of the bean.
     *
     * @param context the entity context.
     */
    public void setEntityContext (EntityContext context) {
        _context = context;
    }

    /**
     * EJB callback method to clears the context of the bean.
     */
    public void unsetEntityContext() {
        _context = null;
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



    /**
     * Create a credit risk param
     * @param anExemptFacility IExemptFacility
     * @return Long - the document item ID (primary key)
     * @throws javax.ejb.CreateException on error
     *
     */
    public Long ejbCreate(IExemptFacility anExemptFacility)
            throws CreateException {
        if (anExemptFacility == null) {
            throw new CreateException("ExemptFacility is null!");
        }

        try {

            long pk = ICMSConstant.LONG_INVALID_VALUE;
            DefaultLogger.debug(this, "Sequence Name: " + getSequenceName());
            pk = Long.parseLong((new SequenceManager()).getSeqNum(
                    getSequenceName(), true));
            DefaultLogger.debug(this, "Item to be inserted: " + anExemptFacility);
            setCmpExemptFacilityID(new Long(pk));
            AccessorUtil.copyValue(anExemptFacility, this, EXCLUDE_METHOD);
            DefaultLogger.debug(this, "PK: " + pk);

            if (ICMSConstant.LONG_INVALID_VALUE == anExemptFacility.getCmsRef() || anExemptFacility.getCmsRef() == 0) {
               setCmsRef(pk);
            } else {
               setCmsRef(anExemptFacility.getCmsRef());
            }
            if (anExemptFacility.getGroupID() == ICMSConstant.LONG_MIN_VALUE  || anExemptFacility.getGroupID() == 0) {
                setGroupID ( pk );
            }
            else {
                setGroupID(anExemptFacility.getGroupID());
            }
            setVersionTime(VersionGenerator.getVersionNumber());
            return new Long(pk);
        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CreateException(
                    "Exception at ejbCreate: " + ex.toString());
        }
    }


    /**
     * Post-Create a record
     *
     * @param anExemptFacility IExemptFacility
     */
    public void ejbPostCreate(IExemptFacility anExemptFacility) {
    }


    /**
     * Return the Interface representation of this object
     * @return IExemptFacility
     *
     */
    public IExemptFacility getValue()
	{
		OBExemptFacility value = new OBExemptFacility();
        AccessorUtil.copyValue(this, value);
        return value;
    }


    /**
     *
     * @param anExemptFacility IExemptFacility
     * @throws ConcurrentUpdateException
     */
    public void setValue(IExemptFacility anExemptFacility)
            throws ConcurrentUpdateException {
        checkVersionMismatch ( anExemptFacility );
        DefaultLogger.debug(this, "before AccessorUtil.copyValue(...)");
        AccessorUtil.copyValue(anExemptFacility, this, EXCLUDE_METHOD);
        DefaultLogger.debug(this, "after AccessorUtil.copyValue(...)");
        setVersionTime(VersionGenerator.getVersionNumber());
    }

    public void setStatusDeleted (IExemptFacility exemptfacility)
        throws ConcurrentUpdateException
    {
        checkVersionMismatch ( exemptfacility );
        setStatus ( ICMSConstant.STATE_DELETED );
        setVersionTime ( VersionGenerator.getVersionNumber() );
    }

    private void checkVersionMismatch (IExemptFacility exemptfacility)
        throws ConcurrentUpdateException
    {
        if (getVersionTime() != exemptfacility.getVersionTime())
            throw new ConcurrentUpdateException ("Mismatch timestamp! " + exemptfacility.getVersionTime());
    }

    /**
     * Get the name of the sequence to be used for the item id
     * @return String - the name of the sequence
     */
    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_EXEMPT_FACILITY_SEQ;
    }
    
    public abstract String getStatus();
    
    public abstract void setStatus(String status);
    
    public abstract String getRemarks();
    
    public abstract void setRemarks(String remarks);
    
    public abstract String getFacilityCode() ;
    public abstract void setFacilityCode(String facilityCode) ;

    public abstract String getFacilityStatusExempted() ;
    public abstract void setFacilityStatusExempted(String facilityStatusExempted);

    public abstract double getFacilityStatusConditionalPerc() ;
    public abstract void setFacilityStatusConditionalPerc(double facilityStatusConditionalPerc) ;

}
