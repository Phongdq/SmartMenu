/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.Panel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.FoodCategory;
import com.nnm.emenu.shared.FoodInfo;

/**
 * @author bizluvsakura
 *
 */
public class MenuContentBinder extends Composite implements HasText {

	private static MenuContentBinderUiBinder uiBinder = GWT
			.create(MenuContentBinderUiBinder.class);

	interface MenuContentBinderUiBinder extends
			UiBinder<Widget, MenuContentBinder> {
	}

	@UiField
	protected FlexPanel header;
	public CustomImageButton btnHeader;
	Label lbHeader;
	@UiField
	public FlexPanel menuContent;
	@UiField
	protected ScrollPanel scrollContent;
	@UiField
	public Panel contentMenu;

	// public Panel contentMenu;
	// public Panel contentMenuDetail;
	boolean isDetailFood;
	int current_food_category_id = -1;

	public ArrayList<Integer> listParentId = new ArrayList<Integer>();
	public ArrayList<String> listTitle = new ArrayList<String>();
	public ArrayList<ItemGroupFood> listItemGroupFood = new ArrayList<ItemGroupFood>();
	public ArrayList<ItemFood> listItemFood = new ArrayList<ItemFood>();

	boolean isHandlerButtonHeader;

	public MenuContentBinder() {
		initWidget(uiBinder.createAndBindUi(this));

		initEleMent();
	}

	public MenuContentBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		initEleMent();
	}

	private void initEleMent() {
		isDetailFood = false;
		header.setOrientation(Orientation.HORIZONTAL);
		header.getElement().getStyle().setMarginTop(2, Unit.PX);
		header.getElement().getStyle().setBorderStyle(BorderStyle.DASHED);
		header.getElement().getStyle().setBorderWidth(2, Unit.PX);
		header.getElement().getStyle().setMarginLeft(-2, Unit.PX);

		int width = (int) (Constants.HEIGHT * 0.09f);
		btnHeader = new CustomImageButton("image/iconBack.png", width + "px",
				"100%");
		lbHeader = new Label("THỰC ĐƠN");
		lbHeader.setStyleName("managerview-titleheader");
		lbHeader.getElement().getStyle().setColor("#116388");
		header.add(btnHeader);
		header.add(lbHeader);

		contentMenu.getElement().getStyle().setBackgroundColor("transparent");
		contentMenu.getElement().getStyle().setBorderWidth(0, Unit.PX);
		contentMenu.getElement().getStyle().setMargin(0, Unit.PCT);
		contentMenu.getElement().getStyle().setPadding(0, Unit.PCT);
		// contentMenu.getElement().getStyle().setMarginLeft(1, Unit.PCT);
		// contentMenu.getElement().getStyle().setMarginRight(1, Unit.PCT);
		// contentMenu.getElement().getStyle().setMarginTop(1, Unit.PCT);
		// contentMenu.getElement().getStyle().setMarginBottom(1, Unit.PCT);

		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			lbHeader.getElement().getStyle().setFontSize(150, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			lbHeader.getElement().getStyle().setFontSize(170, Unit.PCT);
		} else {
			lbHeader.getElement().getStyle().setFontSize(120, Unit.PCT);
		}
	}

	public void backToMenuContent() {
		scrollContent.clear();
		contentMenu.getElement().getStyle().setBackgroundColor("transparent");
		contentMenu.getElement().getStyle().setBorderWidth(0, Unit.PX);
		scrollContent.add(contentMenu);
	}

	public void creatMenuContent(ArrayList<FoodCategory> listCategory,
			ArrayList<FoodInfo> listFood, int parent_id, String title) {
		if (parent_id == 0) {
			lbHeader.setText("THỰC ĐƠN");
		} else {
			lbHeader.setText(title);
		}
		scrollContent.clear();
		contentMenu.clear();
		listItemGroupFood.clear();
		listItemFood.clear();
		// contentMenu.getElement().getStyle().setBorderWidth(1, Unit.PX);
		// contentMenu.getElement().getStyle().setBorderStyle(BorderStyle.DASHED);
		// contentMenu.getElement().getStyle().setBorderColor("gray");
		for (int i = 0; i < listCategory.size(); i++) {
			if (i % 3 == 0) {
				FlexPanel panel = new FlexPanel();
				panel.setOrientation(Orientation.HORIZONTAL);
				float height = 0.667f * Constants.WIDTH / 3 * 10 / 7
						* 0.7f;
				panel.setSize("100%", height + "px");
				panel.getElement().getStyle().setBorderWidth(1, Unit.PX);
				panel.getElement().getStyle()
						.setBorderStyle(BorderStyle.DASHED);
				panel.getElement().getStyle().setBorderColor("gray");
				panel.getElement().getStyle().setMargin(1, Unit.PCT);
				contentMenu.add(panel);
			}
			FoodCategory foodCategory = listCategory.get(i);
			// final ItemGroupFood itemGroupFood = new ItemGroupFood(
			// foodCategory.getId(), Constants.ROOT
			// + "emenucore/download?filedown="
			// + foodCategory.getUrl(),
			// foodCategory.getTitle());
			final ItemGroupFood itemGroupFood = new ItemGroupFood(foodCategory);
			// itemGroupFood.setSize("33%", "100%");
			((FlexPanel) contentMenu
					.getWidget(contentMenu.getWidgetCount() - 1))
					.add(itemGroupFood);
			listItemGroupFood.add(itemGroupFood);
		}
		int cal = listCategory.size() % 3;
		for (int i = 0; i < listFood.size(); i++) {
			if ((i + cal) % 3 == 0) {
				FlexPanel panel = new FlexPanel();
				panel.setOrientation(Orientation.HORIZONTAL);
				float height = 0.667f * Constants.WIDTH / 3 * 10 / 7
						* 0.7f;
				panel.setSize("100%", height + "px");
				panel.getElement().getStyle().setBorderWidth(1, Unit.PX);
				panel.getElement().getStyle()
						.setBorderStyle(BorderStyle.DASHED);
				panel.getElement().getStyle().setBorderColor("gray");
				panel.getElement().getStyle().setMargin(1, Unit.PCT);
				contentMenu.add(panel);
			}
			FoodInfo food = listFood.get(i);
			// final ItemFood itemFood = new ItemFood(food.getId(),
			// Constants.ROOT
			// + "emenucore/download?filedown=" + food.getImage(),
			// food.getTitle(), food.getPrice());
			final ItemFood itemFood = new ItemFood(food);
			// itemFood.setSize("33%", "100%");
			((FlexPanel) contentMenu
					.getWidget(contentMenu.getWidgetCount() - 1)).add(itemFood);
			listItemFood.add(itemFood);
		}
		scrollContent.add(contentMenu);
	}

	public void handlerButtonHeaderListener(CustomImageButton button,
			final ClickListener listener) {
		btnHeader.handleListener(listener);
	}

	public int listParentIdSize() {
		return listParentId.size();
	}

	public void addHistory(int parent_id, String title) {
		listParentId.add(parent_id);
		listTitle.add(title);
	}

	public int lastParentId() {
		return listParentId.get(listParentIdSize() - 1);
	}

	public String lastTitle() {
		return listTitle.get(listParentIdSize() - 1);
	}

	public void removeLast() {
		listTitle.remove(listTitle.size() - 1);
		listParentId.remove(listParentId.size() - 1);
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

	public void removeHistory() {
		listParentId.clear();
		listTitle.clear();
	}

}
