package com.integrosys.cms.app.collateral.bus.type.property;


public interface IPropertyValuation3Dao {

	static final String PROPERTY_VALUATION3 = "propertyValuation3";

	IPropertyValuation3 createPropertyValuation3(IPropertyValuation3 iPropertyValuation3);
	void updatePropertyValuation3(IPropertyValuation3 iPropertyValuation3);
	IPropertyValuation3 getPropertyValuation3(Long preValId);

}
