/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.OrderDetail;
import com.nnm.emenu.shared.UserInfo;

/**
 * @author bizluvsakura
 *
 */
public class OrderChefBinder extends Composite implements HasText {

	private static OrderChefBinderUiBinder uiBinder = GWT
			.create(OrderChefBinderUiBinder.class);

	interface OrderChefBinderUiBinder extends UiBinder<Widget, OrderChefBinder> {
	}

	public long id;

	public int amount;

	@UiField
	protected FlexPanel orderItem;
	@UiField
	protected Label lbTitle;
	@UiField
	protected Label lbAmount;
	@UiField
	protected Button btnCancel;
	@UiField
	protected FlexPanel containerButton;

	public int state;

	Button btnConfirm;
	Button btnCancelState;
	Button btnComplete;

	Label lbState;

	boolean isHandlerButtonConfirm;
	boolean isHandlerButtonCancelState;
	boolean isHandlerButtonComplete;
	boolean isHandleButtonCancel;

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public OrderChefBinder() {
		initWidget(uiBinder.createAndBindUi(this));
		orderItem.setOrientation(Orientation.HORIZONTAL);
		lbTitle.setText("Tên món ăn");
		lbAmount.setText("Số lượng");
		containerButton.setOrientation(Orientation.HORIZONTAL);
		btnConfirm = new Button("Xác nhận");
		btnCancelState = new Button("Từ chối");
		btnComplete = new Button("Hoàn thành");
		btnCancel.setText("");
		lbState = new Label("Trạng thái");
		lbState.setStyleName("lbState");
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_SERVER) {
			containerButton.add(lbState);
		}
		configFontSize();
	}

	public OrderChefBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		configFontSize();
	}

	public OrderChefBinder(long id, String title, int amount) {
		initWidget(uiBinder.createAndBindUi(this));
		this.id = id;
		this.amount = amount;
		orderItem.setOrientation(Orientation.HORIZONTAL);
		lbTitle.setText(title);
		lbAmount.setText(amount + "");
		containerButton.setOrientation(Orientation.HORIZONTAL);
		btnConfirm = new Button("Xác nhận");
		btnCancelState = new Button("Từ chối");
		btnComplete = new Button("Hoàn thành");
		lbState = new Label("");
		lbState.setStyleName("lbState");
		btnCancel.setText("X");
		lbTitle.getElement().getStyle().setTextAlign(TextAlign.LEFT);
		setStateWaitConfirm();
		configFontSize();
	}

	void configFontSize() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			btnConfirm.setStyleName("btnConfirmOrderItem");
			btnCancelState.setStyleName("btnCancelOrderItem");
			btnComplete.setStyleName("btnCompleteOrderItem");
			lbTitle.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbAmount.getElement().getStyle().setFontSize(80, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(90, Unit.PCT);
			lbState.getElement().getStyle().setFontSize(80, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			btnConfirm.setStyleName("btnConfirmOrderItem");
			btnCancelState.setStyleName("btnCancelOrderItem");
			btnComplete.setStyleName("btnCompleteOrderItemLager");
			lbTitle.getElement().getStyle().setFontSize(105, Unit.PCT);
			lbAmount.getElement().getStyle().setFontSize(105, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(105, Unit.PCT);
			lbState.getElement().getStyle().setFontSize(100, Unit.PCT);
		} else {
			btnConfirm.setStyleName("btnConfirmOrderItemSmall");
			btnCancelState.setStyleName("btnCancelOrderItemSmall");
			btnComplete.setStyleName("btnCompleteOrderItemSmall");
			lbTitle.getElement().getStyle().setFontSize(50, Unit.PCT);
			lbAmount.getElement().getStyle().setFontSize(50, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(60, Unit.PCT);
			lbState.getElement().getStyle().setFontSize(55, Unit.PCT);
		}
	}

	public void setAmount(int amount) {
		this.amount = amount;
		this.lbAmount.setText(amount + "");
	}

	public void setStateWaitConfirm() {
		this.state = OrderDetail.STATE_WAIT_CONFIRM;
		containerButton.clear();
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
			containerButton.add(btnConfirm);
			containerButton.add(btnCancelState);
		} else {
			lbState.setText("Đang chờ xác nhận");
			containerButton.add(lbState);
		}
	}

	public void setStateWaitComplete() {
		this.state = OrderDetail.STATE_WAIT_COMPLETE;
		containerButton.clear();
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
			btnComplete.getElement().getStyle().setBackgroundColor("gray");
			containerButton.add(btnComplete);
		} else {
			lbState.setText("Đang chờ hoàn thành");
			containerButton.add(lbState);
		}
	}

	public void setStateComplete() {
		this.state = OrderDetail.STATE_COMPLETE;
		containerButton.clear();
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
			containerButton.add(btnComplete);
			btnComplete.getElement().getStyle().setBackgroundColor("#2adfe6");
		} else {
			lbState.setText("Hoàn thành");
			containerButton.add(lbState);
		}
	}

	public void setStateReject() {
		this.state = OrderDetail.STATE_REJECT;
		containerButton.clear();
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
			containerButton.add(btnComplete);
			btnComplete.getElement().getStyle().setBackgroundColor("gray");
			btnComplete.setText("Từ chối");
		} else {
			lbState.setText("Từ chối");
			containerButton.add(lbState);
			btnCancel.setDisabled(true);
		}
	}

	public void setState(int state) {
		switch (state) {
		case OrderDetail.STATE_WAIT_CONFIRM:
			setStateWaitConfirm();
			break;
		case OrderDetail.STATE_WAIT_COMPLETE:
			setStateWaitComplete();
			break;
		case OrderDetail.STATE_COMPLETE:
			setStateComplete();
			break;
		case OrderDetail.STATE_REJECT:
			setStateReject();
			break;
		case OrderDetail.STATE_CANCEL:
			this.state = OrderDetail.STATE_CANCEL;
			break;
		default:
			break;
		}
	}

	public void hasButtonConfirmListener(OrderChefBinder item,
			final ClickListener listener) {
		if (!isHandlerButtonConfirm) {
			isHandlerButtonConfirm = true;
			btnConfirm.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void hasButtonCancelStateListener(OrderChefBinder item,
			final ClickListener listener) {
		if (!isHandlerButtonCancelState) {
			isHandlerButtonCancelState = true;
			btnCancelState.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void hasButtonCompleteListener(OrderChefBinder item,
			final ClickListener listener) {
		if (!isHandlerButtonComplete) {
			isHandlerButtonComplete = true;
			btnComplete.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void hasButtonCancelListener(OrderChefBinder item,
			final ClickListener listener) {
		if (!isHandleButtonCancel) {
			isHandleButtonCancel = true;
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
