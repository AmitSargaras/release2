/*
 * Created by IntelliJ IDEA.
 * User: Sulin
 * Date: Oct 16, 2003
 * Time: 1:57:13 PM
 */
package com.integrosys.cms.host.eai.security;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.PropertyUtils;

import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.bus.Pledgor;
import com.integrosys.cms.host.eai.security.bus.PledgorCreditGrade;

public class PledgorActualHelper {

	public boolean anyPledgorCreditGrade(Vector vec) {
		if (vec == null) {
			return false;
		}

		if (vec.size() == 1) {
			PledgorCreditGrade plg = (PledgorCreditGrade) vec.elementAt(0);
			if ((plg == null)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if the pledgor information is changed.
	 * 
	 * @param pledgor of type Pledgor
	 * @return boolean
	 */
	public boolean isPledgorChanged(Pledgor pledgor) {
		return String.valueOf(IEaiConstant.CHANGEINDICATOR).equals(pledgor.getChangeIndicator());
	}

	/**
	 * Check if it is to create the pledgor
	 * 
	 * @param pledgor of type Pledgor
	 * @return boolean
	 */
	public boolean isCreatePledgor(Pledgor pledgor) {
		return String.valueOf(IEaiConstant.CREATEINDICATOR).equals(pledgor.getUpdateStatusIndicator());
	}

	/**
	 * Check if it is to update the pledgor
	 * 
	 * @param pledgor of type Pledgor
	 * @return boolean
	 */
	public boolean isUpdatePledgor(Pledgor pledgor) {
		return String.valueOf(IEaiConstant.UPDATEINDICATOR).equals(pledgor.getUpdateStatusIndicator());
	}

	/**
	 * Check if it is to delete the pledgor
	 * 
	 * @param pledgor of type Pledgor
	 * @return boolean
	 */
	public boolean isDeletePledgor(Pledgor pledgor) {
		return String.valueOf(IEaiConstant.DELETEINDICATOR).equals(pledgor.getUpdateStatusIndicator());
	}

	/**
	 * Check if the pledgor credit grade information is changed.
	 * 
	 * @param creditGrade of type PledgorCreditGrade
	 * @return boolean
	 */
	public boolean isPledgorCreditGradeChanged(PledgorCreditGrade creditGrade) {
		return String.valueOf(IEaiConstant.CHANGEINDICATOR).equals(creditGrade.getChangeIndicator());
	}

	/**
	 * Check if it is to create the pledgor credit grade
	 * 
	 * @param creditGrade of type PledgorCreditGrade
	 * @return boolean
	 */
	public boolean isCreatePledgorCreditGrade(PledgorCreditGrade creditGrade) {
		return String.valueOf(IEaiConstant.CREATEINDICATOR).equals(creditGrade.getUpdateStatusIndicator());
	}

	/**
	 * Check if it is to update the pledgor credit grade
	 * 
	 * @param creditGrade of type PledgorCreditGrade
	 * @return boolean
	 */
	public boolean isUpdatePledgorCreditGrade(PledgorCreditGrade creditGrade) {
		return String.valueOf(IEaiConstant.UPDATEINDICATOR).equals(creditGrade.getUpdateStatusIndicator());
	}

	/**
	 * Check if it is to delete the pledgor credit grade
	 * 
	 * @param creditGrade of type PledgorCreditGrade
	 * @return boolean
	 */
	public boolean isDeletePledgorCreditGrade(PledgorCreditGrade creditGrade) {
		return String.valueOf(IEaiConstant.UPDATEINDICATOR).equals(creditGrade.getUpdateStatusIndicator());
	}

	/**
	 * Method to check if the string is empty or null.
	 * 
	 * @param str of type String
	 * @return true if the string is empty or null, otherwise return false
	 */
	public boolean isEmptyString(String str) {
		if (str == null) {
			return true;
		}

		String tmpstr = str.trim();

		if (tmpstr.length() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get a Vector of Pledgors for processing.
	 * 
	 * @param pledgorVec of type Vector
	 * @return a Vector of Pledgor objects
	 */
	public Vector getPledgorForProcess(Vector pledgorVec) {
		int size = pledgorVec.size();
		Vector vec = new Vector();

		for (int i = 0; i < size; i++) {
			Pledgor pledgor = (Pledgor) pledgorVec.elementAt(i);

			if (!isEmptyString(String.valueOf(pledgor.getChangeIndicator()))) {
				vec.add(pledgor);
			}
		}
		return vec;
	}

	/**
	 * Get a Vector of PledgorCreditGrade for processing.
	 * 
	 * @param pcgVec of type Vector
	 * @return a Vector of PledgorCreditGrade objects
	 */
	public Vector getPledgorCreditGradeForProcess(Vector pcgVec) {
		int size = pcgVec.size();
		Vector vec = new Vector();

		for (int i = 0; i < size; i++) {
			PledgorCreditGrade pcg = (PledgorCreditGrade) pcgVec.elementAt(i);

			if (!isEmptyString(String.valueOf(pcg.getChangeIndicator()))) {
				vec.add(pcg);
			}
		}
		return vec;
	}

	/**
	 * To copy on the required fields in the case of variation.<br>
	 * @param source Object<br>
	 * @param target Object<br>
	 * @param properties List of properties to be copied<br>
	 * @return Object (target object which has been copied)
	 */
	public Object copyVariationProperties(Object source, Object target, List properties) {
		for (Iterator iterator = properties.iterator(); iterator.hasNext();) {
			String property = (String) iterator.next();

			try {
				PropertyUtils.setProperty(target, property, PropertyUtils.getProperty(source, property));
			}
			catch (InvocationTargetException ex) {
				throw new IllegalStateException("failed to copy value from source [" + source + "] to target ["
						+ target + "], for property [" + property + "]; nested exception is " + ex.getCause());
			}
			catch (Exception ex) {
				throw new IllegalStateException("failed to copy value from source [" + source + "] to target ["
						+ target + "], for property [" + property + "]; nested exception is " + ex);
			}
		}
		return target;
	}
}
