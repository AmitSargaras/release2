package com.integrosys.cms.app.creditriskparam.proxy.sectorlimit;

import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.util.List;
import java.util.Set;

/**
 * Author: Syukri
 * Date: Jun 5, 2008
 */
public interface ISectorLimitParameterProxy {
    List listSectorLimit() throws SectorLimitException;
    List listSubSectorLimit() throws SectorLimitException;
    List listEcoSectorLimit() throws SectorLimitException;
    ISectorLimitParameterTrxValue getTrxValue(ITrxContext ctx) throws SectorLimitException;
    ISectorLimitParameterTrxValue getTrxValueByTrxId(ITrxContext ctx, String trxId) throws SectorLimitException;
	ISectorLimitParameterTrxValue makerUpdateList(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException;
    ISectorLimitParameterTrxValue checkerApprove(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException;
    ISectorLimitParameterTrxValue checkerReject(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException;
    ISectorLimitParameterTrxValue makerClose(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException;
    ISectorLimitParameterTrxValue makerDelete(ITrxContext ctx, ISectorLimitParameterTrxValue trxValue) throws SectorLimitException;
    IMainSectorLimitParameter getSectorLimitById (long id) throws SectorLimitException;
    ISectorLimitParameterTrxValue getTrxValueById (long id) throws SectorLimitException;
}
