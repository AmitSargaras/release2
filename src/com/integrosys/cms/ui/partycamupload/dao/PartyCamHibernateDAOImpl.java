package com.integrosys.cms.ui.partycamupload.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.batch.partycam.IPartyCamErrDetLog;
import com.integrosys.cms.batch.partycam.IPartyCamErrorLog;

public class PartyCamHibernateDAOImpl extends HibernateDaoSupport implements IPartyCamHibernateDAO{
	public IPartyCamErrorLog insertErrLog(IPartyCamErrorLog objIPartyCamError) {
		try{
			Serializable save = getHibernateTemplate().save("PartyCamErrorLog", objIPartyCamError);
			
			String id =save.toString();
			
			Set errEntriesSet = objIPartyCamError.getErrEntriesSet();
			Set errSet=new HashSet();
			if(!"0".equals(id)){
				for (Iterator iterator = errEntriesSet.iterator(); iterator.hasNext();) {
					IPartyCamErrDetLog detLog = (IPartyCamErrDetLog) iterator.next();
					detLog.setPtId(id);
					getHibernateTemplate().save("PartyCamErrDetLog", detLog);
					errSet.add(detLog);
				}
				objIPartyCamError.setErrEntriesSet(errSet);
			}
			return objIPartyCamError;
		}catch (Exception e) {
				e.printStackTrace();
				return objIPartyCamError;
		}
		}
}
