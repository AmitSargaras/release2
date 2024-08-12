/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/BondFeedProxyImpl.java,v 1.7 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.feed.proxy.mutualfunds;

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
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedBusManager;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedEntryException;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedGroupException;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.mutualfunds.OBMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsFeedProxyImpl implements IMutualFundsFeedProxy {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IBondFeedGroup
	 * @throws MutualFundsFeedGroupException on errors retrieving the bond feed
	 */

	private IMutualFundsFeedBusManager mutualFundsFeedBusManager;

	private IMutualFundsFeedBusManager stagingMutualFundsFeedBusManager;

	private ITrxControllerFactory trxControllerFactory;
	
	//Add For File Upload
    private IMutualFundsFeedBusManager stagingMutualFundsFeedFileMapperIdBusManager;
	
	private IMutualFundsFeedBusManager mutualfundsFeedFileMapperIdBusManager;

	/**
	 * @return the stagingMutualFundsFeedFileMapperIdBusManager
	 */
	public IMutualFundsFeedBusManager getStagingMutualFundsFeedFileMapperIdBusManager() {
		return stagingMutualFundsFeedFileMapperIdBusManager;
	}

	/**
	 * @param stagingMutualFundsFeedFileMapperIdBusManager the stagingMutualFundsFeedFileMapperIdBusManager to set
	 */
	public void setStagingMutualFundsFeedFileMapperIdBusManager(
			IMutualFundsFeedBusManager stagingMutualFundsFeedFileMapperIdBusManager) {
		this.stagingMutualFundsFeedFileMapperIdBusManager = stagingMutualFundsFeedFileMapperIdBusManager;
	}

	/**
	 * @return the mutualfundsFeedFileMapperIdBusManager
	 */
	public IMutualFundsFeedBusManager getMutualfundsFeedFileMapperIdBusManager() {
		return mutualfundsFeedFileMapperIdBusManager;
	}

	/**
	 * @param mutualfundsFeedFileMapperIdBusManager the mutualfundsFeedFileMapperIdBusManager to set
	 */
	public void setMutualfundsFeedFileMapperIdBusManager(
			IMutualFundsFeedBusManager mutualfundsFeedFileMapperIdBusManager) {
		this.mutualfundsFeedFileMapperIdBusManager = mutualfundsFeedFileMapperIdBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IMutualFundsFeedBusManager getMutualFundsFeedBusManager() {
		return mutualFundsFeedBusManager;
	}

	public void setMutualFundsFeedBusManager(
			IMutualFundsFeedBusManager mutualFundsFeedBusManager) {
		this.mutualFundsFeedBusManager = mutualFundsFeedBusManager;
	}

	public IMutualFundsFeedBusManager getStagingMutualFundsFeedBusManager() {
		return stagingMutualFundsFeedBusManager;
	}

	public void setStagingMutualFundsFeedBusManager(
			IMutualFundsFeedBusManager stagingMutualFundsFeedBusManager) {
		this.stagingMutualFundsFeedBusManager = stagingMutualFundsFeedBusManager;
	}

	protected IMutualFundsFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_MUTUAL_FUNDS_FEED_GROUP);

		return aTrxValue;
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws MutualFundsFeedGroupException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new MutualFundsFeedGroupException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			throw new MutualFundsFeedGroupException(e);
		}
		catch (Exception ex) {
			throw new MutualFundsFeedGroupException(ex.toString());
		}
	}

	protected IMutualFundsFeedGroupTrxValue operate(IMutualFundsFeedGroupTrxValue aTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws MutualFundsFeedGroupException {
		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IMutualFundsFeedGroupTrxValue) result.getTrxValue();
	}

	protected IMutualFundsFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IMutualFundsFeedGroup aFeedGroup) {

		IMutualFundsFeedGroupTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBMutualFundsFeedGroupTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBMutualFundsFeedGroupTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IMutualFundsFeedGroupTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingMutualFundsFeedGroup(aFeedGroup);

		return feedGroupTrxValue;
	}

	public IMutualFundsFeedGroupTrxValue getMutualFundsFeedGroup(long groupID) throws MutualFundsFeedGroupException {
		return null; // To change body of implemented methods use Options |
		// File
		// Templates.
	}

	public IMutualFundsFeedGroup getActualMutualFundsFeedGroup() throws MutualFundsFeedGroupException {
		return getMutualFundsFeedBusManager().getMutualFundsFeedGroup(ICMSConstant.MUTUAL_FUNDS_FEED_GROUP_TYPE);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	public IMutualFundsFeedGroupTrxValue checkerApproveMutualFundsFeedGroup(ITrxContext anITrxContext,
			IMutualFundsFeedGroupTrxValue aTrxValue) throws MutualFundsFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");
		
		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_MUTUAL_FUNDS_FEED_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */

	public IMutualFundsFeedGroupTrxValue checkerRejectMutualFundsFeedGroup(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue)
			throws MutualFundsFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_MUTUAL_FUNDS_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IMutualFundsFeedGroupTrxValue getMutualFundsFeedGroup() throws MutualFundsFeedGroupException {
		IMutualFundsFeedBusManager mgr = getMutualFundsFeedBusManager();
		IMutualFundsFeedGroup group = mgr.getMutualFundsFeedGroup(ICMSConstant.MUTUAL_FUNDS_FEED_GROUP_TYPE);

		if (group == null) {
			MutualFundsFeedGroupException e = new MutualFundsFeedGroupException("Cannot find the bond index feed group.");
			e.setErrorCode(IMutualFundsFeedProxy.NO_FEED_GROUP);
			throw e;
		}

		IMutualFundsFeedGroupTrxValue bb = new OBMutualFundsFeedGroupTrxValue();
		bb.setReferenceID(String.valueOf(group.getMutualFundsFeedGroupID()));
		bb.setMutualFundsFeedGroup(group);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_MUTUAL_FUNDS_FEED_GROUP);
		return operate(bb, param);
	}

	/**
	 * Get the transaction value containing MutualFundsFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IMutualFundsFeedGroupTrxValue
	 */
	public IMutualFundsFeedGroupTrxValue getMutualFundsFeedGroupByTrxID(long trxID) throws MutualFundsFeedGroupException {
		IMutualFundsFeedGroupTrxValue trxValue = new OBMutualFundsFeedGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_MUTUAL_FUNDS_FEED_GROUP);

		return operate(trxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the MutualFundsFeedGroup
	 * object
	 * @param aFeedGroup - IMutualFundsFeedGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 * @return IMutualFundsFeedGroupTrxValue the saved trxValue
	 */
	public IMutualFundsFeedGroupTrxValue makerUpdateMutualFundsFeedGroup(ITrxContext anITrxContext,
			IMutualFundsFeedGroupTrxValue aFeedGroupTrxValue, IMutualFundsFeedGroup aFeedGroup) throws MutualFundsFeedGroupException {
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_MUTUAL_FUNDS_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the MutualFundsFeedGroup
	 * object
	 * @param aFeedGroup - IMutualFundsFeedGroup, this could have been passed in the
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 */
	public IMutualFundsFeedGroupTrxValue makerSubmitMutualFundsFeedGroup(ITrxContext anITrxContext,
			IMutualFundsFeedGroupTrxValue aFeedGroupTrxValue, IMutualFundsFeedGroup aFeedGroup) throws MutualFundsFeedGroupException {

		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_MUTUAL_FUNDS_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws MutualFundsFeedGroupException
	 */
	public IMutualFundsFeedGroupTrxValue makerSubmitRejectedMutualFundsFeedGroup(ITrxContext anITrxContext,
			IMutualFundsFeedGroupTrxValue aTrxValue) throws MutualFundsFeedGroupException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_MUTUAL_FUNDS_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdateMutualFundsFeedGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	public IMutualFundsFeedGroupTrxValue makerUpdateRejectedMutualFundsFeedGroup(ITrxContext anITrxContext,
			IMutualFundsFeedGroupTrxValue aTrxValue) throws MutualFundsFeedGroupException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_MUTUAL_FUNDS_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a MutualFundsFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	public IMutualFundsFeedGroupTrxValue makerCloseRejectedMutualFundsFeedGroup(ITrxContext anITrxContext,
			IMutualFundsFeedGroupTrxValue aTrxValue) throws MutualFundsFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_MUTUAL_FUNDS_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a MutualFundsFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	public IMutualFundsFeedGroupTrxValue makerCloseDraftMutualFundsFeedGroup(ITrxContext anITrxContext,
			IMutualFundsFeedGroupTrxValue aTrxValue) throws MutualFundsFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_MUTUAL_FUNDS_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws MutualFundsFeedEntryException
	 */
	public IMutualFundsFeedEntry getMutualFundsFeedEntryByRic(String ric) throws MutualFundsFeedEntryException {
		return getMutualFundsFeedBusManager().getMutualFundsFeedEntryByRic(ric);
	}

	public IMutualFundsFeedEntry getIMutualFundsFeed (String schemeCode) throws MutualFundsFeedEntryException
	 {	
		return  getMutualFundsFeedBusManager().getIMutualFundsFeed(schemeCode);
	 }

	
	public boolean isValidSchemeCode(String schemeCode){
		return  getMutualFundsFeedBusManager().isValidSchemeCode(schemeCode);
	}

	//------------------------------------ File Upload-----------------------------------------------------
	
	 private IMutualFundsFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
		 IMutualFundsFeedGroupTrxValue ccMutualFundsFeedGroupTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccMutualFundsFeedGroupTrxValue = new OBMutualFundsFeedGroupTrxValue(anICMSTrxValue);
	        } else {
	            ccMutualFundsFeedGroupTrxValue = new OBMutualFundsFeedGroupTrxValue();
	        }
	        ccMutualFundsFeedGroupTrxValue = formulateTrxValueID(anITrxContext, (IMutualFundsFeedGroupTrxValue) ccMutualFundsFeedGroupTrxValue);
	        ccMutualFundsFeedGroupTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccMutualFundsFeedGroupTrxValue;
	    }
	 
	 private IMutualFundsFeedGroupTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
		 IMutualFundsFeedGroupTrxValue ccMutualFundsFeedGroupTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccMutualFundsFeedGroupTrxValue = new OBMutualFundsFeedGroupTrxValue(anICMSTrxValue);
	        } else {
	            ccMutualFundsFeedGroupTrxValue = new OBMutualFundsFeedGroupTrxValue();
	        }
	        ccMutualFundsFeedGroupTrxValue = formulateTrxValueID(anITrxContext, (IMutualFundsFeedGroupTrxValue) ccMutualFundsFeedGroupTrxValue);
	        ccMutualFundsFeedGroupTrxValue.setStagingFileMapperID(fileId);
	        return ccMutualFundsFeedGroupTrxValue;
	    }
	 private IMutualFundsFeedGroupTrxValue formulateTrxValueID(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue) {
	        anIMutualFundsFeedGroupTrxValue.setTrxContext(anITrxContext);
	        anIMutualFundsFeedGroupTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_MUTUAL_FUNDS_FEED_GROUP);
	        return anIMutualFundsFeedGroupTrxValue;
	    }

	 
	 /**
		 * @return Maker insert a fileID to generate a transation.
		 */
	 public IMutualFundsFeedGroupTrxValue makerInsertMapperMutualFundsFeedEntry(
				ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
				throws MutualFundsFeedGroupException, TrxParameterException,
				TransactionException {
			// TODO Auto-generated method stub
			if (anITrxContext == null) {
	            throw new MutualFundsFeedGroupException("The ITrxContext is null!!!");
	        }
	        if (obFileMapperID == null) {
	            throw new MutualFundsFeedGroupException("The OBFileMapperID to be updated is null !!!");
	        }

	        IMutualFundsFeedGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
	        trxValue.setFromState("PENDING_INSERT");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
	        return operate(trxValue, param);
		} 
	 
	 /**
		 * @return Maker check if previous upload is pending.
		 */
	 public boolean isPrevFileUploadPending() throws MutualFundsFeedGroupException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getMutualFundsFeedBusManager().isPrevFileUploadPending();
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new MutualFundsFeedGroupException("ERROR-- Due to null MutualFundsFeedEntry object cannot update.");
			}
		}
	 
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public int insertMutualFundsFeedEntry(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws MutualFundsFeedGroupException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getMutualFundsFeedBusManager().insertMutualFundsFeedEntry(fileMapperMaster, userName, resultlList);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new MutualFundsFeedGroupException("ERROR-- Due to null MutualFundsFeedEntry object cannot update.");
			}
		}
	 
	 /**
		 * @return create record with TransID.
		 */
	 
	 public IMutualFundsFeedGroupTrxValue getInsertFileByTrxID(String trxID)
		throws MutualFundsFeedGroupException, TransactionException,
		CommandProcessingException {
		 	IMutualFundsFeedGroupTrxValue trxValue = new OBMutualFundsFeedGroupTrxValue();
		 	trxValue.setTransactionID(String.valueOf(trxID));
		 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
		 	OBCMSTrxParameter param = new OBCMSTrxParameter();
		 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		 return operate(trxValue, param);
}
	 
	 /**
		 * @return Pagination for uploaded files in MutualFundsFeedEntry.
		 */
	 public List getAllStage(String searchBy, String login)throws MutualFundsFeedGroupException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getMutualFundsFeedBusManager().getAllStageMutualFundsFeedEntry( searchBy, login);
		}else{
			throw new MutualFundsFeedGroupException("ERROR- Search criteria is null.");
		}
	  }
	
	 /**
		 * @return Checker approval for uploaded files.
		 */
	 
	 public IMutualFundsFeedGroupTrxValue checkerApproveInsertMutualFundsFeedEntry(
	 		ITrxContext anITrxContext,
	 		IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue)
	 		throws MutualFundsFeedGroupException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	        throw new MutualFundsFeedGroupException("The ITrxContext is null!!!");
	    }
	    if (anIMutualFundsFeedGroupTrxValue == null) {
	        throw new MutualFundsFeedGroupException
	                ("The IMutualFundsFeedGroupTrxValue to be updated is null!!!");
	    }
	    anIMutualFundsFeedGroupTrxValue = formulateTrxValueID(anITrxContext, anIMutualFundsFeedGroupTrxValue);
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
	    return operate(anIMutualFundsFeedGroupTrxValue, param);
	 }
	 
	 /**
		 * @return list of files uploaded in staging table of MutualFundsFeedEntry.
		 */
	 public List getFileMasterList(String searchBy)throws MutualFundsFeedGroupException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getMutualFundsFeedBusManager().getFileMasterList( searchBy);
		}else{
			throw new MutualFundsFeedGroupException("ERROR- Search criteria is null.");
		}
	  }
	 
	 /**
		 * @return Maker insert upload files.
		 */
	 public List insertActualMutualFundsFeedEntry(String sysId) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException

	 {
	  if(sysId != null){
	 	 try {
	 		 return getMutualFundsFeedBusManager().insertActualMutualFundsFeedEntry(sysId);
	 	 } catch (Exception e) {		 		
	 		 e.printStackTrace();
	 		 throw new MutualFundsFeedGroupException("ERROR- Transaction for the Id is invalid.");
	 	 }
	  }else{
	 	 throw new MutualFundsFeedGroupException("ERROR- Id for retrival is null.");
	  }
	 }
	 
	 /**
		 * @return Checker create file master in MutualFundsFeedEntry.
		 */
	 
	// public IMutualFundsFeedGroupTrxValue checkerCreateMutualFundsFeedEntry(ITrxContext anITrxContext, IMutualFundsFeedEntry anICCMutualFundsFeedEntry, String refStage) throws MutualFundsFeedGroupException,TrxParameterException,
	 public IMutualFundsFeedGroupTrxValue checkerCreateMutualFundsFeedEntry(ITrxContext anITrxContext, IMutualFundsFeedGroup anICCMutualFundsFeedEntry, String refStage) throws MutualFundsFeedGroupException,TrxParameterException,
	 
	 TransactionException {
	     if (anITrxContext == null) {
	         throw new MutualFundsFeedGroupException("The ITrxContext is null!!!");
	     }
	     if (anICCMutualFundsFeedEntry == null) {
	         throw new MutualFundsFeedGroupException("The ICCMutualFundsFeedEntry to be updated is null !!!");
	     }

	     IMutualFundsFeedGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCMutualFundsFeedEntry);
	     trxValue.setFromState("PENDING_CREATE");
	     trxValue.setReferenceID(String.valueOf(anICCMutualFundsFeedEntry.getMutualFundsFeedGroupID()));
	     trxValue.setStagingReferenceID(refStage);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
	     return operate(trxValue, param);
	 }
	 
	 /**
		 * @return Checker Reject for upload files MutualFundsFeedEntry.
		 */

	 public IMutualFundsFeedGroupTrxValue checkerRejectInsertMutualFundsFeedEntry(
	 	ITrxContext anITrxContext,
	 	IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue)
	 	throws MutualFundsFeedGroupException, TrxParameterException,
	 	TransactionException {
	 	if (anITrxContext == null) {
	 	  throw new MutualFundsFeedGroupException("The ITrxContext is null!!!");
	 	}
	 	if (anIMutualFundsFeedGroupTrxValue == null) {
	 	  throw new MutualFundsFeedGroupException
	 	          ("The IMutualFundsFeedGroupTrxValue to be updated is null!!!");
	 	}
	 		anIMutualFundsFeedGroupTrxValue = formulateTrxValueID(anITrxContext, anIMutualFundsFeedGroupTrxValue);
	 		OBCMSTrxParameter param = new OBCMSTrxParameter();
	 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	 	return operate(anIMutualFundsFeedGroupTrxValue, param);
	 }
	 
	 /**
		 * @return Maker Close rejected files MutualFundsFeedEntry.
		 */

	 public IMutualFundsFeedGroupTrxValue makerInsertCloseRejectedMutualFundsFeedEntry(
	 		ITrxContext anITrxContext,
	 		IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue)
	 		throws MutualFundsFeedGroupException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	         throw new MutualFundsFeedGroupException("The ITrxContext is null!!!");
	     }
	     if (anIMutualFundsFeedGroupTrxValue == null) {
	         throw new MutualFundsFeedGroupException("The IMutualFundsFeedGroupTrxValue to be updated is null!!!");
	     }
	     anIMutualFundsFeedGroupTrxValue = formulateTrxValue(anITrxContext, anIMutualFundsFeedGroupTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
	     return operate(anIMutualFundsFeedGroupTrxValue, param);
	 }
	 public void updateMutualFundsFeedEntryItem(List MutualFundsFeedEntryList) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException
	 {
		 getMutualFundsFeedBusManager().updateMutualFundsFeedEntryItem(MutualFundsFeedEntryList);
		 
	 }
	 
	 /*Return true if Mutual Funds Scheme Code Exist*/
	 public boolean isMutualFundsCodeExist(List schemeCodeList) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException
	 {	
		return  getMutualFundsFeedBusManager().isMutualFundsCodeExist(schemeCodeList);
	 }
	 /**
		 * @return Maker deleteTransaction.  //A govind 270811
		 */
	 public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,
		TransactionException  {
		 
			try {
				getMutualFundsFeedBusManager().deleteTransaction(obFileMapperMaster);
			} catch (Exception e) {
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- Due to null Mutual Funds cannot update.");
			}
		}
	//------------------------------------End File Upload-----------------------------------------------------

	
}
