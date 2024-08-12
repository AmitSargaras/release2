package com.integrosys.cms.ui.ubsupload.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;

public class UbsHibernateDAOImpl extends HibernateDaoSupport implements IUbsHibernateDAO{
	public IUbsErrorLog insertErrLog(IUbsErrorLog objIUbsError) {
		try{
			Serializable save = getHibernateTemplate().save("UbsErrorLog", objIUbsError);
			
			String id =save.toString();
			
			Set errEntriesSet = objIUbsError.getErrEntriesSet();
			Set errSet=new HashSet();
			if(!"0".equals(id)){
				for (Iterator iterator = errEntriesSet.iterator(); iterator.hasNext();) {
					IUbsErrDetLog detLog = (IUbsErrDetLog) iterator.next();
					detLog.setPtId(id);
					getHibernateTemplate().save("UbsErrDetLog", detLog);
					errSet.add(detLog);
				}
				objIUbsError.setErrEntriesSet(errSet);
			}
			return objIUbsError;
		}catch (Exception e) {
				e.printStackTrace();
				return objIUbsError;
		}
		}
}
