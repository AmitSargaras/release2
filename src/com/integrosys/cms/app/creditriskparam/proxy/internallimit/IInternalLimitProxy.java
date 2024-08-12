/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/IForexFeedProxy.java,v 1.9 2003/09/12 09:24:00 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.proxy.internallimit;

import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitException;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2003/09/12 09:24:00 $ Tag: $Name: $
 */
public interface IInternalLimitProxy extends java.io.Serializable {
	public IInternalLimitParameterTrxValue getILParamTrxValue()throws InternalLimitException;

	public IInternalLimitParameterTrxValue getILParamTrxValueByTrxID(String trxID)throws InternalLimitException;

	public IInternalLimitParameterTrxValue makerUpdateILP(
			ITrxContext trxContext, IInternalLimitParameterTrxValue trxValue)throws InternalLimitException;

	public IInternalLimitParameterTrxValue makerSaveILP(ITrxContext trxContext,
			IInternalLimitParameterTrxValue trxValue)throws InternalLimitException;

	/*public IInternalLimitParameterTrxValue makerCloseILP(
			ITrxContext trxContext, IInternalLimitParameterTrxValue trxValue)throws InternalLimitException;*/
	
	public IInternalLimitParameterTrxValue makerCancelUpdateILP(
			ITrxContext trxContext, IInternalLimitParameterTrxValue trxValue)throws InternalLimitException;

	public IInternalLimitParameterTrxValue checkerApproveILP(
			ITrxContext trxContext, IInternalLimitParameterTrxValue trxValue)throws InternalLimitException;

	public IInternalLimitParameterTrxValue checkerRejectILP(
			ITrxContext trxContext, IInternalLimitParameterTrxValue trxValue)throws InternalLimitException;

	/*
	 * IForexFeedGroupTrxValue makerUpdateForexFeedGroup( ITrxContext
	 * anITrxContext, IForexFeedGroupTrxValue aTrxValue, IForexFeedGroup
	 * aFeedGroup) throws ForexFeedGroupException;
	 * 
	 * IForexFeedGroupTrxValue makerSubmitForexFeedGroup( ITrxContext
	 * anITrxContext, IForexFeedGroupTrxValue aTrxValue, IForexFeedGroup
	 * aFeedGroup) throws ForexFeedGroupException;
	 * 
	 * IForexFeedGroupTrxValue makerSubmitRejectedForexFeedGroup( ITrxContext
	 * anITrxContext, IForexFeedGroupTrxValue aTrxValue) throws
	 * ForexFeedGroupException;
	 * 
	 * IForexFeedGroupTrxValue makerUpdateRejectedForexFeedGroup( ITrxContext
	 * anITrxContext, IForexFeedGroupTrxValue aTrxValue) throws
	 * ForexFeedGroupException;
	 * 
	 * IForexFeedGroupTrxValue makerCloseRejectedForexFeedGroup( ITrxContext
	 * anITrxContext, IForexFeedGroupTrxValue aTrxValue) throws
	 * ForexFeedGroupException;
	 * 
	 * IForexFeedGroupTrxValue makerCloseDraftForexFeedGroup( ITrxContext
	 * anITrxContext, IForexFeedGroupTrxValue aTrxValue) throws
	 * ForexFeedGroupException;
	 * 
	 * IForexFeedGroupTrxValue checkerApproveForexFeedGroup( ITrxContext
	 * anITrxContext, IForexFeedGroupTrxValue aTrxValue) throws
	 * ForexFeedGroupException;
	 * 
	 * IForexFeedGroupTrxValue checkerRejectForexFeedGroup( ITrxContext
	 * anITrxContext, IForexFeedGroupTrxValue aTrxValue) throws
	 * ForexFeedGroupException;
	 */
}
