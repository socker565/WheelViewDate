package android.wheel;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class TradeInfoSearch extends Activity {

	private Button button, startBtn, endBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.tradeinfo_search);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.wheel_sub_title);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("交易查询");

		startBtn = (Button) findViewById(R.id.tradeinfo_startDate);
		endBtn = (Button) findViewById(R.id.tradeinfo_endDate);
		button = (Button) findViewById(R.id.tradeinfo_search_button);

		Date date = new Date();
		final WheelViewDialog startDialog = new WheelViewDialog(this, date);
		startDialog.setTitle("开始日期");
		startDialog.setPattern("/");
		final WheelViewDialog endDialog = new WheelViewDialog(this, date);
		endDialog.setTitle("结束日期");
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startDialog.initData(startBtn);
				startDialog.show();
			}
		});
		endBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				endDialog.initData(endBtn);
				endDialog.show();
			}
		});

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
