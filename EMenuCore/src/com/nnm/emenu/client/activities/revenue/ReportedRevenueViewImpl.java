/**
 * 
 */
package com.nnm.emenu.client.activities.revenue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Alignment;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.nnm.emenu.client.ClientManager;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.client.ultis.DateTime;
import com.nnm.emenu.client.ultis.StringUtils;
import com.nnm.emenu.client.utils.ui.ChartImpl;
import com.nnm.emenu.client.utils.ui.CustomImageButton;
import com.nnm.emenu.client.utils.ui.ItemRevenue;
import com.nnm.emenu.client.utils.ui.Loading;
import com.nnm.emenu.shared.CountFoodOrder;
import com.nnm.emenu.shared.SumMoney;

/**
 * @author bizluvsakura
 *
 */
public class ReportedRevenueViewImpl extends Composite implements HasText,
		ReportedRevenueView {

	private static ReportedRevenueViewImplUiBinder uiBinder = GWT
			.create(ReportedRevenueViewImplUiBinder.class);

	interface ReportedRevenueViewImplUiBinder extends
			UiBinder<Widget, ReportedRevenueViewImpl> {
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
	FlexPanel contentLeft;
	@UiField
	FlexPanel tabbar;
	@UiField
	Button btnDate;
	@UiField
	Button btnProduct;
	@UiField
	Button btnCategory;

	@UiField
	FlexPanel tabfilter;
	@UiField
	Button btnFilterDay;
	@UiField
	Button btnFilterWeek;
	@UiField
	Button btnFilterMonth;
	@UiField
	Button btnFilterYear;

	@UiField
	FlexPanel tabDate;
	@UiField
	DateBox dateFrom;
	@UiField
	DateBox dateTo;
	@UiField
	Button btnView;

	@UiField
	FlexPanel tabContent;
	@UiField
	ScrollPanel scrollLeft;
	@UiField
	FlexPanel scrollContentLeft;

	@UiField
	FlexPanel contentRight;
	@UiField
	ScrollPanel scrollRight;
	@UiField
	FlexPanel scrollContentRight;

	public int tab_selected = -1;
	public static final int TAB_TOTAL_REVENUE = 0;
	public static final int TAB_PRODUCT = 1;
	public static final int TAB_USER = 2;

	public int filter_selected = -1;
	public static final int FILTER_DAY = 0;
	public static final int FILTER_WEEK = 1;
	public static final int FILTER_MONTH = 2;
	public static final int FILTER_YEAR = 3;

	boolean isHandleButtonDate;
	boolean isHandleButtonProduct;
	boolean isHandleButtonCategory;
	boolean isHandleButtonFilterDay;
	boolean isHandleButtonFilterWeek;
	boolean isHandleButtonFilterMonth;
	boolean isHandleButtonFilterYear;
	boolean isHandleButtonView;

	public ReportedRevenueViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		createElement();
		setTabSelected(TAB_TOTAL_REVENUE);
		setFilterSelected(FILTER_DAY);

		scrollContentLeft.setAlignment(Alignment.START);

		config();
	}

	void requestTotalRevenue(final int typeFilter) {
		Loading.getInstance().show();
		EMenuCore.greetingService.getSumMoney(EMenuCore.user.getId(),
				typeFilter,
				DateTime.getStringDate(dateFrom.getValue(), "yyyy-MM-dd"),
				DateTime.getStringDate(dateTo.getValue(), "yyyy-MM-dd"),
				new AsyncCallback<List<SumMoney>>() {

					@Override
					public void onFailure(Throwable caught) {

					}

					@Override
					public void onSuccess(List<SumMoney> result) {
						// TODO Auto-generated method stub
						Loading.getInstance().hide();
						if (result == null) {
							Window.alert("Không có dữ liệu!");
							return;
						}
						if (typeFilter != 4) {
							createChartTabDate(result);
						} else {
							createChartTabUser(
									result,
									DateTime.getStringDate(dateFrom.getValue(),
											"dd/MM/yyyy")
											+ " - "
											+ DateTime.getStringDate(
													dateTo.getValue(),
													"dd/MM/yyyy"));
						}
						System.out.println("RESULT : " + result.size());
						for (int i = 0; i < result.size(); i++) {
							System.out.println(result.get(i).getTime() + ":-:"
									+ result.get(i).getSumMoney());
						}
					}
				});
	}

	void requestCountProduct(final int typeFilter) {
		Loading.getInstance().show();
		EMenuCore.greetingService.getCountFoodOrder(EMenuCore.user.getId(),
				typeFilter,
				DateTime.getStringDate(dateFrom.getValue(), "yyyy-MM-dd"),
				DateTime.getStringDate(dateTo.getValue(), "yyyy-MM-dd"), 7,
				new AsyncCallback<List<CountFoodOrder>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<CountFoodOrder> result) {
						Loading.getInstance().hide();
						if (result == null) {
							Window.alert("Không có dữ liệu!");
							return;
						}

						creatChartTabProduct(
								typeFilter,
								result,
								DateTime.getStringDate(dateFrom.getValue(),
										"dd/MM/yyyy")
										+ " - "
										+ DateTime.getStringDate(
												dateTo.getValue(), "dd/MM/yyyy"));
					}
				});
	}

	void requestTotalRevenueInfo() {
		EMenuCore.greetingService.getTotalRevenueInfo(EMenuCore.user.getId(),
				DateTime.getStringDate(dateFrom.getValue(), "yyyy-MM-dd"),
				DateTime.getStringDate(dateTo.getValue(), "yyyy-MM-dd"),
				new AsyncCallback<Map<String, Long>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, Long> result) {
						if (result == null) {
							Window.alert("Không có dữ liệu!");
							return;
						}
						creatContentRight(result);
					}
				});
	}

	void createChartTabDate(List<SumMoney> result) {
		scrollLeft.clear();
		scrollContentLeft.clear();
		System.out.println("CREAT CHART");
		ChartImpl chart = new ChartImpl(Unit.PCT, getTitleChart());
		chart.represent.add("Doanh thu");
		ArrayList<Long> value = new ArrayList<Long>();
		System.out.println("RESULT SIZE : " + result.size());
		for (int i = 0; i < result.size(); i++) {
			chart.hUnit.add(result.get(i).getTime());
			value.add(result.get(i).getSumMoney());
		}
		chart.values.put(chart.represent.get(0), value);
		chart.initialize();
		int width_chart = result.size() * chart.chart_column_width;
		if (width_chart < Constants.WIDTH * 0.6675f) {
			width_chart = (int) (Constants.WIDTH * 0.6675f);
		}
		chart.setSize(width_chart + "px", "100%");
		scrollContentLeft.setSize(width_chart + "px", "100%");
		scrollContentLeft.add(chart);

		chart.setVisible(true);
		scrollLeft.add(scrollContentLeft);
	}

	void createChartTabUser(List<SumMoney> result, String hUnit) {
		scrollLeft.clear();
		scrollContentLeft.clear();
		System.out.println("CREAT CHART");
		ChartImpl chart = new ChartImpl(Unit.PCT, getTitleChart());

		chart.hUnit.add(hUnit);
		for (int i = 0; i < result.size(); i++) {
			chart.represent.add(result.get(i).getTime());
			ArrayList<Long> value = new ArrayList<Long>();
			value.add(result.get(i).getSumMoney());
			chart.values.put(result.get(i).getTime(), value);
		}
		chart.initialize();
		int width_chart = result.size() * chart.chart_column_width;
		if (width_chart < Constants.WIDTH * 0.6675f) {
			width_chart = (int) (Constants.WIDTH * 0.6675f);
		}
		chart.setSize(width_chart + "px", "100%");
		scrollContentLeft.setSize(width_chart + "px", "100%");
		scrollContentLeft.add(chart);
		chart.setVisible(true);
		scrollLeft.add(scrollContentLeft);
	}

	void creatChartTabProduct(int typeFilter, List<CountFoodOrder> result,
			String hUnit) {
		scrollLeft.clear();
		scrollContentLeft.clear();
		System.out.println("CREAT CHART");
		ChartImpl chart = new ChartImpl(Unit.PCT, getTitleChart());

		chart.hUnit.add(hUnit);
		for (int i = 0; i < result.size(); i++) {
			if (ClientManager.getInstance().getFood(result.get(i).getFood_id()) == null) {
				continue;
			}
			chart.represent.add(ClientManager.getInstance()
					.getFood(result.get(i).getFood_id()).getTitle());
			ArrayList<Long> value = new ArrayList<Long>();
			value.add((long) result.get(i).getCount());
			chart.values.put(
					ClientManager.getInstance()
							.getFood(result.get(i).getFood_id()).getTitle(),
					value);
		}
		chart.initialize();
		int width_chart = result.size() * chart.chart_column_width;
		if (width_chart < Constants.WIDTH * 0.6675f) {
			width_chart = (int) (Constants.WIDTH * 0.6675f);
		}
		chart.setSize(width_chart + "px", "100%");
		scrollContentLeft.setSize(width_chart + "px", "100%");
		scrollContentLeft.add(chart);
		chart.setVisible(true);
		scrollLeft.add(scrollContentLeft);
	}

	void creatContentRight(Map<String, Long> result) {
		scrollRight.clear();
		scrollContentRight.clear();
		ItemRevenue itemMoneyPayment = new ItemRevenue("Doanh thu",
				StringUtils.getNumberString(result.get("money_payment")), "vnđ");
		itemMoneyPayment.setHeight(80 + "px");

		ItemRevenue itemMoneyDiscount = new ItemRevenue("Số tiền chiết khấu",
				StringUtils.getNumberString(result.get("money_discount")),
				"vnđ");
		itemMoneyDiscount.setHeight(80 + "px");

		ItemRevenue itemOrderCount = new ItemRevenue("Số đơn đặt hàng",
				result.get("count_bill") + "", "đơn");
		itemOrderCount.setHeight(80 + "px");

		scrollContentRight.add(itemMoneyPayment);
		scrollContentRight.add(itemMoneyDiscount);
		scrollContentRight.add(itemOrderCount);
		scrollRight.add(scrollContentRight);

	}

	void createElement() {
		headerContentLeft.setOrientation(Orientation.HORIZONTAL);
		headerContentLeft.getElement().getStyle().setBackgroundColor("#116388");
		int width = (int) (Constants.HEIGHT * 0.09f);
		btnHome = new CustomImageButton("image/iconHome.png", width + "px",
				"100%");
		title = new Label("BÁO CÁO DOANH THU");
		title.setStyleName("managerview-titleheader");
		headerContentLeft.add(btnHome);
		headerContentLeft.add(title);

		content.setOrientation(Orientation.HORIZONTAL);

		tabbar.setOrientation(Orientation.HORIZONTAL);
		btnDate.setText("Tổng doanh thu");
		btnProduct.setText("Doanh thu theo sản phẩm");
		btnCategory.setText("Doanh thu theo nhân viên");

		tabfilter.setOrientation(Orientation.HORIZONTAL);
		tabfilter.getElement().getStyle().setBackgroundColor("white");
		btnFilterDay.setText("Xem theo ngày");
		btnFilterWeek.setText("Xem theo tuần");
		btnFilterMonth.setText("Xem theo tháng");
		btnFilterYear.setText("Xem theo năm");

		tabDate.setOrientation(Orientation.HORIZONTAL);
		tabDate.getElement().getStyle().setBackgroundColor("white");
		DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
		dateFrom.setFormat(new DateBox.DefaultFormat(format));
		dateTo.setFormat(new DateBox.DefaultFormat(format));
		dateFrom.setValue(DateTime.getDateBefore(6));
		dateTo.setValue(new Date());
		btnView.setText("Xem");
		btnView.setImportant(true);

		scrollLeft.setScrollingEnabledX(true);
		scrollLeft.setScrollingEnabledY(false);
	}

	public ReportedRevenueViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			btnCategory.getElement().getStyle().setFontSize(95, Unit.PCT);
			btnProduct.getElement().getStyle().setFontSize(95, Unit.PCT);
			btnDate.getElement().getStyle().setFontSize(95, Unit.PCT);
			btnFilterDay.getElement().getStyle().setFontSize(95, Unit.PCT);
			btnFilterMonth.getElement().getStyle().setFontSize(95, Unit.PCT);
			btnFilterWeek.getElement().getStyle().setFontSize(95, Unit.PCT);
			btnFilterYear.getElement().getStyle().setFontSize(95, Unit.PCT);
			title.getElement().getStyle().setFontSize(155, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			btnCategory.getElement().getStyle().setFontSize(125, Unit.PCT);
			btnProduct.getElement().getStyle().setFontSize(125, Unit.PCT);
			btnDate.getElement().getStyle().setFontSize(125, Unit.PCT);
			btnFilterDay.getElement().getStyle().setFontSize(125, Unit.PCT);
			btnFilterMonth.getElement().getStyle().setFontSize(125, Unit.PCT);
			btnFilterWeek.getElement().getStyle().setFontSize(125, Unit.PCT);
			btnFilterYear.getElement().getStyle().setFontSize(125, Unit.PCT);
			title.getElement().getStyle().setFontSize(190, Unit.PCT);
		} else {
			btnCategory.getElement().getStyle().setFontSize(75, Unit.PCT);
			btnProduct.getElement().getStyle().setFontSize(75, Unit.PCT);
			btnDate.getElement().getStyle().setFontSize(75, Unit.PCT);
			btnFilterDay.getElement().getStyle().setFontSize(75, Unit.PCT);
			btnFilterMonth.getElement().getStyle().setFontSize(75, Unit.PCT);
			btnFilterWeek.getElement().getStyle().setFontSize(75, Unit.PCT);
			btnFilterYear.getElement().getStyle().setFontSize(75, Unit.PCT);
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
		}
	}

	@Override
	public void setTabSelected(int index) {
		switch (index) {
		case TAB_TOTAL_REVENUE:
			if (this.tab_selected == index) {
				return;
			}
			this.tab_selected = index;
			btnFilterDay.setText("Xem theo ngày");
			btnFilterWeek.setText("Xem theo tuần");
			btnFilterDay.setVisible(true);
			btnFilterWeek.setVisible(true);
			btnFilterMonth.setVisible(true);
			btnFilterYear.setVisible(true);
			this.filter_selected = -1;
			setFilterSelected(FILTER_WEEK);
			btnDate.getElement().getStyle().setColor("black");
			btnDate.getElement().getStyle().setBackgroundColor("#EEEEEE");
			btnProduct.getElement().getStyle().setColor("white");
			btnProduct.getElement().getStyle().setBackgroundColor("#242b3d");
			btnCategory.getElement().getStyle().setColor("white");
			btnCategory.getElement().getStyle().setBackgroundColor("#242b3d");
			break;
		case TAB_PRODUCT:
			if (this.tab_selected == index) {
				return;
			}
			this.tab_selected = index;
			btnFilterDay.setText("Đặt ít nhất");
			btnFilterWeek.setText("Đặt nhiều nhất");
			btnFilterDay.setVisible(true);
			btnFilterWeek.setVisible(true);
			btnFilterMonth.setVisible(false);
			btnFilterYear.setVisible(false);
			this.filter_selected = -1;
			setFilterSelected(FILTER_WEEK);
			btnProduct.getElement().getStyle().setColor("black");
			btnProduct.getElement().getStyle().setBackgroundColor("#EEEEEE");
			btnDate.getElement().getStyle().setColor("white");
			btnDate.getElement().getStyle().setBackgroundColor("#242b3d");
			btnCategory.getElement().getStyle().setColor("white");
			btnCategory.getElement().getStyle().setBackgroundColor("#242b3d");
			break;
		case TAB_USER:
			if (this.tab_selected == index) {
				return;
			}
			this.tab_selected = index;
			btnFilterDay.setText("Ít nhất");
			btnFilterWeek.setText("Nhiều nhất");
			btnFilterDay.setVisible(false);
			btnFilterWeek.setVisible(false);
			btnFilterMonth.setVisible(false);
			btnFilterYear.setVisible(false);
			this.filter_selected = -1;
			setFilterSelected(FILTER_WEEK);
			btnDate.getElement().getStyle().setColor("white");
			btnDate.getElement().getStyle().setBackgroundColor("#242b3d");
			btnProduct.getElement().getStyle().setColor("white");
			btnProduct.getElement().getStyle().setBackgroundColor("#242b3d");
			btnCategory.getElement().getStyle().setColor("black");
			btnCategory.getElement().getStyle().setBackgroundColor("#EEEEEE");
			break;
		default:
			break;
		}
	}

	@Override
	public void requestRevenue() {
		if (tab_selected == TAB_TOTAL_REVENUE) {
			requestTotalRevenue(filter_selected);
		} else if (tab_selected == TAB_PRODUCT) {
			requestCountProduct(filter_selected);
		} else if (tab_selected == TAB_USER) {
			requestTotalRevenue(4);
		}
		requestTotalRevenueInfo();
	}

	String getTitleChart() {
		switch (tab_selected) {
		case TAB_TOTAL_REVENUE:
			switch (filter_selected) {
			case FILTER_DAY:
				return "Tổng doanh thu theo ngày";
			case FILTER_WEEK:
				return "Tổng doanh thu theo tuần";
			case FILTER_MONTH:
				return "Tổng doanh thu theo tháng";
			case FILTER_YEAR:
				return "Tổng doanh thu theo năm";
			default:
				break;
			}
			break;
		case TAB_PRODUCT:
			switch (filter_selected) {
			case FILTER_DAY:
				return "Danh sách sản phẩm đặt ít nhất";
			case FILTER_WEEK:
				return "Danh sách sản phẩm đặt nhiều nhất";
			default:
				break;
			}
			break;
		case TAB_USER:
			return "Biểu đồ bán hàng của nhân viên";
			// switch (filter_selected) {
			// case FILTER_DAY:
			//
			// break;
			// case FILTER_WEEK:
			//
			// break;
			// case FILTER_MONTH:
			//
			// break;
			// case FILTER_YEAR:
			//
			// break;
			//
			// default:
			// break;
			// }
			// break;

		default:
			break;
		}
		return "";
	}

	@Override
	public void setFilterSelected(int index) {
		switch (index) {
		case FILTER_DAY:
			if (this.filter_selected == index) {
				return;
			}
			this.filter_selected = index;
			requestRevenue();
			btnFilterDay.getElement().getStyle().setBackgroundColor("#FFFFEF");
			btnFilterWeek.getElement().getStyle().setBackgroundColor("#DDDDDD");
			btnFilterMonth.getElement().getStyle()
					.setBackgroundColor("#DDDDDD");
			btnFilterYear.getElement().getStyle().setBackgroundColor("#DDDDDD");
			break;
		case FILTER_WEEK:
			if (this.filter_selected == index) {
				return;
			}
			this.filter_selected = index;
			requestRevenue();
			btnFilterDay.getElement().getStyle().setBackgroundColor("#DDDDDD");
			btnFilterWeek.getElement().getStyle().setBackgroundColor("#FFFFEF");
			btnFilterMonth.getElement().getStyle()
					.setBackgroundColor("#DDDDDD");
			btnFilterYear.getElement().getStyle().setBackgroundColor("#DDDDDD");
			break;
		case FILTER_MONTH:
			if (this.filter_selected == index) {
				return;
			}
			this.filter_selected = index;
			requestRevenue();
			btnFilterDay.getElement().getStyle().setBackgroundColor("#DDDDDD");
			btnFilterWeek.getElement().getStyle().setBackgroundColor("#DDDDDD");
			btnFilterMonth.getElement().getStyle()
					.setBackgroundColor("#FFFFEF");
			btnFilterYear.getElement().getStyle().setBackgroundColor("#DDDDDD");
			break;
		case FILTER_YEAR:
			if (this.filter_selected == index) {
				return;
			}
			this.filter_selected = index;
			requestRevenue();
			btnFilterDay.getElement().getStyle().setBackgroundColor("#DDDDDD");
			btnFilterWeek.getElement().getStyle().setBackgroundColor("#DDDDDD");
			btnFilterMonth.getElement().getStyle()
					.setBackgroundColor("#DDDDDD");
			btnFilterYear.getElement().getStyle().setBackgroundColor("#FFFFEF");
			break;
		default:
			break;
		}
	}

	@Override
	public void handleButtonDate(final ClickListener listener) {
		if (!isHandleButtonDate) {
			isHandleButtonDate = true;
			btnDate.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonProduct(final ClickListener listener) {
		if (!isHandleButtonProduct) {
			isHandleButtonProduct = true;
			btnProduct.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonCategory(final ClickListener listener) {
		if (!isHandleButtonCategory) {
			isHandleButtonCategory = true;
			btnCategory.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonFilterDay(final ClickListener listener) {
		if (!isHandleButtonFilterDay) {
			isHandleButtonFilterDay = true;
			btnFilterDay.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonFilterWeek(final ClickListener listener) {
		if (!isHandleButtonFilterWeek) {
			isHandleButtonFilterWeek = true;
			btnFilterWeek.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonFilterMonth(final ClickListener listener) {
		if (!isHandleButtonFilterMonth) {
			isHandleButtonFilterMonth = true;
			btnFilterMonth.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonFilterYear(final ClickListener listener) {
		if (!isHandleButtonFilterYear) {
			isHandleButtonFilterYear = true;
			btnFilterYear.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	@Override
	public void handleButtonView(final ClickListener listener) {
		if (!isHandleButtonView) {
			isHandleButtonView = true;
			btnView.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	@Override
	public CustomImageButton getButtonHome() {
		return btnHome;
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
