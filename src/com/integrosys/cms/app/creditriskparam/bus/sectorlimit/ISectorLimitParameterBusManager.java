package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Syukri
 * Date: Jun 2, 2008
 */
public interface ISectorLimitParameterBusManager extends Serializable {
    List getAllSectorLimit() throws SectorLimitException;
    List getAllSubSectorLimit() throws SectorLimitException;
    List getAllEcoSectorLimit() throws SectorLimitException;
    IMainSectorLimitParameter createLimit(IMainSectorLimitParameter sectorLimitList) throws SectorLimitException;
    IMainSectorLimitParameter updateLimit(IMainSectorLimitParameter sectorLimitList) throws SectorLimitException;
    IMainSectorLimitParameter getLimitById(long id) throws SectorLimitException;
    IEcoSectorLimitParameter getEcoSectorLimitBySectorCode(String sectorCode);
}
