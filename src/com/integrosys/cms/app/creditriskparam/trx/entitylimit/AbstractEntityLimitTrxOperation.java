/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.entitylimit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.*;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of
 * Entity Limit trx operations.
 *
 * @author   $Author: skchai $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public abstract class AbstractEntityLimitTrxOperation
    extends CMSTrxOperation implements ITrxRouteOperation
{

    private IEntityLimitBusManager entityLimitBusManager;

    private IEntityLimitBusManager stagingEntityLimitBusManager;

    public IEntityLimitBusManager getEntityLimitBusManager() {
        return entityLimitBusManager;
    }

    public void setEntityLimitBusManager(IEntityLimitBusManager entityLimitBusManager) {
        this.entityLimitBusManager = entityLimitBusManager;
    }

    public IEntityLimitBusManager getStagingEntityLimitBusManager() {
        return stagingEntityLimitBusManager;
    }

    public void setStagingEntityLimitBusManager(IEntityLimitBusManager stagingEntityLimitBusManager) {
        this.stagingEntityLimitBusManager = stagingEntityLimitBusManager;
    }

    /**
     * Helper method to cast a generic trx value object to a Entity Limit 
     * specific transaction value object.
     *
     * @param trxValue transaction value
     * @return Entity Limit specific transaction value
     * @throws TrxOperationException if the trxValue is not of type IEntityLimitTrxValue
     */
    protected IEntityLimitTrxValue getEntityLimitTrxValue (ITrxValue trxValue)
        throws TrxOperationException
    {
        try {
            return (IEntityLimitTrxValue ) trxValue;
        }
        catch (ClassCastException e) {
            throw new TrxOperationException ("ITrxValue is not of type IEntityLimitTrxValue: " + e.toString());
        }
    }

    /**
	     * Create staging Entity Limit records.
	     *
	     * @param value is of type IEntityLimitTrxValue
	     * @return Entity Limit transaction value
	     * @throws TrxOperationException on errors
	     */
    protected IEntityLimitTrxValue createStagingEntityLimit (IEntityLimitTrxValue value)
        throws TrxOperationException
    {
        try {           
			
            IEntityLimit[] actual = value.getEntityLimit();
            IEntityLimit[] staging = value.getStagingEntityLimit();

			if(actual != null ) {
				DefaultLogger.debug (this, " Actual length: " + actual.length);
			}

			if(staging != null && staging.length > 0 ) {
				DefaultLogger.debug (this, " Staging length: " + staging.length);

				for (int i=0; i<staging.length; i++) {
					staging[i].setStatus ( ICMSConstant.STATE_ACTIVE );
				}

//				SBEntityLimitBusManager mgr = EntityLimitBusManagerFactory.getStagingEntityLimitBusManager();
                IEntityLimitBusManager mgr = getStagingEntityLimitBusManager();
				staging = mgr.createEntityLimit ( staging );
				value.setStagingEntityLimit (staging);
			}
            return value;
        }
        catch (EntityLimitException e) {
            throw new TrxOperationException ("EntityLimitException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Create actual Entity Limit records.
	     *
	     * @param value is of type IEntityLimitTrxValue
	     * @return Entity Limit transaction value
	     * @throws TrxOperationException on errors creating the Entity Limit
	     */
    protected IEntityLimitTrxValue createActualEntityLimit (IEntityLimitTrxValue value)
        throws TrxOperationException
    {
        try {
            IEntityLimit[] EntityLimit = value.getStagingEntityLimit(); // create get from staging

            for (int i=0; i<EntityLimit.length; i++) {
				EntityLimit[i].setStatus ( ICMSConstant.STATE_ACTIVE );
            }

//            SBEntityLimitBusManager mgr = EntityLimitBusManagerFactory.getActualEntityLimitBusManager();
            IEntityLimitBusManager mgr = getEntityLimitBusManager();
            IEntityLimit[] actual = mgr.createEntityLimit ( EntityLimit );
            value.setEntityLimit (actual); // set into actual
			
			if( EntityLimit != null ) {
				DefaultLogger.debug (this, " Staging length xx: " + EntityLimit.length);
			}
			
            return value;
        }
        catch (EntityLimitException e) {
            throw new TrxOperationException ("EntityLimitException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Update actual Entity Limit.
	     *
	     * @param value is of type IEntityLimitTrxValue
	     * @return Entity Limit transaction value
	     * @throws TrxOperationException on errors updating the actual Entity Limit
	     */
    protected IEntityLimitTrxValue updateActualEntityLimit (IEntityLimitTrxValue value)
        throws TrxOperationException
    {
        try {
            IEntityLimit[] actual = value.getEntityLimit();
            IEntityLimit[] staging = value.getStagingEntityLimit();

			DefaultLogger.debug (this, " Actual length: " + actual.length);
			if( staging != null ) {
				DefaultLogger.debug (this, " Staging length: " + staging.length);
			}
//            SBEntityLimitBusManager mgr = EntityLimitBusManagerFactory.getActualEntityLimitBusManager();
            IEntityLimitBusManager mgr = getEntityLimitBusManager();
			
			long groupID = ICMSConstant.LONG_MIN_VALUE;		
//			ArrayList createList = new ArrayList();
//
//			for (int i = 0; i < actual.length; i++) {
//				boolean found = false;
//				DefaultLogger.debug (this, "processing actual Entity Limit, ref ID: " + String.valueOf( actual[i].getCommonRef() ) );
//				groupID = actual[i].getGroupID();
//				if( staging != null ) {
//					for (int j = 0; j < staging.length; j++) {
//
//						if( String.valueOf( actual[i].getCommonRef() ).equals(  String.valueOf( staging[j].getCommonRef() ) )   ) {
//
//							DefaultLogger.debug (this, "Update Entity Limit, ref ID: " + String.valueOf( actual[i].getCommonRef() ) );
//							actual[i].setStatus ( ICMSConstant.STATE_ACTIVE );
//							actual[i].setLimitAmount(staging[j].getLimitAmount());
//							actual[i].setLimitLastReviewDate(staging[j].getLimitLastReviewDate());
//							found = true;
//							break;
//						}
//					}
//				}
//				if(!found)
//				{
//					DefaultLogger.debug (this, "Delete Entity Limit, ref ID: " + String.valueOf( actual[i].getCommonRef() ) );
//					actual[i].setStatus ( ICMSConstant.STATE_DELETED );
//				}
//				createList.add( actual[i] );
//			}
//
//			if( staging != null ) {
//
//				//create new actual
//				for (int j = 0; j < staging.length; j++) {
//				boolean found = false;
//					for (int i = 0; i < actual.length; i++) {
//						if( String.valueOf( actual[i].getCommonRef() ).equals(  String.valueOf( staging[j].getCommonRef() ) ) ) {
//							found = true;
//							break;
//						}
//					}
//					if(!found)
//					{
//						DefaultLogger.debug (this, "Create Entity Limit, ref ID: " + String.valueOf( staging[j].getCommonRef() ) );
//						//create a new Entity Limit object for actual
//						IEntityLimit newEntityLimit = new OBEntityLimit( staging[j] );
//						DefaultLogger.debug (this, "Create Entity Limit ID, newEntityLimit: " + newEntityLimit );
//						//set primary key to invalid to indicate it is new actual object to create
//						newEntityLimit.setEntityLimitID( ICMSConstant.LONG_INVALID_VALUE );
//						newEntityLimit.setGroupID( groupID );
//						newEntityLimit.setStatus ( ICMSConstant.STATE_ACTIVE );
//						createList.add( newEntityLimit );
//					}
//				}
//			}
//
//			actual = mgr.updateEntityLimit ( (IEntityLimit[]) createList.toArray (new OBEntityLimit[0]) );
//
//            value.setEntityLimit (actual); // set into actual

            List actualList = actual == null ? new ArrayList() : Arrays.asList(actual);
            List stagingList = staging == null ? new ArrayList() : Arrays.asList(staging);

            // Step 1. Replicate an object from staging

            List replicatedEL = (List) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(stagingList, new String[] {"entityLimitID", "versionTime", "groupID"});
            replicatedEL = replicatedEL == null ? new ArrayList() : replicatedEL;

            // Step 2. Sync between actual copy

            List storedActualEL = (List) EntityAssociationUtils.synchronizeCollectionsByProperties(actualList, replicatedEL, new String[] {"commonRef"}, new String[] {"entityLimitID", "versionTime", "groupID"}, true);
            storedActualEL = storedActualEL == null ? new ArrayList() : storedActualEL;

            // Step 3. Find out deleted copies

            List deletedEL = (List) EntityAssociationUtils.retrieveRemovedObjectsCollection(actualList, replicatedEL, new String[] {"commonRef"});
            for (Iterator itr = deletedEL.iterator(); itr.hasNext();) {
                IEntityLimit iEL = (IEntityLimit) itr.next();
                iEL.setStatus(ICMSConstant.STATE_DELETED);
            }
            storedActualEL.addAll(deletedEL);

            // Step 4. Sync between actual copy

            EntityAssociationUtils.synchronizeCollectionsByProperties(storedActualEL, deletedEL, new String[] {"commonRef"}, new String[] {"entityLimitID", "versionTime", "groupID"}, false);

            // Step 5. Consolidate groupId

            for (Iterator itr = actualList.iterator(); itr.hasNext();) {
                IEntityLimit iEL = (IEntityLimit) itr.next();

                if (iEL.getEntityLimitID() != ICMSConstant.LONG_INVALID_VALUE) {
                    groupID = iEL.getGroupID();
                    break;
                }
            }
            for (Iterator itr = storedActualEL.iterator(); itr.hasNext();) {
                IEntityLimit iEL = (IEntityLimit) itr.next();

                iEL.setGroupID(groupID);
            }

            actual = mgr.updateEntityLimit ( (IEntityLimit[]) storedActualEL.toArray (new OBEntityLimit[0]) );

            value.setEntityLimit (actual); // set into actual

            return value;
        }
        catch (EntityLimitException e) {
            throw new TrxOperationException ("EntityLimitException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }
	 
    /**
	     * Method to create a transaction record
	     *
	     * @param value is of type IEntityLimitTrxValue
	     * @return Entity Limit transaction value
	     * @throws TrxOperationException on errors
	      */
    protected IEntityLimitTrxValue createTransaction (IEntityLimitTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue (value);
            ICMSTrxValue tempValue = super.createTransaction (value);
            OBEntityLimitTrxValue newValue = new OBEntityLimitTrxValue (tempValue);
            newValue.setEntityLimit (value.getEntityLimit());
            newValue.setStagingEntityLimit (value.getStagingEntityLimit());
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
	     * @param value is of type IEntityLimitTrxValue
	     * @return Entity Limit transaction value
	     * @throws TrxOperationException on errors updating the transaction
	     */
    protected IEntityLimitTrxValue updateTransaction (IEntityLimitTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue(value);

            ICMSTrxValue tempValue = super.updateTransaction (value);
            OBEntityLimitTrxValue newValue = new OBEntityLimitTrxValue(tempValue);
            newValue.setEntityLimit(value.getEntityLimit());
            newValue.setStagingEntityLimit (value.getStagingEntityLimit());
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
	     * @param value is of type IEntityLimitTrxValue
	     * @return transaction result
	     */
    protected ITrxResult prepareResult (IEntityLimitTrxValue value)
    {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue (value);
        return result;
    }

    /**
	     * Helper method to prepare transaction object.
	     *
	     * @param value of type IEntityLimitTrxValue
	     * @return Entity Limit transaction value
	     */
    protected IEntityLimitTrxValue prepareTrxValue (IEntityLimitTrxValue value)
    {
        if (value != null)
        {
            IEntityLimit[] actual = value.getEntityLimit();
            IEntityLimit[] staging = value.getStagingEntityLimit();

            if( (actual != null) && actual.length != 0  && (actual[0].getGroupID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE))
			{
				DefaultLogger.debug (this, "PrepareTrxValue for actual=" + actual[0].getGroupID());
				value.setReferenceID(String.valueOf(actual[0].getGroupID()));
			}
			else
			{
				value.setReferenceID(null);
			}
			
			if( (staging != null) && staging.length != 0  && (staging[0].getGroupID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE))
			{
				DefaultLogger.debug (this, "PrepareTrxValue for staging=" + staging[0].getGroupID());
				value.setStagingReferenceID(String.valueOf(staging[0].getGroupID()));
			}
			else
			{
				value.setStagingReferenceID(null);
			}
        }
        return value;
    }

}

