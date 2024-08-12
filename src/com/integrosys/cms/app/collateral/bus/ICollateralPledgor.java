/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateralPledgor.java,v 1.5 2004/03/24 03:06:33 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * This interface represents pledgor of collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/03/24 03:06:33 $ Tag: $Name: $
 */
public interface ICollateralPledgor extends IPledgor {
	/**
	 * Get security pledgor mapping id.
	 * 
	 * @return long
	 */
	public long getMapID();

	/**
	 * Set security pledgor mapping id.
	 * 
	 * @param mapID of type long
	 */
	public void setMapID(long mapID);

	/**
	 * Get pledgor reference id from SCI.
	 * 
	 * @return long
	 */
	public long getSCIPledgorID();

	/**
	 * Set pledgor reference id from SCI.
	 * 
	 * @param sciPledgorID is of type long
	 */
	public void setSCIPledgorID(long sciPledgorID);

	/**
	 * Get security reference id from SCI.
	 * 
	 * @return String
	 */
	public String getSCISecID();

	/**
	 * Set security reference id from SCI.
	 * 
	 * @param sciSecID of type String
	 */
	public void setSCISecID(String sciSecID);

	/**
	 * Get mapping reference id from SCI.
	 * 
	 * @return long
	 */
	public long getSCIMapSysGenID();

	/**
	 * Set mapping reference id from SCI.
	 * 
	 * @param sciMapSysGenID of type long
	 */
	public void setSCIMapSysGenID(long sciMapSysGenID);

	/**
	 * Get update status indicator of SCI pledgor map.
	 * 
	 * @return String
	 */
	public String getSCIPledgorMapStatus();

	/**
	 * Set update status indicator of SCI pledgor map.
	 * 
	 * @param sciPledgorMapStatus of type String
	 */
	public void setSCIPledgorMapStatus(String sciPledgorMapStatus);

	/**
	 * @return Returns the plgIdNumText.
	 */
	public String getPlgIdNumText();

	/**
	 * @param plgIdNumText The plgIdNumText to set.
	 */
	public void setPlgIdNumText(String plgIdNumText);

	/**
	 * Get pledgor relationship code.
	 * 
	 * @return String
	 */
	public String getPledgorRelnshipCode();

	/**
	 * Set pledgor relationship code.
	 * 
	 * @param value of type String
	 */
	public void setPledgorRelnshipCode(String value);

	/**
	 * Get pledgor relationship value.
	 * 
	 * @return String
	 */
	public String getPledgorRelnship();

	/**
	 * Set pledgor relationship value.
	 * 
	 * @param value of type String
	 */
	public void setPledgorRelnship(String value);

	/**
	 * Get source ID.
	 * 
	 * @return String
	 */
	public String getSourceID();

	/**
	 * Set source ID.
	 * 
	 * @param value of type String
	 */
	public void setSourceID(String value);

    /**
     * Get security pledgor reference id from SCI.
     *
     * @return long
     */
    public long getSPMID();

    /**
     * Set security pledgor reference id from SCI.
     *
     * @param sPMID is of type long
     */
    public void setSPMID(long sPMID);

}
