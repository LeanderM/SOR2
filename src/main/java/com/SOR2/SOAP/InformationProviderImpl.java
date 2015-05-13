package com.SOR2.SOAP;

import com.SOR2.hibernate.HibernateMain;

public class InformationProviderImpl implements InformationProvider {

	@Override
	public String getMessageStatus(int messageID) {

		String status = HibernateMain.getStatusByMessage_ID(messageID);

		if (status.length() > 0) {
			return status;
		} else {
			return "";
		}

	}

}
