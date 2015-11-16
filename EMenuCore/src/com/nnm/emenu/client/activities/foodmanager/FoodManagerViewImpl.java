/**
 * 
 */
package com.nnm.emenu.client.activities.foodmanager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.client.utils.ui.CustomImageButton;
import com.nnm.emenu.client.utils.ui.CustomSearchTextBox;
import com.nnm.emenu.client.utils.ui.Loading;
import com.nnm.emenu.client.utils.ui.ProductHomeBinder;

/**
 * @author bizluvsakura
 *
 */
public class FoodManagerViewImpl extends Composite implements HasText,
		FoodManagerView {

	private static AddFoodViewImplUiBinder uiBinder = GWT
			.create(AddFoodViewImplUiBinder.class);

	interface AddFoodViewImplUiBinder extends
			UiBinder<Widget, FoodManagerViewImpl> {
	}

	@UiField
	FlexPanel parent;
	// header left
	@UiField
	FlexPanel headerContentLeft;
	CustomImageButton btnHome;
	Label title;
	// content
	@UiField
	FlexPanel content;
	// tabbar
	@UiField
	FlexPanel tabbar;
	@UiField
	Button btnProduct;
	@UiField
	Button btnMaterial;
	@UiField
	Button btnExpenses;
	@UiField
	Button btnOrder;
	// @UiField
	// TextBox tbSearch;

	CustomSearchTextBox tbSearch;

	@UiField
	FlexPanel tabcontent;

	public int selected = -1;
	public static final int TAB_PRODUCT = 0;
	public static final int TAB_MATERIAL = 1;
	public static final int TAB_EXPENSES = 2;
	public static final int TAB_ORDER = 3;

	boolean isHandleButtonProduct;
	boolean isHandleButtonMaterial;
	boolean isHandleButtonExpenses;
	boolean isHandleButtonOrder;

	ProductHomeBinder productHomeBinder;

	public FoodManagerViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		creatElement();
	}

	public FoodManagerViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		creatElement();
	}

	void creatElement() {
		headerContentLeft.setOrientation(Orientation.HORIZONTAL);
		headerContentLeft.getElement().getStyle().setBackgroundColor("#116388");
		int width = (int) (Constants.HEIGHT * 0.09f);
		btnHome = new CustomImageButton("image/iconHome.png", width + "px",
				"100%");
		title = new Label("QUẢN LÝ CỬA HÀNG");
		title.setStyleName("managerview-titleheader");
		headerContentLeft.add(btnHome);
		headerContentLeft.add(title);

		tabbar.setOrientation(Orientation.HORIZONTAL);
		btnProduct.setText("Sản phẩm");
		btnMaterial.setText("Nguyên liệu");
		btnExpenses.setText("Chi phí");
		btnOrder.setText("Đơn hàng");
		float width_tbSearch = Constants.WIDTH * 0.28f;
		float height_tbSearch = Constants.HEIGHT * 0.08f * 0.91f * 0.7f;
		tbSearch = new CustomSearchTextBox(width_tbSearch, height_tbSearch);
		tbSearch.tb.getElement().getStyle().setFontSize(120, Unit.PCT);
		tbSearch.setHint("Tìm kiếm món ăn");
		tabcontent.getElement().getStyle().setBackgroundColor("#FFFFFF");
		tabbar.add(tbSearch);

		productHomeBinder = new ProductHomeBinder();
//		setSelected(TAB_PRODUCT);
		
		config();
	}
	
	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			title.getElement().getStyle().setFontSize(170, Unit.PCT);
			tbSearch.tb.getElement().getStyle().setFontSize(120, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			title.getElement().getStyle().setFontSize(190, Unit.PCT);
			tbSearch.tb.getElement().getStyle().setFontSize(135, Unit.PCT);
		} else {
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
			tbSearch.tb.getElement().getStyle().setFontSize(100, Unit.PCT);
		}
	}

	@Override
	public void setSelected(int index) {
		switch (index) {
		case TAB_PRODUCT:
			if (this.selected == index) {
				return;
			}
			this.selected = index;
			tabcontent.clear();
			tabcontent.add(productHomeBinder);
			btnProduct.getElement().getStyle().setColor("black");
			btnProduct.getElement().getStyle().setBackgroundColor("#EEEEEE");
			btnMaterial.getElement().getStyle().setColor("white");
			btnMaterial.getElement().getStyle().setBackgroundColor("#242b3d");
			btnExpenses.getElement().getStyle().setColor("white");
			btnExpenses.getElement().getStyle().setBackgroundColor("#242b3d");
			btnOrder.getElement().getStyle().setColor("white");
			btnOrder.getElement().getStyle().setBackgroundColor("#242b3d");
			break;
		case TAB_MATERIAL:
			this.selected = index;
			btnMaterial.getElement().getStyle().setColor("black");
			btnMaterial.getElement().getStyle().setBackgroundColor("#EEEEEE");
			btnProduct.getElement().getStyle().setColor("white");
			btnProduct.getElement().getStyle().setBackgroundColor("#242b3d");
			btnExpenses.getElement().getStyle().setColor("white");
			btnExpenses.getElement().getStyle().setBackgroundColor("#242b3d");
			btnOrder.getElement().getStyle().setColor("white");
			btnOrder.getElement().getStyle().setBackgroundColor("#242b3d");
			break;
		case TAB_EXPENSES:
			this.selected = index;
			btnProduct.getElement().getStyle().setColor("white");
			btnProduct.getElement().getStyle().setBackgroundColor("#242b3d");
			btnMaterial.getElement().getStyle().setColor("white");
			btnMaterial.getElement().getStyle().setBackgroundColor("#242b3d");
			btnOrder.getElement().getStyle().setColor("white");
			btnOrder.getElement().getStyle().setBackgroundColor("#242b3d");
			btnExpenses.getElement().getStyle().setColor("black");
			btnExpenses.getElement().getStyle().setBackgroundColor("#EEEEEE");
			break;
		case TAB_ORDER:
			this.selected = index;
			btnMaterial.getElement().getStyle().setColor("white");
			btnMaterial.getElement().getStyle().setBackgroundColor("#242b3d");
			btnExpenses.getElement().getStyle().setColor("white");
			btnExpenses.getElement().getStyle().setBackgroundColor("#242b3d");
			btnProduct.getElement().getStyle().setColor("white");
			btnProduct.getElement().getStyle().setBackgroundColor("#242b3d");
			btnOrder.getElement().getStyle().setColor("black");
			btnOrder.getElement().getStyle().setBackgroundColor("#EEEEEE");
			break;
		default:
			break;
		}
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

	@Override
	public void handleButtonHome(ClickListener listener) {
		btnHome.handleListener(listener);
	}

	@Override
	public void handleButtonProduct(final ClickListener listener) {
		if (!isHandleButtonProduct) {
			isHandleButtonProduct = true;
			btnProduct.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonMaterial(final ClickListener listener) {
		if (!isHandleButtonMaterial) {
			isHandleButtonMaterial = true;
			btnMaterial.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonExpenses(final ClickListener listener) {
		if (!isHandleButtonExpenses) {
			isHandleButtonExpenses = true;
			btnExpenses.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonOrder(final ClickListener listener) {
		if (!isHandleButtonOrder) {
			isHandleButtonOrder = true;
			btnOrder.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	@Override
	public ProductHomeBinder getProductHome() {
		return productHomeBinder;
	}

	@Override
	public CustomSearchTextBox getSearchBox() {
		return tbSearch;
	}

}
