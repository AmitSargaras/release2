package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class TradeInInfoDAOImpl extends HibernateDaoSupport implements TradeInInfoDAO {

	public ITradeInInfo saveOrUpdateTradeInInfo(String entityName, ITradeInInfo tradeInInfo) {
		getHibernateTemplate().saveOrUpdate(entityName, tradeInInfo);
		return tradeInInfo;
	}

	public void deleteTradeInInfoByCollId(String entityName, long collateralId) {
		List resultList = getTradeInInfoListByCollId(entityName, collateralId);
		if (resultList.size() > 0) {
			getHibernateTemplate().deleteAll(getTradeInInfoListByCollId(entityName, collateralId));
		}
	}

	public ITradeInInfo[] getTradeInInfoByCollId(String entityName, long collateralId) {
		List resultList = getTradeInInfoListByCollId(entityName, collateralId);
		return ((OBTradeInInfo[]) resultList.toArray(new OBTradeInInfo[0]));
	}

	private List getTradeInInfoListByCollId(String entityName, long collateralId) {
		DefaultLogger.debug(this, "EntryName :" + entityName + ", Coll-Id :" + collateralId);

		Map parametersMap = new HashMap();
		parametersMap.put("collateralId", new Long(collateralId));

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		DefaultLogger.debug(this, "Size :" + (resultList == null ? 0 : resultList.size()));

		return resultList;
	}
}
