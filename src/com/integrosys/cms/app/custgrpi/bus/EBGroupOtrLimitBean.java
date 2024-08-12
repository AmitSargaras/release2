package com.integrosys.cms.app.custgrpi.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.EJBException;

public abstract class EBGroupOtrLimitBean implements EntityBean, IGroupOtrLimit {

    private static final String[] EXCLUDE_METHOD = new String[]{"getGroupOtrLimitID","getGroupOtrLimitIDRef"};

    public abstract void setCMPGroupOtrLimitID(Long cMPGroupOtrLimitID);
    public abstract Long getCMPGroupOtrLimitID();

    public abstract void setCMPGroupOtrLimitIDRef(Long cMPGroupOtrLimitIDRef);
    public abstract Long getCMPGroupOtrLimitIDRef();

    public abstract void setCMPGrpID(Long cMPGrpID);
    public abstract Long getCMPGrpID();

    public abstract double getCMPLimitAmt();
    public abstract void setCMPLimitAmt(double cMPLimitAmt);

    public abstract String getCMPStatus();
    public abstract void setCMPStatus(String cMPStatus);



    public long getGroupOtrLimitID() {
        if (getCMPGroupOtrLimitID() != null) {
            return getCMPGroupOtrLimitID().longValue();
        }
        return ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGroupOtrLimitID(long CustGrpID) {
        setCMPGroupOtrLimitID(new Long(CustGrpID));
    }

     public String getStatus() {
        if (ICMSConstant.STATE_ACTIVE.equals(getCMPStatus())) {
            return ICMSConstant.STATE_ACTIVE ;
        }
         return ICMSConstant.STATE_DELETED ;
    }
    public void setStatus(String status) {
        if (ICMSConstant.STATE_DELETED.equals(status)) {
            setCMPStatus(ICMSConstant.STATE_DELETED);
            return;
        }
        setCMPStatus(ICMSConstant.STATE_ACTIVE);
    }


    //START HERE

    public Long ejbCreate(IGroupOtrLimit obj) throws CreateException {
        if (obj == null) {
            throw new CreateException("IGroupOtrLimit is null!");
        }
        long pk = ICMSConstant.LONG_INVALID_VALUE;

        try {
            pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
            AccessorUtil.copyValue(obj, this, EBGroupOtrLimitBean.EXCLUDE_METHOD);
            setGroupOtrLimitID(pk);

            if (obj.getGroupOtrLimitIDRef() == ICMSConstant.LONG_INVALID_VALUE){
                setGroupOtrLimitIDRef(pk);
            }else{
                setGroupOtrLimitIDRef(obj.getGroupOtrLimitIDRef());
            }

            return new Long(pk);

        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CreateException("Exception at ejbCreate: " + ex.toString());
        }
    }

    public IGroupOtrLimit getValue() throws CustGrpIdentifierException {
        IGroupOtrLimit value = new OBGroupOtrLimit();
        AccessorUtil.copyValue(this, value);
        return value;
    }

    public void setValue(IGroupOtrLimit obj) throws ConcurrentUpdateException, CustGrpIdentifierException {
        try {
            AccessorUtil.copyValue(obj, this, EBGroupOtrLimitBean.EXCLUDE_METHOD);
        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CustGrpIdentifierException("Exception in setValue: " + ex.toString());
        }
    }



    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_GROUP_OTRLIMIT_SEQ;
    }

    // END HERE


    public void ejbPostCreate(IGroupOtrLimit obj) {
    }

    /**
     * EJB callback method
     */
    public void ejbActivate() {
    }


    /**
     * EJB callback method
     */
    public void ejbPassivate() {
    }


    /**
     * EJB callback method
     */
    public void ejbLoad() {
    }


    /**
     * EJB callback method
     */
    public void ejbStore() {
    }


    /**
     * EJB callback method
     */
    public void ejbRemove() {
    }


    /**
     * EJB Callback Method
     */
    public void setEntityContext(EntityContext ctx) {
        _context = ctx;
    }


    /**
     * EJB Callback Method
     */
    public void unsetEntityContext() {
        _context = null;
    }


    /**
     * The Entity Context
     */
    protected EntityContext _context = null;



    public long getGroupOtrLimitIDRef(){
            if (getCMPGroupOtrLimitIDRef() != null) {
            return getCMPGroupOtrLimitIDRef().longValue();
        }
        return ICMSConstant.LONG_INVALID_VALUE;
    }
    public void setGroupOtrLimitIDRef(long str){
        setCMPGroupOtrLimitIDRef(new Long(str));
    }


     public Amount getLimitAmt() {
        if (getCurrencyCD() == null) {
            return null;
        }
        return new Amount(getCMPLimitAmt(), getCurrencyCD());
    }
    public void setLimitAmt(Amount amount) {
        if (amount != null)
            setCMPLimitAmt(amount.getAmountAsDouble());
    }




    public long getGrpID() {
        if (getCMPGrpID() != null) {
            return getCMPGrpID().longValue();
        }
        return ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGrpID(long CustGrpID) {
        //do nothing (When a cmp-field and a cmr-field (relationship) are mapped to the same column,
        //the setXXX method for the cmp-field may not be called.  The cmp-field is read-only.)
        //setCMPGrpID(new Long(CustGrpID));
    }


   /**   helper method tp print
     *
     * @param msg
     */
    protected void Debug(String msg) {
    	DefaultLogger.debug(this,"EBGroupOtrLimitBean = " + msg);
    }
    
    public abstract String getOtrLimitTypeCD() ;
    public abstract void setOtrLimitTypeCD(String otrLimitTypeCD);

    public abstract String getCurrencyCD() ;
    public abstract void setCurrencyCD(String currencyCD) ;
    
    public abstract String getRemarks() ;
    public abstract void setRemarks(String remarks) ;

    public abstract Date getLastReviewedDt() ;
    public abstract void setLastReviewedDt(Date lastReviewedDt);

    public abstract String getDescription();
    public abstract void setDescription(String description) ;
}