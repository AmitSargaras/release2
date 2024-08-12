/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/ForexFeedProxyImpl.java,v 1.15 2005/01/12 06:36:33 hshii Exp $
 */
package com.integrosys.cms.app.feed.proxy.forex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedBusManager;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.forex.OBForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.batch.forex.OBForex;

public class ForexFeedProxyImpl implements IForexFeedProxy {

	private IForexFeedBusManager forexFeedBusManager;

	private IForexFeedBusManager stagingForexFeedBusManager;

	private ITrxControllerFactory TrxControllerFactory;
	
    private IForexFeedBusManager stagingForexFeedFileMapperIdBusManager;
	
	private IForexFeedBusManager forexFeedFileMapperIdBusManager;
	
	/**
	 * @return the forexFeedBusManager
	 */
	public IForexFeedBusManager getForexFeedBusManager() {
		return forexFeedBusManager;
	}

	/**
	 * @param forexFeedBusManager the forexFeedBusManager to set
	 */
	public void setForexFeedBusManager(IForexFeedBusManager forexFeedBusManager) {
		this.forexFeedBusManager = forexFeedBusManager;
	}

	/**
	 * @return the stagingForexFeedBusManager
	 */
	public IForexFeedBusManager getStagingForexFeedBusManager() {
		return stagingForexFeedBusManager;
	}

	/**
	 * @param stagingForexFeedBusManager the stagingForexFeedBusManager to set
	 */
	public void setStagingForexFeedBusManager(
			IForexFeedBusManager stagingForexFeedBusManager) {
		this.stagingForexFeedBusManager = stagingForexFeedBusManager;
	}

	/**
	 * @return the trxControllerFactory
	 */
	public ITrxControllerFactory getTrxControllerFactory() {
		return TrxControllerFactory;
	}

	/**
	 * @param trxControllerFactory the trxControllerFactory to set
	 */
	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		TrxControllerFactory = trxControllerFactory;
	}

	protected void rollback() throws ForexFeedGroupException {
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	public IForexFeedGroupTrxValue checkerApproveForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aTrxValue) throws ForexFeedGroupException {
		/*try {
			return getSbForexFeedProxy().checkerApproveForexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_FOREX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */

	public IForexFeedGroupTrxValue checkerRejectForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aTrxValue) throws ForexFeedGroupException {
		/*try {
			return getSbForexFeedProxy().checkerRejectForexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_FOREX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IForexFeedGroupTrxValue getForexFeedGroup() throws ForexFeedGroupException {
		/*try {
			return getSbForexFeedProxy().getForexFeedGroup();
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		IForexFeedGroupTrxValue vv = new OBForexFeedGroupTrxValue();
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_FOREX_FEED_GROUP);

		return operate(vv, param);
	}

	/**
	 * Get the transaction value containing ForexFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IForexFeedGroupTrxValue
	 */
	public IForexFeedGroupTrxValue getForexFeedGroupByTrxID(long trxID) throws ForexFeedGroupException {
		/*try {
			return getSbForexFeedProxy().getForexFeedGroupByTrxID(trxID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		IForexFeedGroupTrxValue trxValue = new OBForexFeedGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_FOREX_FEED_GROUP);
		return operate(trxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the ForexFeedGroup
	 *        object
	 * @param aFeedGroup - IForexFeedGroup, this could have been passed in the
	 *        trx value, but the intention is that the caller should not have
	 *        modified the trxValue, as the caller does not need to know about
	 *        staging settings et al.
	 * @return IForexFeedGroupTrxValue the saved trxValue
	 */
	public IForexFeedGroupTrxValue makerUpdateForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aFeedGroupTrxValue, IForexFeedGroup aFeedGroup) throws ForexFeedGroupException {
		/*try {
			return getSbForexFeedProxy().makerUpdateForexFeedGroup(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_FOREX_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the ForexFeedGroup
	 *        object
	 * @param aFeedGroup - IForexFeedGroup, this could have been passed in the
	 *        trx value, but the intention is that the caller should not have
	 *        modified the trxValue, as the caller does not need to know about
	 *        staging settings et al.
	 */
	public IForexFeedGroupTrxValue makerSubmitForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aFeedGroupTrxValue, IForexFeedGroup aFeedGroup) throws ForexFeedGroupException {

		/*try {
			return getSbForexFeedProxy().makerSubmitForexFeedGroup(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_FOREX_FEED_GROUP);

		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws ForexFeedGroupException
	 */
	public IForexFeedGroupTrxValue makerSubmitRejectedForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aTrxValue) throws ForexFeedGroupException {

		/*try {
			return getSbForexFeedProxy().makerSubmitRejectedForexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_FOREX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdateForexFeedGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	public IForexFeedGroupTrxValue makerUpdateRejectedForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aTrxValue) throws ForexFeedGroupException {
		/*try {
			return getSbForexFeedProxy().makerUpdateRejectedForexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_FOREX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a ForexFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	public IForexFeedGroupTrxValue makerCloseRejectedForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aTrxValue) throws ForexFeedGroupException {
		/*try {
			return getSbForexFeedProxy().makerCloseRejectedForexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_FOREX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a ForexFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	public IForexFeedGroupTrxValue makerCloseDraftForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aTrxValue) throws ForexFeedGroupException {
		/*try {
			return getSbForexFeedProxy().makerCloseDraftForexFeedGroup(anITrxContext, aTrxValue);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new ForexFeedGroupException("RemoteException", e);
		}*/
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_FOREX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/*private SBForexFeedProxy getSbForexFeedProxy() {

		return (SBForexFeedProxy) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_FEED_PROXY_JNDI,
				SBForexFeedProxyHome.class.getName());
	}*/

	protected IForexFeedGroupTrxValue operate(IForexFeedGroupTrxValue aTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	throws ForexFeedGroupException {

		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IForexFeedGroupTrxValue) result.getTrxValue();
	}
	
	/**
	 * Formulate the checklist Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @return IForexFeedGroupTrxValue - the checklist trx interface formulated
	 */
	protected IForexFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IForexFeedGroup aFeedGroup) {

		IForexFeedGroupTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBForexFeedGroupTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBForexFeedGroupTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IForexFeedGroupTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingForexFeedGroup(aFeedGroup);

		return feedGroupTrxValue;
	}
	
	/**
	 * Formulate the checklist trx object
	 * @param anITrxContext - ITrxContext
	 * @param aTrxValue -
	 * @return ICheckListTrxValue - the checklist trx interface formulated
	 */
	protected IForexFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, IForexFeedGroupTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_FOREX_FEED_GROUP);

		return aTrxValue;
	}
	
	/**
	 * @param anICMSTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws ForexFeedGroupException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue,anOBCMSTrxParameter);
			if (controller == null) {
				throw new ForexFeedGroupException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new ForexFeedGroupException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new ForexFeedGroupException(ex.toString());
		}
	}

	public IForexFeedGroupTrxValue getForexFeedGroup(long groupID)
			throws ForexFeedGroupException {
		return null;
	}
	
	
	
	//------------------------------------ File Upload-----------------------------------------------------
	
	 private IForexFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
		 IForexFeedGroupTrxValue ccForexFeedGroupTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccForexFeedGroupTrxValue = new OBForexFeedGroupTrxValue(anICMSTrxValue);
	        } else {
	            ccForexFeedGroupTrxValue = new OBForexFeedGroupTrxValue();
	        }
	        ccForexFeedGroupTrxValue = formulateTrxValueID(anITrxContext, (IForexFeedGroupTrxValue) ccForexFeedGroupTrxValue);
	        ccForexFeedGroupTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccForexFeedGroupTrxValue;
	    }
	 
	 private IForexFeedGroupTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
		 IForexFeedGroupTrxValue ccForexFeedGroupTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccForexFeedGroupTrxValue = new OBForexFeedGroupTrxValue(anICMSTrxValue);
	        } else {
	            ccForexFeedGroupTrxValue = new OBForexFeedGroupTrxValue();
	        }
	        ccForexFeedGroupTrxValue = formulateTrxValueID(anITrxContext, (IForexFeedGroupTrxValue) ccForexFeedGroupTrxValue);
	        ccForexFeedGroupTrxValue.setStagingFileMapperID(fileId);
	        return ccForexFeedGroupTrxValue;
	    }
	 private IForexFeedGroupTrxValue formulateTrxValueID(ITrxContext anITrxContext, IForexFeedGroupTrxValue anIForexFeedGroupTrxValue) {
	        anIForexFeedGroupTrxValue.setTrxContext(anITrxContext);
	        anIForexFeedGroupTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_FOREX_FEED_GROUP);
	        return anIForexFeedGroupTrxValue;
	    }
  
	 
	 /**
		 * @return Maker insert a fileID to generate a transation.
		 */
	 public IForexFeedGroupTrxValue makerInsertMapperForexFeedEntry(
				ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
				throws ForexFeedGroupException, TrxParameterException,
				TransactionException {
			// TODO Auto-generated method stub
			if (anITrxContext == null) {
	            throw new ForexFeedGroupException("The ITrxContext is null!!!");
	        }
	        if (obFileMapperID == null) {
	            throw new ForexFeedGroupException("The OBFileMapperID to be updated is null !!!");
	        }

	        IForexFeedGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
	        trxValue.setFromState("PENDING_INSERT");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
	        return operate(trxValue, param);
		} 
	 
	 /**
		 * @return Maker check if previous upload is pending.
		 */
	 public boolean isPrevFileUploadPending() throws ForexFeedGroupException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getForexFeedBusManager().isPrevFileUploadPending();
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new ForexFeedGroupException("ERROR-- Due to null ForexFeedEntry object cannot update.");
			}
		}
	 
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public int insertForexFeedEntry(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws ForexFeedGroupException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getForexFeedBusManager().insertForexFeedEntry(fileMapperMaster, userName, resultlList);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new ForexFeedGroupException("ERROR-- Due to null ForexFeedEntry object cannot update.");
			}
		}
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public HashMap insertForexFeedEntryAuto(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws ForexFeedGroupException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getForexFeedBusManager().insertForexFeedEntryAuto(fileMapperMaster, userName, resultlList);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new ForexFeedGroupException("ERROR-- Due to null ForexFeedEntry object cannot update.");
			}
		}
	 
	 /**
		 * @return create record with TransID.
		 */
	 
	 public IForexFeedGroupTrxValue getInsertFileByTrxID(String trxID)
		throws ForexFeedGroupException, TransactionException,
		CommandProcessingException {
		 	IForexFeedGroupTrxValue trxValue = new OBForexFeedGroupTrxValue();
		 	trxValue.setTransactionID(String.valueOf(trxID));
		 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
		 	OBCMSTrxParameter param = new OBCMSTrxParameter();
		 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		 return operate(trxValue, param);
}
	 
	 /**
		 * @return Pagination for uploaded files in ForexFeedEntry.
		 */
	 public List getAllStage(String searchBy, String login)throws ForexFeedGroupException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getForexFeedBusManager().getAllStageForexFeedEntry( searchBy, login);
		}else{
			throw new ForexFeedGroupException("ERROR- Search criteria is null.");
		}
	  }
	
	 /**
		 * @return Checker approval for uploaded files.
		 */
	 
	 public IForexFeedGroupTrxValue checkerApproveInsertForexFeedEntry(
	 		ITrxContext anITrxContext,
	 		IForexFeedGroupTrxValue anIForexFeedGroupTrxValue)
	 		throws ForexFeedGroupException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	        throw new ForexFeedGroupException("The ITrxContext is null!!!");
	    }
	    if (anIForexFeedGroupTrxValue == null) {
	        throw new ForexFeedGroupException
	                ("The IForexFeedGroupTrxValue to be updated is null!!!");
	    }
	    anIForexFeedGroupTrxValue = formulateTrxValueID(anITrxContext, anIForexFeedGroupTrxValue);
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
	    return operate(anIForexFeedGroupTrxValue, param);
	 }
	 
	 /**
		 * @return list of files uploaded in staging table of ForexFeedEntry.
		 */
	 public List getFileMasterList(String searchBy)throws ForexFeedGroupException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getForexFeedBusManager().getFileMasterList( searchBy);
		}else{
			throw new ForexFeedGroupException("ERROR- Search criteria is null.");
		}
	  }
	 
	 /**
		 * @return Maker insert upload files.
		 */
	 public List insertActualForexFeedEntry(String sysId) throws ForexFeedGroupException,TrxParameterException,TransactionException

	 {
	  if(sysId != null){
	 	 try {
	 		 return getForexFeedBusManager().insertActualForexFeedEntry(sysId);
	 	 } catch (Exception e) {		 		
	 		 e.printStackTrace();
	 		 throw new ForexFeedGroupException("ERROR- Transaction for the Id is invalid.");
	 	 }
	  }else{
	 	 throw new ForexFeedGroupException("ERROR- Id for retrival is null.");
	  }
	 }
	 
	 /**
		 * @return Checker create file master in ForexFeedEntry.
		 */
	 
	// public IForexFeedGroupTrxValue checkerCreateForexFeedEntry(ITrxContext anITrxContext, IForexFeedEntry anICCForexFeedEntry, String refStage) throws ForexFeedGroupException,TrxParameterException,
	 public IForexFeedGroupTrxValue checkerCreateForexFeedEntry(ITrxContext anITrxContext, IForexFeedGroup anICCForexFeedEntry, String refStage) throws ForexFeedGroupException,TrxParameterException,
	 
	 TransactionException {
	     if (anITrxContext == null) {
	         throw new ForexFeedGroupException("The ITrxContext is null!!!");
	     }
	     if (anICCForexFeedEntry == null) {
	         throw new ForexFeedGroupException("The ICCForexFeedEntry to be updated is null !!!");
	     }

	     IForexFeedGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCForexFeedEntry);
	     trxValue.setFromState("PENDING_CREATE");
	     trxValue.setReferenceID(String.valueOf(anICCForexFeedEntry.getForexFeedGroupID()));
	     trxValue.setStagingReferenceID(refStage);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
	     return operate(trxValue, param);
	 }
	 
	 /**
		 * @return Checker Reject for upload files ForexFeedEntry.
		 */

	 public IForexFeedGroupTrxValue checkerRejectInsertForexFeedEntry(
	 	ITrxContext anITrxContext,
	 	IForexFeedGroupTrxValue anIForexFeedGroupTrxValue)
	 	throws ForexFeedGroupException, TrxParameterException,
	 	TransactionException {
	 	if (anITrxContext == null) {
	 	  throw new ForexFeedGroupException("The ITrxContext is null!!!");
	 	}
	 	if (anIForexFeedGroupTrxValue == null) {
	 	  throw new ForexFeedGroupException
	 	          ("The IForexFeedGroupTrxValue to be updated is null!!!");
	 	}
	 		anIForexFeedGroupTrxValue = formulateTrxValueID(anITrxContext, anIForexFeedGroupTrxValue);
	 		OBCMSTrxParameter param = new OBCMSTrxParameter();
	 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	 	return operate(anIForexFeedGroupTrxValue, param);
	 }
	 
	 /**
		 * @return Maker Close rejected files ForexFeedEntry.
		 */

	 public IForexFeedGroupTrxValue makerInsertCloseRejectedForexFeedEntry(
	 		ITrxContext anITrxContext,
	 		IForexFeedGroupTrxValue anIForexFeedGroupTrxValue)
	 		throws ForexFeedGroupException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	         throw new ForexFeedGroupException("The ITrxContext is null!!!");
	     }
	     if (anIForexFeedGroupTrxValue == null) {
	         throw new ForexFeedGroupException("The IForexFeedGroupTrxValue to be updated is null!!!");
	     }
	     anIForexFeedGroupTrxValue = formulateTrxValue(anITrxContext, anIForexFeedGroupTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
	     return operate(anIForexFeedGroupTrxValue, param);
	 }
	 public void updateForexFeedEntryExchangeRate(List forexFeedEntryList) throws ForexFeedGroupException,TrxParameterException,TransactionException
	 {
		 getForexFeedBusManager().updateForexFeedEntryExchangeRate(forexFeedEntryList);
		 
	 }
	 
	 public void updateForexFeedEntryExchangeRateAuto(List forexFeedEntryList) throws ForexFeedGroupException,TrxParameterException,TransactionException
	 {
		 getForexFeedBusManager().updateForexFeedEntryExchangeRateAuto(forexFeedEntryList);
		 
	 }
	 
	 /*Return true if Currency Code not Exist*/
	 public boolean isCurrencyCodeExist(List currencyCodeList) throws ForexFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException
	 {	
		return  getForexFeedBusManager().isCurrencyCodeExist(currencyCodeList);
	 }
	//------------------------------------End File Upload-----------------------------------------------------
	 
	 public BigDecimal getExchangeRateWithINR (String currencyCode) throws ForexFeedGroupException
	 {	
		return  getForexFeedBusManager().getExchangeRateWithINR(currencyCode);
	 }
	 
	 public OBForex retriveCurrency (String currencyCode) throws ForexFeedGroupException
	 {	
		return  getForexFeedBusManager().retriveCurrency(currencyCode);
	 }

	/**
	 * @return the stagingForexFeedFileMapperIdBusManager
	 */
	public IForexFeedBusManager getStagingForexFeedFileMapperIdBusManager() {
		return stagingForexFeedFileMapperIdBusManager;
	}

	/**
	 * @param stagingForexFeedFileMapperIdBusManager the stagingForexFeedFileMapperIdBusManager to set
	 */
	public void setStagingForexFeedFileMapperIdBusManager(
			IForexFeedBusManager stagingForexFeedFileMapperIdBusManager) {
		this.stagingForexFeedFileMapperIdBusManager = stagingForexFeedFileMapperIdBusManager;
	}

	/**
	 * @return the forexFeedFileMapperIdBusManager
	 */
	public IForexFeedBusManager getForexFeedFileMapperIdBusManager() {
		return forexFeedFileMapperIdBusManager;
	}

	/**
	 * @param forexFeedFileMapperIdBusManager the forexFeedFileMapperIdBusManager to set
	 */
	public void setForexFeedFileMapperIdBusManager(
			IForexFeedBusManager forexFeedFileMapperIdBusManager) {
		this.forexFeedFileMapperIdBusManager = forexFeedFileMapperIdBusManager;
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws ForexFeedGroupException, TrxParameterException, TransactionException {
		getForexFeedBusManager().deleteTransaction(obFileMapperMaster);		
	}
	
	public IForexFeedGroupTrxValue makerInsertMapperForexFeedEntryAuto(
			ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
			throws ForexFeedGroupException, TrxParameterException,
			TransactionException {

		if (anITrxContext == null) {
            throw new ForexFeedGroupException("The ITrxContext is null!!!");
        }
        if (obFileMapperID == null) {
            throw new ForexFeedGroupException("The OBFileMapperID to be updated is null !!!");
        }

        IForexFeedGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
        trxValue.setFromState("PENDING_INSERT");
        trxValue.setLoginId(IFileUploadConstants.SYSTEM_UPLOAD);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
        return operate(trxValue, param);
	}
}
