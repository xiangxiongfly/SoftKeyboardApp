package com.example.app;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import org.jetbrains.annotations.NotNull;

/**
 * 监听顶层View，判断软键盘是否弹出
 */
public class SoftKeyboardListener {

    private static int lastVisibleHeight = 0;

    public interface OnSoftKeyboardListener {
        void onKeyboardShow(int keyboardHeight, int diff);

        void onKeyboardHide(int keyboardHeight);
    }

    public static void registerListener(@NotNull Activity activity, @NotNull OnSoftKeyboardListener listener) {
        lastVisibleHeight = 0;
        View rootView = activity.getWindow().getDecorView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
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
                            listener.onKeyboardShow(keyboardHeight, visibleHeight);
                            lastVisibleHeight = visibleHeight;
                        }
                    } else if (lastVisibleHeight < visibleHeight) {
                        int keyboardHeight = visibleHeight - lastVisibleHeight;
                        if (keyboardHeight > 200) {
                            listener.onKeyboardHide(keyboardHeight);
                            lastVisibleHeight = visibleHeight;
                        }
                    }
                }
            }
        });
    }
}
