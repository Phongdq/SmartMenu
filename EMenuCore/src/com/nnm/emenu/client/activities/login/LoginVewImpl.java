/**
 * 
 */
package com.nnm.emenu.client.activities.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;

/**
 * @author bizluvsakura
 *
 */
public class LoginVewImpl extends Composite implements HasText, LoginView {

	private static LoginVewImplUiBinder uiBinder = GWT
			.create(LoginVewImplUiBinder.class);

	interface LoginVewImplUiBinder extends UiBinder<Widget, LoginVewImpl> {
	}

	@UiField
	protected FlexPanel parent;
	@UiField
	protected Label title;
	@UiField
	protected TextBox tbUserName;
	@UiField
	protected PasswordTextBox tbPassWord;
	@UiField
	protected Button btnLogin;

	boolean isHandleListener;

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public LoginVewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		title.setText("EMenu");
		tbUserName.getElement().setAttribute("placeholder", "Tên đăng nhập");
		tbPassWord.getElement().setAttribute("placeholder", "Mật khẩu");

		btnLogin.setText("Đăng nhập");

		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			btnLogin.getElement().getStyle().setFontSize(220, Unit.PCT);
			tbUserName.getElement().getStyle().setFontSize(150, Unit.PCT);
			tbPassWord.getElement().getStyle().setFontSize(150, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			btnLogin.getElement().getStyle().setFontSize(260, Unit.PCT);
			tbUserName.getElement().getStyle().setFontSize(180, Unit.PCT);
			tbPassWord.getElement().getStyle().setFontSize(180, Unit.PCT);
		} else {
			btnLogin.getElement().getStyle().setFontSize(150, Unit.PCT);
			tbUserName.getElement().getStyle().setFontSize(135, Unit.PCT);
			tbPassWord.getElement().getStyle().setFontSize(135, Unit.PCT);
		}
	}

	public LoginVewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi
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

	@Override
	public void hasLoginListener(final ClickListener listener) {
		// TODO Auto-generated method stub
		if (!isHandleListener) {
			isHandleListener = true;
			btnLogin.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return tbUserName.getText();
	}

	@Override
	public String getPassWord() {
		// TODO Auto-generated method stub
		return tbPassWord.getText();
	}

}
