package com.example.app;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import org.jetbrains.annotations.NotNull;

/**
 * 监听顶层View，判断软键盘是否弹出
 */
public class SoftKeyboardListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private static SoftKeyboardListener mSoftKeyboardListener;
    private int lastVisibleHeight;
    private View rootView;
    private OnSoftKeyboardListener mListener;

    public static SoftKeyboardListener with(@NotNull Activity activity) {
        if (mSoftKeyboardListener == null) {
            mSoftKeyboardListener = new SoftKeyboardListener(activity);
        }
        return mSoftKeyboardListener;
    }

    public SoftKeyboardListener(Activity activity) {
        lastVisibleHeight = 0;
        rootView = activity.getWindow().getDecorView();
    }

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        int visibleHeight = rect.height();
        if (lastVisibleHeight == 0) {
            lastVisibleHeight = visibleHeight;
        } else {
            if (lastVisibleHeight > visibleHeight) {
                int keyboardHeight = lastVisibleHeight - visibleHeight;
                if (keyboardHeight > 200) {
                    mListener.onKeyboardShow(keyboardHeight, visibleHeight);
                    lastVisibleHeight = visibleHeight;
                }
            } else if (lastVisibleHeight < visibleHeight) {
                int keyboardHeight = visibleHeight - lastVisibleHeight;
                if (keyboardHeight > 200) {
                    mListener.onKeyboardHide();
                    lastVisibleHeight = visibleHeight;
                }
            }
        }
    }

    /**
     * 注册监听
     */
    public void registerListener(@NotNull OnSoftKeyboardListener listener) {
        if (mSoftKeyboardListener != null) {
            mListener = listener;
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            Log.e("TAG", "r " + this.hashCode());
        }
    }

    /**
     * 取消监听
     */
    public void unregisterListener() {
        if (mSoftKeyboardListener != null) {
            Log.e("TAG", "u " + this.hashCode());
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            mListener = null;
            rootView = null;
            lastVisibleHeight = 0;
            mSoftKeyboardListener = null;
        }
    }

    public interface OnSoftKeyboardListener {
        /**
         * 软键盘弹出
         *
         * @param keyboardHeight 软键盘高度
         * @param diff           内容去高度
         */
        void onKeyboardShow(int keyboardHeight, int diff);

        /**
         * 软键盘收起
         */
        void onKeyboardHide();
    }
}
