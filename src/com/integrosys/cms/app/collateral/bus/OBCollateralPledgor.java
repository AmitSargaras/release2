/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateralPledgor.java,v 1.4 2004/03/24 03:06:44 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents pledgor of the collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/03/24 03:06:44 $ Tag: $Name: $
 */
public class OBCollateralPledgor extends OBPledgor implements ICollateralPledgor, Comparable {

	private static final long serialVersionUID = -6698617658650411662L;

	private long mapID;

	private long sciPledgorID = ICMSConstant.LONG_MIN_VALUE;

	private String sciSecID;

	private long sciMapSysGenID;

	private String sciPledgorMapStatus;

	private String plgIdNumText;

	private String pledgorRelnshipCode;

	private String pledgorRelnship;

	private String sourceID;

	private long sPMID = ICMSConstant.LONG_MIN_VALUE;

	/**
	 * Default Constructor.
	 */
	public OBCollateralPledgor() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICollateralPledgor
	 */
	public OBCollateralPledgor(ICollateralPledgor obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get security pledgor mapping id.
	 * 
	 * @return long
	 */
	public long getMapID() {
		return mapID;
	}

	/**
	 * Set security pledgor mapping id.
	 * 
	 * @param mapID of type long
	 */
	public void setMapID(long mapID) {
		this.mapID = mapID;
	}

	/**
	 * Get pledgor reference id from SCI.
	 * 
	 * @return long
	 */
	public long getSCIPledgorID() {
		return sciPledgorID;
	}

	/**
	 * Set pledgor reference id from SCI.
	 * 
	 * @param sciPledgorID is of type long
	 */
	public void setSCIPledgorID(long sciPledgorID) {
		this.sciPledgorID = sciPledgorID;
	}

	/**
	 * Get security reference id from SCI.
	 * 
	 * @return String
	 */
	public String getSCISecID() {
		return sciSecID;
	}

	/**
	 * Set security reference id from SCI.
	 * 
	 * @param sciSecID of type String
	 */
	public void setSCISecID(String sciSecID) {
		this.sciSecID = sciSecID;
	}

	/**
	 * Get mapping reference id from SCI.
	 * 
	 * @return long
	 */
	public long getSCIMapSysGenID() {
		return sciMapSysGenID;
	}

	/**
	 * Set mapping reference id from SCI.
	 * 
	 * @param sciMapSysGenID of type long
	 */
	public void setSCIMapSysGenID(long sciMapSysGenID) {
		this.sciMapSysGenID = sciMapSysGenID;
	}

	/**
	 * Get update status indicator of SCI pledgor map.
	 * 
	 * @return String
	 */
	public String getSCIPledgorMapStatus() {
		return sciPledgorMapStatus;
	}

	/**
	 * Set update status indicator of SCI pledgor map.
	 * 
	 * @param sciPledgorMapStatus of type String
	 */
	public void setSCIPledgorMapStatus(String sciPledgorMapStatus) {
		this.sciPledgorMapStatus = sciPledgorMapStatus;
	}

	/**
	 * @return Returns the plgIdNumText.
	 */
	public String getPlgIdNumText() {
		return plgIdNumText;
	}

	/**
	 * @param plgIdNumText The plgIdNumText to set.
	 */
	public void setPlgIdNumText(String plgIdNumText) {
		this.plgIdNumText = plgIdNumText;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralPledgor#getPledgorRelnshipCode
	 */
	public String getPledgorRelnshipCode() {
		return this.pledgorRelnshipCode;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralPledgor#setPledgorRelnshipCode
	 */
	public void setPledgorRelnshipCode(String value) {
		this.pledgorRelnshipCode = value;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralPledgor#getPledgorRelnship
	 */
	public String getPledgorRelnship() {
		return this.pledgorRelnship;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralPledgor#setPledgorRelnship
	 */
	public void setPledgorRelnship(String value) {
		this.pledgorRelnship = value;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralPledgor#getSourceID
	 */
	public String getSourceID() {
		return this.sourceID;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralPledgor#setSourceID
	 */
	public void setSourceID(String value) {
		this.sourceID = value;
	}

	/**
	 * Get security pledgor reference id from SCI.
	 * 
	 * @return long
	 */
	public long getSPMID() {
		return sPMID;
	}

	/**
	 * Set security pledgor reference id from SCI.
	 * 
	 * @param sPMID is of type long
	 */
	public void setSPMID(long sPMID) {
		this.sPMID = sPMID;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(mapID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBCollateralPledgor)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public int compareTo(Object other) {
		long otherMapID = (other == null) ? Long.MAX_VALUE : ((ICollateralPledgor) other).getMapID();

		return (this.mapID == otherMapID) ? 0 : ((this.mapID > otherMapID) ? 1 : -1);
	}
}