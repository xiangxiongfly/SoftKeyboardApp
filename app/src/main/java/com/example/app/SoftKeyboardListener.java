package com.example.app;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import org.jetbrains.annotations.NotNull;

/**
 * 监听顶层View，判断软键盘是否弹出
 */
public class SoftKeyboardListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private static SoftKeyboardListener mSoftKeyboardListener;
    private boolean isShowKeyboard;
    private View decorView;
    private OnSoftKeyboardListener mListener;

    public static SoftKeyboardListener with(@NotNull Activity activity) {
        if (mSoftKeyboardListener == null) {
            mSoftKeyboardListener = new SoftKeyboardListener(activity);
        }
        return mSoftKeyboardListener;
    }

    public SoftKeyboardListener(Activity activity) {
        isShowKeyboard = false;
        decorView = activity.getWindow().getDecorView();
    }

    @Override
    public void onGlobalLayout() {
        if (mListener == null) return;
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        int visibleHeight = rect.height();
        int diffHeight = decorView.getHeight() - visibleHeight;
        if (!isShowKeyboard && diffHeight > decorView.getRootView().getHeight() / 4) {
            isShowKeyboard = true;
            mListener.onKeyboardShow(diffHeight);
        } else if (isShowKeyboard && diffHeight < decorView.getRootView().getHeight() / 4) {
            isShowKeyboard = false;
            mListener.onKeyboardHide();
        }
    }

    /**
     * 注册监听
     */
    public void registerListener(@NotNull OnSoftKeyboardListener listener) {
        mListener = listener;
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        Log.e("TAG", "register " + this.hashCode());
    }

    /**
     * 取消监听
     */
    public void unregisterListener() {
        if (mSoftKeyboardListener != null) {
            Log.e("TAG", "unregister " + this.hashCode());
            decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            mListener = null;
            decorView = null;
            isShowKeyboard = false;
            mSoftKeyboardListener = null;
        }
    }

    public interface OnSoftKeyboardListener {
        /**
         * 软键盘弹出
         *
         * @param keyboardHeight 软键盘高度
         */
        void onKeyboardShow(int keyboardHeight);

        /**
         * 软键盘收起
         */
        void onKeyboardHide();
    }
}
