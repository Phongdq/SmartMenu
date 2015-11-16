/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import java.sql.Date;

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
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.client.ultis.DateTime;
import com.nnm.emenu.shared.FoodInfo;

/**
 * @author bizluvsakura
 *
 */
public class ItemProduct extends Composite implements HasText {

	private static ItemProductUiBinder uiBinder = GWT
			.create(ItemProductUiBinder.class);

	interface ItemProductUiBinder extends UiBinder<Widget, ItemProduct> {
	}

	@UiField
	FlexPanel parent;
	@UiField
	FlexPanel panelStt;
	@UiField
	Label lbStt;
	@UiField
	public Label lbName;
	@UiField
	public Label lbCode;
	@UiField
	public Label lbState;
	@UiField
	public Label lbPrice;
	@UiField
	public Label lbCategory;
	@UiField
	public Label lbDate;
	@UiField
	FlexPanel note;
	public CustomImageButton btnEdit;
	public CustomImageButton btnDelete;

	public FoodInfo foodInfo;

	public ItemProduct() {
		initWidget(uiBinder.createAndBindUi(this));

		parent.setOrientation(Orientation.HORIZONTAL);
		parent.getElement().getStyle().setMarginLeft(1, Unit.PCT);

		lbStt.setText("STT");
		lbName.setText("Tên sản phẩm");
		lbCode.setText("Mã sản phẩm");
		lbState.setText("Tình trạng");
		lbPrice.setText("Giá tiền");
		lbCategory.setText("Danh mục");
		lbDate.setText("Ngày tạo");

		lbStt.getElement().getStyle().setColor("#116388");
		lbName.getElement().getStyle().setColor("#116388");
		lbCode.getElement().getStyle().setColor("#116388");
		lbState.getElement().getStyle().setColor("#116388");
		lbPrice.getElement().getStyle().setColor("#116388");
		lbCategory.getElement().getStyle().setColor("#116388");
		lbDate.getElement().getStyle().setColor("#116388");

		note.setOrientation(Orientation.HORIZONTAL);
		note.getElement().getStyle().setBorderColor("black");
		note.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		note.getElement().getStyle().setBorderWidth(1, Unit.PX);

		config();
	}

	public ItemProduct(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		parent.setOrientation(Orientation.HORIZONTAL);
		parent.getElement().getStyle().setMarginLeft(1, Unit.PCT);

		lbStt.setText("STT");
		lbName.setText("Tên sản phẩm");
		lbCode.setText("Mã sản phẩm");
		lbState.setText("Tình trạng");
		lbPrice.setText("Giá tiền");
		lbCategory.setText("Danh mục");
		lbDate.setText("Ngày tạo");

		note.setOrientation(Orientation.HORIZONTAL);
		note.getElement().getStyle().setBorderColor("black");
		note.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		note.getElement().getStyle().setBorderWidth(1, Unit.PX);

		config();
	}

	public ItemProduct(int stt, String name, String code, int state, int price,
			String category, String date) {
		initWidget(uiBinder.createAndBindUi(this));

		parent.setOrientation(Orientation.HORIZONTAL);
		parent.getElement().getStyle().setMarginLeft(1, Unit.PCT);

		lbStt.setText(stt + "");
		lbName.setText(name + "");
		lbCode.setText(code + "");
		lbState.setText("Còn hàng");
		lbPrice.setText(price + "");
		lbCategory.setText(category + "");
		lbDate.setText(date + "");

		note.setOrientation(Orientation.HORIZONTAL);
		note.getElement().getStyle().setBorderColor("black");
		note.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		note.getElement().getStyle().setBorderWidth(1, Unit.PX);
		btnEdit = new CustomImageButton("image/iconEdit.png", 30 + "px",
				30 + "px");
		btnDelete = new CustomImageButton("image/iconClose.png", 30 + "px",
				30 + "px");
		btnEdit.getElement().getStyle().setMarginLeft(10, Unit.PCT);
		btnDelete.getElement().getStyle().setMarginLeft(10, Unit.PCT);

		note.add(btnEdit);
		note.add(btnDelete);

		config();
	}

	public ItemProduct(int stt, FoodInfo food) {
		initWidget(uiBinder.createAndBindUi(this));
		this.foodInfo = food;

		parent.setOrientation(Orientation.HORIZONTAL);
		parent.getElement().getStyle().setMarginLeft(1, Unit.PCT);

		lbStt.setText(stt + "");
		lbName.setText(food.getTitle() + "");
		lbCode.setText(food.getCode() + "");
		lbState.setText("Còn hàng");
		lbPrice.setText(food.getPrice() + "");
		lbCategory.setText(food.getCategory_title() + "");
		if (food.getGendate() != null) {
			lbDate.setText(food.getGendate().substring(0, 10));
		}
		note.setOrientation(Orientation.HORIZONTAL);
		note.getElement().getStyle().setBorderColor("black");
		note.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		note.getElement().getStyle().setBorderWidth(1, Unit.PX);
		btnEdit = new CustomImageButton("image/iconEdit.png", 30 + "px",
				30 + "px");
		btnDelete = new CustomImageButton("image/iconClose.png", 30 + "px",
				30 + "px");
		btnEdit.getElement().getStyle().setMarginLeft(10, Unit.PCT);
		btnDelete.getElement().getStyle().setMarginLeft(10, Unit.PCT);
		note.add(btnEdit);
		note.add(btnDelete);

		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			lbCategory.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbCode.getElement().getStyle().setFontSize(115, Unit.PCT);
			lbDate.getElement().getStyle().setFontSize(115, Unit.PCT);
			lbName.getElement().getStyle().setFontSize(115, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(115, Unit.PCT);
			lbState.getElement().getStyle().setFontSize(115, Unit.PCT);
			lbStt.getElement().getStyle().setFontSize(115, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			lbCategory.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbCode.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbDate.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbName.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbState.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbStt.getElement().getStyle().setFontSize(130, Unit.PCT);
		} else {
			lbCategory.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbCode.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbDate.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbName.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbState.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbStt.getElement().getStyle().setFontSize(100, Unit.PCT);
		}
	}

	public void setText(String text) {

	}

	public String getText() {
		return "";
	}

}
