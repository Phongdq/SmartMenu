package com.nnm.emenu.shared.bean.handler;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.nnm.emenu.shared.bean.UserInfoBean;

public interface MyAutoBeanFactory extends AutoBeanFactory {
	AutoBean<UserInfoBean> message();
}
