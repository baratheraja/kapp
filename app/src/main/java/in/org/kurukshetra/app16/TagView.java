package in.org.kurukshetra.app16;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;

public class TagView extends Button {
	public String tag;
	private int width;
	private int height;
	public TagView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
//		setBackgroundColor(Color.BLUE);
		setSingleLine(true);
	}
	public TagView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}
	public TagView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
	}
	
	public void setCenter(float x, float y) {
		int width = getWidth();
		int height = getHeight();
		Log.i("CloudView", "tag ::: x = " + x + "; y = " + y);
		Log.i("CloudView", "tag ::: width = " + width + "; height = " + height);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setTranslationX(x - (width >> 1));
			setTranslationY(y - (height >> 1));
		} else {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
			params.leftMargin = (int)(x - (width >> 1));
			params.topMargin = (int)(y - (height >> 1));
			params.rightMargin = -width;
			params.bottomMargin = -height;
			requestLayout();
		}
	}
	
}
