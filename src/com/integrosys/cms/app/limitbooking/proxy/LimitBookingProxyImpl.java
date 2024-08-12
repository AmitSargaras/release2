/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.app.limitbooking.proxy;

import com.integrosys.cms.app.transaction.ITrxContext;

import com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingException;
import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingSearchCriteria;
import com.integrosys.cms.app.commodity.common.AmountConversionException;

import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.businfra.search.SearchResult;

import java.util.*;

/**
* This class defines the services that are available in for use
* in the Limit Booking module
*
* @author  $Author$
* @version $Revision$
* @since   $Date$
* Tag:     $Name$
*/
public class LimitBookingProxyImpl extends AbstractLimitBookingProxy {
	
	private static final long serialVersionUID = 1L;

	public ILimitBooking retrieveLimitBookingResult(ILimitBooking value) throws LimitBookingException, AmountConversionException {
        try {
            SBLimitBookingProxy proxy = getProxy();
            ILimitBooking result = proxy.retrieveLimitBookingResult (value);
         
            return result;
        }
        catch(LimitBookingException e) {
            e.printStackTrace();
            throw e;
        } 
		catch(AmountConversionException e) {
            
            throw e;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
	public ILimitBookingTrxValue getLimitBookingTrxValue (ITrxContext ctx, long limitBookingID ) throws LimitBookingException {
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.getLimitBookingTrxValue (ctx, limitBookingID);

        }
        catch(LimitBookingException e) {
            e.printStackTrace();
            throw e;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
		
	public ILimitBookingTrxValue getLimitBookingTrxValueByTrxID (ITrxContext ctx, String trxID)	throws LimitBookingException {
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.getLimitBookingTrxValueByTrxID (ctx, trxID);

        }
        catch(LimitBookingException e) {
            e.printStackTrace();
            throw e;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
    public ILimitBookingTrxValue createLimitBooking(ITrxContext ctx, ILimitBookingTrxValue trxVal, ILimitBooking value) throws LimitBookingException {
        try {
            SBLimitBookingProxy proxy = getProxy();
            ILimitBookingTrxValue result = proxy.createLimitBooking (ctx, trxVal, value);

            //result = prepareTrxResultLimitProfile(result);
            return result;
        }
        catch(LimitBookingException e) {
            e.printStackTrace();
            throw e;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }

	public ILimitBookingTrxValue makerUpdateLimitBooking(ITrxContext ctx, ILimitBookingTrxValue trxVal, ILimitBooking value) throws LimitBookingException {
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.makerUpdateLimitBooking (ctx, trxVal, value);

        }
        catch(LimitBookingException e) {
            e.printStackTrace();
            throw e;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
	public ILimitBookingTrxValue makerSuccessLimitBooking(ITrxContext ctx, ILimitBookingTrxValue trxVal, String lastModBy) throws LimitBookingException {
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.makerSuccessLimitBooking (ctx, trxVal, lastModBy);

        }
        catch(LimitBookingException e) {
            e.printStackTrace();
            throw e;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
	public ILimitBookingTrxValue makerDeleteLimitBooking(ITrxContext ctx, ILimitBookingTrxValue trxVal, String lastModBy) throws LimitBookingException {
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.makerDeleteLimitBooking (ctx, trxVal, lastModBy);

        }
        catch(LimitBookingException e) {
            e.printStackTrace();
            throw e;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
		
    public Long getSubProfileIDByIDNumber(String idNo) throws LimitBookingException{
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.getSubProfileIDByIDNumber(idNo);

        }   catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	public List retrieveBGELGroup(Long subprofileID) throws LimitBookingException{
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.retrieveBGELGroup(subprofileID);

        }   catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
	public SearchResult searchBooking(LimitBookingSearchCriteria searchCriteria) throws LimitBookingException{
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.searchBooking(searchCriteria);

        }   catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
	public List retrieveMasterGroupBySubGroupID(List subgroupIDList) throws LimitBookingException{
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.retrieveMasterGroupBySubGroupID(subgroupIDList);

        }   catch(Exception e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }

    private SBLimitBookingProxy getProxy() throws LimitBookingException {
        SBLimitBookingProxy home = (SBLimitBookingProxy)BeanController.getEJB(
                ICMSJNDIConstant.SB_LIMIT_BOOKING_PROXY_JNDI, SBLimitBookingProxyHome.class.getName());

	    if(null != home) {
	        return home;
	    }
	    else {
	        throw new LimitBookingException("SBLimitProxy is null!");
	    }
	}
    
    public Map getEcoSectorCodeMap() throws SectorLimitException   {
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.getEcoSectorCodeMap();

        }   catch(Exception e) {
            e.printStackTrace();
            throw new SectorLimitException("Caught Exception!", e);
        }
    }
    
    public Map getProductTypeCodeMap() throws ProductLimitException   {
        try {
            SBLimitBookingProxy proxy = getProxy();
            return proxy.getProductTypeCodeMap();

        }   catch(Exception e) {
            e.printStackTrace();
            throw new ProductLimitException("Caught Exception!", e);
        }
    }
  
}
