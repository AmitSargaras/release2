package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;

/**
 * Created by IntelliJ IDEA.
 * User: JITENDRA
 * Date: Jun 4, 2008
 * Time: 4:54:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICustExposureEntityRelationship extends ICustRelationship {

      public String getGroupName();
      public void setGroupName(String groupName) ;


     public String getRelationName();
     public void setRelationName(String relationName);

     public String getCustomerName();
     public void setCustomerName(String customerName) ;


     public String getRelatedEntiyName();
     public void setRelatedEntiyName(String relatedEntiyName) ;

     public long getRelatedEntiySubProfileId() ;
     public void setRelatedEntiySubProfileId(long relatedEntiySubProfileId) ;
     
     public String getRelationType();
     public void setRelationType(String relationType);
}
