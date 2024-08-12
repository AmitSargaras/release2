package com.integrosys.cms.host.eai.security.bus;

/**
 * This class represents Security Pledgor Map for the message field
 * 'securityPledgorMap'.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/10/13 02:57:17 $ Tag: $Name: $
 */
public class SecurityPledgorMap implements java.io.Serializable {
	private String securityId;

	private Long cmsSecurityId;

	private Long securityPledgorMapId;

	private Long cmsSecurityPledgorMapId;

	private Long pledgorId;

	private Long cmsPledgorId;

	private String updateStatusIndicator;

	private String changeIndicator;

	private boolean hasSecuritId;

	private boolean hasPledgorId;

	private String pledgorRelTypeNumber = "34";

	private String pledgorRelTypeValue;

	/**
	 * Default constructor.
	 */
	public SecurityPledgorMap() {
		super();
	}

	/**
	 * Get SCI security id.
	 * 
	 * @return SCI security id, the value of field 'securityId'
	 */
	public String getSecurityId() {
		return securityId;
	}

	/**
	 * Set SCI security id.
	 * 
	 * @param securityId the value of field 'securityId'
	 */
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
		this.hasSecuritId = true;
	}

	/**
	 * Get CMS security id.
	 * 
	 * @return CMS security id
	 */
	public Long getCMSSecurityId() {
		return cmsSecurityId;
	}

	/**
	 * Set CMS security id.
	 * 
	 * @param cmsSecurityId security id in CMS context
	 */
	public void setCMSSecurityId(Long cmsSecurityId) {
		this.cmsSecurityId = cmsSecurityId;
	}

	/**
	 * Get security pledgor map id in SCI.
	 * 
	 * @return pledgor map id, the value of the field 'securityPledgorMapId'
	 */
	public Long getSecurityPledgorMapId() {
		return this.securityPledgorMapId;
	}

	/**
	 * Set security pledgor map id in SCI.
	 * 
	 * @param securityPledgorMapId pledgor map id, the value of the field
	 *        'securityPledgorMapId'
	 */
	public void setSecurityPledgorMapId(Long securityPledgorMapId) {
		this.securityPledgorMapId = securityPledgorMapId;
	}

	/**
	 * Get security pledgor map id in CMS.
	 * 
	 * @return CMS security pledgor map id
	 */
	public Long getCMSSecurityPledgorMapId() {
		return this.cmsSecurityPledgorMapId;
	}

	/**
	 * Set security pledgor map id in CMS.
	 * 
	 * @param cmsSecurityPledgorMapId CMS security pledgor map id
	 */
	public void setCMSSecurityPledgorMapId(Long cmsSecurityPledgorMapId) {
		this.cmsSecurityPledgorMapId = cmsSecurityPledgorMapId;
	}

	/**
	 * Get SCI pledgor id.
	 * 
	 * @return SCI pledgor id, the value of field 'pledgorId'
	 */
	public Long getPledgorId() {
		return this.pledgorId;
	}

	/**
	 * Set SCI pledgor id.
	 * 
	 * @param pledgorId the value of field 'pledgorId'
	 */
	public void setPledgorId(Long pledgorId) {
		this.pledgorId = pledgorId;
		this.hasPledgorId = true;
	}

	/**
	 * Get CMS pledgor id.
	 * 
	 * @return pledgor id in CMS context
	 */
	public Long getCMSPledgorId() {
		return cmsPledgorId;
	}

	/**
	 * Set CMS pledgor id.
	 * 
	 * @param cmsPledgorId cms pledgor id
	 */
	public void setCMSPledgorId(Long cmsPledgorId) {
		this.cmsPledgorId = cmsPledgorId;
	}

	/**
	 * Get change indicator to indicate if the pledgor map has been changed.
	 * 
	 * @return the value of field 'changeIndicator'
	 */
	public String getChangeIndicator() {
		return changeIndicator;
	}

	/**
	 * Set change indicator.
	 * 
	 * @param changeIndicator of type String, the value of field
	 *        'changeIndicator'
	 */
	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	/**
	 * Returns the value of field 'updateStatusIndicator'.
	 * 
	 * @return the value of field 'updateStatusIndicator'.
	 */
	public String getUpdateStatusIndicator() {
		return this.updateStatusIndicator;
	}

	/**
	 * Set the value of field 'updateStatusIndicator'.
	 * 
	 * @param updateStatusIndicator the value of field 'updateStatusIndicator'
	 */
	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	/**
	 * Method hasPledgorId
	 */
	public boolean hasPledgorId() {
		return this.hasPledgorId;
	}

	/**
	 * Method hasSecurityId
	 */
	public boolean hasSecurityId() {
		return this.hasSecuritId;
	}

	public String getPledgorRelTypeNumber() {
		return pledgorRelTypeNumber;
	}

	public void setPledgorRelTypeNumber(String pledgorRelTypeNumber) {
		this.pledgorRelTypeNumber = pledgorRelTypeNumber;
	}

	public String getPledgorRelTypeValue() {
		return pledgorRelTypeValue;
	}

	public void setPledgorRelTypeValue(String pledgorRelTypeValue) {
		this.pledgorRelTypeValue = pledgorRelTypeValue;
	}

}
