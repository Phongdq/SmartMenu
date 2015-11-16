package com.nnm.emenu.client.activities.manageruser;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.client.utils.ui.AddUserInfo;
import com.nnm.emenu.client.utils.ui.CustomImageButton;
import com.nnm.emenu.client.utils.ui.ItemUserRow;
import com.nnm.emenu.shared.UserInfo;

/**
 * @author bizluvsakura
 *
 */
public class ManagerUserViewImpl extends Composite implements HasText,
		ManagerUserVew {

	private static ManagerUserViewImplUiBinder uiBinder = GWT
			.create(ManagerUserViewImplUiBinder.class);

	interface ManagerUserViewImplUiBinder extends
			UiBinder<Widget, ManagerUserViewImpl> {
	}

	@UiField
	FlexPanel parent;

	@UiField
	FlexPanel headerContentLeft;
	public CustomImageButton btnHome;
	Label title;

	@UiField
	FlexPanel content;
	@UiField
	FlexPanel panelScroll;
	@UiField
	ScrollPanel scroll;
	@UiField
	FlexPanel scrollContent;

	@UiField
	Button btnAdd;

	PopupPanel popup;
	boolean isHandleButtonAddListener;
	AddUserInfo addUserInfo;

	public ManagerUserViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		createElement();
	}

	public ManagerUserViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		createElement();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			title.getElement().getStyle().setFontSize(170, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			title.getElement().getStyle().setFontSize(190, Unit.PCT);
		} else {
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
		}
	}

	@Override
	public void createListUser(List<UserInfo> result) {
		scroll.clear();
		scrollContent.clear();
		scrollContent.add(new ItemUserRow());
		for (int i = 0; i < result.size(); i++) {
			scrollContent.add(new ItemUserRow(i + 1, result.get(i)));
		}
		scroll.add(scrollContent);
		System.out.println("create Success");
	}

	void createElement() {
		headerContentLeft.setOrientation(Orientation.HORIZONTAL);
		headerContentLeft.getElement().getStyle().setBackgroundColor("#116388");
		int width = (int) (Constants.HEIGHT * 0.09f);
		btnHome = new CustomImageButton("image/iconHome.png", width + "px",
				"100%");
		title = new Label("QUẢN LÝ NHÂN VIÊN");
		title.setStyleName("managerview-titleheader");
		headerContentLeft.add(btnHome);
		headerContentLeft.add(title);

		btnAdd.setText("Thêm nhân viên mới");

		popup = new PopupPanel(true);
		popup.setAutoHideEnabled(false);
		popup.setPixelSize((int) (0.7f * Constants.WIDTH),
				(int) (0.7f * Constants.HEIGHT));
		popup.setPopupPosition((int) (0.15f * Constants.WIDTH),
				(int) (0.15f * Constants.HEIGHT));
		addUserInfo = new AddUserInfo();
		popup.add(addUserInfo);
		
		config();
	}

	@Override
	public void handleButtonAddListener(final ClickListener listener) {
		if (!isHandleButtonAddListener) {
			isHandleButtonAddListener = true;
			btnAdd.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonHomeListener(ClickListener listener) {
		btnHome.handleListener(listener);
	}

	@Override
	public PopupPanel getPopUp() {
		return popup;
	}

	@Override
	public AddUserInfo getAddUserInfoBinder() {
		return addUserInfo;
	}

	@Override
	public FlexPanel getScrollContent() {
		return scrollContent;
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
