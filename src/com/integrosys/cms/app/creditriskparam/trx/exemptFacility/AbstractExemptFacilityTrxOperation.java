/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/AbstractExemptFacilityTrxOperation.java,v 1.8 2003/08/11 06:36:51 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.*;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract class that contain methods that is common among the set of document item trx operations
 *
 * @author $Author: btchng $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/11 06:36:51 $
 * Tag: $Name:  $
 */
public abstract class AbstractExemptFacilityTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {


protected IExemptFacilityGroupTrxValue getExemptFacilityGroupTrxValue (ITrxValue trxValue)
        throws TrxOperationException
    {
        try {
            return (IExemptFacilityGroupTrxValue ) trxValue;
        }
        catch (ClassCastException e) {
            throw new TrxOperationException ("ITrxValue is not of type IExemptFacilityGroupTrxValue: " + e.toString());
        }
    }

    protected IExemptFacilityGroupTrxValue createStagingExemptFacilityGroup (IExemptFacilityGroupTrxValue value)
        throws TrxOperationException
    {
        try {

            IExemptFacilityGroup actualGrp = value.getExemptFacilityGroup();
            IExemptFacility[] actual = null;
            if (actualGrp != null){
                actual = actualGrp.getExemptFacility();
                DefaultLogger.debug(this, " actualGrp = " + actualGrp);

            }
            IExemptFacilityGroup stageGrp = value.getStagingExemptFacilityGroup();
            IExemptFacility[] staging = null;
            if (stageGrp != null)
                staging = stageGrp.getExemptFacility();
            DefaultLogger.debug(this, " stageGrp = " + stageGrp);

			if(staging != null && staging.length > 0 ) {
				DefaultLogger.debug (this, " Staging length: " + staging.length);

				for (int i=0; i<staging.length; i++) {
                    if (staging[i].getStatus() == null)
					    staging[i].setStatus ( ICMSConstant.STATE_ACTIVE );
				}

				SBExemptFacilityBusManager mgr = ExemptFacilityBusManagerFactory.getStagingFeedBusManager();
				stageGrp = mgr.createExemptFacilityGroup ( stageGrp );
				value.setStagingExemptFacilityGroup(stageGrp);
                if (stageGrp != null && stageGrp.getExemptFacility()!= null &&
                        stageGrp.getExemptFacility().length != 0  && (stageGrp.getExemptFacility()[0].getGroupID() != ICMSConstant.LONG_INVALID_VALUE))
                {
                    DefaultLogger.debug (this, "PrepareTrxValue for actual=" + stageGrp.getExemptFacility()[0].getGroupID());
                    value.setStagingReferenceID(String.valueOf(stageGrp.getExemptFacility()[0].getGroupID()));
                }

			}
            return value;
        }
        catch (ExemptFacilityException e) {
            throw new TrxOperationException ("ExemptFacilityException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    protected IExemptFacilityGroupTrxValue createActualExemptFacilityGroup (IExemptFacilityGroupTrxValue value)
        throws TrxOperationException
    {
        try {
            IExemptFacilityGroup group = value.getStagingExemptFacilityGroup(); // create get from staging
            IExemptFacility[] exemptFacility = null;
            if (group != null)
                exemptFacility = group.getExemptFacility(); // create get from staging

            for (int i=0; i<exemptFacility.length; i++) {
                if (exemptFacility[i].getStatus() == null)
				    exemptFacility[i].setStatus ( ICMSConstant.STATE_ACTIVE );
            }

            SBExemptFacilityBusManager mgr = ExemptFacilityBusManagerFactory.getActualFeedBusManager();
            IExemptFacilityGroup actualGroup = mgr.createExemptFacilityGroup ( group );
            value.setExemptFacilityGroup (actualGroup); // set into actual
//            if (actualGroup != null)
//                value.setReferenceID(String.valueOf(actualGroup.getExemptFacilityGroupID()));
            if(value.getReferenceID() == null || value.getReferenceID().equals("") || value.getReferenceID().equals("0") ){
                if (actualGroup != null && actualGroup.getExemptFacility()!= null &&
                        actualGroup.getExemptFacility().length != 0  && (actualGroup.getExemptFacility()[0].getGroupID() != ICMSConstant.LONG_INVALID_VALUE))
                {
                    DefaultLogger.debug (this, "PrepareTrxValue for actual=" + actualGroup.getExemptFacility()[0].getGroupID());
                    value.setReferenceID(String.valueOf(actualGroup.getExemptFacility()[0].getGroupID()));
                }
            }
			if( exemptFacility != null ) {
				DefaultLogger.debug (this, " Staging length xx: " + exemptFacility.length);
			}

            return value;
        }
        catch (ExemptFacilityException e) {
            throw new TrxOperationException ("ExemptFacilityException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    protected IExemptFacilityGroupTrxValue updateActualExemptFacilityGroup (IExemptFacilityGroupTrxValue value)
        throws TrxOperationException
    {
        try {
            IExemptFacilityGroup actualGrp = value.getExemptFacilityGroup();
            IExemptFacility[] actual = null;
            if (actualGrp != null)
                actual = actualGrp.getExemptFacility();
            IExemptFacilityGroup stageGrp = value.getStagingExemptFacilityGroup();
            //IExemptFacility[] staging = null;
//            if (stageGrp != null)
//                staging = stageGrp.getExemptFacility();

			DefaultLogger.debug (this, " Actual length: " + actual.length);

            IExemptFacility[] itemList = null;
            if (stageGrp != null)
            	itemList = stageGrp.getExemptFacility();
            
            IExemptFacility[] stagingParametersDel=null; // Array without deleted items
            Collection newList = new ArrayList();

            if (itemList != null)
            for (int ii=0; ii<itemList.length; ii++)
            {
                if (!(itemList[ii].getStatus().equals(ICMSConstant.STATE_DELETED)))
                    newList.add(itemList[ii]);
                DefaultLogger.debug(this,"itemList[ii].getStatus() = " + itemList[ii].getStatus());
            }
            stagingParametersDel = (OBExemptFacility[])newList.toArray(new OBExemptFacility[0]);

            if( stagingParametersDel != null ) {
                DefaultLogger.debug (this, " Staging After Deleted length: " + stagingParametersDel.length);
            }

            SBExemptFacilityBusManager mgr = ExemptFacilityBusManagerFactory.getActualFeedBusManager();

			long groupID = ICMSConstant.LONG_MIN_VALUE;
			ArrayList createList = new ArrayList();

			for (int i = 0; i < actual.length; i++) {
				boolean found = false;
				DefaultLogger.debug (this, "processing actual Exempted Fac, ref ID: " + String.valueOf( actual[i].getCmsRef() ) );
				groupID = actual[i].getGroupID();
				if( stagingParametersDel != null ) {
					for (int j = 0; j < stagingParametersDel.length; j++) {

						if( String.valueOf( actual[i].getCmsRef() ).equals(  String.valueOf( stagingParametersDel[j].getCmsRef() ) )   ) {

							DefaultLogger.debug (this, "Update Exempted Fac, ref ID: " + String.valueOf( actual[i].getCmsRef() ) );
							actual[i].setStatus ( ICMSConstant.STATE_ACTIVE );
							found = true;
							break;
						}
					}
				}
				if(!found)
				{
					DefaultLogger.debug (this, "Delete Exempted Fac, ref ID: " + String.valueOf( actual[i].getCmsRef() ) );
					actual[i].setStatus ( ICMSConstant.STATE_DELETED );
				}
				createList.add( actual[i] );
			}

			if( stagingParametersDel != null ) {

				//create new actual
				for (int j = 0; j < stagingParametersDel.length; j++) {
				boolean found = false;
					for (int i = 0; i < actual.length; i++) {
						if( String.valueOf( actual[i].getCmsRef() ).equals(  String.valueOf( stagingParametersDel[j].getCmsRef() ) ) ) {
							found = true;
							break;
						}
					}
					if(!found)
					{
						DefaultLogger.debug (this, "Create Exempted Fac, ref ID: " + String.valueOf( stagingParametersDel[j].getCmsRef() ) );
						//create a new Exempt Facility object for actual
						IExemptFacility newexemptFacility = new OBExemptFacility( stagingParametersDel[j] );
						DefaultLogger.debug (this, "Create Exempted Facility ID, newexemptFacility: " + newexemptFacility );
						//set primary key to invalid to indicate it is new actual object to create
						newexemptFacility.setExemptFacilityID( ICMSConstant.LONG_INVALID_VALUE );
						newexemptFacility.setGroupID( groupID );
						newexemptFacility.setStatus ( ICMSConstant.STATE_ACTIVE );
						createList.add( newexemptFacility );
					}
				}
			}

            actual = (IExemptFacility[]) createList.toArray (new OBExemptFacility[0]);
            actualGrp.setExemptFacility(actual);
			actualGrp = mgr.updateExemptFacilityGroup(actualGrp  );

            value.setExemptFacilityGroup (actualGrp); // set into actual

            return value;
        }
        catch (ExemptFacilityException e) {
            throw new TrxOperationException ("ExemptFacilityException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }

    protected IExemptFacilityGroupTrxValue createTransaction (IExemptFacilityGroupTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue (value);
            ICMSTrxValue tempValue = super.createTransaction (value);
            OBExemptFacilityGroupTrxValue newValue = new OBExemptFacilityGroupTrxValue (tempValue);
            newValue.setExemptFacilityGroup (value.getExemptFacilityGroup());
            newValue.setStagingExemptFacilityGroup (value.getStagingExemptFacilityGroup());
            return newValue;
        }
        catch(TrxOperationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    protected IExemptFacilityGroupTrxValue updateTransaction (IExemptFacilityGroupTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue(value);
            ICMSTrxValue tempValue = super.updateTransaction (value);
            OBExemptFacilityGroupTrxValue newValue = new OBExemptFacilityGroupTrxValue(tempValue);
            newValue.setExemptFacilityGroup(value.getExemptFacilityGroup());
            newValue.setStagingExemptFacilityGroup (value.getStagingExemptFacilityGroup());
            return newValue;
        }
        catch (TrxOperationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    protected ITrxResult prepareResult (IExemptFacilityGroupTrxValue value)
    {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue (value);
        return result;
    }


    public ITrxValue getNextRoute(ITrxValue value) throws TransactionException
    {
        return value;
    }

    protected IExemptFacilityGroupTrxValue prepareTrxValue (IExemptFacilityGroupTrxValue value)
    {
        if (value != null)
        {
            IExemptFacilityGroup actualGrp = value.getExemptFacilityGroup();
            IExemptFacility[] actual = null;
            if (actualGrp != null)
                actual = actualGrp.getExemptFacility();
            IExemptFacilityGroup stageGrp = value.getStagingExemptFacilityGroup();
            IExemptFacility[] staging = null;
            if (stageGrp != null)
                staging = stageGrp.getExemptFacility();

            
            //if((value.getReferenceID() == null || value.getReferenceID().equals(""))){
                if (actual != null && actual.length != 0  && (actual[0].getGroupID() != ICMSConstant.LONG_INVALID_VALUE))
                {
                    DefaultLogger.debug (this, "PrepareTrxValue for actual=" + actual[0].getGroupID());
                    value.setReferenceID(String.valueOf(actual[0].getGroupID()));
                }
                else
                {
                    // TODO
                    DefaultLogger.debug(this," Please check whether null to reference ID in actual !!");
                    value.setReferenceID(null);
                }
            //}

			if( staging != null && staging.length != 0  && (staging[0].getGroupID() != ICMSConstant.LONG_INVALID_VALUE))
			{
				DefaultLogger.debug (this, "PrepareTrxValue for staging=" + staging[0].getGroupID());
				value.setStagingReferenceID(String.valueOf(staging[0].getGroupID()));
			} else {
				value.setStagingReferenceID(null);
			}
        }
        return value;
    }


    /*protected IExemptFacilityGroupTrxValue updateExemptFacilityGroupTransaction(
            IExemptFacilityGroupTrxValue anIExemptFacilityGroupTrxValue)
            throws TrxOperationException {
        try {
            anIExemptFacilityGroupTrxValue = prepareTrxValue(anIExemptFacilityGroupTrxValue);
            DefaultLogger.debug(this, "anIExemptFacilityGroupTrxValue's version time = " +anIExemptFacilityGroupTrxValue.getVersionTime());
            ICMSTrxValue tempValue = super.updateTransaction(anIExemptFacilityGroupTrxValue);
            DefaultLogger.debug(this,"tempValue's version time = " + tempValue.getVersionTime());
            OBExemptFacilityGroupTrxValue newValue = new OBExemptFacilityGroupTrxValue(tempValue);
            newValue.setExemptFacilityGroup(anIExemptFacilityGroupTrxValue.getExemptFacilityGroup());
            newValue.setStagingExemptFacilityGroup(anIExemptFacilityGroupTrxValue.getStagingExemptFacilityGroup());
            DefaultLogger.debug(this,"newValue's version time = " + newValue.getVersionTime());
            return newValue;
        } catch (TransactionException tex) {
            throw new TrxOperationException(tex);
        } catch (Exception ex) {
            throw new TrxOperationException(
                    "General Exception: " + ex.toString());
        }
    }
*/

    /**
     * To get the remote handler for the staging exemptFacilityGroup session bean
     * @return SBExemptFacilityBusManagernager - the remote handler for the staging exemptFacilityGroup session bean
     */
    protected SBExemptFacilityBusManager getSBStagingFeedBusManager() {
        SBExemptFacilityBusManager remote = (SBExemptFacilityBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_EXEMPT_FACILITY_BUS_MANAGER_JNDI_STAGING,
                SBExemptFacilityBusManagerHome.class.getName());
        return remote;
    }


    /**
     * To get the remote handler for the exemptFacilityGroup session bean
     * @return SBExemptFacilityBusManagernager - the remote handler for the exemptFacilityGroup session bean
     */
    protected SBExemptFacilityBusManager getSBExemptFacilityBusManager() {
        SBExemptFacilityBusManager busMgr = (SBExemptFacilityBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_EXEMPT_FACILITY_BUS_MANAGER_JNDI,
                SBExemptFacilityBusManagerHome.class.getName());

        if (busMgr == null) {
            DefaultLogger.error(this, "Unable to get forex feed manager");
        }

        return busMgr;
    }


    /**
     * This method set the primary key from the original to the copied checklist objects.
     * It is required for the case of updating staging from actual and vice versa as there is a need to perform
     * a deep clone of the object and set the required attribute in the object to the original one so that a proper update
     * can be done.
     * @param anOriginal
     * @param aCopy - ICheckList
     * @return ICheckList - the copied object with required attributes from the original checklist
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException on errors
     */
    protected IExemptFacilityGroup mergeExemptFacilityGroup(IExemptFacilityGroup anOriginal,
            IExemptFacilityGroup aCopy)
            throws TrxOperationException {
        aCopy.setExemptFacilityGroupID(anOriginal.getExemptFacilityGroupID());
        aCopy.setVersionTime(anOriginal.getVersionTime());

        return aCopy;
    }

}