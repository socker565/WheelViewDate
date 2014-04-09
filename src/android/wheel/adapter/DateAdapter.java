
package android.wheel.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.wheel.R;
import android.widget.TextView;

/**
 * @author liuxian
 */
public class DateAdapter extends AbstractWheelTextAdapter {
	/**
	 * Constructor
	 */
	public List<String> list;

	public DateAdapter(Context context, List<String> list) {
		super(context, R.layout.wheel_view_layout, NO_RESOURCE);
		this.list = list;
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		View view = super.getItem(index, cachedView, parent);

		TextView textCity = (TextView) view.findViewById(R.id.textView);
		textCity.setText(list.get(index));
		return view;
	}

	public int getItemsCount() {
		return list.size();
	}

	@Override
	public CharSequence getItemText(int index) {
		return list.get(index);
	}
}