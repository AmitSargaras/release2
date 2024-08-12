/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.bus.OBCustShareholder;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipBusManagerFactory;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipException;
import com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager;

import java.util.ArrayList;

/**
 * Abstract class that contain methods that is common among the set of
 * Customer Shareholder trx operations.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public abstract class AbstractCustShareholderTrxOperation
    extends CMSTrxOperation implements ITrxRouteOperation
{
    /**
	     * Helper method to cast a generic trx value object to a Customer Shareholder 
	     * specific transaction value object.
	     *
	     * @param trxValue transaction value
	     * @return Customer Shareholder specific transaction value
	     * @throws TrxOperationException if the trxValue is not of type ICustShareholderTrxValue
	     */
    protected ICustShareholderTrxValue getCustShareholderTrxValue (ITrxValue trxValue)
        throws TrxOperationException
    {
        try {
            return (ICustShareholderTrxValue ) trxValue;
        }
        catch (ClassCastException e) {
            throw new TrxOperationException ("ITrxValue is not of type ICustShareholderTrxValue: " + e.toString());
        }
    }

    /**
	     * Create staging Customer Shareholder records.
	     *
	     * @param value is of type ICustShareholderTrxValue
	     * @return Customer Shareholder transaction value
	     * @throws TrxOperationException on errors
	     */
    protected ICustShareholderTrxValue createStagingCustShareholder (ICustShareholderTrxValue value)
        throws TrxOperationException
    {
        try {           
			
			long parentSubProfileID = value.getParentSubProfileID();
            ICustShareholder[] actual = value.getCustShareholder();
            ICustShareholder[] staging = value.getStagingCustShareholder();

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
				staging = mgr.createCustShareholder ( parentSubProfileID, staging );
				value.setStagingCustShareholder (staging);
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
	     * Create actual Customer Shareholder records.
	     *
	     * @param value is of type ICustShareholderTrxValue
	     * @return Customer Shareholder transaction value
	     * @throws TrxOperationException on errors creating the Customer Shareholder
	     */
    protected ICustShareholderTrxValue createActualCustShareholder (ICustShareholderTrxValue value)
        throws TrxOperationException
    {
        try {
            ICustShareholder[] custRelnship = value.getStagingCustShareholder(); // create get from staging
			long parentSubProfileID = value.getParentSubProfileID();

            for (int i=0; i<custRelnship.length; i++) {
				custRelnship[i].setStatus ( ICMSConstant.STATE_ACTIVE );
            }

            SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getActualCustRelationshipBusManager();
            custRelnship = mgr.createCustShareholder ( parentSubProfileID, custRelnship );
            value.setCustShareholder (custRelnship); // set into actual
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
	     * Update actual Customer Shareholder.
	     *
	     * @param value is of type ICustShareholderTrxValue
	     * @return Customer Shareholder transaction value
	     * @throws TrxOperationException on errors updating the actual Customer Shareholder
	     */
    protected ICustShareholderTrxValue updateActualCustShareholder (ICustShareholderTrxValue value)
        throws TrxOperationException
    {
        try {
            ICustShareholder[] actual = value.getCustShareholder();
            ICustShareholder[] staging = value.getStagingCustShareholder();

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
							actual[i].setPercentageOwn( staging[j].getPercentageOwn() );
							
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
						ICustShareholder newCustRel = new OBCustShareholder( staging[j] );
						//DefaultLogger.debug (this, "Create cust rel ID, newCustRel: " + newCustRel );
						//set primary key to invalid to indicate it is new actual object to create
						newCustRel.setCustRelationshipID( ICMSConstant.LONG_INVALID_VALUE );
						newCustRel.setGroupID( groupID );						
						newCustRel.setStatus ( ICMSConstant.STATE_ACTIVE );
						createList.add( newCustRel );
					}
				}
			}
            
			actual = mgr.updateCustShareholder ( parentSubProfileID, 
					(ICustShareholder[]) createList.toArray (new OBCustShareholder[0]) );

            value.setCustShareholder (actual); // set into actual

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
	     * @param value is of type ICustShareholderTrxValue
	     * @return Customer Shareholder transaction value
	     * @throws TrxOperationException on errors
	      */
    protected ICustShareholderTrxValue createTransaction (ICustShareholderTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue (value);
            ICMSTrxValue tempValue = super.createTransaction (value);
            OBCustShareholderTrxValue newValue = new OBCustShareholderTrxValue (tempValue);
            newValue.setCustShareholder (value.getCustShareholder());
            newValue.setStagingCustShareholder (value.getStagingCustShareholder());
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
	     * @param value is of type ICustShareholderTrxValue
	     * @return Customer Shareholder transaction value
	     * @throws TrxOperationException on errors updating the transaction
	     */
    protected ICustShareholderTrxValue updateTransaction (ICustShareholderTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue(value);

            ICMSTrxValue tempValue = super.updateTransaction (value);
            OBCustShareholderTrxValue newValue = new OBCustShareholderTrxValue(tempValue);
            newValue.setCustShareholder(value.getCustShareholder());
            newValue.setStagingCustShareholder (value.getStagingCustShareholder());
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
	     * @param value is of type ICustShareholderTrxValue
	     * @return transaction result
	     */
    protected ITrxResult prepareResult (ICustShareholderTrxValue value)
    {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue (value);
        return result;
    }

    /**
	     * Helper method to prepare transaction object.
	     *
	     * @param value of type ICustShareholderTrxValue
	     * @return Customer Shareholder transaction value
	     */
    protected ICustShareholderTrxValue prepareTrxValue (ICustShareholderTrxValue value)
    {
        if (value != null)
        {
            ICustShareholder[] actual = value.getCustShareholder();
            ICustShareholder[] staging = value.getStagingCustShareholder();

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

