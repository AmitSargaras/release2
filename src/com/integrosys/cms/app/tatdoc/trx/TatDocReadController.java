package com.integrosys.cms.app.tatdoc.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 6:04:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class TatDocReadController extends AbstractTrxController implements ITrxOperationFactory {

    private Map nameTrxOperationMap;

    // ****************************
    // Getter and Setter Methods
    // ****************************
    /**
     * @return &lt;name, ITrxOperation&gt; pair map to be injected, name and
     *         ITrxOperation name will be the same.
     */
    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }


    // ****************************
    // Original Methods
    // ****************************
    /**
	 * Default Constructor
	 */
	public TatDocReadController() {
	}

	/**
	 * Return instance name
	 * @return String - the instance name
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_TAT_DOC;
	}

	/**
	 * This operate method invokes the operation for a read operation.
	 *
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxResult - the trx result
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException ,
	 *         TrxControllerException, TransactionException on errors
	 */
	public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException,
			TrxControllerException, TransactionException {
		if (null == value) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		value = setInstanceName(value);
		DefaultLogger.debug(this, "Instance Name: " + value.getInstanceName());
		ITrxOperation op = getOperation(value, param);
		DefaultLogger.debug(this, "From state " + value.getFromState());
		CMSReadTrxManager mgr = new CMSReadTrxManager();

		ITrxResult result = null;
		try {
			result = mgr.operateTransaction(op, value);
			return result;
		}
		catch (TransactionException te) {
			throw te;
		}
		catch (Exception re) {
			throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
		}
	}

	/**
	 * Get the ITrxOperation
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException on
	 *         errors
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();
		if (action != null) {
			if (action.equals(ICMSConstant.ACTION_READ_TAT_DOC)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadTatDocOperation");
			}
			else if (action.equals(ICMSConstant.ACTION_READ_TAT_DOC_ID)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadTatDocTrxIDOperation");
			}

			throw new TrxParameterException("Unknow Action: " + action + ".");
		}
		throw new TrxParameterException("Action is null!");
	}
}
