/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptedinst;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.OBExemptedInst;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstBusManagerFactory;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstException;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager;

import java.util.ArrayList;

/**
 * Abstract class that contain methods that is common among the set of
 * Exempted Institution trx operations.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public abstract class AbstractExemptedInstTrxOperation
    extends CMSTrxOperation implements ITrxRouteOperation
{
    /**
	     * Helper method to cast a generic trx value object to a Exempted Institution 
	     * specific transaction value object.
	     *
	     * @param trxValue transaction value
	     * @return Exempted Institution specific transaction value
	     * @throws TrxOperationException if the trxValue is not of type IExemptedInstTrxValue
	     */
    protected IExemptedInstTrxValue getExemptedInstTrxValue (ITrxValue trxValue)
        throws TrxOperationException
    {
        try {
            return (IExemptedInstTrxValue ) trxValue;
        }
        catch (ClassCastException e) {
            throw new TrxOperationException ("ITrxValue is not of type IExemptedInstTrxValue: " + e.toString());
        }
    }

    /**
	     * Create staging Exempted Institution records.
	     *
	     * @param value is of type IExemptedInstTrxValue
	     * @return Exempted Institution transaction value
	     * @throws TrxOperationException on errors
	     */
    protected IExemptedInstTrxValue createStagingExemptedInst (IExemptedInstTrxValue value)
        throws TrxOperationException
    {
        try {           
			
            IExemptedInst[] actual = value.getExemptedInst();
            IExemptedInst[] staging = value.getStagingExemptedInst();

			if(actual != null ) {
				DefaultLogger.debug (this, " Actual length: " + actual.length);
			}

			if(staging != null && staging.length > 0 ) {
				DefaultLogger.debug (this, " Staging length: " + staging.length);

				for (int i=0; i<staging.length; i++) {
					staging[i].setStatus ( ICMSConstant.STATE_ACTIVE );
				}

				SBExemptedInstBusManager mgr = ExemptedInstBusManagerFactory.getStagingExemptedInstBusManager();
				staging = mgr.createExemptedInst ( staging );
				value.setStagingExemptedInst (staging);
			}
            return value;
        }
        catch (ExemptedInstException e) {
            throw new TrxOperationException ("ExemptedInstException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Create actual Exempted Institution records.
	     *
	     * @param value is of type IExemptedInstTrxValue
	     * @return Exempted Institution transaction value
	     * @throws TrxOperationException on errors creating the Exempted Institution
	     */
    protected IExemptedInstTrxValue createActualExemptedInst (IExemptedInstTrxValue value)
        throws TrxOperationException
    {
        try {
            IExemptedInst[] exemptedInst = value.getStagingExemptedInst(); // create get from staging

            for (int i=0; i<exemptedInst.length; i++) {
				exemptedInst[i].setStatus ( ICMSConstant.STATE_ACTIVE );
            }

            SBExemptedInstBusManager mgr = ExemptedInstBusManagerFactory.getActualExemptedInstBusManager();
            IExemptedInst[] actual = mgr.createExemptedInst ( exemptedInst );
            value.setExemptedInst (actual); // set into actual
			
			if( exemptedInst != null ) {
				DefaultLogger.debug (this, " Staging length xx: " + exemptedInst.length);
			}
			
            return value;
        }
        catch (ExemptedInstException e) {
            throw new TrxOperationException ("ExemptedInstException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
	     * Update actual Exempted Institution.
	     *
	     * @param value is of type IExemptedInstTrxValue
	     * @return Exempted Institution transaction value
	     * @throws TrxOperationException on errors updating the actual Exempted Institution
	     */
    protected IExemptedInstTrxValue updateActualExemptedInst (IExemptedInstTrxValue value)
        throws TrxOperationException
    {
        try {
            IExemptedInst[] actual = value.getExemptedInst();
            IExemptedInst[] staging = value.getStagingExemptedInst();

			DefaultLogger.debug (this, " Actual length: " + actual.length);
			if( staging != null ) {
				DefaultLogger.debug (this, " Staging length: " + staging.length);
			}
            SBExemptedInstBusManager mgr = ExemptedInstBusManagerFactory.getActualExemptedInstBusManager();
			
			long groupID = ICMSConstant.LONG_MIN_VALUE;		
			ArrayList createList = new ArrayList();			
			
			for (int i = 0; i < actual.length; i++) {
				boolean found = false;
				DefaultLogger.debug (this, "processing actual Exempted Inst, ref ID: " + String.valueOf( actual[i].getCommonRef() ) );
				groupID = actual[i].getGroupID();
				if( staging != null ) {
					for (int j = 0; j < staging.length; j++) {

						if( String.valueOf( actual[i].getCommonRef() ).equals(  String.valueOf( staging[j].getCommonRef() ) )   ) {
					
							DefaultLogger.debug (this, "Update Exempted Inst, ref ID: " + String.valueOf( actual[i].getCommonRef() ) );
							actual[i].setStatus ( ICMSConstant.STATE_ACTIVE );							
							found = true;
							break;
						}
					}
				}
				if(!found)
				{
					DefaultLogger.debug (this, "Delete Exempted Inst, ref ID: " + String.valueOf( actual[i].getCommonRef() ) );
					actual[i].setStatus ( ICMSConstant.STATE_DELETED );
				}
				createList.add( actual[i] );
			}
			
			if( staging != null ) {

				//create new actual
				for (int j = 0; j < staging.length; j++) {
				boolean found = false;
					for (int i = 0; i < actual.length; i++) {
						if( String.valueOf( actual[i].getCommonRef() ).equals(  String.valueOf( staging[j].getCommonRef() ) ) ) {
							found = true;
							break;
						}
					}
					if(!found)
					{
						DefaultLogger.debug (this, "Create Exempted Inst, ref ID: " + String.valueOf( staging[j].getCommonRef() ) );
						//create a new Exempted Inst object for actual
						IExemptedInst newExemptedInst = new OBExemptedInst( staging[j] );
						DefaultLogger.debug (this, "Create Exempted Inst ID, newExemptedInst: " + newExemptedInst );
						//set primary key to invalid to indicate it is new actual object to create
						newExemptedInst.setExemptedInstID( ICMSConstant.LONG_INVALID_VALUE );
						newExemptedInst.setGroupID( groupID );	
						newExemptedInst.setStatus ( ICMSConstant.STATE_ACTIVE );
						createList.add( newExemptedInst );
					}
				}
			}
            
			actual = mgr.updateExemptedInst ( (IExemptedInst[]) createList.toArray (new OBExemptedInst[0]) );

            value.setExemptedInst (actual); // set into actual

            return value;
        }
        catch (ExemptedInstException e) {
            throw new TrxOperationException ("ExemptedInstException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }
	 
    /**
	     * Method to create a transaction record
	     *
	     * @param value is of type IExemptedInstTrxValue
	     * @return Exempted Institution transaction value
	     * @throws TrxOperationException on errors
	      */
    protected IExemptedInstTrxValue createTransaction (IExemptedInstTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue (value);
            ICMSTrxValue tempValue = super.createTransaction (value);
            OBExemptedInstTrxValue newValue = new OBExemptedInstTrxValue (tempValue);
            newValue.setExemptedInst (value.getExemptedInst());
            newValue.setStagingExemptedInst (value.getStagingExemptedInst());
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
	     * @param value is of type IExemptedInstTrxValue
	     * @return Exempted Institution transaction value
	     * @throws TrxOperationException on errors updating the transaction
	     */
    protected IExemptedInstTrxValue updateTransaction (IExemptedInstTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue(value);

            ICMSTrxValue tempValue = super.updateTransaction (value);
            OBExemptedInstTrxValue newValue = new OBExemptedInstTrxValue(tempValue);
            newValue.setExemptedInst(value.getExemptedInst());
            newValue.setStagingExemptedInst (value.getStagingExemptedInst());
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
	     * @param value is of type IExemptedInstTrxValue
	     * @return transaction result
	     */
    protected ITrxResult prepareResult (IExemptedInstTrxValue value)
    {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue (value);
        return result;
    }

    /**
	     * Helper method to prepare transaction object.
	     *
	     * @param value of type IExemptedInstTrxValue
	     * @return Exempted Institution transaction value
	     */
    protected IExemptedInstTrxValue prepareTrxValue (IExemptedInstTrxValue value)
    {
        if (value != null)
        {
            IExemptedInst[] actual = value.getExemptedInst();
            IExemptedInst[] staging = value.getStagingExemptedInst();

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

