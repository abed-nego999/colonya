package sngular.com.atmsmap.presentation.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import sngular.com.atmsmap.R;

/**
 * Created by alberto.hernandez on 29/04/2016
 */
public class OpenSansTextView extends TextView {

    public OpenSansTextView(Context context) {
        super(context);
    }

    public OpenSansTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public OpenSansTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.OpenSansTextView);
        String customFont = a.getString(R.styleable.OpenSansTextView_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    private boolean setCustomFont(Context ctx, String asset) {
        setMaxLines(2);
        setEllipsize(TextUtils.TruncateAt.END);
        int style = -1;
        if (getTypeface() != null) {
            style = getTypeface().getStyle();
        }
        if (style == Typeface.BOLD) {
            Typeface mBFont = Typeface.createFromAsset(ctx.getAssets(),
                    "fonts/OpenSansBold.ttf");
            setTypeface(mBFont);
        } else if (style == Typeface.ITALIC) {
            Typeface mSbFont = Typeface.createFromAsset(ctx.getAssets(),
                    "fonts/OpenSansSemibold.ttf");
            setTypeface(mSbFont);
        } else {
            Typeface mFont = Typeface.createFromAsset(ctx.getAssets(),
                    "fonts/OpenSansRegular.ttf");
            setTypeface(mFont);
        }
        return true;
    }
}
