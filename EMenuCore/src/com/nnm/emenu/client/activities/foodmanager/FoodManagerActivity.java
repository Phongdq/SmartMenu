package com.nnm.emenu.client.activities.foodmanager;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.widget.image.ImageHolder;
import com.nnm.emenu.client.ClientManager;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.activities.home.HomePlace;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.client.ultis.DateTime;
import com.nnm.emenu.client.utils.ui.AddProductBinder;
import com.nnm.emenu.client.utils.ui.ItemCategory;
import com.nnm.emenu.client.utils.ui.ItemProduct;
import com.nnm.emenu.client.utils.ui.Loading;
import com.nnm.emenu.shared.FoodCategory;
import com.nnm.emenu.shared.FoodInfo;

public class FoodManagerActivity extends MGWTAbstractActivity {
	FoodManagerView addfoodView;

	public FoodManagerActivity() {
		super();
		addfoodView = EMenuCore.clientFactory.getFoodManagerView();
		handleListener();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		getListCategory();
		panel.setWidget(addfoodView.asWidget());
		super.start(panel, eventBus);
	}

	void getListCategory() {
		EMenuCore.greetingService.getFoodCategory(-1,
				new AsyncCallback<List<FoodCategory>>() {

					@Override
					public void onSuccess(List<FoodCategory> result) {
						System.out.println("List Food Category : "
								+ result.size());
						addfoodView.getProductHome().listFoodCategory = result;
						addfoodView.getProductHome().creat();
						handleItemProductHome();
						addfoodView
								.setSelected(FoodManagerViewImpl.TAB_PRODUCT);
						Loading.getInstance().hide();
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}
				});
	}

	void handleListener() {
		addfoodView.handleButtonHome(new ClickListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				EMenuCore.clientFactory.getPlaceController().goTo(
						new HomePlace(Constants.HOME_PLACE));
			}
		});

		addfoodView.handleButtonProduct(new ClickListener() {

			@Override
			public void onClick() {
				Loading.getInstance().show();
				addfoodView.getProductHome().creat();
				addfoodView.setSelected(FoodManagerViewImpl.TAB_PRODUCT);
				handleItemProductHome();
				Loading.getInstance().hide();
			}
		});

		addfoodView.handleButtonMaterial(new ClickListener() {

			@Override
			public void onClick() {
				addfoodView.setSelected(FoodManagerViewImpl.TAB_MATERIAL);
			}
		});

		addfoodView.handleButtonExpenses(new ClickListener() {

			@Override
			public void onClick() {
				addfoodView.setSelected(FoodManagerViewImpl.TAB_EXPENSES);
			}
		});

		addfoodView.handleButtonOrder(new ClickListener() {

			@Override
			public void onClick() {
				addfoodView.setSelected(FoodManagerViewImpl.TAB_ORDER);
			}
		});

		addfoodView.getProductHome().handleButtonProduct(new ClickListener() {

			@Override
			public void onClick() {
				addfoodView.getProductHome().addProductBinder
						.createListBoxParent(addfoodView.getProductHome().listFoodCategory);
				addfoodView.getProductHome().addProductBinder.setStateNew();
				addfoodView.getProductHome().showProduct();
			}
		});

		addfoodView.getProductHome().handleButtonCategory(new ClickListener() {

			@Override
			public void onClick() {
				Loading.getInstance().show();
				addfoodView.getProductHome().addCategoryBinder.setStateNew();
				addfoodView.getProductHome().addCategoryBinder
						.create(addfoodView.getProductHome().listFoodCategory);
				addfoodView.getProductHome().showCategory();
				handleItemCategoryListener();
				Loading.getInstance().hide();
			}
		});

		addfoodView.getSearchBox().handleIconSearch(new ClickListener() {

			@Override
			public void onClick() {
				if (addfoodView.getSearchBox().getText().replaceAll(" ", "")
						.equals("")) {
					Loading.getInstance().show();
					addfoodView.getProductHome().creat();
					handleItemProductHome();
				} else {
					Loading.getInstance().show();
					EMenuCore.greetingService.search(EMenuCore.user.getId(),
							addfoodView.getSearchBox().getText(),
							new AsyncCallback<List<FoodInfo>>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(List<FoodInfo> result) {
									if (result == null) {
										Loading.getInstance().hide();
										Window.alert("Không có dữ liệu!");
										return;
									}
									addfoodView.getProductHome().create(result);
									handleItemProductHome();
								}
							});
				}
			}
		});

		handleAddProductListener();
		handleAddCategory();
	}

	void handleItemProductHome() {
		for (int i = 1; i < addfoodView.getProductHome().scrollContent
				.getWidgetCount(); i++) {
			final ItemProduct item = (ItemProduct) addfoodView.getProductHome().scrollContent
					.getWidget(i);
			item.btnEdit.handleListener(new ClickListener() {

				@Override
				public void onClick() {
					// TODO Auto-generated method stub
					addfoodView.getProductHome().addProductBinder
							.createListBoxParent(addfoodView.getProductHome().listFoodCategory);
					addfoodView.getProductHome().addProductBinder.image
							.setUrl(Constants.ROOT
									+ "emenucore/download?filedown="
									+ item.foodInfo.getImage());
					addfoodView.getProductHome().addProductBinder.tbCode
							.setEnabled(false);
					addfoodView.getProductHome().addProductBinder.tbCode
							.setText(item.lbCode.getText());
					addfoodView.getProductHome().addProductBinder.tbName
							.setText(item.lbName.getText());
					addfoodView.getProductHome().addProductBinder.tbPrice
							.setText(item.lbPrice.getText());
					addfoodView.getProductHome().addProductBinder.tbUnit
							.setText(item.foodInfo.getUnit());
					System.out.println("========================="
							+ addfoodView.getProductHome().getItemIndex(
									item.foodInfo.getCategory_id())
							+ "=============");
					addfoodView.getProductHome().addProductBinder.ltbCategory
							.setSelectedIndex(addfoodView.getProductHome()
									.getItemIndex(
											item.foodInfo.getCategory_id()));
					addfoodView.getProductHome().addProductBinder.taDes
							.setText(item.foodInfo.getDescription());
					addfoodView.getProductHome().addProductBinder
							.setStateUpdate();
					addfoodView.getProductHome().showProduct();
				}
			});
			item.btnDelete.handleListener(new ClickListener() {

				@Override
				public void onClick() {
					EMenuCore.greetingService.deleteFood(
							EMenuCore.user.getId(), item.foodInfo.getCode(),
							new AsyncCallback<Integer>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(Integer result) {
									// TODO Auto-generated method stub
									if (result == 1) {
										Window.alert("Xóa thành công!!!");
										addfoodView
												.getProductHome()
												.deleteFood(
														item.foodInfo.getCode());
									} else if (result == 0) {
										Window.alert("Xóa không thành công!!!");
									} else if (result == -1) {
										Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
									}
								}
							});
				}
			});
		}
	}

	void handleAddProductListener() {
		addfoodView.getProductHome().addProductBinder
				.handleButtonClose(new ClickListener() {

					@Override
					public void onClick() {
						addfoodView.getProductHome().popup.hide();
					}
				});

		addfoodView.getProductHome().addProductBinder
				.handleButtonInfo(new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						addfoodView.getProductHome().addProductBinder
								.setSelected(AddProductBinder.TAB_INFO);
					}
				});

		addfoodView.getProductHome().addProductBinder
				.handleButtonConvert(new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						addfoodView.getProductHome().addProductBinder
								.setSelected(AddProductBinder.TAB_CONVERT);
					}
				});

		addfoodView.getProductHome().addProductBinder
				.handleImage(new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						addfoodView.getProductHome().addProductBinder.fileUpload.click();
					}
				});

		addfoodView.getProductHome().addProductBinder
				.handleButtonSave(new ClickListener() {

					@Override
					public void onClick() {
						if (addfoodView.getProductHome().addProductBinder
								.checkFull()) {
							if (addfoodView.getProductHome().addProductBinder
									.isStateNew()) {
								if (addfoodView.getProductHome().addProductBinder.fileUpload
										.getFilename().equals("")
										|| addfoodView.getProductHome().addProductBinder.lbImage
												.getText().equals("")) {
									Window.alert("Vui lòng chọn ảnh !!!");
									return;
								}
							}
							if (addfoodView.getProductHome().addProductBinder
									.isStateNew()) {
								EMenuCore.greetingService.saveNewFood(
										EMenuCore.user.getId(),
										addfoodView.getProductHome().addProductBinder.tbName
												.getText(),
										addfoodView.getProductHome().addProductBinder.tbCode
												.getText(),
										addfoodView.getProductHome().addProductBinder.taDes
												.getText(),
										Integer.valueOf(addfoodView
												.getProductHome().addProductBinder.tbPrice
												.getText()),
										addfoodView.getProductHome().addProductBinder.tbUnit
												.getText(),
										Integer.valueOf(addfoodView
												.getProductHome().addProductBinder.ltbCategory
												.getSelectedValue()),
										addfoodView.getProductHome().addProductBinder.ltbCategory
												.getSelectedItemText(), 1,
										DateTime.getCurrentDate("yyyy-MM-dd"),
										new AsyncCallback<FoodInfo>() {

											@Override
											public void onFailure(
													Throwable caught) {

											}

											@Override
											public void onSuccess(
													FoodInfo result) {
												if (result == null) {
													Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
													return;
												} else if (result.getId() == -1) {
													Window.alert(result
															.getTitle());
													return;
												} else {
													System.out
															.println("Upload submit with new image");
													addfoodView
															.getProductHome().addProductBinder.fileUpload.setName("C_"
															+ result.getCode()
															+ "");
													addfoodView
															.getProductHome().addProductBinder.form
															.submit();
													addfoodView
															.getProductHome()
															.addFood(result);
													handleItemProductHome();
												}
											}
										});
							} else {
								EMenuCore.greetingService.updateFood(
										EMenuCore.user.getId(),
										addfoodView.getProductHome().addProductBinder.tbCode
												.getText(),
										addfoodView.getProductHome().addProductBinder.tbName
												.getText(),
										addfoodView.getProductHome().addProductBinder.taDes
												.getText(),
										addfoodView.getProductHome().addProductBinder.tbPrice
												.getText(),
										addfoodView.getProductHome().addProductBinder.tbUnit
												.getText(),
										addfoodView.getProductHome().addProductBinder.ltbCategory
												.getSelectedValue(), DateTime
												.getCurrentDate("yyyy-MM-dd"),
										new AsyncCallback<FoodInfo>() {

											@Override
											public void onFailure(
													Throwable caught) {

											}

											@Override
											public void onSuccess(
													FoodInfo result) {
												if (result == null) {
													Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
													return;
												}
												if (!addfoodView
														.getProductHome().addProductBinder.lbImage
														.getText().equals("")) {
													System.out.println("Upload submit with old image : "
															+ addfoodView
																	.getProductHome().addProductBinder.form
																	.getAction());
													addfoodView
															.getProductHome().addProductBinder.fileUpload.setName("C_"
															+ result.getCode()
															+ "");
													addfoodView
															.getProductHome().addProductBinder.form
															.submit();
												} else {
													addfoodView
															.getProductHome().addProductBinder
															.reset();
													addfoodView
															.getProductHome().popup
															.hide();
												}
												// update to UI
												addfoodView
														.getProductHome()
														.updateFood(
																addfoodView
																		.getProductHome().addProductBinder.tbCode
																		.getText(),
																result);
											}
										});
							}

						} else {
							Window.alert("Vui lòng nhập đầy đủ thông tin!!!");
						}
					}
				});

		addfoodView.getProductHome().addProductBinder
				.handleButtonSaveAndNew(new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						if (addfoodView.getProductHome().addProductBinder
								.checkFull()) {
							if ((addfoodView.getProductHome().addProductBinder.fileUpload
									.getFilename().equals("") || addfoodView
									.getProductHome().addProductBinder.lbImage
									.getText().equals(""))
									&& addfoodView.getProductHome().addProductBinder
											.isStateNew()) {
								Window.alert("Vui lòng chọn ảnh !!!");
								return;
							}
							EMenuCore.greetingService.saveNewFood(
									EMenuCore.user.getId(),
									addfoodView.getProductHome().addProductBinder.tbName
											.getText(),
									addfoodView.getProductHome().addProductBinder.tbCode
											.getText(),
									addfoodView.getProductHome().addProductBinder.taDes
											.getText(),
									Integer.valueOf(addfoodView
											.getProductHome().addProductBinder.tbPrice
											.getText()),
									addfoodView.getProductHome().addProductBinder.tbUnit
											.getText(),
									Integer.valueOf(addfoodView
											.getProductHome().addProductBinder.ltbCategory
											.getSelectedValue()),
									addfoodView.getProductHome().addProductBinder.ltbCategory
											.getSelectedItemText(), 1, DateTime
											.getCurrentDate("yyyy-MM-dd"),
									new AsyncCallback<FoodInfo>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(FoodInfo result) {
											// TODO Auto-generated method stub
											if (result == null) {
												Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
												return;
											} else if (result.getId() == -1) {
												Window.alert(result.getTitle());
												return;
											} else {
												addfoodView.getProductHome().addProductBinder.fileUpload.setName("C_"
														+ result.getCode() + "");
												addfoodView.getProductHome().addProductBinder.form
														.submit();
												addfoodView.getProductHome().addProductBinder
														.reset();
												addfoodView.getProductHome()
														.addFood(result);
												handleItemProductHome();
											}
										}
									});
						} else {
							Window.alert("Vui lòng nhập đầy đủ thông tin!!!");
						}
					}
				});

		addfoodView.getProductHome().addProductBinder
				.handleFileUpload(new ClickListener() {

					@Override
					public void onClick() {
						addfoodView.getProductHome().addProductBinder.lbImage
								.setText(addfoodView.getProductHome().addProductBinder.fileUpload
										.getFilename());
						addfoodView.getProductHome().addProductBinder.image
								.setResource(ImageHolder.get().picture());
						addfoodView.getProductHome().addProductBinder.lbImage
								.setVisible(true);
					}
				});

		addfoodView.getProductHome().addProductBinder
				.handleForm(new ClickListener() {

					@Override
					public void onClick() {
						Window.alert("Thành công");
						ClientManager.getInstance().getListFood();
						if (!addfoodView.getProductHome().addProductBinder.saveandnew) {
							addfoodView.getProductHome().addProductBinder
									.reset();
							addfoodView.getProductHome().popup.hide();
						}
					}
				});
	}

	void handleItemCategoryListener() {
		for (int i = 1; i < addfoodView.getProductHome().addCategoryBinder.scrollContent
				.getWidgetCount(); i++) {
			final ItemCategory item = (ItemCategory) addfoodView
					.getProductHome().addCategoryBinder.scrollContent
					.getWidget(i);
			item.btnEdit.handleListener(new ClickListener() {

				@Override
				public void onClick() {
					addfoodView.getProductHome().addCategoryBinder.tbName
							.setText(item.foodCategory.getTitle());
					int index = -1;
					List<FoodCategory> listParent = addfoodView
							.getProductHome().removeFoodCategory(
									item.foodCategory);
					for (int i = 0; i < listParent.size(); i++) {
						if (listParent.get(i).getId() == item.foodCategory
								.getParent_id()) {
							index = i;
						}
					}
					addfoodView.getProductHome().addCategoryBinder.current_id_selected = item.foodCategory
							.getId();
					System.out.println("FoodCategoryID : "
							+ item.foodCategory.getId());
					addfoodView.getProductHome().addCategoryBinder
							.createListBoxParent(listParent);
					addfoodView.getProductHome().addCategoryBinder.ltbParent
							.setSelectedIndex(index + 1);
					addfoodView.getProductHome().addCategoryBinder.taDes
							.setText(item.foodCategory.getDescription());
					addfoodView.getProductHome().addCategoryBinder.image
							.setUrl(Constants.ROOT
									+ "emenucore/download?filedown="
									+ item.foodCategory.getUrl());
					addfoodView.getProductHome().addCategoryBinder
							.setStateUpdate();
				}
			});

			item.btnDelete.handleListener(new ClickListener() {

				@Override
				public void onClick() {
					// TODO Auto-generated method stub
					EMenuCore.greetingService.deleteFoodCategory(
							EMenuCore.user.getId(), item.foodCategory.getId(),
							new AsyncCallback<Integer>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(Integer result) {
									if (result == 0) {
										Window.alert("Bạn phải đăng nhập trước khi thực hiện chức năng này!");
										return;
									} else {
										addfoodView.getProductHome().addCategoryBinder
												.removeItemCategory(item.foodCategory
														.getId());
										Window.alert("Xóa thành công!");
									}
								}
							});
				}
			});
		}
	}

	void handleAddCategory() {

		addfoodView.getProductHome().addCategoryBinder
				.handleButtonClose(new ClickListener() {

					@Override
					public void onClick() {
						addfoodView.getProductHome().popup.hide();
					}
				});

		addfoodView.getProductHome().addCategoryBinder
				.handleFileUpload(new ClickListener() {

					@Override
					public void onClick() {
						addfoodView.getProductHome().addCategoryBinder.imageUrl
								.setText(addfoodView.getProductHome().addCategoryBinder.fileUpload
										.getFilename());
						addfoodView.getProductHome().addCategoryBinder.image
								.setResource(ImageHolder.get().picture());
					}
				});

		addfoodView.getProductHome().addCategoryBinder
				.handleForm(new ClickListener() {

					@Override
					public void onClick() {
						if (addfoodView.getProductHome().addCategoryBinder
								.isStateUpdate()) {
							EMenuCore.greetingService.getFoodCategoryById(
									addfoodView.getProductHome().addCategoryBinder.current_id_selected,
									new AsyncCallback<FoodCategory>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(
												FoodCategory result) {
											// TODO Auto-generated method stub
											addfoodView
													.getProductHome()
													.replaceFoodCategory(
															addfoodView
																	.getProductHome().addCategoryBinder.current_id_selected,
															result);
											addfoodView.getProductHome().addCategoryBinder
													.updateListFoodCategory(result);
										}
									});
						} else {
							ItemCategory item = (ItemCategory) addfoodView
									.getProductHome().addCategoryBinder.scrollContent
									.getWidget(addfoodView.getProductHome().addCategoryBinder.scrollContent
											.getWidgetCount() - 1);
							EMenuCore.greetingService.getFoodCategoryById(
									item.foodCategory.getId(),
									new AsyncCallback<FoodCategory>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(
												FoodCategory result) {
											addfoodView.getProductHome().listFoodCategory
													.add(result);
											addfoodView.getProductHome().addCategoryBinder
													.updateListFoodCategory(result);
										}
									});
						}
						Window.alert("Thành công!");
					}
				});

		addfoodView.getProductHome().addCategoryBinder
				.handleImage(new ClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						addfoodView.getProductHome().addCategoryBinder.fileUpload
								.click();
					}
				});
		addfoodView.getProductHome().addCategoryBinder
				.handleButtonSave(new ClickListener() {

					@Override
					public void onClick() {
						if (!addfoodView.getProductHome().addCategoryBinder
								.checkFull()) {
							Window.alert("Vui lòng nhập tên danh mục sản phẩm");
						} else {
							if (addfoodView.getProductHome().addCategoryBinder.fileUpload
									.getFilename().equals("")
									&& addfoodView.getProductHome().addCategoryBinder
											.isStateNew()) {
								Window.alert("Vui lòng chọn ảnh!!!");
							} else {
								final String gendate = DateTime
										.getCurrentDate("yyyy-MM-dd hh:mm:ss");
								if (addfoodView.getProductHome().addCategoryBinder
										.isStateNew()) {
									EMenuCore.greetingService.saveNewFoodCategory(
											EMenuCore.user.getId(),
											addfoodView.getProductHome().addCategoryBinder.tbName
													.getText(),
											addfoodView.getProductHome().addCategoryBinder.taDes
													.getText(),
											"null",
											Integer.valueOf(addfoodView
													.getProductHome().addCategoryBinder.ltbParent
													.getSelectedValue()),
											gendate,
											new AsyncCallback<FoodCategory>() {

												@Override
												public void onFailure(
														Throwable caught) {

												}

												@Override
												public void onSuccess(
														FoodCategory result) {
													if (result == null) {
														Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
														return;
													} else {
														addfoodView
																.getProductHome().addCategoryBinder.fileUpload
																.setName("G_"
																		+ gendate
																		+ "");
														addfoodView
																.getProductHome().addCategoryBinder
																.addFoodCategory(result);
														addfoodView
																.getProductHome().addCategoryBinder.form
																.submit();
														addfoodView
																.getProductHome().addCategoryBinder.tbName
																.setText("");
														addfoodView
																.getProductHome().addCategoryBinder.taDes
																.setText("");
													}
												}
											});
								} else {
									EMenuCore.greetingService.updateFoodCategory(
											EMenuCore.user.getId(),
											addfoodView.getProductHome().addCategoryBinder.current_id_selected,
											addfoodView.getProductHome().addCategoryBinder.tbName
													.getText(),
											addfoodView.getProductHome().addCategoryBinder.taDes
													.getText(),
											Integer.valueOf(addfoodView
													.getProductHome().addCategoryBinder.ltbParent
													.getSelectedValue()),
											gendate,
											new AsyncCallback<FoodCategory>() {

												@Override
												public void onFailure(
														Throwable caught) {
												}

												@Override
												public void onSuccess(
														FoodCategory result) {
													if (result == null) {
														Window.alert("Bạn phải đăng nhập trước khi thực hiện thao tác này!");
														return;
													} else {
														if (!addfoodView
																.getProductHome().addCategoryBinder.fileUpload
																.getFilename()
																.equals("")) {
															addfoodView
																	.getProductHome().addCategoryBinder.fileUpload
																	.setName("G_"
																			+ gendate
																			+ "");
															addfoodView
																	.getProductHome().addCategoryBinder.form
																	.submit();
															addfoodView
																	.getProductHome().addCategoryBinder.tbName
																	.setText("");
															addfoodView
																	.getProductHome().addCategoryBinder.taDes
																	.setText("");
														} else {
															addfoodView
																	.getProductHome()
																	.replaceFoodCategory(
																			addfoodView
																					.getProductHome().addCategoryBinder.current_id_selected,
																			result);
															addfoodView
																	.getProductHome().addCategoryBinder
																	.updateListFoodCategory(result);
														}
													}
												}
											});
								}
							}
						}
					}
				});

	}

}
