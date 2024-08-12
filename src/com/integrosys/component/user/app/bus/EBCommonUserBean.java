package com.integrosys.component.user.app.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.component.bizstructure.app.bus.EBTeamTypeMembership;
import com.integrosys.component.bizstructure.app.bus.EBTeamTypeMembershipHome;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

public abstract class EBCommonUserBean implements EntityBean, ICommonUser {
	static final String DOCUMENT_CODE_SEQ = "DOCUMENT_CODE_SEQ";
	static final String USER_SEQ = "USER_SEQ";

	public abstract Long getEjbUserID();

	public abstract void setEjbUserID(Long var1);

	public long getUserID() {
		return this.getEjbUserID();
	}

	public void setUserID(long uid) {
		this.setEjbUserID(new Long(uid));
	}

	public abstract void setLoginID(String var1);

	public abstract void setPassword(String var1);

	public abstract void setUserName(String var1);

	public abstract void setEmployeeID(String var1);

	public abstract void setDepartment(String var1);

	public abstract void setPosition(String var1);

	public abstract void setPhoneNumber(String var1);

	public abstract void setEmail(String var1);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long var1);

	public abstract String getStatus();

	public abstract void setStatus(String var1);

	public abstract Date getValidFromDate();

	public abstract Date getValidToDate();

	public abstract void setValidFromDate(Date var1);

	public abstract void setValidToDate(Date var1);

	public abstract Long getEjbRoleID();

	public abstract void setEjbRoleID(Long var1);

	public abstract Long getEjbTeamTypeMembershipID();

	public abstract void setEjbTeamTypeMembershipID(Long var1);

	public ITeamTypeMembership getTeamTypeMembership() {
		Long teamTypeMembershipID = this.getEjbTeamTypeMembershipID();
		if (null == teamTypeMembershipID) {
			return null;
		} else {
			try {
				EBTeamTypeMembershipHome ttmh = this.getTeamTypeMembershipHome();
				EBTeamTypeMembership home = ttmh.findByPrimaryKey(teamTypeMembershipID);
				return home.getOBTeamTypeMembership();
			} catch (FinderException var4) {
				var4.printStackTrace();
				return null;
			} catch (RemoteException var5) {
				var5.printStackTrace();
				return null;
			}
		}
	}

	public void setTeamTypeMembership(ITeamTypeMembership teamTypeMembership) {
		if (null == teamTypeMembership) {
			this.setEjbTeamTypeMembershipID((Long) null);
		} else {
			this.setEjbTeamTypeMembershipID(new Long(teamTypeMembership.getMembershipID()));
		}

	}

	public ICommonUserSegment[] getUserSegment() {
		return null;
	}

	public void setUserSegment(ICommonUserSegment[] value) {
	}

	public ICommonUserRegion[] getUserRegion() {
		return null;
	}

	public void setUserRegion(ICommonUserRegion[] value) {
	}

	public abstract Long getEjbCityId();

	public abstract void setEjbCityId(Long var1);

	public abstract Long getEjbStateId();

	public abstract void setEjbStateId(Long var1);

	public abstract Long getEjbCountryId();

	public abstract void setEjbCountryId(Long var1);

	public abstract String getEjbBranchCode();

	public abstract void setEjbBranchCode(String var1);

	public abstract String getEjbAddress();

	public abstract void setEjbAddress(String var1);

	public abstract Long getEjbRegion();

	public abstract void setEjbRegion(Long var1);
	
	public abstract String getEmployeeGrade();

	public abstract void setEmployeeGrade(String employeeGrade);

	public abstract String getOverrideExceptionForLoa();

	public abstract void setOverrideExceptionForLoa(String overrideExceptionForLoa);
	
	public abstract String getIsacRefNumber();

	public abstract void setIsacRefNumber(String IsacRefNumber);
	
	
	public abstract String getMakerDt();

	public abstract void setMakerDt(String makerDt);
	
	public abstract String getCheckerDt();

	public abstract void setCheckerDt(String checkerDt);


	public IRoleType getRoleType() {
		Long roleID = this.getEjbRoleID();
		if (null == roleID) {
			return null;
		} else {
			try {
				EBRoleTypeLocalHome rth = this.getRoleTypeLocalHome();
				EBRoleTypeLocal local = rth.findByPrimaryKey(roleID);
				return local.getOBRoleType();
			} catch (FinderException var4) {
				var4.printStackTrace();
				return null;
			}
		}
	}

	public void setRoleType(IRoleType roleType) {
		if (null == roleType) {
			this.setEjbRoleID((Long) null);
		} else {
			this.setEjbRoleID(new Long(roleType.getRoleTypeID()));
		}

	}

	public ICommonUser getOBUser() {
		try {
			OBCommonUser usr = new OBCommonUser();
			usr.setUserID(this.getUserID());
			usr.setUserName(this.getUserName());
			usr.setPassword(this.getPassword());
			usr.setLoginID(this.getLoginID());
			usr.setDepartment(this.getDepartment());
			usr.setEmail(this.getEmail());
			usr.setEmployeeID(this.getEmployeeID());
			usr.setPhoneNumber(this.getPhoneNumber());
			usr.setPosition(this.getPosition());
			usr.setStatus(this.getStatus());
			usr.setVersionTime(this.getVersionTime());
			usr.setRoleType(this.getRoleType());
			usr.setCountry(this.getCountry());
			usr.setOrganisation(this.getOrganisation());
			usr.setValidFromDate(this.getValidFromDate());
			usr.setValidToDate(this.getValidToDate());
			usr.setEjbAddress(this.getEjbAddress());
			usr.setEjbRegion(this.getEjbRegion());
			usr.setEjbCityId(this.getEjbCityId());
			usr.setEjbStateId(this.getEjbStateId());
			usr.setEjbCountryId(this.getEjbCountryId());
			usr.setEjbBranchCode(this.getEjbBranchCode());
			usr.setTeamTypeMembership(this.getTeamTypeMembership());
			usr.setUserSegment(this.retrieveUserSegment());
			usr.setUserRegion(this.retrieveUserRegion());
			usr.setEmployeeGrade(this.getEmployeeGrade());
			usr.setOverrideExceptionForLoa(this.getOverrideExceptionForLoa());
			usr.setIsacRefNumber(this.getIsacRefNumber());
			return usr;
		} catch (Exception var2) {
			var2.printStackTrace();
			return null;
		}
	}

	public void setOBUser(ICommonUser user) throws ConcurrentUpdateException {
		this.checkVersionMismatch(user);
		this.simpleSetOBUser(user, false);
	}

	private void simpleSetOBUser(ICommonUser user, boolean isAdd) {
		try {
			this.setUserName(user.getUserName());
			this.setPassword(user.getPassword());
			this.setLoginID(user.getLoginID().toUpperCase());
			this.setDepartment(user.getDepartment());
			this.setEmail(user.getEmail());
			this.setEmployeeID(user.getEmployeeID());
			this.setPhoneNumber(user.getPhoneNumber());
			this.setPosition(user.getPosition());
			this.setCountry(user.getCountry());
			this.setOrganisation(user.getOrganisation());
			this.setValidFromDate(user.getValidFromDate());
			this.setValidToDate(user.getValidToDate());
			this.setReferences(user);
			this.setStatus(user.getStatus());
			this.setVersionTime(VersionGenerator.getVersionNumber());
			this.setTeamTypeMembership(user.getTeamTypeMembership());
			this.setEjbCityId(user.getEjbCityId());
			this.setEjbCountryId(user.getEjbCountryId());
			this.setEjbRegion(user.getEjbRegion());
			this.setEjbStateId(user.getEjbStateId());
			this.setEjbAddress(user.getEjbAddress());
			this.setEjbBranchCode(user.getEjbBranchCode());
			this.setEmployeeGrade(user.getEmployeeGrade());
			this.setOverrideExceptionForLoa(user.getOverrideExceptionForLoa());
			this.setIsacRefNumber(user.getIsacRefNumber());
		} catch (Exception var4) {
			var4.printStackTrace();
		}

	}

	public Long ejbCreate(OBCommonUser user) throws CreateException {
		this.setUserID(user.getUserID());
		this.setStatus(user.getStatus());
		this.simpleSetOBUser(user, true);
		return null;
	}

	public void ejbPostCreate(OBCommonUser user) throws CreateException {
		this.setReferences(user);
	}

	protected void setReferences(ICommonUser user) {
		this.setRoleType(user.getRoleType());
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void ejbLoad() {
	}

	public void ejbStore() {
	}

	public void ejbRemove() throws RemoveException {
	}

	public void setEntityContext(EntityContext ctx) {
	}

	public void unsetEntityContext() {
	}

	public Long ejbCreate() throws CreateException {
		return null;
	}

	public void ejbPostCreate() throws CreateException {
	}

	public void deleteRecord(ICommonUser persistEntity) throws ConcurrentUpdateException {
		this.checkVersionMismatch(persistEntity);
		this.setStatus("D");
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	protected void checkVersionMismatch(ICommonUser persistEntity) throws ConcurrentUpdateException {
		if (this.getVersionTime() != persistEntity.getVersionTime()) {
			throw new ConcurrentUpdateException("Mismatch timestamp");
		}
	}

	protected EBRoleTypeLocalHome getRoleTypeLocalHome() {
		EBRoleTypeLocalHome rth = (EBRoleTypeLocalHome) BeanController.getEJBLocalHome("EBRoleTypeLocalHome",
				EBRoleTypeLocalHome.class.getName());
		return rth;
	}

	protected EBUserSegmentLocalHome getUserSegmentLocalHome() {
		EBUserSegmentLocalHome rth = (EBUserSegmentLocalHome) BeanController.getEJBLocalHome("EBUserSegmentLocalHome",
				EBUserSegmentLocalHome.class.getName());
		return rth;
	}

	protected EBUserRegionLocalHome getUserRegionLocalHome() {
		EBUserRegionLocalHome rth = (EBUserRegionLocalHome) BeanController.getEJBLocalHome("EBUserRegionLocalHome",
				EBUserRegionLocalHome.class.getName());
		return rth;
	}

	protected EBCommonUserSegmentHome getCommonUserSegmentHome() {
		EBCommonUserSegmentHome rth = (EBCommonUserSegmentHome) BeanController.getEJBHome("EBCommonUserSegmentHome",
				EBCommonUserSegmentHome.class.getName());
		return rth;
	}

	protected EBCommonUserRegionHome getCommonUserRegionHome() {
		EBCommonUserRegionHome rth = (EBCommonUserRegionHome) BeanController.getEJBHome("EBCommonUserRegionHome",
				EBCommonUserRegionHome.class.getName());
		return rth;
	}

	protected EBTeamTypeMembershipHome getTeamTypeMembershipHome() {
		EBTeamTypeMembershipHome ttmh = (EBTeamTypeMembershipHome) BeanController.getEJBHome("EBTeamTypeMembershipHome",
				"com.integrosys.component.bizstructure.app.bus.EBTeamTypeMembershipHome");
		return ttmh;
	}

	public abstract Collection getCMRUserSegment();

	public abstract void setCMRUserSegment(Collection var1);

	public abstract Collection getCMRUserRegion();

	public abstract void setCMRUserRegion(Collection var1);

	private ICommonUserSegment[] retrieveUserSegment() throws Exception {
		try {
			Collection c = this.getCMRUserSegment();
			if (null != c && c.size() != 0) {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();

				while (i.hasNext()) {
					EBUserSegmentLocal local = (EBUserSegmentLocal) i.next();
					ICommonUserSegment ob = local.getOBCommonUserSegment();
					aList.add(ob);
				}

				return (ICommonUserSegment[]) aList.toArray(new ICommonUserSegment[0]);
			} else {
				return null;
			}
		} catch (Exception var6) {
			var6.printStackTrace();
			return null;
		}
	}

	private ICommonUserRegion[] retrieveUserRegion() throws Exception {
		try {
			Collection c = this.getCMRUserRegion();
			if (null != c && c.size() != 0) {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();

				while (i.hasNext()) {
					EBUserRegionLocal local = (EBUserRegionLocal) i.next();
					ICommonUserRegion ob = local.getOBCommonUserRegion();
					aList.add(ob);
				}

				return (ICommonUserRegion[]) aList.toArray(new ICommonUserRegion[0]);
			} else {
				return null;
			}
		} catch (Exception var6) {
			var6.printStackTrace();
			return null;
		}
	}

	private void updateUserSegment(ICommonUser user) throws Exception {
		ICommonUserSegment[] addr = user.getUserSegment();

		try {
			Collection c = this.getCMRUserSegment();
			if (null == addr) {
				if (null == c || c.size() == 0) {
					return;
				}

				this.deleteUserSegment(new ArrayList(c));
			} else if (null != c && c.size() != 0) {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList();

				boolean found;
				while (i.hasNext()) {
					EBUserSegmentLocal local = (EBUserSegmentLocal) i.next();
					Long id = local.getId();
					found = false;

					for (int j = 0; j < addr.length; ++j) {
						ICommonUserSegment newOB = addr[j];
						if (newOB.getId() == id) {
							local.setOBCommonUserSegment(newOB);
							found = true;
							break;
						}
					}

					if (!found) {
						deleteList.add(local);
					}
				}

				for (int j = 0; j < addr.length; ++j) {
					i = c.iterator();
					ICommonUserSegment newOB = addr[j];
					found = false;

					while (i.hasNext()) {
						EBUserSegmentLocal local = (EBUserSegmentLocal) i.next();
						Long id = local.getId();
						if (newOB.getId() == id) {
							found = true;
							break;
						}
					}

					if (!found) {
						createList.add(newOB);
					}
				}

				this.deleteUserSegment(deleteList);
				this.createUserSegment(createList, user);
			} else {
				this.createUserSegment(Arrays.asList(addr), user);
			}

		} catch (Exception var12) {
			var12.printStackTrace();
			throw var12;
		}
	}

	private void deleteUserSegment(List deleteList) throws Exception {
		if (null != deleteList && deleteList.size() != 0) {
			try {
				Collection c = this.getCMRUserSegment();
				Iterator i = deleteList.iterator();

				while (i.hasNext()) {
					EBUserSegmentLocal local = (EBUserSegmentLocal) i.next();
					c.remove(local);
					local.remove();
				}
			} catch (Exception var5) {
				var5.printStackTrace();
			}

		}
	}

	private void createUserSegment(List createList, ICommonUser user) throws Exception {
		if (null != createList && createList.size() != 0) {
			Collection c = this.getCMRUserSegment();
			Iterator i = createList.iterator();

			try {
				EBUserSegmentLocalHome home = this.getUserSegmentLocalHome();

				while (i.hasNext()) {
					ICommonUserSegment ob = (ICommonUserSegment) i.next();
					if (ob != null) {
						long pk = Long.parseLong((new SequenceManager()).getSeqNum("DOCUMENT_CODE_SEQ", true));
						ob.setId(new Long(pk));
						ob.setUserID(user.getUserID());
						EBUserSegmentLocal local = home.create(ob);
						c.add(local);
					}
				}

				EBUserSegmentLocal var11 = (EBUserSegmentLocal) c.iterator().next();
			} catch (Exception var10) {
				var10.printStackTrace();
			}

		}
	}

	private void updateUserRegion(ICommonUser user) throws Exception {
		ICommonUserRegion[] addr = user.getUserRegion();

		try {
			Collection c = this.getCMRUserRegion();
			if (null == addr) {
				if (null == c || c.size() == 0) {
					return;
				}

				this.deleteUserRegion(new ArrayList(c));
			} else if (null != c && c.size() != 0) {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList();

				boolean found;
				while (i.hasNext()) {
					EBUserRegionLocal local = (EBUserRegionLocal) i.next();
					Long id = local.getId();
					found = false;

					for (int j = 0; j < addr.length; ++j) {
						ICommonUserRegion newOB = addr[j];
						if (newOB.getId() == id) {
							local.setOBCommonUserRegion(newOB);
							found = true;
							break;
						}
					}

					if (!found) {
						deleteList.add(local);
					}
				}

				for (int j = 0; j < addr.length; ++j) {
					i = c.iterator();
					ICommonUserRegion newOB = addr[j];
					found = false;

					while (i.hasNext()) {
						EBUserRegionLocal local = (EBUserRegionLocal) i.next();
						Long id = local.getId();
						if (newOB.getId() == id) {
							found = true;
							break;
						}
					}

					if (!found) {
						createList.add(newOB);
					}
				}

				this.deleteUserRegion(deleteList);
				this.createUserRegion(createList, user);
			} else {
				this.createUserRegion(Arrays.asList(addr), user);
			}

		} catch (Exception var12) {
			throw var12;
		}
	}

	private void deleteUserRegion(List deleteList) throws Exception {
		if (null != deleteList && deleteList.size() != 0) {
			try {
				Collection c = this.getCMRUserRegion();
				Iterator i = deleteList.iterator();

				while (i.hasNext()) {
					EBUserRegionLocal local = (EBUserRegionLocal) i.next();
					c.remove(local);
					local.remove();
				}
			} catch (Exception var5) {
				var5.printStackTrace();
			}

		}
	}

	private void createUserRegion(List createList, ICommonUser user) throws Exception {
		if (null != createList && createList.size() != 0) {
			Collection c = this.getCMRUserRegion();
			Iterator i = createList.iterator();

			try {
				EBUserRegionLocalHome home = this.getUserRegionLocalHome();

				while (i.hasNext()) {
					ICommonUserRegion ob = (ICommonUserRegion) i.next();
					if (ob != null) {
						long pk = Long.parseLong((new SequenceManager()).getSeqNum("USER_SEQ", true));
						ob.setUserID(user.getUserID());
						ob.setId(new Long(pk));
						EBUserRegionLocal local = home.create(ob);
						c.add(local);
					}
				}
			} catch (Exception var10) {
				var10.printStackTrace();
			}

		}
	}

	public void createDependants(ICommonUser value) throws UserException {
		this.updateDependants(value);
	}

	private void updateDependants(ICommonUser user) throws UserException {
		try {
			this.updateUserSegment(user);
			this.updateUserRegion(user);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new UserException(var3.getMessage());
		}
	}
}