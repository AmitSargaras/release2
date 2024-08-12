package com.integrosys.cms.host.stp.chain.collateral;

import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBPledgor;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.feed.bus.forex.IForexDao;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 5, 2008
 * Time: 12:05:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class StpColChargorCreationCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        HashMap addHdrField = new HashMap();
        OBCollateralTrxValue obCollateralTrxValue = (OBCollateralTrxValue) ctx.get(COL_TRX_VALUE);
        OBCollateral obCollateral = (OBCollateral) obCollateralTrxValue.getCollateral();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);

        ctx.put(FIELD_VAL_MAP, addHdrField);
        ctx.put(REF_NUM, Long.toString(obCollateral.getCollateralID()));
        ctx.put(STP_TRANS_MAP, StpCommandUtil.getTrxMap(obStpMasterTrans));
        OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

        if (obStpTrans != null) {
            OBPledgor[] obPledgor = (OBPledgor[]) obCollateral.getPledgors();
            int plgSize = 0;
            if (obPledgor.length > 5) {
                plgSize = 5;
            } else {
                plgSize = obPledgor.length;
            }
            ArrayList fivePlg = new ArrayList();
            for (int i = 0; i < plgSize; i++) {
                fivePlg.add(obPledgor[i]);
            }

            //put the OB into an array of object
            ArrayList aObject = new ArrayList();
            aObject.add(fivePlg);
            aObject.add(obCollateral);
            aObject.add(addHdrField);

            //map to field OB
            List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.COLLATERAL_PATH);

            // construct msg, send msg and decipher msg
            stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());

            //put required OB in context to be used in subsequent command
            ctx.put(STP_TRX_VALUE, obStpMasterTrans);
            ctx.put(COL_TRX_VALUE, obCollateralTrxValue);

            //map object to biz OB and update STP transaction
            boolean successUpd = processResponse(ctx, obStpTrans, stpList);
            if (!successUpd) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
