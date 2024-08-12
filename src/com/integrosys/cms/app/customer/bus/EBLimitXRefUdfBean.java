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

public abstract class EBLimitXRefUdfBean implements EntityBean, ILimitXRefUdf {
	
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_LIMIT_XREF_UDF;
	private static final String[] EXCLUDE_METHOD = new String[] { "getId", "getXRefID" };
	
	protected EntityContext _context = null;

	

	public long getId() {
		if (null != getUdfLimitPK()) {
			return getUdfLimitPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	public void setId(long value) {
		setUdfLimitPK(new Long(value));
	}
		
	public long getXRefID() {
		if (null != getLimitXRefIdFK()) {
			return getLimitXRefIdFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setXRefID(long value) {
		setLimitXRefIdFK(new Long(value));
	}
	
	public ILimitXRefUdf getValue() {
		ILimitXRefUdf value = new OBLimitXRefUdf();
		AccessorUtil.copyValue(this, value);
		return value;
	}
	
	public Long ejbCreate(ILimitXRefUdf value) throws CreateException {
		if (null == value) {
			throw new CreateException("ILimitXRefUdf is null!");
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
	
	public void setValue(ILimitXRefUdf value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}
	
	public void ejbPostCreate(ILimitXRefUdf value) {}
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
	
	public abstract Long getUdfLimitPK();
	public abstract void setUdfLimitPK(Long value);
	
	public abstract Long getLimitXRefIdFK();
	public abstract void setLimitXRefIdFK(Long value);
	
	public abstract String getUdf1_Label();
	public abstract String getUdf2_Label();
	public abstract String getUdf3_Label();
	public abstract String getUdf4_Label();
	public abstract String getUdf5_Label();
	public abstract String getUdf6_Label();
	public abstract String getUdf7_Label();
	public abstract String getUdf8_Label();
	public abstract String getUdf9_Label();
	public abstract String getUdf10_Label();
	public abstract String getUdf11_Label();
	public abstract String getUdf12_Label();
	public abstract String getUdf13_Label();
	public abstract String getUdf14_Label();
	public abstract String getUdf15_Label();
	public abstract String getUdf16_Label();
	public abstract String getUdf17_Label();
	public abstract String getUdf18_Label();
	public abstract String getUdf19_Label();
	public abstract String getUdf20_Label();
	public abstract String getUdf21_Label();
	public abstract String getUdf22_Label();
	public abstract String getUdf23_Label();
	public abstract String getUdf24_Label();
	public abstract String getUdf25_Label();
	public abstract String getUdf26_Label();
	public abstract String getUdf27_Label();
	public abstract String getUdf28_Label();
	public abstract String getUdf29_Label();
	public abstract String getUdf30_Label();
	public abstract String getUdf31_Label();
	public abstract String getUdf32_Label();
	public abstract String getUdf33_Label();
	public abstract String getUdf34_Label();
	public abstract String getUdf35_Label();
	public abstract String getUdf36_Label();
	public abstract String getUdf37_Label();
	public abstract String getUdf38_Label();
	public abstract String getUdf39_Label();
	public abstract String getUdf40_Label();
	public abstract String getUdf41_Label();
	public abstract String getUdf42_Label();
	public abstract String getUdf43_Label();
	public abstract String getUdf44_Label();
	public abstract String getUdf45_Label();
	public abstract String getUdf46_Label();
	public abstract String getUdf47_Label();
	public abstract String getUdf48_Label();
	public abstract String getUdf49_Label();
	public abstract String getUdf50_Label();
    public  abstract String getUdf51_Label();
    public  abstract String getUdf52_Label();
    public  abstract String getUdf53_Label();
    public  abstract String getUdf54_Label();
    public  abstract String getUdf55_Label();
    public  abstract String getUdf56_Label();
    public  abstract String getUdf57_Label();
    public  abstract String getUdf58_Label();
    public  abstract String getUdf59_Label();
    public  abstract String getUdf60_Label();
    public  abstract String getUdf61_Label();
    public  abstract String getUdf62_Label();
    public  abstract String getUdf63_Label();
    public  abstract String getUdf64_Label();
    public  abstract String getUdf65_Label();
    public  abstract String getUdf66_Label();
    public  abstract String getUdf67_Label();
    public  abstract String getUdf68_Label();
    public  abstract String getUdf69_Label();
    public  abstract String getUdf70_Label();
    public  abstract String getUdf71_Label();
    public  abstract String getUdf72_Label();
    public  abstract String getUdf73_Label();
    public  abstract String getUdf74_Label();
    public  abstract String getUdf75_Label();
    public  abstract String getUdf76_Label();
    public  abstract String getUdf77_Label();
    public  abstract String getUdf78_Label();
    public  abstract String getUdf79_Label();
    public  abstract String getUdf80_Label();
    public  abstract String getUdf81_Label();
    public  abstract String getUdf82_Label();
    public  abstract String getUdf83_Label();
    public  abstract String getUdf84_Label();
    public  abstract String getUdf85_Label();
    public  abstract String getUdf86_Label();
    public  abstract String getUdf87_Label();
    public  abstract String getUdf88_Label();
    public  abstract String getUdf89_Label();
    public  abstract String getUdf90_Label();
    public  abstract String getUdf91_Label();
    public  abstract String getUdf92_Label();
    public  abstract String getUdf93_Label();
    public  abstract String getUdf94_Label();
    public  abstract String getUdf95_Label();
    public  abstract String getUdf96_Label();
    public  abstract String getUdf97_Label();
    public  abstract String getUdf98_Label();
    public  abstract String getUdf99_Label();
    public  abstract String getUdf100_Label();
    
    public  abstract String getUdf101_Label();
    public  abstract String getUdf102_Label();
    public  abstract String getUdf103_Label();
    public  abstract String getUdf104_Label();
    public  abstract String getUdf105_Label();
    public  abstract String getUdf106_Label();
    public  abstract String getUdf107_Label();
    public  abstract String getUdf108_Label();
    public  abstract String getUdf109_Label();
    public  abstract String getUdf110_Label();
    public  abstract String getUdf111_Label();
    public  abstract String getUdf112_Label();
    public  abstract String getUdf113_Label();
    public  abstract String getUdf114_Label();
    public  abstract String getUdf115_Label();
     
    public abstract String getUdf1_Value();
	public abstract String getUdf2_Value();
	public abstract String getUdf3_Value();
	public abstract String getUdf4_Value();
	public abstract String getUdf5_Value();
	public abstract String getUdf6_Value();
	public abstract String getUdf7_Value();
	public abstract String getUdf8_Value();
	public abstract String getUdf9_Value();
	public abstract String getUdf10_Value();
	public abstract String getUdf11_Value();
	public abstract String getUdf12_Value();
	public abstract String getUdf13_Value();
	public abstract String getUdf14_Value();
	public abstract String getUdf15_Value();
	public abstract String getUdf16_Value();
	public abstract String getUdf17_Value();
	public abstract String getUdf18_Value();
	public abstract String getUdf19_Value();
	public abstract String getUdf20_Value();
	public abstract String getUdf21_Value();
	public abstract String getUdf22_Value();
	public abstract String getUdf23_Value();
	public abstract String getUdf24_Value();
	public abstract String getUdf25_Value();
	public abstract String getUdf26_Value();
	public abstract String getUdf27_Value();
	public abstract String getUdf28_Value();
	public abstract String getUdf29_Value();
	public abstract String getUdf30_Value();
	public abstract String getUdf31_Value();
	public abstract String getUdf32_Value();
	public abstract String getUdf33_Value();
	public abstract String getUdf34_Value();
	public abstract String getUdf35_Value();
	public abstract String getUdf36_Value();
	public abstract String getUdf37_Value();
	public abstract String getUdf38_Value();
	public abstract String getUdf39_Value();
	public abstract String getUdf40_Value();
	public abstract String getUdf41_Value();
	public abstract String getUdf42_Value();
	public abstract String getUdf43_Value();
	public abstract String getUdf44_Value();
	public abstract String getUdf45_Value();
	public abstract String getUdf46_Value();
	public abstract String getUdf47_Value();
	public abstract String getUdf48_Value();
	public abstract String getUdf49_Value();
	public abstract String getUdf50_Value();
    public  abstract String getUdf51_Value();
    public  abstract String getUdf52_Value();
    public  abstract String getUdf53_Value();
    public  abstract String getUdf54_Value();
    public  abstract String getUdf55_Value();
    public  abstract String getUdf56_Value();
    public  abstract String getUdf57_Value();
    public  abstract String getUdf58_Value();
    public  abstract String getUdf59_Value();
    public  abstract String getUdf60_Value();
    public  abstract String getUdf61_Value();
    public  abstract String getUdf62_Value();
    public  abstract String getUdf63_Value();
    public  abstract String getUdf64_Value();
    public  abstract String getUdf65_Value();
    public  abstract String getUdf66_Value();
    public  abstract String getUdf67_Value();
    public  abstract String getUdf68_Value();
    public  abstract String getUdf69_Value();
    public  abstract String getUdf70_Value();
    public  abstract String getUdf71_Value();
    public  abstract String getUdf72_Value();
    public  abstract String getUdf73_Value();
    public  abstract String getUdf74_Value();
    public  abstract String getUdf75_Value();
    public  abstract String getUdf76_Value();
    public  abstract String getUdf77_Value();
    public  abstract String getUdf78_Value();
    public  abstract String getUdf79_Value();
    public  abstract String getUdf80_Value();
    public  abstract String getUdf81_Value();
    public  abstract String getUdf82_Value();
    public  abstract String getUdf83_Value();
    public  abstract String getUdf84_Value();
    public  abstract String getUdf85_Value();
    public  abstract String getUdf86_Value();
    public  abstract String getUdf87_Value();
    public  abstract String getUdf88_Value();
    public  abstract String getUdf89_Value();
    public  abstract String getUdf90_Value();
    public  abstract String getUdf91_Value();
    public  abstract String getUdf92_Value();
    public  abstract String getUdf93_Value();
    public  abstract String getUdf94_Value();
    public  abstract String getUdf95_Value();
    public  abstract String getUdf96_Value();
    public  abstract String getUdf97_Value();
    public 	abstract String getUdf98_Value();
    public  abstract String getUdf99_Value();
    public  abstract String getUdf100_Value();
    
      public  abstract String getUdf101_Value();
    public  abstract String getUdf102_Value();
    public  abstract String getUdf103_Value();
    public  abstract String getUdf104_Value();
    public  abstract String getUdf105_Value();

     public  abstract String getUdf106_Value();
    public  abstract String getUdf107_Value();
    public  abstract String getUdf108_Value();
    public  abstract String getUdf109_Value();
    public  abstract String getUdf110_Value();
    public  abstract String getUdf111_Value();
    public  abstract String getUdf112_Value();
    public  abstract String getUdf113_Value();
    public  abstract String getUdf114_Value();
    public  abstract String getUdf115_Value();
   

    public abstract String getUdf1_Flag();
	public abstract String getUdf2_Flag();
	public abstract String getUdf3_Flag();
	public abstract String getUdf4_Flag();
	public abstract String getUdf5_Flag();
	public abstract String getUdf6_Flag();
	public abstract String getUdf7_Flag();
	public abstract String getUdf8_Flag();
	public abstract String getUdf9_Flag();
	public abstract String getUdf10_Flag();
	public abstract String getUdf11_Flag();
	public abstract String getUdf12_Flag();
	public abstract String getUdf13_Flag();
	public abstract String getUdf14_Flag();
	public abstract String getUdf15_Flag();
	public abstract String getUdf16_Flag();
	public abstract String getUdf17_Flag();
	public abstract String getUdf18_Flag();
	public abstract String getUdf19_Flag();
	public abstract String getUdf20_Flag();
	public abstract String getUdf21_Flag();
	public abstract String getUdf22_Flag();
	public abstract String getUdf23_Flag();
	public abstract String getUdf24_Flag();
	public abstract String getUdf25_Flag();
	public abstract String getUdf26_Flag();
	public abstract String getUdf27_Flag();
	public abstract String getUdf28_Flag();
	public abstract String getUdf29_Flag();
	public abstract String getUdf30_Flag();
	public abstract String getUdf31_Flag();
	public abstract String getUdf32_Flag();
	public abstract String getUdf33_Flag();
	public abstract String getUdf34_Flag();
	public abstract String getUdf35_Flag();
	public abstract String getUdf36_Flag();
	public abstract String getUdf37_Flag();
	public abstract String getUdf38_Flag();
	public abstract String getUdf39_Flag();
	public abstract String getUdf40_Flag();
	public abstract String getUdf41_Flag();
	public abstract String getUdf42_Flag();
	public abstract String getUdf43_Flag();
	public abstract String getUdf44_Flag();
	public abstract String getUdf45_Flag();
	public abstract String getUdf46_Flag();
	public abstract String getUdf47_Flag();
	public abstract String getUdf48_Flag();
	public abstract String getUdf49_Flag();
	public abstract String getUdf50_Flag();
    public  abstract String getUdf51_Flag();
    public  abstract String getUdf52_Flag();
    public  abstract String getUdf53_Flag();
    public  abstract String getUdf54_Flag();
    public  abstract String getUdf55_Flag();
    public  abstract String getUdf56_Flag();
    public  abstract String getUdf57_Flag();
    public  abstract String getUdf58_Flag();
    public  abstract String getUdf59_Flag();
    public  abstract String getUdf60_Flag();
    public  abstract String getUdf61_Flag();
    public  abstract String getUdf62_Flag();
    public  abstract String getUdf63_Flag();
    public  abstract String getUdf64_Flag();
    public  abstract String getUdf65_Flag();
    public  abstract String getUdf66_Flag();
    public  abstract String getUdf67_Flag();
    public  abstract String getUdf68_Flag();
    public  abstract String getUdf69_Flag();
    public  abstract String getUdf70_Flag();
    public  abstract String getUdf71_Flag();
    public  abstract String getUdf72_Flag();
    public  abstract String getUdf73_Flag();
    public  abstract String getUdf74_Flag();
    public  abstract String getUdf75_Flag();
    public  abstract String getUdf76_Flag();
    public  abstract String getUdf77_Flag();
    public  abstract String getUdf78_Flag();
    public  abstract String getUdf79_Flag();
    public  abstract String getUdf80_Flag();
    public  abstract String getUdf81_Flag();
    public  abstract String getUdf82_Flag();
    public  abstract String getUdf83_Flag();
    public  abstract String getUdf84_Flag();
    public  abstract String getUdf85_Flag();
    public  abstract String getUdf86_Flag();
    public  abstract String getUdf87_Flag();
    public  abstract String getUdf88_Flag();
    public  abstract String getUdf89_Flag();
    public  abstract String getUdf90_Flag();
    public  abstract String getUdf91_Flag();
    public  abstract String getUdf92_Flag();
    public  abstract String getUdf93_Flag();
    public  abstract String getUdf94_Flag();
    public  abstract String getUdf95_Flag();
    public  abstract String getUdf96_Flag();
    public  abstract String getUdf97_Flag();
    public  abstract String getUdf98_Flag();
    public  abstract String getUdf99_Flag();
    public  abstract String getUdf100_Flag();
    
       public  abstract String getUdf101_Flag();
    public  abstract String getUdf102_Flag();
    public  abstract String getUdf103_Flag();
    public  abstract String getUdf104_Flag();
    public  abstract String getUdf105_Flag();
    
     public  abstract String getUdf106_Flag();
    public  abstract String getUdf107_Flag();
    public  abstract String getUdf108_Flag();
    public  abstract String getUdf109_Flag();
    public  abstract String getUdf110_Flag();
    public  abstract String getUdf111_Flag();
    public  abstract String getUdf112_Flag();
    public  abstract String getUdf113_Flag();
    public  abstract String getUdf114_Flag();
    public  abstract String getUdf115_Flag();
   
    
    public abstract void setUdf1_Label(String udf_Label);
	public abstract void setUdf2_Label(String udf_Label);
	public abstract void setUdf3_Label(String udf_Label);
	public abstract void setUdf4_Label(String udf_Label);
	public abstract void setUdf5_Label(String udf_Label);
	public abstract void setUdf6_Label(String udf_Label);
	public abstract void setUdf7_Label(String udf_Label);
	public abstract void setUdf8_Label(String udf_Label);
	public abstract void setUdf9_Label(String udf_Label);
	public abstract void setUdf10_Label(String udf_Label);
	public abstract void setUdf11_Label(String udf_Label);
	public abstract void setUdf12_Label(String udf_Label);
	public abstract void setUdf13_Label(String udf_Label);
	public abstract void setUdf14_Label(String udf_Label);
	public abstract void setUdf15_Label(String udf_Label);
	public abstract void setUdf16_Label(String udf_Label);
	public abstract void setUdf17_Label(String udf_Label);
	public abstract void setUdf18_Label(String udf_Label);
	public abstract void setUdf19_Label(String udf_Label);
	public abstract void setUdf20_Label(String udf_Label);
	public abstract void setUdf21_Label(String udf_Label);
	public abstract void setUdf22_Label(String udf_Label);
	public abstract void setUdf23_Label(String udf_Label);
	public abstract void setUdf24_Label(String udf_Label);
	public abstract void setUdf25_Label(String udf_Label);
	public abstract void setUdf26_Label(String udf_Label);
	public abstract void setUdf27_Label(String udf_Label);
	public abstract void setUdf28_Label(String udf_Label);
	public abstract void setUdf29_Label(String udf_Label);
	public abstract void setUdf30_Label(String udf_Label);
	public abstract void setUdf31_Label(String udf_Label);
	public abstract void setUdf32_Label(String udf_Label);
	public abstract void setUdf33_Label(String udf_Label);
	public abstract void setUdf34_Label(String udf_Label);
	public abstract void setUdf35_Label(String udf_Label);
	public abstract void setUdf36_Label(String udf_Label);
	public abstract void setUdf37_Label(String udf_Label);
	public abstract void setUdf38_Label(String udf_Label);
	public abstract void setUdf39_Label(String udf_Label);
	public abstract void setUdf40_Label(String udf_Label);
	public abstract void setUdf41_Label(String udf_Label);
	public abstract void setUdf42_Label(String udf_Label);
	public abstract void setUdf43_Label(String udf_Label);
	public abstract void setUdf44_Label(String udf_Label);
	public abstract void setUdf45_Label(String udf_Label);
	public abstract void setUdf46_Label(String udf_Label);
	public abstract void setUdf47_Label(String udf_Label);
	public abstract void setUdf48_Label(String udf_Label);
	public abstract void setUdf49_Label(String udf_Label);
	public abstract void setUdf50_Label(String udf_Label);
    public  abstract void setUdf51_Label(String udf51_Label);
    public  abstract void setUdf52_Label(String udf52_Label);
    public  abstract void setUdf53_Label(String udf53_Label);
    public  abstract void setUdf54_Label(String udf54_Label);
    public  abstract void setUdf55_Label(String udf55_Label);
    public  abstract void setUdf56_Label(String udf56_Label);
    public  abstract void setUdf57_Label(String udf57_Label);
    public  abstract void setUdf58_Label(String udf58_Label);
    public  abstract void setUdf59_Label(String udf59_Label);
    public  abstract void setUdf60_Label(String udf60_Label);
    public  abstract void setUdf61_Label(String udf61_Label);
    public  abstract void setUdf62_Label(String udf62_Label);
    public  abstract void setUdf63_Label(String udf63_Label);
    public  abstract void setUdf64_Label(String udf64_Label);
    public  abstract void setUdf65_Label(String udf65_Label);
    public  abstract void setUdf66_Label(String udf66_Label);
    public  abstract void setUdf67_Label(String udf67_Label);
    public  abstract void setUdf68_Label(String udf68_Label);
    public  abstract void setUdf69_Label(String udf69_Label);
    public  abstract void setUdf70_Label(String udf70_Label);
    public  abstract void setUdf71_Label(String udf71_Label);
    public  abstract void setUdf72_Label(String udf72_Label);
    public  abstract void setUdf73_Label(String udf73_Label);
    public  abstract void setUdf74_Label(String udf74_Label);
    public  abstract void setUdf75_Label(String udf75_Label);
    public  abstract void setUdf76_Label(String udf76_Label);
    public  abstract void setUdf77_Label(String udf77_Label);
    public  abstract void setUdf78_Label(String udf78_Label);
    public  abstract void setUdf79_Label(String udf79_Label);
    public  abstract void setUdf80_Label(String udf80_Label);
    public  abstract void setUdf81_Label(String udf81_Label);
    public  abstract void setUdf82_Label(String udf82_Label);
    public  abstract void setUdf83_Label(String udf83_Label);
    public  abstract void setUdf84_Label(String udf84_Label);
    public  abstract void setUdf85_Label(String udf85_Label);
    public  abstract void setUdf86_Label(String udf86_Label);
    public  abstract void setUdf87_Label(String udf87_Label);
    public  abstract void setUdf88_Label(String udf88_Label);
    public  abstract void setUdf89_Label(String udf89_Label);
    public  abstract void setUdf90_Label(String udf90_Label);
    public  abstract void setUdf91_Label(String udf91_Label);
    public  abstract void setUdf92_Label(String udf92_Label);
    public  abstract void setUdf93_Label(String udf93_Label);
    public  abstract void setUdf94_Label(String udf94_Label);
    public  abstract void setUdf95_Label(String udf95_Label);
    public  abstract void setUdf96_Label(String udf96_Label);
    public  abstract void setUdf97_Label(String udf97_Label);
    public  abstract void setUdf98_Label(String udf98_Label);
    public  abstract void setUdf99_Label(String udf99_Label);
    public  abstract void setUdf100_Label(String udf100_Label);
   
    public  abstract void setUdf101_Label(String udf101_Label);
    public  abstract void setUdf102_Label(String udf102_Label);
    public  abstract void setUdf103_Label(String udf103_Label);
    public  abstract void setUdf104_Label(String udf104_Label);
    public  abstract void setUdf105_Label(String udf105_Label);
    public  abstract void setUdf106_Label(String udf106_Label);
    public  abstract void setUdf107_Label(String udf107_Label); 
    public  abstract void setUdf108_Label(String udf108_Label);
    public  abstract void setUdf109_Label(String udf109_Label);
    public  abstract void setUdf110_Label(String udf110_Label);
    public  abstract void setUdf111_Label(String udf111_Label);
    public  abstract void setUdf112_Label(String udf112_Label);
    public  abstract void setUdf113_Label(String udf113_Label);
    public  abstract void setUdf114_Label(String udf114_Label);
    public  abstract void setUdf115_Label(String udf115_Label);
   

    public abstract void setUdf1_Value(String udf_Value);
	public abstract void setUdf2_Value(String udf_Value);
	public abstract void setUdf3_Value(String udf_Value);
	public abstract void setUdf4_Value(String udf_Value);
	public abstract void setUdf5_Value(String udf_Value);
	public abstract void setUdf6_Value(String udf_Value);
	public abstract void setUdf7_Value(String udf_Value);
	public abstract void setUdf8_Value(String udf_Value);
	public abstract void setUdf9_Value(String udf_Value);
	public abstract void setUdf10_Value(String udf_Value);
	public abstract void setUdf11_Value(String udf_Value);
	public abstract void setUdf12_Value(String udf_Value);
	public abstract void setUdf13_Value(String udf_Value);
	public abstract void setUdf14_Value(String udf_Value);
	public abstract void setUdf15_Value(String udf_Value);
	public abstract void setUdf16_Value(String udf_Value);
	public abstract void setUdf17_Value(String udf_Value);
	public abstract void setUdf18_Value(String udf_Value);
	public abstract void setUdf19_Value(String udf_Value);
	public abstract void setUdf20_Value(String udf_Value);
	public abstract void setUdf21_Value(String udf_Value);
	public abstract void setUdf22_Value(String udf_Value);
	public abstract void setUdf23_Value(String udf_Value);
	public abstract void setUdf24_Value(String udf_Value);
	public abstract void setUdf25_Value(String udf_Value);
	public abstract void setUdf26_Value(String udf_Value);
	public abstract void setUdf27_Value(String udf_Value);
	public abstract void setUdf28_Value(String udf_Value);
	public abstract void setUdf29_Value(String udf_Value);
	public abstract void setUdf30_Value(String udf_Value);
	public abstract void setUdf31_Value(String udf_Value);
	public abstract void setUdf32_Value(String udf_Value);
	public abstract void setUdf33_Value(String udf_Value);
	public abstract void setUdf34_Value(String udf_Value);
	public abstract void setUdf35_Value(String udf_Value);
	public abstract void setUdf36_Value(String udf_Value);
	public abstract void setUdf37_Value(String udf_Value);
	public abstract void setUdf38_Value(String udf_Value);
	public abstract void setUdf39_Value(String udf_Value);
	public abstract void setUdf40_Value(String udf_Value);
	public abstract void setUdf41_Value(String udf_Value);
	public abstract void setUdf42_Value(String udf_Value);
	public abstract void setUdf43_Value(String udf_Value);
	public abstract void setUdf44_Value(String udf_Value);
	public abstract void setUdf45_Value(String udf_Value);
	public abstract void setUdf46_Value(String udf_Value);
	public abstract void setUdf47_Value(String udf_Value);
	public abstract void setUdf48_Value(String udf_Value);
	public abstract void setUdf49_Value(String udf_Value);
	public abstract void setUdf50_Value(String udf_Value);
    public  abstract void setUdf51_Value(String udf51_Value);
    public  abstract void setUdf52_Value(String udf52_Value);
    public  abstract void setUdf53_Value(String udf53_Value);
    public  abstract void setUdf54_Value(String udf54_Value);
    public  abstract void setUdf55_Value(String udf55_Value);
    public  abstract void setUdf56_Value(String udf56_Value);
    public  abstract void setUdf57_Value(String udf57_Value);
    public  abstract void setUdf58_Value(String udf58_Value);
    public  abstract void setUdf59_Value(String udf59_Value);
    public  abstract void setUdf60_Value(String udf60_Value);
    public  abstract void setUdf61_Value(String udf61_Value);
    public  abstract void setUdf62_Value(String udf62_Value);
    public  abstract void setUdf63_Value(String udf63_Value);
    public  abstract void setUdf64_Value(String udf64_Value);
    public  abstract void setUdf65_Value(String udf65_Value);
    public  abstract void setUdf66_Value(String udf66_Value);
    public  abstract void setUdf67_Value(String udf67_Value);
    public  abstract void setUdf68_Value(String udf68_Value);
    public  abstract void setUdf69_Value(String udf69_Value);
    public  abstract void setUdf70_Value(String udf70_Value);
    public  abstract void setUdf71_Value(String udf71_Value);
    public  abstract void setUdf72_Value(String udf72_Value);
    public  abstract void setUdf73_Value(String udf73_Value);
    public  abstract void setUdf74_Value(String udf74_Value);
    public  abstract void setUdf75_Value(String udf75_Value);
    public  abstract void setUdf76_Value(String udf76_Value);
    public  abstract void setUdf77_Value(String udf77_Value);
    public  abstract void setUdf78_Value(String udf78_Value);
    public  abstract void setUdf79_Value(String udf79_Value);
    public  abstract void setUdf80_Value(String udf80_Value);
    public  abstract void setUdf81_Value(String udf81_Value);
    public  abstract void setUdf82_Value(String udf82_Value);
    public  abstract void setUdf83_Value(String udf83_Value);
    public  abstract void setUdf84_Value(String udf84_Value);
    public  abstract void setUdf85_Value(String udf85_Value);
    public  abstract void setUdf86_Value(String udf86_Value);
    public  abstract void setUdf87_Value(String udf87_Value);
    public  abstract void setUdf88_Value(String udf88_Value);
    public  abstract void setUdf89_Value(String udf89_Value);
    public  abstract void setUdf90_Value(String udf90_Value);
    public  abstract void setUdf91_Value(String udf91_Value);
    public  abstract void setUdf92_Value(String udf92_Value);
    public  abstract void setUdf93_Value(String udf93_Value);
    public  abstract void setUdf94_Value(String udf94_Value);
    public  abstract void setUdf95_Value(String udf95_Value);
    public  abstract void setUdf96_Value(String udf96_Value);
    public  abstract void setUdf97_Value(String udf97_Value);
    public  abstract void setUdf98_Value(String udf98_Value);
    public  abstract void setUdf99_Value(String udf99_Value);
    public  abstract void setUdf100_Value(String udf100_Value);
    
    public  abstract void setUdf101_Value(String udf101_Value);
    public  abstract void setUdf102_Value(String udf102_Value);
    public  abstract void setUdf103_Value(String udf103_Value);
    public  abstract void setUdf104_Value(String udf104_Value);
    public  abstract void setUdf105_Value(String udf105_Value);
    public  abstract void setUdf106_Value(String udf106_Value);
	public  abstract void setUdf107_Value(String udf107_Value); 
	public  abstract void setUdf108_Value(String udf108_Value);
	public  abstract void setUdf109_Value(String udf109_Value);
	public  abstract void setUdf110_Value(String udf110_Value);
	public  abstract void setUdf111_Value(String udf111_Value);
	public  abstract void setUdf112_Value(String udf112_Value);
	public  abstract void setUdf113_Value(String udf113_Value);
	public  abstract void setUdf114_Value(String udf114_Value);
	public  abstract void setUdf115_Value(String udf115_Value);
	
     
    public abstract void setUdf1_Flag(String udf_Flag);
	public abstract void setUdf2_Flag(String udf_Flag);
	public abstract void setUdf3_Flag(String udf_Flag);
	public abstract void setUdf4_Flag(String udf_Flag);
	public abstract void setUdf5_Flag(String udf_Flag);
	public abstract void setUdf6_Flag(String udf_Flag);
	public abstract void setUdf7_Flag(String udf_Flag);
	public abstract void setUdf8_Flag(String udf_Flag);
	public abstract void setUdf9_Flag(String udf_Flag);
	public abstract void setUdf10_Flag(String udf_Flag);
	public abstract void setUdf11_Flag(String udf_Flag);
	public abstract void setUdf12_Flag(String udf_Flag);
	public abstract void setUdf13_Flag(String udf_Flag);
	public abstract void setUdf14_Flag(String udf_Flag);
	public abstract void setUdf15_Flag(String udf_Flag);
	public abstract void setUdf16_Flag(String udf_Flag);
	public abstract void setUdf17_Flag(String udf_Flag);
	public abstract void setUdf18_Flag(String udf_Flag);
	public abstract void setUdf19_Flag(String udf_Flag);
	public abstract void setUdf20_Flag(String udf_Flag);
	public abstract void setUdf21_Flag(String udf_Flag);
	public abstract void setUdf22_Flag(String udf_Flag);
	public abstract void setUdf23_Flag(String udf_Flag);
	public abstract void setUdf24_Flag(String udf_Flag);
	public abstract void setUdf25_Flag(String udf_Flag);
	public abstract void setUdf26_Flag(String udf_Flag);
	public abstract void setUdf27_Flag(String udf_Flag);
	public abstract void setUdf28_Flag(String udf_Flag);
	public abstract void setUdf29_Flag(String udf_Flag);
	public abstract void setUdf30_Flag(String udf_Flag);
	public abstract void setUdf31_Flag(String udf_Flag);
	public abstract void setUdf32_Flag(String udf_Flag);
	public abstract void setUdf33_Flag(String udf_Flag);
	public abstract void setUdf34_Flag(String udf_Flag);
	public abstract void setUdf35_Flag(String udf_Flag);
	public abstract void setUdf36_Flag(String udf_Flag);
	public abstract void setUdf37_Flag(String udf_Flag);
	public abstract void setUdf38_Flag(String udf_Flag);
	public abstract void setUdf39_Flag(String udf_Flag);
	public abstract void setUdf40_Flag(String udf_Flag);
	public abstract void setUdf41_Flag(String udf_Flag);
	public abstract void setUdf42_Flag(String udf_Flag);
	public abstract void setUdf43_Flag(String udf_Flag);
	public abstract void setUdf44_Flag(String udf_Flag);
	public abstract void setUdf45_Flag(String udf_Flag);
	public abstract void setUdf46_Flag(String udf_Flag);
	public abstract void setUdf47_Flag(String udf_Flag);
	public abstract void setUdf48_Flag(String udf_Flag);
	public abstract void setUdf49_Flag(String udf_Flag);
	public abstract void setUdf50_Flag(String udf_Flag);
    public  abstract void setUdf51_Flag(String udf51_Flag);
    public  abstract void setUdf52_Flag(String udf52_Flag);
    public  abstract void setUdf53_Flag(String udf53_Flag);
    public  abstract void setUdf54_Flag(String udf54_Flag);
    public  abstract void setUdf55_Flag(String udf55_Flag);
    public  abstract void setUdf56_Flag(String udf56_Flag);
    public  abstract void setUdf57_Flag(String udf57_Flag);
    public  abstract void setUdf58_Flag(String udf58_Flag);
    public  abstract void setUdf59_Flag(String udf59_Flag);
    public  abstract void setUdf60_Flag(String udf60_Flag);
    public  abstract void setUdf61_Flag(String udf61_Flag);
    public  abstract void setUdf62_Flag(String udf62_Flag);
    public  abstract void setUdf63_Flag(String udf63_Flag);
    public  abstract void setUdf64_Flag(String udf64_Flag);
    public  abstract void setUdf65_Flag(String udf65_Flag);
    public  abstract void setUdf66_Flag(String udf66_Flag);
    public  abstract void setUdf67_Flag(String udf67_Flag);
    public  abstract void setUdf68_Flag(String udf68_Flag);
    public  abstract void setUdf69_Flag(String udf69_Flag);
    public  abstract void setUdf70_Flag(String udf70_Flag);
    public  abstract void setUdf71_Flag(String udf71_Flag);
    public  abstract void setUdf72_Flag(String udf72_Flag);
    public  abstract void setUdf73_Flag(String udf73_Flag);
    public  abstract void setUdf74_Flag(String udf74_Flag);
    public  abstract void setUdf75_Flag(String udf75_Flag);
    public  abstract void setUdf76_Flag(String udf76_Flag);
    public  abstract void setUdf77_Flag(String udf77_Flag);
    public  abstract void setUdf78_Flag(String udf78_Flag);
    public  abstract void setUdf79_Flag(String udf79_Flag);
    public  abstract void setUdf80_Flag(String udf80_Flag);
    public  abstract void setUdf81_Flag(String udf81_Flag);
    public  abstract void setUdf82_Flag(String udf82_Flag);
    public  abstract void setUdf83_Flag(String udf83_Flag);
    public  abstract void setUdf84_Flag(String udf84_Flag);
    public  abstract void setUdf85_Flag(String udf85_Flag);
    public  abstract void setUdf86_Flag(String udf86_Flag);
    public  abstract void setUdf87_Flag(String udf87_Flag);
    public  abstract void setUdf88_Flag(String udf88_Flag);
    public  abstract void setUdf89_Flag(String udf89_Flag);
    public  abstract void setUdf90_Flag(String udf90_Flag);
    public  abstract void setUdf91_Flag(String udf91_Flag);
    public  abstract void setUdf92_Flag(String udf92_Flag);
    public  abstract void setUdf93_Flag(String udf93_Flag);
    public  abstract void setUdf94_Flag(String udf94_Flag);
    public  abstract void setUdf95_Flag(String udf95_Flag);
    public  abstract void setUdf96_Flag(String udf96_Flag);
    public  abstract void setUdf97_Flag(String udf97_Flag);
    public  abstract void setUdf98_Flag(String udf98_Flag);
    public  abstract void setUdf99_Flag(String udf99_Flag);
    public  abstract void setUdf100_Flag(String udf100_Flag);
    
    public  abstract void setUdf101_Flag(String udf101_Flag);
    public  abstract void setUdf102_Flag(String udf102_Flag);
    public  abstract void setUdf103_Flag(String udf103_Flag);
    public  abstract void setUdf104_Flag(String udf104_Flag);
    public  abstract void setUdf105_Flag(String udf105_Flag);
    public  abstract void setUdf106_Flag(String udf106_Flag);
	public  abstract void setUdf107_Flag(String udf107_Flag); 
	public  abstract void setUdf108_Flag(String udf108_Flag);
	public  abstract void setUdf109_Flag(String udf109_Flag);
	public  abstract void setUdf110_Flag(String udf110_Flag);
	public  abstract void setUdf111_Flag(String udf111_Flag);
	public  abstract void setUdf112_Flag(String udf112_Flag);
	public  abstract void setUdf113_Flag(String udf113_Flag);
	public  abstract void setUdf114_Flag(String udf114_Flag);
	public  abstract void setUdf115_Flag(String udf115_Flag);
	
    	/*public ILimitXRefUdf[] getUdfData() {
		return null;
	}
	public void setUdfData(ILimitXRefUdf[] udfData) {
		
	}*/
}
