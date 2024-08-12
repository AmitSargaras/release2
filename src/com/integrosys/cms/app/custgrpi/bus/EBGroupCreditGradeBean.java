package com.integrosys.cms.app.custgrpi.bus;

import java.util.Date;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

public abstract class EBGroupCreditGradeBean implements EntityBean, IGroupCreditGrade {


    private static final String[] EXCLUDE_METHOD = new String[]{"getGroupCreditGradeID","getGroupCreditGradeIDRef"};

    private static long LONG_INVALID_VALUE = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

    public abstract void setCMPGroupCreditGradeID(Long cMPGroupCreditGradeID);
    public abstract Long getCMPGroupCreditGradeID();

    public abstract void setCMPGroupCreditGradeIDRef(Long cMPGroupCreditGradeIDRef);
    public abstract Long getCMPGroupCreditGradeIDRef();

    public abstract void setCMPGrpID(Long cMPGrpID);
    public abstract Long getCMPGrpID();

    public abstract String getCMPStatus();
    public abstract void setCMPStatus(String cMPStatus);


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

   //pk
    public long getGroupCreditGradeID() {
        if (getCMPGroupCreditGradeID() != null) {
            return getCMPGroupCreditGradeID().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }




    public Long ejbCreate(IGroupCreditGrade obj) throws CreateException {
        if (obj == null) {
            throw new CreateException("ICustGrpIdentifier is null!");
        }
        long pk = ICMSConstant.LONG_INVALID_VALUE;


        try {
            pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
//            System.out.println("EBGroupCreditGradeBean pk = " + pk);
            AccessorUtil.copyValue(obj, this, EXCLUDE_METHOD);
            setGroupCreditGradeID(pk);
            if (LONG_INVALID_VALUE == obj.getGroupCreditGradeIDRef() || obj.getGroupCreditGradeIDRef() == 0) {
                   setGroupCreditGradeIDRef(pk);
              } else {
                   setGroupCreditGradeIDRef(obj.getGroupCreditGradeIDRef());
              }


        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CreateException("Exception at ejbCreate: " + ex.toString());
        }
        return new Long(pk);
    }
    public void ejbPostCreate(IGroupCreditGrade obj) {}
  

    public IGroupCreditGrade getValue()  {
        IGroupCreditGrade value = new OBGroupCreditGrade();
        AccessorUtil.copyValue(this, value);
        return value;
    }

    public void setValue(IGroupCreditGrade obj) throws CustGrpIdentifierException {
        try {
            AccessorUtil.copyValue(obj, this, EXCLUDE_METHOD);

           // setVersionTime(VersionGenerator.getVersionNumber());
        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CustGrpIdentifierException("Exception in setValue: " + ex.toString());
        }
    }


    public void setGroupCreditGradeID(long pk) {
        setCMPGroupCreditGradeID(new Long(pk));
    }


    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_GROUP_CREDIT_GRADE;
    }






    /**
     * EJB callback method
     */
    public void ejbActivate() {}


    /**
     * EJB callback method
     */
    public void ejbPassivate() {}


    /**
     * EJB callback method
     */
    public void ejbLoad() {}


    /**
     * EJB callback method
     */
    public void ejbStore() {}


    /**
     * EJB callback method
     */
    public void ejbRemove() {}


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

    public long getGrpID() {
        if (getCMPGrpID() != null) {
            return getCMPGrpID().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGrpID(long typeCD) {

    }


    public long getGroupCreditGradeIDRef() {
        if (getCMPGroupCreditGradeIDRef() != null) {
            return getCMPGroupCreditGradeIDRef().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGroupCreditGradeIDRef(long groupCreditGradeIDRef) {
        setCMPGroupCreditGradeIDRef(new Long(groupCreditGradeIDRef));
    }

      private void Debug(String msg) {
    	  DefaultLogger.debug(this,"EBGroupCreditGradeBean = " + msg);
    }
      
      public abstract String getTypeCD() ;
      public abstract void setTypeCD(String typeCD) ;

      public abstract String getRatingCD() ;
      public abstract void setRatingCD(String ratingCD);

      public abstract Date getRatingDt() ;
      public abstract void setRatingDt(Date ratingDt) ;

      public abstract String getExpectedTrendRating() ;
      public abstract void setExpectedTrendRating(String expectedTrendRating) ;

      public abstract String getReason() ;
      public abstract void setReason(String reason) ;

      
}
