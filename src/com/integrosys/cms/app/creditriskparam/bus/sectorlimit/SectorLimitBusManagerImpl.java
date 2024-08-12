package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.gold.AbstractGoldFeedBusManager;
import com.integrosys.cms.app.feed.bus.gold.GoldFeedGroupException;
import com.integrosys.cms.app.feed.bus.gold.IGoldDao;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;

import javax.ejb.FinderException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SectorLimitBusManagerImpl extends AbstractSectorLimitBusManager  {

    public String getMainSectorLimitEntityName() {
        return  ISectorLimitDao.MAIN_SECTOR_LIMIT_PARAM;
    }

    public String getSubSectorLimitEntityName() {
        return  ISectorLimitDao.SUB_SECTOR_LIMIT_PARAM;
    }

    public String getEcoSectorLimitEntityName() {
        return  ISectorLimitDao.ECO_SECTOR_LIMIT_PARAM;
    }

    public List getAllSectorLimit()throws SectorLimitException {
        try {
             return getSectorLimitDao().getAllSectorLimit(getMainSectorLimitEntityName());
        }catch (Exception e) {
            throw new SectorLimitException ("Exception caught at getAll " + e.toString());
        }
    }

    public List getAllSubSectorLimit()throws SectorLimitException {
        try {
            return getSectorLimitDao().getAllSubSectorLimit(getSubSectorLimitEntityName());

        }catch (Exception e) {
             throw new SectorLimitException ("Exception caught at getAll " + e.toString());
        }
    }

    public List getAllEcoSectorLimit()throws SectorLimitException {
        try {
            return getSectorLimitDao().getAllSectorLimit(getEcoSectorLimitEntityName());

        }catch (Exception e) {
            throw new SectorLimitException ("Exception caught at getAll " + e.toString());
        }
    }

    public IMainSectorLimitParameter getLimitById(long id) throws SectorLimitException {
        try {
        	DefaultLogger.debug(this, "getLimitById 1: " + id);

            IMainSectorLimitParameter mainSectorLimitParameter = getSectorLimitDao().getLimitById(getMainSectorLimitEntityName(), id);

            return mainSectorLimitParameter;
        } catch (Exception e) {
            throw new SectorLimitException("RemoteException caught! " + e.toString());
        }
    }

    public IEcoSectorLimitParameter getEcoSectorLimitBySectorCode(String code) throws SectorLimitException {
        try {
			IEcoSectorLimitParameter sectorLimit = getSectorLimitDao().getEcoSectorLimitBySectorCode(getEcoSectorLimitEntityName(), code);

			return sectorLimit;
        }
        catch (Exception e) {
            throw new SectorLimitException ("Exception caught at getEcoSectorLimitBySectorCode " + e.toString());
        }
	}

    public ISubSectorLimitParameter getSubSectorLimitBySectorCode(String code) throws SectorLimitException {
        try {
			ISubSectorLimitParameter sectorLimit = getSectorLimitDao().getSubSectorLimitBySectorCode(getSubSectorLimitEntityName(), code);

			return sectorLimit;
        }
        catch (Exception e) {
            throw new SectorLimitException ("Exception caught at getSubSectorLimitBySectorCode " + e.toString());
        }
	}

    public IMainSectorLimitParameter getMainSectorLimitBySectorCode(String code) throws SectorLimitException {
        try {
			IMainSectorLimitParameter sectorLimit = getSectorLimitDao().getMainSectorLimitBySectorCode(getMainSectorLimitEntityName(), code);

			return sectorLimit;
        }
        catch (Exception e) {
            throw new SectorLimitException ("Exception caught at getMainSectorLimitBySectorCode " + e.toString());
        }
	}
    public IMainSectorLimitParameter createLimit(IMainSectorLimitParameter sectorLimit) throws SectorLimitException {
        if (sectorLimit == null)
            throw new SectorLimitException("Sector Limit Parameter is null");
        try {
            return getSectorLimitDao().createLimit(getMainSectorLimitEntityName(), sectorLimit);
        } catch (Exception e) {
            throw new SectorLimitException("Exception caught! " + e.toString());
        }
    }
    public IMainSectorLimitParameter updateLimit(IMainSectorLimitParameter sectorLimit) throws SectorLimitException {
        try {
                if (sectorLimit.getId() == null) {       //add
                    return getSectorLimitDao().createLimit(getMainSectorLimitEntityName(), sectorLimit);

                } else if (ICMSConstant.STATE_DELETED.equals(sectorLimit.getStatus())) {          //detete
                    IMainSectorLimitParameter mainSectorLimitParameter = getSectorLimitDao().updateLimit(getMainSectorLimitEntityName(), sectorLimit);
                    return mainSectorLimitParameter;

                } else {                                         //edit
                    IMainSectorLimitParameter mainSectorLimitParameter = getSectorLimitDao().updateLimit(getMainSectorLimitEntityName(), sectorLimit);
                    return mainSectorLimitParameter;
            }
        } catch (Exception e) {
            throw new SectorLimitException("Exception caught! " + e.toString());
        }
    }
}