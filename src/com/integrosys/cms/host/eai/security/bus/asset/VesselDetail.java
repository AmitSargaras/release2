/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents Vessel Detail of Asset Based.
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class VesselDetail implements Serializable {

	private static final long serialVersionUID = -1141592272623719652L;

	private String vesselSerialNo;

	private String vesselName;

	private String registeredCountry;

	private StandardCode vesselState;

	private Date expectedOccupancyDate;

	private String expectedOccupancy;

	private StandardCode occupancyType;

	private Long builtYear;

	private String builder;

	private String mainRegistration;

	private String length;

	private String width;

	private String depth;

	private String deckLoading;

	private String deckWeight;

	private String sideBoard;

	private String bow;

	private String deck;

	private String deckThickness;

	private String bottom;

	private String winchDrive;

	private String bHP;

	private String speed;

	private String anchor;

	private String anchorDrive;

	private String classSociety;

	private String constructionCountry;

	private String constructionPlace;

	private String vesselUse;

	private String charterContractFlag;

	private String chartererName;

	private Long charterPeriod;

	private StandardCode charterPeriodUnit;

	private Double charterAmount;

	private String charterCurrency;

	private StandardCode charterRate;

	private String charterRateOthers;

	private String charterRemarks;

	private String purchaseCurrency;

	private String PortRegistration;
	
	private StandardCode vesselType;

	/**
	 * Default constructor.
	 */
	public VesselDetail() {
		super();
	}

	public String getAnchor() {
		return anchor;
	}

	public String getAnchorDrive() {
		return anchorDrive;
	}

	public String getBHP() {
		return bHP;
	}

	public String getBottom() {
		return bottom;
	}

	public String getBow() {
		return bow;
	}

	public String getBuilder() {
		return builder;
	}

	public Long getBuiltYear() {
		return builtYear;
	}

	public Double getCharterAmount() {
		return charterAmount;
	}

	public String getCharterContractFlag() {
		return charterContractFlag;
	}

	public String getCharterCurrency() {
		return charterCurrency;
	}

	public String getChartererName() {
		return chartererName;
	}

	public Long getCharterPeriod() {
		return charterPeriod;
	}

	public StandardCode getCharterPeriodUnit() {
		return charterPeriodUnit;
	}

	public StandardCode getCharterRate() {
		return charterRate;
	}

	public String getCharterRateOthers() {
		return charterRateOthers;
	}

	public String getCharterRemarks() {
		return charterRemarks;
	}

	public String getClassSociety() {
		return classSociety;
	}

	public String getConstructionCountry() {
		return constructionCountry;
	}

	public String getConstructionPlace() {
		return constructionPlace;
	}

	public String getDeck() {
		return deck;
	}

	public String getDeckLoading() {
		return deckLoading;
	}

	public String getDeckThickness() {
		return deckThickness;
	}

	public String getDeckWeight() {
		return deckWeight;
	}

	public String getDepth() {
		return depth;
	}

	public String getExpectedOccupancy() {
		return expectedOccupancy;
	}

	public String getExpectedOccupancyDate() {
		return  MessageDate.getInstance().getString(expectedOccupancyDate);
	}

	public Date getJDOExpectedOccupancyDate() {
		return expectedOccupancyDate;
	}

	public String getLength() {
		return length;
	}

	public String getMainRegistration() {
		return mainRegistration;
	}

	public StandardCode getOccupancyType() {
		return occupancyType;
	}

	public String getPortRegistration() {
		return PortRegistration;
	}

	public String getPurchaseCurrency() {
		return purchaseCurrency;
	}

	public String getRegisteredCountry() {
		return registeredCountry;
	}

	public String getSideBoard() {
		return sideBoard;
	}

	public String getSpeed() {
		return speed;
	}

	public String getVesselName() {
		return vesselName;
	}

	public String getVesselSerialNo() {
		return vesselSerialNo;
	}

	public StandardCode getVesselState() {
		return vesselState;
	}

	public StandardCode getVesselType() {
		return vesselType;
	}

	public String getVesselUse() {
		return vesselUse;
	}

	public String getWidth() {
		return width;
	}

	public String getWinchDrive() {
		return winchDrive;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public void setAnchorDrive(String anchorDrive) {
		this.anchorDrive = anchorDrive;
	}

	public void setBHP(String bHP) {
		this.bHP = bHP;
	}

	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	public void setBow(String bow) {
		this.bow = bow;
	}

	public void setBuilder(String builder) {
		this.builder = builder;
	}

	public void setBuiltYear(Long builtYear) {
		this.builtYear = builtYear;
	}

	public void setCharterAmount(Double charterAmount) {
		this.charterAmount = charterAmount;
	}

	public void setCharterContractFlag(String charterContractFlag) {
		this.charterContractFlag = charterContractFlag;
	}

	public void setCharterCurrency(String charterCurrency) {
		this.charterCurrency = charterCurrency;
	}

	public void setChartererName(String chartererName) {
		this.chartererName = chartererName;
	}

	public void setCharterPeriod(Long charterPeriod) {
		this.charterPeriod = charterPeriod;
	}

	public void setCharterPeriodUnit(StandardCode charterPeriodUnit) {
		this.charterPeriodUnit = charterPeriodUnit;
	}

	public void setCharterRate(StandardCode charterRate) {
		this.charterRate = charterRate;
	}

	public void setCharterRateOthers(String charterRateOthers) {
		this.charterRateOthers = charterRateOthers;
	}

	public void setCharterRemarks(String charterRemarks) {
		this.charterRemarks = charterRemarks;
	}

	public void setClassSociety(String classSociety) {
		this.classSociety = classSociety;
	}

	public void setConstructionCountry(String constructionCountry) {
		this.constructionCountry = constructionCountry;
	}

	public void setConstructionPlace(String constructionPlace) {
		this.constructionPlace = constructionPlace;
	}

	public void setDeck(String deck) {
		this.deck = deck;
	}

	public void setDeckLoading(String deckLoading) {
		this.deckLoading = deckLoading;
	}

	public void setDeckThickness(String deckThickness) {
		this.deckThickness = deckThickness;
	}

	public void setDeckWeight(String deckWeight) {
		this.deckWeight = deckWeight;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public void setExpectedOccupancy(String expectedOccupancy) {
		this.expectedOccupancy = expectedOccupancy;
	}

	public void setExpectedOccupancyDate(String expectedOccupancyDate) {
		this.expectedOccupancyDate = MessageDate.getInstance().getDate(expectedOccupancyDate);
	}

	public void setJDOExpectedOccupancyDate(Date expectedOccupancyDate) {
		this.expectedOccupancyDate = expectedOccupancyDate;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public void setMainRegistration(String mainRegistration) {
		this.mainRegistration = mainRegistration;
	}

	public void setOccupancyType(StandardCode occupancyType) {
		this.occupancyType = occupancyType;
	}

	public void setPortRegistration(String portRegistration) {
		PortRegistration = portRegistration;
	}

	public void setPurchaseCurrency(String purchaseCurrency) {
		this.purchaseCurrency = purchaseCurrency;
	}

	public void setRegisteredCountry(String registeredCountry) {
		this.registeredCountry = registeredCountry;
	}

	public void setSideBoard(String sideBoard) {
		this.sideBoard = sideBoard;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public void setVesselSerialNo(String vesselSerialNo) {
		this.vesselSerialNo = vesselSerialNo;
	}

	public void setVesselState(StandardCode vesselState) {
		this.vesselState = vesselState;
	}

	public void setVesselType(StandardCode vesselType) {
		this.vesselType = vesselType;
	}

	public void setVesselUse(String vesselUse) {
		this.vesselUse = vesselUse;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setWinchDrive(String winchDrive) {
		this.winchDrive = winchDrive;
	}

}
