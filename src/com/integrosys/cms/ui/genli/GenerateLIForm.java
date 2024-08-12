package com.integrosys.cms.ui.genli;

import java.util.Locale;

import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.IGroupMember;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.ui.common.TrxContextForm;

public class GenerateLIForm extends TrxContextForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String grpNo  ;

    // For  Group Profile
    private String grpIDRef;
    private String grpID;
    //private String grpEntityID;
    private String masterGroupEntityID;
    private String masterGroupInd;

    private String groupTypeCD = "";
    private String accountMgmtCD = "";
    private String groupName = "";
    private String groupAccountMgrID = "";
    private String groupAccountMgrName = "";
    private String countyCD = "";
    private String currencyCD = "";
    private String businessUnit = "";
    private String lastReviewDt = "";
    private String groupRemarks = "";



    private String approvedBy = "";

    private String internalLmt = "";
    private String groupLmt = "";
    private String status = "";

     private String description = "";

    // for used in delete the Item
    private String[] deleteItem;


    private String customerSeach = "";
    private String gobutton = "";
    private String all = "";
    private Locale locale;


    // used in Group Credit Grade in Sub Heading
    private String itemType;
    public IGroupCreditGrade[] groupCreditGrade;
    public IGroupSubLimit[] groupSubLimit;
    public IGroupMember[] groupMember;


    // Customer Info for search

    private String searchGroupID;
    private String searchGroupName = "";

    private String searchType;
    private String userID = "";
    private String subProfileID = "";
    private String legalName = "";
    private String legalIdSub = "";

    private String customerName = "";
    private String legalID = "";
    private String leIDType = "";
    private String idNO = "";


    // used in Group Member  in Sub Heading
    private String[] groupMemberID;
    private String[] groupMemberIDRef;
    private String[] entityID;
    private String[] entityType;
    private String[] entityName;
    private String[] relationName;
    private String[] percentOwned;
    private String[] relBorMemberName;
    private String[] membersCreditRating;
    private String[] sourceID;
    private String[] lEIDSource;
    private String[] lmpLeID;


    public String getGrpNo() {
        return grpNo;
    }

    public void setGrpNo(String grpNo) {
        this.grpNo = grpNo;
    }


    public String getSearchGroupID() {
        return searchGroupID;
    }

    public void setSearchGroupID(String searchGroupID) {
        this.searchGroupID = searchGroupID;
    }

    public String getSearchGroupName() {
        return searchGroupName;
    }

    public void setSearchGroupName(String searchGroupName) {
        this.searchGroupName = searchGroupName;
    }


    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }


    public String getCustomerSeach() {
        return customerSeach;
    }

    public void setCustomerSeach(String customerSeach) {
        this.customerSeach = customerSeach;
    }


    public String getGroupAccountMgrID() {
        return groupAccountMgrID;
    }

    public void setGroupAccountMgrID(String groupAccountMgrID) {
        this.groupAccountMgrID = groupAccountMgrID;
    }


    public IGroupMember[] getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(IGroupMember[] groupMember) {
        this.groupMember = groupMember;
    }


    public IGroupSubLimit[] getGroupSubLimit() {
        return groupSubLimit;
    }

    public void setGroupSubLimit(IGroupSubLimit[] groupSubLimit) {
        this.groupSubLimit = groupSubLimit;
    }


    public IGroupCreditGrade[] getGroupCreditGrade() {
        return groupCreditGrade;
    }

    public void setGroupCreditGrade(IGroupCreditGrade[] groupCreditGrade) {
        this.groupCreditGrade = groupCreditGrade;
    }


    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }


    public String[] getDeleteItem() {
        return deleteItem;
    }

    public void setDeleteItem(String[] deleteItem) {
        this.deleteItem = deleteItem;
    }


    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* public String getGrpEntityID() {
        return grpEntityID;
    }

    public void setGrpEntityID(String grpEntityID) {
        this.grpEntityID = grpEntityID;
    }*/


    public String getGrpIDRef() {
        return grpIDRef;
    }

    public void setGrpIDRef(String grpIDRef) {
        this.grpIDRef = grpIDRef;
    }

    public String getGrpID() {
        return grpID;
    }

    public void setGrpID(String grpID) {
        this.grpID = grpID;
    }

    public String getMasterGroupEntityID() {
        return masterGroupEntityID;
    }

    public void setMasterGroupEntityID(String masterGroupEntityID) {
        this.masterGroupEntityID = masterGroupEntityID;
    }


    public String getMasterGroupInd() {
        return masterGroupInd;
    }

    public void setMasterGroupInd(String masterGroupInd) {
        this.masterGroupInd = masterGroupInd;
    }

    public String getGroupTypeCD() {
        return groupTypeCD;
    }

    public void setGroupTypeCD(String groupTypeCD) {
        this.groupTypeCD = groupTypeCD;
    }

    public String getAccountMgmtCD() {
        return accountMgmtCD;
    }

    public void setAccountMgmtCD(String accountMgmtCD) {
        this.accountMgmtCD = accountMgmtCD;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCountyCD() {
        return countyCD;
    }

    public void setCountyCD(String countyCD) {
        this.countyCD = countyCD;
    }

    public String getCurrencyCD() {
        return currencyCD;
    }

    public void setCurrencyCD(String currencyCD) {
        this.currencyCD = currencyCD;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getLastReviewDt() {
        return lastReviewDt;
    }

    public void setLastReviewDt(String lastReviewDt) {
        this.lastReviewDt = lastReviewDt;
    }

    public String getGroupRemarks() {
        return groupRemarks;
    }

    public void setGroupRemarks(String groupRemarks) {
        this.groupRemarks = groupRemarks;
    }


    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }


    public String getInternalLmt() {
        return internalLmt;
    }

    public void setInternalLmt(String internalLmt) {
        this.internalLmt = internalLmt;
    }

    public String getGroupLmt() {
        return groupLmt;
    }

    public void setGroupLmt(String groupLmt) {
        this.groupLmt = groupLmt;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSubProfileID() {
        return subProfileID;
    }

    public void setSubProfileID(String subProfileID) {
        this.subProfileID = subProfileID;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalIdSub() {
        return legalIdSub;
    }

    public void setLegalIdSub(String legalIdSub) {
        this.legalIdSub = legalIdSub;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLegalID() {
        return legalID;
    }

    public void setLegalID(String legalID) {
        this.legalID = legalID;
    }

    public String getLeIDType() {
        return leIDType;
    }

    public void setLeIDType(String leIDType) {
        this.leIDType = leIDType;
    }

    public String getIdNO() {
        return idNO;
    }

    public void setIdNO(String idNO) {
        this.idNO = idNO;
    }

    public String getGobutton() {
        return gobutton;
    }

    public void setGobutton(String gobutton) {
        this.gobutton = gobutton;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }





    public void reset() {
        setCustomerName("");
        setLegalID("");
        setLeIDType("");
        setIdNO("");
    }


    public String[] getGroupMemberID() {
        return groupMemberID;
    }

    public void setGroupMemberID(String[] groupMemberID) {
        this.groupMemberID = groupMemberID;
    }

    public String[] getGroupMemberIDRef() {
        return groupMemberIDRef;
    }

    public void setGroupMemberIDRef(String[] groupMemberIDRef) {
        this.groupMemberIDRef = groupMemberIDRef;
    }


    public String[] getEntityID() {
        return entityID;
    }

    public void setEntityID(String[] entityID) {
        this.entityID = entityID;
    }

    public String[] getEntityType() {
        return entityType;
    }

    public void setEntityType(String[] entityType) {
        this.entityType = entityType;
    }

    public String[] getEntityName() {
        return entityName;
    }

    public void setEntityName(String[] entityName) {
        this.entityName = entityName;
    }

    public String[] getRelationName() {
        return relationName;
    }

    public void setRelationName(String[] relationName) {
        this.relationName = relationName;
    }


    public String[] getPercentOwned() {
        return percentOwned;
    }

    public void setPercentOwned(String[] percentOwned) {
        this.percentOwned = percentOwned;
    }

    public String[] getRelBorMemberName() {
        return relBorMemberName;
    }

    public void setRelBorMemberName(String[] relBorMemberName) {
        this.relBorMemberName = relBorMemberName;
    }

    public String[] getMembersCreditRating() {
        return membersCreditRating;
    }

    public void setMembersCreditRating(String[] membersCreditRating) {
        this.membersCreditRating = membersCreditRating;
    }

    public String[] getSourceID() {
        return sourceID;
    }

    public void setSourceID(String[] sourceID) {
        this.sourceID = sourceID;
    }

    public String[] getlEIDSource() {
        return lEIDSource;
    }

    public void setlEIDSource(String[] lEIDSource) {
        this.lEIDSource = lEIDSource;
    }

    public String[] getLmpLeID() {
        return lmpLeID;
    }

    public void setLmpLeID(String[] lmpLeID) {
        this.lmpLeID = lmpLeID;
    }

    public void setGroupAccountMgrName(String name ) {
        this.groupAccountMgrName=name;
    }

    public String getGroupAccountMgrName() {
        return groupAccountMgrName;
    }


    public String toString() {
        return "CustGrpIdentifierForm{" +
                "grpIDRef='" + grpIDRef + '\'' +
                ", grpID='" + grpID + '\'' +
                ", masterGroupInd='" + masterGroupInd + '\'' +
                ", groupTypeCD='" + groupTypeCD + '\'' +
                ", accountMgmtCD='" + accountMgmtCD + '\'' +
                ", groupName='" + groupName + '\'' +
                ", countyCD='" + countyCD + '\'' +
                ", currencyCD='" + currencyCD + '\'' +
                ", businessUnit='" + businessUnit + '\'' +
                ", lastReviewDt='" + lastReviewDt + '\'' +
                ", groupRemarks='" + groupRemarks + '\'' +
                ", internalLmt='" + internalLmt + '\'' +
                ", groupLmt='" + groupLmt + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

//    public String[][] getMapper() {
//          String[][] input = {
//                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
//                {CustExposureUIHelper.form_CustExposureSearchObj, "com.integrosys.cms.ui.custexposure.CustExposureSearchMapper"},
//                {CustExposureUIHelper.form_groupResult, "com.integrosys.cms.ui.custexposure.CustExposureSearchListMapper"},
//        };
//
//        return input;
//    }
}
