package com.integrosys.cms.app.udf.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitProfileUdf;
import com.integrosys.cms.ui.manualinput.aa.AADetailForm;

public class UdfDaoImpl extends HibernateDaoSupport implements IUdfDao {

	public void deleteUdf(IUdf udf) throws UdfException {
		if (udf == null) {
			throw new UdfException("UdfDaoImpl.deleteUdf(IUdf): UDF cannot be null.");
		}
		getHibernateTemplate().delete(udf);
	}

	public void freezeUdf(IUdf udf) throws UdfException {
		if (udf == null) {
			throw new UdfException("UdfDaoImpl.freezeUdf(IUdf): UDF cannot be null.");
		}
		udf = (IUdf) this.findUdfById("com.integrosys.cms.app.udf.bus.OBUdf", udf.getId());
		udf.setStatus(UDFConstants.STATUS_FREEZED);
		getHibernateTemplate().update(udf);
	}
	
	public List findAllUdfs() throws UdfException {
		List udfList = (List) getHibernateTemplate().loadAll(OBUdf.class);
		if (udfList == null) {
			udfList = new ArrayList();
		}
		return udfList;
	}

	public IUdf findUdfById(String entityName, long id) throws UdfException {
		if (id == 0) {
			throw new UdfException("UdfDaoImpl.findUdfById(String, long): ID cannot be 0.");
		}
		if (entityName == null || entityName.length() == 0) {
			throw new UdfException("UdfDaoImpl.findUdfById(String, long): entityName cannot be null.");
		}
		return (IUdf)getHibernateTemplate().get(entityName , new Long(id));	
	}

	public List findUdfByStatus(String entityName, String status) throws UdfException {
		if (status == null) {
			throw new UdfException("UdfDaoImpl.findUdfById(String, String): status cannot be null.");
		}
		if (entityName == null || entityName.length() == 0) {
			throw new UdfException("UdfDaoImpl.findUdfById(String, String): entityName cannot be null.");
		}
		String query = "FROM "+IUdfDao.ACTUAL_UDF_NAME+" where status="+status;
	    List udfList = (ArrayList) getHibernateTemplate().find(query);
	    return udfList;
	}
	
	public IUdf insertUdf(IUdf udf) throws UdfException {
		if (udf == null) {
			throw new UdfException("UdfDaoImpl.insertUdf(IUdf): UDF cannot be null.");
		}
		Long id = (Long) getHibernateTemplate().save(udf);
		udf.setId(id.longValue());
		return udf;
	}

	public IUdf updateUdf(IUdf udf) throws UdfException {
		if (udf == null) {
			throw new UdfException("UdfDaoImpl.updateUdf(IUdf): UDF cannot be null.");
		}
		getHibernateTemplate().update(udf);
		return  (IUdf) getHibernateTemplate().load(IUdf.class, new Long(udf.getId()));
	}

	public List getUdfByModuleId (String moduleId)  throws UdfException {
		List udfList = null;
		if (moduleId == null || moduleId.equals("0")) {
			throw new UdfException("UDF Generation Failed : Invalid Module Id.");
		}
		String query = "FROM "+IUdfDao.ACTUAL_UDF_NAME+" where moduleId="+moduleId;
	    udfList = (ArrayList) getHibernateTemplate().find(query);
	    return udfList;
	}
	
	public List getUdfSequencesByModuleId (String moduleId)  throws UdfException { 
		List udfList = null;
		if (moduleId == null || moduleId.equals("0")) {
			throw new UdfException("UDF Generation Failed : Invalid Module Id.");
		}
		String query = "select udf.sequence FROM "+IUdfDao.ACTUAL_UDF_NAME+" udf where moduleId="+moduleId;
	    udfList = (ArrayList) getHibernateTemplate().find(query);
	    return udfList;
	}
	
	public List getUdfByModuleIdAndStatus (String moduleId, String status)  throws UdfException {
		List udfList = null;
		if (moduleId == null || moduleId.equals("0")) {
			throw new UdfException("UDF Generation Failed : Invalid Module Id.");
		}
		if (status == null || moduleId.equals("")) {
			throw new UdfException("UDF Generation Failed : Invalid Status.");
		}
		String query = "FROM "+IUdfDao.ACTUAL_UDF_NAME+"  where moduleId="+moduleId+" and status='"+status+"'";
	    udfList = (ArrayList) getHibernateTemplate().find(query);
	    return udfList;
	}
	public List getUdfByMandatory(String moduleId)  throws UdfException {
		List udfList = new ArrayList();
		
		String query = "FROM "+IUdfDao.ACTUAL_UDF_NAME+" where moduleId="+moduleId+" and mandatory='on'and status='ACTIVE'";
	    udfList = (ArrayList) getHibernateTemplate().find(query);
	    return udfList;
	}
	
	public List getUdfByFieldTypeId(String moduleId ,int fieldId)  throws UdfException {
		List udfList = new ArrayList();
		
		String query = "FROM "+IUdfDao.ACTUAL_UDF_NAME+" where moduleId="+moduleId+" and fieldTypeId="+fieldId+" and status='ACTIVE'";
	    udfList = (ArrayList) getHibernateTemplate().find(query);
	    return udfList;
	}
	
	//Added by Abhijit R.
	
	public boolean findUdfByName( String name , String ModuleId) throws UdfException {
		List udfList = new ArrayList();
		boolean isFieldName=false;
		//String query = "FROM com.integrosys.cms.app.udf.bus.OBUdf udf where upper(FIELDNAME) ='"+name+"' ";
		String query = "FROM "+IUdfDao.ACTUAL_UDF_NAME+" where FIELDNAME ='"+name+"' and MODULEID ='"+ModuleId+"' ";
	    udfList = (ArrayList) getHibernateTemplate().find(query);
	    
	    if(udfList!=null){
	    	if(udfList.size()>0){
	    		isFieldName=true;
	    	}
	    }
	    
	    
		return isFieldName;	
	}
	//Added by santosh 
	public List getUdfByNonMandatory(String moduleId)  throws UdfException {
		List udfList = new ArrayList();
		//String query = "FROM com.integrosys.cms.app.udf.bus.OBUdf udf where moduleId="+moduleId+" and mandatory is null and status='Freezed'";
		String query = "FROM "+IUdfDao.ACTUAL_UDF_NAME+" where moduleId="+moduleId+" and mandatory is null and status='ACTIVE'";
	    udfList = (ArrayList) getHibernateTemplate().find(query);
	    return udfList;
	}
	//End santosh
	
	//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
	public AADetailForm setUdfDataForCam(ILimitProfile limitProfileOB){
		AADetailForm form=new AADetailForm();
		if (limitProfileOB.getUdfData() != null && limitProfileOB.getUdfData().length > 0) {
			ILimitProfileUdf udf = limitProfileOB.getUdfData()[0];	
				form.setUdf1(udf.getUdf1());
	            form.setUdf2(udf.getUdf2());
	            form.setUdf3(udf.getUdf3());
	            form.setUdf4(udf.getUdf4());
	            form.setUdf5(udf.getUdf5());
	            form.setUdf6(udf.getUdf6());
	            form.setUdf7(udf.getUdf7());
	            form.setUdf8(udf.getUdf8());
	            form.setUdf9(udf.getUdf9());
	            form.setUdf10(udf.getUdf10());
	            form.setUdf11(udf.getUdf11());
	            form.setUdf12(udf.getUdf12());
	            form.setUdf13(udf.getUdf13());
	            form.setUdf14(udf.getUdf14());
	            form.setUdf15(udf.getUdf15());
	            form.setUdf16(udf.getUdf16());
	            form.setUdf17(udf.getUdf17());
	            form.setUdf18(udf.getUdf18());
	            form.setUdf19(udf.getUdf19());
	            form.setUdf20(udf.getUdf20());
	            form.setUdf21(udf.getUdf21());
	            form.setUdf22(udf.getUdf22());
	            form.setUdf23(udf.getUdf23());
	            form.setUdf24(udf.getUdf24());
	            form.setUdf25(udf.getUdf25());
	            form.setUdf26(udf.getUdf26());
	            form.setUdf27(udf.getUdf27());
	            form.setUdf28(udf.getUdf28());
	            form.setUdf29(udf.getUdf29());
	            form.setUdf30(udf.getUdf30());
	            form.setUdf31(udf.getUdf31());
	            form.setUdf32(udf.getUdf32());
	            form.setUdf33(udf.getUdf33());
	            form.setUdf34(udf.getUdf34());
	            form.setUdf35(udf.getUdf35());
	            form.setUdf36(udf.getUdf36());
	            form.setUdf37(udf.getUdf37());
	            form.setUdf38(udf.getUdf38());
	            form.setUdf39(udf.getUdf39());
	            form.setUdf40(udf.getUdf40());
	            form.setUdf41(udf.getUdf41());
	            form.setUdf42(udf.getUdf42());
	            form.setUdf43(udf.getUdf43());
	            form.setUdf44(udf.getUdf44());
	            form.setUdf45(udf.getUdf45());
	            form.setUdf46(udf.getUdf46());
	            form.setUdf47(udf.getUdf47());
	            form.setUdf48(udf.getUdf48());
	            form.setUdf49(udf.getUdf49());
	            form.setUdf50(udf.getUdf50());
			    form.setUdf51(udf.getUdf51());
	            form.setUdf52(udf.getUdf52());
	            form.setUdf53(udf.getUdf53());
	            form.setUdf54(udf.getUdf54());
	            form.setUdf55(udf.getUdf55());
	            form.setUdf56(udf.getUdf56());
	            form.setUdf57(udf.getUdf57());
	            form.setUdf58(udf.getUdf58());
	            form.setUdf59(udf.getUdf59());
	            form.setUdf60(udf.getUdf60());
	            form.setUdf61(udf.getUdf61());
	            form.setUdf62(udf.getUdf62());
	            form.setUdf63(udf.getUdf63());
	            form.setUdf64(udf.getUdf64());
	            form.setUdf65(udf.getUdf65());
	            form.setUdf66(udf.getUdf66());
	            form.setUdf67(udf.getUdf67());
	            form.setUdf68(udf.getUdf68());
	            form.setUdf69(udf.getUdf69());
	            form.setUdf70(udf.getUdf70());
	            form.setUdf71(udf.getUdf71());
	            form.setUdf72(udf.getUdf72());
	            form.setUdf73(udf.getUdf73());
	            form.setUdf74(udf.getUdf74());
	            form.setUdf75(udf.getUdf75());
	            form.setUdf76(udf.getUdf76());
	            form.setUdf77(udf.getUdf77());
	            form.setUdf78(udf.getUdf78());
	            form.setUdf79(udf.getUdf79());
	            form.setUdf80(udf.getUdf80());
	            form.setUdf81(udf.getUdf81());
	            form.setUdf82(udf.getUdf82());
	            form.setUdf83(udf.getUdf83());
	            form.setUdf84(udf.getUdf84());
	            form.setUdf85(udf.getUdf85());
	            form.setUdf86(udf.getUdf86());
	            form.setUdf87(udf.getUdf87());
	            form.setUdf88(udf.getUdf88());
	            form.setUdf89(udf.getUdf89());
	            form.setUdf90(udf.getUdf90());
	            form.setUdf91(udf.getUdf91());
	            form.setUdf92(udf.getUdf92());
	            form.setUdf93(udf.getUdf93());
	            form.setUdf94(udf.getUdf94());
	            form.setUdf95(udf.getUdf95());
	            form.setUdf96(udf.getUdf96());
	            form.setUdf97(udf.getUdf97());
	            form.setUdf98(udf.getUdf98());
	            form.setUdf99(udf.getUdf99());
	            form.setUdf100(udf.getUdf100());
	            form.setUdfId(udf.getId());
	}
		return form;
	}
	//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
	
	/**
	 * @return Udf Object
	 * @param Entity Name
	 * @param Udf Object  
	 * This method Creates Udf Object
	 */
	public IUdf createUdf(String entityName,IUdf udf) throws UdfException {
		if(!(entityName==null|| udf==null)){
			udf.setStatus("ACTIVE");
			udf.setDeprecated("N");
			Long key = (Long) getHibernateTemplate().save(entityName, udf);
			udf.setId(key.longValue());
			return udf;
			}else{
				throw new UdfException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return Udf Object
	 * @param Entity Name
	 * @param Udf Object  
	 * This method Updates Udf Object
	 */
	
	public IUdf updateUdf(String entityName, IUdf item) throws UdfException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IUdf) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new UdfException("ERROR-- Entity Name Or Key is null");
		}
	}

	
	public IUdf getUdf(String entityName, Serializable key) throws UdfException {
		if(!(entityName==null|| key==null)){
			
			return (IUdf) getHibernateTemplate().get(entityName, key);
			}else{
				throw new UdfException("ERROR-- Entity Name Or Key is null");
			}
	}

	
	public List getUdfList() {

		List udfList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+IUdfDao.ACTUAL_UDF_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			udfList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return udfList;
		
	}

	public IUdf deleteUdf(String entityName, IUdf item)
			throws UdfException {

		if (!(entityName == null || item == null)) {
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (IUdf) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new UdfException("ERROR-- Entity Name Or Key is null");
		}
	}

	public IUdf enableUdf(String entityName, IUdf item)
			throws UdfException {

		if (!(entityName == null || item == null)) {
			item.setDeprecated("N");
			item.setStatus("ACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (IUdf) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new UdfException("ERROR-- Entity Name Or Key is null");
		}
	}

	public IUdf getUdfByModuleIdAndSequence(String moduleId, String sequence) {
		try{
			List udfList = new ArrayList();
			String query = "SELECT FROM " + IUdfDao.ACTUAL_UDF_NAME 
					+ " WHERE status='ACTIVE' AND DEPRECATED='N' "
					+ "and moduleId = " + moduleId + " and sequence = " + sequence;
			udfList = (List) getHibernateTemplate().find(query);
			if(udfList != null && udfList.size() > 0) {
				return (IUdf) udfList.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//	public List getUdfByMandatoryView(String moduleId)  throws UdfException {
//		List udfList = new ArrayList();
//		
//		String query = "FROM "+IUdfDao.ACTUAL_UDF_NAME+" where moduleId="+moduleId+" and mandatory='on'";
//	    udfList = (ArrayList) getHibernateTemplate().find(query);
//	    return udfList;
//	}
//	
//	public List getUdfByNonMandatoryView(String moduleId)  throws UdfException {
//		List udfList = new ArrayList();
//		String query = "FROM "+IUdfDao.ACTUAL_UDF_NAME+" where moduleId="+moduleId+" and mandatory is null";
//	    udfList = (ArrayList) getHibernateTemplate().find(query);
//	    return udfList;
//	}
}
