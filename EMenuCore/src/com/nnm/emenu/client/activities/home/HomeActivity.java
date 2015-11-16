package com.nnm.emenu.client.activities.home;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.nnm.emenu.client.ClientManager;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.OrderManagerClient;
import com.nnm.emenu.client.activities.foodmanager.FoodManagerPlace;
import com.nnm.emenu.client.activities.login.LoginPlace;
import com.nnm.emenu.client.activities.manageruser.ManagerUserPlace;
import com.nnm.emenu.client.activities.revenue.ReportedRevenuePlace;
import com.nnm.emenu.client.events.LoginSuccessEvent;
import com.nnm.emenu.client.events.LoginSuccessEventHandle;
import com.nnm.emenu.client.events.OrderDataReceiveEvent;
import com.nnm.emenu.client.events.OrderDataReceiveEventHandle;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.client.ultis.DateTime;
import com.nnm.emenu.client.utils.ui.ItemFood;
import com.nnm.emenu.client.utils.ui.ItemGroupFood;
import com.nnm.emenu.client.utils.ui.Loading;
import com.nnm.emenu.client.utils.ui.OrderChefBinder;
import com.nnm.emenu.client.utils.ui.OrderContentBinder;
import com.nnm.emenu.client.utils.ui.OrderItem;
import com.nnm.emenu.client.utils.ui.TableObject;
import com.nnm.emenu.shared.FoodCategory;
import com.nnm.emenu.shared.FoodInfo;
import com.nnm.emenu.shared.Order;
import com.nnm.emenu.shared.OrderData;
import com.nnm.emenu.shared.OrderDetail;
import com.nnm.emenu.shared.UserInfo;
import com.sun.org.apache.xpath.internal.operations.Or;

public class HomeActivity extends MGWTAbstractActivity {
	HomeView homeView;
	SimpleEventBus eventBus;
	boolean handlerItemGroupFood = false;
	boolean handlerItemFood = false;
	int current_catrgory_id = -1;

	public HomeActivity() {
		homeView = EMenuCore.clientFactory.getHomeView();
		eventBus = EMenuCore.clientFactory.getEventBus();
		handlerListener();
		addEventBusHandle();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
		// homeView.getOrderContent().btnChangeTable.setVisible(true);
		// }
		EMenuCore.clientAutoPing.run();
		panel.setWidget(homeView.asWidget());
		super.start(panel, eventBus);
	}

	void handlerListener() {
		homeView.getButtonEMenu().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				creatMenuContent(0, "THỰC ĐƠN");
			}
		});

		homeView.getButtonMenu().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				homeView.handlerMenuLeft();
			}
		});

		homeView.getButtonOrder().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				if (homeView.getOrderContent().state == OrderContentBinder.STATE_HOME) {
					homeView.getOrderContent().state = OrderContentBinder.STATE_ORDER_MANAGER;
				} else {
					homeView.getOrderContent().state = OrderContentBinder.STATE_HOME;
				}
				if (!homeView.isStateHome()) {
					homeView.backtoHome();
					homeView.getOrderContent().state = OrderContentBinder.STATE_ORDER_MANAGER;
				}
				if (OrderManagerClient.getInstance().getOrder(
						EMenuCore.currentTable) == null) {
					homeView.getOrderContent().setOrderTable(
							EMenuCore.currentTable);
				} else {
					if (EMenuCore.user.getState() == UserInfo.STATE_OFFLINE) {
						Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
						return;
					}
					homeView.getOrderContent().setOrderTable(
							OrderManagerClient.getInstance().getOrder(
									EMenuCore.currentTable));
					handlerButtonCancelOrderItem();

				}
			}
		});

		homeView.getSearchBox().handleIconSearch(new ClickListener() {

			@Override
			public void onClick() {
				if (homeView.getSearchBox().getText().replaceAll(" ", "")
						.equals("")) {
					Window.alert("Vui lòng nhập tên món ăn muốn tìm kiếm");
				} else {
					Loading.getInstance().show();
					EMenuCore.greetingService.search(EMenuCore.user.getId(),
							homeView.getSearchBox().getText(),
							new AsyncCallback<List<FoodInfo>>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(List<FoodInfo> result) {
									Loading.getInstance().hide();
									if (result == null) {
										Window.alert("Không có dữ liệu!");
										return;
									}
									createMenuContent(
											new ArrayList<FoodCategory>(),
											result, 0, "");
								}
							});
				}
			}
		});

		handlerTableListener();
		handleChangeTableListener();
		handleOrderDetail();

		homeView.getOrderContent().btnChangeTable
				.handleListener(new ClickListener() {

					@Override
					public void onClick() {
						if (EMenuCore.currentTable == -1) {
							Window.alert("Chưa có bàn nào được chọn!");
						} else {
							homeView.showChangeTable(EMenuCore.currentTable);
						}
					}
				});

		homeView.getOrderContent().handleButtonPaymentListener(
				new ClickListener() {

					@Override
					public void onClick() {
						homeView.getPaymentContent().setPaymentMoney(
								homeView.getOrderContent().total_money);
						homeView.getPaymentContent().setVAT(10);
						homeView.getPaymentContent().calculatorMoneyPayment();
						homeView.showPayment();
					}
				});

		homeView.getOrderContent().handleButtonCancelListener(
				new ClickListener() {

					@Override
					public void onClick() {
						if (EMenuCore.currentTable != -1) {
							if (OrderManagerClient.getInstance().getOrder(
									EMenuCore.currentTable) != null) {
								Loading.getInstance().show();
								EMenuCore.greetingService.cancelOrder(
										EMenuCore.currentTable,
										EMenuCore.user.getId(),
										new AsyncCallback<Integer>() {

											@Override
											public void onFailure(
													Throwable caught) {
												Loading.getInstance().hide();
											}

											@Override
											public void onSuccess(Integer result) {
												Loading.getInstance().hide();
												if (result == 0) {
													Window.alert("Hủy bàn không thành công!");
												} else if (result == 1) {
													homeView.changeStateTable(
															EMenuCore.currentTable,
															TableObject.STATE_EMPTY);
													OrderManagerClient
															.getInstance()
															.removerOrder(
																	EMenuCore.currentTable);
													homeView.getOrderContent()
															.setOrderTable(-1);
													EMenuCore.currentTable = -1;
													Window.alert("Hủy bàn thành công!");
												} else if (result == 2) {
													Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
												}
											}
										});
							} else {
								homeView.changeStateTable(
										EMenuCore.currentTable,
										TableObject.STATE_EMPTY);
								EMenuCore.currentTable = -1;
								homeView.getOrderContent().setOrderTable(-1);
							}
						}
					}
				});

		homeView.getOrderContent().handleButtonCancelListener(
				new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						homeView.getPopup().hide();
						homeView.getPaymentContent().clearTextBox();
					}
				});

		homeView.getPaymentContent().handleButtonCloseListener(
				new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						homeView.getPopup().hide();
						homeView.getPaymentContent().clearTextBox();
					}
				});

		homeView.getPaymentContent().handleButtonPaymentListener(
				new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						if (homeView.getPaymentContent().checkFullInfo()) {
							if (OrderManagerClient.getInstance().getOrder(
									EMenuCore.currentTable) == null) {
								Window.alert("Chưa có hóa đơn thanh toán");
								return;
							}
							EMenuCore.greetingService.payment(
									OrderManagerClient.getInstance()
											.getOrder(EMenuCore.currentTable)
											.getOrder().getId(),
									EMenuCore.user.getId(),
									EMenuCore.currentTable,
									homeView.getPaymentContent()
											.getMoneyTotal(),
									homeView.getPaymentContent()
											.getMoneyDiscount(),
									homeView.getPaymentContent().getVAT(),
									homeView.getPaymentContent()
											.getMoneyPayment(),
									homeView.getPaymentContent()
											.getMoneyCustomer(),
									homeView.getPaymentContent()
											.getMoneyReturn(),
									DateTime.getCurrentDate(DateTime.FORMAT_FULL),
									new AsyncCallback<Boolean>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub
											Window.alert("Có lỗi xảy ra!");
										}

										@Override
										public void onSuccess(Boolean result) {
											// TODO Auto-generated method stub
											if (result) {
												OrderManagerClient
														.getInstance()
														.removerOrder(
																EMenuCore.currentTable);
												homeView.getOrderContent()
														.setOrderTable(-1);
												homeView.getPopup().hide();
												homeView.getPaymentContent()
														.clearTextBox();
												homeView.changeStateTable(
														EMenuCore.currentTable,
														TableObject.STATE_EMPTY);
												Window.alert("Thanh toán thành công");
											} else {
												Window.alert("Thanh toán thất bại");
											}
										}
									});
						} else {
							Window.alert("Vui lòng nhập đầy đủ thông tin để thanh toán!");
						}
					}
				});

		homeView.getPaymentContent().handleButtonPrintListener(
				new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						Window.alert("in hoa don");
					}
				});

		homeView.getItemMenuUser().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				String role = "";
				if (EMenuCore.user.getRoleId() == UserInfo.ROLE_ADMIN) {
					role = "Admin";
				} else if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
					role = "Đầu bếp";
				} else if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
					role = "Phục vụ";
				}

				Window.alert("Thông tin người dùng : \n\n\n"
						+ "Họ tên : "
						+ EMenuCore.user.getName()
						+ "\nTên đăng nhập : "
						+ EMenuCore.user.getUsername()
						+ "\nChức danh : "
						+ role
						+ "\nTrạng thái : "
						+ (EMenuCore.user.getState() == UserInfo.STATE_ONLINE ? "Online"
								: "Offline"));
			}
		});

		homeView.getItemMenuManager().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				if (EMenuCore.user.getRoleId() == UserInfo.ROLE_ADMIN) {
					Loading.getInstance().show();
					EMenuCore.clientFactory.getPlaceController().goTo(
							new FoodManagerPlace(Constants.ADDFOOD_PLACE));
				} else {
					Window.alert("Chức năng này chỉ dành cho Admin");
				}
			}
		});

		homeView.getItemMenuUpdate().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				Loading.getInstance().show();
				ClientManager.getInstance().updateListFood();
			}
		});

		homeView.getItemManagerUser().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				if (EMenuCore.user.getRoleId() == UserInfo.ROLE_ADMIN) {
					EMenuCore.clientFactory.getPlaceController().goTo(
							new ManagerUserPlace(Constants.MANAGER_USER_PLACE));
				} else {
					Window.alert("Chức năng này chỉ dành cho Admin");
				}
			}
		});

		homeView.getItemMenuReport().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				if (EMenuCore.user.getRoleId() == UserInfo.ROLE_ADMIN) {
					EMenuCore.clientFactory.getPlaceController().goTo(
							new ReportedRevenuePlace(
									Constants.REPORTED_RENEVUE_PLACE));
				} else {
					Window.alert("Chức năng này chỉ dành cho Admin");
				}
			}
		});

		homeView.getItemMenuInfo().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				Window.alert("Ứng dụng EMenu - Quản lý và đặt món trong nhà hàng\n\n\nSV Thực hiện : Nguyễn Ngọc Minh - CNTT2-K55-BKHN\nGV Hướng dẫn : TS Nguyễn Thanh Hùng");
			}
		});

		homeView.getItemMenuLogout().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				EMenuCore.greetingService.logout(EMenuCore.user.getId(),
						new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub
								EMenuCore.clientFactory.getPlaceController()
										.goTo(new LoginPlace(
												Constants.LOGIN_PLACE));
								// EMenuCore.activityMapper.clearHomeActivity();
								// EMenuCore.clientFactory.clearHomeView();
								EMenuCore.clientAutoPing.cancel();
								EMenuCore.dataVersion = -1;
							}
						});
			}
		});
	}

	void handleChangeTableListener() {
		homeView.getChangeTableBinder().handleButtonOk(new ClickListener() {

			@Override
			public void onClick() {
				if (homeView.getChangeTableBinder().getNewTable() == -1) {
					Window.alert("Vui lòng nhập số bàn muốn chuyển đến!");
					return;
				}
				if (OrderManagerClient.getInstance().getOrder(
						homeView.getChangeTableBinder().getNewTable()) != null) {
					Window.alert("Bàn bạn muốn chuyển đã có người ngồi!");
					return;
				}
				if (OrderManagerClient.getInstance().getOrder(
						EMenuCore.currentTable) == null) {
					homeView.changeStateTable(EMenuCore.currentTable,
							TableObject.STATE_EMPTY);
					EMenuCore.currentTable = homeView.getChangeTableBinder()
							.getNewTable();
					homeView.changeStateTable(EMenuCore.currentTable,
							TableObject.STATE_HAVE_NOTIFY);
					homeView.getOrderContent().setOrderTable(
							EMenuCore.currentTable);
					homeView.getPopup().hide();
					return;
				}
				EMenuCore.greetingService.changeTable(OrderManagerClient
						.getInstance().getOrder(EMenuCore.currentTable)
						.getOrder().getId(), homeView.getChangeTableBinder()
						.getNewTable(), EMenuCore.user.getId(),
						new AsyncCallback<Integer>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Integer result) {
								if (result == 0) {
									Window.alert("Bạn phải đăng nhập để sử dụng chức năng này!");
								} else {
									OrderManagerClient
											.getInstance()
											.changeOrdertable(
													EMenuCore.currentTable,
													homeView.getChangeTableBinder()
															.getNewTable());
									homeView.changeStateTable(
											EMenuCore.currentTable,
											TableObject.STATE_EMPTY);
									EMenuCore.currentTable = homeView
											.getChangeTableBinder()
											.getNewTable();
									homeView.changeStateTable(
											EMenuCore.currentTable,
											TableObject.STATE_HAVE_NOTIFY);
									homeView.getOrderContent()
											.setOrderTable(
													OrderManagerClient
															.getInstance()
															.getOrder(
																	EMenuCore.currentTable));
									homeView.getPopup().hide();
								}

							}
						});
			}
		});

		homeView.getChangeTableBinder().handleButtonCancel(new ClickListener() {

			@Override
			public void onClick() {
				homeView.getPopup().hide();
			}
		});
	}

	void createMenuContent(List<FoodCategory> listFoodCategory,
			List<FoodInfo> listFoodInfo, int parent_id, final String title) {
		homeView.showMenuContent();
		homeView.getMenuContentBinder().creatMenuContent(
				(ArrayList<FoodCategory>) listFoodCategory,
				(ArrayList<FoodInfo>) listFoodInfo, parent_id, title);
		handlerItemGroupFood();
		handlerItemFood();
		handlerButtonMenuHeader();
		homeView.setStateEMenu();
		homeView.getMenuContentBinder().removeHistory();
		homeView.getMenuContentBinder().addHistory(parent_id,
				(parent_id == 0 ? "THỰC ĐƠN" : title));
	}

	void creatMenuContent(final int parent_id, final String title) {
		Loading.getInstance().show();
		EMenuCore.greetingService.getFoodCategory(parent_id,
				new AsyncCallback<List<FoodCategory>>() {

					@Override
					public void onSuccess(List<FoodCategory> result) {
						homeView.showMenuContent();
						homeView.getMenuContentBinder().creatMenuContent(
								(ArrayList<FoodCategory>) result,
								ClientManager.getInstance().getFoodByCategoty(
										parent_id), parent_id, title);
						handlerItemGroupFood();
						handlerItemFood();
						handlerButtonMenuHeader();
						homeView.setStateEMenu();
						homeView.getMenuContentBinder().addHistory(parent_id,
								(parent_id == 0 ? "THỰC ĐƠN" : title));
						Loading.getInstance().hide();
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Loading.getInstance().hide();
						Window.alert("Failure : " + caught.getMessage());
					}
				});
	}

	void handlerButtonMenuHeader() {
		homeView.getMenuContentBinder().handlerButtonHeaderListener(
				homeView.getButtonMenuHeader(), new ClickListener() {

					@Override
					public void onClick() {
						if (homeView.getMenuContentBinder().listParentIdSize() == 1) {
							homeView.backtoHome();
							homeView.getMenuContentBinder().removeLast();
						} else {
							homeView.getMenuContentBinder().removeLast();
							creatMenuContent(homeView.getMenuContentBinder()
									.lastParentId(), homeView
									.getMenuContentBinder().lastTitle());
							homeView.getMenuContentBinder().removeLast();
						}
					}
				});
	}

	void handlerItemGroupFood() {
		for (int i = 0; i < homeView.getMenuContentBinder().listItemGroupFood
				.size(); i++) {
			final ItemGroupFood item = homeView.getMenuContentBinder().listItemGroupFood
					.get(i);
			item.hasListener(item, new ClickListener() {

				@Override
				public void onClick() {
					creatMenuContent(item.foodCategory.getId(),
							item.foodCategory.getTitle());
				}
			});
		}
	}

	void handlerItemFood() {
		for (int i = 0; i < homeView.getMenuContentBinder().listItemFood.size(); i++) {
			final ItemFood item = homeView.getMenuContentBinder().listItemFood
					.get(i);
			item.hasListener(item, new ClickListener() {

				@Override
				public void onClick() {
					if (EMenuCore.currentTable == -1) {
						Window.alert("Chọn bàn cần đặt món!!!");
					} else {
						if (EMenuCore.user.getState() == UserInfo.STATE_OFFLINE) {
							Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
							return;
						}
						homeView.showOrderDetail(EMenuCore.currentTable,
								item.id, item.titleFood.getText(), item.price);
						// final String time = DateTime
						// .getCurrentDate(DateTime.FORMAT_FULL);
						// EMenuCore.greetingService.order(EMenuCore.currentTable,
						// EMenuCore.user.getId(), item.id, 1, item.price,
						// time, new AsyncCallback<OrderDetail>() {
						//
						// @Override
						// public void onSuccess(OrderDetail result) {
						// if (result == null) {
						// Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
						// return;
						// }
						// if (OrderManagerClient.getInstance()
						// .getOrder(
						// EMenuCore.currentTable) == null) {
						// OrderData orderData = new OrderData();
						// orderData.setOrder(new Order(
						// EMenuCore.currentTable,
						// time));
						// OrderManagerClient.getInstance()
						// .addNewOrder(orderData);
						//
						// }
						// OrderManagerClient
						// .getInstance()
						// .getOrder(
						// EMenuCore.currentTable)
						// .addNewOrderDetail(result);
						// homeView.getOrderContent()
						// .setOrderTable(
						// OrderManagerClient
						// .getInstance()
						// .getOrder(
						// EMenuCore.currentTable));
						// handlerButtonCancelOrderItem();
						// }
						//
						// @Override
						// public void onFailure(Throwable caught) {
						// // TODO Auto-generated method stub
						// Window.alert("Failure : "
						// + caught.getMessage());
						// }
						// });
					}
				}
			});
		}
	}

	void handleOrderDetail() {
		homeView.getOrderDetailBinder().handleButtonAdd(new ClickListener() {

			@Override
			public void onClick() {
				if (homeView.getOrderDetailBinder().getAmount() == 0) {
					Window.alert("Số lượng phải là chữ số và lớn hơn 0");
					return;
				}
				final String time = DateTime
						.getCurrentDate(DateTime.FORMAT_FULL);
				EMenuCore.greetingService.order(EMenuCore.currentTable,
						EMenuCore.user.getId(),
						homeView.getOrderDetailBinder().id, homeView
								.getOrderDetailBinder().getAmount(),
						homeView.getOrderDetailBinder().price
								* homeView.getOrderDetailBinder().getAmount(),
						time, new AsyncCallback<OrderDetail>() {

							@Override
							public void onSuccess(OrderDetail result) {
								if (result == null) {
									Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
									return;
								} else {
									if (OrderManagerClient.getInstance()
											.getOrder(EMenuCore.currentTable) == null) {
										OrderData orderData = new OrderData();
										orderData.setOrder(new Order(
												EMenuCore.currentTable, time));
										OrderManagerClient.getInstance()
												.addNewOrder(orderData);

									}
									OrderManagerClient.getInstance()
											.getOrder(EMenuCore.currentTable)
											.addNewOrderDetail(result);
									homeView.getOrderContent()
											.setOrderTable(
													OrderManagerClient
															.getInstance()
															.getOrder(
																	EMenuCore.currentTable));
									handlerButtonCancelOrderItem();
									homeView.getPopup().hide();
								}
							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("Failure : " + caught.getMessage());
							}
						});
			}
		});

		homeView.getOrderDetailBinder().handleButtonCancel(new ClickListener() {

			@Override
			public void onClick() {
				homeView.getPopup().hide();
			}
		});
	}

	void handlerButtonCancelOrderItem() {
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
			if (homeView.getOrderContent().state == OrderContentBinder.STATE_HOME) {
				for (int i = 0; i < homeView.getOrderContent().orderContent
						.getWidgetCount(); i++) {
					final OrderItem item = (OrderItem) homeView
							.getOrderContent().orderContent.getWidget(i);
					item.hasListener(item, new ClickListener() {

						@Override
						public void onClick() {
							if (item.amount == 0)
								return;
							EMenuCore.greetingService.changeAmountOrderDetail(
									EMenuCore.currentTable, item.id,
									item.amount - 1, EMenuCore.user.getId(),
									new AsyncCallback<Integer>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(Integer result) {
											if (result == 1) {
												item.setAmount(item.amount - 1);
												OrderManagerClient
														.getInstance()
														.changeAmountOrderDetail(
																EMenuCore.currentTable,
																item.id,
																item.amount);
												homeView.getOrderContent()
														.refreshTotalMoney();
												if (item.amount == 0) {
													homeView.getOrderContent().orderContent
															.remove(item);
												}
											} else if (result == 0) {
												Window.alert("Bạn phải đăng nhập trước khi thực thiện chức năng này!");
											} else {
												Window.alert("Bạn không thể hủy món này do nhà bếp đã chuẩn bị!");
											}
										}
									});
						}
					});
				}
			} else if (homeView.getOrderContent().state == OrderContentBinder.STATE_ORDER_MANAGER) {
				for (int i = 0; i < homeView.getOrderContent().orderContent
						.getWidgetCount(); i++) {
					final OrderChefBinder item = (OrderChefBinder) homeView
							.getOrderContent().orderContent.getWidget(i);
					item.hasButtonCancelListener(item, new ClickListener() {

						@Override
						public void onClick() {
							if (item.amount == 0)
								return;
							EMenuCore.greetingService.changeAmountOrderDetail(
									EMenuCore.currentTable, item.id,
									item.amount - 1, EMenuCore.user.getId(),
									new AsyncCallback<Integer>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(Integer result) {
											if (result == 1) {
												item.setAmount(item.amount - 1);
												OrderManagerClient
														.getInstance()
														.changeAmountOrderDetail(
																EMenuCore.currentTable,
																item.id,
																item.amount);
												homeView.getOrderContent()
														.refreshTotalMoney();
												if (item.amount == 0) {
													homeView.getOrderContent().orderContent
															.remove(item);
												}
											} else if (result == 0) {
												Window.alert("Bạn phải đăng nhập trước khi thực thiện chức năng này!");
											} else {
												Window.alert("Bạn không thể hủy món này do nhà bếp đã chuẩn bị!");
											}
										}
									});
						}
					});
				}
			}
		} else if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
			for (int i = 0; i < homeView.getOrderContent().orderContent
					.getWidgetCount(); i++) {
				final OrderChefBinder item = (OrderChefBinder) homeView
						.getOrderContent().orderContent.getWidget(i);
				item.hasButtonConfirmListener(item, new ClickListener() {

					@Override
					public void onClick() {
						EMenuCore.greetingService.changeStateOrder(
								EMenuCore.currentTable, item.id,
								OrderDetail.STATE_WAIT_COMPLETE,
								EMenuCore.user.getId(),
								new AsyncCallback<Integer>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onSuccess(Integer result) {
										if (result == 0) {
											Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
											return;
										} else {
											item.setStateWaitComplete();
											OrderManagerClient
													.getInstance()
													.changeStateOrderDetail(
															EMenuCore.currentTable,
															item.id,
															OrderDetail.STATE_WAIT_COMPLETE);
										}

									}
								});
					}
				});

				item.hasButtonCancelStateListener(item, new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						EMenuCore.greetingService.changeStateOrder(
								EMenuCore.currentTable, item.id,
								OrderDetail.STATE_REJECT,
								EMenuCore.user.getId(),
								new AsyncCallback<Integer>() {

									@Override
									public void onFailure(Throwable caught) {
									}

									@Override
									public void onSuccess(Integer result) {
										if (result == 0) {
											Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
											return;
										} else {
											item.setStateReject();
											OrderManagerClient
													.getInstance()
													.changeStateOrderDetail(
															EMenuCore.currentTable,
															item.id,
															OrderDetail.STATE_REJECT);
										}

									}
								});
					}
				});

				item.hasButtonCompleteListener(item, new ClickListener() {

					@Override
					public void onClick() {
						if (item.state == OrderDetail.STATE_REJECT
								|| item.state == OrderDetail.STATE_CANCEL) {
							return;
						}
						EMenuCore.greetingService.changeStateOrder(
								EMenuCore.currentTable, item.id,
								OrderDetail.STATE_COMPLETE,
								EMenuCore.user.getId(),
								new AsyncCallback<Integer>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onSuccess(Integer result) {
										if (result == 0) {
											Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
											return;
										} else {
											item.setStateComplete();
											OrderManagerClient
													.getInstance()
													.changeStateOrderDetail(
															EMenuCore.currentTable,
															item.id,
															OrderDetail.STATE_COMPLETE);
										}

									}
								});
					}
				});
			}
		}
	}

	void handlerTableListener() {
		for (int i = 0; i < ClientManager.getInstance().getNumber_table(); i++) {
			final TableObject table = homeView.getTable(i);
			table.handlerListener(new ClickListener() {

				@Override
				public void onClick() {
					if (OrderManagerClient.getInstance().getOrder(
							table.getNumber()) == null) {
						if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
							Loading.getInstance().show();
							EMenuCore.greetingService.createOrder(table
									.getNumber(), DateTime
									.getCurrentDate("yyyy-MM-dd hh:mm:ss"),
									EMenuCore.user.getId(),
									new AsyncCallback<Order>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub
											Loading.getInstance().hide();
											Window.alert("Có lỗi xảy ra!");
										}

										@Override
										public void onSuccess(Order result) {
											// TODO Auto-generated method stub
											Loading.getInstance().hide();
											if (result == null) {
												Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
											} else {
												OrderData orderData = new OrderData();
												orderData.setOrder(result);
												OrderManagerClient
														.getInstance()
														.addNewOrder(orderData);
												homeView.changeStateTable(
														table.getNumber(),
														TableObject.STATE_NOT_EMPTY);
												EMenuCore.currentTable = table
														.getNumber();
												homeView.getOrderContent()
														.setOrderTable(
																OrderManagerClient
																		.getInstance()
																		.getOrder(
																				table.getNumber()));
											}
										}
									});
						} else if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
							Window.alert("Bàn hiện tại chưa có khách!");
						} else {
							homeView.getOrderContent().setOrderTable(
									table.getNumber());
						}
					} else {
						homeView.getOrderContent().setOrderTable(
								OrderManagerClient.getInstance().getOrder(
										table.getNumber()));
						handlerButtonCancelOrderItem();
						homeView.changeStateTable(table.getNumber(),
								TableObject.STATE_NOT_EMPTY);
						EMenuCore.currentTable = table.getNumber();
					}
					// creatMenuContent(0, "THỰC ĐƠN");
				}
			});
		}
	}

	void addEventBusHandle() {
		eventBus.addHandler(OrderDataReceiveEvent.TYPE,
				new OrderDataReceiveEventHandle() {

					@Override
					public void receive(OrderDataReceiveEvent event) {
						ArrayList<OrderData> result = event.getOrders();
						for (int i = 0; i < ClientManager.getInstance()
								.getNumber_table(); i++) {
							if (OrderManagerClient.getInstance()
									.getOrder(i + 1) != null
									&& !isContain(i + 1, result)) {
								OrderManagerClient.getInstance().removerOrder(
										i + 1);
								homeView.changeStateTable(i + 1,
										TableObject.STATE_EMPTY);
								if (EMenuCore.currentTable == i + 1) {
									homeView.getOrderContent()
											.setOrderTable(-1);
								}
							}
						}
						for (int i = 0; i < result.size(); i++) {
							OrderData order = OrderManagerClient
									.getInstance()
									.getOrder(
											result.get(i).getOrder().getTable());
							if (order == null) {
								OrderManagerClient.getInstance().addNewOrder(
										result.get(i));
								order = OrderManagerClient.getInstance()
										.getOrder(
												result.get(i).getOrder()
														.getTable());
								homeView.changeStateTable(order.getOrder()
										.getTable(),
										TableObject.STATE_HAVE_NOTIFY);
							}
							if (result.get(i).getVersion() != order
									.getVersion()) {
								System.out.println(EMenuCore.user.getName()
										+ " ---- OrderDataV2 : " + i
										+ " : change version");
								OrderManagerClient.getInstance().changeOrder(
										order.getOrder().getTable(),
										result.get(i));
								homeView.changeStateTable(order.getOrder()
										.getTable(),
										TableObject.STATE_HAVE_NOTIFY);
							}
						}
					}
				});
		eventBus.addHandler(LoginSuccessEvent.TYPE,
				new LoginSuccessEventHandle() {

					@Override
					public void success(LoginSuccessEvent event) {
						// TODO Auto-generated method stub
						homeView.setUserTitle(event.getUser().getUsername());
						if (event.getUser().getRoleId() == UserInfo.ROLE_CHEF) {
							homeView.getOrderContent().btnPayment
									.setVisible(false);
							homeView.getOrderContent().btnCancel
									.setVisible(false);
							homeView.getButtonOrder().btn.setDisabled(true);
							homeView.getButtonOrder().icon
									.addClickHandler(new ClickHandler() {

										@Override
										public void onClick(ClickEvent event) {
											// TODO Auto-generated method stub

										}
									});
						} else if (event.getUser().getRoleId() == UserInfo.ROLE_SERVER) {
							homeView.getOrderContent().btnChangeTable
									.setVisible(true);
						} else {
							homeView.getOrderContent().btnChangeTable
									.setVisible(false);
							homeView.getOrderContent().btnPayment
									.setVisible(false);
							homeView.getOrderContent().btnCancel
									.setVisible(false);
						}
						homeView.setMenuLeftPermission();
					}
				});
	}

	boolean isContain(int table, ArrayList<OrderData> orders) {
		for (int i = 0; i < orders.size(); i++) {
			if (table == orders.get(i).getOrder().getTable()) {
				return true;
			}
		}
		return false;
	}
}
