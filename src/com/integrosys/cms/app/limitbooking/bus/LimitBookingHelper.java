package com.integrosys.cms.app.limitbooking.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.ui.common.ForexHelper;


public class LimitBookingHelper {

    public static final String BASE_CURR = CurrencyCode.MYR.getCode();
	private static final int SCALE = 0;
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
   
	public static Amount convertBaseAmount(Amount amt) throws AmountConversionException {
        try {			
            //throw AmountConversionException to UI and show an error message to user
            Amount convertedAmt = CommonUtil.convertAmount (amt, BASE_CURR);
            
            // round up the converted amount (no decimal point) - 
            // the original could be too big for db storage
            if (convertedAmt != null && convertedAmt.getAmountAsBigDecimal() != null) {
            	BigDecimal bd = convertedAmt.getAmountAsBigDecimal();
            	bd = bd.setScale(SCALE, ROUNDING_MODE);
            	convertedAmt.setAmount(bd.doubleValue());
            }
            
            return convertedAmt;

        } catch (AmountConversionException e) {
            
			e.setFromCcyCode( amt.getCurrencyCode() );
			e.setToCcyCode( BASE_CURR );
			throw e;
        }
    }
	
	public static boolean isEmptyAmount(Amount amt) {
        if( amt == null || amt.getAmountAsBigDecimal() == null ) {

			return true;
		}
        
		if( ( amt.getCurrencyCode() == null || amt.getCurrencyCode().trim().length() == 0 ) && amt.getAmount() == 0 ) {
		
			return true;
		}	

        return false;
    }
	
    public static String checkLimit(Amount bookingAmt, Amount currentExposure, Amount currentBooked,
                                    Amount crpLimit, String baseCurrencyCode ){
        try
        {
            if (crpLimit != null)
            {
                if (bookingAmt == null)
                    return ICMSConstant.FAIL;
                ForexHelper forex = ForexHelper.getInstance();
                Amount baseTotalAmt = forex.convert(bookingAmt, new CurrencyCode(baseCurrencyCode));
                baseTotalAmt = CommonUtil.addAmount(baseTotalAmt,currentExposure);
                baseTotalAmt = CommonUtil.addAmount(baseTotalAmt,currentBooked);

//              If ((Booking Amount + Total of Limit under [Customer] + Total of Booked Limit under [Customer]) <= Entity Limit set for [Customer]) THEN
                if (CommonUtil.compareAmount(baseTotalAmt,crpLimit) == CommonUtil.LESS)
                   return ICMSConstant.PASS;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ICMSConstant.FAIL;
    }

    public static ILimitBooking computeBookingResults(ILimitBooking limitBooking, String baseCurrencyCode ){
        if (limitBooking == null)
            return null;
        List allBkgs = limitBooking.getAllBkgs();
        ArrayList newList = new ArrayList();
		String overallResult = ICMSConstant.PASS;
        for (Iterator iterator = allBkgs.iterator(); iterator.hasNext();) {
            ILimitBookingDetail dtl =  (ILimitBookingDetail)iterator.next();
            DefaultLogger.debug("com.integrosys.cms.app.limitbooking.bus.LimitBookingHelper","Detail "+ dtl.getBkgType() +" "+ dtl.getBkgTypeCode() +" "+ dtl.getBkgTypeDesc());
            //if (ICMSConstant.BKG_TYPE_COUNTRY.equals(dtl.getBkgType())
            //   || ICMSConstant.BKG_TYPE_ENTITY.equals(dtl.getBkgType())
            //    || ICMSConstant.BKG_TYPE_BGEL.equals(dtl.getBkgType())){
                dtl.setBkgResult(checkLimit(dtl.getBkgAmount(),
                               dtl.getCurrentExposure(),
                               dtl.getTotalBookedAmount(),
                               dtl.getLimitAmount(),
                               baseCurrencyCode));
							   
                newList.add(dtl);
                DefaultLogger.debug("com.integrosys.cms.app.limitbooking.bus.LimitBookingHelper","Result "+ dtl.getBkgResult() +" "+ dtl.getBkgTypeCode() +" "+ dtl.getBkgTypeDesc());
            //}
            // Do for Sub Sector and Sector here
			if( dtl.getBkgResult().equals( ICMSConstant.FAIL ) ) {
				overallResult = ICMSConstant.FAIL;
			}
        }
        limitBooking.setAllBkgs(newList);
        limitBooking.setOverallBkgResult(overallResult);
        return limitBooking;
    }

	public static void updateBookingDetails(ILimitBooking limitBooking){
       
        List allBkgs = limitBooking.getAllBkgs();
        if( allBkgs != null ) {
		
	        for (Iterator iterator = allBkgs.iterator(); iterator.hasNext();) {
	            ILimitBookingDetail dtl =  (ILimitBookingDetail)iterator.next();            
								   
				dtl.setPreBookedAmount( dtl.getBkgAmount() );		   
					            
	        }
		}
    }

}
