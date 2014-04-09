package android.wheel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.wheel.adapter.DateAdapter;
import android.wheel.module.OnWheelScrollListener;
import android.wheel.module.WheelView;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author liuxian
 */
public class WheelViewDialog extends Dialog {
	private WheelView yearView;
	private WheelView monthView;
	private WheelView dayView;
	private TextView title;
	private Button done;
	private Dialog dialog;
	private DateAdapter yearAdapter;
	private DateAdapter monthAdapter;
	private DateAdapter dayAdapter;
	private TextView dateTV;
	Resources res;
	Context context;

	private int yearIndex, monthIndex, dayIndex;
	private int MIN_YEAR = 2010;
	private int MAX_YEAR;
	private List<String> yearList, monthList, dayList;
	private String[] months, days;
	private String year = "", month = "", day = "";
	final Date date;
	boolean current = false;
	String pattern = "/";

	public List<String> getYearList() {
		return yearList;
	}

	public List<String> getMonthList() {
		return monthList;
	}

	public List<String> getDayList() {
		return dayList;
	}

	public void setMinYear(int min) {
		this.MIN_YEAR = min;
	}

	public void setMaxYear(int max) {
		this.MAX_YEAR = max;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @param context
	 * @param date
	 */
	public WheelViewDialog(Context context, Date date) {
		super(context);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wheel_view_dialog);
		dialog = this;
		this.date = date;
		initViews();
		initAction();
	}

	/**
	 * 
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		Window window = this.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setLayout(ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		window.setWindowAnimations(R.style.view_animation);
		yearView = (WheelView) findViewById(R.id.year);
		monthView = (WheelView) findViewById(R.id.month);
		dayView = (WheelView) findViewById(R.id.day);
		title = (TextView) findViewById(R.id.whell_view_textview);
		done = (Button) findViewById(R.id.done);
		res = context.getResources();
		months = res.getStringArray(R.array.months);
		days = res.getStringArray(R.array.days_31);
		initYear();
	}

	/**
	 * 
	 */
	private void initYear() {
		// TODO Auto-generated method stub
		if (MAX_YEAR == 0) {
			SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
			String year = yearFormat.format(date);
			MAX_YEAR = Integer.parseInt(year);
		}
		yearList = new ArrayList<String>();
		for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
			yearList.add(i + "");
		}
	}

	/**
	 * 
	 */
	private void initAction() {
		// TODO Auto-generated method stub
		monthList = Arrays.asList(months);
		dayList = Arrays.asList(days);
		yearAdapter = new DateAdapter(context, yearList);
		monthAdapter = new DateAdapter(context, monthList);
		dayAdapter = new DateAdapter(context, dayList);
		yearView.setViewAdapter(yearAdapter);
		monthView.setViewAdapter(monthAdapter);
		dayView.setViewAdapter(dayAdapter);
		done.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				String year = (String) yearAdapter.getItemText(yearIndex);
				String month = (String) monthAdapter.getItemText(monthIndex);
				String day = (String) dayAdapter.getItemText(dayIndex);
				dateTV.setText(year + pattern + month + pattern + day);
			}
		});
		yearView.addScrollingListener(new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				yearIndex = wheel.getCurrentItem();
				String year = (String) yearAdapter.getItemText(yearIndex);
				String month = (String) monthAdapter.getItemText(monthIndex);
				if (Integer.parseInt(month) == 2) {
					if (isLeapYear(year)) {
						// 29 闰年2月29天
						if (dayAdapter.list.size() != 29) {
							dayList = Arrays.asList(res
									.getStringArray(R.array.days_29));
							dayAdapter = new DateAdapter(context, dayList);
							dayView.setViewAdapter(dayAdapter);
							if (dayIndex > 28) {
								dayView.setCurrentItem(0);
								dayIndex = 0;
							} else {
								dayView.setCurrentItem(dayIndex);
							}
						}
					} else {
						// 28 非闰年2月28天
						if (dayAdapter.list.size() != 28) {
							dayList = Arrays.asList(res
									.getStringArray(R.array.days_28));
							dayAdapter = new DateAdapter(context, dayList);
							dayView.setViewAdapter(dayAdapter);
							if (dayIndex > 27) {
								dayView.setCurrentItem(0);
								dayIndex = 0;
							} else {
								dayView.setCurrentItem(dayIndex);
							}
						}
					}
				}
			}
		});
		monthView.addScrollingListener(new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				monthIndex = wheel.getCurrentItem();
				String year = (String) yearAdapter.getItemText(yearIndex);
				String month = (String) monthAdapter.getItemText(monthIndex);
				int i = Integer.parseInt(month);
				if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10
						|| i == 12) {
					// 31
					if (dayAdapter.list.size() != 31) {
						dayList = Arrays.asList(res
								.getStringArray(R.array.days_31));
						dayAdapter = new DateAdapter(context, dayList);
						dayView.setViewAdapter(dayAdapter);
						dayView.setCurrentItem(dayIndex);
					}
				} else if (i == 2) {
					if (isLeapYear(year)) {
						// 29
						if (dayAdapter.list.size() != 29) {
							dayList = Arrays.asList(res
									.getStringArray(R.array.days_29));
							dayAdapter = new DateAdapter(context, dayList);
							dayView.setViewAdapter(dayAdapter);
							if (dayIndex > 28) {
								dayView.setCurrentItem(0);
								dayIndex = 0;
							} else {
								dayView.setCurrentItem(dayIndex);
							}
						}
					} else {
						// 28
						if (dayAdapter.list.size() != 28) {
							dayList = Arrays.asList(res
									.getStringArray(R.array.days_28));
							dayAdapter = new DateAdapter(context, dayList);
							dayView.setViewAdapter(dayAdapter);
							if (dayIndex > 27) {
								dayView.setCurrentItem(0);
								dayIndex = 0;
							} else {
								dayView.setCurrentItem(dayIndex);
							}
						}
					}
				} else {
					// 30
					if (dayAdapter.list.size() != 30) {
						dayList = Arrays.asList(res
								.getStringArray(R.array.days_30));
						dayAdapter = new DateAdapter(context, dayList);
						dayView.setViewAdapter(dayAdapter);
						if (dayIndex > 29) {
							dayView.setCurrentItem(0);
							dayIndex = 0;
						} else {
							dayView.setCurrentItem(dayIndex);
						}
					}
				}
			}
		});
		dayView.addScrollingListener(new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				dayIndex = wheel.getCurrentItem();
			}
		});
	}

	public void setTitle(String str) {
		title.setText(str);
	}

	/**
	 * 判断是否是闰年
	 * */
	public static boolean isLeapYear(String str) {
		int year = Integer.parseInt(str);
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
	}

	/**
	 * @param startBtn
	 * 
	 */
	public void initData(TextView btn) {
		// TODO Auto-generated method stub
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
		this.dateTV = btn;
		if ("".equals(year)) {
			year = MAX_YEAR + "";
			month = monthFormat.format(date);
			day = dayFormat.format(date);
		}
		yearIndex = yearList.indexOf(year);
		monthIndex = monthList.indexOf(month);
		dayIndex = dayList.indexOf(day);
		if (yearIndex == -1) {
			yearIndex = 0;
		}
		if (monthIndex == -1) {
			monthIndex = 0;
		}
		if (dayIndex == -1) {
			dayIndex = 0;
		}
		yearView.setCurrentItem(yearIndex);
		monthView.setCurrentItem(monthIndex);
		dayView.setCurrentItem(dayIndex);
	}
}
