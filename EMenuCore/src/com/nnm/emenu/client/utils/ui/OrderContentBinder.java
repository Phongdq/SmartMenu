/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.nnm.emenu.client.ClientManager;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.OrderManagerClient;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.FoodInfo;
import com.nnm.emenu.shared.OrderData;
import com.nnm.emenu.shared.OrderDetail;
import com.nnm.emenu.shared.UserInfo;

/**
 * @author bizluvsakura
 *
 */
public class OrderContentBinder extends Composite implements HasText {

	private static OrderContentBinderUiBinder uiBinder = GWT
			.create(OrderContentBinderUiBinder.class);

	interface OrderContentBinderUiBinder extends
			UiBinder<Widget, OrderContentBinder> {
	}

	@UiField
	protected FlexPanel header;
	@UiField
	protected Image imgOrder;
	@UiField
	protected Label titleOrder;
	@UiField
	FlexPanel panelButtonChange;
	public CustomImageButton btnChangeTable;

	@UiField
	protected FlexPanel bottom;
	@UiField
	public Button btnPayment;
	@UiField
	public Button btnCancel;

	@UiField
	ScrollPanel scrollContent;
	@UiField
	public FlexPanel orderContent;
	@UiField
	protected FlexPanel orderContainer;
	@UiField
	protected FlexPanel totalContent;
	@UiField
	protected Label lbTotal;
	@UiField
	protected Label totalMoney;

	boolean isHandleButtonPaymentListener;
	boolean isHandleButtonCancelListener;

	int current_order_table = -1;

	// doi voi user phuc vu
	public static final int STATE_HOME = 0;
	public static final int STATE_ORDER_MANAGER = 1;
	public int state = STATE_HOME;

	public long total_money;

	public OrderContentBinder() {
		initWidget(uiBinder.createAndBindUi(this));
		creatElement();
	}

	void creatElement() {
		header.setOrientation(Orientation.HORIZONTAL);
		header.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		header.getElement().getStyle().setBackgroundColor("#116388");

		float height_button = 0.13f * Constants.HEIGHT * 0.91f;
		btnChangeTable = new CustomImageButton("image/iconChange.png",
				height_button + "px", height_button + "px");
		panelButtonChange.add(btnChangeTable);
		btnChangeTable.setVisible(false);
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
			btnChangeTable.setVisible(true);
		}

		bottom.setOrientation(Orientation.HORIZONTAL);
		btnPayment.getElement().getStyle()
				.setVerticalAlign(VerticalAlign.MIDDLE);
		btnCancel.getElement().getStyle()
				.setVerticalAlign(VerticalAlign.MIDDLE);

		orderContainer.getElement().getStyle().setBackgroundColor("white");
		// scrollContent.getElement().getStyle().setMarginLeft(2, Unit.PCT);
		// scrollContent.getElement().getStyle().setMarginRight(2, Unit.PCT);
		totalContent.setOrientation(Orientation.HORIZONTAL);

		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			btnPayment.getElement().getStyle().setFontSize(140, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(140, Unit.PCT);
			lbTotal.getElement().getStyle().setFontSize(110, Unit.PCT);
			totalMoney.getElement().getStyle().setFontSize(110, Unit.PCT);
			titleOrder.getElement().getStyle().setFontSize(130, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			btnPayment.getElement().getStyle().setFontSize(170, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(170, Unit.PCT);
			lbTotal.getElement().getStyle().setFontSize(130, Unit.PCT);
			totalMoney.getElement().getStyle().setFontSize(130, Unit.PCT);
			titleOrder.getElement().getStyle().setFontSize(140, Unit.PCT);
		} else {
			btnPayment.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbTotal.getElement().getStyle().setFontSize(90, Unit.PCT);
			totalMoney.getElement().getStyle().setFontSize(90, Unit.PCT);
			titleOrder.getElement().getStyle().setFontSize(110, Unit.PCT);
		}
	}

	public OrderContentBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setOrderTable(OrderData order) {
		total_money = 0;
		titleOrder.setText("Bàn " + order.getOrder().getTable());
		scrollContent.clear();
		orderContent.clear();
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER
				|| EMenuCore.user.getRoleId() == UserInfo.ROLE_ADMIN) {
			if (state == STATE_HOME) {
				OrderItem titleOrder = new OrderItem("Tên món ăn", "Số lượng",
						"Đơn giá", "Thành tiền");
				titleOrder.getElement().getStyle().setMarginBottom(10, Unit.PX);
				orderContent.add(titleOrder);
			} else if (state == STATE_ORDER_MANAGER) {
				OrderChefBinder titleOrder = new OrderChefBinder();
				titleOrder.getElement().getStyle().setMarginBottom(10, Unit.PX);
				orderContent.add(titleOrder);
			}
		} else if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
			OrderChefBinder titleOrder = new OrderChefBinder();
			titleOrder.getElement().getStyle().setMarginBottom(10, Unit.PX);
			orderContent.add(titleOrder);
		}
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER
				|| EMenuCore.user.getRoleId() == UserInfo.ROLE_ADMIN) {
			if (state == STATE_HOME) {
				lbTotal.setText("Tổng tiền");
				totalMoney.setText("0 VNĐ");
			} else if (state == STATE_ORDER_MANAGER) {
				lbTotal.setText("");
				totalMoney.setText("");
			}
		} else {
			lbTotal.setText("");
			totalMoney.setText("");
		}
		// ArrayList<OrderDetail> listOrderDetail = new ArrayList<OrderDetail>(
		// getOrderV2(order).values());
		for (int i = 0; i < order.listOrderDetail.size(); i++) {
			if (order.listOrderDetail.get(i).getAmount() == 0) {
				continue;
			}
			final FoodInfo food = ClientManager.getInstance().getFood(
					order.listOrderDetail.get(i).getFood_id());
			if (food == null) {
				System.out.println("can not get food");
				continue;
			}

			if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER
					|| EMenuCore.user.getRoleId() == UserInfo.ROLE_ADMIN) {
				if (state == STATE_HOME) {
					System.out.println("id : "
							+ order.listOrderDetail.get(i).getId());
					if (order.listOrderDetail.get(i).getState() != OrderDetail.STATE_REJECT) {
						OrderItem orderItem = new OrderItem(food.getTitle(),
								order.listOrderDetail.get(i).getId(),
								order.listOrderDetail.get(i).getAmount(),
								food.getPrice(), food.getPrice()
										* order.listOrderDetail.get(i)
												.getAmount(), i);
						total_money += food.getPrice()
								* order.listOrderDetail.get(i).getAmount();
						orderContent.add(orderItem);
					}
				} else if (state == STATE_ORDER_MANAGER) {
					OrderChefBinder orderItem = new OrderChefBinder(
							order.listOrderDetail.get(i).getId(),
							food.getTitle(), order.listOrderDetail.get(i)
									.getAmount());
					total_money += food.getPrice()
							* order.listOrderDetail.get(i).getAmount();
					orderContent.add(orderItem);
					orderItem.setState(order.listOrderDetail.get(i).getState());
				}
			} else if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
				OrderChefBinder orderItem = new OrderChefBinder(
						order.listOrderDetail.get(i).getId(), food.getTitle(),
						order.listOrderDetail.get(i).getAmount());
				total_money += food.getPrice()
						* order.listOrderDetail.get(i).getAmount();
				orderContent.add(orderItem);
				orderItem.setState(order.listOrderDetail.get(i).getState());
			}
		}

		if (order.listOrderDetail.size() != 0) {
			if (EMenuCore.user.getRoleId() != UserInfo.ROLE_CHEF) {
				if (state == STATE_HOME) {
					totalMoney.setText(total_money + " VNĐ");
				}
			}
		}
		scrollContent.add(orderContent);
	}

	Map<Integer, OrderDetail> getOrderV2(OrderData order) {
		Map<Integer, OrderDetail> result = new HashMap<Integer, OrderDetail>();
		for (int i = 0; i < order.listOrderDetail.size(); i++) {
			if (!result.containsKey(order.listOrderDetail.get(i).getFood_id())) {
				OrderDetail orderDetai = order.listOrderDetail.get(i);
				orderDetai.setAmount(0);
				result.put(orderDetai.getFood_id(), orderDetai);
			}
			result.get(order.listOrderDetail.get(i).getFood_id()).setAmount(
					result.get(order.listOrderDetail.get(i).getFood_id())
							.getAmount() + 1);
		}
		return result;
	}

	public void setOrderTable(int table) {
		if (table > 0) {
			titleOrder.setText("Bàn " + table);
		} else {
			titleOrder.setText("Chọn bàn");
		}
		orderContent.clear();
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER
				|| EMenuCore.user.getRoleId() == UserInfo.ROLE_ADMIN) {
			lbTotal.setText("Tổng tiền");
			totalMoney.setText("0 VNĐ");
		} else {
			lbTotal.setText("");
			totalMoney.setText("");
		}
	}

	// public void setOrderTable(OrderData order) {
	// System.out.println("current table : " + current_order_table
	// + "\btable : " + order.getTable());
	// int money = 0;
	// // if (order.getTable() == current_order_table) {
	// // System.out.println("current order");
	// // ArrayList<Integer> listFoodId = order.getListFoodId();
	// // ArrayList<Integer> listAmount = order.getListAmount();
	// // for (int i = 0; i < listFoodId.size(); i++) {
	// // money += ClientManager.getInstance().getFood(listFoodId.get(i))
	// // .getPrice()
	// // * listAmount.get(i);
	// // if (i == listFoodId.size() - 1) {
	// // final FoodInfo food = ClientManager.getInstance().getFood(
	// // listFoodId.get(i));
	// // if (food == null) {
	// // System.out.println("can not get food");
	// // return;
	// // }
	// // if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
	// // OrderItem orderItem = new OrderItem(food.getTitle(),
	// // food.getId(), listAmount.get(i),
	// // food.getPrice(), food.getPrice()
	// // * listAmount.get(i));
	// // orderContent.add(orderItem);
	// // } else if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
	// // OrderChefBinder orderItem = new OrderChefBinder(
	// // food.getTitle(), listAmount.get(i));
	// // orderContent.add(orderItem);
	// // }
	// // }
	// // }
	// // } else {
	// // System.out.println("new order");
	// current_order_table = order.getTable();
	// titleOrder.setText("Bàn " + order.getTable());
	// orderContent.clear();
	// if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
	// OrderItem titleOrder = new OrderItem("Tên món ăn", "Số lượng",
	// "Đơn giá", "Thành tiền");
	// titleOrder.getElement().getStyle().setMarginBottom(10, Unit.PX);
	// orderContent.add(titleOrder);
	// } else if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
	// OrderChefBinder titleOrder = new OrderChefBinder();
	// titleOrder.getElement().getStyle().setMarginBottom(10, Unit.PX);
	// orderContent.add(titleOrder);
	// }
	// if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
	// lbTotal.setText("Tổng tiền");
	// totalMoney.setText("0 VNĐ");
	// } else {
	// lbTotal.setText("");
	// totalMoney.setText("");
	// }
	// ArrayList<Integer> listFoodId = order.getListFoodId();
	// ArrayList<Integer> listAmount = order.getListAmount();
	// System.out.println("listFoodId Size : " + listFoodId.size());
	// for (int i = 0; i < listFoodId.size(); i++) {
	// final FoodInfo food = ClientManager.getInstance().getFood(
	// listFoodId.get(i));
	// if (food == null) {
	// System.out.println("can not get food");
	// continue;
	// }
	//
	// if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
	// OrderItem orderItem = new OrderItem(food.getTitle(),
	// food.getId(), listAmount.get(i), food.getPrice(),
	// food.getPrice() * listAmount.get(i), i);
	// money += food.getPrice() * listAmount.get(i);
	// orderContent.add(orderItem);
	// } else if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
	// OrderChefBinder orderItem = new OrderChefBinder(
	// food.getTitle(), listAmount.get(i));
	// money += food.getPrice() * listAmount.get(i);
	// orderContent.add(orderItem);
	// }
	// }
	// // }
	//
	// if (order.getListFoodId().size() != 0) {
	// totalMoney.setText(money + " VNĐ");
	// }
	// }

	public void refreshTotalMoney() {
		int total_money = 0;
		// OrderData order = ClientManager.getInstance().getOrder(
		// EMenuCore.currentTable);
		// System.out.println("current order");
		// ArrayList<Integer> listFoodId = order.getListFoodId();
		// ArrayList<Integer> listAmount = order.getListAmount();
		// for (int i = 0; i < listFoodId.size(); i++) {
		// money += ClientManager.getInstance().getFood(listFoodId.get(i))
		// .getPrice()
		// * listAmount.get(i);
		// }
		OrderData order = OrderManagerClient.getInstance().getOrder(
				EMenuCore.currentTable);
		for (int i = 0; i < order.listOrderDetail.size(); i++) {
			if (order.listOrderDetail.get(i).getState() != OrderDetail.STATE_CANCEL
					&& order.listOrderDetail.get(i).getState() != OrderDetail.STATE_REJECT) {
				total_money += order.listOrderDetail.get(i).getMoney();
			}
		}
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER
				&& state == STATE_HOME) {
			totalMoney.setText(total_money + " VNĐ");
		}
	}

	public void handleButtonPaymentListener(final ClickListener listener) {
		if (!isHandleButtonPaymentListener) {
			isHandleButtonPaymentListener = true;
			btnPayment.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	public void handleButtonCancelListener(final ClickListener listener) {
		if (!isHandleButtonCancelListener) {
			isHandleButtonCancelListener = true;
			btnCancel.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void setText(String text) {
	}

	/**
	 * Gets invoked when the default constructor is called and a string is
	 * provided in the ui.xml file.
	 */
	public String getText() {
		return "";
	}
}
