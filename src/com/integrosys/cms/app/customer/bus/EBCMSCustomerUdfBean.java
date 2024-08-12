package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBCMSCustomerUdfBean implements ICMSCustomerUdf, EntityBean {

	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_UDF;
	private static final String[] EXCLUDE_METHOD = new String[] { "getId", "getLEID" };
	
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	public EBCMSCustomerUdfBean() {
	}
	
	
//	 ************* Non-persistent methods ***********

	public long getId() {
		if (null != getUdfInfoPK()) {
			return getUdfInfoPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	public void setId(long value) {
		setUdfInfoPK(new Long(value));
	}
	
	public long getLEID() {
		if (null != getCmsLeMainProfileIdFK()) {
			return getCmsLeMainProfileIdFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setLEID(long value) {
		setCmsLeMainProfileIdFK(new Long(value));
	}
	
	public abstract Long getCmsLeMainProfileIdFK();
	public abstract void setCmsLeMainProfileIdFK(Long value);

	
	// ************** Abstract methods ************

	public abstract Long getUdfInfoPK();
	public abstract void setUdfInfoPK(Long value);


	// *****************************************************
	
	/**
	 * Create a Contact Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the IContact object
	 * @return Long the contact ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICMSCustomerUdf value) throws CreateException {
		if (null == value) {
			throw new CreateException("ICMSCustomerUdf is null!");
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

	public void ejbPostCreate(ICMSCustomerUdf value) {}
	
	public ICMSCustomerUdf getValue() {
		ICMSCustomerUdf value = new OBCMSCustomerUdf();
		AccessorUtil.copyValue(this, value);
		return value;
	}
	
	public void setValue(ICMSCustomerUdf value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}
	
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

    public abstract String getUdf1();
    public abstract String getUdf2();
    public abstract String getUdf3();
    public abstract String getUdf4();
    public abstract String getUdf5();
    public abstract String getUdf6();
    public abstract String getUdf7();
    public abstract String getUdf8();
    public abstract String getUdf9();
    public abstract String getUdf10();
    public abstract String getUdf11();
    public abstract String getUdf12();
    public abstract String getUdf13();
    public abstract String getUdf14();
    public abstract String getUdf15();
    public abstract String getUdf16();
    public abstract String getUdf17();
    public abstract String getUdf18();
    public abstract String getUdf19();
    public abstract String getUdf20();
    public abstract String getUdf21();
    public abstract String getUdf22();
    public abstract String getUdf23();
    public abstract String getUdf24();
    public abstract String getUdf25();
    public abstract String getUdf26();
    public abstract String getUdf27();
    public abstract String getUdf28();
    public abstract String getUdf29();
    public abstract String getUdf30();
    public abstract String getUdf31();
    public abstract String getUdf32();
    public abstract String getUdf33();
    public abstract String getUdf34();
    public abstract String getUdf35();
    public abstract String getUdf36();
    public abstract String getUdf37();
    public abstract String getUdf38();
    public abstract String getUdf39();
    public abstract String getUdf40();
    public abstract String getUdf41();
    public abstract String getUdf42();
    public abstract String getUdf43();
    public abstract String getUdf44();
    public abstract String getUdf45();
    public abstract String getUdf46();
    public abstract String getUdf47();
    public abstract String getUdf48();
    public abstract String getUdf49();
    public abstract String getUdf50();
    public abstract void setUdf1(String udf1);
    public abstract void setUdf2(String udf2);
    public abstract void setUdf3(String udf3);
    public abstract void setUdf4(String udf4);
    public abstract void setUdf5(String udf5);
    public abstract void setUdf6(String udf6);
    public abstract void setUdf7(String udf7);
    public abstract void setUdf8(String udf8);
    public abstract void setUdf9(String udf9);
    public abstract void setUdf10(String udf10);
    public abstract void setUdf11(String udf11);
    public abstract void setUdf12(String udf12);
    public abstract void setUdf13(String udf13);
    public abstract void setUdf14(String udf14);
    public abstract void setUdf15(String udf15);
    public abstract void setUdf16(String udf16);
    public abstract void setUdf17(String udf17);
    public abstract void setUdf18(String udf18);
    public abstract void setUdf19(String udf19);
    public abstract void setUdf20(String udf20);
    public abstract void setUdf21(String udf21);
    public abstract void setUdf22(String udf22);
    public abstract void setUdf23(String udf23);
    public abstract void setUdf24(String udf24);
    public abstract void setUdf25(String udf25);
    public abstract void setUdf26(String udf26);
    public abstract void setUdf27(String udf27);
    public abstract void setUdf28(String udf28);
    public abstract void setUdf29(String udf29);
    public abstract void setUdf30(String udf30);
    public abstract void setUdf31(String udf31);
    public abstract void setUdf32(String udf32);
    public abstract void setUdf33(String udf33);
    public abstract void setUdf34(String udf34);
    public abstract void setUdf35(String udf35);
    public abstract void setUdf36(String udf36);
    public abstract void setUdf37(String udf37);
    public abstract void setUdf38(String udf38);
    public abstract void setUdf39(String udf39);
    public abstract void setUdf40(String udf40);
    public abstract void setUdf41(String udf41);
    public abstract void setUdf42(String udf42);
    public abstract void setUdf43(String udf43);
    public abstract void setUdf44(String udf44);
    public abstract void setUdf45(String udf45);
    public abstract void setUdf46(String udf46);
    public abstract void setUdf47(String udf47);
    public abstract void setUdf48(String udf48);
    public abstract void setUdf49(String udf49);
    public abstract void setUdf50(String udf50);

    public abstract String getUdf51();
    public abstract String getUdf52();
    public abstract String getUdf53();
    public abstract String getUdf54();
    public abstract String getUdf55();
    public abstract String getUdf56();
    public abstract String getUdf57();
    public abstract String getUdf58();
    public abstract String getUdf59();
    public abstract String getUdf60();
    public abstract String getUdf61();
    public abstract String getUdf62();
    public abstract String getUdf63();
    public abstract String getUdf64();
    public abstract String getUdf65();
    public abstract String getUdf66();
    public abstract String getUdf67();
    public abstract String getUdf68();
    public abstract String getUdf69();
    public abstract String getUdf70();
    public abstract String getUdf71();
    public abstract String getUdf72();
    public abstract String getUdf73();
    public abstract String getUdf74();
    public abstract String getUdf75();
    public abstract String getUdf76();
    public abstract String getUdf77();
    public abstract String getUdf78();
    public abstract String getUdf79();
    public abstract String getUdf80();
    public abstract String getUdf81();
    public abstract String getUdf82();
    public abstract String getUdf83();
    public abstract String getUdf84();
    public abstract String getUdf85();
    public abstract String getUdf86();
    public abstract String getUdf87();
    public abstract String getUdf88();
    public abstract String getUdf89();
    public abstract String getUdf90();
    public abstract String getUdf91();
    public abstract String getUdf92();
    public abstract String getUdf93();
    public abstract String getUdf94();
    public abstract String getUdf95();
    public abstract String getUdf96();
    public abstract String getUdf97();
    public abstract String getUdf98();
    public abstract String getUdf99();
    public abstract String getUdf100();
    public abstract String getUdf101();
    public abstract String getUdf102();
    public abstract String getUdf103();
    public abstract String getUdf104();
    public abstract String getUdf105();
    public abstract String getUdf106();
    public abstract String getUdf107();
    public abstract String getUdf108();
    public abstract String getUdf109();
    public abstract String getUdf110();
    public abstract String getUdf111();
    public abstract String getUdf112();
    public abstract String getUdf113();
    public abstract String getUdf114();
    public abstract String getUdf115();
    public abstract String getUdf116();
    public abstract String getUdf117();
    public abstract String getUdf118();
    public abstract String getUdf119();
    public abstract String getUdf120();
    
    public abstract void setUdf51(String udf51);
    public abstract void setUdf52(String udf52);
    public abstract void setUdf53(String udf53);
    public abstract void setUdf54(String udf54);
    public abstract void setUdf55(String udf55);
    public abstract void setUdf56(String udf56);
    public abstract void setUdf57(String udf57);
    public abstract void setUdf58(String udf58);
    public abstract void setUdf59(String udf59);
    public abstract void setUdf60(String udf60);
    public abstract void setUdf61(String udf61);
    public abstract void setUdf62(String udf62);
    public abstract void setUdf63(String udf63);
    public abstract void setUdf64(String udf64);
    public abstract void setUdf65(String udf65);
    public abstract void setUdf66(String udf66);
    public abstract void setUdf67(String udf67);
    public abstract void setUdf68(String udf68);
    public abstract void setUdf69(String udf69);
    public abstract void setUdf70(String udf70);
    public abstract void setUdf71(String udf71);
    public abstract void setUdf72(String udf72);
    public abstract void setUdf73(String udf73);
    public abstract void setUdf74(String udf74);
    public abstract void setUdf75(String udf75);
    public abstract void setUdf76(String udf76);
    public abstract void setUdf77(String udf77);
    public abstract void setUdf78(String udf78);
    public abstract void setUdf79(String udf79);
    public abstract void setUdf80(String udf80);
    public abstract void setUdf81(String udf81);
    public abstract void setUdf82(String udf82);
    public abstract void setUdf83(String udf83);
    public abstract void setUdf84(String udf84);
    public abstract void setUdf85(String udf85);
    public abstract void setUdf86(String udf86);
    public abstract void setUdf87(String udf87);
    public abstract void setUdf88(String udf88);
    public abstract void setUdf89(String udf89);
    public abstract void setUdf90(String udf90);
    public abstract void setUdf91(String udf91);
    public abstract void setUdf92(String udf92);
    public abstract void setUdf93(String udf93);
    public abstract void setUdf94(String udf94);
    public abstract void setUdf95(String udf95);
    public abstract void setUdf96(String udf96);
    public abstract void setUdf97(String udf97);
    public abstract void setUdf98(String udf98);
    public abstract void setUdf99(String udf99);
    public abstract void setUdf100(String udf100);
    public abstract void setUdf101(String udf101);
    public abstract void setUdf102(String udf102);
    public abstract void setUdf103(String udf103);
    public abstract void setUdf104(String udf104);
    public abstract void setUdf105(String udf105);
    public abstract void setUdf106(String udf106);
    public abstract void setUdf107(String udf107);
    public abstract void setUdf108(String udf108);
    public abstract void setUdf109(String udf109);
    public abstract void setUdf110(String udf110);
    public abstract void setUdf111(String udf111);
    public abstract void setUdf112(String udf112);
    public abstract void setUdf113(String udf113);
    public abstract void setUdf114(String udf114);
    public abstract void setUdf115(String udf115);
    public abstract void setUdf116(String udf116);
    public abstract void setUdf117(String udf117);
    public abstract void setUdf118(String udf118);
    public abstract void setUdf119(String udf119);
    public abstract void setUdf120(String udf120);

    
}
