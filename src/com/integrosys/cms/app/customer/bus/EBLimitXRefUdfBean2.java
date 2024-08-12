package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.batch.common.BatchResourceFactory;
 
public abstract class EBLimitXRefUdfBean2 implements EntityBean, ILimitXRefUdf2 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_LIMIT_XREF_UDF;
	private static final String[] EXCLUDE_METHOD = new String[] { "getId", "getXRefID" };
	
	protected EntityContext _context = null;

	
//mm
	public long getId() {
		if (null != getUdfLimitPK2()) {
			return getUdfLimitPK2().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	public void setId(long value) {
		setUdfLimitPK2(new Long(value));
	}
		
	public long getXRefID() {
		if (null != getLimitXRefIdFK2()) {
			return getLimitXRefIdFK2().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setXRefID(long value) {
		setLimitXRefIdFK2(new Long(value));
	}
	
	public ILimitXRefUdf2 getValue() {
		ILimitXRefUdf2 value = new OBLimitXRefUdf2();
		AccessorUtil.copyValue(this, value);
		return value;
	}
	
	public Long ejbCreate(ILimitXRefUdf2 value) throws CreateException {
		if (null == value) {
			throw new CreateException("ILimitXRefUdf2 is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setId(pk);
			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			_context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}
	
	public void setValue(ILimitXRefUdf2 value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}
	
	public void ejbPostCreate(ILimitXRefUdf2 value) {}
	public void ejbActivate() throws EJBException, RemoteException {}
	public void ejbLoad() throws EJBException, RemoteException {}
	public void ejbPassivate() throws EJBException, RemoteException {}
	public void ejbRemove() throws RemoveException, EJBException, RemoteException {}
	public void ejbStore() throws EJBException, RemoteException {}

	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}
	public void unsetEntityContext() {
		_context = null;
	}
	
	public abstract Long getUdfLimitPK2();
	public abstract void setUdfLimitPK2(Long value);
	
	public abstract Long getLimitXRefIdFK2();
	public abstract void setLimitXRefIdFK2(Long value);
	
	
   
    public  abstract String getUdf116_Label();
    public  abstract String getUdf117_Label();
    public  abstract String getUdf118_Label();
    public  abstract String getUdf119_Label();
    public  abstract String getUdf120_Label();
    public  abstract String getUdf121_Label();
    public  abstract String getUdf122_Label();
    public  abstract String getUdf123_Label();
    public  abstract String getUdf124_Label();
    public  abstract String getUdf125_Label();
    public  abstract String getUdf126_Label();
    public  abstract String getUdf127_Label();
    public  abstract String getUdf128_Label();
    public  abstract String getUdf129_Label();
    public  abstract String getUdf130_Label();
    public  abstract String getUdf131_Label();
    public  abstract String getUdf132_Label();
    public  abstract String getUdf133_Label();
    public  abstract String getUdf134_Label();
    public  abstract String getUdf135_Label();
    public  abstract String getUdf136_Label();
    public  abstract String getUdf137_Label();
    public  abstract String getUdf138_Label();
    public  abstract String getUdf139_Label();
    public  abstract String getUdf140_Label();
    public  abstract String getUdf141_Label();
    public  abstract String getUdf142_Label();
    public  abstract String getUdf143_Label();
    public  abstract String getUdf144_Label();
    public  abstract String getUdf145_Label();
    public  abstract String getUdf146_Label();
    public  abstract String getUdf147_Label();
    public  abstract String getUdf148_Label();
    public  abstract String getUdf149_Label();
    public  abstract String getUdf150_Label();

    public  abstract String getUdf151_Label();
    public  abstract String getUdf152_Label();
    public  abstract String getUdf153_Label();
    public  abstract String getUdf154_Label();
    public  abstract String getUdf155_Label();
    public  abstract String getUdf156_Label();
    public  abstract String getUdf157_Label();
    public  abstract String getUdf158_Label();
    public  abstract String getUdf159_Label();
    public  abstract String getUdf160_Label();
    public  abstract String getUdf161_Label();
    public  abstract String getUdf162_Label();
    public  abstract String getUdf163_Label();
    public  abstract String getUdf164_Label();
    public  abstract String getUdf165_Label();
    public  abstract String getUdf166_Label();
    public  abstract String getUdf167_Label();
    public  abstract String getUdf168_Label();
    public  abstract String getUdf169_Label();
    public  abstract String getUdf170_Label();
    public  abstract String getUdf171_Label();
    public  abstract String getUdf172_Label();
    public  abstract String getUdf173_Label();
    public  abstract String getUdf174_Label();
    public  abstract String getUdf175_Label();
    public  abstract String getUdf176_Label();
    public  abstract String getUdf177_Label();
    public  abstract String getUdf178_Label();
    public  abstract String getUdf179_Label();
    public  abstract String getUdf180_Label();
    public  abstract String getUdf181_Label();
    public  abstract String getUdf182_Label();
    public  abstract String getUdf183_Label();
    public  abstract String getUdf184_Label();
    public  abstract String getUdf185_Label();
    public  abstract String getUdf186_Label();
    public  abstract String getUdf187_Label();
    public  abstract String getUdf188_Label();
    public  abstract String getUdf189_Label();
    public  abstract String getUdf190_Label();
    public  abstract String getUdf191_Label();
    public  abstract String getUdf192_Label();
    public  abstract String getUdf193_Label();
    public  abstract String getUdf194_Label();
    public  abstract String getUdf195_Label();
    public  abstract String getUdf196_Label();
    public  abstract String getUdf197_Label();
    public  abstract String getUdf198_Label();
    public  abstract String getUdf199_Label();
    public  abstract String getUdf200_Label();
   
    public  abstract String getUdf201_Label();
    public  abstract String getUdf202_Label();
    public  abstract String getUdf203_Label();
    public  abstract String getUdf204_Label();
    public  abstract String getUdf205_Label();
    public  abstract String getUdf206_Label();
    public  abstract String getUdf207_Label();
    public  abstract String getUdf208_Label();
    public  abstract String getUdf209_Label();
    public  abstract String getUdf210_Label();
    public  abstract String getUdf211_Label();
    public  abstract String getUdf212_Label();
    public  abstract String getUdf213_Label();
    public  abstract String getUdf214_Label();
    public  abstract String getUdf215_Label();

    public  abstract String getUdf116_Value();
    public  abstract String getUdf117_Value();
    public  abstract String getUdf118_Value();
    public  abstract String getUdf119_Value();
    public  abstract String getUdf120_Value();
    public  abstract String getUdf121_Value();
    public  abstract String getUdf122_Value();
    public  abstract String getUdf123_Value();
    public  abstract String getUdf124_Value();
    public  abstract String getUdf125_Value();
    public  abstract String getUdf126_Value();
    public  abstract String getUdf127_Value();
    public  abstract String getUdf128_Value();
    public  abstract String getUdf129_Value();
    public  abstract String getUdf130_Value();
    public  abstract String getUdf131_Value();
    public  abstract String getUdf132_Value();
    public  abstract String getUdf133_Value();
    public  abstract String getUdf134_Value();
    public  abstract String getUdf135_Value();
    public  abstract String getUdf136_Value();
    public  abstract String getUdf137_Value();
    public  abstract String getUdf138_Value();
    public  abstract String getUdf139_Value();
    public  abstract String getUdf140_Value();
    public  abstract String getUdf141_Value();
    public  abstract String getUdf142_Value();
    public  abstract String getUdf143_Value();
    public  abstract String getUdf144_Value();
    public  abstract String getUdf145_Value();
    public  abstract String getUdf146_Value();
    public  abstract String getUdf147_Value();
    public  abstract String getUdf148_Value();
    public  abstract String getUdf149_Value();
    public  abstract String getUdf150_Value();


    public  abstract String getUdf151_Value();
    public  abstract String getUdf152_Value();
    public  abstract String getUdf153_Value();
    public  abstract String getUdf154_Value();
    public  abstract String getUdf155_Value();
    public  abstract String getUdf156_Value();
    public  abstract String getUdf157_Value();
    public  abstract String getUdf158_Value();
    public  abstract String getUdf159_Value();
    public  abstract String getUdf160_Value();
    public  abstract String getUdf161_Value();
    public  abstract String getUdf162_Value();
    public  abstract String getUdf163_Value();
    public  abstract String getUdf164_Value();
    public  abstract String getUdf165_Value();
    public  abstract String getUdf166_Value();
    public  abstract String getUdf167_Value();
    public  abstract String getUdf168_Value();
    public  abstract String getUdf169_Value();
    public  abstract String getUdf170_Value();
    public  abstract String getUdf171_Value();
    public  abstract String getUdf172_Value();
    public  abstract String getUdf173_Value();
    public  abstract String getUdf174_Value();
    public  abstract String getUdf175_Value();
    public  abstract String getUdf176_Value();
    public  abstract String getUdf177_Value();
    public  abstract String getUdf178_Value();
    public  abstract String getUdf179_Value();
    public  abstract String getUdf180_Value();
    public  abstract String getUdf181_Value();
    public  abstract String getUdf182_Value();
    public  abstract String getUdf183_Value();
    public  abstract String getUdf184_Value();
    public  abstract String getUdf185_Value();
    public  abstract String getUdf186_Value();
    public  abstract String getUdf187_Value();
    public  abstract String getUdf188_Value();
    public  abstract String getUdf189_Value();
    public  abstract String getUdf190_Value();
    public  abstract String getUdf191_Value();
    public  abstract String getUdf192_Value();
    public  abstract String getUdf193_Value();
    public  abstract String getUdf194_Value();
    public  abstract String getUdf195_Value();
    public  abstract String getUdf196_Value();
    public  abstract String getUdf197_Value();
    public  abstract String getUdf198_Value();
    public  abstract String getUdf199_Value();
    public  abstract String getUdf200_Value();
    
    public  abstract String getUdf201_Value();
    public  abstract String getUdf202_Value();
    public  abstract String getUdf203_Value();
    public  abstract String getUdf204_Value();
    public  abstract String getUdf205_Value();
    public  abstract String getUdf206_Value();
    public  abstract String getUdf207_Value();
    public  abstract String getUdf208_Value();
    public  abstract String getUdf209_Value();
    public  abstract String getUdf210_Value();
    public  abstract String getUdf211_Value();
    public  abstract String getUdf212_Value();
    public  abstract String getUdf213_Value();
    public  abstract String getUdf214_Value();
    public  abstract String getUdf215_Value();
    public  abstract String getUdf116_Flag();
    public  abstract String getUdf117_Flag();
    public  abstract String getUdf118_Flag();
    public  abstract String getUdf119_Flag();
    public  abstract String getUdf120_Flag();
    public  abstract String getUdf121_Flag();
    public  abstract String getUdf122_Flag();
    public  abstract String getUdf123_Flag();
    public  abstract String getUdf124_Flag();
    public  abstract String getUdf125_Flag();
    public  abstract String getUdf126_Flag();
    public  abstract String getUdf127_Flag();
    public  abstract String getUdf128_Flag();
    public  abstract String getUdf129_Flag();
    public  abstract String getUdf130_Flag();
    public  abstract String getUdf131_Flag();
    public  abstract String getUdf132_Flag();
    public  abstract String getUdf133_Flag();
    public  abstract String getUdf134_Flag();
    public  abstract String getUdf135_Flag();
    public  abstract String getUdf136_Flag();
    public  abstract String getUdf137_Flag();
    public  abstract String getUdf138_Flag();
    public  abstract String getUdf139_Flag();
    public  abstract String getUdf140_Flag();
    public  abstract String getUdf141_Flag();
    public  abstract String getUdf142_Flag();
    public  abstract String getUdf143_Flag();
    public  abstract String getUdf144_Flag();
    public  abstract String getUdf145_Flag();
    public  abstract String getUdf146_Flag();
    public  abstract String getUdf147_Flag();
    public  abstract String getUdf148_Flag();
    public  abstract String getUdf149_Flag();
    public  abstract String getUdf150_Flag();


    public  abstract String getUdf151_Flag();
    public  abstract String getUdf152_Flag();
    public  abstract String getUdf153_Flag();
    public  abstract String getUdf154_Flag();
    public  abstract String getUdf155_Flag();
    public  abstract String getUdf156_Flag();
    public  abstract String getUdf157_Flag();
    public  abstract String getUdf158_Flag();
    public  abstract String getUdf159_Flag();
    public  abstract String getUdf160_Flag();
    public  abstract String getUdf161_Flag();
    public  abstract String getUdf162_Flag();
    public  abstract String getUdf163_Flag();
    public  abstract String getUdf164_Flag();
    public  abstract String getUdf165_Flag();
    public  abstract String getUdf166_Flag();
    public  abstract String getUdf167_Flag();
    public  abstract String getUdf168_Flag();
    public  abstract String getUdf169_Flag();
    public  abstract String getUdf170_Flag();
    public  abstract String getUdf171_Flag();
    public  abstract String getUdf172_Flag();
    public  abstract String getUdf173_Flag();
    public  abstract String getUdf174_Flag();
    public  abstract String getUdf175_Flag();
    public  abstract String getUdf176_Flag();
    public  abstract String getUdf177_Flag();
    public  abstract String getUdf178_Flag();
    public  abstract String getUdf179_Flag();
    public  abstract String getUdf180_Flag();
    public  abstract String getUdf181_Flag();
    public  abstract String getUdf182_Flag();
    public  abstract String getUdf183_Flag();
    public  abstract String getUdf184_Flag();
    public  abstract String getUdf185_Flag();
    public  abstract String getUdf186_Flag();
    public  abstract String getUdf187_Flag();
    public  abstract String getUdf188_Flag();
    public  abstract String getUdf189_Flag();
    public  abstract String getUdf190_Flag();
    public  abstract String getUdf191_Flag();
    public  abstract String getUdf192_Flag();
    public  abstract String getUdf193_Flag();
    public  abstract String getUdf194_Flag();
    public  abstract String getUdf195_Flag();
    public  abstract String getUdf196_Flag();
    public  abstract String getUdf197_Flag();
    public  abstract String getUdf198_Flag();
    public  abstract String getUdf199_Flag();
    public  abstract String getUdf200_Flag();
    
    public  abstract String getUdf201_Flag();;
    public  abstract String getUdf202_Flag();;
    public  abstract String getUdf203_Flag();;
    public  abstract String getUdf204_Flag();;
    public  abstract String getUdf205_Flag();;
    public  abstract String getUdf206_Flag();;
    public  abstract String getUdf207_Flag();;
    public  abstract String getUdf208_Flag();;
    public  abstract String getUdf209_Flag();;
    public  abstract String getUdf210_Flag();;
    public  abstract String getUdf211_Flag();;
    public  abstract String getUdf212_Flag();;
    public  abstract String getUdf213_Flag();;
    public  abstract String getUdf214_Flag();;
    public  abstract String getUdf215_Flag();;

    public  abstract void setUdf116_Label(String udf116_Label);
    public  abstract void setUdf117_Label(String udf117_Label);
    public  abstract void setUdf118_Label(String udf118_Label);
    public  abstract void setUdf119_Label(String udf119_Label);
    public  abstract void setUdf120_Label(String udf120_Label);
    public  abstract void setUdf121_Label(String udf121_Label);
    public  abstract void setUdf122_Label(String udf122_Label);
    public  abstract void setUdf123_Label(String udf123_Label);
    public  abstract void setUdf124_Label(String udf124_Label);
    public  abstract void setUdf125_Label(String udf125_Label);
    public  abstract void setUdf126_Label(String udf126_Label);
    public  abstract void setUdf127_Label(String udf127_Label);
    public  abstract void setUdf128_Label(String udf128_Label);
    public  abstract void setUdf129_Label(String udf129_Label);
    public  abstract void setUdf130_Label(String udf130_Label);
    public  abstract void setUdf131_Label(String udf131_Label);
    public  abstract void setUdf132_Label(String udf132_Label);
    public  abstract void setUdf133_Label(String udf133_Label);
    public  abstract void setUdf134_Label(String udf134_Label);
    public  abstract void setUdf135_Label(String udf135_Label);
    public  abstract void setUdf136_Label(String udf136_Label);
    public  abstract void setUdf137_Label(String udf137_Label);
    public  abstract void setUdf138_Label(String udf138_Label);
    public  abstract void setUdf139_Label(String udf139_Label);
    public  abstract void setUdf140_Label(String udf140_Label);
    public  abstract void setUdf141_Label(String udf141_Label);
    public  abstract void setUdf142_Label(String udf142_Label);
    public  abstract void setUdf143_Label(String udf143_Label);
    public  abstract void setUdf144_Label(String udf144_Label);
    public  abstract void setUdf145_Label(String udf145_Label);
    public  abstract void setUdf146_Label(String udf146_Label);
    public  abstract void setUdf147_Label(String udf147_Label);
    public  abstract void setUdf148_Label(String udf148_Label);
    public  abstract void setUdf149_Label(String udf149_Label);
    public  abstract void setUdf150_Label(String udf150_Label);
    public  abstract void setUdf151_Label(String udf151_Label);
    public  abstract void setUdf152_Label(String udf152_Label);
    public  abstract void setUdf153_Label(String udf153_Label);
    public  abstract void setUdf154_Label(String udf154_Label);
    public  abstract void setUdf155_Label(String udf155_Label);
    public  abstract void setUdf156_Label(String udf156_Label);
    public  abstract void setUdf157_Label(String udf157_Label);
    public  abstract void setUdf158_Label(String udf158_Label);
    public  abstract void setUdf159_Label(String udf159_Label);
    public  abstract void setUdf160_Label(String udf160_Label);
    public  abstract void setUdf161_Label(String udf161_Label);
    public  abstract void setUdf162_Label(String udf162_Label);
    public  abstract void setUdf163_Label(String udf163_Label);
    public  abstract void setUdf164_Label(String udf164_Label);
    public  abstract void setUdf165_Label(String udf165_Label);

    public  abstract void setUdf166_Label(String udf166_Label);
    public  abstract void setUdf167_Label(String udf167_Label);
    public  abstract void setUdf168_Label(String udf168_Label);
    public  abstract void setUdf169_Label(String udf169_Label);
    public  abstract void setUdf170_Label(String udf170_Label);
    public  abstract void setUdf171_Label(String udf171_Label);
    public  abstract void setUdf172_Label(String udf172_Label);
    public  abstract void setUdf173_Label(String udf173_Label);
    public  abstract void setUdf174_Label(String udf174_Label);
    public  abstract void setUdf175_Label(String udf175_Label);
    public  abstract void setUdf176_Label(String udf176_Label);
    public  abstract void setUdf177_Label(String udf177_Label);
    public  abstract void setUdf178_Label(String udf178_Label);
    public  abstract void setUdf179_Label(String udf179_Label);
    public  abstract void setUdf180_Label(String udf180_Label);
    public  abstract void setUdf181_Label(String udf181_Label);
    public  abstract void setUdf182_Label(String udf182_Label);
    public  abstract void setUdf183_Label(String udf183_Label);
    public  abstract void setUdf184_Label(String udf184_Label);
    public  abstract void setUdf185_Label(String udf185_Label);
    public  abstract void setUdf186_Label(String udf186_Label);
    public  abstract void setUdf187_Label(String udf187_Label);
    public  abstract void setUdf188_Label(String udf188_Label);
    public  abstract void setUdf189_Label(String udf189_Label);
    public  abstract void setUdf190_Label(String udf190_Label);
    public  abstract void setUdf191_Label(String udf191_Label);
    public  abstract void setUdf192_Label(String udf192_Label);
    public  abstract void setUdf193_Label(String udf193_Label);
    public  abstract void setUdf194_Label(String udf194_Label);
    public  abstract void setUdf195_Label(String udf195_Label);
    public  abstract void setUdf196_Label(String udf196_Label);
    public  abstract void setUdf197_Label(String udf197_Label);
    public  abstract void setUdf198_Label(String udf198_Label);
    public  abstract void setUdf199_Label(String udf199_Label);
    public  abstract void setUdf200_Label(String udf200_Label);

    public  abstract void setUdf201_Label(String udf201_Label);
    public  abstract void setUdf202_Label(String udf202_Label);
    public  abstract void setUdf203_Label(String udf203_Label);
    public  abstract void setUdf204_Label(String udf204_Label);
    public  abstract void setUdf205_Label(String udf205_Label);
    public  abstract void setUdf206_Label(String udf206_Label);
    public  abstract void setUdf207_Label(String udf207_Label); 
    public  abstract void setUdf208_Label(String udf208_Label);
    public  abstract void setUdf209_Label(String udf209_Label);
    public  abstract void setUdf210_Label(String udf210_Label);
    public  abstract void setUdf211_Label(String udf211_Label);
    public  abstract void setUdf212_Label(String udf212_Label);
    public  abstract void setUdf213_Label(String udf213_Label);
    public  abstract void setUdf214_Label(String udf214_Label);
    public  abstract void setUdf215_Label(String udf215_Label);
  
	public  abstract void setUdf116_Value(String udf116_Value);
	public  abstract void setUdf117_Value(String udf117_Value);
	public  abstract void setUdf118_Value(String udf118_Value);
	public  abstract void setUdf119_Value(String udf119_Value);
	public  abstract void setUdf120_Value(String udf120_Value);
	public  abstract void setUdf121_Value(String udf121_Value);
	public  abstract void setUdf122_Value(String udf122_Value);
	public  abstract void setUdf123_Value(String udf123_Value);
	public  abstract void setUdf124_Value(String udf124_Value);
	public  abstract void setUdf125_Value(String udf125_Value);
	public  abstract void setUdf126_Value(String udf126_Value);
	public  abstract void setUdf127_Value(String udf127_Value);
	public  abstract void setUdf128_Value(String udf128_Value);
	public  abstract void setUdf129_Value(String udf129_Value);
	public  abstract void setUdf130_Value(String udf130_Value);
	public  abstract void setUdf131_Value(String udf131_Value);
	public  abstract void setUdf132_Value(String udf132_Value);
	public  abstract void setUdf133_Value(String udf133_Value);
	public  abstract void setUdf134_Value(String udf134_Value);
	public  abstract void setUdf135_Value(String udf135_Value);
	public  abstract void setUdf136_Value(String udf136_Value);
	public  abstract void setUdf137_Value(String udf137_Value);
	public  abstract void setUdf138_Value(String udf138_Value);
	public  abstract void setUdf139_Value(String udf139_Value);
	public  abstract void setUdf140_Value(String udf140_Value);
	public  abstract void setUdf141_Value(String udf141_Value);
	public  abstract void setUdf142_Value(String udf142_Value);
	public  abstract void setUdf143_Value(String udf143_Value);
	public  abstract void setUdf144_Value(String udf144_Value);
	public  abstract void setUdf145_Value(String udf145_Value);
	public  abstract void setUdf146_Value(String udf146_Value);
	public  abstract void setUdf147_Value(String udf147_Value);
	public  abstract void setUdf148_Value(String udf148_Value);
	public  abstract void setUdf149_Value(String udf149_Value);
	public  abstract void setUdf150_Value(String udf150_Value);
	
	public  abstract void setUdf151_Value(String udf151_Value);
	public  abstract void setUdf152_Value(String udf152_Value);
	public  abstract void setUdf153_Value(String udf153_Value);
	public  abstract void setUdf154_Value(String udf154_Value);
	public  abstract void setUdf155_Value(String udf155_Value);
	public  abstract void setUdf156_Value(String udf156_Value);
	public  abstract void setUdf157_Value(String udf157_Value);
	public  abstract void setUdf158_Value(String udf158_Value);
	public  abstract void setUdf159_Value(String udf159_Value);
	public  abstract void setUdf160_Value(String udf160_Value);
	public  abstract void setUdf161_Value(String udf161_Value);
	public  abstract void setUdf162_Value(String udf162_Value);
	public  abstract void setUdf163_Value(String udf163_Value);
	public  abstract void setUdf164_Value(String udf164_Value);
	public  abstract void setUdf165_Value(String udf165_Value);	
	public  abstract void setUdf166_Value(String udf166_Value);
	public  abstract void setUdf167_Value(String udf167_Value);
	public  abstract void setUdf168_Value(String udf168_Value);
	public  abstract void setUdf169_Value(String udf169_Value);
	public  abstract void setUdf170_Value(String udf170_Value);
	public  abstract void setUdf171_Value(String udf171_Value);
	public  abstract void setUdf172_Value(String udf172_Value);
	public  abstract void setUdf173_Value(String udf173_Value);
	public  abstract void setUdf174_Value(String udf174_Value);
	public  abstract void setUdf175_Value(String udf175_Value);
	public  abstract void setUdf176_Value(String udf176_Value);
	public  abstract void setUdf177_Value(String udf177_Value);
	public  abstract void setUdf178_Value(String udf178_Value);
	public  abstract void setUdf179_Value(String udf179_Value);
	public  abstract void setUdf180_Value(String udf180_Value);
	public  abstract void setUdf181_Value(String udf181_Value);
	public  abstract void setUdf182_Value(String udf182_Value);
	public  abstract void setUdf183_Value(String udf183_Value);
	public  abstract void setUdf184_Value(String udf184_Value);
	public  abstract void setUdf185_Value(String udf185_Value);
	public  abstract void setUdf186_Value(String udf186_Value);
	public  abstract void setUdf187_Value(String udf187_Value);
	public  abstract void setUdf188_Value(String udf188_Value);
	public  abstract void setUdf189_Value(String udf189_Value);
	public  abstract void setUdf190_Value(String udf190_Value);
	public  abstract void setUdf191_Value(String udf191_Value);
	public  abstract void setUdf192_Value(String udf192_Value);
	public  abstract void setUdf193_Value(String udf193_Value);
	public  abstract void setUdf194_Value(String udf194_Value);
	public  abstract void setUdf195_Value(String udf195_Value);
	public  abstract void setUdf196_Value(String udf196_Value);
	public  abstract void setUdf197_Value(String udf197_Value);
	public  abstract void setUdf198_Value(String udf198_Value);
	public  abstract void setUdf199_Value(String udf199_Value);
	public  abstract void setUdf200_Value(String udf200_Value);

	public  abstract void setUdf201_Value(String udf201_Value);
	public  abstract void setUdf202_Value(String udf202_Value);
	public  abstract void setUdf203_Value(String udf203_Value);
	public  abstract void setUdf204_Value(String udf204_Value);
	public  abstract void setUdf205_Value(String udf205_Value);
	public  abstract void setUdf206_Value(String udf206_Value);
	public  abstract void setUdf207_Value(String udf207_Value); 
	public  abstract void setUdf208_Value(String udf208_Value);
	public  abstract void setUdf209_Value(String udf209_Value);
	public  abstract void setUdf210_Value(String udf210_Value);
	public  abstract void setUdf211_Value(String udf211_Value);
	public  abstract void setUdf212_Value(String udf212_Value);
	public  abstract void setUdf213_Value(String udf213_Value);
	public  abstract void setUdf214_Value(String udf214_Value);
	public  abstract void setUdf215_Value(String udf215_Value);
	    
	public  abstract void setUdf116_Flag(String udf116_Flag);
	public  abstract void setUdf117_Flag(String udf117_Flag);
	public  abstract void setUdf118_Flag(String udf118_Flag);
	public  abstract void setUdf119_Flag(String udf119_Flag);
	public  abstract void setUdf120_Flag(String udf120_Flag);
	public  abstract void setUdf121_Flag(String udf121_Flag);
	public  abstract void setUdf122_Flag(String udf122_Flag);
	public  abstract void setUdf123_Flag(String udf123_Flag);
	public  abstract void setUdf124_Flag(String udf124_Flag);
	public  abstract void setUdf125_Flag(String udf125_Flag);
	public  abstract void setUdf126_Flag(String udf126_Flag);
	public  abstract void setUdf127_Flag(String udf127_Flag);
	public  abstract void setUdf128_Flag(String udf128_Flag);
	public  abstract void setUdf129_Flag(String udf129_Flag);
	public  abstract void setUdf130_Flag(String udf130_Flag);
	public  abstract void setUdf131_Flag(String udf131_Flag);
	public  abstract void setUdf132_Flag(String udf132_Flag);
	public  abstract void setUdf133_Flag(String udf133_Flag);
	public  abstract void setUdf134_Flag(String udf134_Flag);
	public  abstract void setUdf135_Flag(String udf135_Flag);
	public  abstract void setUdf136_Flag(String udf136_Flag);
	public  abstract void setUdf137_Flag(String udf137_Flag);
	public  abstract void setUdf138_Flag(String udf138_Flag);
	public  abstract void setUdf139_Flag(String udf139_Flag);
	public  abstract void setUdf140_Flag(String udf140_Flag);
	public  abstract void setUdf141_Flag(String udf141_Flag);
	public  abstract void setUdf142_Flag(String udf142_Flag);
	public  abstract void setUdf143_Flag(String udf143_Flag);
	public  abstract void setUdf144_Flag(String udf144_Flag);
	public  abstract void setUdf145_Flag(String udf145_Flag);
	public  abstract void setUdf146_Flag(String udf146_Flag);
	public  abstract void setUdf147_Flag(String udf147_Flag);
	public  abstract void setUdf148_Flag(String udf148_Flag);
	public  abstract void setUdf149_Flag(String udf149_Flag);
	public  abstract void setUdf150_Flag(String udf150_Flag);
	public  abstract void setUdf151_Flag(String udf151_Flag);
	public  abstract void setUdf152_Flag(String udf152_Flag);
	public  abstract void setUdf153_Flag(String udf153_Flag);
	public  abstract void setUdf154_Flag(String udf154_Flag);
	public  abstract void setUdf155_Flag(String udf155_Flag);
	public  abstract void setUdf156_Flag(String udf156_Flag);
	public  abstract void setUdf157_Flag(String udf157_Flag);
	public  abstract void setUdf158_Flag(String udf158_Flag);
	public  abstract void setUdf159_Flag(String udf159_Flag);
	public  abstract void setUdf160_Flag(String udf160_Flag);
	public  abstract void setUdf161_Flag(String udf161_Flag);
	public  abstract void setUdf162_Flag(String udf162_Flag);
	public  abstract void setUdf163_Flag(String udf163_Flag);
	public  abstract void setUdf164_Flag(String udf164_Flag);
	public  abstract void setUdf165_Flag(String udf165_Flag);
	
	public  abstract void setUdf166_Flag(String udf166_Flag);
	public  abstract void setUdf167_Flag(String udf167_Flag);
	public  abstract void setUdf168_Flag(String udf168_Flag);
	public  abstract void setUdf169_Flag(String udf169_Flag);
	public  abstract void setUdf170_Flag(String udf170_Flag);
	public  abstract void setUdf171_Flag(String udf171_Flag);
	public  abstract void setUdf172_Flag(String udf172_Flag);
	public  abstract void setUdf173_Flag(String udf173_Flag);
	public  abstract void setUdf174_Flag(String udf174_Flag);
	public  abstract void setUdf175_Flag(String udf175_Flag);
	public  abstract void setUdf176_Flag(String udf176_Flag);
	public  abstract void setUdf177_Flag(String udf177_Flag);
	public  abstract void setUdf178_Flag(String udf178_Flag);
	public  abstract void setUdf179_Flag(String udf179_Flag);
	public  abstract void setUdf180_Flag(String udf180_Flag);
	public  abstract void setUdf181_Flag(String udf181_Flag);
	public  abstract void setUdf182_Flag(String udf182_Flag);
	public  abstract void setUdf183_Flag(String udf183_Flag);
	public  abstract void setUdf184_Flag(String udf184_Flag);
	public  abstract void setUdf185_Flag(String udf185_Flag);
	public  abstract void setUdf186_Flag(String udf186_Flag);
	public  abstract void setUdf187_Flag(String udf187_Flag);
	public  abstract void setUdf188_Flag(String udf188_Flag);
	public  abstract void setUdf189_Flag(String udf189_Flag);
	public  abstract void setUdf190_Flag(String udf190_Flag);
	public  abstract void setUdf191_Flag(String udf191_Flag);
	public  abstract void setUdf192_Flag(String udf192_Flag);
	public  abstract void setUdf193_Flag(String udf193_Flag);
	public  abstract void setUdf194_Flag(String udf194_Flag);
	public  abstract void setUdf195_Flag(String udf195_Flag);
	public  abstract void setUdf196_Flag(String udf196_Flag);
	public  abstract void setUdf197_Flag(String udf197_Flag);
	public  abstract void setUdf198_Flag(String udf198_Flag);
	public  abstract void setUdf199_Flag(String udf199_Flag);
	public  abstract void setUdf200_Flag(String udf200_Flag);
  
	public  abstract void setUdf201_Flag(String udf201_Flag);
    public  abstract void setUdf202_Flag(String udf202_Flag);
	public  abstract void setUdf203_Flag(String udf203_Flag);
	public  abstract void setUdf204_Flag(String udf204_Flag);
	public  abstract void setUdf205_Flag(String udf205_Flag);
	public  abstract void setUdf206_Flag(String udf206_Flag);
	public  abstract void setUdf207_Flag(String udf207_Flag); 
	public  abstract void setUdf208_Flag(String udf208_Flag);
	public  abstract void setUdf209_Flag(String udf209_Flag);
	public  abstract void setUdf210_Flag(String udf210_Flag);
	public  abstract void setUdf211_Flag(String udf211_Flag);
	public  abstract void setUdf212_Flag(String udf212_Flag);
	public  abstract void setUdf213_Flag(String udf213_Flag);
	public  abstract void setUdf214_Flag(String udf214_Flag);
	public  abstract void setUdf215_Flag(String udf215_Flag);
    
	/*public ILimitXRefUdf[] getUdfData() {
		return null;
	}
	public void setUdfData(ILimitXRefUdf[] udfData) {
		
	}*/
}
