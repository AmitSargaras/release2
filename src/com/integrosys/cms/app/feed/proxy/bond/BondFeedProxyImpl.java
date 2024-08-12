/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/BondFeedProxyImpl.java,v 1.7 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.feed.proxy.bond;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.bond.BondFeedEntryException;
import com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedBusManager;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.bond.OBBondFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class BondFeedProxyImpl implements IBondFeedProxy {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IBondFeedGroup
	 * @throws BondFeedGroupException on errors retrieving the bond feed
	 */

	private IBondFeedBusManager bondFeedBusManager;

	private IBondFeedBusManager stagingbondFeedBusManager;

	private ITrxControllerFactory trxControllerFactory;
	
	//Add For File Upload
    private IBondFeedBusManager stagingBondFeedFileMapperIdBusManager;
	
	private IBondFeedBusManager bondFeedFileMapperIdBusManager;




	/**
	 * @return the stagingBondFeedFileMapperIdBusManager
	 */
	public IBondFeedBusManager getStagingBondFeedFileMapperIdBusManager() {
		return stagingBondFeedFileMapperIdBusManager;
	}

	/**
	 * @param stagingBondFeedFileMapperIdBusManager the stagingBondFeedFileMapperIdBusManager to set
	 */
	public void setStagingBondFeedFileMapperIdBusManager(
			IBondFeedBusManager stagingBondFeedFileMapperIdBusManager) {
		this.stagingBondFeedFileMapperIdBusManager = stagingBondFeedFileMapperIdBusManager;
	}

	/**
	 * @return the bondFeedFileMapperIdBusManager
	 */
	public IBondFeedBusManager getBondFeedFileMapperIdBusManager() {
		return bondFeedFileMapperIdBusManager;
	}

	/**
	 * @param bondFeedFileMapperIdBusManager the bondFeedFileMapperIdBusManager to set
	 */
	public void setBondFeedFileMapperIdBusManager(
			IBondFeedBusManager bondFeedFileMapperIdBusManager) {
		this.bondFeedFileMapperIdBusManager = bondFeedFileMapperIdBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IBondFeedBusManager getBondFeedBusManager() {
		return bondFeedBusManager;
	}

	public void setBondFeedBusManager(IBondFeedBusManager bondFeedBusManager) {
		this.bondFeedBusManager = bondFeedBusManager;
	}

	public IBondFeedBusManager getStagingbondFeedBusManager() {
		return stagingbondFeedBusManager;
	}

	public void setStagingbondFeedBusManager(IBondFeedBusManager stagingbondFeedBusManager) {
		this.stagingbondFeedBusManager = stagingbondFeedBusManager;
	}

	protected IBondFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_BOND_FEED_GROUP);

		return aTrxValue;
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws BondFeedGroupException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new BondFeedGroupException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			throw new BondFeedGroupException(e);
		}
		catch (Exception ex) {
			throw new BondFeedGroupException(ex.toString());
		}
	}

	protected IBondFeedGroupTrxValue operate(IBondFeedGroupTrxValue aTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws BondFeedGroupException {
		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IBondFeedGroupTrxValue) result.getTrxValue();
	}

	protected IBondFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IBondFeedGroup aFeedGroup) {

		IBondFeedGroupTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBBondFeedGroupTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBBondFeedGroupTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IBondFeedGroupTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingBondFeedGroup(aFeedGroup);

		return feedGroupTrxValue;
	}

	public IBondFeedGroupTrxValue getBondFeedGroup(long groupID) throws BondFeedGroupException {
		return null; // To change body of implemented methods use Options |
		// File
		// Templates.
	}

	public IBondFeedGroup getActualBondFeedGroup() throws BondFeedGroupException {
		return getBondFeedBusManager().getBondFeedGroup(ICMSConstant.BOND_FEED_GROUP_TYPE);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	public IBondFeedGroupTrxValue checkerApproveBondFeedGroup(ITrxContext anITrxContext,
			IBondFeedGroupTrxValue aTrxValue) throws BondFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");
		
		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_BOND_FEED_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */

	public IBondFeedGroupTrxValue checkerRejectBondFeedGroup(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue)
			throws BondFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_BOND_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IBondFeedGroupTrxValue getBondFeedGroup() throws BondFeedGroupException {
		IBondFeedBusManager mgr = getBondFeedBusManager();
		IBondFeedGroup group = mgr.getBondFeedGroup(ICMSConstant.BOND_FEED_GROUP_TYPE);

		if (group == null) {
			BondFeedGroupException e = new BondFeedGroupException("Cannot find the bond index feed group.");
			e.setErrorCode(IBondFeedProxy.NO_FEED_GROUP);
			throw e;
		}

		IBondFeedGroupTrxValue bb = new OBBondFeedGroupTrxValue();
		bb.setReferenceID(String.valueOf(group.getBondFeedGroupID()));
		bb.setBondFeedGroup(group);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_BOND_FEED_GROUP);
		return operate(bb, param);
	}

	/**
	 * Get the transaction value containing BondFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IBondFeedGroupTrxValue
	 */
	public IBondFeedGroupTrxValue getBondFeedGroupByTrxID(long trxID) throws BondFeedGroupException {
		IBondFeedGroupTrxValue trxValue = new OBBondFeedGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_BOND_FEED_GROUP);

		return operate(trxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the BondFeedGroup
	 * object
	 * @param aFeedGroup - IBondFeedGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 * @return IBondFeedGroupTrxValue the saved trxValue
	 */
	public IBondFeedGroupTrxValue makerUpdateBondFeedGroup(ITrxContext anITrxContext,
			IBondFeedGroupTrxValue aFeedGroupTrxValue, IBondFeedGroup aFeedGroup) throws BondFeedGroupException {
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_BOND_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the BondFeedGroup
	 * object
	 * @param aFeedGroup - IBondFeedGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 */
	public IBondFeedGroupTrxValue makerSubmitBondFeedGroup(ITrxContext anITrxContext,
			IBondFeedGroupTrxValue aFeedGroupTrxValue, IBondFeedGroup aFeedGroup) throws BondFeedGroupException {

		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_BOND_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws BondFeedGroupException
	 */
	public IBondFeedGroupTrxValue makerSubmitRejectedBondFeedGroup(ITrxContext anITrxContext,
			IBondFeedGroupTrxValue aTrxValue) throws BondFeedGroupException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_BOND_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdateBondFeedGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	public IBondFeedGroupTrxValue makerUpdateRejectedBondFeedGroup(ITrxContext anITrxContext,
			IBondFeedGroupTrxValue aTrxValue) throws BondFeedGroupException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_BOND_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a BondFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	public IBondFeedGroupTrxValue makerCloseRejectedBondFeedGroup(ITrxContext anITrxContext,
			IBondFeedGroupTrxValue aTrxValue) throws BondFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_BOND_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a BondFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	public IBondFeedGroupTrxValue makerCloseDraftBondFeedGroup(ITrxContext anITrxContext,
			IBondFeedGroupTrxValue aTrxValue) throws BondFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_BOND_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws BondFeedEntryException
	 */
	public IBondFeedEntry getBondFeedEntryByRic(String ric) throws BondFeedEntryException {
		return getBondFeedBusManager().getBondFeedEntryByRic(ric);
	}
	
	public IBondFeedEntry getBondFeedEntry(String bondCode) throws BondFeedEntryException {
		return getBondFeedBusManager().getBondFeedEntry(bondCode);
	}
	
	//------------------------------------ File Upload-----------------------------------------------------
	
	 private IBondFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
		 IBondFeedGroupTrxValue ccBondFeedGroupTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccBondFeedGroupTrxValue = new OBBondFeedGroupTrxValue(anICMSTrxValue);
	        } else {
	            ccBondFeedGroupTrxValue = new OBBondFeedGroupTrxValue();
	        }
	        ccBondFeedGroupTrxValue = formulateTrxValueID(anITrxContext, (IBondFeedGroupTrxValue) ccBondFeedGroupTrxValue);
	        ccBondFeedGroupTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccBondFeedGroupTrxValue;
	    }
	 
	 private IBondFeedGroupTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
		 IBondFeedGroupTrxValue ccBondFeedGroupTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccBondFeedGroupTrxValue = new OBBondFeedGroupTrxValue(anICMSTrxValue);
	        } else {
	            ccBondFeedGroupTrxValue = new OBBondFeedGroupTrxValue();
	        }
	        ccBondFeedGroupTrxValue = formulateTrxValueID(anITrxContext, (IBondFeedGroupTrxValue) ccBondFeedGroupTrxValue);
	        ccBondFeedGroupTrxValue.setStagingFileMapperID(fileId);
	        return ccBondFeedGroupTrxValue;
	    }
	 private IBondFeedGroupTrxValue formulateTrxValueID(ITrxContext anITrxContext, IBondFeedGroupTrxValue anIBondFeedGroupTrxValue) {
	        anIBondFeedGroupTrxValue.setTrxContext(anITrxContext);
	        anIBondFeedGroupTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_BOND_FEED_GROUP);
	        return anIBondFeedGroupTrxValue;
	    }
 
	 
	 /**
		 * @return Maker insert a fileID to generate a transation.
		 */
	 public IBondFeedGroupTrxValue makerInsertMapperBondFeedEntry(
				ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
				throws BondFeedGroupException, TrxParameterException,
				TransactionException {
			// TODO Auto-generated method stub
			if (anITrxContext == null) {
	            throw new BondFeedGroupException("The ITrxContext is null!!!");
	        }
	        if (obFileMapperID == null) {
	            throw new BondFeedGroupException("The OBFileMapperID to be updated is null !!!");
	        }

	        IBondFeedGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
	        trxValue.setFromState("PENDING_INSERT");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
	        return operate(trxValue, param);
		} 
	 
	 /**
		 * @return Maker check if previous upload is pending.
		 */
	 public boolean isPrevFileUploadPending() throws BondFeedGroupException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getBondFeedBusManager().isPrevFileUploadPending();
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new BondFeedGroupException("ERROR-- Due to null BondFeedEntry object cannot update.");
			}
		}
	 
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public int insertBondFeedEntry(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws BondFeedGroupException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getBondFeedBusManager().insertBondFeedEntry(fileMapperMaster, userName, resultlList);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new BondFeedGroupException("ERROR-- Due to null BondFeedEntry object cannot update.");
			}
		}
	 
	 /**
		 * @return create record with TransID.
		 */
	 
	 public IBondFeedGroupTrxValue getInsertFileByTrxID(String trxID)
		throws BondFeedGroupException, TransactionException,
		CommandProcessingException {
		 	IBondFeedGroupTrxValue trxValue = new OBBondFeedGroupTrxValue();
		 	trxValue.setTransactionID(String.valueOf(trxID));
		 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
		 	OBCMSTrxParameter param = new OBCMSTrxParameter();
		 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		 return operate(trxValue, param);
}
	 
	 /**
		 * @return Pagination for uploaded files in BondFeedEntry.
		 */
	 public List getAllStage(String searchBy, String login)throws BondFeedGroupException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getBondFeedBusManager().getAllStageBondFeedEntry( searchBy, login);
		}else{
			throw new BondFeedGroupException("ERROR- Search criteria is null.");
		}
	  }
	
	 /**
		 * @return Checker approval for uploaded files.
		 */
	 
	 public IBondFeedGroupTrxValue checkerApproveInsertBondFeedEntry(
	 		ITrxContext anITrxContext,
	 		IBondFeedGroupTrxValue anIBondFeedGroupTrxValue)
	 		throws BondFeedGroupException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	        throw new BondFeedGroupException("The ITrxContext is null!!!");
	    }
	    if (anIBondFeedGroupTrxValue == null) {
	        throw new BondFeedGroupException
	                ("The IBondFeedGroupTrxValue to be updated is null!!!");
	    }
	    anIBondFeedGroupTrxValue = formulateTrxValueID(anITrxContext, anIBondFeedGroupTrxValue);
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
	    return operate(anIBondFeedGroupTrxValue, param);
	 }
	 
	 /**
		 * @return list of files uploaded in staging table of BondFeedEntry.
		 */
	 public List getFileMasterList(String searchBy)throws BondFeedGroupException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getBondFeedBusManager().getFileMasterList( searchBy);
		}else{
			throw new BondFeedGroupException("ERROR- Search criteria is null.");
		}
	  }
	 
	 /**
		 * @return Maker insert upload files.
		 */
	 public List insertActualBondFeedEntry(String sysId) throws BondFeedGroupException,TrxParameterException,TransactionException

	 {
	  if(sysId != null){
	 	 try {
	 		 return getBondFeedBusManager().insertActualBondFeedEntry(sysId);
	 	 } catch (Exception e) {		 		
	 		 e.printStackTrace();
	 		 throw new BondFeedGroupException("ERROR- Transaction for the Id is invalid.");
	 	 }
	  }else{
	 	 throw new BondFeedGroupException("ERROR- Id for retrival is null.");
	  }
	 }
	 
	 /**
		 * @return Checker create file master in BondFeedEntry.
		 */
	 
	// public IBondFeedGroupTrxValue checkerCreateBondFeedEntry(ITrxContext anITrxContext, IBondFeedEntry anICCBondFeedEntry, String refStage) throws BondFeedGroupException,TrxParameterException,
	 public IBondFeedGroupTrxValue checkerCreateBondFeedEntry(ITrxContext anITrxContext, IBondFeedGroup anICCBondFeedEntry, String refStage) throws BondFeedGroupException,TrxParameterException,
	 
	 TransactionException {
	     if (anITrxContext == null) {
	         throw new BondFeedGroupException("The ITrxContext is null!!!");
	     }
	     if (anICCBondFeedEntry == null) {
	         throw new BondFeedGroupException("The ICCBondFeedEntry to be updated is null !!!");
	     }

	     IBondFeedGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCBondFeedEntry);
	     trxValue.setFromState("PENDING_CREATE");
	     trxValue.setReferenceID(String.valueOf(anICCBondFeedEntry.getBondFeedGroupID()));
	     trxValue.setStagingReferenceID(refStage);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
	     return operate(trxValue, param);
	 }
	 
	 /**
		 * @return Checker Reject for upload files BondFeedEntry.
		 */

	 public IBondFeedGroupTrxValue checkerRejectInsertBondFeedEntry(
	 	ITrxContext anITrxContext,
	 	IBondFeedGroupTrxValue anIBondFeedGroupTrxValue)
	 	throws BondFeedGroupException, TrxParameterException,
	 	TransactionException {
	 	if (anITrxContext == null) {
	 	  throw new BondFeedGroupException("The ITrxContext is null!!!");
	 	}
	 	if (anIBondFeedGroupTrxValue == null) {
	 	  throw new BondFeedGroupException
	 	          ("The IBondFeedGroupTrxValue to be updated is null!!!");
	 	}
	 		anIBondFeedGroupTrxValue = formulateTrxValueID(anITrxContext, anIBondFeedGroupTrxValue);
	 		OBCMSTrxParameter param = new OBCMSTrxParameter();
	 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	 	return operate(anIBondFeedGroupTrxValue, param);
	 }
	 
	 /**
		 * @return Maker Close rejected files BondFeedEntry.
		 */

	 public IBondFeedGroupTrxValue makerInsertCloseRejectedBondFeedEntry(
	 		ITrxContext anITrxContext,
	 		IBondFeedGroupTrxValue anIBondFeedGroupTrxValue)
	 		throws BondFeedGroupException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	         throw new BondFeedGroupException("The ITrxContext is null!!!");
	     }
	     if (anIBondFeedGroupTrxValue == null) {
	         throw new BondFeedGroupException("The IBondFeedGroupTrxValue to be updated is null!!!");
	     }
	     anIBondFeedGroupTrxValue = formulateTrxValue(anITrxContext, anIBondFeedGroupTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
	     return operate(anIBondFeedGroupTrxValue, param);
	 }
	 public void updateBondFeedEntryItem(List BondFeedEntryList) throws BondFeedGroupException,TrxParameterException,TransactionException
	 {
		 getBondFeedBusManager().updateBondFeedEntryItem(BondFeedEntryList);
		 
	 }
	 
	 /*Return true if Currency Code not Exist*/
	 public boolean isBondCodeExist(List bondCodeList) throws BondFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException
	 {	
		return  getBondFeedBusManager().isBondCodeExist(bondCodeList);
	 }
	 
	 public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws BondFeedGroupException, TrxParameterException, TransactionException {
		 getBondFeedBusManager().deleteTransaction(obFileMapperMaster);		
		}
	//------------------------------------End File Upload-----------------------------------------------------

	public boolean isExistBondCode(String bondCode)
			throws BondFeedGroupException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		return getBondFeedBusManager().isExistBondCode(bondCode);
	}

	
}
