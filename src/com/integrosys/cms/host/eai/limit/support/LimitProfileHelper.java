/*
 * Created by IntelliJ IDEA.
 * User: Sulin
 * Date: Oct 20, 2003
 * Time: 8:04:27 PM
 */
package com.integrosys.cms.host.eai.limit.support;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.host.eai.customer.EAICustomerMessageException;
import com.integrosys.cms.host.eai.limit.NoSuchCreditGradeException;
import com.integrosys.cms.host.eai.limit.NoSuchJointBorrowerException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.bus.JointBorrower;
import com.integrosys.cms.host.eai.limit.bus.LimitCreditGrade;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;

public class LimitProfileHelper extends CommonHelper {
	public boolean isUpdate(LimitProfile lp) {
		return ((lp.getChangeIndicator().equals(String.valueOf( CHANGEINDICATOR))) && (lp.getUpdateStatusIndicator().equals(String.valueOf( UPDATEINDICATOR))));
	}

	public boolean isDelete(LimitProfile lp) {
		return ((lp.getChangeIndicator().equals(String.valueOf( CHANGEINDICATOR))) && (lp.getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))));
	}

	public long extractLEID(String key) throws EAICustomerMessageException {
		if (-1 != key.indexOf(LIMITPROFILE_KEY)) {
			return getToken(key, 1);
		}
		throw new EAICustomerMessageException("Not a Limiprofile Key : " + key);
	}

	public long extractSubProfileId(String key) throws EAICustomerMessageException {
		if (-1 != key.indexOf(LIMITPROFILE_KEY)) {
			return getToken(key, 2);
		}
		throw new EAICustomerMessageException("Not a Limiprofile Key : " + key);
	}

	public long extractLimitProfileId(String key) throws EAICustomerMessageException {
		if (-1 != key.indexOf(LIMITPROFILE_KEY)) {
			return getToken(key, 3);
		}
		throw new EAICustomerMessageException("Not a Limiprofile Key : " + key);
	}

	public String constructLimitProfileKey(String leid, long subprofileid, long limitprofileid) {
		return LIMITPROFILE_KEY + DELIMITER + leid + DELIMITER + subprofileid + DELIMITER + limitprofileid;
	}

	public LimitProfile getLimitProfile(LimitProfile limitProfile, String leID, long subprofileid, long limitprofileid)
			throws NoSuchLimitProfileException {
		if (limitProfile.getCIFId().equalsIgnoreCase(leID) && (limitProfile.getSubProfileId() == subprofileid)) {
			return limitProfile;
		}
		// }
		throw new NoSuchLimitProfileException(leID, subprofileid, limitprofileid);

	}
	
	public JointBorrower getJointBorrower(Vector jointBorrowers,String cifId,String aaNo)throws NoSuchJointBorrowerException
	{
		for(Iterator iter = jointBorrowers.iterator();iter.hasNext();)
		{
			JointBorrower jointBorrower = (JointBorrower)iter.next();
		
			if(jointBorrower.getCIFId().equals(cifId))
			{
				return jointBorrower;
			}
		}
		throw new NoSuchJointBorrowerException(cifId,aaNo);
	}
	
	
	public LimitCreditGrade getLimitCreditGrade(Vector creditGrades,long creditGradeID,String aaNo)throws NoSuchCreditGradeException
	{
		for(Iterator iter = creditGrades.iterator();iter.hasNext();)
		{
			LimitCreditGrade limitCreditGrade = (LimitCreditGrade)iter.next();
		
			if(limitCreditGrade.getCreditGradeId()== creditGradeID  && limitCreditGrade.getLOSAANumber().equals(aaNo) )
			{
				return limitCreditGrade;
			}
		}
		throw new NoSuchCreditGradeException (String.valueOf( creditGradeID),aaNo);
	}

	public Collection getLimitProfile(Vector veclp, String leID, long subprofileid) {
		Vector vec = new Vector();
		for (Iterator iter = veclp.iterator(); iter.hasNext();) {
			LimitProfile lp = (LimitProfile) iter.next();
			if (lp.getCIFId().equalsIgnoreCase(leID) && (lp.getSubProfileId() == subprofileid)) {
				vec.add(lp);
			}
		}
		return vec;
	}

	public boolean anyLimitProfile(Vector vec) {
		if (vec == null) {
			return false;
		}

		if (vec.size() == 1) {
			LimitProfile obj = (LimitProfile) vec.elementAt(0);
			if ((obj == null) || Character.isIdentifierIgnorable(obj.getChangeIndicator().charAt(0))) {
				return false;
			}
		}
		return true;
	}
}
