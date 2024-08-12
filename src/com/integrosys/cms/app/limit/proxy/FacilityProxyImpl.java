package com.integrosys.cms.app.limit.proxy;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.OBTrxParameter;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.limit.bus.FacilityException;
import com.integrosys.cms.app.limit.bus.IFacilityBusManager;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.trx.FacilityReadController;
import com.integrosys.cms.app.limit.trx.FacilityTrxController;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author Chong Jun Yong
 * @author Andy Wong
 * 
 */
public class FacilityProxyImpl implements IFacilityProxy {

	private static final Logger logger = LoggerFactory.getLogger(FacilityProxyImpl.class);

	private IFacilityBusManager actualFacilityBusManager;

	private IFacilityBusManager stagingFacilityBusManager;

	private ITrxController facilityReadController;

	private ITrxController facilityTrxController;

	/**
	 * @return the actualBusManager
	 */
	public IFacilityBusManager getActualFacilityBusManager() {
		return actualFacilityBusManager;
	}

	public IFacilityBusManager getStagingFacilityBusManager() {
		return stagingFacilityBusManager;
	}

	/**
	 * @return the facilityReadController
	 */
	public ITrxController getFacilityReadController() {
		return facilityReadController;
	}

	/**
	 * @return the facilityTrxController
	 */
	public ITrxController getFacilityTrxController() {
		return facilityTrxController;
	}

	/**
	 * @param actualFacilityBusManager the actualBusManager to set
	 */
	public void setActualFacilityBusManager(IFacilityBusManager actualFacilityBusManager) {
		this.actualFacilityBusManager = actualFacilityBusManager;
	}

	public void setStagingFacilityBusManager(IFacilityBusManager stagingFacilityBusManager) {
		this.stagingFacilityBusManager = stagingFacilityBusManager;
	}

	/**
	 * @param facilityReadController the facilityReadController to set
	 */
	public void setFacilityReadController(ITrxController facilityReadController) {
		this.facilityReadController = facilityReadController;
	}

	/**
	 * @param facilityTrxController the facilityTrxController to set
	 */
	public void setFacilityTrxController(ITrxController facilityTrxController) {
		this.facilityTrxController = facilityTrxController;
	}

	public IFacilityTrxValue checkerApproveFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException {
		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityTrxController.ACTION_CHECKER_APPROVE);

		trxValue.setTrxContext(context);

		ICMSTrxResult result = doInTrxController(getFacilityTrxController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public IFacilityTrxValue checkerRejectFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException {
		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityTrxController.ACTION_CHECKER_REJECT);

		trxValue.setTrxContext(context);

		ICMSTrxResult result = doInTrxController(getFacilityTrxController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public IFacilityTrxValue makerCloseFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException {
		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityTrxController.ACTION_MAKER_CLOSE);

		trxValue.setTrxContext(context);

		ICMSTrxResult result = doInTrxController(getFacilityTrxController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public IFacilityTrxValue makerCreateFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException {
		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityTrxController.ACTION_MAKER_CREATE);

		trxValue.setTrxContext(context);

		ICMSTrxResult result = doInTrxController(getFacilityTrxController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public IFacilityTrxValue makerSaveFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException {
		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityTrxController.ACTION_MAKER_SAVE);

		trxValue.setTrxContext(context);

		ICMSTrxResult result = doInTrxController(getFacilityTrxController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public IFacilityTrxValue makerUpdateFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException {
		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityTrxController.ACTION_MAKER_UPDATE);

		trxValue.setTrxContext(context);

		ICMSTrxResult result = doInTrxController(getFacilityTrxController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public IFacilityTrxValue retrieveFacilityMasterTransactionByCmsLimitId(long cmsLimitId) throws FacilityException {
		IFacilityMaster facilityMaster = getActualFacilityBusManager().retrieveFacilityMasterByCmsLimitId(cmsLimitId);
		if (facilityMaster == null) {
			logger.warn("there is no facility master created for cms limit id [" + cmsLimitId
					+ "] querying from staging copy.");
			facilityMaster = getStagingFacilityBusManager().retrieveFacilityMasterByCmsLimitId(cmsLimitId);
			if (facilityMaster != null) {
				return retrieveFacilityMasterTransactionByStagingId(facilityMaster.getId());
			}
			else {
				return null;
			}
		}

		return retrieveFacilityMasterTransactionById(facilityMaster.getId());
	}

	public IFacilityTrxValue retrieveFacilityMasterTransactionById(long cmsFacilityMasterId) throws FacilityException {
		OBFacilityTrxValue trxValue = new OBFacilityTrxValue();
		trxValue.setReferenceID(String.valueOf(cmsFacilityMasterId));

		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityReadController.ACTION_READ_TRX_BY_FAC_MASTER_ID);

		ICMSTrxResult result = doInTrxController(getFacilityReadController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public IFacilityTrxValue retrieveFacilityMasterTransactionByTransactionId(String transactionId)
			throws FacilityException {
		OBFacilityTrxValue trxValue = new OBFacilityTrxValue();
		trxValue.setTransactionID(transactionId);

		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityReadController.ACTION_READ_TRX);

		ICMSTrxResult result = doInTrxController(getFacilityReadController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public List retrieveListOfFacilityMasterByLimitProfileId(long cmsLimitProfileId) throws FacilityException {
		return getActualFacilityBusManager().retrieveListOfFacilityMasterByCmsLimitProfileId(cmsLimitProfileId);
	}

	public IFacilityTrxValue systemApproveFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException {
		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityTrxController.ACTION_SYSTEM_APPROVE);

		trxValue.setTrxContext(context);

		ICMSTrxResult result = doInTrxController(getFacilityTrxController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public IFacilityTrxValue systemUpdateFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException {
		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityTrxController.ACTION_SYSTEM_UPDATE);

		trxValue.setTrxContext(context);

		ICMSTrxResult result = doInTrxController(getFacilityTrxController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public IFacilityTrxValue systemRejectFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException {
		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityTrxController.ACTION_SYSTEM_REJECT);

		trxValue.setTrxContext(context);

		ICMSTrxResult result = doInTrxController(getFacilityTrxController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	/**
	 * Operate using controller supplied internally
	 * 
	 * @param trxController trx controller to be used to operate the param and
	 *        trx value supplied
	 * @param param trx parameter normally contained 'action' only
	 * @param trxValue transaction value to be operated
	 */
	protected ICMSTrxResult doInTrxController(ITrxController trxController, ITrxParameter param,
			IFacilityTrxValue trxValue) {
		Validate.notNull(trxController, "'trxController' to do operation must not be null");
		Validate.notNull(param, "'param' to undergo operation must not be null");
		Validate.notNull(trxValue, "'trxValue' to undergo operation must not be null");

		try {
			return (ICMSTrxResult) trxController.operate(trxValue, param);
		}
		catch (TransactionException e) {
			throw new FacilityException("failed to operate facility workflow", e);
		}
	}

	public IFacilityTrxValue retrieveFacilityMasterTransactionByStagingId(long cmsFacilityMasterId)
			throws FacilityException {
		OBFacilityTrxValue trxValue = new OBFacilityTrxValue();
		trxValue.setStagingReferenceID(String.valueOf(cmsFacilityMasterId));

		OBTrxParameter param = new OBTrxParameter();
		param.setAction(FacilityReadController.ACTION_READ_TRX_BY_FAC_MASTER_ID);

		ICMSTrxResult result = doInTrxController(getFacilityReadController(), param, trxValue);
		return (IFacilityTrxValue) result.getTrxValue();
	}

	public String getProductGroupByProductCode(String productType) {
		return getActualFacilityBusManager().getProductGroupByProductCode(productType);
	}

	public String getDealerProductFlagByProductCode(String productType) {
		return getActualFacilityBusManager().getDealerProductFlagByProductCode(productType);
	}

	public String getRevolvingFlagByFacilityCode(String facilityCode) {
		return getActualFacilityBusManager().getRevolvingFlagByFacilityCode(facilityCode);
	}

	public String getConceptCodeByProductCode(String productType) {
		return getActualFacilityBusManager().getConceptCodeByProductCode(productType);
	}

	public String getAccountTypeByFacilityCode(String facilityCode) {
		return getActualFacilityBusManager().getAccountTypeByFacilityCode(facilityCode);
	}

}
