package com.integrosys.cms.app.limit.bus.support;

import org.springframework.beans.factory.config.AbstractFactoryBean;

public class FacilityCodeBasedCollateralUpdateMetaInfoFactoryBean extends AbstractFactoryBean {

	private FacilityCodeBasedCollateralUpdateMetaInfo[] metainfos;

	public void setMetaInfos(FacilityCodeBasedCollateralUpdateMetaInfo[] metainfos) {
		this.metainfos = metainfos;
	}

	protected Object createInstance() throws Exception {
		return this.metainfos;
	}

	public Class getObjectType() {
		return FacilityCodeBasedCollateralUpdateMetaInfo[].class;
	}

}
