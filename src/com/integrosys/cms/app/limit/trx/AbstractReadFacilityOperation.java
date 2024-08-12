package com.integrosys.cms.app.limit.trx;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.limit.bus.IFacilityBusManager;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * <p>
 * Abstract facility operation to be used by operation that suppose to read
 * transaction value only.
 * 
 * <p>
 * Sub class should choose what to use on the workflow manager by implement
 * {@link #doInTrxManager(ICMSTrxValue)}. This class will cater for bus manager
 * retrieval.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public abstract class AbstractReadFacilityOperation extends CMSTrxOperation implements ITrxReadOperation {

	private static final long serialVersionUID = 8971201763702777037L;

	/** logger for subclasses */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private IFacilityBusManager actualFacilityBusManager;

	private IFacilityBusManager stagingFacilityBusManager;

	public void setActualFacilityBusManager(IFacilityBusManager actualFacilityBusManager) {
		this.actualFacilityBusManager = actualFacilityBusManager;
	}

	public IFacilityBusManager getActualFacilityBusManager() {
		return actualFacilityBusManager;
	}

	public void setStagingFacilityBusManager(IFacilityBusManager stagingFacilityBusManager) {
		this.stagingFacilityBusManager = stagingFacilityBusManager;
	}

	public IFacilityBusManager getStagingFacilityBusManager() {
		return stagingFacilityBusManager;
	}

	/**
	 * Sub class to implement this method to do the actual process when
	 * interface with workflow manager, ie. trx manager.
	 * 
	 * @param trxValue facility trx value object, contain certain criteria to be
	 *        used by subclasses
	 * @return full trx value object will reference id, staging reference id,
	 *         others information retrieved.
	 * @throws TransactionException if there is any error occur when interface
	 *         with trx manager.
	 */
	protected abstract ICMSTrxValue doInTrxManager(ICMSTrxValue trxValue) throws TransactionException;

	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
		ICMSTrxValue trxValue = super.getCMSTrxValue(value);

		trxValue = doInTrxManager(trxValue);

		if (trxValue == null) {
			return null;
		}

		OBFacilityTrxValue facTrxValue = new OBFacilityTrxValue();
		AccessorUtil.copyValue(trxValue, facTrxValue);

		String referenceId = trxValue.getReferenceID();
		String stagingReferenceId = trxValue.getStagingReferenceID();

		if (StringUtils.isNotBlank(referenceId)) {
			IFacilityMaster facilityMaster = getActualFacilityBusManager().retrieveFacilityMasterByPrimaryKey(
					Long.parseLong(referenceId));
			facTrxValue.setFacilityMaster(facilityMaster);
		}

		if (StringUtils.isNotBlank(stagingReferenceId)) {
			IFacilityMaster facilityMaster = getStagingFacilityBusManager().retrieveFacilityMasterByPrimaryKey(
					Long.parseLong(stagingReferenceId));
			facTrxValue.setStagingFacilityMaster(facilityMaster);
		}

		return facTrxValue;
	}

}
