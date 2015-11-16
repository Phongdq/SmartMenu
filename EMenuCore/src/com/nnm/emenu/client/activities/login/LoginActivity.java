package com.nnm.emenu.client.activities.login;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.nnm.emenu.client.ClientManager;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.OrderManagerClient;
import com.nnm.emenu.client.activities.home.HomePlace;
import com.nnm.emenu.client.events.LoginSuccessEvent;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.client.utils.ui.Loading;
import com.nnm.emenu.shared.UserInfo;

public class LoginActivity extends MGWTAbstractActivity {
	LoginView loginView;

	public LoginActivity() {
		super();
		loginView = EMenuCore.clientFactory.getLoginView();
		handlerLoginClickListener();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// panel.setWidget(new HomeViewImplement());
		panel.setWidget(loginView.asWidget());
		super.start(panel, eventBus);
	}

	void handlerLoginClickListener() {
		loginView.hasLoginListener(new ClickListener() {

			@Override
			public void onClick() {
				if (loginView.getUserName().equals("")) {
					Window.alert("Nhập tài khoản để đăng nhập!");
				} else if (loginView.getPassWord().equals("")) {
					Window.alert("Nhập mật khẩu để đăng nhập!");
				} else {
					Loading.getInstance().show();
					EMenuCore.greetingService.login(loginView.getUserName(),
							loginView.getPassWord(),
							new AsyncCallback<UserInfo>() {

								@Override
								public void onSuccess(UserInfo result) {
									Loading.getInstance().hide();
									if (result != null) {
										if (result.getId() == -1) {
											Window.alert("Tài khoản đang đăng nhập ở một thiết bị khác!");
											return;
										} else {
											ClientManager.getInstance()
													.getListFood();
											OrderManagerClient.getInstance()
													.getListOrder();
											EMenuCore.clientAutoPing.refresh();
											EMenuCore.user = result;
											EMenuCore.clientFactory
													.getPlaceController()
													.goTo(new HomePlace(
															Constants.HOME_PLACE));
											EMenuCore.clientFactory
											.getEventBus()
											.fireEvent(
													new LoginSuccessEvent(
															result));
										}
									} else if (result == null) {
										Window.alert("Tài khoản hoặc mật khẩu không chính xác!");
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Có lỗi xảy ra trong quá trình đăng nhập!!!");
									caught.printStackTrace();
								}
							});
				}
			}
		});
	}
}
