/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
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
import com.googlecode.mgwt.ui.client.widget.button.ImageButton;
import com.googlecode.mgwt.ui.client.widget.image.ImageHolder;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.UserInfo;

/**
 * @author bizluvsakura
 *
 */
public class ItemUserRow extends Composite implements HasText {

	private static ItemUserRowUiBinder uiBinder = GWT
			.create(ItemUserRowUiBinder.class);

	interface ItemUserRowUiBinder extends UiBinder<Widget, ItemUserRow> {
	}

	@UiField
	FlexPanel parent;
	@UiField
	FlexPanel panelStt;
	@UiField
	Label lbSTT;
	@UiField
	FlexPanel panelName;
	@UiField
	public Label lbName;
	@UiField
	FlexPanel panelUserName;
	@UiField
	public Label lbUserName;
	@UiField
	FlexPanel panelPassWord;
	@UiField
	public Label lbPassWord;
	@UiField
	FlexPanel panelRole;
	@UiField
	public Label lbRole;
	@UiField
	FlexPanel panelState;
	@UiField
	public Label lbState;
	@UiField
	FlexPanel panelNote;
	public CustomImageButton btnEdit;
	public CustomImageButton btnDelete;

	public UserInfo user;

	public ItemUserRow() {
		initWidget(uiBinder.createAndBindUi(this));

		parent.setOrientation(Orientation.HORIZONTAL);
		parent.setHeight("33px");
		parent.getElement().getStyle().setMarginLeft(2, Unit.PCT);

		lbSTT.setText("STT");
		lbSTT.getElement().getStyle().setColor("#116388");
		lbSTT.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lbSTT.getElement().getStyle().setFontSize(110, Unit.PCT);
		lbSTT.getElement().getStyle().setTextAlign(TextAlign.CENTER);

		lbName.setText("Họ Tên");
		lbName.getElement().getStyle().setColor("#116388");
		lbName.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lbName.getElement().getStyle().setFontSize(110, Unit.PCT);
		lbName.getElement().getStyle().setTextAlign(TextAlign.CENTER);

		lbUserName.setText("Tài khoản");
		lbUserName.getElement().getStyle().setColor("#116388");
		lbUserName.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lbUserName.getElement().getStyle().setFontSize(110, Unit.PCT);
		lbUserName.getElement().getStyle().setTextAlign(TextAlign.CENTER);

		lbPassWord.setText("Mật khẩu");
		lbPassWord.getElement().getStyle().setColor("#116388");
		lbPassWord.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lbPassWord.getElement().getStyle().setFontSize(110, Unit.PCT);
		lbPassWord.getElement().getStyle().setTextAlign(TextAlign.CENTER);

		lbRole.setText("Chức vụ");
		lbRole.getElement().getStyle().setColor("#116388");
		lbRole.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lbRole.getElement().getStyle().setFontSize(110, Unit.PCT);
		lbRole.getElement().getStyle().setTextAlign(TextAlign.CENTER);

		lbState.setText("Trạng thái");
		lbState.getElement().getStyle().setColor("#116388");
		lbState.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lbState.getElement().getStyle().setFontSize(110, Unit.PCT);
		lbState.getElement().getStyle().setTextAlign(TextAlign.CENTER);

		panelName.getElement().getStyle().setBorderColor("gray");
		panelName.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelName.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelPassWord.getElement().getStyle().setBorderColor("gray");
		panelPassWord.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelPassWord.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelRole.getElement().getStyle().setBorderColor("gray");
		panelRole.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelRole.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelState.getElement().getStyle().setBorderColor("gray");
		panelState.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelState.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelStt.getElement().getStyle().setBorderColor("gray");
		panelStt.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelStt.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelUserName.getElement().getStyle().setBorderColor("gray");
		panelUserName.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelUserName.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelNote.getElement().getStyle().setBorderColor("gray");
		panelNote.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelNote.getElement().getStyle().setBorderWidth(1, Unit.PX);

		config();
	}

	public ItemUserRow(int stt, UserInfo userInfo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.user = userInfo;

		parent.setOrientation(Orientation.HORIZONTAL);
		parent.getElement().getStyle().setMarginLeft(2, Unit.PCT);

		this.lbSTT.setText(stt + "");
		this.lbName.setText(userInfo.getName());
		this.lbUserName.setText(userInfo.getUsername());
		this.lbPassWord.setText(userInfo.getPassword());
		if (userInfo.getRoleId() == UserInfo.ROLE_ADMIN) {
			this.lbRole.setText("Quản lý");
		} else if (userInfo.getRoleId() == UserInfo.ROLE_CHEF) {
			this.lbRole.setText("Đầu bếp");
		} else if (userInfo.getRoleId() == UserInfo.ROLE_SERVER) {
			this.lbRole.setText("Phục vụ");
		}
		if (userInfo.getState() == 1) {
			this.lbState.setText("Online");
		} else if (userInfo.getState() == 0) {
			this.lbState.setText("Offline");
		}

		panelName.getElement().getStyle().setBorderColor("gray");
		panelName.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelName.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelPassWord.getElement().getStyle().setBorderColor("gray");
		panelPassWord.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelPassWord.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelRole.getElement().getStyle().setBorderColor("gray");
		panelRole.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelRole.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelState.getElement().getStyle().setBorderColor("gray");
		panelState.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelState.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelStt.getElement().getStyle().setBorderColor("gray");
		panelStt.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelStt.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelUserName.getElement().getStyle().setBorderColor("gray");
		panelUserName.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelUserName.getElement().getStyle().setBorderWidth(1, Unit.PX);

		panelNote.setOrientation(Orientation.HORIZONTAL);
		panelNote.getElement().getStyle().setBorderColor("gray");
		panelNote.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelNote.getElement().getStyle().setBorderWidth(1, Unit.PX);

		btnEdit = new CustomImageButton("image/iconEdit.png", 50 + "px",
				50 + "px");
		btnDelete = new CustomImageButton("image/iconClose.png", 50 + "px",
				50 + "px");
		btnEdit.getElement().getStyle().setMarginLeft(10, Unit.PCT);
		btnDelete.getElement().getStyle().setMarginLeft(10, Unit.PCT);

		panelNote.add(btnEdit);
		panelNote.add(btnDelete);

		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			lbName.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbPassWord.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbRole.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbState.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbSTT.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbUserName.getElement().getStyle().setFontSize(120, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			lbName.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbPassWord.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbRole.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbState.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbSTT.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbUserName.getElement().getStyle().setFontSize(130, Unit.PCT);
		} else {
			lbName.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbPassWord.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbRole.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbState.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbSTT.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbUserName.getElement().getStyle().setFontSize(100, Unit.PCT);
		}
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
