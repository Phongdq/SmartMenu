/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.image.ImageHolder;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.FoodCategory;

/**
 * @author bizluvsakura
 *
 */
public class AddCategoryBinder extends Composite implements HasText {

	private static AddCategoryBinderUiBinder uiBinder = GWT
			.create(AddCategoryBinderUiBinder.class);

	interface AddCategoryBinderUiBinder extends
			UiBinder<Widget, AddCategoryBinder> {
	}

	@UiField
	FlexPanel parent;
	// header
	@UiField
	FlexPanel header;
	@UiField
	Label title;
	CustomImageButton btnClose;
	// content
	@UiField
	FlexPanel content;
	// contentLeft
	@UiField
	FlexPanel contentLeft;
	@UiField
	FlexPanel paneltitle;
	@UiField
	Label titleContentLeft;
	@UiField
	FlexPanel panelName;
	@UiField
	Label lbName;
	@UiField
	public TextBox tbName;
	@UiField
	FlexPanel panelParent;
	@UiField
	Label lbParent;
	@UiField
	public ListBox ltbParent;
	@UiField
	FlexPanel panelImage;
	@UiField
	Label lbImage;
	@UiField
	FlexPanel subpanelImage;
	@UiField
	public Image image;
	@UiField
	public Label imageUrl;
	@UiField
	FlexPanel panelDes;
	@UiField
	Label lbDes;
	@UiField
	public TextArea taDes;
	@UiField
	FlexPanel panelButton;
	@UiField
	Button btnAdd;
	// contentRight
	@UiField
	FlexPanel contentRight;
	@UiField
	ScrollPanel scroll;
	@UiField
	public FlexPanel scrollContent;

	public FormPanel form;
	public FileUpload fileUpload;

	boolean isHandleButtonClose;
	boolean isHandleButtonAdd;
	boolean isHandleFileUpload;
	boolean isHandleForm;
	boolean isHandleImage;

	int STATE_NEW = 0;
	int STATE_UPDATE = 1;
	int state = STATE_NEW;

	public int current_id_selected;

	public AddCategoryBinder() {
		initWidget(uiBinder.createAndBindUi(this));

		createElement();
	}

	public AddCategoryBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	void createElement() {
		parent.getElement().getStyle().setBorderColor("black");
		parent.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		parent.getElement().getStyle().setBorderWidth(1, Unit.PX);

		header.setOrientation(Orientation.HORIZONTAL);
		header.getElement().getStyle().setBorderColor("black");
		header.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		header.getElement().getStyle().setBorderWidth(1, Unit.PX);
		title.setText("Thêm nhóm thực đơn");

		float btnClose_height = Constants.HEIGHT * 0.8f * 0.1f * 0.9f;
		btnClose = new CustomImageButton("image/iconClose.png", btnClose_height
				+ "px", btnClose_height + "px");
		header.add(btnClose);

		content.setOrientation(Orientation.HORIZONTAL);
		contentLeft.getElement().getStyle().setBackgroundColor("white");
		contentLeft.getElement().getStyle().setBorderColor("black");
		contentLeft.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		contentLeft.getElement().getStyle().setBorderWidth(1, Unit.PX);

		titleContentLeft.setText("Thêm danh mục sản phẩm mới");

		panelName.setOrientation(Orientation.HORIZONTAL);
		lbName.setText("Tên");
		float tbName_width = Constants.WIDTH * 0.9f * 0.4f * 0.75f
				* 0.8f;
		tbName.setWidth((tbName_width - 12) + "px");

		panelParent.setOrientation(Orientation.HORIZONTAL);
		lbParent.setText("Nhóm cha mẹ");
		ltbParent.setWidth(tbName_width + "px");
		ltbParent.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {

			}
		});

		form = new FormPanel();
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setAction("emenucore/upload");
		fileUpload = new FileUpload();
		fileUpload.setName("filename");
		form.add(fileUpload);
		panelImage.setOrientation(Orientation.HORIZONTAL);
		lbImage.setText("Tải hình ảnh");
		float height = Constants.HEIGHT * 0.25f * 0.9f * 0.95f * 0.8f;
		subpanelImage.setOrientation(Orientation.VERTICAL);
		subpanelImage.setSize(height + "px", height + "px");
		subpanelImage.getElement().getStyle().setBorderColor("black");
		subpanelImage.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		subpanelImage.getElement().getStyle().setBorderWidth(1, Unit.PX);
		subpanelImage.getElement().getStyle().setMarginLeft(1, Unit.PCT);
		image.setResource(ImageHolder.get().picture());
		panelImage.add(form);
		form.setVisible(false);

		panelDes.setOrientation(Orientation.HORIZONTAL);
		lbDes.setText("Mô tả");
		taDes.setWidth((tbName_width - 10) + "px");

		btnAdd.setText("Thêm mới");
		btnAdd.setImportant(true);

		contentRight.getElement().getStyle().setBorderColor("black");
		contentRight.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		contentRight.getElement().getStyle().setBorderWidth(1, Unit.PX);

		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			title.getElement().getStyle().setFontSize(130, Unit.PCT);
			titleContentLeft.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbDes.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbImage.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbName.getElement().getStyle().setFontSize(100, Unit.PCT);
			lbParent.getElement().getStyle().setFontSize(100, Unit.PCT);
			tbName.getElement().getStyle().setFontSize(110, Unit.PCT);
			taDes.getElement().getStyle().setFontSize(110, Unit.PCT);
			ltbParent.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnAdd.getElement().getStyle().setFontSize(100, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
			titleContentLeft.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbDes.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbImage.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbName.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbParent.getElement().getStyle().setFontSize(110, Unit.PCT);
			tbName.getElement().getStyle().setFontSize(120, Unit.PCT);
			taDes.getElement().getStyle().setFontSize(120, Unit.PCT);
			ltbParent.getElement().getStyle().setFontSize(120, Unit.PCT);
			btnAdd.getElement().getStyle().setFontSize(110, Unit.PCT);
		} else {
			title.getElement().getStyle().setFontSize(120, Unit.PCT);
			titleContentLeft.getElement().getStyle().setFontSize(95, Unit.PCT);
			lbDes.getElement().getStyle().setFontSize(85, Unit.PCT);
			lbImage.getElement().getStyle().setFontSize(85, Unit.PCT);
			lbName.getElement().getStyle().setFontSize(85, Unit.PCT);
			lbParent.getElement().getStyle().setFontSize(85, Unit.PCT);
			tbName.getElement().getStyle().setFontSize(90, Unit.PCT);
			taDes.getElement().getStyle().setFontSize(90, Unit.PCT);
			ltbParent.getElement().getStyle().setFontSize(90, Unit.PCT);
			btnAdd.getElement().getStyle().setFontSize(90, Unit.PCT);
		}
	}

	public void create(List<FoodCategory> result) {
		createListBoxParent(result);
		createListCategory(result);
		Loading.getInstance().hide();
	}

	public boolean checkFull() {
		if (tbName.getText().equals("")) {
			return false;
		}
		return true;
	}

	public void createListCategory(List<FoodCategory> listFoodCategory) {
		scroll.clear();
		scrollContent.clear();
		scrollContent.add(new ItemCategory());
		for (int i = 0; i < listFoodCategory.size(); i++) {
			ItemCategory item = new ItemCategory(listFoodCategory.get(i));
			scrollContent.add(item);
		}
		scroll.add(scrollContent);
	}

	public void createListBoxParent(List<FoodCategory> listFoodCategory) {
		ltbParent.clear();
		ltbParent.addItem("Nhóm thực đơn", "0");
		for (int i = 0; i < listFoodCategory.size(); i++) {
			ltbParent.addItem(listFoodCategory.get(i).getTitle(),
					listFoodCategory.get(i).getId() + "");
		}
	}

	public void addFoodCategory(FoodCategory foodCategory) {
		scroll.clear();
		ItemCategory item = new ItemCategory(foodCategory);
		scrollContent.add(item);
		scrollContent.setHeight("auto");
		scroll.add(scrollContent);
	}

	public void updateListFoodCategory(FoodCategory foodCategory) {
		ItemCategory item = getItemCategory(foodCategory.getId());
		item.foodCategory = foodCategory;
		item.lbName.setText(foodCategory.getTitle());
		item.lbDes.setText(foodCategory.getDescription());
		item.image.setUrl(Constants.ROOT + "emenucore/download?filedown="
				+ foodCategory.getUrl());
	}

	public ItemCategory getItemCategory(int id) {
		for (int i = 1; i < scrollContent.getWidgetCount(); i++) {
			ItemCategory item = (ItemCategory) scrollContent.getWidget(i);
			if (item.foodCategory.getId() == id) {
				return item;
			}
		}
		return null;
	}

	public void removeItemCategory(int id) {
		for (int i = 1; i < scrollContent.getWidgetCount(); i++) {
			ItemCategory item = (ItemCategory) scrollContent.getWidget(i);
			if (item.foodCategory.getId() == i) {
				scrollContent.remove(i);
				return;
			}
		}
	}

	public void reset() {
		tbName.setText("");
		taDes.setText("");
		imageUrl.setText("");
	}

	public void handleButtonSave(final ClickListener listener) {
		if (!isHandleButtonAdd) {
			isHandleButtonAdd = true;
			btnAdd.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	public void handleButtonClose(final ClickListener listener) {
		btnClose.handleListener(listener);
	}

	public void handleFileUpload(final ClickListener listener) {
		if (!isHandleFileUpload) {
			isHandleFileUpload = true;
			fileUpload.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					listener.onClick();
				}
			});
		}
	}

	public void handleForm(final ClickListener listener) {
		if (!isHandleForm) {
			isHandleForm = true;
			form.addSubmitCompleteHandler(new SubmitCompleteHandler() {

				@Override
				public void onSubmitComplete(SubmitCompleteEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void handleImage(final ClickListener listener) {
		if (!isHandleImage) {
			isHandleImage = true;
			image.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					listener.onClick();
				}
			});
		}
	}

	public void setStateNew() {
		this.state = STATE_NEW;
		reset();
		image.setResource(ImageHolder.get().picture());
		btnAdd.setText("Thêm mới");
	}

	public void setStateUpdate() {
		this.state = STATE_UPDATE;
		imageUrl.setText("");
		btnAdd.setText("Cập nhật");
	}

	public boolean isStateNew() {
		if (state == STATE_NEW)
			return true;
		return false;
	}

	public boolean isStateUpdate() {
		if (state == STATE_UPDATE)
			return true;
		return false;
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
