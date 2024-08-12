/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.trx;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;

import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail;
import com.integrosys.cms.app.limitbooking.bus.SBLimitBookingBusManager;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingException;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingBusManagerFactory;

import java.util.ArrayList;
import java.util.List;
/**
 * Abstract class that contain methods that is common among the set of
 * Limit Booking trx operations.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public abstract class AbstractLimitBookingTrxOperation
    extends CMSTrxOperation implements ITrxRouteOperation
{
    private static final String[] EXCLUDE_UPDATE_METHOD = new String[] {"getLmtBookDtlID", "getLimitBookingID", "getCmsRef", "getStatus", "getVersionTime"};

    /**
	     * Helper method to cast a generic trx value object to a Limit Booking
	     * specific transaction value object.
	     *
	     * @param trxValue transaction value
	     * @return Limit Booking specific transaction value
	     * @throws TrxOperationException if the trxValue is not of type ILimitBookingTrxValue
	     */
    protected ILimitBookingTrxValue getLimitBookingTrxValue (ITrxValue trxValue)
        throws TrxOperationException
    {
        try {
            return (ILimitBookingTrxValue ) trxValue;
        }
        catch (ClassCastException e) {
            throw new TrxOperationException ("ITrxValue is not of type ILimitBookingTrxValue: " + e.toString());
        }
    }

    /**
	     * Create staging Limit Booking records.
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     * @throws TrxOperationException on errors
	     */
    protected ILimitBookingTrxValue createStagingLimitBooking (ILimitBookingTrxValue value)
        throws TrxOperationException
    {
        try {

			ILimitBooking actual = value.getLimitBooking();
			ILimitBooking staging = value.getStagingLimitBooking();						
			
            SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getStagingLimitBookingBusManager();
            staging = mgr.createLimitBooking (actual);
            value.setStagingLimitBooking (staging);
            return value;
        }
        catch (LimitBookingException e) {
            throw new TrxOperationException ("LimitBookingException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Create actual Limit Booking records.
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     * @throws TrxOperationException on errors creating the Limit Booking
	     */
    protected ILimitBookingTrxValue createActualLimitBooking (ILimitBookingTrxValue value)
        throws TrxOperationException
    {
        try {
            ILimitBooking limitBookingValue = value.getStagingLimitBooking(); // create get from staging          

            SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getActualLimitBookingBusManager();
            limitBookingValue = mgr.createLimitBooking ( limitBookingValue );

			value.setLimitBooking ( limitBookingValue ); // set into actual
            return value;
        }
        catch (LimitBookingException e) {
            throw new TrxOperationException ("LimitBookingException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Update actual Limit Booking.
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     * @throws TrxOperationException on errors updating the actual collateral
	     */
    protected ILimitBookingTrxValue updateActualLimitBooking (ILimitBookingTrxValue value)
        throws TrxOperationException
    {
        try {
            ILimitBooking actual = value.getLimitBooking();
            ILimitBooking staging = value.getStagingLimitBooking(); //update from staging

            long actualId = actual.getLimitBookingID();
            long id = staging.getLimitBookingID();
            long stageVersion = staging.getVersionTime();
            
            staging.setLimitBookingID (actual.getLimitBookingID());     //but maintain actual's pk
            staging.setVersionTime (actual.getVersionTime());   //and actual's version time
			
			List createList = new ArrayList();			

			List actualBkgs = actual.getAllBkgs();
			List stagingBkgs = staging.getAllBkgs();
			
			if( actualBkgs != null ) {
				for (int i = 0; i < actualBkgs.size(); i++) {
					boolean found = false;
					ILimitBookingDetail actualLmtBookDtl = (ILimitBookingDetail)actualBkgs.get(i);

					if( stagingBkgs != null ) {
						for (int j = 0; j < stagingBkgs.size(); j++) {
							ILimitBookingDetail stgLmtBookDtl = (ILimitBookingDetail)stagingBkgs.get(j);
						
							if(  actualLmtBookDtl.getBkgType().equals( stgLmtBookDtl.getBkgType() ) && 
									actualLmtBookDtl.getBkgTypeCode().equals( stgLmtBookDtl.getBkgTypeCode() ) &&
								( ( actualLmtBookDtl.getBkgSubType() == null && stgLmtBookDtl.getBkgSubType() == null ) ||
								( actualLmtBookDtl.getBkgSubType() != null && stgLmtBookDtl.getBkgSubType() != null &&
								actualLmtBookDtl.getBkgSubType().equals( stgLmtBookDtl.getBkgSubType() ) ) )					
							)						
							{					
								AccessorUtil.copyValue(stgLmtBookDtl, actualLmtBookDtl, EXCLUDE_UPDATE_METHOD);
								//DefaultLogger.debug (this, " updateActualLimitBooking actualLmtBookDtl: " + actualLmtBookDtl);		
								found = true;
								break;
							}
						}
					}
					if(!found)
					{
						actualLmtBookDtl.setStatus ( ICMSConstant.STATUS_LIMIT_BOOKING_DELETED );
						//DefaultLogger.debug (this, " updateActualLimitBooking set delete: " + actualLmtBookDtl);		

					}
					createList.add( actualLmtBookDtl );
				}
			}
			
			if( stagingBkgs != null ) {

				//create new actual
				for (int j = 0; j < stagingBkgs.size(); j++) {
				boolean found = false;
				ILimitBookingDetail stgLmtBookDtl = (ILimitBookingDetail)stagingBkgs.get(j);

					for (int i = 0; i < actualBkgs.size(); i++) {
						
						ILimitBookingDetail actualLmtBookDtl = (ILimitBookingDetail)actualBkgs.get(i);

						if(  actualLmtBookDtl.getBkgType().equals( stgLmtBookDtl.getBkgType() ) && 
								actualLmtBookDtl.getBkgTypeCode().equals( stgLmtBookDtl.getBkgTypeCode() ) &&
							( ( actualLmtBookDtl.getBkgSubType() == null && stgLmtBookDtl.getBkgSubType() == null ) ||
							( actualLmtBookDtl.getBkgSubType() != null && stgLmtBookDtl.getBkgSubType() != null &&
							actualLmtBookDtl.getBkgSubType().equals( stgLmtBookDtl.getBkgSubType() ) ) )					
						)			
						{
							found = true;
							break;
						}
					}
					if(!found)
					{
						stgLmtBookDtl.setLimitBookingID (actualId);
						createList.add( stgLmtBookDtl );
					}
				}
			}
			
			
	        staging.setAllBkgs( createList );
	
            SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getActualLimitBookingBusManager();

            actual = mgr.updateLimitBooking (staging);

            value.setLimitBooking (actual);    //set into actual

            value.getStagingLimitBooking().setLimitBookingID (id);
            value.getStagingLimitBooking().setVersionTime (stageVersion);
           

            return value;
        }
        catch (LimitBookingException e) {
            throw new TrxOperationException ("LimitBookingException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }

    /**
	     * Method to delete a Limit Booking record in staging.
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     * @throws TrxOperationException on errors
	      */
    protected ILimitBookingTrxValue deleteStagingLimitBooking (ILimitBookingTrxValue value)
        throws TrxOperationException
    {
        try {
            ILimitBooking limitBookingValue = value.getStagingLimitBooking();		

            SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getStagingLimitBookingBusManager();
            limitBookingValue = mgr.deleteLimitBooking (limitBookingValue);
            value.setStagingLimitBooking (limitBookingValue);
            return value;
        }
        catch (LimitBookingException e) {
            throw new TrxOperationException ("LimitBookingException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Method to delete a Limit Booking record in actual.
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     * @throws TrxOperationException on errors
	      */
    protected ILimitBookingTrxValue deleteActualLimitBooking (ILimitBookingTrxValue value)
        throws TrxOperationException
    {
        try {

            ILimitBooking limitBookingValue = value.getLimitBooking();		

            SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getActualLimitBookingBusManager();
            limitBookingValue = mgr.deleteLimitBooking (limitBookingValue);
            value.setLimitBooking (limitBookingValue);
            return value;
        }
        catch (LimitBookingException e) {
            throw new TrxOperationException ("LimitBookingException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }
	
	/**
	     * Method to update status to successful a Limit Booking record in staging.
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     * @throws TrxOperationException on errors
	      */
    protected ILimitBookingTrxValue successStagingLimitBooking (ILimitBookingTrxValue value)
        throws TrxOperationException
    {
        try {
            ILimitBooking limitBookingValue = value.getStagingLimitBooking();		

            SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getStagingLimitBookingBusManager();
            limitBookingValue = mgr.successLimitBooking (limitBookingValue);
            value.setStagingLimitBooking (limitBookingValue);
            return value;
        }
        catch (LimitBookingException e) {
            throw new TrxOperationException ("LimitBookingException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Method to update status to successful a Limit Booking record in actual.
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     * @throws TrxOperationException on errors
	      */
    protected ILimitBookingTrxValue successActualLimitBooking (ILimitBookingTrxValue value)
        throws TrxOperationException
    {
        try {

            ILimitBooking limitBookingValue = value.getLimitBooking();		

            SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getActualLimitBookingBusManager();
            limitBookingValue = mgr.successLimitBooking (limitBookingValue);
            value.setLimitBooking (limitBookingValue);
            return value;
        }
        catch (LimitBookingException e) {
            throw new TrxOperationException ("LimitBookingException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }
	
    
    /**
	     * Method to create a transaction record
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     * @throws TrxOperationException on errors
	      */
    protected ILimitBookingTrxValue createTransaction (ILimitBookingTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue (value);
            ICMSTrxValue tempValue = super.createTransaction (value);
            OBLimitBookingTrxValue newValue = new OBLimitBookingTrxValue (tempValue);
            newValue.setLimitBooking (value.getLimitBooking());
            newValue.setStagingLimitBooking (value.getStagingLimitBooking());
            return newValue;
        }
        catch(TrxOperationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Method to update a transaction record.
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     * @throws TrxOperationException on errors updating the transaction
	     */
    protected ILimitBookingTrxValue updateTransaction (ILimitBookingTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue(value);

            ICMSTrxValue tempValue = super.updateTransaction (value);
            OBLimitBookingTrxValue newValue = new OBLimitBookingTrxValue(tempValue);
            newValue.setLimitBooking(value.getLimitBooking());
            newValue.setStagingLimitBooking (value.getStagingLimitBooking());
            return newValue;
        }
        catch (TrxOperationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Prepares a result object to be returned.
	     *
	     * @param value is of type ILimitBookingTrxValue
	     * @return transaction result
	     */
    protected ITrxResult prepareResult (ILimitBookingTrxValue value)
    {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue (value);
        return result;
    }

    /**
	     * Helper method to prepare transaction object.
	     *
	     * @param value of type ILimitBookingTrxValue
	     * @return Limit Booking transaction value
	     */
    protected ILimitBookingTrxValue prepareTrxValue (ILimitBookingTrxValue value)
    {
        if (value != null)
        {
            ILimitBooking actual = value.getLimitBooking();
            ILimitBooking staging = value.getStagingLimitBooking();

            value.setReferenceID ( actual != null ? String.valueOf (actual.getLimitBookingID()) : null );
            value.setStagingReferenceID ( staging != null ? String.valueOf (staging.getLimitBookingID()) : null );
        }
        return value;
    }

}

