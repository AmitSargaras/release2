package com.integrosys.cms.app.collateral.bus.type.property;


public interface IPropertyValuation2Dao {

	static final String PROPERTY_VALUATION2 = "propertyValuation2";

	IPropertyValuation2 createPropertyValuation2(IPropertyValuation2 iPropertyValuation2);
	void updatePropertyValuation2(IPropertyValuation2 iPropertyValuation2);
	IPropertyValuation2 getPropertyValuation2(Long preValId);
	

}
