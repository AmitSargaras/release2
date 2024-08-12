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

public abstract class EBGroupSubLimitBean implements EntityBean, IGroupSubLimit {


    private static final String[] EXCLUDE_METHOD = new String[]{"getGroupSubLimitID","getGroupSubLimitIDRef"};

    public abstract void setCMPGroupSubLimitID(Long cMPGroupSubLimitID);
    public abstract Long getCMPGroupSubLimitID();

    public abstract void setCMPGroupSubLimitIDRef(Long cMPGroupSubLimitIDRef);
    public abstract Long getCMPGroupSubLimitIDRef();

    public abstract void setCMPGrpID(Long cMPGrpID);
    public abstract Long getCMPGrpID();

    public abstract double getCMPLimitAmt();
    public abstract void setCMPLimitAmt(double cMPLimitAmt);

    public abstract String getCMPStatus();
    public abstract void setCMPStatus(String cMPStatus);



    public long getGroupSubLimitID() {
        if (getCMPGroupSubLimitID() != null) {
            return getCMPGroupSubLimitID().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGroupSubLimitID(long CustGrpID) {
        setCMPGroupSubLimitID(new Long(CustGrpID));
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

    public Long ejbCreate(IGroupSubLimit obj) throws CreateException {
        if (obj == null) {
            throw new CreateException("IGroupSubLimit is null!");
        }
        long pk = ICMSConstant.LONG_INVALID_VALUE;

        try {
            pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
            AccessorUtil.copyValue(obj, this, EBGroupSubLimitBean.EXCLUDE_METHOD);
            setGroupSubLimitID(pk);

            if (obj.getGroupSubLimitIDRef() == ICMSConstant.LONG_INVALID_VALUE){
                setGroupSubLimitIDRef(pk);
            }else{
                setGroupSubLimitIDRef(obj.getGroupSubLimitIDRef());
            }

            return new Long(pk);

        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CreateException("Exception at ejbCreate: " + ex.toString());
        }
    }

    public IGroupSubLimit getValue() throws CustGrpIdentifierException {
        IGroupSubLimit value = new OBGroupSubLimit();
        AccessorUtil.copyValue(this, value);
        return value;
    }

    public void setValue(IGroupSubLimit obj) throws ConcurrentUpdateException, CustGrpIdentifierException {
        try {
            AccessorUtil.copyValue(obj, this, EBGroupSubLimitBean.EXCLUDE_METHOD);
        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CustGrpIdentifierException("Exception in setValue: " + ex.toString());
        }
    }



    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_GROUP_SUBLIMIT_SEQ;
    }

    // END HERE


    public void ejbPostCreate(IGroupSubLimit obj) {
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



    public long getGroupSubLimitIDRef(){
            if (getCMPGroupSubLimitIDRef() != null) {
            return getCMPGroupSubLimitIDRef().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }
    public void setGroupSubLimitIDRef(long str){
        setCMPGroupSubLimitIDRef(new Long(str));
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
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
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
    	DefaultLogger.debug(this,"EBGroupSubLimitBean = " + msg);
    }
    
    public abstract String getSubLimitTypeCD() ;
    public abstract void setSubLimitTypeCD(String subLimitTypeCD);

    public abstract String getCurrencyCD() ;
    public abstract void setCurrencyCD(String currencyCD) ;

    public abstract Date getLastReviewedDt() ;
    public abstract void setLastReviewedDt(Date lastReviewedDt);

    public abstract String getDescription();
    public abstract void setDescription(String description) ;

    public abstract String getRemarks() ;
    public abstract void setRemarks(String remarks) ;

}
