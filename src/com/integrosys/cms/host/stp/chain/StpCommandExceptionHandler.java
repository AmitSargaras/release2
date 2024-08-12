package com.integrosys.cms.host.stp.chain;

import com.integrosys.cms.host.stp.bus.*;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.StpCommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 23, 2008
 * Time: 2:40:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class StpCommandExceptionHandler implements IStpConstants, IStpCommandExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private IStpTransBusManager stpTransBusManager;

    public IStpTransBusManager getStpTransBusManager() {
        return stpTransBusManager;
    }

    public void setStpTransBusManager(IStpTransBusManager stpTransBusManager) {
        this.stpTransBusManager = stpTransBusManager;
    }

    public boolean postprocess(Map context, Exception e) {
        IStpMasterTrans obStpMasterTrans = (IStpMasterTrans) context.get(STP_TRX_VALUE);

        if (e == null) return false;

        if (e instanceof StpCommonException) {
            StpCommonException comExp = (StpCommonException) e;
            if(comExp.getErrorCode().equals(ERR_CODE_INVALID_PACK)){
                obStpMasterTrans.setStatus(MASTER_TRX_PENDING_MAKER);
            }else{
                obStpMasterTrans.setStatus(MASTER_TRX_PENDING_CHECKER);
            }

            for (Iterator iterator = obStpMasterTrans.getTrxEntriesSet().iterator(); iterator.hasNext();) {
                OBStpTrans o = (OBStpTrans) iterator.next();
                if (o.getTrxId().equals(comExp.getTrxId())) {
                    IStpTransError obStpTransError = new OBStpTransError();
                    Set errSet = o.getTrxErrorSet()!=null?o.getTrxErrorSet():new HashSet();
                    for (Iterator iterError = errSet.iterator(); iterError.hasNext();) {
                        IStpTransError existTransError = (IStpTransError) iterError.next();
                        if(existTransError.getTrxUID().equals(o.getTrxUID())){
                            //we reuse existing error record when trx UID same, used for resend case
                            obStpTransError = existTransError;
                            break;
                        }
                    }
                    obStpTransError.setTrxUID(o.getTrxUID());
                    obStpTransError.setErrorCode(comExp.getErrorCode());
                    obStpTransError.setErrorDesc(comExp.getErrorDesc());
                    errSet.add(obStpTransError);
                    o.setTrxErrorSet(errSet);
                    break;
                }
            }
        } else {
            // unhandled exception catch here
            obStpMasterTrans.setStatus(MASTER_TRX_RESET);
        }
        
        context.put(STP_TRX_VALUE, obStpMasterTrans);
        getStpTransBusManager().updateMasterTrans(obStpMasterTrans);
        logger.error("Exception caught in StpCommandExceptionHandler", e);
        return true;
    }
}
