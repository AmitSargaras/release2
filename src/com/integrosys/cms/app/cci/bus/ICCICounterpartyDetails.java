package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 19, 2007
 * Time: 5:27:31 PM
 * To change this template use File | Settings | File Templates.
 */


public interface ICCICounterpartyDetails extends Serializable, IValueObject {


    public ICCICounterparty[]  getICCICounterparty();
    public void setICCICounterparty(ICCICounterparty[]  ICCICounterparty);

    public long getGroupCCINoRef() ;
    public void setGroupCCINoRef(long groupCCINoRef);


    public long getStagingGroupCCINoRef() ;
   public void setStagingGroupCCINoRef(long stagingGroupCCINoRef);


    public long getGroupCCINo() ;
    public void setGroupCCINo(long groupCCINo) ;

    public long getLimitProfileID();
    public void setLimitProfileID(long limitProfileID) ;


    public String getLeID();
    public void setLeID(String leID) ;

    public boolean getDeletedInd() ;
    public void setDeletedInd( boolean deletedInd);





}
