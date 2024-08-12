package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 15, 2007        ICustGrpIdentifier
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IGroupMembers extends Serializable, IValueObject {


    public long getGroupCCINoRef() ;
    public void setGroupCCINoRef(long groupCCINoRef);

    public long getGroupCCIMapID();
    public void setGroupCCIMapID(long groupCCIMapID);

    public boolean getDeletedInd() ;
    public void setDeletedInd( boolean deletedInd);

    public String getLegalName() ;
    public void setLegalName(String legalName) ;

    public String getSourceID();
    public void setSourceID(String sourceID) ;


    public long getGroupCCINo();
    public void setGroupCCINo(long groupCCINo) ;

    public String getCustomerName();
    public void setCustomerName(String customerName);



    public String getLeIDType();
    public void setLeIDType(String leIDType);

    public String getIdNO();
    public void setIdNO(String idNO);

    public long getLegalID();
    public void setLegalID(long legalID);

    public long getSubProfileID();
    public void setSubProfileID(long subProfileID) ;

    public long getLimitProfileID();
    public void setLimitProfileID(long limitProfileID);


    public String getDeleteCustomerID() ;
    public void setDeleteCustomerID(String deleteCustomerID) ;


    public String getLmpLeID() ;
    public void setLmpLeID(String lmpLeID);





}
