package com.integrosys.cms.host.stp.chain;

import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.proxy.IFacilityProxy;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.IStpTransBusManager;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.StpCommonException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 3, 2008
 * Time: 6:22:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class StpCatalogLoader implements IStpCatalogLoader, IStpConstants {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private IFacilityProxy facilityProxy;
    private ICollateralProxy collateralProxy;
    private IStpTransBusManager stpTransBusManager;
    private IStpCommandExceptionHandler stpCommandExceptionHandler;
    private Map chainMap;

    public StpCatalogLoader() {
    }

    public IFacilityProxy getFacilityProxy() {
        return facilityProxy;
    }

    public void setFacilityProxy(IFacilityProxy facilityProxy) {
        this.facilityProxy = facilityProxy;
    }

    public ICollateralProxy getCollateralProxy() {
        return collateralProxy;
    }

    public void setCollateralProxy(ICollateralProxy collateralProxy) {
        this.collateralProxy = collateralProxy;
    }

    public IStpTransBusManager getStpTransBusManager() {
        return stpTransBusManager;
    }

    public void setStpTransBusManager(IStpTransBusManager stpTransBusManager) {
        this.stpTransBusManager = stpTransBusManager;
    }

    public Map getChainMap() {
        return chainMap;
    }

    public void setChainMap(Map chainMap) {
        this.chainMap = chainMap;
    }

    public IStpCommandExceptionHandler getStpCommandExceptionHandler() {
        return stpCommandExceptionHandler;
    }

    public void setStpCommandExceptionHandler(IStpCommandExceptionHandler stpCommandExceptionHandler) {
        this.stpCommandExceptionHandler = stpCommandExceptionHandler;
    }

    public boolean executeCommand(IStpMasterTrans obStpMasterTrans) throws Exception {
        Map ctx = new HashMap();
        boolean status = false;
        String currentChain = null;
        ctx.put(STP_TRX_VALUE, obStpMasterTrans);

        String stpTransType = obStpMasterTrans.getTransactionType();
        if (stpTransType.equals(TRANS_TYPE_LIMIT)) {
            IFacilityTrxValue obFacilityTrxValue = getFacilityProxy().retrieveFacilityMasterTransactionByTransactionId(obStpMasterTrans.getTransactionId());
            String islamicLoanType = getStpTransBusManager().getStpIslamicLoanType(obFacilityTrxValue.getFacilityMaster().getLimit().getProductDesc(),
                    obFacilityTrxValue.getFacilityMaster().getLimit().getFacilityCode());

            if (StringUtils.equals(obFacilityTrxValue.getStatus(), ICMSConstant.STATE_LOADING)) {
                if (obFacilityTrxValue.getFacilityMaster().getLimit().getFacilitySequence() > 0) {
                    currentChain = "facChainUpdate";
                } else {
                    currentChain = "facChainCreate";
                }

                if (StringUtils.equals(islamicLoanType, STP_ISLAMIC_LOAN_TYPE_MASTER)) {
                    currentChain = currentChain + "2";
                } else if (StringUtils.equals(islamicLoanType, STP_ISLAMIC_LOAN_TYPE_BBA)) {
                    currentChain = currentChain + "3";
                } else if (StringUtils.equals(islamicLoanType, STP_ISLAMIC_LOAN_TYPE_CORPORATE)) {
                    //out of scope
                } else {
                    currentChain = currentChain + "1";
                }
            } else if (StringUtils.equals(obFacilityTrxValue.getStatus(), ICMSConstant.STATE_LOADING_DELETE)) {
                if (StringUtils.equals(islamicLoanType, STP_ISLAMIC_LOAN_TYPE_MASTER)) {
                    currentChain = "facChainDelete2";
                } else if (StringUtils.equals(islamicLoanType, STP_ISLAMIC_LOAN_TYPE_BBA)) {
                    currentChain = "facChainDelete3";
                } else if (StringUtils.equals(islamicLoanType, STP_ISLAMIC_LOAN_TYPE_CORPORATE)) {
                    //out of scope                    
                } else {
                    currentChain = "facChainDelete1";
                }
            } else {
                throw new StpCommonException(ERR_CODE_GENERIC, ERR_DESC_INVALID_TRX_STATUS, null);
            }

            logger.info("##### Facility chain used=" + currentChain + " Facility Id=" + obFacilityTrxValue.getReferenceID());

            ctx.put(FAC_TRX_VALUE, obFacilityTrxValue);
            status = execute(currentChain, ctx);

            //update master trx status to COMPLETE if all commands executed sucessfully
            if (!status) {
                obStpMasterTrans.setStatus(MASTER_TRX_COMPLETE);
                getStpTransBusManager().updateMasterTrans(obStpMasterTrans);
            }

            //Andy Wong: set previous transaction team membership id when system update collateral
            //so that pending retry trx visible to checker
            obFacilityTrxValue.getTrxContext().setTeamMembershipID(obFacilityTrxValue.getTeamMembershipID());

            //Andy Wong: update transaction following Stp response
            if (StringUtils.equals(obStpMasterTrans.getStatus(), IStpConstants.MASTER_TRX_PENDING_MAKER)) {
                getFacilityProxy().systemRejectFacilityMaster(obFacilityTrxValue.getTrxContext(), obFacilityTrxValue);
            } else if (StringUtils.equals(obStpMasterTrans.getStatus(), IStpConstants.MASTER_TRX_PENDING_CHECKER) || StringUtils.equals(obStpMasterTrans.getStatus(), IStpConstants.MASTER_TRX_RESET)) {
                getFacilityProxy().systemUpdateFacilityMaster(obFacilityTrxValue.getTrxContext(), obFacilityTrxValue);
            } else if (StringUtils.equals(obStpMasterTrans.getStatus(), IStpConstants.MASTER_TRX_COMPLETE)) {
                getFacilityProxy().systemApproveFacilityMaster(obFacilityTrxValue.getTrxContext(), obFacilityTrxValue);
            }

        } else if (stpTransType.equals(TRANS_TYPE_COL)) {
            ICollateralTrxValue obCollateralTrxValue = getCollateralProxy().getCollateralTrxValue(null, obStpMasterTrans.getTransactionId());
            if (StringUtils.equals(obCollateralTrxValue.getStatus(), ICMSConstant.STATE_LOADING)) {
                //route workflow to update operation when col master been stp
                if (StringUtils.isBlank(obCollateralTrxValue.getCollateral().getSCISecurityID())) {
                    currentChain = "colChainCreate";
                } else {
                    currentChain = "colChainUpdate";
                }
            } else if (StringUtils.equals(obCollateralTrxValue.getStatus(), ICMSConstant.STATE_LOADING_DELETE)) {
                currentChain = "colChainDelete";
            }

            logger.info("##### Collateral chain used=" + currentChain + " Collateral Id=" + obCollateralTrxValue.getReferenceID());

            ctx.put(COL_TRX_VALUE, obCollateralTrxValue);
            status = execute(currentChain, ctx);

            //update master trx status to COMPLETE if all commands executed sucessfully
            if (!status) {
                obStpMasterTrans.setStatus(MASTER_TRX_COMPLETE);
                getStpTransBusManager().updateMasterTrans(obStpMasterTrans);
            }

            //Andy Wong: set previous transaction team membership id when system update collateral
            //so that pending retry trx visible to checker
            obCollateralTrxValue.getTrxContext().setTeamMembershipID(obCollateralTrxValue.getTeamMembershipID());

            //Andy Wong: update transaction following Stp response
            if (StringUtils.equals(obStpMasterTrans.getStatus(), IStpConstants.MASTER_TRX_PENDING_MAKER)) {
                getCollateralProxy().systemRejectCollateral(obCollateralTrxValue.getTrxContext(), obCollateralTrxValue);
            } else if (StringUtils.equals(obStpMasterTrans.getStatus(), IStpConstants.MASTER_TRX_PENDING_CHECKER) || StringUtils.equals(obStpMasterTrans.getStatus(), IStpConstants.MASTER_TRX_RESET)) {
                getCollateralProxy().systemUpdateCollateral(obCollateralTrxValue.getTrxContext(), obCollateralTrxValue);
            } else if (StringUtils.equals(obStpMasterTrans.getStatus(), IStpConstants.MASTER_TRX_COMPLETE)) {
                getCollateralProxy().systemApproveCollateral(obCollateralTrxValue.getTrxContext(), obCollateralTrxValue);
            }
        }
        return status;
    }

    private boolean execute(String chainName, Map ctx) {
        List chain = (List) getChainMap().get(chainName);
        try {
            if (chain != null) {
                for (Iterator iterator = chain.iterator(); iterator.hasNext();) {
                    IStpCommand stpCommand = (IStpCommand) iterator.next();

                    if (stpCommand.execute(ctx)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            getStpCommandExceptionHandler().postprocess(ctx, e);
            return true;
        }

        return false;
    }
}
