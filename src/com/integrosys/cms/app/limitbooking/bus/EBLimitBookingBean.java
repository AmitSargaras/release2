/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.TicketNumberUtil;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierBusManagerFactory;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifierBusManager;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.util.DateUtil;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.Calendar;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import javax.ejb.EJBException;
import java.rmi.RemoteException;
import javax.ejb.FinderException;

import java.math.BigDecimal;

/**
 * Entity bean implementation for Limit Booking entity.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:$
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public abstract class EBLimitBookingBean implements ILimitBooking, EntityBean
{
    /** The container assigned reference to the entity. */
    private EntityContext context;

    /** A list of methods to be excluded during update to the Limit Booking. */
    protected static final String[] EXCLUDE_METHOD = new String[] {"getLimitBookingID","getCmsRef","getTicketNo"};
    
	protected static final int EXPIRED_IN_MONTHS = 6;
	
    public static final String EXCLUDE_STATUS = ICMSConstant.STATUS_LIMIT_BOOKING_DELETED;
    public static final String EMPTY_STR = "";
	
    public abstract Long getLimitBookingIDPK();
    public abstract void setLimitBookingIDPK(Long limitBookingIDPK);

    public abstract Long getSubProfileID();
    public abstract void setSubProfileID(Long value);

	public abstract String getTicketNo();
    public abstract void setTicketNo(String value);
	
    public abstract String getAaNo();
    public abstract void setAaNo(String value);

    public abstract String getAaSource();
    public abstract void setAaSource(String value);

    public abstract String getBkgName();
    public abstract void setBkgName(String value);
    
	public abstract String getBkgIDNo();
    public abstract void setBkgIDNo(String value);
	
    public abstract String getBkgCurrency() ;
    public abstract void setBkgCurrency(String bkgCurrency) ;

    public abstract BigDecimal getBkgAmt() ;
    public abstract void setBkgAmt(BigDecimal value) ;

    public abstract String getBkgCountry() ;
    public abstract void setBkgCountry(String bkgCountry) ;

    public abstract String getBkgBusUnit();
    public abstract void setBkgBusUnit(String bkgBusUnit) ;

	public abstract String getBkgBusSector();
    public abstract void setBkgBusSector(String bkgBusSector);
	
	public abstract String getBkgBankEntity();
    public abstract void setBkgBankEntity(String bkgBankEntity);
	
    public abstract String getBkgStatus() ;
    public abstract void setBkgStatus(String bkgStatus) ;
	
	public abstract String getOverallBkgResult();
    public abstract void setOverallBkgResult(String overallBkgResult);
	
    public abstract Date getExpiryDate() ;
    public abstract void setExpiryDate(Date expiryDate) ;
	
	public abstract Date getCreateDate() ;
    public abstract void setCreateDate(Date value) ;
	
    public abstract long getVersionTime();
    public abstract void setVersionTime (long versionTime);	
	
	public abstract String getIsExistingCustomer();
    public abstract void setIsExistingCustomer(String existingCustomer);

    public abstract String getIsFinancialInstitution();
    public abstract void setIsFinancialInstitution(String isFnancialInstitution);	
	
    public abstract Long getEBCmsRef();
    public abstract void setEBCmsRef(Long value);
		
	public abstract String getLastModifiedBy();
    public abstract void setLastModifiedBy(String value);

	public abstract Date getLastModifiedDate() ;
    public abstract void setLastModifiedDate(Date value) ;
	

    //************ Non-persistence method *************

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#getLimitBookingID
	    */
    public long getLimitBookingID() {
        if(null != getLimitBookingIDPK()) {
            return getLimitBookingIDPK().longValue();
        }
        else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#setLimitBookingID
	    */
    public void setLimitBookingID(long value) {
        setLimitBookingIDPK(new Long(value));
    }  

	 /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#getCmsRef
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
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#setCmsRef
	    */
    public void setCmsRef(long value) {
        setEBCmsRef(new Long(value));
    }  

	
	/**
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#getBkgAmount
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
    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#setBkgAmount
    */
    public void setBkgAmount(Amount value) {
        if( null != value ) {
            setBkgCurrency( value.getCurrencyCode() );
            setBkgAmt( value.getAmountAsBigDecimal() );
        }
    }
	
	
	 /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#getLoanSectorList
	    */
    public List getLoanSectorList() {                   
		return retrieveLoanSectorDetail();			       
    }

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#setLoanSectorList
	    */
    public void setLoanSectorList(List value) {
        //do nothing
    }
	
    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#getBankGroupList
	    */
    public List getBankGroupList() {         
        return retrieveBankGroupDetail();
		//return null;
    }
	
	/**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#setLimitBookingDetailList
	    */
	public void setBankGroupList(List value) {
         //do nothing
    }
	
	/**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#getAllBkgs
	    */
    public List getAllBkgs() {         
        return retrieveBookingResultDetail();
    }
	
	/**
	    * @see com.integrosys.cms.app.limitbooking.bus.ILimitBooking#setAllBkgs
	    */
	public void setAllBkgs(List value) {
         //do nothing
    }
	
	/**
	     * Get the sequence of primary key for this Limit Booking.
	     *
	     * @return String
	     */
    protected String getPKSequenceName ()
    {
        return ICMSConstant.SEQUENCE_LIMIT_BOOKING;
    }
	    
	protected String getFindExcludeStatus ()
    {
        return ICMSConstant.STATUS_LIMIT_BOOKING_DELETED;
    }
	    
	/**
	     * Matching method of the create(...) method of the bean's home interface.
	     * The container invokes an ejbCreate method to create entity bean instance.
	     *
	     * @param limitBooking of type ILimitBooking
	     * @throws CreateException on error creating the entity object
	     */
    public Long ejbCreate (ILimitBooking limitBooking) throws CreateException
    {
		if(null == limitBooking) {
			throw new CreateException("ILimitBooking is null!");
		}
		
        try {

            long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
            pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));
            DefaultLogger.debug(this, "Creating limitBooking with ID: " + pk);
            setLimitBookingIDPK( new Long( pk ) );
			
            AccessorUtil.copyValue(limitBooking, this, EXCLUDE_METHOD);
						
			if (limitBooking.getCmsRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setEBCmsRef( new Long( pk ) );
			} else {
				// else maintain this reference id.
				setEBCmsRef( new Long( limitBooking.getCmsRef() ) );
			}
			setBkgStatus( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );	
			Date currDate = CommonUtil.getCurrentDate();
			if( limitBooking.getCreateDate() == null ) {
				setCreateDate( currDate );	
			}
			setLastModifiedDate( currDate );	
			
			if( limitBooking.getExpiryDate() == null ) {
				Calendar cal = DateUtil.getCalendar();
				cal.set(Calendar.MONTH, cal.get( Calendar.MONTH ) + EXPIRED_IN_MONTHS);
				setExpiryDate( cal.getTime() );	
			}
			if( limitBooking.getTicketNo() == null || limitBooking.getTicketNo().trim().length() == 0 ) {
				setTicketNo(TicketNumberUtil.generateTicketNumber());
			} else { 
				setTicketNo( limitBooking.getTicketNo() );
			}
			setVersionTime( VersionGenerator.getVersionNumber() );           
			return new Long(pk);
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CreateException (e.toString());
        }
    }

    /**
	     * The container invokes this method after invoking the ejbCreate
	     * method with the same arguments.
	     * @param limitBooking of type ILimitBooking
	     */
    public void ejbPostCreate (ILimitBooking limitBooking)
    {		
	}
	 
	 /**
	    * @see com.integrosys.cms.app.limitbooking.bus.EBLimitBooking#createDependants
	    */
    public void createDependants(ILimitBooking value, long verTime, long limitBookingPK) 
		throws LimitBookingException, ConcurrentUpdateException 
	{
        if(verTime != getVersionTime()) {
            throw new ConcurrentUpdateException("Version mismatched!");
        }
        else {
            
			List itemList = populateLimitBookingDetail( value );		
        	
        	createLimitBookingDetail( itemList, limitBookingPK );		
						
        	itemList.clear();
        }
    }
	
	private List populateLimitBookingDetail( ILimitBooking value )
    {		
		List itemList = new ArrayList();
		//booking results
		List bookResultList = value.getAllBkgs();						
		itemList = new ArrayList();
		if( bookResultList != null ) {
			itemList.addAll( bookResultList );		
		}			
		
		// bank group
		List bankGroupList = value.getBankGroupList();			
		//DefaultLogger.debug (this, "populateLimitBookingDetail, bankGroupList: " + bankGroupList);

		if (bankGroupList != null)
		{
			for(int i=0; i<bankGroupList.size(); i++)
			{
				IBankGroupDetail next = (IBankGroupDetail)bankGroupList.get(i);		
				boolean found = false;
				if (bookResultList != null)
				{
					for(int j=0; j<bookResultList.size(); j++)
					{
						ILimitBookingDetail bookResult = (ILimitBookingDetail)bookResultList.get(j);				
						
						if( bookResult.getBkgType().equals( ICMSConstant.BKG_TYPE_BGEL ) && 
							bookResult.getBkgTypeCode().equals( next.getBkgTypeCode() ) &&
							bookResult.getBkgSubType() != null							
							) {
							found = true;
							if( !LimitBookingHelper.isEmptyAmount( next.getLimitConvAmount() ) && 
									bookResult.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_CONV ) ) {
								next.setLimitConvAmount( null );  
								
							}
							if( !LimitBookingHelper.isEmptyAmount( next.getLimitIslamAmount() ) && 
									bookResult.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_ISLM ) ) {
								next.setLimitIslamAmount( null );  
								
							}
							if( !LimitBookingHelper.isEmptyAmount( next.getLimitInvAmount() ) && 
									bookResult.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_INV ) ) {
								next.setLimitInvAmount( null );  
								
							}								
							if( !LimitBookingHelper.isEmptyAmount( next.getLimitAmount() ) && 
									bookResult.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_BANK ) ) {
								next.setLimitAmount( null );  
								
							}
							
							
						}
					} //end for
				} //end if
				if( !LimitBookingHelper.isEmptyAmount( next.getLimitConvAmount() ) ) {
					
					ILimitBookingDetail lmtBookDtls = new OBLimitBookingDetail( next );
					lmtBookDtls.setBkgType( ICMSConstant.BKG_TYPE_BGEL );
					lmtBookDtls.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_CONV );
					lmtBookDtls.setLimitAmount( next.getLimitConvAmount() );
					
					itemList.add( lmtBookDtls );
				}
				if( !LimitBookingHelper.isEmptyAmount( next.getLimitIslamAmount() ) ) {
					
					ILimitBookingDetail lmtBookDtls = new OBLimitBookingDetail( next );
					lmtBookDtls.setBkgType( ICMSConstant.BKG_TYPE_BGEL );
					lmtBookDtls.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_ISLM );
					lmtBookDtls.setLimitAmount( next.getLimitIslamAmount() );
					
					itemList.add( lmtBookDtls );
				}
				if( !LimitBookingHelper.isEmptyAmount( next.getLimitInvAmount() ) ) {
					
					ILimitBookingDetail lmtBookDtls = new OBLimitBookingDetail( next );
					lmtBookDtls.setBkgType( ICMSConstant.BKG_TYPE_BGEL );
					lmtBookDtls.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_INV );
					lmtBookDtls.setLimitAmount( next.getLimitInvAmount() );
					
					itemList.add( lmtBookDtls );
				}
				if( !LimitBookingHelper.isEmptyAmount( next.getLimitAmount() ) ) {
					
					ILimitBookingDetail lmtBookDtls = new OBLimitBookingDetail( next );
					lmtBookDtls.setBkgType( ICMSConstant.BKG_TYPE_BGEL );
					lmtBookDtls.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_BANK );
					//DefaultLogger.debug (this, "populateLimitBookingDetail, add item bank: " + lmtBookDtls);
					itemList.add( lmtBookDtls );
				}
				if( !found ) {
					DefaultLogger.debug (this, "populateLimitBookingDetail, found: " + found);

					ILimitBookingDetail lmtBookDtls = new OBLimitBookingDetail( next );
					lmtBookDtls.setBkgType( ICMSConstant.BKG_TYPE_BGEL );
					//lmtBookDtls.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_BANK );
					
					itemList.add( lmtBookDtls );
				}
				
			}
		}			
		
		List loanSectorList = value.getLoanSectorList();						
	
		if (loanSectorList != null)
		{
			for(int i=0; i<loanSectorList.size(); i++)
			{
				ILoanSectorDetail next = (ILoanSectorDetail)loanSectorList.get(i);			
				boolean found = false;
				if (bookResultList != null)
				{
					for(int j=0; j<bookResultList.size(); j++)
					{
						ILimitBookingDetail bookResult = (ILimitBookingDetail)bookResultList.get(j);				

						if( bookResult.getBkgType().equals( ICMSConstant.BKG_TYPE_ECO_POL ) && 
							bookResult.getBkgTypeCode().equals( next.getBkgTypeCode() ) ) {
							found = true;
							break;
						}
					} //end for
				} //end if
				if( !found ) {
					ILimitBookingDetail lmtBookDtls = new OBLimitBookingDetail( next );
					lmtBookDtls.setBkgType( ICMSConstant.BKG_TYPE_ECO_POL );
					
					itemList.add( lmtBookDtls );
				}
			}
		}
		return itemList;
	}
	
    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.EBLimitBooking#getValue
	    */
    public ILimitBooking getValue()
    {
        OBLimitBooking limitBooking = new OBLimitBooking();
        AccessorUtil.copyValue ( this, limitBooking );      
		//limitBooking.setBankGroupList( retrieveBankGroupDetail() ); 					
		//limitBooking.setLoanSectorList( retrieveLoanSectorDetail() ); 					
        return limitBooking;
    }

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.EBLimitBooking#setValue
	    */
    public void setValue (ILimitBooking limitBooking)
        throws LimitBookingException, VersionMismatchException
    {	
		try {
		
            checkVersionMismatch ( limitBooking );
	        AccessorUtil.copyValue ( limitBooking, this, EXCLUDE_METHOD );		
	       			
			List itemList = populateLimitBookingDetail( limitBooking );
			
			updateLimitBookingDetail( itemList );
	        setVersionTime ( VersionGenerator.getVersionNumber() );
			setLastModifiedDate( CommonUtil.getCurrentDate() );	

        }
        catch(Exception e) {
            throw new LimitBookingException("Caught Exception!", e);
        }        
    }    

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.EBLimitBooking#delete
	    */
    public void delete (ILimitBooking limitBooking)
        throws LimitBookingException, VersionMismatchException
    {
        checkVersionMismatch ( limitBooking );
        setBkgStatus ( ICMSConstant.STATUS_LIMIT_BOOKING_DELETED );        
        setLastModifiedBy ( limitBooking.getLastModifiedBy() );        
		
		deleteAllLimitBookingDetail();
		setVersionTime ( VersionGenerator.getVersionNumber() );		
    }

	/**
	    * @see com.integrosys.cms.app.limitbooking.bus.EBLimitBooking#success
	    */
    public void success (ILimitBooking limitBooking)
        throws LimitBookingException, VersionMismatchException
    {
        checkVersionMismatch ( limitBooking );
        setBkgStatus ( ICMSConstant.STATUS_LIMIT_BOOKING_SUCCESSFUL );        
        setLastModifiedBy ( limitBooking.getLastModifiedBy() );        

		successAllLimitBookingDetail();
		setVersionTime ( VersionGenerator.getVersionNumber() );		
    }
	
    /**
	     * Check the version of this Limit Booking.
	     *
	     * @param limitBooking of type ILimitBooking
	     * @throws VersionMismatchException if the entity version is invalid
	     */
    private void checkVersionMismatch (ILimitBooking limitBooking)
        throws VersionMismatchException
    {
        if (getVersionTime() != limitBooking.getVersionTime())
            throw new VersionMismatchException ("Mismatch timestamp! " + limitBooking.getVersionTime());
    }  
	
	
	// ******************** Private methods for Limit Booking Detail ****************
    private ArrayList retrieveLoanSectorDetail() {
		
		try {
			EBLimitBookingDetailLocalHome ebLimitBookingDetailLocalHome = getEBLimitBookingDetailLocalHome();
	            
			Collection c = ebLimitBookingDetailLocalHome.findByLimitBookingIDAndType(
															ICMSConstant.BKG_TYPE_ECO_POL, getLimitBookingID(), EXCLUDE_STATUS );
			if(null == c || c.size() == 0) {
				return null;
			}
			else {
				
		        Iterator i = c.iterator();
		        ArrayList list = new ArrayList();

		        while (i.hasNext()) {
		            EBLimitBookingDetailLocal theEjb = (EBLimitBookingDetailLocal) i.next();
		            ILimitBookingDetail item = theEjb.getValue();
		            if (item.getStatus() != null
		                    && item.getStatus().equals( getFindExcludeStatus() )) {
		                continue;
		            }							
					ILoanSectorDetail lnSector = new OBLoanSectorDetail( item );
					DefaultLogger.debug(this, "retrieveLoanSectorDetail, LimitBookingID="+lnSector.getLimitBookingID());
					DefaultLogger.debug(this, "retrieveLoanSectorDetail, LimitBookingDetailID="+lnSector.getLmtBookDtlID());
		            list.add(lnSector);
		        }
		        return list;
			}
			
		}  catch (FinderException e) {
			DefaultLogger.error (this, "", e);
            throw new EJBException(e);
        } catch (LimitBookingException e) {
			DefaultLogger.error (this, "", e);
            throw new EJBException(e);
        }
    }
	
	private ArrayList retrieveBankGroupDetail() {
		
		try {
			EBLimitBookingDetailLocalHome ebLimitBookingDetailLocalHome = getEBLimitBookingDetailLocalHome();
	            
			Collection c = ebLimitBookingDetailLocalHome.findByLimitBookingIDAndType(
															ICMSConstant.BKG_TYPE_BGEL, getLimitBookingID(), EXCLUDE_STATUS );
			if(null == c || c.size() == 0) {
				return null;
			}
			else {
				
		        Iterator i = c.iterator();
		        ArrayList list = new ArrayList();
				IBankGroupDetail currentBankGroup = null;
				String currentBkgTypeCode = "";
		        while (i.hasNext()) {
		            EBLimitBookingDetailLocal theEjb = (EBLimitBookingDetailLocal) i.next();
		            ILimitBookingDetail item = theEjb.getValue();
		            if (item.getStatus() != null
		                    && item.getStatus().equals( getFindExcludeStatus() )) {
		                continue;
		            }
					if (item.getBkgSubType() != null
		                    &&  ( item.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_BANK_NONFI ) &&
									item.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_EXEMPT ) &&
									item.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_NON_EXEMPT ) 
							) ) {
		                continue;
		            }
					if( !currentBkgTypeCode.equals("") && !currentBkgTypeCode.equals( item.getBkgTypeCode() ) ) {
						list.add( currentBankGroup );
						currentBankGroup = null;
					}
					
					if( currentBankGroup == null ) {
						currentBankGroup = new OBBankGroupDetail( item );
					}
					
					ICustGrpIdentifierBusManager mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();
					ICustGrpIdentifier grpInfo = mgr.getCustGrpIdentifierByTrxIDRef( Long.parseLong( currentBankGroup.getBkgTypeCode() ) );
					
					IGroupSubLimit[] subLimitList = grpInfo.getGroupSubLimit();		
					
					if(subLimitList != null && subLimitList.length > 0 ) {
						DefaultLogger.debug (this, " subLimitList length: " + subLimitList.length);

						for (int j=0; j<subLimitList.length; j++) {
							DefaultLogger.debug (this, " subLimitList : " + subLimitList[j]);
							if( subLimitList[j].getSubLimitTypeCD().equals ( ICMSConstant.SUB_LIMIT_DESC_INTER_LIMIT_ENTRY_CODE )) {
								currentBankGroup.setLimitAmount( subLimitList[j].getLimitAmt() );
							}
							if( subLimitList[j].getDescription().equals( ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE ) ) {
								currentBankGroup.setLimitConvAmount( subLimitList[j].getLimitAmt() );
							}
							if( subLimitList[j].getDescription().equals( ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE ) ) {
								currentBankGroup.setLimitIslamAmount( subLimitList[j].getLimitAmt());
							}
							if( subLimitList[j].getDescription().equals( ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE ) ) {
								currentBankGroup.setLimitInvAmount( subLimitList[j].getLimitAmt() );
							}	
						}//end for
					}//end if subLimitList != null
					
					/*if( item.getBkgSubType() != null ) {
						if( item.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_BANK ) ) {
							currentBankGroup.setLimitAmount( item.getLimitAmount() );
						}		
						if( item.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_CONV ) ) {
							currentBankGroup.setLimitConvAmount( item.getLimitAmount() );
						}
						if( item.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_ISLM ) ) {
							currentBankGroup.setLimitIslamAmount( item.getLimitAmount() );
						}
						if( item.getBkgSubType().equals( ICMSConstant.BKG_SUB_TYPE_INV ) ) {
							currentBankGroup.setLimitInvAmount( item.getLimitAmount() );
						}					
					}			
					*/
					currentBkgTypeCode = item.getBkgTypeCode();
					DefaultLogger.debug(this, "retrieveBankGroupDetail, currentBkgTypeCode="+currentBkgTypeCode);
					DefaultLogger.debug(this, "retrieveBankGroupDetail, LimitBookingID="+item.getLimitBookingID());
					DefaultLogger.debug(this, "retrieveBankGroupDetail, LimitBookingDetailID="+item.getLmtBookDtlID());
		            
		        }
				if( !currentBkgTypeCode.equals("") ) {
					list.add( currentBankGroup );
				}
		        return list;
			}
		} catch(CustGrpIdentifierException e) {           
			DefaultLogger.error (this, "", e);
            throw new EJBException("Caught Exception!", e);        	
		} 
		catch (FinderException e) {
			DefaultLogger.error (this, "", e);
            throw new EJBException(e);
        } catch (LimitBookingException e) {
			DefaultLogger.error (this, "", e);
            throw new EJBException(e);
        }
    }	
	
	private ArrayList retrieveBookingResultDetail() {
		
		try {
			EBLimitBookingDetailLocalHome ebLimitBookingDetailLocalHome = getEBLimitBookingDetailLocalHome();
	            
			Collection c = ebLimitBookingDetailLocalHome.findByLimitBookingID(
															getLimitBookingID(), EXCLUDE_STATUS );
			if(null == c || c.size() == 0) {
				return null;
			}
			else {
				
		        Iterator i = c.iterator();
		        ArrayList list = new ArrayList();

		        while (i.hasNext()) {
		            EBLimitBookingDetailLocal theEjb = (EBLimitBookingDetailLocal) i.next();
		            ILimitBookingDetail item = theEjb.getValue();
		            if (item.getStatus() != null
		                    && item.getStatus().equals( getFindExcludeStatus() )) {
		                continue;
		            }			
					// this is not booking result					
					 if ( item.getLimitAmount() == null && item.getBkgResult() == null ) {
		                continue;
		            }	

					DefaultLogger.debug(this, "retrieveBookingResultDetail, LimitBookingID="+item.getLimitBookingID());
					DefaultLogger.debug(this, "retrieveBookingResultDetail, LimitBookingDetailID="+item.getLmtBookDtlID());
					DefaultLogger.debug(this, "retrieveBookingResultDetail, getStatus="+item.getStatus());
		            list.add(item);
		        }
		        return list;
			}
			
		}  catch (FinderException e) {
			DefaultLogger.error (this, "", e);
            throw new EJBException(e);
        } catch (LimitBookingException e) {
			DefaultLogger.error (this, "", e);
            throw new EJBException(e);
        }
    }
	
	private void createLimitBookingDetail(List createList, long limitBookingPK) throws LimitBookingException {		     

        if(null == createList || createList.size() == 0) {
            return; //do nothing
        }
		try {
	        EBLimitBookingDetailLocalHome ebLimitBookingDetailLocalHome = getEBLimitBookingDetailLocalHome();
	            
			Collection c = ebLimitBookingDetailLocalHome.findByLimitBookingID(
															getLimitBookingID(), EXCLUDE_STATUS );

	        Iterator i = createList.iterator();
			ArrayList aList = new ArrayList(c.size());
            EBLimitBookingDetailLocalHome home = getEBLimitBookingDetailLocalHome();
            while(i.hasNext()) {
                ILimitBookingDetail ob = (ILimitBookingDetail)i.next();               
                EBLimitBookingDetailLocal local = home.create(ob, limitBookingPK);
                aList.add(local);
            }
        }
        catch (FinderException e) {
			DefaultLogger.error (this, "", e);
            throw new LimitBookingException("Caught FinderException Exception!", e);
        }
		catch(LimitBookingException e) {
			DefaultLogger.error (this, "", e);
            throw e;
        }
        catch(Exception e) {
			DefaultLogger.error (this, "", e);
            throw new LimitBookingException("Caught Exception!", e);
        }
    }	
	
	private void deleteAllLimitBookingDetail() throws LimitBookingException 
	{
		updateLimitBookingDetail( null );		
	}
	
	private void successAllLimitBookingDetail() throws LimitBookingException 
	{
		successLimitBookingDetail( ICMSConstant.BKG_TYPE_ECO_POL );
		successLimitBookingDetail( ICMSConstant.BKG_TYPE_BGEL );
	}
		
	private void updateLimitBookingDetail(List value) throws LimitBookingException 
	{    
	    try {
            EBLimitBookingDetailLocalHome ebLimitBookingDetailLocalHome = getEBLimitBookingDetailLocalHome();
            
			Collection c = ebLimitBookingDetailLocalHome.findByLimitBookingID(
														getLimitBookingID(), EXCLUDE_STATUS );
			
				
            if(null == value) {
                if(null == c || c.size() == 0) {
                    return; //nothing to do
                }
                else {
                    //delete all Limit Booking Detail
                    deleteLimitBookingDetail(new ArrayList(c));
                }
            }
            else if(null == c || c.size() == 0) {
                //create new records
                createLimitBookingDetail( value, getLimitBookingID() );
            }
            else {
                Iterator i = c.iterator();
                ArrayList createList = new ArrayList();    //contains list of OBs
                ArrayList deleteList = new ArrayList(); //contains list of local interfaces

                //identify identify records for delete or udpate first
                while(i.hasNext()) {
                    EBLimitBookingDetailLocal local = (EBLimitBookingDetailLocal)i.next();
                    
                    long sid = local.getCmsRef();
                    boolean update = false;

                    for(int j=0; j<value.size(); j++) {
                        ILimitBookingDetail newOB = (ILimitBookingDetail)value.get(j);
                       
                        if(newOB.getCmsRef() == sid) {
                            //perform update
                            local.setValue(newOB);
                            update = true;
                            break;
                        }
                    }
                    if(!update) {
                        //add for delete
                        deleteList.add(local);
                    }
                }
                //next identify records for add
                for(int j=0; j<value.size(); j++) {
                    i = c.iterator();
                    ILimitBookingDetail newOB = (ILimitBookingDetail)value.get(j);
                    boolean found = false;

                    while(i.hasNext()) {
                        EBLimitBookingDetailLocal local = (EBLimitBookingDetailLocal)i.next();                        
                        long sid = local.getCmsRef();
                        
                        if(newOB.getCmsRef() == sid) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        //add for adding
                        createList.add(newOB);
                    }
                }
                deleteLimitBookingDetail(deleteList);
                createLimitBookingDetail( createList, getLimitBookingID() );
            }
        }
        catch (FinderException e) {
			DefaultLogger.error (this, "", e);
            throw new LimitBookingException("Caught FinderException Exception!", e);
        }
		catch(LimitBookingException e) {
			DefaultLogger.error (this, "", e);
            throw e;
        }
        catch(Exception e) {
			DefaultLogger.error (this, "", e);
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
	private void deleteLimitBookingDetail(List deleteList) throws LimitBookingException {
	
        if(null == deleteList || deleteList.size() == 0) {
            return; //do nothing
        }
        try {            
			
            Iterator i = deleteList.iterator();
            while(i.hasNext()) {
                EBLimitBookingDetailLocal local = (EBLimitBookingDetailLocal)i.next();    

                //do soft delete
				local.delete();                
            }
        }
        catch(Exception e) {
			e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    } 
	
	private void successLimitBookingDetail(String bkgCatType) throws LimitBookingException {
		                    			       
        try {            
			
            EBLimitBookingDetailLocalHome ebLimitBookingDetailLocalHome = getEBLimitBookingDetailLocalHome();
			Collection c = null;
			if( bkgCatType != null ) {
				c = ebLimitBookingDetailLocalHome.findByLimitBookingIDAndType(
														bkgCatType, getLimitBookingID(), EXCLUDE_STATUS );
			
				
            } else {
				c = ebLimitBookingDetailLocalHome.findByLimitBookingID(
														getLimitBookingID(), EXCLUDE_STATUS );
			}
			
			if(null == c || c.size() == 0) {
				return; //nothing to do
			}
			else {
				
				Iterator i = c.iterator();
	            while(i.hasNext()) {
	                EBLimitBookingDetailLocal local = (EBLimitBookingDetailLocal)i.next();    

	                //update status to successful
					local.success();                
	            }
			}
        }
        catch(Exception e) {
			e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    } 
	
    /**
	     * EJB callback method to set the context of the bean.
	     * @param context the entity context.
	     */
    public void setEntityContext (EntityContext context) {
        this.context = context;
    }

    /**
	     * EJB callback method to clears the context of the bean.
	     */
    public void unsetEntityContext() {
        this.context = null;
    }

    /**
	     * This method is called when the container picks this entity object
	     * and assigns it to a specific entity object. No implementation currently
	     * acquires any additional resources that it needs when it is in the
	     * ready state.
	     */
    public void ejbActivate()
    {}

    /**
	     * This method is called when the container diassociates the bean
	     * from the entity object identity and puts the instance back into
	     * the pool of available instances. No implementation is currently
	     * provided to release resources that should not be held while the
	     * instance is in the pool.
	     */
    public void ejbPassivate()
    {}

    /**
	     * The container invokes this method on the bean whenever it
	     * becomes necessary to synchronize the bean's state with the
	     * state in the database. This method is called after the container
	     * has loaded the bean's state from the database.
	     */
    public void ejbLoad()
    {}

    /**
	     * The container invokes this method on the bean whenever it
	     * becomes necessary to synchronize the state in the database
	     * with the state of the bean. This method is called before the
	     * container extracts the fields and writes them into the database.
	     */
    public void ejbStore()
    {}

    /**
	     * The container invokes this method in response to a client-invoked
	     * remove request. No implementation is currently provided for taking
	     * actions before the bean is removed from the database.
	     */
    public void ejbRemove()
    {		
	}
	
	/**
	* Method to get EB Local Home for EBLimitBookingDetail
	*
	* @return EBLimitBookingDetailLocalHome
	* @throws LimitBookingException on errors
	*/
	protected EBLimitBookingDetailLocalHome getEBLimitBookingDetailLocalHome() throws LimitBookingException {
		EBLimitBookingDetailLocalHome home = (EBLimitBookingDetailLocalHome)BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_BOOKING_DETAIL_LOCAL_JNDI, EBLimitBookingDetailLocalHome.class.getName());

		if(null != home) {
			return home;
		}
		else {
			throw new LimitBookingException ("EBLimitBookingDetailLocalHome is null!");
		}
	}
	
}