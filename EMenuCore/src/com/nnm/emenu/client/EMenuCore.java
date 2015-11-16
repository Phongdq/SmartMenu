package com.nnm.emenu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.gwtphonegap.client.event.BackButtonPressedEvent;
import com.googlecode.gwtphonegap.client.event.BackButtonPressedHandler;
import com.googlecode.gwtphonegap.client.util.PhonegapUtil;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.widget.animation.AnimationWidget;
import com.googlecode.mgwt.ui.client.widget.dialog.ConfirmDialog;
import com.googlecode.mgwt.ui.client.widget.dialog.ConfirmDialog.ConfirmCallback;
import com.nnm.emenu.client.activities.AppActivityMapper;
import com.nnm.emenu.client.activities.AppPlaceHistoryMapper;
import com.nnm.emenu.client.activities.ClientFactory;
import com.nnm.emenu.client.activities.ClientFactoryImpl;
import com.nnm.emenu.client.activities.login.LoginPlace;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.UserInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EMenuCore implements EntryPoint {
	public static final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	public static final ClientFactory clientFactory = new ClientFactoryImpl();
	public static AppActivityMapper activityMapper = new AppActivityMapper(
			clientFactory);
	public static AppAnimationMapper animationMapper = new AppAnimationMapper();
	public static final ClientAutoPing clientAutoPing = new ClientAutoPing();
	AppPlaceHistoryMapper appPlaceHistoryMapper;
	public static UserInfo user = new UserInfo();
	public static int currentTable = -1;
	public static long dataVersion = -1;

	// use when complie mobile
//	public static final PhoneGap phoneGap = GWT.create(PhoneGap.class);
//	ConfirmDialog confirm;

	public void onModuleLoad() {
		Constants.setBuildMobile(false);

//		handlerPhoneGap();
//		handleQuitAppMobile();

		startApp();

		ClientManager.getInstance().getListFood();
		OrderManagerClient.getInstance().getListOrder();

		handleQuitApp();
		Constants.CalculatorSize(false);
		// RootLayoutPanel.get().add(new GChartExample02());
	}

//	void handlerPhoneGap() {
//		phoneGap.addHandler(new PhoneGapAvailableHandler() {
//
//			@Override
//			public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
//				if (phoneGap.isPhoneGapDevice()) {
//					PhonegapUtil.prepareService(
//							(ServiceDefTarget) greetingService, Constants.ROOT,
//							"emenucore/greet");
//				}
//			}
//		});
//		phoneGap.addHandler(new PhoneGapTimeoutHandler() {
//
//			@Override
//			public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
//				Window.alert("Something wrong happened");
//			}
//		});
//		phoneGap.initializePhoneGap();
//	}

	void startApp() {
		ViewPort viewPort = new MGWTSettings.ViewPort();
		// viewPort.setWidth(800).setHeight(480);
		viewPort.setWidthToDeviceWidth().setHeightToDeviceHeight();
		MGWTSettings settings = new MGWTSettings();
		settings.setFullscreen(true);
		settings.setViewPort(viewPort);
		viewPort.setUserScaleAble(true).setMinimumScale(0.5)
				.setMaximumScale(3.0).setInitialScale(1);
		MGWT.applySettings(settings);

		creatPhoneDisplay(clientFactory);
		appPlaceHistoryMapper = GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(
				appPlaceHistoryMapper);
		try {
			placeHistoryHandler.register(clientFactory.getPlaceController(),
					clientFactory.getEventBus(), new LoginPlace(
							Constants.LOGIN_PLACE));
		} catch (Exception ex) {
			Window.alert("PlaceHistoryHandler : " + ex.toString());
		}
		placeHistoryHandler.handleCurrentHistory();
	}

	void creatPhoneDisplay(ClientFactory clientFactory) {
		AnimationWidget display = new AnimationWidget();
		AnimatingActivityManager activityManager = new AnimatingActivityManager(
				activityMapper, animationMapper, clientFactory.getEventBus());
		activityManager.setDisplay(display);
		RootLayoutPanel.get().add(display);
		RootLayoutPanel.get().setPixelSize(Window.getClientWidth(),
				Window.getClientHeight());
		System.out.println("Width screen : " + Constants.WIDTH
				+ "\nHeight Screen : " + Constants.HEIGHT);
//		Window.alert("WIDTH : " + Constants.WIDTH + " HEIGHT : " + Constants.HEIGHT);
		Window.enableScrolling(true);
	}

//	void handleQuitAppMobile() {
//		confirm = new ConfirmDialog("Thoát", "Bạn chắc chắn muốn thoát",
//				new ConfirmCallback() {
//
//					@Override
//					public void onOk() {
//						if (user != null) {
//							greetingService.logout(user.getId(),
//									new AsyncCallback<Void>() {
//
//										@Override
//										public void onSuccess(Void result) {
//											System.out.println("Quit app");
//										}
//
//										@Override
//										public void onFailure(Throwable caught) {
//										}
//									});
//						}
//						phoneGap.exitApp();
//					}
//
//					@Override
//					public void onCancel() {
//						confirm.hide();
//					}
//				});
//		phoneGap.getEvent().getBackButton()
//				.addBackButtonPressedHandler(new BackButtonPressedHandler() {
//
//					@Override
//					public void onBackButtonPressed(BackButtonPressedEvent event) {
//						try {
//							LoginPlace place = (LoginPlace) clientFactory
//									.getPlaceController().getWhere();
//							phoneGap.exitApp();
//						} catch (Exception e) {
//							e.printStackTrace();
//							confirm.show();
//						}
//					}
//				});
//	}

	void handleQuitApp() {
		Window.addCloseHandler(new CloseHandler<Window>() {

			@Override
			public void onClose(CloseEvent<Window> event) {
				if (user != null) {
					greetingService.logout(user.getId(),
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									System.out.println("Quit app");
								}

								@Override
								public void onFailure(Throwable caught) {
								}
							});
				}
			}
		});
	}
}
