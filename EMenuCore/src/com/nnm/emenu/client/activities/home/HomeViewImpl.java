/**
 * 
 */
package com.nnm.emenu.client.activities.home;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.Panel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Alignment;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.nnm.emenu.client.ClientManager;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.OrderManagerClient;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.client.utils.ui.ChangeTableBinder;
import com.nnm.emenu.client.utils.ui.CustomImageButton;
import com.nnm.emenu.client.utils.ui.CustomSearchTextBox;
import com.nnm.emenu.client.utils.ui.ItemMenu;
import com.nnm.emenu.client.utils.ui.MenuContentBinder;
import com.nnm.emenu.client.utils.ui.OrderContentBinder;
import com.nnm.emenu.client.utils.ui.OrderDetailBinder;
import com.nnm.emenu.client.utils.ui.PaymentContentBinder;
import com.nnm.emenu.client.utils.ui.TableObject;
import com.nnm.emenu.shared.UserInfo;

/**
 * @author bizluvsakura
 *
 */
public class HomeViewImpl extends Composite implements HasText, HomeView {

	private static HomeViewBinderUiBinder uiBinder = GWT
			.create(HomeViewBinderUiBinder.class);

	interface HomeViewBinderUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}

	@UiField
	protected FlexPanel parent;

	@UiField
	protected FlexPanel headerContentLeft;

	CustomImageButton btnMenu;
	CustomImageButton btnOrder;
	CustomImageButton btnEMenu;
	CustomSearchTextBox custbSearch;

	// content
	@UiField
	protected ScrollPanel scrollContent;
	@UiField
	protected FlexPanel content;
	@UiField
	protected FlexPanel contentLeft;
	@UiField
	protected FlexPanel contentRight;
	// menuLeft
	@UiField
	protected FlexPanel menu;
	@UiField
	protected FlexPanel menuContent;

	ItemMenu btnUser;
	ItemMenu btnUpdate;
	ItemMenu btnManager;
	ItemMenu btnManagerUser;
	ItemMenu btnReport;
	ItemMenu btnInfo;
	ItemMenu btnLogout;

	float scrollMenuX;
	int scrollContentX;
	boolean isShowMenu = true;

	MenuContentBinder menuContentBinder;
	OrderContentBinder orderContentBinder;
	ScrollPanel tableContent;
	Panel containerLeft;
	ArrayList<TableObject> listTable;

	final int HOME = 0;
	final int EMENU = 1;
	final int EMENU_DETAIL = 2;
	int stateContent = HOME;

	boolean isHandlerButtonEMenuListener;
	boolean isHandlerButtonOrderListener;

	public HomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		creatButtonHeader();
		creatMenuLeft();
		creatBasicContent();
		creatTableContent();
		creatOrderContent();
		creatPopup();
	}

	void creatButtonHeader() {
		headerContentLeft.setOrientation(Orientation.HORIZONTAL);
		headerContentLeft.getElement().getStyle().setBackgroundColor("#116388");

		int width = (int) (Constants.HEIGHT * 0.09f);
		btnMenu = new CustomImageButton("image/iconMenu.png", width + "px",
				"100%");
		btnMenu.getElement().getStyle().setMarginLeft(1, Unit.PCT);
		btnOrder = new CustomImageButton("image/iconOrder.png", width + "px",
				"100%");
		btnOrder.getElement().getStyle().setMarginLeft(3, Unit.PCT);
		btnEMenu = new CustomImageButton("image/iconeMenu.png", width + "px",
				"100%");
		btnEMenu.getElement().getStyle().setMarginLeft(4, Unit.PCT);

		// tbSearch = new TextBox();
		int tbSearchWidth = (int) (0.5f * 1.33f * Constants.WIDTH - 3
				* width - 0.11 * 0.5f * 1.33f * Constants.WIDTH);
		// tbSearch.setSize(tbSearchWidth + "px", "75%");
		// tbSearch.setStyleName("tbSearch");

		custbSearch = new CustomSearchTextBox(0.95f * tbSearchWidth,
				0.8f * width);
		custbSearch.setHint("Tìm kiếm món ăn");

		headerContentLeft.add(btnMenu);
		headerContentLeft.add(btnOrder);
		headerContentLeft.add(custbSearch);
		headerContentLeft.add(btnEMenu);
	}

	void creatMenuLeft() {
		btnUser = new ItemMenu("image/iconUser1.png", "bizluvsakura");
		btnUpdate = new ItemMenu("image/iconUpdate.png", "Cập nhật dữ liệu");
		btnManager = new ItemMenu("image/iconManager.png", "Quản lý cửa hàng");
		btnManagerUser = new ItemMenu("image/iconManagerUser.png",
				"Quản lý nhân viên");
		btnReport = new ItemMenu("image/iconReport.png", "Báo cáo doanh thu");
		btnInfo = new ItemMenu("image/iconInfo.png", "Thông tin ứng dụng");
		btnLogout = new ItemMenu("image/iconLogout.png", "Đăng xuất");

		menuContent.add(btnUser);
		menuContent.add(btnUpdate);
		menuContent.add(btnManager);
		menuContent.add(btnManagerUser);
		menuContent.add(btnReport);
		menuContent.add(btnInfo);
		menuContent.add(btnLogout);
	}

	void creatBasicContent() {
		scrollContentX = 33 * Constants.WIDTH / 133;
		content.setOrientation(Orientation.HORIZONTAL);

		scrollContent.setSnap(true);
		scrollContent.setMomentum(false);
	}

	private void creatTableContent() {
		setStateHome();
		contentLeft.clear();
		contentLeft.clearAlignment();
		contentLeft.clearJustification();
		contentLeft.getElement().getStyle().setBackgroundColor("#fafafa");
		if (tableContent == null) {
			listTable = new ArrayList<TableObject>();
			tableContent = new ScrollPanel();
			tableContent.setSize("100%", "50%");
			containerLeft = new Panel();
			containerLeft.getElement().getStyle()
					.setBackgroundColor("transparent");
			containerLeft.getElement().getStyle().setPadding(0, Unit.PX);
			containerLeft.getElement().getStyle().setBorderWidth(0, Unit.PX);
			tableContent.add(containerLeft);
			for (int i = 0; i < ClientManager.getInstance().getNumber_table(); i++) {
				if (i % 3 == 0) {
					FlexPanel row = new FlexPanel();
					row.setWidth("100%");
					int height = (int) (0.5f * 1.33f * Constants.WIDTH * 2 / 12);
					row.setHeight(height + "px");
					row.setOrientation(Orientation.HORIZONTAL);
					row.setAlignment(Alignment.CENTER);
					containerLeft.add(row);
				}

				final TableObject table = new TableObject(i + 1);
				listTable.add(table);
				((FlexPanel) containerLeft.getWidget(containerLeft
						.getWidgetCount() - 1)).add(table.asWidget());
				if (OrderManagerClient.getInstance()
						.getOrder(table.getNumber()) != null) {
					table.setState(TableObject.STATE_NOT_EMPTY);
				}
			}
		}
		contentLeft.add(tableContent);
	}

	private void creatMenuContent() {
		setStateEMenu();
		contentLeft.clear();
		contentLeft.clearAlignment();
		contentLeft.clearJustification();

		if (menuContentBinder == null) {
			menuContentBinder = new MenuContentBinder();
		}
		contentLeft.add(menuContentBinder);
		contentLeft.getElement().getStyle().setBackgroundColor("#f9f2c3");
	}

	private void creatOrderContent() {
		orderContentBinder = new OrderContentBinder();
		contentRight.add(orderContentBinder);
	}

	PopupPanel popup;
	PaymentContentBinder pamentContent;
	ChangeTableBinder changeTableBinder;
	OrderDetailBinder orderDetailBinder = new OrderDetailBinder("");

	void creatPopup() {
		popup = new PopupPanel();
		popup.getElement().getStyle().setBackgroundColor("#4d4d4d");
		popup.getElement().getStyle().setPadding(0, Unit.PX);
		popup.getElement().getStyle().setMargin(0, Unit.PX);
		popup.setAutoHideEnabled(false);
		popup.setPixelSize(2 * Constants.WIDTH / 5,
				2 * Constants.HEIGHT / 3);
		popup.setPopupPosition(Constants.WIDTH / 7,
				Constants.HEIGHT / 6);
		pamentContent = new PaymentContentBinder();
		popup.add(pamentContent);
		popup.setAnimationEnabled(true);

		changeTableBinder = new ChangeTableBinder(-1);
	}

	@Override
	public PaymentContentBinder getPaymentContent() {
		return pamentContent;
	}

	@Override
	public PopupPanel getPopup() {
		return popup;
	}

	@Override
	public void showPayment() {
		popup.getElement().getStyle().setBackgroundColor("#4d4d4d");
		popup.clear();
		popup.add(pamentContent);
		popup.show();
	}

	@Override
	public void showChangeTable(int table) {
		popup.getElement().getStyle().setBackgroundColor("#FEFEFE");
		popup.clear();
		changeTableBinder.setTable(table);
		changeTableBinder.tb.setText("");
		popup.add(changeTableBinder);
		popup.show();
	}

	@Override
	public void showOrderDetail(int table, int food_id, String title, int price) {
		popup.getElement().getStyle().setBackgroundColor("#FEFEFE");
		popup.clear();
		orderDetailBinder.setOrderInfo(table, food_id, title, price);
		popup.add(orderDetailBinder);
		popup.show();
	}

	@Override
	public ChangeTableBinder getChangeTableBinder() {
		return changeTableBinder;
	}
	
	@Override
	public OrderDetailBinder getOrderDetailBinder(){
		return orderDetailBinder;
	}

	@Override
	public CustomImageButton getButtonMenu() {
		return btnMenu;
	}

	@Override
	public CustomImageButton getButtonOrder() {
		return btnOrder;
	}

	@Override
	public CustomImageButton getButtonEMenu() {
		return btnEMenu;
	}

	@Override
	public CustomSearchTextBox getSearchBox() {
		return custbSearch;
	}

	@Override
	public void showMenuContent() {
		if (stateContent != EMENU) {
			creatMenuContent();
		}
	}

	@Override
	public void handlerMenuLeft() {
		if (scrollContent.getCurrentPageX() == 0) {
			scrollContent.scrollToPage(1, 0, 500);
		} else {
			scrollContent.scrollToPage(0, 0, 500);
		}
	}

	@Override
	public CustomImageButton getButtonMenuHeader() {
		if (menuContentBinder == null)
			return null;
		return menuContentBinder.btnHeader;
	}

	@Override
	public Panel getMenuContent() {
		if (menuContentBinder == null)
			return null;
		return menuContentBinder.contentMenu;
	}

	@Override
	public Panel getMenuContentDetail() {
		if (menuContentBinder == null)
			return null;
		return menuContentBinder.contentMenu;
	}

	@Override
	public MenuContentBinder getMenuContentBinder() {
		return menuContentBinder;
	}

	@Override
	public void setStateHome() {
		stateContent = HOME;
	}

	@Override
	public void setStateEMenu() {
		stateContent = EMENU;
	}

	@Override
	public void setStateEMenuDetail() {
		stateContent = EMENU_DETAIL;
	}

	@Override
	public int getStateContent() {
		return stateContent;
	}

	@Override
	public boolean isStateHome() {
		if (stateContent == HOME)
			return true;
		else
			return false;
	}

	@Override
	public boolean isStateEMenu() {
		if (stateContent == EMENU)
			return true;
		else
			return false;
	}

	@Override
	public boolean isStateEMenuDetail() {
		if (stateContent == EMENU_DETAIL)
			return true;
		else
			return false;
	}

	@Override
	public void backtoHome() {
		creatTableContent();
		setStateHome();
	}

	@Override
	public void backToEMenu() {
		menuContentBinder.backToMenuContent();
		setStateEMenu();
	}

	@Override
	public OrderContentBinder getOrderContent() {
		return orderContentBinder;
	}

	@Override
	public void changeStateTable(int table, int state) {
		listTable.get(table - 1).setState(state);
	}

	@Override
	public TableObject getTable(int table) {
		return listTable.get(table);
	}

	@Override
	public void setUserTitle(String userName) {
		btnUser.title.setText(userName);
	}

	@Override
	public ItemMenu getItemMenuUser() {
		// TODO Auto-generated method stub
		return btnUser;
	}

	@Override
	public ItemMenu getItemMenuManager() {
		return btnManager;
	}

	@Override
	public ItemMenu getItemManagerUser() {
		return btnManagerUser;
	}

	@Override
	public ItemMenu getItemMenuReport() {
		return btnReport;
	}

	@Override
	public ItemMenu getItemMenuUpdate() {
		return btnUpdate;
	}

	@Override
	public ItemMenu getItemMenuInfo() {
		return btnInfo;
	}

	public ItemMenu getItemMenuLogout() {
		return btnLogout;
	};

	@Override
	public void setMenuLeftPermission() {
		if (EMenuCore.user.getRoleId() != UserInfo.ROLE_ADMIN) {
			btnManager.btn.getElement().getStyle()
					.setBackgroundColor("rgba(100,100,100,0.6)");
			btnReport.btn.getElement().getStyle()
					.setBackgroundColor("rgba(100,100,100,0.6)");
			btnManagerUser.btn.getElement().getStyle()
					.setBackgroundColor("rgba(100,100,100,0.6)");
		} else {
			btnManager.btn.getElement().getStyle()
					.setBackgroundColor("transparent");
			btnReport.btn.getElement().getStyle()
					.setBackgroundColor("transparent");
			btnManagerUser.btn.getElement().getStyle()
					.setBackgroundColor("transparent");
		}
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public void setText(String text) {

	}
}
