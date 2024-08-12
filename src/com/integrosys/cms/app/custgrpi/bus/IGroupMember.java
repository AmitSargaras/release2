package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 15, 2007
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IGroupMember extends Serializable {

   //Used in Table
    public long getGroupMemberID() ;
    public void setGroupMemberID(long groupMemberID) ;

    public long getGroupMemberIDRef() ;
    public void setGroupMemberIDRef(long groupMemberIDRef) ;

    public long getEntityID() ;
    public void setEntityID(long entityID);

    public String getEntityType() ;
    public void setEntityType(String entityType) ;

    public String getRelationName() ;
    public void setRelationName(String relationName) ;

    public Double getPercentOwned() ;
    public void setPercentOwned(Double percentOwned) ;


    public String getRelBorMemberName() ;
    public void setRelBorMemberName(String relBorMemberName) ;


    public String getStatus() ;
    public void setStatus(String status) ;

    public long getGrpID();
    public void setGrpID(long grpID) ;

    public String getMembersCreditRating();
    public void setMembersCreditRating(String membersCreditRating) ;


    public long getGrpNo();
    public void setGrpNo(long grpNo) ;

    //User others
    public String getEntityName() ;
    public void setEntityName(String entityName) ;

    public String getSourceID();
    public void setSourceID(String sourceID) ;

    public String getlEIDSource();
    public void setlEIDSource(String lEIDSource) ;

    public String getLeID_GroupID() ;
    public void setLeID_GroupID(String leID_GroupID) ;

    public String getLmpLeID() ;
    public void setLmpLeID(String lmpLeID) ;

    public String getIdNO() ;
    public void setIdNO(String idNO) ;

    public String getIndexID() ;
    public void setIndexID(String indexID);

    public String getNextPage();
    public void setNextPage(String nextPage) ;

    public String getItemType() ;
    public void setItemType(String itemType) ;

    //Andy Wong, 2 July 2008: entity limit field for ABG requirement
    public Amount getEntityLmt() ;
    public void setEntityLmt(Amount entityLmt) ;
}
