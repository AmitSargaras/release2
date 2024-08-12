package com.integrosys.cms.host.stp.chain.facility;

import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.bus.IFacilityInsurance;
import com.integrosys.cms.app.limit.bus.OBFacilityInsurance;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.component.user.app.bus.ICommonUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 4, 2008
 * Time: 3:18:23 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Andy Wong
 * @since 24 Oct 2008
 */
public class StpFacInsCreationCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        HashMap fieldValMap = new HashMap();
        OBFacilityTrxValue obFacilityTrxValue = (OBFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
        OBFacilityMaster obFacilityMaster = (OBFacilityMaster) obFacilityTrxValue.getFacilityMaster();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        OBLimit obLimit = (OBLimit) getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());

        //Andy Wong, 23 Jan 2009: check against product group FS before firing
        if (StringUtils.equals(getActualFacilityBusManager().getProductGroupByProductCode(obLimit.getProductDesc()), "FS")
                && obFacilityMaster.getFacilityInsuranceSet().size() > 0) {
            ctx.put(FIELD_VAL_MAP, fieldValMap);
            ctx.put(REF_NUM, obFacilityTrxValue.getReferenceID());
            ctx.put(STP_TRANS_MAP, StpCommandUtil.getTrxMap(obStpMasterTrans));
            OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

            ArrayList hFacInsSet = new ArrayList();
            Set setFacilityInsurance = obFacilityMaster.getFacilityInsuranceSet();
            IFacilityInsurance[] facilityInsurances = new OBFacilityInsurance[setFacilityInsurance.size()];
            Iterator iter = setFacilityInsurance.iterator();
            while (iter.hasNext()) {
                IFacilityInsurance temp = (IFacilityInsurance) iter.next();
                facilityInsurances[temp.getOrder()] = temp;
            }
            for (int i = 0; i < setFacilityInsurance.size(); i++) {
                hFacInsSet.add(facilityInsurances[i]);
            }
            //put the OB into an array of object
            ArrayList aObject = new ArrayList();
            aObject.add(obLimit);
            aObject.add(hFacInsSet);
            aObject.add(fieldValMap);

            //map to field OB
            List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.FACILITY_PATH);

            // construct msg, send msg and decipher msg
            stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());

            //put required OB in context to be used in subsequent command
            ctx.put(STP_TRX_VALUE, obStpMasterTrans);
            ctx.put(FAC_TRX_VALUE, obFacilityTrxValue);

            //map object to biz OB and update STP transaction
            boolean successUpd = processResponse(ctx, obStpTrans, stpList);
            if (!successUpd) {
                return true;
            }
        }
        return false;
    }
}
