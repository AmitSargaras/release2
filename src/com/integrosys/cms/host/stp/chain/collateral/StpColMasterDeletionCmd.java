package com.integrosys.cms.host.stp.chain.collateral;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.integrosys.cms.host.stp.bus.*;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.collateral.bus.*;
import com.integrosys.component.user.app.bus.OBCommonUser;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 5, 2008
 * Time: 12:28:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class StpColMasterDeletionCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        OBCollateralTrxValue obCollateralTrxValue = (OBCollateralTrxValue) ctx.get(COL_TRX_VALUE);
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        HashMap addHdrField = new HashMap();
        ctx.put(FIELD_VAL_MAP, addHdrField);
        ctx.put(REF_NUM, Long.toString(obCollateralTrxValue.getCollateral().getCollateralID()));
        ctx.put(STP_TRANS_MAP, stpTransMap);
        ctx.put(IS_DELETE, new Boolean(true));
        OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

        if(obStpTrans != null){
            //put the OB into an array of object
            ArrayList aObject = new ArrayList();
            OBCollateral obCollateral = (OBCollateral) obCollateralTrxValue.getCollateral();
            aObject.add(obCollateral);
            aObject.add(addHdrField);

            //map to field OB
            List stpList = prepareRequest(ctx, aObject.toArray(),obStpTrans.getTrxType(), ISTPMapper.COLLATERAL_PATH);

            // construct msg, send msg and decipher msg
            stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());

            //put required OB in context to be used in subsequent command
            ctx.put(STP_TRX_VALUE, obStpMasterTrans);
            ctx.put(COL_TRX_VALUE, obCollateralTrxValue);

            //map object to biz OB and update STP transaction
            boolean successUpd = processResponse(ctx,obStpTrans,stpList);
            if(!successUpd){
                return true;
            }
        }
        return false;
    }
}
