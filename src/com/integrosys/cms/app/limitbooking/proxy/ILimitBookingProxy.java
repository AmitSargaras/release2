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

import com.integrosys.cms.app.transaction.ITrxContext;

import com.integrosys.base.businfra.search.SearchResult;

import java.util.List;
import java.util.Map;

/**
* This interface defines the services that are available in for use
* in the interaction with Limit Booking.

* @author  $Author$
* @version $Revision$
* @since   $Date$
* Tag:     $Name$
*/
public interface ILimitBookingProxy extends java.io.Serializable {

    public ILimitBooking retrieveLimitBookingResult(ILimitBooking limitBooking) throws LimitBookingException, AmountConversionException;

    public ILimitBookingTrxValue createLimitBooking(ITrxContext ctx, ILimitBookingTrxValue trxVal, ILimitBooking value) throws LimitBookingException;
	
	public ILimitBookingTrxValue getLimitBookingTrxValue (ITrxContext ctx, long limitBookingID ) throws LimitBookingException;
		
	public ILimitBookingTrxValue getLimitBookingTrxValueByTrxID (ITrxContext ctx, String trxID)	throws LimitBookingException;
	
	public ILimitBookingTrxValue makerUpdateLimitBooking (ITrxContext ctx, ILimitBookingTrxValue trxVal, ILimitBooking value) throws LimitBookingException;
		
    public ILimitBookingTrxValue makerSuccessLimitBooking (ITrxContext ctx, ILimitBookingTrxValue trxVal, String lastModBy) throws LimitBookingException;
    
	public ILimitBookingTrxValue makerDeleteLimitBooking (ITrxContext ctx, ILimitBookingTrxValue trxVal, String lastModBy) throws LimitBookingException;

    public SearchResult searchBooking(LimitBookingSearchCriteria searchCriteria) throws LimitBookingException;
    
	public Long getSubProfileIDByIDNumber(String idNo) throws LimitBookingException;
	
	public List retrieveBGELGroup(Long subprofileID) throws LimitBookingException;
	
	public List retrieveMasterGroupBySubGroupID(List subgroupIDList) throws LimitBookingException;
	
	public Map getEcoSectorCodeMap() throws SectorLimitException;
	
	public Map getProductTypeCodeMap() throws ProductLimitException;
	
}
