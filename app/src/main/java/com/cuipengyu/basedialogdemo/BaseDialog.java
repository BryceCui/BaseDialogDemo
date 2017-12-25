package com.cuipengyu.basedialogdemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by 崔鹏宇 on 2017/12/22.
 */

public class BaseDialog extends Dialog implements DialogControl {
    private TextView tvTitle;//标题
    private ProgressBar pbLoading;//进度条
    private TextView btnPositive;//确定
    private TextView btnNegative;//取消
    private TextView tvMsg;//取消
    private FrameLayout flCustom;//中间内容
    private static BaseDialog mBaseDialog;

    /**
     * 接受显示的内容
     */
    private String mTitle;
    private String mMessage;
    private String positiveText;
    private String negativeText;
    /**
     * 进度条和按钮是否显示
     */
    private boolean isProgressBarShow = false;
    private boolean isNegativeBtnShow = true;
    private View mView;

    public BaseDialog(@NonNull Context context) {
        //绑定自定义主题
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base);
        flCustom = findViewById(R.id.fl_dialog_content);
        tvTitle = findViewById(R.id.tv_title);
        pbLoading = findViewById(R.id.pb_loading);
        btnPositive = findViewById(R.id.btn_positive);
        btnNegative = findViewById(R.id.btn_negative);
        tvMsg = findViewById(R.id.tv_msg);
    }

    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(BaseDialog baseDialog) {
        if (!TextUtils.isEmpty(baseDialog.mTitle)) {
            baseDialog.tvTitle.setText(baseDialog.mTitle);
        }
        if (baseDialog.mView != null) {
            baseDialog.flCustom.addView(baseDialog.mView);
            baseDialog.pbLoading.setVisibility(View.GONE);
            baseDialog.tvMsg.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(baseDialog.mMessage)) {
                baseDialog.tvMsg.setText(baseDialog.mMessage);
                baseDialog.tvMsg.setVisibility(View.VISIBLE);
            }
            if (isProgressBarShow) {
                baseDialog.pbLoading.setVisibility(View.VISIBLE);
                baseDialog.btnPositive.setVisibility(View.GONE);
                baseDialog.btnNegative.setVisibility(View.GONE);
            }
        }
        if (!baseDialog.isNegativeBtnShow) {
            baseDialog.btnNegative.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) baseDialog.btnPositive
                    .getLayoutParams();
            layoutParams.setMargins(150, layoutParams.topMargin, 150, layoutParams.bottomMargin);
            baseDialog.btnPositive.setLayoutParams(layoutParams);
        } else {
            baseDialog.btnNegative.setOnClickListener(baseDialog.onNegativeListener);
            if (!TextUtils.isEmpty(baseDialog.negativeText)) {
                baseDialog.btnNegative.setText(baseDialog.negativeText);
            }
        }
        baseDialog.btnPositive.setOnClickListener(baseDialog.onPositiveListener);
        if (!TextUtils.isEmpty(baseDialog.positiveText)) {
            baseDialog.btnPositive.setText(baseDialog.positiveText);
        }

    }

    @Override
    public void DialogShow() {

    }

    @Override
    public void DialogDismiss() {

    }

    public static class Builder {

        public Builder(Context context) {
            mBaseDialog = new BaseDialog(context);
        }


        //设置标题
        public Builder setTitle(String title) {
            mBaseDialog.mTitle = title;
            return this;
        }

        //设置内容
        public Builder setMessage(String msg) {
            mBaseDialog.mMessage = msg;
            return this;
        }


        //更改确认按钮的文字
        public Builder setPositiveTextView(String positiveTextView) {
            mBaseDialog.positiveText = positiveTextView;
            return this;
        }


        //更改取消按钮的文字
        public Builder setNegativeTextView(String negativeTextView) {
            mBaseDialog.negativeText = negativeTextView;
            return this;
        }

        //设置进度条是否显示，默认不显示
        public Builder setProgressBarShow(boolean isProgressBarShow) {
            mBaseDialog.isProgressBarShow = isProgressBarShow;
            return this;
        }

        //是否显示默认取消按钮
        public Builder setNegativeBtnShow(boolean isNegativeBtnShow) {
            mBaseDialog.isNegativeBtnShow = isNegativeBtnShow;
            return this;
        }

        //设置自定义布局
        public Builder setDialogView(View dialogView) {
            mBaseDialog.mView = dialogView;
            return this;
        }

        //设置该对话框是否可以取消
        public Builder setCancelable(boolean isCan) {
            mBaseDialog.setCancelable(isCan);
            return this;
        }

        //设置对话框被cancel对应的回调接口，cancel()方法被调用时才会回调该接口
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mBaseDialog.setOnCancelListener(onCancelListener);
            return this;
        }

        //设置对话框消失对应的回调接口，一切对话框消失都会回调该接口
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            mBaseDialog.setOnDismissListener(onDismissListener);
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public BaseDialog create() {
            return mBaseDialog;
        }

    }

    //设置确定按钮回调
    public void setPositiveButton(View.OnClickListener positiveButton) {
        mBaseDialog.onPositiveListener = positiveButton;
    }

    //设置取消按钮回调
    public void setNegativeButton(View.OnClickListener negativeButton) {
        mBaseDialog.onNegativeListener = negativeButton;
    }

    private View.OnClickListener onDefaultClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
        }
    };
    private View.OnClickListener onPositiveListener = onDefaultClickListener;
    private View.OnClickListener onNegativeListener = onDefaultClickListener;


}
