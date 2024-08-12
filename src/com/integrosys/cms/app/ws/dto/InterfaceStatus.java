package com.integrosys.cms.app.ws.dto;

public enum InterfaceStatus {
		
		SUCCESS {
			@Override
			public String toString() {
				return "Success";
			}
		},
		FAILURE {
			@Override
			public String toString() {
				return "Failure";
			}
		},
		ERRORINDOWNLOAD {
			@Override
			public String toString() {
				return "Error in Download";
			}
		}, 
		
		ERRORINREADING {
			@Override
			public String toString() {
				return "Error in Reading";
			}
		}, 
		ERRORINUPDATE {
			@Override
			public String toString() {
				return "Error in Update";
			}
		}, 
		ERRORINDELETIONOFCHECKFILE {
			@Override
			public String toString() {
				return "Error in Deletion of Check File";
			}
		}; 
	}


