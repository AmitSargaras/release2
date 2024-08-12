package com.integrosys.cms.app.segmentWiseEmail.proxy;

import java.sql.SQLException;
import java.util.List;
import org.apache.commons.lang.Validate;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.productMaster.bus.OBProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.productMaster.trx.IProductMasterTrxValue;
import com.integrosys.cms.app.productMaster.trx.OBProductMasterTrxValue;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmailBusManager;
import com.integrosys.cms.app.segmentWiseEmail.bus.OBSegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.SegmentWiseEmailException;
import com.integrosys.cms.app.segmentWiseEmail.trx.ISegmentWiseEmailTrxValue;
import com.integrosys.cms.app.segmentWiseEmail.trx.OBSegmentWiseEmailTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class SegmentWiseEmailProxyManagerImpl implements ISegmentWiseEmailProxyManager {

	private ISegmentWiseEmailBusManager segmentWiseEmailBusManager;
	private ISegmentWiseEmailBusManager stagingSegmentWiseEmailBusManager;
	private ITrxControllerFactory trxControllerFactory;

	public ISegmentWiseEmailBusManager getSegmentWiseEmailBusManager() {
		return segmentWiseEmailBusManager;
	}

	public void setSegmentWiseEmailBusManager(ISegmentWiseEmailBusManager segmentWiseEmailBusManager) {
		this.segmentWiseEmailBusManager = segmentWiseEmailBusManager;
	}

	public ISegmentWiseEmailBusManager getStagingSegmentWiseEmailBusManager() {
		return stagingSegmentWiseEmailBusManager;
	}

	public void setStagingSegmentWiseEmailBusManager(ISegmentWiseEmailBusManager stagingSegmentWiseEmailBusManager) {
		this.stagingSegmentWiseEmailBusManager = stagingSegmentWiseEmailBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	/**
	 * @return List of all SegmentWiseEmail
	 */
	@Override
	public SearchResult getAllActualSegmentWiseEmail()
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		try {
			return getSegmentWiseEmailBusManager().getAllSegmentWiseEmail();
		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR- Cannot retrive list from database.");

		}
	}

	// getAllSegment
	public List getAllSegment() throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		try {
			return getSegmentWiseEmailBusManager().getAllSegment();
		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR- Cannot retrive list from database.");

		}
	}

	@Override
	public SearchResult getSegmentWiseEmail(String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		try {
			return getSegmentWiseEmailBusManager().getSegmentWiseEmail(segment);
		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR- Cannot retrive list from database.");

		}
	}

	/**
	 * @return Maker Create SegmentWiseEmail
	 */
	public ISegmentWiseEmailTrxValue makerCreateSegmentWiseEmail(ITrxContext anITrxContext,
			ISegmentWiseEmail anICCSegmentWiseEmail)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new SegmentWiseEmailException("The ITrxContext is null!!!");
		}
		if (anICCSegmentWiseEmail == null) {
			throw new SegmentWiseEmailException("The ICCSegmentWiseEmail to be updated is null !!!");
		}

		ISegmentWiseEmailTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCSegmentWiseEmail);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_SEGMENT_WISE_EMAIL);
		return operate(trxValue, param);
	}

	private ISegmentWiseEmailTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ISegmentWiseEmail anICCSegmentWiseEmail) {
		ISegmentWiseEmailTrxValue ccSegmentWiseEmailTrxValue = null;
		if (anICMSTrxValue != null) {
			ccSegmentWiseEmailTrxValue = new OBSegmentWiseEmailTrxValue(anICMSTrxValue);
		} else {
			ccSegmentWiseEmailTrxValue = new OBSegmentWiseEmailTrxValue();
		}
		ccSegmentWiseEmailTrxValue = formulateTrxValue(anITrxContext,
				(ISegmentWiseEmailTrxValue) ccSegmentWiseEmailTrxValue);
		ccSegmentWiseEmailTrxValue.setStagingSegmentWiseEmail(anICCSegmentWiseEmail);
		return ccSegmentWiseEmailTrxValue;
	}

	private ISegmentWiseEmailTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ISegmentWiseEmailTrxValue anISegmentWiseEmailTrxValue) {
		anISegmentWiseEmailTrxValue.setTrxContext(anITrxContext);
		anISegmentWiseEmailTrxValue.setTransactionType(ICMSConstant.INSTANCE_SEGMENT_WISE_EMAIL);
		return anISegmentWiseEmailTrxValue;
	}

	private ISegmentWiseEmailTrxValue operate(ISegmentWiseEmailTrxValue anISegmentWiseEmailTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anISegmentWiseEmailTrxValue, anOBCMSTrxParameter);
		return (ISegmentWiseEmailTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (SegmentWiseEmailException ex) {
			throw new SegmentWiseEmailException("ERROR--Cannot Get the SegmentWiseEmail Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new SegmentWiseEmailException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new SegmentWiseEmailException("ERROR--Cannot Get the SegmentWiseEmail Controller.");
		}
	}

	/**
	 * @return ProductMaster TRX value according to trxId .
	 */

	public ISegmentWiseEmailTrxValue getSegmentWiseEmailByTrxID(String aTrxID)
			throws SegmentWiseEmailException, TransactionException, CommandProcessingException {
		ISegmentWiseEmailTrxValue trxValue = new OBSegmentWiseEmailTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_SEGMENT_WISE_EMAIL);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_SEGMENT_WISE_EMAIL_ID);
		return operate(trxValue, param);
	}

	public void insertDataToEmailTable(String refId, List emails, String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException, SQLException {
		try {
			getSegmentWiseEmailBusManager().insertDataToEmailTable(refId, emails, segment);
		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR- Cannot retrive list from database.");

		}
	}

	public SearchResult getStageEmail(long id)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		try {
			return getSegmentWiseEmailBusManager().getStageEmail(id);
		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR- Cannot retrive list from database.");

		}
	}

	/**
	 * @return Checker Approve SegmentWiseEmail according to criteria .
	 */

	public ISegmentWiseEmailTrxValue checkerApproveSegmentWiseEmail(ITrxContext anITrxContext,
			ISegmentWiseEmailTrxValue anISegmentWiseEmailTrxValue)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new SegmentWiseEmailException("The ITrxContext is null!!!");
		}
		if (anISegmentWiseEmailTrxValue == null) {
			throw new SegmentWiseEmailException("The ISegmentWiseEmailTrxValue to be updated is null!!!");
		}
		anISegmentWiseEmailTrxValue = formulateTrxValue(anITrxContext, anISegmentWiseEmailTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_SEGMENT_WISE_EMAIL);
		return operate(anISegmentWiseEmailTrxValue, param);
	}

	/**
	 * @return Checker Reject SegmentWiseEmail according to criteria .
	 */

	public ISegmentWiseEmailTrxValue checkerRejectSegmentWiseEmail(ITrxContext anITrxContext,
			ISegmentWiseEmailTrxValue anICCSegmentWiseEmailTrxValue)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new SegmentWiseEmailException("The ITrxContext is null!!!");
		}
		if (anICCSegmentWiseEmailTrxValue == null) {
			throw new SegmentWiseEmailException("The ISegmentWiseEmailTrxValue to be updated is null!!!");
		}
		anICCSegmentWiseEmailTrxValue = formulateTrxValue(anITrxContext, anICCSegmentWiseEmailTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_SEGMENT_WISE_EMAIL);
		return operate(anICCSegmentWiseEmailTrxValue, param);
	}

	public void insertDataToActualEmailTable(String refsId, List<OBSegmentWiseEmail> objList)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException, SQLException {
		try {
			getSegmentWiseEmailBusManager().insertDataToActualEmailTable(refsId, objList);
		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR- Cannot retrive list from database.");

		}
	}

	/**
	 * @return Maker Close FCCBranch.
	 */

	public ISegmentWiseEmailTrxValue makerCloseRejectedSegmentWiseEmail(ITrxContext anITrxContext,
			ISegmentWiseEmailTrxValue anICCSegmentWiseEmailTrxValue)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new SegmentWiseEmailException("The ITrxContext is null!!!");
		}
		if (anICCSegmentWiseEmailTrxValue == null) {
			throw new SegmentWiseEmailException("The ICCSegmentWiseEmailTrxValue to be updated is null!!!");
		}
		anICCSegmentWiseEmailTrxValue = formulateTrxValue(anITrxContext, anICCSegmentWiseEmailTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_SEGMENT_WISE_EMAIL);
		return operate(anICCSegmentWiseEmailTrxValue, param);
	}

	public void deleteStageEmailIDs(String refId, List emails, String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException, SQLException {
		try {
			getSegmentWiseEmailBusManager().deleteStageEmailIDs(refId, emails, segment);
		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR- Cannot retrive list from database.");

		}
	}

	public void deleteActualEmailIDs(String refsId, List<OBSegmentWiseEmail> objList)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException, SQLException {
		try {
			getSegmentWiseEmailBusManager().deleteActualEmailIDs(refsId, objList);
		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR- Cannot retrive list from database.");

		}
	}
	
	public long getSegmentId(String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		try {
			return getSegmentWiseEmailBusManager().getSegmentId(segment);
		} catch (Exception e) {
			throw new SegmentWiseEmailException("ERROR- Cannot retrive list from database.");
		}
	}
	
	 /**
	 * @return  Product Master TRX value  .
	 */
	public ISegmentWiseEmailTrxValue getSegmentWiseEmailTrxValue(long segmentId) throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (segmentId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
          throw new SegmentWiseEmailException("Invalid segment Id");
      }
	  ISegmentWiseEmailTrxValue trxValue = new OBSegmentWiseEmailTrxValue();
      trxValue.setReferenceID(String.valueOf(segmentId));
      trxValue.setTransactionType(ICMSConstant.INSTANCE_SEGMENT_WISE_EMAIL);
      OBCMSTrxParameter param = new OBCMSTrxParameter();
      param.setAction(ICMSConstant.ACTION_READ_SEGMENT_WISE_EMAIL);
      return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update ProductMaster
	 */

	public ISegmentWiseEmailTrxValue makerUpdateSegmentWiseEmail(OBTrxContext anITrxContext,
			ISegmentWiseEmailTrxValue anICCSegmentWiseEmailTrxValue, OBSegmentWiseEmail anICCSegmentWiseEmail)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new SegmentWiseEmailException("The ITrxContext is null!!!");
		}
		if (anICCSegmentWiseEmail == null) {
			throw new SegmentWiseEmailException("The ICCSegmentWiseEmail to be updated is null !!!");
		}
		ISegmentWiseEmailTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSegmentWiseEmailTrxValue,anICCSegmentWiseEmail);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SEGMENT_WISE_EMAIL);
		return operate(trxValue, param);
	}
	
	public ISegmentWiseEmailTrxValue makerUpdateRejectedSegmentWiseEmail(OBTrxContext anITrxContext,
			ISegmentWiseEmailTrxValue anICCSegmentWiseEmailTrxValue, OBSegmentWiseEmail anICCSegmentWiseEmail)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new SegmentWiseEmailException("The ITrxContext is null!!!");
		}
		if (anICCSegmentWiseEmail == null) {
			throw new SegmentWiseEmailException("The ICCSegmentWiseEmail to be updated is null !!!");
		}
		ISegmentWiseEmailTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSegmentWiseEmailTrxValue,anICCSegmentWiseEmail);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_SEGMENT_WISE_EMAIL);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Create SegmentWiseEmail
	 */
	public ISegmentWiseEmailTrxValue makerCreateRejectedSegmentWiseEmail(ITrxContext anITrxContext,
			ISegmentWiseEmail anICCSegmentWiseEmail)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new SegmentWiseEmailException("The ITrxContext is null!!!");
		}
		if (anICCSegmentWiseEmail == null) {
			throw new SegmentWiseEmailException("The ICCSegmentWiseEmail to be updated is null !!!");
		}

		ISegmentWiseEmailTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCSegmentWiseEmail);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_SEGMENT_WISE_EMAIL);
		return operate(trxValue, param);
	}
	
	public ISegmentWiseEmailTrxValue makerDeleteSegmentWiseEmail(OBTrxContext anITrxContext,
			ISegmentWiseEmailTrxValue anICCSegmentWiseEmailTrxValue, OBSegmentWiseEmail anICCSegmentWiseEmail)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new SegmentWiseEmailException("The ITrxContext is null!!!");
		}
		if (anICCSegmentWiseEmail == null) {
			throw new SegmentWiseEmailException("The ICCSegmentWiseEmail to be updated is null !!!");
		}
		ISegmentWiseEmailTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSegmentWiseEmailTrxValue,anICCSegmentWiseEmail);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_SEGMENT_WISE_EMAIL);
		return operate(trxValue, param);
	}
	
	public ISegmentWiseEmailTrxValue makerDeleteRejectedSegmentWiseEmail(OBTrxContext anITrxContext,
			ISegmentWiseEmailTrxValue anICCSegmentWiseEmailTrxValue, OBSegmentWiseEmail anICCSegmentWiseEmail)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new SegmentWiseEmailException("The ITrxContext is null!!!");
		}
		if (anICCSegmentWiseEmail == null) {
			throw new SegmentWiseEmailException("The ICCSegmentWiseEmail to be updated is null !!!");
		}
		ISegmentWiseEmailTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSegmentWiseEmailTrxValue,anICCSegmentWiseEmail);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_SEGMENT_WISE_EMAIL);
		return operate(trxValue, param);
	}

}
