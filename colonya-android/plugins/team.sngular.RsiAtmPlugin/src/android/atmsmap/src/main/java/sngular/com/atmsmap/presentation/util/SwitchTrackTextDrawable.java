package sngular.com.atmsmap.presentation.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import sngular.com.atmsmap.R;

/**
 * Created by alberto.hernandez on 26/04/2016.
 */
public class SwitchTrackTextDrawable extends Drawable {

    private final Context mContext;

    private final String mLeftText;

    private final String mRightText;

    private final Paint mTextPaint;

    public SwitchTrackTextDrawable(@NonNull Context context,
                                   @StringRes int leftTextId,
                                   @StringRes int rightTextId) {
        mContext = context;

        mTextPaint = createTextPaint();
        mLeftText = context.getString(leftTextId);
        mRightText = context.getString(rightTextId);
    }

    private Paint createTextPaint() {
        Paint textPaint = new Paint();
        textPaint.setColor(mContext.getResources().getColor(R.color.app_switch_dark));
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(dpConverter(15));
        return textPaint;
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect textBounds = new Rect();
        mTextPaint.getTextBounds(mRightText, 0, mRightText.length(), textBounds);

        final int heightBaseline = canvas.getClipBounds().height() / 2 + textBounds.height() / 2;
        final int widthQuarter = canvas.getClipBounds().width() / 4;
        canvas.drawText(mLeftText, 0, mLeftText.length(),
                widthQuarter, heightBaseline,
                mTextPaint);
        canvas.drawText(mRightText, 0, mRightText.length(),
                widthQuarter * 3, heightBaseline,
                mTextPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        //Empty Constructor
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        //Empty Constructor
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    public int dpConverter(int dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
