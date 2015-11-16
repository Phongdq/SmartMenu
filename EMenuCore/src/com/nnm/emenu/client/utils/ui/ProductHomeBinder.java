/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.nnm.emenu.client.ClientManager;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.FoodCategory;
import com.nnm.emenu.shared.FoodInfo;

/**
 * @author bizluvsakura
 *
 */
public class ProductHomeBinder extends Composite implements HasText {

	private static ProductHomeBinderUiBinder uiBinder = GWT
			.create(ProductHomeBinderUiBinder.class);

	interface ProductHomeBinderUiBinder extends
			UiBinder<Widget, ProductHomeBinder> {
	}

	@UiField
	FlexPanel top;
	@UiField
	Button btnProduct;
	@UiField
	Button btnCategory;
	@UiField
	ScrollPanel scroll;
	@UiField
	public FlexPanel scrollContent;

	public PopupPanel popup;

	boolean isHandleButtonCategory;
	boolean isHanleButtonProduct;

	public AddProductBinder addProductBinder;
	public AddCategoryBinder addCategoryBinder;

	public List<FoodCategory> listFoodCategory;

	public ProductHomeBinder() {
		initWidget(uiBinder.createAndBindUi(this));

		top.setOrientation(Orientation.HORIZONTAL);
		btnProduct.setText("Thêm sản phẩm");
		btnCategory.setText("Thêm nhóm thực đơn");
		scroll.getElement().getStyle().setMarginTop(4, Unit.PCT);
		scroll.getElement().getStyle().setBorderWidth(1, Unit.PX);
		scroll.getElement().getStyle().setBorderColor("black");
		scroll.getElement().getStyle().setBorderStyle(BorderStyle.DOTTED);

		popup = new PopupPanel();
		popup.setAutoHideEnabled(false);
		popup.getElement().getStyle().setBackgroundColor("#EEEEEE");
		popup.getElement().getStyle().setPadding(0, Unit.PX);
		popup.getElement().getStyle().setMargin(0, Unit.PX);

		addProductBinder = new AddProductBinder();
		addCategoryBinder = new AddCategoryBinder();

		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			btnCategory.getElement().getStyle().setFontSize(115, Unit.PCT);
			btnProduct.getElement().getStyle().setFontSize(115, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			btnCategory.getElement().getStyle().setFontSize(130, Unit.PCT);
			btnProduct.getElement().getStyle().setFontSize(130, Unit.PCT);
		} else {
			btnCategory.getElement().getStyle().setFontSize(95, Unit.PCT);
			btnProduct.getElement().getStyle().setFontSize(95, Unit.PCT);
		}
	}

	public void creat() {
		scroll.clear();
		scrollContent.clear();
		scrollContent.add(new ItemProduct());
		ArrayList<FoodInfo> listFood = new ArrayList<FoodInfo>(
				ClientManager.getInstance().food.values());
		for (int i = 0; i < listFood.size(); i++) {
			scrollContent.add(new ItemProduct(i + 1, listFood.get(i)));
		}
		scroll.add(scrollContent);
		Loading.getInstance().hide();
	}

	public void deleteFood(String code) {
		for (int i = 1; i < scrollContent.getWidgetCount(); i++) {
			ItemProduct item = (ItemProduct) scrollContent.getWidget(i);
			if (item.foodInfo.getCode().equals(code)) {
				scrollContent.remove(i);
				return;
			}
		}
	}

	public void addFood(FoodInfo foodInfo) {
		scroll.clear();
		ItemProduct item = new ItemProduct(scrollContent.getWidgetCount(),
				foodInfo);
		scrollContent.add(item);
		scrollContent.setHeight("auto");
		scroll.add(scrollContent);
	}

	public void updateFood(String code, FoodInfo food) {
		for (int i = 1; i < scrollContent.getWidgetCount(); i++) {
			ItemProduct item = (ItemProduct) scrollContent.getWidget(i);
			if (item.foodInfo.getCode().equals(code)) {
				item.lbCategory.setText(food.getCategory_title());
				item.lbCode.setText(food.getCode());
				item.lbDate.setText(food.getGendate().substring(0, 10));
				item.lbName.setText(food.getTitle());
				item.lbPrice.setText(food.getPrice() + "");
				item.lbState.setText(food.getState() == 0 ? "Hết hàng"
						: "Còn hàng");
				item.foodInfo = food;
				return;
			}
		}
	}

	public void create(List<FoodInfo> listFoods) {
		scroll.clear();
		scrollContent.clear();
		scrollContent.add(new ItemProduct());
		for (int i = 0; i < listFoods.size(); i++) {
			scrollContent.add(new ItemProduct(i + 1, listFoods.get(i)));
		}
		scroll.add(scrollContent);
		Loading.getInstance().hide();
	}

	public void showCategory() {
		popup.setPixelSize(Constants.WIDTH * 94 / 100,
				Constants.HEIGHT * 80 / 100);
		popup.setPopupPosition(Constants.WIDTH * 3 / 100,
				Constants.HEIGHT * 15 / 100);
		popup.clear();
		popup.add(addCategoryBinder);
		popup.show();
	}

	public void showProduct() {
		popup.setPixelSize(Constants.WIDTH * 70 / 100,
				Constants.HEIGHT * 80 / 100);
		popup.setPopupPosition(Constants.WIDTH * 15 / 100,
				Constants.HEIGHT * 15 / 100);
		popup.clear();
		popup.add(addProductBinder);
		popup.show();
	}

	public ProductHomeBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

	public void handleButtonCategory(final ClickListener listener) {
		if (!isHandleButtonCategory) {
			isHandleButtonCategory = true;
			btnCategory.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	public void handleButtonProduct(final ClickListener listener) {
		if (!isHanleButtonProduct) {
			isHanleButtonProduct = true;
			btnProduct.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	public int getItemIndex(int categoryId) {
		for (int i = 0; i < listFoodCategory.size(); i++) {
			if (categoryId == listFoodCategory.get(i).getId()) {
				System.out.println("index :=======: " + i + 1);
				return i + 1;
			}
		}
		return -1;
	}

	public ArrayList<FoodCategory> removeFoodCategory(FoodCategory foodCategory) {
		ArrayList<FoodCategory> result = new ArrayList<FoodCategory>();
		for (int i = 0; i < listFoodCategory.size(); i++) {
			if (listFoodCategory.get(i).getId() != foodCategory.getId()) {
				result.add(listFoodCategory.get(i));
			}
		}
		return result;
	}

	public void replaceFoodCategory(int old_foodcategory_id,
			FoodCategory newFoodCategory) {
		for (int i = 0; i < listFoodCategory.size(); i++) {
			if (listFoodCategory.get(i).getId() == old_foodcategory_id) {
				listFoodCategory.set(i, newFoodCategory);
				return;
			}
		}
	}
}
