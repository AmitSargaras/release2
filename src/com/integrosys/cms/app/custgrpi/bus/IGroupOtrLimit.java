package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: June 27, 2008
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IGroupOtrLimit extends Serializable {

    public long getGroupOtrLimitID() ;
    public void setGroupOtrLimitID(long groupOtrLimitID);

    public long getGroupOtrLimitIDRef() ;
    public void setGroupOtrLimitIDRef(long groupOtrLimitIDRef) ;

    public String getOtrLimitTypeCD() ;
    public void setOtrLimitTypeCD(String otrLimitTypeCD);

    public Amount getLimitAmt();
    public void setLimitAmt(Amount limitAmt) ;

     public String getCurrencyCD() ;
     public void setCurrencyCD(String currencyCD) ;

    public Date getLastReviewedDt() ;
    public void setLastReviewedDt(Date lastReviewedDt);

    public String getDescription();
    public void setDescription(String description) ;

    public String getRemarks() ;
    public void setRemarks(String remarks) ;

    public String getStatus() ;
    public void setStatus(String status) ;

    public long getGrpID() ;
    public void setGrpID(long grpID);

}