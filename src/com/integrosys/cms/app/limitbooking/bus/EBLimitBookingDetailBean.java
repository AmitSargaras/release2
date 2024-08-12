/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;

import javax.ejb.*;
import java.util.*;

import java.math.BigDecimal;

/**
 * This entity bean represents the persistence for Limit Booking Detail.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
*/
public abstract class EBLimitBookingDetailBean implements EntityBean, ILimitBookingDetail {

    private static final String[] EXCLUDE_METHOD = new String[] {"getLmtBookDtlID", "getLimitBookingID", "getCmsRef"};

	/**
	* The Entity Context
	*/
    protected EntityContext _context = null;

	/**
	* Default Constructor
	*/
	public EBLimitBookingDetailBean() {}  
       

    //********************** Abstract Methods **********************
   
	public abstract Long getLmtBookDtlIDPK();
	public abstract void setLmtBookDtlIDPK(Long value);
    
	public abstract Long getLimitBookingIDFK();
	public abstract void setLimitBookingIDFK(Long value);	
	
	public abstract Long getEBCmsRef();
	public abstract void setEBCmsRef(Long value);	

    public abstract String getBkgType() ;
    public abstract void setBkgType(String value) ;

    public abstract String getBkgTypeCode() ;
    public abstract void setBkgTypeCode(String value) ;

    public abstract String getBkgTypeDesc() ;
    public abstract void setBkgTypeDesc(String value) ;

	public abstract String getBkgSubType() ;
    public abstract void setBkgSubType(String value) ;

    public abstract String getBkgSubTypeCode() ;
    public abstract void setBkgSubTypeCode(String value) ;

    public abstract String getBkgSubTypeDesc() ;
    public abstract void setBkgSubTypeDesc(String value) ;
	
	public abstract String getBkgCurrency() ;
    public abstract void setBkgCurrency(String value) ;

	public abstract BigDecimal getBkgAmt() ;
    public abstract void setBkgAmt(BigDecimal value) ;	

	public abstract String getPreBookedCurrency() ;
    public abstract void setPreBookedCurrency(String value) ;

    public abstract BigDecimal getPreBookedAmt() ;
    public abstract void setPreBookedAmt(BigDecimal value) ;	

	public abstract String getLimitCurrency() ;
    public abstract void setLimitCurrency(String value) ;

    public abstract BigDecimal getLimitAmt() ;
    public abstract void setLimitAmt(BigDecimal value) ;

	public abstract String getCurExposureCurrency() ;
    public abstract void setCurExposureCurrency(String value) ;
	
    public abstract BigDecimal getCurrentExposureAmt() ;
    public abstract void setCurrentExposureAmt(BigDecimal value) ;

	public abstract String getTotalBookedCurrency() ;
    public abstract void setTotalBookedCurrency(String value) ;
	
    public abstract BigDecimal getTotalBookedAmt() ;
    public abstract void setTotalBookedAmt(BigDecimal value) ;

	public abstract String getEBGrpIsRetrieved();
    public abstract void setEBGrpIsRetrieved(String isRetrieved) ;
	
    public abstract String getBkgResult() ;
    public abstract void setBkgResult(String value) ;

	public abstract String getStatus() ;
    public abstract void setStatus(String status) ;
	
	public abstract long getVersionTime();
    public abstract void setVersionTime (long versionTime);	
	
	
	/**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#getLmtBookDtlID
	    */
    public long getLmtBookDtlID() {
        if(null != getLmtBookDtlIDPK()) {
            return getLmtBookDtlIDPK().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#setLmtBookDtlID
	    */
    public void setLmtBookDtlID(long value) {
        setLmtBookDtlIDPK(new Long(value));
    }  
	
	/**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#getLimitBookingID
	    */
    public long getLimitBookingID() {
        if(null != getLimitBookingIDFK()) {
            return getLimitBookingIDFK().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#setLimitBookingID
	    */
    public void setLimitBookingID(long value) {
        setLimitBookingIDFK(new Long(value));
    }  

	 /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#getCmsRef
	    */
    public long getCmsRef() {
        if(null != getEBCmsRef()) {
            return getEBCmsRef().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#setCmsRef
	    */
    public void setCmsRef(long value) {
        setEBCmsRef(new Long(value));
    }  
	
	/**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#getBkgAmount
    */
    public Amount getBkgAmount() {
        String ccy = getBkgCurrency();
        BigDecimal amt = getBkgAmt();

        if( amt != null && ccy != null ) {

        	return new Amount( amt, new CurrencyCode( ccy ) );
        }
        return null;
    }

    /**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#setBkgAmount
    */
    public void setBkgAmount(Amount value) {
        if( null != value ) {
            setBkgCurrency( value.getCurrencyCode() );
            setBkgAmt( value.getAmountAsBigDecimal() );
        }
    }
	
	/**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#getPreBookedAmount
    */
    public Amount getPreBookedAmount() {
        String ccy = getPreBookedCurrency();
        BigDecimal amt = getPreBookedAmt();

        if( amt != null && ccy != null ) {

        	return new Amount( amt, new CurrencyCode( ccy ) );
        }
        return null;
    }

    /**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#setPreBookedAmount
    */
    public void setPreBookedAmount(Amount value) {
        if( null != value ) {
            setPreBookedCurrency( value.getCurrencyCode() );
            setPreBookedAmt( value.getAmountAsBigDecimal() );
        }
    }
	
	/**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#getLimitAmount
    */
    public Amount getLimitAmount() {
        String ccy = getLimitCurrency();
        BigDecimal amt = getLimitAmt();

        if( amt != null && ccy != null ) {

        	return new Amount( amt, new CurrencyCode( ccy ) );
        }
        return null;
    }

    /**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#setLimitAmount
    */
    public void setLimitAmount(Amount value) {
        if( null != value ) {
            setLimitCurrency( value.getCurrencyCode() );
            setLimitAmt( value.getAmountAsBigDecimal() );
        }
    }
	
	/**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#getCurrentExposure
    */
    public Amount getCurrentExposure() {
        String ccy = getCurExposureCurrency();
        BigDecimal amt = getCurrentExposureAmt();

        if( amt != null && ccy != null ) {

        	return new Amount( amt, new CurrencyCode( ccy ) );
        }
        return null;
    }

    /**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#setCurrentExposure
    */
    public void setCurrentExposure(Amount value) {
        if( null != value ) {
            setCurExposureCurrency( value.getCurrencyCode() );
            setCurrentExposureAmt( value.getAmountAsBigDecimal() );
        }
    }
	/**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#getTotalBookedAmount
    */
    public Amount getTotalBookedAmount() {
        String ccy = getTotalBookedCurrency();
        BigDecimal amt = getTotalBookedAmt();

        if( amt != null && ccy != null ) {

        	return new Amount( amt, new CurrencyCode( ccy ) );
        }
        return null;
    }

    /**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail#setTotalBookedAmount
    */
    public void setTotalBookedAmount(Amount value) {
        if( null != value ) {
            setTotalBookedCurrency( value.getCurrencyCode() );
            setTotalBookedAmt( value.getAmountAsBigDecimal() );
        }
    }
	
	public boolean getGrpIsRetrieved() {
        if( getEBGrpIsRetrieved() != null && getEBGrpIsRetrieved().equals( ICMSConstant.TRUE_VALUE ) ) {
			return true;
		} else {
			return false;
		}
    }

    public void setGrpIsRetrieved(boolean grpIsRetrieved) {
        setEBGrpIsRetrieved( grpIsRetrieved? ICMSConstant.TRUE_VALUE: ICMSConstant.FALSE_VALUE );
    } 
	
	public List getBookedLimitList() {
        return null;
    }

    public void setBookedLimitList(List bookedLimitList) {
        // do nothing
    }
	
	public Amount getBkgBaseAmount() {
        return null;
    }

    public void setBkgBaseAmount(Amount bkgBaseAmount) {
        // do nothing
    }
	
    //************************ ejbCreate methods ********************
	/**
	     * Get the sequence of primary key for this Limit Booking Detail.
	     *
	     * @return String
	*/
    protected String getPKSequenceName ()
    {
        return ICMSConstant.SEQUENCE_LIMIT_BOOKING_DTL;
    }
	
    /**
	* Create a Limit Booking Detail 
	*
	* @param value is the ILimitBookingDetail object
	* @return Long the primary key
	*/
	public Long ejbCreate( ILimitBookingDetail value, long limitBookingIDPK ) throws CreateException {
	    if(null == value) {
	        throw new CreateException("ILimitBookingDetail is null!");
	    }
	   if (limitBookingIDPK == ICMSConstant.LONG_INVALID_VALUE) {
			throw new CreateException("limit booking ID is null!");
		} 
	    try {
	        long pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));

	        DefaultLogger.debug(this, "Creating item with ID: " + pk);
	        DefaultLogger.debug(this, "Creating item with value: " + value);

            AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
            setLmtBookDtlIDPK( new Long( pk ) );
			//setLimitBookingID( value.getLimitBookingID() );
			setLimitBookingID( limitBookingIDPK );

			if (value.getCmsRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setEBCmsRef( new Long( pk ) );
			} else {
				// else maintain this reference id.
				setEBCmsRef( new Long( value.getCmsRef() ) );
			}
			setStatus( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );	
			setVersionTime( VersionGenerator.getVersionNumber() );           
            return new Long(pk);
        }
        catch(Exception e) {
            e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}	
	 	
	/**
	* Post Create a Limit Booking Detail
	*
	* @param value is the ILoanSectorDetail object
	*/
	public void ejbPostCreate(ILimitBookingDetail value, long limitBookingIDPK) throws CreateException {
	    //do nothing
	}	
	
	/**
	* Method to get an object representation from persistance
	*
	* @return ILimitBookingDetail
	*/
	public ILimitBookingDetail getValue() {
	    OBLimitBookingDetail value = new OBLimitBookingDetail();
		AccessorUtil.copyValue(this, value);

		return value;
	}	
	
	/**
	* Method to set an object representation into persistance
	*
	* @param value is of type ILimitBookingDetail
	* @throws LimitBookingException on error
	*/
	public void setValue(ILimitBookingDetail value) throws LimitBookingException {
        if(null != value) {
            AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	    }
	    else {
	        throw new LimitBookingException("ILimitBookingDetail is null!");
	    }
	}
		

	/**
	 * Delete this Limit Booking Detail.
	 */
	public void delete() {
		setStatus(ICMSConstant.STATUS_LIMIT_BOOKING_DELETED);
	}
	
	/**
	 * Delete this Limit Booking Detail.
	 */
	public void success() {
		setStatus(ICMSConstant.STATUS_LIMIT_BOOKING_SUCCESSFUL);
	}
	//************************************************************************
	/**
	* EJB callback method
	*/
    public void ejbActivate() {
    }

    /**
	* EJB callback method
	*/
    public void ejbPassivate() {
    }

    /**
	* EJB callback method
	*/
    public void ejbLoad() {
    }

    /**
	* EJB callback method
	*/
    public void ejbStore() {
    }

    /**
	* EJB callback method
	*/
    public void ejbRemove() throws RemoveException, EJBException {
    }
	/**
	* EJB Callback Method
	*/
    public void setEntityContext( EntityContext ctx ) {
        _context = ctx;
    }
	/**
	* EJB Callback Method
	*/
    public void unsetEntityContext() {
        _context = null;
    }
    
    public abstract String getBkgProdTypeCode() ;
    public abstract void setBkgProdTypeCode(String bkgProdTypeCode) ;

    public abstract String getBkgProdTypeDesc() ;
    public abstract void setBkgProdTypeDesc(String bkgProdTypeDesc);
}