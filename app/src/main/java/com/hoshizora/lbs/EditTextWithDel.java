package com.hoshizora.lbs;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatEditText;

public class EditTextWithDel extends AppCompatEditText {

    private Drawable imgInable;
    private Context myContext;

    private void setDrawable() {
        if (length() < 1)
            setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        else
            setCompoundDrawablesWithIntrinsicBounds(null,null,imgInable,null);
    }

    private void init() {
        imgInable = myContext.getResources().getDrawable(R.drawable.close);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
    }

    public EditTextWithDel(Context context) {
        super(context);
        myContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        myContext = context;
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgInable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX,eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }
}
