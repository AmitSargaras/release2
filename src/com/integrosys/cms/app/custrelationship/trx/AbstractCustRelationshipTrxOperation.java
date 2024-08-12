/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.bus.OBCustRelationship;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipBusManagerFactory;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipException;
import com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager;

import java.util.ArrayList;

/**
 * Abstract class that contain methods that is common among the set of
 * Customer Relationship trx operations.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public abstract class AbstractCustRelationshipTrxOperation
    extends CMSTrxOperation implements ITrxRouteOperation
{
    /**
	     * Helper method to cast a generic trx value object to a Customer Relationship 
	     * specific transaction value object.
	     *
	     * @param trxValue transaction value
	     * @return Customer Relationship specific transaction value
	     * @throws TrxOperationException if the trxValue is not of type ICustRelationshipTrxValue
	     */
    protected ICustRelationshipTrxValue getCustRelationshipTrxValue (ITrxValue trxValue)
        throws TrxOperationException
    {
        try {
            return (ICustRelationshipTrxValue ) trxValue;
        }
        catch (ClassCastException e) {
            throw new TrxOperationException ("ITrxValue is not of type ICustRelationshipTrxValue: " + e.toString());
        }
    }

    /**
	     * Create staging Customer Relationship records.
	     *
	     * @param value is of type ICustRelationshipTrxValue
	     * @return Customer Relationship transaction value
	     * @throws TrxOperationException on errors
	     */
    protected ICustRelationshipTrxValue createStagingCustRelationship (ICustRelationshipTrxValue value)
        throws TrxOperationException
    {
        try {           
			
			long parentSubProfileID = value.getParentSubProfileID();
            ICustRelationship[] actual = value.getCustRelationship();
            ICustRelationship[] staging = value.getStagingCustRelationship();

			if(actual != null ) {
				DefaultLogger.debug (this, " Actual length: " + actual.length);
			}
			DefaultLogger.debug (this, " ParentSubProfileID: " + parentSubProfileID);

			if(staging != null && staging.length > 0 ) {
				DefaultLogger.debug (this, " Staging length: " + staging.length);

				for (int i=0; i<staging.length; i++) {
					staging[i].setStatus ( ICMSConstant.STATE_ACTIVE );
				}

				SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getStagingCustRelationshipBusManager();
				staging = mgr.createCustRelationship ( parentSubProfileID, staging );
				value.setStagingCustRelationship (staging);
			}
            return value;
        }
        catch (CustRelationshipException e) {
            throw new TrxOperationException ("CustRelationshipException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Create actual Customer Relationship records.
	     *
	     * @param value is of type ICustRelationshipTrxValue
	     * @return Customer Relationship transaction value
	     * @throws TrxOperationException on errors creating the Customer Relationship
	     */
    protected ICustRelationshipTrxValue createActualCustRelationship (ICustRelationshipTrxValue value)
        throws TrxOperationException
    {
        try {
            ICustRelationship[] custRelnship = value.getStagingCustRelationship(); // create get from staging
			long parentSubProfileID = value.getParentSubProfileID();

            for (int i=0; i<custRelnship.length; i++) {
				custRelnship[i].setStatus ( ICMSConstant.STATE_ACTIVE );
            }

            SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getActualCustRelationshipBusManager();
            custRelnship = mgr.createCustRelationship ( parentSubProfileID, custRelnship );
            value.setCustRelationship (custRelnship); // set into actual
            return value;
        }
        catch (CustRelationshipException e) {
            throw new TrxOperationException ("CustRelationshipException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Update actual Customer Relationship.
	     *
	     * @param value is of type ICustRelationshipTrxValue
	     * @return Customer Relationship transaction value
	     * @throws TrxOperationException on errors updating the actual Customer Relationship
	     */
    protected ICustRelationshipTrxValue updateActualCustRelationship (ICustRelationshipTrxValue value)
        throws TrxOperationException
    {
        try {
            ICustRelationship[] actual = value.getCustRelationship();
            ICustRelationship[] staging = value.getStagingCustRelationship();

			DefaultLogger.debug (this, " Actual length: " + actual.length);
			if( staging != null ) {
				DefaultLogger.debug (this, " Staging length: " + staging.length);
			}
            SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getActualCustRelationshipBusManager();

			long parentSubProfileID = value.getParentSubProfileID();
			long groupID = ICMSConstant.LONG_MIN_VALUE;
			
			//DefaultLogger.debug (this, "parentSubProfileID: " + parentSubProfileID);

			ArrayList createList = new ArrayList();
			for (int i = 0; i < actual.length; i++) {
				boolean found = false;
				DefaultLogger.debug (this, "processing actual cust rel, ref ID: " + String.valueOf( actual[i].getRefID() ) );
				groupID = actual[i].getGroupID();
				if( staging != null ) {
					for (int j = 0; j < staging.length; j++) {

						if( String.valueOf( actual[i].getRefID() ).equals(  String.valueOf( staging[j].getRefID() ) )   ) {
					
							DefaultLogger.debug (this, "Update cust rel, ref ID: " + String.valueOf( actual[i].getRefID() ) );
							actual[i].setStatus ( ICMSConstant.STATE_ACTIVE );
							actual[i].setRelationshipValue( staging[j].getRelationshipValue() );
							actual[i].setRemarks( staging[j].getRemarks() );
							
							found = true;
							break;
						}
					}
				}
				if(!found)
				{
					DefaultLogger.debug (this, "Delete cust rel, ref ID: " + String.valueOf( actual[i].getRefID() ) );
					actual[i].setStatus ( ICMSConstant.STATE_DELETED );
				}
				createList.add( actual[i] );
			}

			if( staging != null ) {

				//create new actual
				for (int j = 0; j < staging.length; j++) {
				boolean found = false;
					for (int i = 0; i < actual.length; i++) {
						if( String.valueOf( actual[i].getRefID() ).equals(  String.valueOf( staging[j].getRefID() ) ) ) {
							found = true;
							break;
						}
					}
					if(!found)
					{
						DefaultLogger.debug (this, "Create cust rel, ref ID: " + String.valueOf( staging[j].getRefID() ) );
						//create a new customer relationship object for actual
						ICustRelationship newCustRel = new OBCustRelationship( staging[j] );
						DefaultLogger.debug (this, "Create cust rel ID, newCustRel: " + newCustRel );
						//set primary key to invalid to indicate it is new actual object to create
						newCustRel.setCustRelationshipID( ICMSConstant.LONG_INVALID_VALUE );
						newCustRel.setGroupID( groupID );						
						newCustRel.setStatus ( ICMSConstant.STATE_ACTIVE );
						createList.add( newCustRel );
					}
				}
			}
            
			actual = mgr.updateCustRelationship ( parentSubProfileID, 
					(ICustRelationship[]) createList.toArray (new OBCustRelationship[0]) );

            value.setCustRelationship (actual); // set into actual

            return value;
        }
        catch (CustRelationshipException e) {
            throw new TrxOperationException ("CustRelationshipException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }
	 
    /**
	     * Method to create a transaction record
	     *
	     * @param value is of type ICustRelationshipTrxValue
	     * @return Customer Relationship transaction value
	     * @throws TrxOperationException on errors
	      */
    protected ICustRelationshipTrxValue createTransaction (ICustRelationshipTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue (value);
            ICMSTrxValue tempValue = super.createTransaction (value);
            OBCustRelationshipTrxValue newValue = new OBCustRelationshipTrxValue (tempValue);
            newValue.setCustRelationship (value.getCustRelationship());
            newValue.setStagingCustRelationship (value.getStagingCustRelationship());
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
	     * @param value is of type ICustRelationshipTrxValue
	     * @return Customer Relationship transaction value
	     * @throws TrxOperationException on errors updating the transaction
	     */
    protected ICustRelationshipTrxValue updateTransaction (ICustRelationshipTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue(value);

            ICMSTrxValue tempValue = super.updateTransaction (value);
            OBCustRelationshipTrxValue newValue = new OBCustRelationshipTrxValue(tempValue);
            newValue.setCustRelationship(value.getCustRelationship());
            newValue.setStagingCustRelationship (value.getStagingCustRelationship());
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
	     * @param value is of type ICustRelationshipTrxValue
	     * @return transaction result
	     */
    protected ITrxResult prepareResult (ICustRelationshipTrxValue value)
    {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue (value);
        return result;
    }

    /**
	     * Helper method to prepare transaction object.
	     *
	     * @param value of type ICustRelationshipTrxValue
	     * @return Customer Relationship transaction value
	     */
    protected ICustRelationshipTrxValue prepareTrxValue (ICustRelationshipTrxValue value)
    {
        if (value != null)
        {
            ICustRelationship[] actual = value.getCustRelationship();
            ICustRelationship[] staging = value.getStagingCustRelationship();

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

