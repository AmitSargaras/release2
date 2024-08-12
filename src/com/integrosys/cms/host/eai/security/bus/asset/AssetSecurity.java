/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.host.eai.security.bus.asset;

import java.util.List;

import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;

/**
 * This class represents approved security of type Asset.
 * 
 * @author shphoon
 * @author Chong Jun Yong
 */
public class AssetSecurity extends ApprovedSecurity {

	private static final long serialVersionUID = -5969899797661399173L;

	private String assetDescription;

	private AirCraftDetail aircraftDetail;

	private GoldDetail goldDetail;

	private VehicleDetail vehicleDetail;

	private VesselDetail vesselDetail;

	private CommonChargeDetail commonChargeDetail;

	private SpecificChargeDetail specificChargeDetail;

	private PlantEquipDetail plantEquipDetail;

	private PostDatedCheque postDatedCheque;

	private ReceivableDetail receivableDetail;

	private List chequeDetail;

	private List tradeInInformation;

	/**
	 * Default constructor.
	 */
	public AssetSecurity() {
		super();
	}

	public AirCraftDetail getAircraftDetail() {
		return aircraftDetail;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public List getChequeDetail() {
		return chequeDetail;
	}

	public CommonChargeDetail getCommonChargeDetail() {
		return commonChargeDetail;
	}

	public GoldDetail getGoldDetail() {
		return goldDetail;
	}

	public PlantEquipDetail getPlantEquipDetail() {
		return plantEquipDetail;
	}

	public PostDatedCheque getPostDatedCheque() {
		return postDatedCheque;
	}

	public ReceivableDetail getReceivableDetail() {
		return receivableDetail;
	}

	public SpecificChargeDetail getSpecificChargeDetail() {
		return specificChargeDetail;
	}

	public List getTradeInInformation() {
		return tradeInInformation;
	}

	public VehicleDetail getVehicleDetail() {
		return vehicleDetail;
	}

	public VesselDetail getVesselDetail() {
		return vesselDetail;
	}

	public void setAircraftDetail(AirCraftDetail aircraftDetail) {
		this.aircraftDetail = aircraftDetail;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public void setChequeDetail(List chequeDetail) {
		this.chequeDetail = chequeDetail;
	}

	public void setCommonChargeDetail(CommonChargeDetail commonChargeDetail) {
		this.commonChargeDetail = commonChargeDetail;
	}

	public void setGoldDetail(GoldDetail goldDetail) {
		this.goldDetail = goldDetail;
	}

	public void setPlantEquipDetail(PlantEquipDetail plantEquipDetail) {
		this.plantEquipDetail = plantEquipDetail;
	}

	public void setPostDatedCheque(PostDatedCheque postDatedCheque) {
		this.postDatedCheque = postDatedCheque;
	}

	public void setReceivableDetail(ReceivableDetail receivableDetail) {
		this.receivableDetail = receivableDetail;
	}

	public void setSpecificChargeDetail(SpecificChargeDetail specificChargeDetail) {
		this.specificChargeDetail = specificChargeDetail;
	}

	public void setTradeInInformation(List tradeInInformation) {
		this.tradeInInformation = tradeInInformation;
	}

	public void setVehicleDetail(VehicleDetail vehicleDetail) {
		this.vehicleDetail = vehicleDetail;
	}

	public void setVesselDetail(VesselDetail vesselDetail) {
		this.vesselDetail = vesselDetail;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("AssetSecurity [");
		buf.append("assetDescription=");
		buf.append(assetDescription);
		buf.append(", commonChargeDetail=");
		buf.append(commonChargeDetail);
		buf.append(", specificChargeDetail=");
		buf.append(specificChargeDetail);
		buf.append(", vehicleDetail=");
		buf.append(vehicleDetail);
		buf.append(", tradeInInformation=");
		buf.append(tradeInInformation);
		buf.append(", plantEquipDetail=");
		buf.append(plantEquipDetail);
		buf.append(", goldDetail=");
		buf.append(goldDetail);
		buf.append(", aircraftDetail=");
		buf.append(aircraftDetail);
		buf.append(", postDatedCheque=");
		buf.append(postDatedCheque);
		buf.append(", chequeDetail=");
		buf.append(chequeDetail);
		buf.append(", receivableDetail=");
		buf.append(receivableDetail);
		buf.append(", vesselDetail=");
		buf.append(vesselDetail);
		buf.append("]");
		return buf.toString();
	}
	
	
}
