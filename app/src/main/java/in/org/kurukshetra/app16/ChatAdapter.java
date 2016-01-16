package in.org.kurukshetra.app16;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Message> chatData;

	public ChatAdapter (Context context, ArrayList<Message> stringList) {
		mContext = context;
		chatData = stringList;
	}

	@Override
	public boolean areAllItemsEnabled () {
		return true;
	}

	@Override
	public boolean isEnabled (int position) {
		return true;
	}

	@Override
	public void registerDataSetObserver (DataSetObserver observer) {}

	@Override
	public void unregisterDataSetObserver (DataSetObserver observer) {}

	@Override
	public int getCount () {
		return chatData.size ();
	}

	@Override
	public Object getItem (int position) {
		return chatData.get (position).getMessage ();
	}

	@Override
	public long getItemId (int position) {
		return position;
	}

	@Override
	public boolean hasStableIds () {
		return false;
	}

	@Override
	public View getView (int position, View convertView, ViewGroup parent) {

		LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService (Context.LAYOUT_INFLATER_SERVICE);

		View rView;
		if(chatData.get (position).getType ().equals ("user"))
			rView = layoutInflater.inflate (R.layout.message, null);
		else
			rView = layoutInflater.inflate (R.layout.reply, null);

		TextView textView = (TextView) rView.findViewById (R.id.message);

		textView.setText (Html.fromHtml (chatData.get (position).getMessage ()));

		return rView;
	}

	@Override
	public int getItemViewType (int position) {
		return chatData.get (position).getType ().equals ("user") ? 0 : 1;
	}

	@Override
	public int getViewTypeCount () {
		return 2;
	}

	@Override
	public boolean isEmpty () {
		return false;
	}
}