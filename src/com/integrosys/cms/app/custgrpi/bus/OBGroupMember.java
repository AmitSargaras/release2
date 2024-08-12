package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 15, 2007
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBGroupMember implements IGroupMember {

    //Used in table
    private long groupMemberID = ICMSConstant.LONG_INVALID_VALUE;
    private long groupMemberIDRef = ICMSConstant.LONG_INVALID_VALUE;

    private long entityID = ICMSConstant.LONG_INVALID_VALUE;
    private String entityType;
    private String relationName;
    private Double percentOwned;
    private String RelBorMemberName;

    private String status;

    private long grpID = ICMSConstant.LONG_INVALID_VALUE;
    private String membersCreditRating;

    private long grpNo = ICMSConstant.LONG_INVALID_VALUE;

    //Used in Others
    private String entityName;
    private String sourceID;
    private String lEIDSource;
    private String leID_GroupID;
    private String lmpLeID;
    private String IdNO;
    private String indexID;
    private String nextPage;
    private String itemType;
    private Amount entityLmt;

    public Amount getEntityLmt() {
        return entityLmt;
    }

    public void setEntityLmt(Amount entityLmt) {
        this.entityLmt = entityLmt;
    }

    public OBGroupMember() {
    }

    public OBGroupMember(IGroupMember obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }


    public long getGroupMemberID() {
        return groupMemberID;
    }

    public void setGroupMemberID(long groupMemberID) {
        this.groupMemberID = groupMemberID;
    }

    public long getGroupMemberIDRef() {
        return groupMemberIDRef;
    }

    public void setGroupMemberIDRef(long groupMemberIDRef) {
        this.groupMemberIDRef = groupMemberIDRef;
    }

    public long getEntityID() {
        return entityID;
    }

    public void setEntityID(long entityID) {
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


    public Double getPercentOwned() {
        return percentOwned;
    }

    public void setPercentOwned(Double percentOwned) {
        this.percentOwned = percentOwned;
    }


    public String getRelBorMemberName() {
        return RelBorMemberName;
    }

    public void setRelBorMemberName(String relBorMemberName) {
        RelBorMemberName = relBorMemberName;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getGrpID() {
        return grpID;
    }

    public void setGrpID(long grpID) {
        this.grpID = grpID;
    }

    public String getMembersCreditRating() {
        return membersCreditRating;
    }

    public void setMembersCreditRating(String membersCreditRating) {
        this.membersCreditRating = membersCreditRating;
    }


    public long getGrpNo() {
        return grpNo;
    }

    public void setGrpNo(long grpNo) {
        this.grpNo = grpNo;
    }


    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
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

    public String getLeID_GroupID() {
        return leID_GroupID;
    }

    public void setLeID_GroupID(String leID_GroupID) {
        this.leID_GroupID = leID_GroupID;
    }

    public String getLmpLeID() {
        return lmpLeID;
    }

    public void setLmpLeID(String lmpLeID) {
        this.lmpLeID = lmpLeID;
    }

    public String getIdNO() {
        return IdNO;
    }

    public void setIdNO(String idNO) {
        IdNO = idNO;
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }


}
