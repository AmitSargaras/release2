/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/pledgor/PledgorRepublishActualTrxHandler.java,v 1.4 2003/12/05 10:59:44 slong Exp $
 */
package com.integrosys.cms.host.eai.security.handler.actualtrxhandler;

import java.util.Map;

import com.integrosys.cms.host.eai.security.bus.Pledgor;
import com.integrosys.cms.host.eai.security.bus.PledgorCreditGrade;

/**
 * This class will handle republishing of actual business data for pledgor.
 * 
 * @author $Author: slong $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/12/05 10:59:44 $ Tag: $Name: $
 */
public class PledgorRepublishActualTrxHandler extends PledgorActualTrxHandler {
	/**
	 * Get pledgor for processing.
	 * 
	 * @param cdb of type CastorDb
	 * @param pledgor of type Pledgor
	 * @return a pledgor
	 */
	protected Pledgor getPledgorForProcessing(Pledgor pledgor,boolean isVariation) {

		Pledgor newMap = loadPledgor(pledgor, isVariation);
		return newMap;

	}

	/**
	 * Check if the pledgor is changed.
	 * 
	 * @param pledgor of type Pledgor
	 * @return boolean
	 */
	protected boolean isPledgorChanged(Pledgor pledgor) {
		return true;
	}

	/**
	 * Check if it is to delete the pledgor.
	 * 
	 * @param pledgor of type Pledgor
	 * @return boolean
	 */
	protected boolean isDeletePledgor(Pledgor pledgor) {
		return false;
	}

	/**
	 * Get pledgor credit grade for processing.
	 * 
	 * @param creditGrade of type PledgorCreditGrade
	 * @return pledgor credit grade
	 */
	protected PledgorCreditGrade getPledgorCreditGradeForProcessing(PledgorCreditGrade creditGrade) {

		PledgorCreditGrade pledgorCreditGrade = loadPledgorCreditGrade(creditGrade);
		return pledgorCreditGrade;

	}

	/**
	 * Check if the pledgor credit grade is changed.
	 * 
	 * @param creditGrade of type PledgorCreditGrade
	 * @return boolean
	 */
	protected boolean isPledgorCreditGradeChanged(PledgorCreditGrade creditGrade) {
		return true;
	}

	/**
	 * Check if it is to delete the pledgor credit grade.
	 * 
	 * @param creditGrade of type PledgorCreditGrade
	 * @return boolean
	 */
	protected boolean isDeletePledgorCreditGrade(PledgorCreditGrade creditGrade) {
		return false;
	}
	public Map getVariationProperties() {
		return super.getVariationProperties();
	}
	public void setVariationProperties(Map variationProperties) {
		super.setVariationProperties(variationProperties);
	}
}