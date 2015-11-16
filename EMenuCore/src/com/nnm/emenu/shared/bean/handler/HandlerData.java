package com.nnm.emenu.shared.bean.handler;

import com.google.gwt.core.shared.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.nnm.emenu.shared.bean.UserInfoBean;

public class HandlerData {

	static MyAutoBeanFactory myAutoBeanFactory = GWT
			.create(MyAutoBeanFactory.class);

	public static UserInfoBean creatUserInfo() {
		AutoBean<UserInfoBean> message = myAutoBeanFactory.message();
		return message.as();
	}

	public static String serializeMessageToJson(UserInfoBean userinfo) {
		AutoBean<UserInfoBean> bean = AutoBeanUtils.getAutoBean(userinfo);
		if (bean == null)
			System.out.println("bean null");
		String y = AutoBeanCodex.encode(bean).getPayload();
		return y;
	}

	public static UserInfoBean deserializeJsonToMessage(String json) {
		AutoBean<UserInfoBean> bean = AutoBeanCodex.decode(myAutoBeanFactory,
				UserInfoBean.class, json);
		return (UserInfoBean) bean.as();
	}
}
