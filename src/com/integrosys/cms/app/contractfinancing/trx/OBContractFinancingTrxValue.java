package com.integrosys.cms.app.contractfinancing.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public class OBContractFinancingTrxValue extends OBCMSTrxValue implements IContractFinancingTrxValue {

	private IContractFinancing contractFinancing = null;

	private IContractFinancing stageContractFinancing = null;

	/**
	 * Default Constructor
	 */
	public OBContractFinancingTrxValue() {
	}

	/**
	 * Construct the object based on the contract finance object
	 * @param obj - IContractFinancing
	 */
	public OBContractFinancingTrxValue(IContractFinancing obj) {
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBContractFinancingTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public IContractFinancing getContractFinancing() {
		return contractFinancing;
	}

	public void setContractFinancing(IContractFinancing contractFinancing) {
		this.contractFinancing = contractFinancing;
	}

	public IContractFinancing getStagingContractFinancing() {
		return stageContractFinancing;
	}

	public void setStagingContractFinancing(IContractFinancing stageContractFinancing) {
		this.stageContractFinancing = stageContractFinancing;
	}

}
