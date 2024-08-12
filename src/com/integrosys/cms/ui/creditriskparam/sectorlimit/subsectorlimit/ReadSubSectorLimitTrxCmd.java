package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.OBSubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.OBSectorLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitCommand;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ReadSubSectorLimitTrxCmd extends SectorLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
            {"sectorLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue", SERVICE_SCOPE},
			{"sectorLimitId", "java.lang.String", REQUEST_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
            {"remarks","java.lang.String",REQUEST_SCOPE },
            {"indexId", "java.lang.String", REQUEST_SCOPE},

	    });
	}

	public String[][] getResultDescriptor() {
		return (new String[][]{
           {"actualSubSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
           {"stagingSubSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
           {"event", "java.lang.String", REQUEST_SCOPE},
           {"remarks","java.lang.String",REQUEST_SCOPE },
           {"fromEvent", "java.lang.String", REQUEST_SCOPE},
	    });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String event = (String)map.get("event");
        String indexId = (String)map.get("indexId");
        DefaultLogger.debug(this, "sub event= "+ event );
        DefaultLogger.debug(this, "prepare update index id= "+ indexId );

        ISectorLimitParameterTrxValue trxValue = null ;

        trxValue = (ISectorLimitParameterTrxValue)map.get("sectorLimitTrxObj");

        ISubSectorLimitParameter actualObj = null, stagingObj = null;

        if(trxValue.getActualMainSectorLimitParameter() != null
                && trxValue.getActualMainSectorLimitParameter().getSubSectorList() != null
                && trxValue.getActualMainSectorLimitParameter().getSubSectorList().size() > 0){
            for(Iterator a = trxValue.getActualMainSectorLimitParameter().getSubSectorList().iterator(); a.hasNext();){
                ISubSectorLimitParameter sub = (ISubSectorLimitParameter) a.next();
                if(sub.getSectorCode().equals(indexId)){
                    actualObj = sub;
                    break;
                }
            }
            //   actualObj = (ISubSectorLimitParameter)((List) trxValue.getActualMainSectorLimitParameter().getSubSectorList()).get(Integer.parseInt(indexId));

        }

        if(trxValue.getStagingMainSectorLimitParameter() != null
                && trxValue.getStagingMainSectorLimitParameter().getSubSectorList() != null
                && trxValue.getStagingMainSectorLimitParameter().getSubSectorList().size() > 0){
            for(Iterator s = trxValue.getStagingMainSectorLimitParameter().getSubSectorList().iterator(); s.hasNext();){
                ISubSectorLimitParameter sub = (ISubSectorLimitParameter) s.next();
                if(sub.getSectorCode().equals(indexId)){
                    stagingObj = sub;
                    break;
                }
            }
         //   stagingObj = (ISubSectorLimitParameter)((List) trxValue.getStagingMainSectorLimitParameter().getSubSectorList()).get(Integer.parseInt(indexId));
        }
      /*  if (stagingObj == null){
            stagingObj = getSectorLimitProxy().getDeletedSectorLimitSubSectorInStagingByIdAndSectorCode(trxValue.getStagingMainSectorLimitParameter().getId().longValue(), indexId);
        }*/
        
	    DefaultLogger.debug(this, "********** actualObj IS: " + actualObj);

	    DefaultLogger.debug(this, "********** stagingObj IS: " + stagingObj);

        String remarks = (String)map.get("remarks");

        resultMap.put("actualSubSectorLimitObj", actualObj);
        resultMap.put("stagingSubSectorLimitObj", stagingObj);

       // resultMap.put("indexId", indexId);
        resultMap.put("remarks", remarks);

        returnMap.put("fromEvent", event);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}