package in.org.kurukshetra.app16;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ChatHead extends Service {

	private WindowManager windowManager;
	private RelativeLayout removeView;
	private View chatRL, chatDialogView;
	private ListView chatListView;
	private ChatAdapter chatAdapter;
	private JSONFile jsonFile;
	private ImageView removeImage, chatHeadView;
	private ArrayList<Message> arrayList;
	private Point initialCoordinates = new Point(0, 0);
	private Point initialMargin = new Point(0, 0);
	private Point windowSize = new Point (0 ,0);

	@Override
	public void onCreate () {
		super.onCreate ();
	}

	public void onBackPressed() {

	}

	private void handleStart () {

		windowManager = (WindowManager) getSystemService (WINDOW_SERVICE);

		LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

		removeView = (RelativeLayout) inflater.inflate (R.layout.remove, null);
		WindowManager.LayoutParams paramRemove = new WindowManager.LayoutParams (
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
				WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);
		paramRemove.gravity = Gravity.TOP | Gravity.START;

		removeView.setVisibility (View.GONE);
		removeImage = (ImageView) removeView.findViewById (R.id.remove_img);
		windowManager.addView (removeView, paramRemove);

		chatDialogView = inflater.inflate (R.layout.chat, null);

		//View tempView = inflater.inflate (R.layout.chathead, null);

		//chatHeadView = (ImageView) tempView.findViewById (R.id.chathead_img);


		chatDialogView.setOnKeyListener (new View.OnKeyListener () {
			@Override
			public boolean onKey (View v, int keyCode, KeyEvent event) {
				if (event.getKeyCode () == KeyEvent.KEYCODE_BACK) {
					chatDialogView.setVisibility (View.GONE);
				}
				//keyCode
				return false;
			}
		});

		chatHeadView = new ImageView (this);
		chatHeadView.setImageResource (R.drawable.cyclotron);

		arrayList = new ArrayList<> ();

		jsonFile = new JSONFile (getAssets ());

		String[] temp = jsonFile.getStringArray ("help");
		StringBuilder help = new StringBuilder();
		for (String i : temp) help.append ("\n").append (i);

		arrayList.add (new Message ("bot", help.toString ()));

		chatAdapter = new ChatAdapter (this, arrayList);

		chatListView = (ListView) chatDialogView.findViewById (R.id.chat);
		chatListView.setAdapter(chatAdapter);
		//chatRL = chatDialogView.findViewById (R.id.editor);

		windowManager.getDefaultDisplay().getSize(windowSize);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
				WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.START;
		params.x = 0;
		params.y = 100;

		windowManager.addView(chatHeadView, params);

		WindowManager.LayoutParams nparams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAGS_CHANGED,
				PixelFormat.TRANSLUCENT);
		nparams.gravity = Gravity.CENTER_VERTICAL | Gravity.BOTTOM;

		chatDialogView.setVisibility (View.GONE);
		windowManager.addView (chatDialogView, nparams);

		chatHeadView.setOnTouchListener (new View.OnTouchListener () {
			long startTime = 0, endTime = 0;
			boolean isLongclick = false, inBounded = false;
			Point removeImageAttr = new Point(0, 0);

			Handler handler_longClick = new Handler ();
			Runnable runnable_longClick = new Runnable () {

				@Override
				public void run () {
					isLongclick = true;
					removeView.setVisibility (View.VISIBLE);
					chathead_longclick ();
				}
			};

			@Override
			public boolean onTouch (View v, MotionEvent event) {
				WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) chatHeadView.getLayoutParams ();

				Point tempCoordinates = new Point ((int) event.getRawX (), (int) event.getRawY ());

				Point destinationCoordinates = new Point(0 ,0);

				switch (event.getAction ()) {
					case MotionEvent.ACTION_DOWN:
						startTime = System.currentTimeMillis ();
						handler_longClick.postDelayed (runnable_longClick, 600);

						removeImageAttr.set (removeImage.getLayoutParams ().width, removeImage.getLayoutParams ().height);

						initialCoordinates = tempCoordinates;

						initialMargin.set (layoutParams.x, layoutParams.y);

						break;
					case MotionEvent.ACTION_MOVE:
						Point moveDifference = new Point(tempCoordinates.x - initialCoordinates.x, tempCoordinates.y - initialCoordinates.y);

						destinationCoordinates.set (initialMargin.x + moveDifference.x, initialMargin.y + moveDifference.y);

						if (isLongclick) {
							int x_bound_left = (windowSize.x - removeView.getWidth ()) / 2 - 250;
							int x_bound_right = (windowSize.x + removeView.getWidth ()) / 2 + 100;

							int y_bound_top = windowSize.y - (removeView.getHeight () + getStatusBarHeight ()) - 200;

							if ((destinationCoordinates.x >= x_bound_left && destinationCoordinates.x <= x_bound_right) && destinationCoordinates.y >= y_bound_top) {
								inBounded = true;

								layoutParams.x = (windowSize.x - chatHeadView.getWidth ()) / 2;
								layoutParams.y = windowSize.y - (removeView.getHeight () + getStatusBarHeight ()) + 70;

								if (removeImage.getLayoutParams ().height == removeImageAttr.y) {

									removeImage.getLayoutParams ().width = (int) (removeImageAttr.x * 1.5);
									removeImage.getLayoutParams ().height = (int) (removeImageAttr.y * 1.5);

									WindowManager.LayoutParams param_remove = (WindowManager.LayoutParams) removeView.getLayoutParams ();
									param_remove.x = (int) ((windowSize.x - (removeImageAttr.y * 1.5)) / 2);
									param_remove.y = (int) (windowSize.y - ((removeImageAttr.x * 1.5) + getStatusBarHeight ()));

									windowManager.updateViewLayout (removeView, param_remove);
								}

								windowManager.updateViewLayout (chatHeadView, layoutParams);
								break;
							}
							else {
								inBounded = false;

								removeImage.getLayoutParams ().width = removeImageAttr.x;
								removeImage.getLayoutParams ().height = removeImageAttr.y;

								WindowManager.LayoutParams param_remove = (WindowManager.LayoutParams) removeView.getLayoutParams ();
								param_remove.x = (windowSize.x - removeView.getWidth ()) / 2;
								param_remove.y = windowSize.y - (removeView.getHeight () + getStatusBarHeight ());

								windowManager.updateViewLayout (removeView, param_remove);
							}
						}

						layoutParams.x = destinationCoordinates.x;
						layoutParams.y = destinationCoordinates.y;

						windowManager.updateViewLayout (chatHeadView, layoutParams);
						break;
					case MotionEvent.ACTION_UP:
						isLongclick = false;
						removeView.setVisibility (View.GONE);

						removeImage.getLayoutParams ().width = removeImageAttr.x;
						removeImage.getLayoutParams ().height = removeImageAttr.y;

						handler_longClick.removeCallbacks (runnable_longClick);

						if (inBounded) {
							stopService (new Intent (ChatHead.this, ChatHead.class));
							inBounded = false;
							break;
						}

						Point diff = new Point (tempCoordinates.x - initialCoordinates.x, tempCoordinates.y - initialCoordinates.y);

						if (diff.x < 5 && diff.y < 5) {
							endTime = System.currentTimeMillis ();
							if ((endTime - startTime) < 500) chatheadClick ();
						}

						destinationCoordinates.set (initialMargin.x + diff.x, initialMargin.y + diff.y);

						int BarHeight = getStatusBarHeight ();
						if (destinationCoordinates.y < 0)
							destinationCoordinates.y = 0;
						else if (destinationCoordinates.y + (chatHeadView.getHeight () + BarHeight) > windowSize.y)
							destinationCoordinates.y = windowSize.y - (chatHeadView.getHeight () + BarHeight);

						layoutParams.y = destinationCoordinates.y;

						inBounded = false;
						resetPosition (destinationCoordinates);

						break;
					default:
						break;
				}
				return true;
			}
		});
	}

	@Override
	public void onConfigurationChanged (Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		windowManager.getDefaultDisplay().getSize(windowSize);

		WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) chatHeadView.getLayoutParams();

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

			if(layoutParams.y + (chatHeadView.getHeight() + getStatusBarHeight()) > windowSize.y){
				layoutParams.y = windowSize.y - (chatHeadView.getHeight() + getStatusBarHeight());
				windowManager.updateViewLayout(chatHeadView, layoutParams);
			}

			if(layoutParams.x != 0 && layoutParams.x < windowSize.x)
				resetPosition (windowSize);

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT && layoutParams.x > windowSize.x)
			resetPosition (windowSize);
	}

	private void resetPosition (Point currentCoordinate) {
		if (currentCoordinate.x + (chatHeadView.getWidth () / 2) <= windowSize.x / 2)
			moveToLeft(currentCoordinate);
		else
			moveToRight (currentCoordinate);
	}

	private void moveToLeft (final Point currentCoordinate){

		new CountDownTimer (500, 5) {
			WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) chatHeadView.getLayoutParams();

			public void onTick(long t) {
				long step = (500 - t)/5;
				mParams.x = (int)(double) bounceValue (step, currentCoordinate.x);
				windowManager.updateViewLayout (chatHeadView, mParams);
			}

			public void onFinish() {
				mParams.x = 0;
				windowManager.updateViewLayout(chatHeadView, mParams);
			}
		}.start();
	}

	private  void moveToRight (final Point currentCoordinate){

		new CountDownTimer(500, 5) {

			WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) chatHeadView.getLayoutParams();

			public void onTick(long t) {
				long step = (500 - t) / 5;
				mParams.x = windowSize.x + (int)(double) bounceValue (step, currentCoordinate.x) - chatHeadView.getWidth ();
				windowManager.updateViewLayout (chatHeadView, mParams);
			}

			public void onFinish() {
				mParams.x = windowSize.x - chatHeadView.getWidth();
				windowManager.updateViewLayout(chatHeadView, mParams);
			}
		}.start();
	}

	private double bounceValue (long step, long scale) {
		return scale * Math.exp(-0.055 * step) * Math.cos(0.08 * step);
	}

	private int getStatusBarHeight () {
		return (int) Math.ceil(25 * getApplicationContext().getResources().getDisplayMetrics().density);
	}

	private void chatheadClick (){

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.TOP | Gravity.START;

		params.x = 0;
		params.y = 100;

		if (chatDialogView != null)
			if(chatDialogView.isShown ())
				chatDialogView.setVisibility (View.GONE);
			else
				chatDialogView.setVisibility (View.VISIBLE);

	/*	if(chatListView != null && !chatListView.isShown () ) {
			chatListView.setVisibility (View.VISIBLE);
			chatRL.setVisibility (View.VISIBLE);
		} else {
			if (chatListView != null) {
				chatListView.setVisibility (View.INVISIBLE);
			}
			chatRL.setVisibility (View.INVISIBLE);
		}
	*/
		windowManager.updateViewLayout (chatHeadView, params);

	}

	private void chathead_longclick(){
		WindowManager.LayoutParams param_remove = (WindowManager.LayoutParams) removeView.getLayoutParams();
		int x_cord_remove = (windowSize.x - removeView.getWidth()) / 2;
		int y_cord_remove = windowSize.y - (removeView.getHeight() + getStatusBarHeight() );

		param_remove.x = x_cord_remove;
		param_remove.y = y_cord_remove;

		windowManager.updateViewLayout(removeView, param_remove);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if(startId == Service.START_STICKY) {
			handleStart();
			return super.onStartCommand(intent, flags, startId);
		}
		return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy ();

		if(chatHeadView != null)
			windowManager.removeView (chatHeadView);

		if(chatDialogView != null)
			windowManager.removeView (chatDialogView);

		if(removeView != null)
			windowManager.removeView (removeView);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
