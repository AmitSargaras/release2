package com.integrosys.cms.app.propertyindex.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.host.stp.bus.IStpMasterTrans;

import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.ResultSet;
import java.io.Serializable;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 11, 2008
 * Time: 3:11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyIdxDaoImpl extends HibernateDaoSupport implements IPropertyIdxDao {

    public IPropertyIdx createPropertyIdx(String entityName, IPropertyIdx propertyIdx) {
        Long key = (Long) getHibernateTemplate().save(entityName, propertyIdx);
        propertyIdx.setPropertyIdxId(key.longValue());
		return propertyIdx;
	}

	public IPropertyIdx getPropertyIdx(String entityName, long key) {
        return (IPropertyIdx) getHibernateTemplate().get(entityName, new Long(key));
	}

	public IPropertyIdx updatePropertyIdx(String entityName, IPropertyIdx propertyIdx) {
		getHibernateTemplate().update(entityName, propertyIdx);
		return (IPropertyIdx) getHibernateTemplate().load(entityName, new Long(propertyIdx.getPropertyIdxId()));
	}
}
