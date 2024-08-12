package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

import java.util.Map;

/**
 * @author abhijit.rudrakshawar
 * Read System Bank Controller
 */
public class SystemBankReadController extends AbstractTrxController implements ITrxOperationFactory {

    private Map nameTrxOperationMap;

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

    /**
     * Default Constructor
     */
    public SystemBankReadController() {
    }

    /**
     * Return instance name
     *
     * @return String - the instance name
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_SYSTEM_BANK;
    }

    /**
     * This operate method invokes the operation for a read operation.
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxResult - the trx result
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException,
     *          TrxControllerException, TransactionException on errors
     */
    public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException, TrxControllerException, TransactionException {
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

         
        try {
        	ITrxResult result = mgr.operateTransaction(op, value);
            return result;
        }
        catch (Exception re) {
            throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
        }
    }

    /**
     * Get the ITrxOperation
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws TrxParameterException on errors
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        if (null == param) {
            throw new TrxParameterException("ITrxParameter is null!");
        }
        String action = param.getAction();
        if (action != null) {
            if (action.equals(ICMSConstant.ACTION_READ_SYSTEM_BANK)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadSystemBankOperation");
            } else if (action.equals(ICMSConstant.ACTION_READ_SYSTEM_BANK_ID)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadSystemBankIDOperation");
            }else{

            throw new TrxParameterException("Unknow Action: " + action + ".");
            }
        }else{
        throw new TrxParameterException("Action is null!");
        }
	}

}
