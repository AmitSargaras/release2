package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.asset.PlantEquipDetail;

/**
 * A search criteria represent the plant and equipment detail
 * 
 * @author Chong Jun Yong
 * 
 */
public class PlantEquipDetailSearchItem extends PlantEquipDetail {

	private static final long serialVersionUID = -402238635573868727L;

	private StandardCode plantEquipType;

	private StandardCode model;

	private Long manufactureYear;

	public StandardCode getPlantEquipType() {
		return plantEquipType;
	}

	public StandardCode getModel() {
		return model;
	}

	public void setPlantEquipType(StandardCode plantEquipType) {
		this.plantEquipType = plantEquipType;
	}

	public void setModel(StandardCode model) {
		this.model = model;
	}

	public Long getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(Long manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

}
