//package com.integrosys.cms.batch.dormantUser;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.integrosys.cms.app.user.bus.StdUserDAO;
//
///**
// * <p>
// * Batch program to perform the computation of due date of recurrent and
// * covenant item, subsequently create sub items
// * 
// * Required parameter:
// * <ul>
// * <li>country
// * </ul>
// * 
// * @author hmbao
// * @author Chong Jun Yong
// * @since 2006/10/09 06:10:07
// */
//public class UserDormantMain  {
//
//
//
//
//
//
//	public static  void main(String[] args) {
//		
//System.out.println("IN main");
//		
//		executeInternal();
//	}
//
//	/**
//	 * Process recurrent due date computation
//	 */
//	private static void executeInternal() {
//		System.out.println("IN execute");
//		int updatedDormantUser = 0;
//		List loginIdList= new ArrayList();
//		List  list = (new StdUserDAO()).searchDormantNewUser();
//		if(list!=null){
//		String[] login_id= new String[list.size()];
//		for(int i=0;i<list.size();i++){
//			String login=(String)list.get(i);
//			loginIdList.add(login);
//		}
//		System.out.println("####################!!!!!!!!!!!!!!!!!!!!!!!!!######################");
//		if ((null != loginIdList) && (loginIdList.size() != 0)) {
//			for (int j = 0; j < loginIdList.size(); j++) {
//				try {
//					(new StdUserDAO()).updateDormantUser(String.valueOf(loginIdList.get(j)));
//					updatedDormantUser++;
//				}
//				catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		}
//		
//		System.out.println("####################!!!!!!!!!!!!!!!!!!!!!!!!!######################");
//
//	}
//}