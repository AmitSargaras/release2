/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.internalcreditrating;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.*;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author priya
 *
 */
public abstract class AbstractInternalCreditRatingTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IInternalCreditRatingBusManager internalCreditRatingBusManager;

    private IInternalCreditRatingBusManager stagingInternalCreditRatingBusManager;

    public IInternalCreditRatingBusManager getInternalCreditRatingBusManager() {
        return internalCreditRatingBusManager;
    }

    public void setInternalCreditRatingBusManager(IInternalCreditRatingBusManager internalCreditRatingBusManager) {
        this.internalCreditRatingBusManager = internalCreditRatingBusManager;
    }

    public IInternalCreditRatingBusManager getStagingInternalCreditRatingBusManager() {
        return stagingInternalCreditRatingBusManager;
    }

    public void setStagingInternalCreditRatingBusManager(IInternalCreditRatingBusManager stagingInternalCreditRatingBusManager) {
        this.stagingInternalCreditRatingBusManager = stagingInternalCreditRatingBusManager;
    }

    protected ITrxResult prepareResult(IInternalCreditRatingTrxValue trxValue) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(trxValue);
		return result;
	}

	protected IInternalCreditRatingTrxValue createStagingInternalCreditRating (IInternalCreditRatingTrxValue trxValue) throws TrxOperationException {
		
		try {
			
			List iCRList = trxValue.getStagingICRList();

            for (int i=0; i<iCRList.size(); i++) {
                ((IInternalCreditRating)iCRList.get(i)).setStatus (ICMSConstant.STATE_ACTIVE);
            }

//            SBInternalCreditRatingBusManager mgr = InternalCreditRatingBusManagerFactory.getStagingInternalCreditRatingBusManager();
            IInternalCreditRatingBusManager mgr = getStagingInternalCreditRatingBusManager();
            iCRList = mgr.createInternalCreditRating(iCRList);
            trxValue.setStagingICRList(iCRList);
			
			return trxValue;
			
		} catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
	protected IInternalCreditRatingTrxValue createActualInternalCreditRating (IInternalCreditRatingTrxValue trxValue) throws TrxOperationException {
		
		try {
            List iCRList = trxValue.getStagingICRList(); // create get from staging

            for (int i=0; i<iCRList.size(); i++) {
            	((IInternalCreditRating)iCRList.get(i)).setStatus (ICMSConstant.STATE_ACTIVE);
            }

//            SBInternalCreditRatingBusManager mgr = InternalCreditRatingBusManagerFactory.getActualInternalCreditRatingBusManager();
            IInternalCreditRatingBusManager mgr = getInternalCreditRatingBusManager();
            List actual = mgr.createInternalCreditRating(iCRList);
            trxValue.setActualICRList(actual); // set into actual
			
			if(iCRList != null) {
				DefaultLogger.debug (this, " Staging length xx: " + iCRList.size());
			}
			
            return trxValue;
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
		
	}
	
	protected IInternalCreditRatingTrxValue updateActualInternalCreditRating (IInternalCreditRatingTrxValue trxValue) throws TrxOperationException {
		
		try {
			
			List actualICR = trxValue.getActualICRList();
            List stagingICR = trxValue.getStagingICRList();
            
            DefaultLogger.debug (this, " Actual length: " + actualICR.size());
			if( stagingICR != null ) {
				DefaultLogger.debug (this, " Staging length: " + stagingICR.size());
			}
					            
//            SBInternalCreditRatingBusManager mgr = InternalCreditRatingBusManagerFactory.getActualInternalCreditRatingBusManager();
            IInternalCreditRatingBusManager mgr = getInternalCreditRatingBusManager();
			
            long groupId = ICMSConstant.LONG_MIN_VALUE;
//			ArrayList createList = new ArrayList();
//
//			for (int i = 0; i < actualICR.size(); i++) {
//
//				boolean found = false;
//				DefaultLogger.debug (this, "processing actual Internal Credit Rating, ref ID: " + String.valueOf(((IInternalCreditRating)actualICR.get(i)).getRefId()));
//
//				groupId = ((IInternalCreditRating)actualICR.get(i)).getGroupId();
//
//				if(stagingICR != null) {
//					for (int j = 0; j < stagingICR.size(); j++) {
//
//						if( String.valueOf(((IInternalCreditRating)actualICR.get(i)).getRefId()).equals(String.valueOf(((IInternalCreditRating)stagingICR.get(j)).getRefId()))) {
//
//							DefaultLogger.debug (this, "Update Internal Credit Rating, ref ID: " + String.valueOf(((IInternalCreditRating)actualICR.get(i)).getRefId()));
//
//							((IInternalCreditRating)actualICR.get(i)).setIntCredRatCode(((IInternalCreditRating)stagingICR.get(j)).getIntCredRatCode());
//							((IInternalCreditRating)actualICR.get(i)).setIntCredRatLmtAmtCurCode(((IInternalCreditRating)stagingICR.get(j)).getIntCredRatLmtAmtCurCode());
//							((IInternalCreditRating)actualICR.get(i)).setIntCredRatLmtAmt(((IInternalCreditRating)stagingICR.get(j)).getIntCredRatLmtAmt());
//							((IInternalCreditRating)actualICR.get(i)).setStatus (ICMSConstant.STATE_ACTIVE);
//							found = true;
//							break;
//						}
//					}
//				}
//				if(!found)
//				{
//					DefaultLogger.debug (this, "Delete Internal Credit Rating, ref ID: " + String.valueOf(((IInternalCreditRating)actualICR.get(i)).getRefId()));
//					((IInternalCreditRating)actualICR.get(i)).setStatus (ICMSConstant.STATE_DELETED);
//				}
//				createList.add((actualICR.get(i)));
//			}
//
//			if( stagingICR != null ) {
//
//				//create new actual
//
//				for (int j = 0; j < stagingICR.size(); j++) {
//				boolean found = false;
//					for (int i = 0; i < actualICR.size(); i++) {
//						if( String.valueOf(((IInternalCreditRating)actualICR.get(i)).getRefId()).equals(String.valueOf(((IInternalCreditRating)stagingICR.get(j)).getRefId()))) {
//							found = true;
//							break;
//						}
//					}
//					if(!found)
//					{
//						DefaultLogger.debug (this, "Create Internal Credit Rating, ref ID: " + String.valueOf(((IInternalCreditRating)stagingICR.get(j)).getRefId()));
//						//create a new Entity Limit object for actual
//						IInternalCreditRating newICR = new OBInternalCreditRating((IInternalCreditRating)stagingICR.get(j));
//						DefaultLogger.debug (this, "Create Internal Credit Rating ID, newInternalCreditRating: " + newICR);
//						//set primary key to invalid to indicate it is new actual object to create
//						newICR.setIntCredRatId(new Long(ICMSConstant.LONG_INVALID_VALUE));
//						newICR.setGroupId(groupId);
//						newICR.setStatus (ICMSConstant.STATE_ACTIVE);
//						createList.add(newICR);
//					}
//				}
//			}
//
//            List actualICRFinal = mgr.updateInternalCreditRating(createList);
//            trxValue.setActualICRList(actualICRFinal);
            
            // Step 1. Replicate an object from staging

            List replicatedICR = (List) ReplicateUtils.replicateCollectionObject(stagingICR, new String[] {"intCredRatId", "versionTime", "groupId"});
            replicatedICR = replicatedICR == null ? new ArrayList() : replicatedICR;

            // Step 2. Sync between actual copy

            List storedActualICR = (List) EntityAssociationUtils.synchronizeCollectionsByProperties(actualICR, replicatedICR, new String[] {"refId"}, new String[] {"intCredRatId", "versionTime", "groupId"}, true);
            storedActualICR = storedActualICR == null ? new ArrayList() : storedActualICR;

            // Step 3. Find out deleted copies

            List deletedICR = (List) EntityAssociationUtils.retrieveRemovedObjectsCollection(actualICR, replicatedICR, new String[] {"refId"});
            for (Iterator itr = deletedICR.iterator(); itr.hasNext();) {
                IInternalCreditRating iCR = (IInternalCreditRating) itr.next();
                iCR.setStatus(ICMSConstant.STATE_DELETED);
            }
            storedActualICR.addAll(deletedICR);

            // Step 4. Sync between actual copy

            EntityAssociationUtils.synchronizeCollectionsByProperties(storedActualICR, deletedICR, new String[] {"refId"}, new String[] {"intCredRatId", "versionTime", "groupId"}, false);

            // Step 5. Consolidate groupId

            for (Iterator itr = actualICR.iterator(); itr.hasNext();) {
                IInternalCreditRating iCR = (IInternalCreditRating) itr.next();

                if (iCR.getIntCredRatId().longValue() != ICMSConstant.LONG_INVALID_VALUE) {
                    groupId = iCR.getGroupId();
                    break;
                }
            }
            for (Iterator itr = storedActualICR.iterator(); itr.hasNext();) {
                IInternalCreditRating iCR = (IInternalCreditRating) itr.next();

                iCR.setGroupId(groupId);
            }

            List actualICRFinal = mgr.updateInternalCreditRating(storedActualICR);
            trxValue.setActualICRList(actualICRFinal);
	            
	        return trxValue;
				
		} catch (Exception e) {
			throw new TrxOperationException(
			"Exception in updateActualInternalCreditRating(): " + e.toString());
		}
	}

	protected IInternalCreditRatingTrxValue createTransaction(IInternalCreditRatingTrxValue trxValue)throws TrxOperationException {
		
		DefaultLogger.debug(this, "createTransaction - Begin.");
		
		try {
			
			trxValue = prepareTrxValue (trxValue);
            ICMSTrxValue tempValue = super.createTransaction (trxValue);
            OBInternalCreditRatingTrxValue newValue = new OBInternalCreditRatingTrxValue (tempValue);
            newValue.setActualICRList (trxValue.getActualICRList());
            newValue.setStagingICRList(trxValue.getStagingICRList());
            return newValue;
            
		} catch (Exception e) {
			throw new TrxOperationException(e);
		}
		
	}

	protected IInternalCreditRatingTrxValue updateTransaction(IInternalCreditRatingTrxValue trxValue)throws TrxOperationException {
		
		DefaultLogger.debug(this, "updateTransaction - Begin.");
		
		try {
			
			trxValue = prepareTrxValue(trxValue);
			ICMSTrxValue tempValue = super.updateTransaction(trxValue);
			IInternalCreditRatingTrxValue newTrxValue = new OBInternalCreditRatingTrxValue(tempValue);
			newTrxValue.setActualICRList(trxValue.getActualICRList());
			newTrxValue.setStagingICRList(trxValue.getStagingICRList());
			return newTrxValue;
			
		} catch (TransactionException e) {
			throw new TrxOperationException(e);
		} finally {
			DefaultLogger.debug(this, "updateTransaction - End.");
		}
		
	}
	
	private IInternalCreditRatingTrxValue prepareTrxValue (IInternalCreditRatingTrxValue value)
    {
        if (value != null)
        {
            List actual = value.getActualICRList();
            List staging = value.getStagingICRList();

            value.setReferenceID (actual != null && actual.size() != 0 ? String.valueOf (((IInternalCreditRating)actual.get(0)).getGroupId()) : null);
            value.setStagingReferenceID (staging != null && staging.size() != 0 ? String.valueOf (((IInternalCreditRating)staging.get(0)).getGroupId()) : null);
        }
        return value;
    }

	protected IInternalCreditRatingTrxValue getInternalCreditRatingTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IInternalCreditRatingTrxValue) anITrxValue;
		} catch (ClassCastException e) {
			throw new TrxOperationException(
					"The ITrxValue is not of type IInternalCreditRatingTrxValue: "
							+ e.toString());
		}
	}

}
