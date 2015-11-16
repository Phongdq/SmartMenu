/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.UserInfo;

/**
 * @author bizluvsakura
 *
 */
public class AddUserInfo extends Composite implements HasText {

	private static AddUserInfoUiBinder uiBinder = GWT
			.create(AddUserInfoUiBinder.class);

	interface AddUserInfoUiBinder extends UiBinder<Widget, AddUserInfo> {
	}

	@UiField
	FlexPanel parent;
	// header
	@UiField
	FlexPanel header;
	@UiField
	Label title;
	public CustomImageButton btnClose;
	// content
	@UiField
	FlexPanel content;
	@UiField
	FlexPanel panelName;
	@UiField
	Label lbName;
	@UiField
	public TextBox tbName;
	@UiField
	FlexPanel panelUserName;
	@UiField
	Label lbUserName;
	@UiField
	public TextBox tbUserName;
	@UiField
	FlexPanel panelPassWord;
	@UiField
	Label lbPassWord;
	@UiField
	public TextBox tbPassWord;
	@UiField
	FlexPanel panelRole;
	@UiField
	Label lbRole;
	@UiField
	public ListBox ltbRole;

	@UiField
	Button btnAdd;

	boolean isHandleButtonAddListener;

	public int state;
	public int STATE_NEW_USER = 1;
	public int STATE_UPDATE_USER = 2;

	public AddUserInfo() {
		initWidget(uiBinder.createAndBindUi(this));
		createElement();
	}

	public AddUserInfo(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		createElement();
	}

	void createElement() {
		parent.getElement().getStyle().setBorderColor("black");
		parent.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		parent.getElement().getStyle().setBorderWidth(1, Unit.PX);

		header.setOrientation(Orientation.HORIZONTAL);
		header.getElement().getStyle().setBorderColor("black");
		header.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		header.getElement().getStyle().setBorderWidth(1, Unit.PX);
		title.setText("Thêm nhân viên");

		float btnClose_height = Constants.HEIGHT * 0.8f * 0.1f * 0.9f;
		btnClose = new CustomImageButton("image/iconClose.png", btnClose_height
				+ "px", btnClose_height + "px");
		header.add(btnClose);

		panelName.setOrientation(Orientation.HORIZONTAL);
		panelUserName.setOrientation(Orientation.HORIZONTAL);
		panelPassWord.setOrientation(Orientation.HORIZONTAL);
		panelRole.setOrientation(Orientation.HORIZONTAL);

		lbName.setText("Họ Tên");
		lbUserName.setText("Tên tài khoản");
		lbPassWord.setText("Mật khẩu");
		lbRole.setText("Chức vụ");
		ltbRole.addItem("Phục vụ", UserInfo.ROLE_SERVER + "");
		ltbRole.addItem("Đầu bếp", UserInfo.ROLE_CHEF + "");
		ltbRole.addItem("Quản lý", UserInfo.ROLE_ADMIN + "");

		btnAdd.setText("Thêm nhân viên");
	}

	public void handleButtonAddListener(final ClickListener listener) {
		if (!isHandleButtonAddListener) {
			isHandleButtonAddListener = true;
			btnAdd.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					if (checkFull()) {
						listener.onClick();
					} else {
						Window.alert("Vui lòng nhập đầy đủ thông tin!!!");
					}
				}
			});
		}
	}

	public void reset() {
		tbName.setText("");
		tbUserName.setText("");
		tbPassWord.setText("");
		ltbRole.setSelectedIndex(0);
	}

	public void setStateNewUser() {
		this.state = STATE_NEW_USER;
		btnAdd.setText("Thêm thành viên");
	}

	public void setStateUpdateUser() {
		this.state = STATE_UPDATE_USER;
		btnAdd.setText("Cập nhật");
	}

	boolean checkFull() {
		if (tbName.getText().equals("") || tbUserName.getText().equals("")
				|| tbPassWord.getText().equals("")) {
			return false;
		}
		return true;
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
