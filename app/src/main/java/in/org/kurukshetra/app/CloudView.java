package in.org.kurukshetra.app;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CloudView extends RelativeLayout {
	private static final String TAG = "CloudView";
	private ArrayList<TagView> mTags;
	private ArrayList<Point> mPoints = new ArrayList<Point>();
	private GestureDetector mDetector;
	private int mTagTextSize = 30;
	private Point normalDirection = null;
	public CloudView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public CloudView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public CloudView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mDetector = new GestureDetector(context, mGestureListener);
//		setBackgroundColor(Color.RED);
		setClipChildren(false);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		
		
		mDetector.onTouchEvent(ev);
		super.dispatchTouchEvent(ev);
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			timerStop();
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			timerStart();
			break;
		default:
			break;
		}
		
		return true;
	}
	
	public void setCloudTags(ArrayList<TagView> tags) {
		mTags = tags;
		post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int width = getWidth();
				int height = getHeight();
				for (TagView tag : mTags) {
					tag.setCenter(width / 2.0f, height / 2.0f);
					tag.setTextSize(mTagTextSize);
				}
				
				int len = mTags.size();
				double p1 = Math.PI * (3 - Math.sqrt(5));
				double p2 = 2.0 / len;
				for (int i = 0; i < len; i ++) {
					double y = i * p2 - 1 + (p2 / 2);
					double r = Math.sqrt(1 - (y * y));
					double p3 = i * p1;
					double x = Math.cos(p3) * r;
					double z = Math.sin(p3) * r;
					Point point = new Point(x, y, z);
					mPoints.add(point);
					
					setTagOfPoint(point, i);
				}
				
				Random random = new Random(10);
				int a = random.nextInt();
				int b = random.nextInt();
				Log.i(TAG, "a = " + a + "; b = " + b);
				normalDirection = new Point(a - 5, b - 5, 0);
				timerStart();
				
			}
		});
	}
	
	public void update() {
		int len = mPoints.size();
		for (int i = 0; i < len; i ++) {
			setTagOfPoint(mPoints.get(i), i);
		}
	}
	
	private Timer mTimer;
	private TimerTask mTask;
	private synchronized void timerStart() {
//		if (mTimer != null) {
//			mTimer.cancel();
//			mTask.cancel();
//			mTimer = null;
//			mTask = null;
//		}
//		mTimer = new Timer();
//		mTask = new TimerTask() {
//			@Override
//			public void run() {
//				mHandler.sendEmptyMessage(0);
//			}
//		};
//		mTimer.scheduleAtFixedRate(mTask, new Date(System.currentTimeMillis()), 40);
		isAuto = true;
		mHandler.sendEmptyMessage(0);
	}
	
	private synchronized void timerStop() {
		if (mTimer != null) {
			mTimer.cancel();
			mTask.cancel();
			mTimer = null;
			mTask = null;
		}
		isAuto = false;
	}
	
	private boolean isAuto = true;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			if (what == 0) {
				autoTurnRotation();
				if (isAuto) {
					sendEmptyMessage(0);
				}
			} 
		};
	};
	
	private void autoTurnRotation() {
		int count = mTags.size();
		for (int i = 0; i < count; i ++) {
			updateOfPoint(i, normalDirection, 0.006);
		}
	}
	
	private void setTagOfPoint(Point point, int index) {
		TagView tag = mTags.get(index);
		float x = (float) ((point.x + 1) * getWidth() / 2.0f );
		float y = (float) ((point.y + 1) * getHeight() / 2.0f);
		tag.setCenter(x, y);
		
		Log.i(TAG, "index = " + index + " >>> z = " + point.z);
		double z =  ((point.z + 2) / 3.0);
		tag.setTextSize((float) (z * mTagTextSize));
		tag.setAlpha((float) z);
		if (point.z < 0 ) {
			tag.setClickable(false);
		} else {
			tag.setClickable(true);
		}
	}
	
	private void updateOfPoint(int index, Point direction, double angle) {
		Point point = mPoints.get(index);
		Point rPoint = Matrix.pointMakeRotation(point, direction, angle);
		mPoints.set(index, rPoint);
		setTagOfPoint(rPoint, index);
		rPoint = Matrix.pointMakeRotation(rPoint, direction, angle);
		mPoints.set(index, rPoint);
		setTagOfPoint(rPoint, index);
	}
	
	private Point last;
	private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.OnGestureListener() {
		
		@Override
		public boolean onSingleTapUp(MotionEvent arg0) {
			// TODO Auto-generated method stub
//			for (Tag tag : mTags) {
//				tag.setCenter(arg0.getX(), arg0.getY());
//			}
			return true;
		}
		
		@Override
		public void onShowPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			
			Log.i(TAG, "arg0.getY() = " + arg0.getY() + "; arg1.getY() = " + arg1.getY());
//			for (Tag tag : mTags) {
//				tag.setCenter(arg1.getX(), arg1.getY());
//			}
			Point direction = new Point(last.y - arg1.getY(), arg1.getX() - last.x, 0);
			double distance = Math.sqrt(direction.x * direction.x + direction.y * direction.y);
			double angle = distance / (getWidth() / 2.0);
			int count = mTags.size();
			for (int i = 0; i < count; i ++) {
				updateOfPoint(i, direction, angle);
			}
			last.x = arg1.getX();
			last.y = arg1.getY();
			normalDirection = direction;
			return false;
		}
		
		@Override
		public void onLongPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onFling");
			return false;
		}
		
		@Override
		public boolean onDown(MotionEvent arg0) {
			// TODO Auto-generated method stub
			last = new Point(arg0.getX(), arg0.getY(), 0);
			return false;
		}
	};
}
