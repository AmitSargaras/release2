/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.*;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;

//import com.integrosys.cms.app.creditriskparam.bus.countrylimit.CountryLimitBusManagerFactory;
//import com.integrosys.cms.app.creditriskparam.bus.countrylimit.SBCountryLimitBusManager;

import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitParameterDAO;

import java.math.BigDecimal;
import java.util.*;

/**
 * Abstract class that contain methods that is common among the set of
 * Country Limit trx operations.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public abstract class AbstractCountryLimitTrxOperation
    extends CMSTrxOperation implements ITrxRouteOperation
{

    private ICountryLimitBusManager countryLimitBusManager;

    private ICountryLimitBusManager stagingCountryLimitBusManager;

    public ICountryLimitBusManager getCountryLimitBusManager() {
        return countryLimitBusManager;
    }

    public void setCountryLimitBusManager(ICountryLimitBusManager countryLimitBusManager) {
        this.countryLimitBusManager = countryLimitBusManager;
    }

    public ICountryLimitBusManager getStagingCountryLimitBusManager() {
        return stagingCountryLimitBusManager;
    }

    public void setStagingCountryLimitBusManager(ICountryLimitBusManager stagingCountryLimitBusManager) {
        this.stagingCountryLimitBusManager = stagingCountryLimitBusManager;
    }

    public static final int SCALE = 4;
	public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
	public static final String MY_CURR = CurrencyCode.MYR.getCode();
    
	/**
	     * Helper method to cast a generic trx value object to a Country Limit 
	     * specific transaction value object.
	     *
	     * @param trxValue transaction value
	     * @return Country Limit specific transaction value
	     * @throws TrxOperationException if the trxValue is not of type ICountryLimitTrxValue
	     */
    protected ICountryLimitTrxValue getCountryLimitTrxValue (ITrxValue trxValue)
        throws TrxOperationException
    {
        try {
            return (ICountryLimitTrxValue ) trxValue;
        }
        catch (ClassCastException e) {
            throw new TrxOperationException ("ITrxValue is not of type ICountryLimitTrxValue: " + e.toString());
        }
    }

    /**
	     * Create staging Country Limit records.
	     *
	     * @param value is of type ICountryLimitTrxValue
	     * @return Country Limit transaction value
	     * @throws TrxOperationException on errors
	     */
    protected ICountryLimitTrxValue createStagingCountryLimit (ICountryLimitTrxValue value)
        throws TrxOperationException
    {
        try {           
			
			ICountryLimitParam staging = value.getStagingCountryLimitParam(); // create get from staging
			
			ICountryLimit[] countryLimitStg = staging.getCountryLimitList();
            ICountryRating[] countryRatingStg = staging.getCountryRatingList();
						
			if(countryLimitStg != null && countryLimitStg.length > 0 ) {
				DefaultLogger.debug (this, " Staging length: " + countryLimitStg.length);

				for (int i=0; i<countryLimitStg.length; i++) {
					countryLimitStg[i].setStatus ( ICMSConstant.STATE_ACTIVE );
				}
			}
			
			if(countryRatingStg != null && countryRatingStg.length > 0 ) {
				DefaultLogger.debug (this, " Staging length: " + countryRatingStg.length);

				for (int i=0; i<countryRatingStg.length; i++) {
					//countryRatingStg[i].setStatus ( ICMSConstant.STATE_ACTIVE );
				}
			}
			
//			SBCountryLimitBusManager mgr = CountryLimitBusManagerFactory.getStagingCountryLimitBusManager();
            ICountryLimitBusManager mgr = getStagingCountryLimitBusManager();
			staging = mgr.createCountryLimitParam ( staging );
			value.setStagingCountryLimitParam (staging);

            return value;
        }
        catch (CountryLimitException e) {
            throw new TrxOperationException ("CountryLimitException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }  

    /**
	     * Update actual Country Limit.
	     *
	     * @param value is of type ICountryLimitTrxValue
	     * @return Country Limit transaction value
	     * @throws TrxOperationException on errors updating the actual Country Limit
	     */
    protected ICountryLimitTrxValue updateActualCountryLimit (ICountryLimitTrxValue value)
        throws TrxOperationException
    {
        try {
			ICountryLimitParam actual = value.getCountryLimitParam();
            ICountryLimitParam staging = value.getStagingCountryLimitParam();
					            
//            SBCountryLimitBusManager mgr = CountryLimitBusManagerFactory.getActualCountryLimitBusManager();
            ICountryLimitBusManager mgr = getCountryLimitBusManager();
			
			ICountryLimitParam newCountryLimitParam = new OBCountryLimitParam();
			long groupID = ICMSConstant.LONG_MIN_VALUE;		
			ArrayList createList = new ArrayList();			
			HashMap ratingMap = new HashMap();				
			
			ICountryRating[] actualCtryRating = actual.getCountryRatingList();
            ICountryRating[] stagingCtryRating = staging.getCountryRatingList();

			DefaultLogger.debug (this, " Actual length: " + actualCtryRating.length);
			if( stagingCtryRating != null ) {
				DefaultLogger.debug (this, " Staging length: " + stagingCtryRating.length);
			}
			
//			for (int i = 0; i < actualCtryRating.length; i++) {
//				boolean found = false;
//				DefaultLogger.debug (this, "processing actual country rating, country rating code: " + actualCtryRating[i].getCountryRatingCode() );
//				groupID = actualCtryRating[i].getGroupID();
//				if( stagingCtryRating != null ) {
//					for (int j = 0; j < stagingCtryRating.length; j++) {
//
//						if( actualCtryRating[i].getCountryRatingCode().equals( stagingCtryRating[j].getCountryRatingCode() ) ) {
//
//							DefaultLogger.debug (this, "Update country rating, country rating code: " + actualCtryRating[i].getCountryRatingCode() );
//
//							actualCtryRating[i].setBankCapFundPercentage ( stagingCtryRating[j].getBankCapFundPercentage() );
//							actualCtryRating[i].setPresetCtryLimitPercentage ( stagingCtryRating[j].getPresetCtryLimitPercentage() );
//							found = true;
//							break;
//						}
//					}
//				}
//
//				createList.add( actualCtryRating[i] );
//				ratingMap.put( actualCtryRating[i].getCountryRatingCode(), actualCtryRating[i] );
//			}

            //==========================================================================================================
            createList = (ArrayList) EntityAssociationUtils.synchronizeCollectionsByProperties(actualCtryRating == null ? null : Arrays.asList(actualCtryRating),
                    stagingCtryRating == null ? null : Arrays.asList(stagingCtryRating),
                    new String[]{"countryRatingCode"},
                    new String[]{"countryRatingID", "versionTime", "countryRatingCode", "groupID"},
                    true);
            createList = createList == null ? new ArrayList() : createList;

            for (Iterator itr = createList.iterator(); itr.hasNext();) {
                ICountryRating iCR = (ICountryRating) itr.next();
                groupID = iCR.getGroupID();
                ratingMap.put(iCR.getCountryRatingCode(), iCR);
            }
            //==========================================================================================================
			
			newCountryLimitParam.setCountryRatingList( (ICountryRating[]) createList.toArray (new OBCountryRating[0]) );
			
			createList = new ArrayList();	
			ICountryLimit[] actualCtryLimit = actual.getCountryLimitList();
            ICountryLimit[] stagingCtryLimit = staging.getCountryLimitList();

			DefaultLogger.debug (this, " Actual length: " + actualCtryLimit.length);
			if( stagingCtryLimit != null ) {
				DefaultLogger.debug (this, " Staging length: " + stagingCtryLimit.length);
			}
			
//			for (int i = 0; i < actualCtryLimit.length; i++) {
//				boolean found = false;
//				DefaultLogger.debug (this, "processing actual country limit, ref ID: " + actualCtryLimit[i].getRefID() );
//				groupID = actualCtryLimit[i].getGroupID();
//				if( stagingCtryLimit != null ) {
//					for (int j = 0; j < stagingCtryLimit.length; j++) {
//
//						if( actualCtryLimit[i].getRefID() == stagingCtryLimit[j].getRefID() )
//						{
//							DefaultLogger.debug (this, "Update country limit, ref ID: " + actualCtryLimit[i].getRefID() );
//							actualCtryLimit[i].setCountryCode ( stagingCtryLimit[j].getCountryCode() );
//							actualCtryLimit[i].setCountryRatingCode ( stagingCtryLimit[j].getCountryRatingCode() );
//							actualCtryLimit[i].setCountryLimitAmount ( null );
//							actualCtryLimit[i].setStatus ( ICMSConstant.STATE_ACTIVE );
//							found = true;
//							break;
//						}
//					}
//				}
//				if(!found)
//				{
//					DefaultLogger.debug (this, "Delete country limit, ref ID: " + actualCtryLimit[i].getRefID() );
//					actualCtryLimit[i].setStatus ( ICMSConstant.STATE_DELETED );
//				}
//				createList.add( actualCtryLimit[i] );
//			}
//
//			if( stagingCtryLimit != null ) {
//
//				//create new actual
//				for (int j = 0; j < stagingCtryLimit.length; j++) {
//				boolean found = false;
//					for (int i = 0; i < actualCtryLimit.length; i++) {
//
//						if( actualCtryLimit[i].getRefID() == stagingCtryLimit[j].getRefID() )
//						{
//							found = true;
//							break;
//						}
//					}
//					if(!found)
//					{
//						//create a new country limit object for actual
//						ICountryLimit newCountryLimit = new OBCountryLimit( stagingCtryLimit[j] );
//						DefaultLogger.debug (this, "Create country limit, newCountryLimit: " + newCountryLimit );
//						//set primary key to invalid to indicate it is new actual object to create
//						newCountryLimit.setCountryLimitID( ICMSConstant.LONG_INVALID_VALUE );
//						newCountryLimit.setGroupID( groupID );
//						newCountryLimit.setStatus ( ICMSConstant.STATE_ACTIVE );
//						createList.add( newCountryLimit );
//					}
//				}
//			}

            //==========================================================================================================
            // Step 1. Replicate an object from staging

            List actCtryLimit = actualCtryLimit == null ? null : Arrays.asList(actualCtryLimit);
            List stgCtryLimit = stagingCtryLimit == null ? null : Arrays.asList(stagingCtryLimit);

            List replicatedICL = (List) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(stgCtryLimit, new String[]{"countryLimitID", "versionTime", "groupID"});
            replicatedICL = replicatedICL == null ? new ArrayList() : replicatedICL;

            // Step 2. Sync between actual copy

            createList = (ArrayList) EntityAssociationUtils.synchronizeCollectionsByProperties(actCtryLimit,
                    replicatedICL, new String[]{"refID"}, new String[]{"countryLimitID", "versionTime", "groupID"}, true);
            createList = createList == null ? new ArrayList() : createList;

            // Step 3. Find out deleted copies

            List deletedICL = (List) EntityAssociationUtils.retrieveRemovedObjectsCollection(actCtryLimit,
                    replicatedICL, new String[]{"refID"});
            for (Iterator itr = deletedICL.iterator(); itr.hasNext();) {
                ICountryLimit iCL = (ICountryLimit) itr.next();
                iCL.setStatus(ICMSConstant.STATE_DELETED);
            }
            createList.addAll(deletedICL);

            // Step 4. Sync between actual copy

            EntityAssociationUtils.synchronizeCollectionsByProperties(createList, deletedICL, new String[]{"refID"}, new String[]{"countryLimitID", "versionTime", "groupID"}, false);

            // Step 5. Consolidate groupId

            for (int i = 0; i < actualCtryLimit.length; i++) {
                ICountryLimit iCL = (ICountryLimit) actualCtryLimit[i];

                if (iCL.getCountryLimitID() != ICMSConstant.LONG_INVALID_VALUE) {
                    groupID = iCL.getGroupID();
                    break;
                }
            }
            for (Iterator itr = createList.iterator(); itr.hasNext();) {
                ICountryLimit iCL = (ICountryLimit) itr.next();

                iCL.setGroupID(groupID);
            }
            //==========================================================================================================
			
			calcCountryLimitAmount( createList, ratingMap );			
			            
			newCountryLimitParam.setCountryLimitList( (ICountryLimit[]) createList.toArray (new OBCountryLimit[0]) );
									
			actual = mgr.updateCountryLimitParam ( newCountryLimitParam );

            value.setCountryLimitParam (actual); // set into actual		
			
            return value;
        }
        catch (CountryLimitException e) {
            throw new TrxOperationException ("CountryLimitException caught!", e);
        }
        catch (SearchDAOException e) {
            throw new TrxOperationException("SearchDAOException caught!", e);
        }
		catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }
	

	private void calcCountryLimitAmount( List countryLimitList, HashMap ratingMap )
		throws Exception, SearchDAOException
	{
		
		//Country limit = % of Alliance Banking Group Capital Fund based on the Country Rating x Preset Country Limit %
		//Alliance Banking Group Capital Fund is derived from the internal limit parameter, Alliance Banking Group Capital Fund Amount field. 
		BigDecimal bankCapFund = new BigDecimal("0");
		Amount bankCapFundAmount = new InternalLimitParameterDAO().retrieveBankGroupCapitalFund();
		if( bankCapFundAmount != null ) {
			bankCapFund = bankCapFundAmount.getAmountAsBigDecimal();
		}
		calcCountryLimitAmount( countryLimitList, ratingMap, bankCapFund );			
	}
	
	private static void calcCountryLimitAmount( List countryLimitList, HashMap ratingMap, BigDecimal bankCapFund )
		throws Exception
	{		
		
		if( bankCapFund == null ) {
			return;
		}
		for (int i = 0; i < countryLimitList.size(); i++) {
			ICountryLimit ctryLimit = (ICountryLimit)countryLimitList.get(i);
			
			ICountryRating rating = (ICountryRating)ratingMap.get( ctryLimit.getCountryRatingCode() );
				
			if( rating != null && ( rating.getBankCapFundPercentage() != null || rating.getPresetCtryLimitPercentage() != null ) ) {
						
				BigDecimal result = null;
				if( rating.getBankCapFundPercentage() == null ) {
					rating.setBankCapFundPercentage( new Double(0) );
				}
				if( rating.getPresetCtryLimitPercentage() == null ) {
					rating.setPresetCtryLimitPercentage( new Double(0) );
				}
				
				BigDecimal presetPercent = new BigDecimal( rating.getPresetCtryLimitPercentage().doubleValue() ).divide(new BigDecimal( 100 ), SCALE, ROUNDING_MODE);

				result = new BigDecimal( rating.getBankCapFundPercentage().doubleValue() ).divide(new BigDecimal( 100 ), SCALE, ROUNDING_MODE);
				result = result.multiply( bankCapFund ).multiply( presetPercent );								
				
				if( result != null ) {
					ctryLimit.setCountryLimitAmount ( new Amount( result, new CurrencyCode( MY_CURR ) ) );
				}
			}//endif		
		
		}//endfor
	}
	
	public static void calcCountryLimitAmount( ICountryLimit[] countryLimitList, HashMap ratingMap, BigDecimal bankCapFund )
		throws Exception
	{			
		List list = new ArrayList();
		for (int i = 0; i < countryLimitList.length; i++) {
				
			ICountryLimit ctryLimit = countryLimitList[i];		
			list.add( ctryLimit );
		}
		calcCountryLimitAmount( list, ratingMap, bankCapFund );			
			
	}
	
    /**
	     * Method to create a transaction record
	     *
	     * @param value is of type ICountryLimitTrxValue
	     * @return Country Limit transaction value
	     * @throws TrxOperationException on errors
	      */
    protected ICountryLimitTrxValue createTransaction (ICountryLimitTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue (value);
            ICMSTrxValue tempValue = super.createTransaction (value);
            OBCountryLimitTrxValue newValue = new OBCountryLimitTrxValue (tempValue);
            newValue.setCountryLimitParam (value.getCountryLimitParam());
            newValue.setStagingCountryLimitParam (value.getStagingCountryLimitParam());
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
	     * @param value is of type ICountryLimitTrxValue
	     * @return Country Limit transaction value
	     * @throws TrxOperationException on errors updating the transaction
	     */
    protected ICountryLimitTrxValue updateTransaction (ICountryLimitTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue(value);

            ICMSTrxValue tempValue = super.updateTransaction (value);
            OBCountryLimitTrxValue newValue = new OBCountryLimitTrxValue(tempValue);
            newValue.setCountryLimitParam(value.getCountryLimitParam());
            newValue.setStagingCountryLimitParam(value.getStagingCountryLimitParam());
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
	     * @param value is of type ICountryLimitTrxValue
	     * @return transaction result
	     */
    protected ITrxResult prepareResult (ICountryLimitTrxValue value)
    {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue (value);
        return result;
    }

    /**
	     * Helper method to prepare transaction object.
	     *
	     * @param value of type ICountryLimitTrxValue
	     * @return Country Limit transaction value
	     */
    protected ICountryLimitTrxValue prepareTrxValue (ICountryLimitTrxValue value)
    {
        if (value != null)
        {
            ICountryLimitParam actual = value.getCountryLimitParam();
            ICountryLimitParam staging = value.getStagingCountryLimitParam();

            if( (actual != null) && (actual.getGroupID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE))
			{
				DefaultLogger.debug (this, "PrepareTrxValue for actual=" + actual.getGroupID());
				value.setReferenceID(String.valueOf(actual.getGroupID()));
			}
			else
			{
				value.setReferenceID(null);
			}
			
			if( (staging != null) && (staging.getGroupID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE))
			{
				DefaultLogger.debug (this, "PrepareTrxValue for staging=" + staging.getGroupID());
				value.setStagingReferenceID(String.valueOf(staging.getGroupID()));
			}
			else
			{
				value.setStagingReferenceID(null);
			}
        }
        return value;
    }

}

