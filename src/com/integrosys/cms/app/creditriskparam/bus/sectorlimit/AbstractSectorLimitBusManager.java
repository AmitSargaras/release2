package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.cms.app.feed.bus.gold.GoldFeedGroupException;
import com.integrosys.cms.app.feed.bus.gold.IGoldDao;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedBusManager;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;

import java.util.List;

public abstract class AbstractSectorLimitBusManager implements ISectorLimitBusManager {
	private ISectorLimitDao sectorLimitDao;

    public ISectorLimitDao getSectorLimitDao() {
        return sectorLimitDao;
    }

    public void setSectorLimitDao(ISectorLimitDao sectorLimitDao) {
        this.sectorLimitDao = sectorLimitDao;
    }

	public abstract String getMainSectorLimitEntityName();

    public abstract String getSubSectorLimitEntityName();

    public abstract String getEcoSectorLimitEntityName();



    
}