package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 15, 2007        ICustGrpIdentifier
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */


public interface ICustGrpIdentifier extends Serializable, IValueObject {

    public long getGrpNo();
    public void setGrpNo(long grpNo) ;
    
//// new
     public long getMasterGroupEntityID() ;
     public void setMasterGroupEntityID(long masterGroupEntityID) ;

    public long getGrpID() ;
    public void setGrpID(long GrpID);

    public String getGroupName() ;
    public void setGroupName(String groupName) ;


    public String getGroupType() ;
    public void setGroupType(String groupType) ;

    public String getAccountMgmt();
    public void setAccountMgmt(String accountMgmt) ;

    public String getGroupCounty() ;
    public void setGroupCounty(String groupCounty) ;

    public String getGroupCurrency();
    public void setGroupCurrency(String groupCounty) ;

    public String getBusinessUnit() ;
    public void setBusinessUnit(String businessUnit) ;

    public long getGroupAccountMgrID() ;
    public void setGroupAccountMgrID(long groupAccountMgrID) ;

    public String getApprovedBy();
    public void setApprovedBy(String approvedBy);

    public String getGroupRemarks() ;
    public void setGroupRemarks(String groupRemarks);

    public long getGrpIDRef() ;
    public void setGrpIDRef(long custGrpIDRef);

    public boolean getMasterGroupInd();
    public void  setMasterGroupInd(boolean masterGroupInd) ;

    public String getInternalLmt() ;
    public void setInternalLmt(String internalLmt);

    public Amount getGroupLmt() ;
    public void setGroupLmt(Amount groupLmt) ;

    public String getStatus() ;
    public void setStatus(String status) ;

    public Date getLastReviewDt();
    public void setLastReviewDt(Date lastReviewDt);

    public String getGroupAccountMgrCode() ;
    public void setGroupAccountMgrCode(String groupAccountMgrCode) ;

    public boolean getIsBGEL() ;
    public void setIsBGEL(boolean isBGEL) ;

    public IGroupCreditGrade[] getGroupCreditGrade();
    public void setGroupCreditGrade(IGroupCreditGrade[] aIGroupCreditGrade);

    public IGroupSubLimit[] getGroupSubLimit();
    public void setGroupSubLimit(IGroupSubLimit[] groupSubLimit) ;

    public IGroupMember[] getGroupMember();
    public void setGroupMember(IGroupMember[] groupMember);

    public IGroupOtrLimit[] getGroupOtrLimit();
    public void setGroupOtrLimit(IGroupOtrLimit[] groupOtrLimit) ;

}
