package com.integrosys.cms.app.systemBankBranch.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * @author abhijit.rudrakshawar
 * Read System Bank Controller
 */
public class ReadInsertFileTrxController extends AbstractTrxController implements ITrxOperationFactory {

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
    public ReadInsertFileTrxController() {
    }

    /**
     * Return instance name
     *
     * @return String - the instance name
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_INSERT_SYS_BRANCH;
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
        if ( value==null) {
            throw new TrxParameterException("ITrxValue is null!");
        }
        if (param==null) {
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
            throw new TrxControllerException("Caught Unknown Exception: " + re.getMessage());
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
            if (action.equals(ICMSConstant.ACTION_READ_FILE_INSERT)) {
                return (ITrxOperation) getNameTrxOperationMap().get("FileInsertReadOperation");
            }else{

            throw new TrxParameterException("Unknow Action: " + action + ".");
            }
        }else{
        throw new TrxParameterException("Action is null!");
        }
	}

}
