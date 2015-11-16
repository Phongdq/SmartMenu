package com.nnm.emenu.client.activities.manageruser;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.activities.home.HomePlace;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.client.utils.ui.ItemUserRow;
import com.nnm.emenu.client.utils.ui.Loading;
import com.nnm.emenu.shared.UserInfo;

public class ManagerUserActivity extends MGWTAbstractActivity {
	ManagerUserVew managerUserView;

	public ManagerUserActivity() {
		super();
		managerUserView = EMenuCore.clientFactory.getManagerUserVew();
		handleListener();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(managerUserView.asWidget());
		Loading.getInstance().show();
		requestGetListUser();
		super.start(panel, eventBus);
	}

	void requestGetListUser() {
		EMenuCore.greetingService
				.getListUser(new AsyncCallback<List<UserInfo>>() {

					@Override
					public void onSuccess(List<UserInfo> result) {
						Loading.getInstance().hide();
						managerUserView.createListUser(result);
						handleListUserListener();
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Có lỗi xảy ra!!!");
						Loading.getInstance().hide();
					}
				});
	}

	void handleListUserListener() {
		managerUserView.handleButtonHomeListener(new ClickListener() {

			@Override
			public void onClick() {
				EMenuCore.clientFactory.getPlaceController().goTo(
						new HomePlace(Constants.HOME_PLACE));
			}
		});

		for (int i = 1; i < managerUserView.getScrollContent().getWidgetCount(); i++) {
			final ItemUserRow item = (ItemUserRow) managerUserView
					.getScrollContent().getWidget(i);
			item.btnEdit.handleListener(new ClickListener() {

				@Override
				public void onClick() {
					managerUserView.getAddUserInfoBinder().tbName
							.setText(item.lbName.getText());
					managerUserView.getAddUserInfoBinder().tbUserName
							.setText(item.lbUserName.getText());
					managerUserView.getAddUserInfoBinder().tbUserName
							.setEnabled(false);
					managerUserView.getAddUserInfoBinder().tbPassWord
							.setText(item.lbPassWord.getText());
					managerUserView.getAddUserInfoBinder().ltbRole
							.setSelectedIndex(item.user.getRoleId() - 1);
					managerUserView.getAddUserInfoBinder().setStateUpdateUser();
					managerUserView.getPopUp().show();
				}
			});
			item.btnDelete.handleListener(new ClickListener() {

				@Override
				public void onClick() {
					EMenuCore.greetingService.deleteUser(
							EMenuCore.user.getId(), item.lbUserName.getText(),
							new AsyncCallback<Integer>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(Integer result) {
									if (result == 0) {
										Window.alert("Xóa thất bại!");
									} else if (result == 1) {
										Window.alert("Xóa thành công!");
										requestGetListUser();
									} else if (result == -1) {
										Window.alert("Vui lòng đăng nhập trước khi thực hiện thao tác này!");
									}
								}
							});
				}
			});
		}
	}

	void handleListener() {
		managerUserView.handleButtonAddListener(new ClickListener() {

			@Override
			public void onClick() {
				managerUserView.getAddUserInfoBinder().setStateNewUser();
				managerUserView.getAddUserInfoBinder().tbUserName
						.setEnabled(true);
				managerUserView.getAddUserInfoBinder().reset();
				managerUserView.getPopUp().show();
			}
		});

		managerUserView.getAddUserInfoBinder().handleButtonAddListener(
				new ClickListener() {

					@Override
					public void onClick() {
						if (managerUserView.getAddUserInfoBinder().state == managerUserView
								.getAddUserInfoBinder().STATE_NEW_USER) {
							EMenuCore.greetingService.registerUser(
									managerUserView.getAddUserInfoBinder().tbName
											.getText(), managerUserView
											.getAddUserInfoBinder().tbUserName
											.getText(), managerUserView
											.getAddUserInfoBinder().tbPassWord
											.getText(),
									Integer.valueOf(managerUserView
											.getAddUserInfoBinder().ltbRole
											.getSelectedValue()),
									new AsyncCallback<Integer>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(Integer result) {
											if (result == 0) {
												Window.alert("Tài khoản đã tồn tại!!!");
											} else if (result == 1) {
												managerUserView.getPopUp()
														.hide();
												managerUserView
														.getAddUserInfoBinder()
														.reset();
												Window.alert("Đăng ký thành công!");
												requestGetListUser();
											}
										}
									});
						} else if (managerUserView.getAddUserInfoBinder().state == managerUserView
								.getAddUserInfoBinder().STATE_UPDATE_USER) {
							EMenuCore.greetingService.updateUser(
									EMenuCore.user.getId(),
									managerUserView.getAddUserInfoBinder().tbName
											.getText(),
									managerUserView.getAddUserInfoBinder().tbUserName
											.getText(), managerUserView
											.getAddUserInfoBinder().tbPassWord
											.getText(),
									Integer.valueOf(managerUserView
											.getAddUserInfoBinder().ltbRole
											.getSelectedValue()),
									new AsyncCallback<Integer>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(Integer result) {
											if (result == 0) {
												Window.alert("Cập nhật thông tin thất bại!!!");
											} else if (result == 1) {
												managerUserView.getPopUp()
														.hide();
												managerUserView
														.getAddUserInfoBinder()
														.reset();
												Window.alert("Cập nhật thông tin thành công!");
												requestGetListUser();
											} else if (result == -1) {
												Window.alert("Vui lòng đăng nhập trước khi thực hiện thao tác này!");
											}
										}
									});
						}
					}
				});
		managerUserView.getAddUserInfoBinder().btnClose
				.handleListener(new ClickListener() {

					@Override
					public void onClick() {
						managerUserView.getPopUp().hide();
					}
				});

	}
}
