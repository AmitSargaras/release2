package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

public abstract class EBGroupMemberBean implements EntityBean, IGroupMember {


    private static final String[] EXCLUDE_METHOD = new String[]{"getGroupMemberID", "getGroupMemberIDRef"};

    public abstract void setCMPGroupMemberID(Long cMPGroupMemberID);

    public abstract Long getCMPGroupMemberID();

    public abstract void setCMPEntityID(Long cMPEntityID);

    public abstract Long getCMPEntityID();


    public abstract void setCMPGroupMemberIDRef(Long cMPGroupMembIDRef);

    public abstract Long getCMPGroupMemberIDRef();

    public abstract void setCMPGrpID(Long cMPGrpID);

    public abstract Long getCMPGrpID();


    public abstract String getCMPStatus();

    public abstract void setCMPStatus(String cMPStatus);


    public long getGroupMemberID() {
        if (getCMPGroupMemberID() != null) {
            return getCMPGroupMemberID().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGroupMemberID(long groupMemberID) {
        setCMPGroupMemberID(new Long(groupMemberID));
    }


    public String getStatus() {
        if (ICMSConstant.STATE_ACTIVE.equals(getCMPStatus())) {
            return ICMSConstant.STATE_ACTIVE;
        }
        return ICMSConstant.STATE_DELETED;
    }

    public void setStatus(String status) {
        if (ICMSConstant.STATE_DELETED.equals(status)) {
            setCMPStatus(ICMSConstant.STATE_DELETED);
            return;
        }
        setCMPStatus(ICMSConstant.STATE_ACTIVE);
    }

    //START HERE

    public Long ejbCreate(IGroupMember obj) throws CreateException {
        if (obj == null) {
            throw new CreateException("IGroupSubLimit is null!");
        }
        long pk = ICMSConstant.LONG_INVALID_VALUE;


        try {
            pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
            AccessorUtil.copyValue(obj, this, EXCLUDE_METHOD);
            setGroupMemberID(pk);
            if (obj.getGroupMemberIDRef() == ICMSConstant.LONG_INVALID_VALUE) {
                setGroupMemberIDRef(pk);
            } else {
                setGroupMemberIDRef(obj.getGroupMemberIDRef());
            }

            return new Long(pk);

        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CreateException("Exception at ejbCreate: " + ex.toString());
        }
    }

    public IGroupMember getValue() throws CustGrpIdentifierException {
        IGroupMember value = new OBGroupMember();
        AccessorUtil.copyValue(this, value);
        return value;
    }

    public void setValue(IGroupMember obj) throws ConcurrentUpdateException, CustGrpIdentifierException {
        try {
            AccessorUtil.copyValue(obj, this, EXCLUDE_METHOD);
        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CustGrpIdentifierException("Exception in setValue: " + ex.toString());
        }
    }


    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_GROUP_MEMBER_SEQ;
    }

    // END HERE


    public void ejbPostCreate(IGroupMember obj) {
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


    public long getGroupMemberIDRef() {
        if (getCMPGroupMemberIDRef() != null) {
            return getCMPGroupMemberIDRef().longValue();
        }
        return ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGroupMemberIDRef(long str) {
        setCMPGroupMemberIDRef(new Long(str));
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


    public long getEntityID() {
        if (getCMPEntityID() != null) {
            return getCMPEntityID().longValue();
        }
        return ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setEntityID(long str) {
        setCMPEntityID(new Long(str));
    }

    //User others

    public long getGrpNo() {
        ICustGrpIdentifierDAO dao = CustGrpIdentifierDAOFactory.getDAO();
        IGroupMember obj = new OBGroupMember();
        obj.setEntityID(getEntityID());
        try {
            if (ICMSConstant.ENTITY_TYPE_GROUP.equals(getEntityType())) {
                IGroupMember newObj = dao.setGroupDetails(obj);
                if (newObj != null) {
                    return newObj.getGrpNo();
                }
            }

        } catch (SearchDAOException ex) {
            ex.printStackTrace();
        }
        return ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGrpNo(long grpNo) {
    }

    ;

    public String getEntityName() {
        return null;
    }

    public void setEntityName(String sourceID) {
    }

    ;

    public String getSourceID() {
        return null;
    }

    public void setSourceID(String sourceID) {
    }

    ;

    public String getlEIDSource() {
        return null;
    }

    public void setlEIDSource(String lEIDSource) {
    }

    ;

    public String getLeID_GroupID() {
        return null;
    }

    public void setLeID_GroupID(String leID_GroupID) {
    }

    ;

    public String getLmpLeID() {
        return null;
    }

    public void setLmpLeID(String lmpLeID) {
    }

    ;

    public String getIdNO() {
        return null;
    }

    public void setIdNO(String idNO) {
    }

    ;

    public String getIndexID() {
        return null;
    }

    public void setIndexID(String indexID) {
    }

    ;

    public String getNextPage() {
        return null;
    }

    public void setNextPage(String nextPage) {
    }

    ;

    public String getItemType() {
        return null;
    }

    public void setItemType(String itemType) {
    };

    public String getMembersCreditRating() {
        return null;
    }

    public void setMembersCreditRating(String itemType) {
    };

    public Amount getEntityLmt()
    {
        return null;
    }

    public void setEntityLmt(Amount amount) {
    };
    
    public abstract String getEntityType() ;
    public abstract void setEntityType(String entityType) ;

    public abstract String getRelationName() ;
    public abstract void setRelationName(String relationName) ;

    public abstract Double getPercentOwned() ;
    public abstract void setPercentOwned(Double percentOwned) ;


    public abstract String getRelBorMemberName() ;
    public abstract void setRelBorMemberName(String relBorMemberName) ;

}
