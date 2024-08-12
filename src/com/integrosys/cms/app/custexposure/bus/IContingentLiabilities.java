package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.base.businfra.currency.Amount;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: JITENDRA
 * Date: Jun 4, 2008
 * Time: 2:40:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IContingentLiabilities extends Serializable {


    public String getBorrowerName() ;
    public void setBorrowerName(String borrowerName) ;

    public String getGuaranteeType() ;
    public void setGuaranteeType(String guaranteeType) ;


    public Amount getGuaranteeAmt() ;
    public void setGuaranteeAmt(Amount guaranteeAmt) ;

    public String getCurrencyCode() ;
    public void setCurrencyCode(String currencyCode);

    public String getBankEntity();
    public void setBankEntity(String bankEntity);
    
    public String getSourceSecurityId();
    public void setSourceSecurityId(String sourceSecurityId);


}
