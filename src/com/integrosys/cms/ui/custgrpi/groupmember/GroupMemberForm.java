package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.customer.CustomerSearchForm;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.currency.Amount;

public class GroupMemberForm extends CustomerSearchForm implements java.io.Serializable {


    private String groupMemberID;
    private String groupMemberIDRef;
    private String entityID;
    private String entityType;
    private String relationName;
    private String relationValue;
    private String membersCreditRating;
    private String status;
    private String grpID;

    //Entity party
    private String relBorMemberName;
    private String entityName;
    private String sourceID;
    private String sourceDesc;
    private String lEIDSource;
    private String lmpLeID;

    private String indexID;

    //Used in Group Memeber Search
    private String searchGroupNo  ;
    private String searchType;
    private String searchGroupID;
    private String searchGroupName = "";

    private String userID = "";
    private String subProfileID = "";
    private String legalName = "";
    private String legalIdSub = "";

    private String groupName = "";

    private String customerName = "";
    private String legalID = "";
    private String sourceType = "";
    private String idNO = "";
    private String customerSeach = "";
    private String nextPage;
    private String itemType;
    private String gobutton = "";
    private String indicator = "";
    private String all = "";
    private Amount entityLmt;

    public Amount getEntityLmt() {
        return entityLmt;
    }

    public void setEntityLmt(Amount entityLmt) {
        this.entityLmt = entityLmt;
    }

    public String getSearchGroupNo() {
        return searchGroupNo;
    }

    public void setSearchGroupNo(String searchGroupNo) {
        this.searchGroupNo = searchGroupNo;
    }


    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
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


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getlEIDSource() {
        return lEIDSource;
    }

    public void setlEIDSource(String lEIDSource) {
        this.lEIDSource = lEIDSource;
    }

    public String getLmpLeID() {
        return lmpLeID;
    }

    public void setLmpLeID(String lmpLeID) {
        this.lmpLeID = lmpLeID;
    }


    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }


    public String getRelBorMemberName() {
        return relBorMemberName;
    }

    public void setRelBorMemberName(String relBorMemberName) {
        this.relBorMemberName = relBorMemberName;
    }


    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getGroupMemberID() {
        return groupMemberID;
    }

    public void setGroupMemberID(String groupMemberID) {
        this.groupMemberID = groupMemberID;
    }

    public String getGroupMemberIDRef() {
        return groupMemberIDRef;
    }

    public void setGroupMemberIDRef(String groupMemberIDRef) {
        this.groupMemberIDRef = groupMemberIDRef;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }


    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getRelationValue() {
        return relationValue;
    }

    public void setRelationValue(String relationValue) {
        this.relationValue = relationValue;
    }

    public String getMembersCreditRating() {
        return membersCreditRating;
    }

    public void setMembersCreditRating(String membersCreditRating) {
        this.membersCreditRating = membersCreditRating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrpID() {
        return grpID;
    }

    public void setGrpID(String grpID) {
        this.grpID = grpID;
    }

    public String getIndexID() {
        return indexID;
    }

    public void setIndexID(String indexID) {
        this.indexID = indexID;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }


    private String[] entityCheckBoxID;


    public String[] getEntityCheckBoxID() {
        return entityCheckBoxID;
    }

    public void setEntityCheckBoxID(String[] entityCheckBoxID) {
        this.entityCheckBoxID = entityCheckBoxID;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
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


    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
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

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getCustomerSeach() {
        return customerSeach;
    }

    public void setCustomerSeach(String customerSeach) {
        this.customerSeach = customerSeach;
    }


    public String[][] getMapper() {

        String[][] input = {
                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
                {CustGroupUIHelper.form_EntitySelectedIDMapper, "com.integrosys.cms.ui.custgrpi.groupmember.EntitySelectedIDMapper"},
                {CustGroupUIHelper.form_groupmemberObj, "com.integrosys.cms.ui.custgrpi.groupmember.GroupMemberMapper"},

                {CustGroupUIHelper.form_searchGroupResult, "com.integrosys.cms.ui.custgrpi.groupmember.GroupMemberSearchListMapper"},

                {CustGroupUIHelper.form_groupMemberSearchCriteria, "com.integrosys.cms.ui.custgrpi.groupmember.GroupMemberSearchMapper"},
                {CustGroupUIHelper.form_groupMemberSearchList, "com.integrosys.cms.ui.custgrpi.groupmember.GroupMemberSearchListMapper"},
        };

        return input;
    }

}
