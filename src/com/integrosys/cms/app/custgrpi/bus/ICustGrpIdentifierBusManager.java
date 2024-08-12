package com.integrosys.cms.app.custgrpi.bus;

import java.io.Serializable;
import com.integrosys.base.businfra.currency.Amount;
import java.util.List;


public interface ICustGrpIdentifierBusManager extends Serializable {


    public ICustGrpIdentifier deleteCustGrpIdentifier(ICustGrpIdentifier details)
            throws CustGrpIdentifierException;

    public ICustGrpIdentifier updateCustGrpIdentifier(ICustGrpIdentifier details)
            throws CustGrpIdentifierException;


    public ICustGrpIdentifier createCustGrpIdentifier(ICustGrpIdentifier details)
            throws CustGrpIdentifierException;


    public ICustGrpIdentifier getCustGrpIdentifierByTrxIDRef(long groupIDRef)
            throws CustGrpIdentifierException;

	/**
	* @see com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManager#getCustGrpByInternalLimitType
	*/
	public ICustGrpIdentifier[] getCustGrpByInternalLimitType(String internalLimitType) throws CustGrpIdentifierException;
	
	/**
	* @see com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManager#updateCustGrpLimitAmount
	*/
	public void updateCustGrpLimitAmount(ICustGrpIdentifier[] grpObjList) throws CustGrpIdentifierException;
	
	/**
	* @see com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManager#updateCustGrpLimitAmount
	*/
	public void updateCustGrpLimitAmount(List grpIDList, Amount lmtAmt) throws CustGrpIdentifierException;
	
	/**
	* @see com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManager#getCustGrpIdentifierByGrpID
	*/
	public ICustGrpIdentifier getCustGrpIdentifierByGrpID(Long grpID) throws CustGrpIdentifierException;

}
