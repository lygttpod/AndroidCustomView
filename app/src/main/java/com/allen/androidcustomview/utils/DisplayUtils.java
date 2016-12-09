package com.allen.androidcustomview.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 与屏幕信息有关的类，包括屏幕的长宽、分辨率、长度换算
 * 
 */
public class DisplayUtils {


	/**
	 * 获取屏幕分辨率
	 * @param context
	 * @return
	 */
	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}

	/** 获取屏幕宽度 */
	public static int getDisplayWidth(Context context) {
		if (context != null) {
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			int w_screen = dm.widthPixels;
			// int h_screen = dm.heightPixels;
			return w_screen;
		}
		return 720;
	}

	/** 获取屏幕高度 */
	public static int getDisplayHight(Context context) {
		if (context != null) {
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			// int w_screen = dm.widthPixels;
			int h_screen = dm.heightPixels;
			return h_screen;
		}
		return 1280;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
