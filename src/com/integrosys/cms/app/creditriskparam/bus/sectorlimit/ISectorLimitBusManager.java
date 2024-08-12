package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.cms.app.feed.bus.gold.GoldFeedGroupException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;

import java.util.List;
import java.util.Set;

public interface ISectorLimitBusManager {

    public List getAllSectorLimit();

    public List getAllSubSectorLimit();

    public List getAllEcoSectorLimit();

    public IMainSectorLimitParameter getLimitById(long id);

	public IMainSectorLimitParameter createLimit(IMainSectorLimitParameter stagingLimit);

    public IMainSectorLimitParameter updateLimit(IMainSectorLimitParameter updActual);

    public IEcoSectorLimitParameter getEcoSectorLimitBySectorCode(String sectorCode);

    public ISubSectorLimitParameter getSubSectorLimitBySectorCode(String sectorcode);

    public IMainSectorLimitParameter getMainSectorLimitBySectorCode(String sectorcode);
}