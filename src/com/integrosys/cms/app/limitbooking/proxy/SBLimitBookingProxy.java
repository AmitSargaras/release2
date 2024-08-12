/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.limitbooking.proxy;

import com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingException;
import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingSearchCriteria;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.transaction.ITrxContext;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * This is the remote interface to the SBLimitBookingProxy
 * session bean.
 *
 * @author  $Author$
 * @version $Revision$
 * @since   $Date$
 * Tag:     $Name$
 */
public interface SBLimitBookingProxy extends EJBObject
{

    public ILimitBooking retrieveLimitBookingResult(ILimitBooking limitBooking) throws LimitBookingException, AmountConversionException, RemoteException;
    
	public ILimitBookingTrxValue createLimitBooking(ITrxContext ctx, ILimitBookingTrxValue trxVal, ILimitBooking value) throws LimitBookingException, RemoteException;

	public ILimitBookingTrxValue getLimitBookingTrxValue (ITrxContext ctx, long limitBookingID ) throws LimitBookingException, RemoteException;
		
	public ILimitBookingTrxValue getLimitBookingTrxValueByTrxID (ITrxContext ctx, String trxID)	throws LimitBookingException, RemoteException;
	
	public ILimitBookingTrxValue makerUpdateLimitBooking (ITrxContext ctx, ILimitBookingTrxValue trxVal, ILimitBooking value) throws LimitBookingException, RemoteException;
		
    public ILimitBookingTrxValue makerSuccessLimitBooking (ITrxContext ctx, ILimitBookingTrxValue trxVal, String lastModBy) throws LimitBookingException, RemoteException;
    
	public ILimitBookingTrxValue makerDeleteLimitBooking (ITrxContext ctx, ILimitBookingTrxValue trxVal, String lastModBy) throws LimitBookingException, RemoteException;
		   
	public Long getSubProfileIDByIDNumber(String idNo) throws LimitBookingException, RemoteException;
	
	public List retrieveBGELGroup(Long subprofileID) throws LimitBookingException, RemoteException;
    
	public SearchResult searchBooking(LimitBookingSearchCriteria searchCriteria) throws LimitBookingException, RemoteException;

	public List retrieveMasterGroupBySubGroupID(List subgroupIDList) throws LimitBookingException, RemoteException;
	
	public Map getEcoSectorCodeMap() throws SectorLimitException, RemoteException;
	
	public Map getProductTypeCodeMap() throws ProductLimitException, RemoteException;
	
}