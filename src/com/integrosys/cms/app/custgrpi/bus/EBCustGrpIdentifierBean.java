package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.base.businfra.currency.CurrencyCode;

import javax.ejb.*;
import java.util.*;
import java.math.BigDecimal;


public abstract class EBCustGrpIdentifierBean implements EntityBean, ICustGrpIdentifier {


    private static final String[] EXCLUDE_METHOD = new String[]{"getGrpID"};

    private static long LONG_INVALID_VALUE = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

    public abstract void setCMPGrpID(Long cMPGrpID);

    public abstract Long getCMPGrpID();

    public abstract void setCMPGrpNo(Long cMPGrpNo);

    public abstract Long getCMPGrpNo();


    public long getGrpNo() {
        if (getCMPGrpNo() != null) {
            return getCMPGrpNo().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGrpNo(long grpNo) {
        setCMPGrpNo(new Long(grpNo));
    }


    public long getGrpID() {
        if (getCMPGrpID() != null) {
            return getCMPGrpID().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGrpID(long CustGrpID) {
        setCMPGrpID(new Long(CustGrpID));
    }

    public abstract Collection getGroupCreditGradeCMR();

    public abstract void setGroupCreditGradeCMR(Collection groupCreditGradeCMR);

    public abstract Collection getGroupMemberCMR();

    public abstract void setGroupMemberCMR(Collection groupMemberCMR);

    public abstract Collection getGroupSubLimitCMR();

    public abstract void setGroupSubLimitCMR(Collection groupSubLimitCMR);

    public abstract void setCMPGrpIDRef(Long cMPGrpIDRef);

    public abstract Long getCMPGrpIDRef();


    public abstract void setCMPGroupAccountMgrID(Long cMPGroupAccountMgrID);

    public abstract Long getCMPGroupAccountMgrID();

    public abstract void setCMPMasterGroupInd(String cMPMasterGroupInd);

    public abstract String getCMPMasterGroupInd();

    public abstract String getCMPStatus();

    public abstract void setCMPStatus(String cMPStatus);

    public abstract String getCMPInternalLmt();

    public abstract void setCMPInternalLmt(String cMPInternalLmt);

    public abstract BigDecimal getCMPGroupLmt();

    public abstract void setCMPGroupLmt(BigDecimal cMPGroupLmt);

    public abstract long getVersionTime();

    public abstract void setVersionTime(long value);

    public abstract void setCMPGroupAccountMgrCode(String cMPGroupAccountMgrCode);

    public abstract String getCMPGroupAccountMgrCode();

    public abstract void setCMPIsBGEL(String cMPIsBGEL);

    public abstract String getCMPIsBGEL();

    public abstract Collection getGroupOtrLimitCMR();

    public abstract void setGroupOtrLimitCMR(Collection groupOtrLimitCMR);

    public void setGroupLmt(Amount amount) {
        if (amount != null) {
            setCMPGroupLmt( amount.getAmountAsBigDecimal() );
        } else {
            setCMPGroupLmt(null);
        }
    }

    public Amount getGroupLmt() {
        if (getCMPGroupLmt() != null && getGroupCurrency() != null) {
            return new Amount( getCMPGroupLmt(), new CurrencyCode( getGroupCurrency() ) );
        } else {
            return null;
        }
    }

    public void setInternalLmt(String internalLmt) {
        setCMPInternalLmt(internalLmt);
    }


   public String getInternalLmt() {
       return getCMPInternalLmt();
    }

    public long getGrpIDRef() {
        if (getCMPGrpIDRef() != null) {
            return getCMPGrpIDRef().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGrpIDRef(long grpIDRef) {
        setCMPGrpIDRef(new Long(grpIDRef));
    }

    public long getGroupAccountMgrID() {
        if (getCMPGroupAccountMgrID() != null) {
            return getCMPGroupAccountMgrID().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setGroupAccountMgrID(long grpIDRef) {
        setCMPGroupAccountMgrID(new Long(grpIDRef));
    }

    public boolean getMasterGroupInd() {
        if (getCMPMasterGroupInd() != null && getCMPMasterGroupInd().equals(ICMSConstant.TRUE_VALUE)) {
            return true;
        }
        return false;
    }

    public void setMasterGroupInd(boolean deletedInd) {
        if (deletedInd) {
            setCMPMasterGroupInd(ICMSConstant.TRUE_VALUE);
            return;
        }
        setCMPMasterGroupInd(ICMSConstant.FALSE_VALUE);
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

    public String getGroupAccountMgrCode() {
        if (getCMPGroupAccountMgrCode() != null) {
            return getCMPGroupAccountMgrCode();
        }
        return null;
    }

    public void setGroupAccountMgrCode(String grpIDRef) {
        setCMPGroupAccountMgrCode(grpIDRef);
    }

    public boolean getIsBGEL() {
        if (getCMPIsBGEL() != null && getCMPIsBGEL().equals(ICMSConstant.TRUE_VALUE)) {
            return true;
        }
        return false;
    }

    public void setIsBGEL(boolean deletedInd) {
        if (deletedInd) {
            setCMPIsBGEL(ICMSConstant.TRUE_VALUE);
            return;
        }
        setCMPIsBGEL(ICMSConstant.FALSE_VALUE);
    }

    //START HERE

    public Long ejbCreate(ICustGrpIdentifier obj) throws CreateException {
        if (obj == null) {
            throw new CreateException("ICustGrpIdentifier is null!");
        }
        long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;


        try {
            pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
            Debug(" pk = " + pk);
            AccessorUtil.copyValue(obj, this, EXCLUDE_METHOD);

            setGrpID(pk);

            if (LONG_INVALID_VALUE == obj.getGrpIDRef() || obj.getGrpIDRef() == 0) {
                setGrpIDRef(pk);
            } else {
                setGrpIDRef(obj.getGrpIDRef());
            }

            if (LONG_INVALID_VALUE == obj.getGrpNo() || obj.getGrpNo() == 0) {
                long grpNo = Long.parseLong((new SequenceManager()).getSeqNum(getGroupNOSequenceName(), true));
                setGrpNo(grpNo);
            } else {
                setGrpNo(obj.getGrpNo());
            }

            setVersionTime(VersionGenerator.getVersionNumber());

            return new Long(pk);

        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CreateException("Exception at ejbCreate: " + ex.toString());
        }
    }

    public ICustGrpIdentifier getValue() throws CustGrpIdentifierException {
        ICustGrpIdentifier value = new OBCustGrpIdentifier();
        AccessorUtil.copyValue(this, value);

        IGroupSubLimit[] groupSubLimit = retrieveGroupSubLimitList();
        value.setGroupSubLimit(groupSubLimit);

        IGroupCreditGrade[] groupCreditGrade = retrieveGroupCreditGradeList();
        value.setGroupCreditGrade(groupCreditGrade);

        IGroupMember[] groupMember = retrieveGroupMemberList();
        value.setGroupMember(groupMember);

        IGroupOtrLimit[] groupOtrLimit = retrieveGroupOtrLimitList();
        value.setGroupOtrLimit(groupOtrLimit);

        return value;
    }

    public void setValue(ICustGrpIdentifier aICustGrpIdentifier) throws ConcurrentUpdateException, CustGrpIdentifierException {
        try {
            if (getVersionTime() != aICustGrpIdentifier.getVersionTime()) {
                throw new ConcurrentUpdateException("Mismatch timestamp");
            }
            AccessorUtil.copyValue(aICustGrpIdentifier, this, EXCLUDE_METHOD);

            setReferences(aICustGrpIdentifier, false);

            setVersionTime(VersionGenerator.getVersionNumber());
        } catch (ConcurrentUpdateException ex) {
            _context.setRollbackOnly();
            throw ex;
        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CustGrpIdentifierException("Exception in setValue: " + ex.toString());
        }
    }

	public void updateGroupLimitAmount (ICustGrpIdentifier aICustGrpIdentifier)
        throws ConcurrentUpdateException
    {
        if (getVersionTime() != aICustGrpIdentifier.getVersionTime()) {
			throw new ConcurrentUpdateException("Mismatch timestamp");
		}
        setGroupLmt ( aICustGrpIdentifier.getGroupLmt() );
        setVersionTime ( VersionGenerator.getVersionNumber() );
    }
	
    public void createDependants(ICustGrpIdentifier obj, long versionTime) throws VersionMismatchException {
        DefaultLogger.debug(this, "In createDependants");
        checkVersionMismatch(versionTime);
        this.setVersionTime(VersionGenerator.getVersionNumber());
        setReferences(obj, true);
    }

    protected void setReferences(ICustGrpIdentifier obj, boolean isAdd) {
        try {
            DefaultLogger.debug(this, "- In setReferences.");
            // updateGroupSubLimit(obj) ;
            // updateGroupMember(obj) ;
            // updateGroupCreditGrade(obj) ;
            setGroupSubLimitRef(obj.getGroupSubLimit(), isAdd);
            setGroupMemberRef(obj.getGroupMember(), isAdd);
            setGroupCreditGradeRef(obj.getGroupCreditGrade(), isAdd);
            setGroupOtrLimitRef(obj.getGroupOtrLimit(), isAdd);

        } catch (Exception e) {
            throw new EJBException(e);
        }
    }


    // Start Business Method for GroupSubLimit
    protected void setGroupSubLimitRef(IGroupSubLimit[] subLimitArray, boolean isAdd)
            throws CreateException, CustGrpIdentifierException, ConcurrentUpdateException {
        if (subLimitArray == null || subLimitArray.length == 0) {
            removeALLGroupSubLimit();
            return;
        }

        EBGroupSubLimitLocalHome ejbHome = getEBGroupSubLimitLocalHome();

        Collection c = getGroupSubLimitCMR();

        if (isAdd || c.size() == 0) {
            for (int i = 0; i < subLimitArray.length; i++) {
                IGroupSubLimit obj = new OBGroupSubLimit(subLimitArray[i]);
                obj.setGrpID(getGrpID());
                c.add(ejbHome.create(obj));
            }
            return;
        }

        deleteGroupSubLimit(c, subLimitArray);

        Iterator iterator = c.iterator();
        ArrayList newObjList = new ArrayList();

        for (int i = 0; i < subLimitArray.length; i++) {
            IGroupSubLimit newObj = new OBGroupSubLimit(subLimitArray[i]);
            newObj.setGrpID(getGrpID());
            boolean found = false;

            while (iterator.hasNext()) {
                EBGroupSubLimitLocal theEjb = (EBGroupSubLimitLocal) iterator.next();
                IGroupSubLimit value = theEjb.getValue();
                if (value.getStatus() != null && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    continue;
                }

                if (newObj.getGroupSubLimitIDRef() == value.getGroupSubLimitIDRef()) {
                    theEjb.setValue(newObj);
                    found = true;
                    break;
                }
            }
            if (!found) {
                newObjList.add(newObj);
            }
            iterator = c.iterator();
        }

        iterator = newObjList.iterator();

        while (iterator.hasNext()) {
            c.add(ejbHome.create((IGroupSubLimit) iterator.next()));
        }
    }

    private void removeALLGroupSubLimit() {
        Collection c = getGroupSubLimitCMR();
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
            EBGroupSubLimitLocal theEjb = (EBGroupSubLimitLocal) iterator.next();
            deleteGroupSubLimit(theEjb);
        }
    }

    private void deleteGroupSubLimit(Collection col, IGroupSubLimit[] anIList) throws CustGrpIdentifierException {

        Debug(" new Before deleteGroupSubLimit ");

        try {
            Iterator iterator = col.iterator();
            while (iterator.hasNext()) {
                EBGroupSubLimitLocal theEjb = (EBGroupSubLimitLocal) iterator.next();
                IGroupSubLimit EjbObj = theEjb.getValue();
                if (EjbObj.getStatus() != null && EjbObj.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    continue;
                }
                boolean found = false;
                if (anIList != null && anIList.length > 0) {
                    for (int i = 0; i < anIList.length; i++) {
                        IGroupSubLimit delObj = anIList[i];
                        if (delObj.getGroupSubLimitIDRef() == EjbObj.getGroupSubLimitIDRef()) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    Debug(" To be deleted deleteGroupSubLimit_New " + theEjb.getGroupSubLimitIDRef());
                    deleteGroupSubLimit(theEjb);
                }
            }
        } catch (Exception ex) {
            throw new CustGrpIdentifierException("Exception in deleteGroupSubLimit: " + ex.toString());
        }
    }

    private void deleteGroupSubLimit(EBGroupSubLimitLocal theEjb) {
        theEjb.setStatus(ICMSConstant.STATE_DELETED);
    }

    private IGroupSubLimit[] retrieveGroupSubLimitList() throws CustGrpIdentifierException {

        ArrayList list = new ArrayList();
        try {
            Collection coll = getGroupSubLimitCMR();
            if (coll == null || coll.size() == 0) {
                return null;
            } else {
                Iterator iter = coll.iterator();
                while (iter.hasNext()) {
                    EBGroupSubLimitLocal local = (EBGroupSubLimitLocal) iter.next();
                    IGroupSubLimit obj = local.getValue();
                    if (!(ICMSConstant.STATE_DELETED.equals(obj.getStatus()))) {
                        list.add(obj);
                    }
                }
                return (IGroupSubLimit[]) list.toArray(new IGroupSubLimit[0]);
            }

        } catch (Exception ex) {
            throw new CustGrpIdentifierException("Exception at retrieveGroupSubLimitList: " + ex.toString());
        }

    }
    // End Business Method for GroupSubLimit

    // Start Business Method for GroupOtrLimit
    protected void setGroupOtrLimitRef(IGroupOtrLimit[] otrLimitArray, boolean isAdd)
            throws CreateException, CustGrpIdentifierException, ConcurrentUpdateException {
        if (otrLimitArray == null || otrLimitArray.length == 0) {
            removeALLGroupOtrLimit();
            return;
        }

        EBGroupOtrLimitLocalHome ejbHome = getEBGroupOtrLimitLocalHome();

        Collection c = getGroupOtrLimitCMR();

        if (isAdd || c.size() == 0) {
            for (int i = 0; i < otrLimitArray.length; i++) {
                IGroupOtrLimit obj = new OBGroupOtrLimit(otrLimitArray[i]);
                obj.setGrpID(getGrpID());
                c.add(ejbHome.create(obj));
            }
            return;
        }

        deleteGroupOtrLimit(c, otrLimitArray);

        Iterator iterator = c.iterator();
        ArrayList newObjList = new ArrayList();

        for (int i = 0; i < otrLimitArray.length; i++) {
            IGroupOtrLimit newObj = new OBGroupOtrLimit(otrLimitArray[i]);
            newObj.setGrpID(getGrpID());
            boolean found = false;

            while (iterator.hasNext()) {
                EBGroupOtrLimitLocal theEjb = (EBGroupOtrLimitLocal) iterator.next();
                IGroupOtrLimit value = theEjb.getValue();
                if (value.getStatus() != null && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    continue;
                }

                if (newObj.getGroupOtrLimitIDRef() == value.getGroupOtrLimitIDRef()) {
                    theEjb.setValue(newObj);
                    found = true;
                    break;
                }
            }
            if (!found) {
                newObjList.add(newObj);
            }
            iterator = c.iterator();
        }

        iterator = newObjList.iterator();

        while (iterator.hasNext()) {
            c.add(ejbHome.create((IGroupOtrLimit) iterator.next()));
        }
    }

    private void removeALLGroupOtrLimit() {
        Collection c = getGroupOtrLimitCMR();
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
            EBGroupOtrLimitLocal theEjb = (EBGroupOtrLimitLocal) iterator.next();
            deleteGroupOtrLimit(theEjb);
        }
    }

    private void deleteGroupOtrLimit(Collection col, IGroupOtrLimit[] anIList) throws CustGrpIdentifierException {

        Debug(" new Before deleteGroupOtrLimit ");

        try {
            Iterator iterator = col.iterator();
            while (iterator.hasNext()) {
                EBGroupOtrLimitLocal theEjb = (EBGroupOtrLimitLocal) iterator.next();
                IGroupOtrLimit EjbObj = theEjb.getValue();
                if (EjbObj.getStatus() != null && EjbObj.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    continue;
                }
                boolean found = false;
                if (anIList != null && anIList.length > 0) {
                    for (int i = 0; i < anIList.length; i++) {
                        IGroupOtrLimit delObj = anIList[i];
                        if (delObj.getGroupOtrLimitIDRef() == EjbObj.getGroupOtrLimitIDRef()) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    Debug(" To be deleted deleteGroupOtrLimit_New " + theEjb.getGroupOtrLimitIDRef());
                    deleteGroupOtrLimit(theEjb);
                }
            }
        } catch (Exception ex) {
            throw new CustGrpIdentifierException("Exception in deleteGroupOtrLimit: " + ex.toString());
        }
    }

    private void deleteGroupOtrLimit(EBGroupOtrLimitLocal theEjb) {
        theEjb.setStatus(ICMSConstant.STATE_DELETED);
    }

    private IGroupOtrLimit[] retrieveGroupOtrLimitList() throws CustGrpIdentifierException {

        ArrayList list = new ArrayList();
        try {
            Collection coll = getGroupOtrLimitCMR();
            if (coll == null || coll.size() == 0) {
                return null;
            } else {
                Iterator iter = coll.iterator();
                while (iter.hasNext()) {
                    EBGroupOtrLimitLocal local = (EBGroupOtrLimitLocal) iter.next();
                    IGroupOtrLimit obj = local.getValue();
                    if (!(ICMSConstant.STATE_DELETED.equals(obj.getStatus()))) {
                        list.add(obj);
                    }
                }
                return (IGroupOtrLimit[]) list.toArray(new IGroupOtrLimit[0]);
            }

        } catch (Exception ex) {
            throw new CustGrpIdentifierException("Exception at retrieveGroupOtrLimitList: " + ex.toString());
        }

    }
    // End Business Method for GroupSubLimit

    // Start Business Method for GroupMember

    protected void setGroupMemberRef(IGroupMember[] subLimitArray, boolean isAdd)
            throws CreateException, CustGrpIdentifierException, ConcurrentUpdateException {
        if (subLimitArray == null || subLimitArray.length == 0) {
            removeALLGroupMember();
            return;
        }

        EBGroupMemberLocalHome ejbHome = getEBGroupMemberLocalHome();

        Collection c = getGroupMemberCMR();

        if (isAdd || c.size() == 0) {
            for (int i = 0; i < subLimitArray.length; i++) {
                IGroupMember obj = new OBGroupMember(subLimitArray[i]);
                obj.setGrpID(getGrpID());
                c.add(ejbHome.create(obj));
            }
            return;
        }

        deleteGroupMember(c, subLimitArray);

        Iterator iterator = c.iterator();
        ArrayList newObjList = new ArrayList();

        for (int i = 0; i < subLimitArray.length; i++) {
            IGroupMember newObj = new OBGroupMember(subLimitArray[i]);
            newObj.setGrpID(getGrpID());
            boolean found = false;

            while (iterator.hasNext()) {
                EBGroupMemberLocal theEjb = (EBGroupMemberLocal) iterator.next();
                IGroupMember value = theEjb.getValue();
                if (value.getStatus() != null && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    continue;
                }

                if (newObj.getGroupMemberIDRef() == value.getGroupMemberIDRef()) {
                    theEjb.setValue(newObj);
                    found = true;
                    break;
                }
            }
            if (!found) {
                newObjList.add(newObj);
            }
            iterator = c.iterator();
        }

        iterator = newObjList.iterator();

        while (iterator.hasNext()) {
            c.add(ejbHome.create((IGroupMember) iterator.next()));
        }
    }

    private void removeALLGroupMember() {
        Collection c = getGroupMemberCMR();
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
            EBGroupMemberLocal theEjb = (EBGroupMemberLocal) iterator.next();
            deleteGroupMember(theEjb);
        }
    }

    private void deleteGroupMember(Collection col, IGroupMember[] anIList) throws CustGrpIdentifierException {

        Debug(" new Before deleteGroupMember ");

        try {
            Iterator iterator = col.iterator();
            while (iterator.hasNext()) {
                EBGroupMemberLocal theEjb = (EBGroupMemberLocal) iterator.next();
                IGroupMember EjbObj = theEjb.getValue();
                if (EjbObj.getStatus() != null && EjbObj.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    continue;
                }
                boolean found = false;
                if (anIList != null && anIList.length > 0) {
                    for (int i = 0; i < anIList.length; i++) {
                        IGroupMember delObj = anIList[i];
                        if (delObj.getGroupMemberIDRef() == EjbObj.getGroupMemberIDRef()) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    Debug(" To be deleted deleteGroupMember_New " + theEjb.getGroupMemberIDRef());
                    deleteGroupMember(theEjb);
                }
            }
        } catch (Exception ex) {
            throw new CustGrpIdentifierException("Exception in deleteGroupMember: " + ex.toString());
        }
    }

    private void deleteGroupMember(EBGroupMemberLocal theEjb) {
        theEjb.setStatus(ICMSConstant.STATE_DELETED);
    }

    private IGroupMember[] retrieveGroupMemberList() throws CustGrpIdentifierException {

        ArrayList list = new ArrayList();
        try {
            Collection coll = getGroupMemberCMR();
            if (coll == null || coll.size() == 0) {
                return null;
            } else {
                Iterator iter = coll.iterator();
                while (iter.hasNext()) {
                    EBGroupMemberLocal local = (EBGroupMemberLocal) iter.next();
                    IGroupMember obj = local.getValue();
                    if (!(ICMSConstant.STATE_DELETED.equals(obj.getStatus()))) {
                        list.add(obj);
                    }
                }
                setEntityDetails(list);
                return (IGroupMember[]) list.toArray(new IGroupMember[0]);
            }

        } catch (Exception ex) {
            throw new CustGrpIdentifierException("Exception at retrieveGroupMemberList: " + ex.toString());
        }

    }

    protected void setEntityDetails(List list) {
        ICustGrpIdentifierDAO dao = CustGrpIdentifierDAOFactory.getDAO();
        if (list != null && !list.isEmpty()) {
            try {
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    IGroupMember obj = (IGroupMember) iter.next();
                    if (obj != null && obj.getEntityType().equals(ICMSConstant.ENTITY_TYPE_CUSTOMER)) {
                        dao.setCustomerDetails(obj);
                    } else {
                        dao.setGroupDetails(obj);
                    }
                }
            } catch (SearchDAOException ex) {
                ex.printStackTrace();
            }
        }

    }
    // End Business Method for GroupMember

    // Start Business Method for Group Grade

    protected void setGroupCreditGradeRef(IGroupCreditGrade[] subLimitArray, boolean isAdd)
            throws CreateException, CustGrpIdentifierException, ConcurrentUpdateException {
        if (subLimitArray == null || subLimitArray.length == 0) {
            removeALLGroupCreditGrade();
            return;
        }

        EBGroupCreditGradeLocalHome ejbHome = getEBGroupCreditGradeLocalHome();

        Collection c = getGroupCreditGradeCMR();

        if (isAdd || c.size() == 0) {
            for (int i = 0; i < subLimitArray.length; i++) {
                IGroupCreditGrade obj = new OBGroupCreditGrade(subLimitArray[i]);
                obj.setGrpID(getGrpID());
                c.add(ejbHome.create(obj));
            }
            return;
        }

        deleteGroupCreditGrade(c, subLimitArray);

        Iterator iterator = c.iterator();
        ArrayList newObjList = new ArrayList();

        for (int i = 0; i < subLimitArray.length; i++) {
            IGroupCreditGrade newObj = new OBGroupCreditGrade(subLimitArray[i]);
            newObj.setGrpID(getGrpID());
            boolean found = false;

            while (iterator.hasNext()) {
                EBGroupCreditGradeLocal theEjb = (EBGroupCreditGradeLocal) iterator.next();
                IGroupCreditGrade value = theEjb.getValue();
                if (value.getStatus() != null && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    continue;
                }

                if (newObj.getGroupCreditGradeIDRef() == value.getGroupCreditGradeIDRef()) {
                    theEjb.setValue(newObj);
                    found = true;
                    break;
                }
            }
            if (!found) {
                newObjList.add(newObj);
            }
            iterator = c.iterator();
        }

        iterator = newObjList.iterator();

        while (iterator.hasNext()) {
            c.add(ejbHome.create((IGroupCreditGrade) iterator.next()));
        }
    }

    private void removeALLGroupCreditGrade() {
        Collection c = getGroupCreditGradeCMR();
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
            EBGroupCreditGradeLocal theEjb = (EBGroupCreditGradeLocal) iterator.next();
            deleteGroupCreditGrade(theEjb);
        }
    }

    private void deleteGroupCreditGrade(Collection col, IGroupCreditGrade[] anIList) throws CustGrpIdentifierException {

        Debug(" new Before deleteGroupCreditGrade ");

        try {
            Iterator iterator = col.iterator();
            while (iterator.hasNext()) {
                EBGroupCreditGradeLocal theEjb = (EBGroupCreditGradeLocal) iterator.next();
                IGroupCreditGrade EjbObj = theEjb.getValue();
                if (EjbObj.getStatus() != null && EjbObj.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    continue;
                }
                boolean found = false;
                if (anIList != null && anIList.length > 0) {
                    for (int i = 0; i < anIList.length; i++) {
                        IGroupCreditGrade delObj = anIList[i];
                        if (delObj.getGroupCreditGradeIDRef() == EjbObj.getGroupCreditGradeIDRef()) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    Debug(" To be deleted deleteGroupCreditGrade_New " + theEjb.getGroupCreditGradeIDRef());
                    deleteGroupCreditGrade(theEjb);
                }
            }
        } catch (Exception ex) {
            throw new CustGrpIdentifierException("Exception in deleteGroupCreditGrade: " + ex.toString());
        }
    }

    private void deleteGroupCreditGrade(EBGroupCreditGradeLocal theEjb) {
        theEjb.setStatus(ICMSConstant.STATE_DELETED);
    }

    private IGroupCreditGrade[] retrieveGroupCreditGradeList() throws CustGrpIdentifierException {

        ArrayList list = new ArrayList();
        try {
            Collection coll = getGroupCreditGradeCMR();
            if (coll == null || coll.size() == 0) {
                return null;
            } else {
                Iterator iter = coll.iterator();
                while (iter.hasNext()) {
                    EBGroupCreditGradeLocal local = (EBGroupCreditGradeLocal) iter.next();
                    IGroupCreditGrade obj = local.getValue();
                    if (!(ICMSConstant.STATE_DELETED.equals(obj.getStatus()))) {
                        list.add(obj);
                    }
                }

                return (IGroupCreditGrade[]) list.toArray(new IGroupCreditGrade[0]);
            }

        } catch (Exception ex) {
            throw new CustGrpIdentifierException("Exception at retrieveGroupCreditGradeList: " + ex.toString());
        }

    }
    // End Business Method for Group Grade


    private void checkVersionMismatch(long versionTime) throws VersionMismatchException {
        if (getVersionTime() != versionTime) {
            throw new VersionMismatchException("Mismatch timestamp! " + versionTime);
        }
    }


    // JNDI Names Here
    protected EBGroupSubLimitLocalHome getEBGroupSubLimitLocalHome() {
        EBGroupSubLimitLocalHome ejbHome = (EBGroupSubLimitLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_SUBLIMIT_LOCAL_JNDI, EBGroupSubLimitLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupSubLimitLocalHome is Null!");
        }

        return ejbHome;
    }

    protected EBGroupOtrLimitLocalHome getEBGroupOtrLimitLocalHome() {
        EBGroupOtrLimitLocalHome ejbHome = (EBGroupOtrLimitLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_OTRLIMIT_LOCAL_JNDI, EBGroupOtrLimitLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupOtrLimitLocalHome is Null!");
        }

        return ejbHome;
    }


    protected EBGroupMemberLocalHome getEBGroupMemberLocalHome() {
        EBGroupMemberLocalHome ejbHome = (EBGroupMemberLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_MEMBER_LOCAL_JNDI, EBGroupMemberLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupMemberLocalHome is Null!");
        }

        return ejbHome;
    }

    protected EBGroupCreditGradeLocalHome getEBGroupCreditGradeLocalHome() {
        EBGroupCreditGradeLocalHome ejbHome = (EBGroupCreditGradeLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_CREDIT_GRADE_LOCAL_JNDI, EBGroupCreditGradeLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupCreditGradeLocalHome is Null!");
        }

        return ejbHome;
    }

    //end Here

    /**
     * Return default DAO
     *
     * @return CustGrpIdentifierDAO
     */

    protected ICustGrpIdentifierDAO getDAO() {
        return new CustGrpIdentifierDAO();
    }

    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_CUST_GRP_SEQ;
    }

    protected String getGroupNOSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_CUST_GRP_NO_SEQ;
    }

    // END HERE


    public void ejbPostCreate(ICustGrpIdentifier aICustGrpIdentifier) {
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


    public String getLegalName() {
        return null;
    }

    public void setLegalName(String str) {
    }


    public String getCustomerName() {
        return null;
    }

    public void setCustomerName(String str) {
    }


    public long getLegalID() {
        return ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setLegalID(long str) {
    }


    public String getIdNO() {
        return null;
    }

    public void setIdNO(String customerName) {
    }


    public String getLeIDType() {
        return null;
    }

    public void setLeIDType(String str) {
    }


    public String getDeleteCustomerID() {
        return null;
    }

    public void setDeleteCustomerID(String str) {
    }


    public long getSubProfileID() {
        return ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setSubProfileID(long str) {
    }


    public String getSourceID() {
        return null;
    }

    public void setSourceID(String str) {
    }


    //     for Group Credit Grade
    public IGroupCreditGrade[] getGroupCreditGrade() {
        ArrayList arrList = new ArrayList();
        Collection col = getGroupCreditGradeCMR();
        try {
            Iterator i = col.iterator();
            while (i.hasNext()) {
                EBGroupCreditGradeLocal theEjb = (EBGroupCreditGradeLocal) i.next();
                IGroupCreditGrade obj = theEjb.getValue();
                if (!ICMSConstant.STATE_DELETED.equals(obj.getStatus())) {
                    arrList.add(obj);
                }
            }
        } catch (Exception e) {

        }
        return (IGroupCreditGrade[]) arrList.toArray(new OBGroupCreditGrade[0]);
    }

    public void setGroupCreditGrade(IGroupCreditGrade[] objArray) {
        // do nothing
    }

    //     for Group Sub Limit
    public IGroupSubLimit[] getGroupSubLimit() {
        ArrayList arrList = new ArrayList();
        Collection col = getGroupSubLimitCMR();
        try {
            Iterator i = col.iterator();
            while (i.hasNext()) {
                EBGroupSubLimitLocal theEjb = (EBGroupSubLimitLocal) i.next();
                IGroupSubLimit obj = theEjb.getValue();
                if (!ICMSConstant.STATE_DELETED.equals(obj.getStatus())) {
                    arrList.add(obj);
                }
            }
        } catch (Exception e) {

        }
        return (IGroupSubLimit[]) arrList.toArray(new OBGroupSubLimit[0]);
    }

    public void setGroupSubLimit(IGroupSubLimit[] objArray) {
        // do nothing
    }

    public void setGroupOtrLimit(IGroupOtrLimit[] objArray) {
        // do nothing
    }

    public IGroupMember[] getGroupMember() {

        ArrayList arrList = new ArrayList();
        Collection col = getGroupMemberCMR();
        try {
            Iterator i = col.iterator();
            while (i.hasNext()) {
                EBGroupMemberLocal theEjb = (EBGroupMemberLocal) i.next();
                IGroupMember obj = theEjb.getValue();
                if (!ICMSConstant.STATE_DELETED.equals(obj.getStatus())) {
                    arrList.add(obj);
                }
            }
        } catch (Exception e) {

        }
        return (IGroupMember[]) arrList.toArray(new OBGroupMember[0]);
    }

    //     for Group Other Limit
    public IGroupOtrLimit[] getGroupOtrLimit() {
        ArrayList arrList = new ArrayList();
        Collection col = getGroupOtrLimitCMR();
        try {
            Iterator i = col.iterator();
            while (i.hasNext()) {
                EBGroupOtrLimitLocal theEjb = (EBGroupOtrLimitLocal) i.next();
                IGroupOtrLimit obj = theEjb.getValue();
                if (!ICMSConstant.STATE_DELETED.equals(obj.getStatus())) {
                    arrList.add(obj);
                }
            }
        } catch (Exception e) {

        }
        return (IGroupOtrLimit[]) arrList.toArray(new OBGroupOtrLimit[0]);
    }

    public void setGroupMember(IGroupMember[] groupMember) {
        // do nothing
    }


    public void setGroupAccountMgrName(String str) {
    }

    public String getGroupAccountMgrName() {
        return null;
    }

    public long getMasterGroupEntityID() {
        return ICMSConstant.LONG_INVALID_VALUE;
    }

    public void setMasterGroupEntityID(long masterGroupEntityID) {

    }


    /**
     * helper method tp print
     *
     * @param msg
     */
    private void Debug(String msg) {
    	DefaultLogger.debug(this,"EBCustGrpIdentifierBean = " + msg);
    }


    public SearchResult ejbHomeSearchEntryDetails(GroupMemberSearchCriteria obj) throws SearchDAOException {
        ICustGrpIdentifierDAO dao = CustGrpIdentifierDAOFactory.getDAO();
        return dao.searchEntryDetails(obj);
    }

    public SearchResult ejbHomeSearchGroup(CustGrpIdentifierSearchCriteria obj) throws SearchDAOException {
        ICustGrpIdentifierDAO dao = CustGrpIdentifierDAOFactory.getDAO();
        return dao.searchGroup(obj);
    }

    public Map ejbHomeGetGroupAccountMgrCodes(Map inputMap) throws SearchDAOException {
        ICustGrpIdentifierDAO dao = CustGrpIdentifierDAOFactory.getDAO();
        return dao.getGroupAccountMgr(inputMap);
    }

    public Amount ejbHomeGetGroupLimit(String intLmt, String rating) throws SearchDAOException {
        ICustGrpIdentifierDAO dao = CustGrpIdentifierDAOFactory.getDAO();
        return dao.getGroupLimit(intLmt, rating);
    }

    public List ejbHomeSetEntityDetails(List list) {
        ICustGrpIdentifierDAO dao = CustGrpIdentifierDAOFactory.getDAO();
        List returnList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            try {
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    IGroupMember obj = (IGroupMember) iter.next();
                    if (obj != null && obj.getEntityType().equals(ICMSConstant.ENTITY_TYPE_CUSTOMER)) {
                        dao.setCustomerDetails(obj);
                    } else {
                        dao.setGroupDetails(obj);
                    }
                    returnList.add(obj);
                }
            } catch (SearchDAOException ex) {
                ex.printStackTrace();
            }
        }
        return returnList;
    }
    
    public abstract String getGroupName() ;
    public abstract void setGroupName(String groupName) ;

    public abstract String getGroupType() ;
    public abstract void setGroupType(String groupType) ;

    public abstract String getAccountMgmt();
    public abstract void setAccountMgmt(String accountMgmt) ;

    public abstract String getGroupCounty() ;
    public abstract void setGroupCounty(String groupCounty) ;

    public abstract String getGroupCurrency();
    public abstract void setGroupCurrency(String groupCounty) ;

    public abstract String getBusinessUnit() ;
    public abstract void setBusinessUnit(String businessUnit) ;

    public abstract String getApprovedBy();
    public abstract void setApprovedBy(String approvedBy);

    public abstract String getGroupRemarks() ;
    public abstract void setGroupRemarks(String groupRemarks);

    public abstract Date getLastReviewDt();
    public abstract void setLastReviewDt(Date lastReviewDt);

}
