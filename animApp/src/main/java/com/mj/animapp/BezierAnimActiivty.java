package com.mj.animapp;

import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mj.animapp.anim.BezierLayoutBear;
import com.nineoldandroids.animation.Keyframe;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengmj on 17-7-10.
 */

public class BezierAnimActiivty extends AppCompatActivity {

    private final static int ANMIBEANCOUNT = 20; //这里配置个数
    @Bind(R.id.llLeftRate)
    LinearLayout llLeftRate;
    @Bind(R.id.llRightRate)
    LinearLayout llRightRate;
    @Bind(R.id.rlRate)
    RelativeLayout rlRate;
    @Bind(R.id.ivBean)
    ImageView ivBean;
    @Bind(R.id.tvMyBean)
    TextView tvMyBean;
    @Bind(R.id.tvBeanNum)
    TextView tvBeanNum;
    @Bind(R.id.rlMyBean)
    RelativeLayout rlMyBean;
    @Bind(R.id.AnmiView)
    BezierLayoutBear mAnmiView;
    @Bind(R.id.ivExceedBean)
    ImageView ivExceedBean;
    @Bind(R.id.rlzhangBean)
    RelativeLayout rlZhangBean;
    @Bind(R.id.ivNoExceedBean)
    ImageView ivNoExceedBean;
    @Bind(R.id.rlDieBean)
    RelativeLayout rlDieBean;
    private int statusBarHeight;// 获取标题的高度


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        ButterKnife.bind(this);


        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // 获取状态栏高度
        statusBarHeight = rect.top;

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.llLeftRate)
    public void llLeftRateOnlick(View view) {
        int[] starRect = new int[2];
        ivBean.getLocationOnScreen(starRect);
        int[] endRect = new int[2];
        rlZhangBean.getLocationOnScreen(endRect);
        int starx = starRect[0];
        int stary = starRect[1] - statusBarHeight;
        int endx = 0;
        int endy = 0;
        for (int i = 0; i < ANMIBEANCOUNT; i++) {
            endx = endRect[0]
                    + (int) (Math.random() * (rlZhangBean.getWidth() + 1));
            endy = (endRect[1] - statusBarHeight)
                    + (int) (Math.random() * (rlZhangBean.getHeight() + 1));
            mAnmiView.addBezierView(new PointF(starx, stary),
                    new PointF(endx - 50, endy + 100), new PointF(endx,
                            endy));
        }
        ObjectAnimator objAnim = rotationAnim(llLeftRate, 1f);
        objAnim.setStartDelay(1000);
        objAnim.start();
    }

    public ObjectAnimator rotationAnim(View view, float shakeFactor) {

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(
                "scaleX",
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.0f),// 1.1f
                Keyframe.ofFloat(.4f, 1.0f), Keyframe.ofFloat(.5f, 1.0f),
                Keyframe.ofFloat(.6f, 1.0f), Keyframe.ofFloat(.7f, 1.0f),
                Keyframe.ofFloat(.8f, 1.0f), Keyframe.ofFloat(.9f, 1.0f),
                Keyframe.ofFloat(1f, 1f));

        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(
                "scaleY", Keyframe.ofFloat(0f, 1f), Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f), Keyframe.ofFloat(.3f, 1.0f),
                Keyframe.ofFloat(.4f, 1.0f), Keyframe.ofFloat(.5f, 1.0f),
                Keyframe.ofFloat(.6f, 1.0f), Keyframe.ofFloat(.7f, 1.0f),
                Keyframe.ofFloat(.8f, 1.0f), Keyframe.ofFloat(.9f, 1.0f),
                Keyframe.ofFloat(1f, 1f));

        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(
                "rotation", Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(.1f, -3f * shakeFactor),
                Keyframe.ofFloat(.2f, -3f * shakeFactor),
                Keyframe.ofFloat(.3f, 3f * shakeFactor),
                Keyframe.ofFloat(.4f, -3f * shakeFactor),
                Keyframe.ofFloat(.5f, 3f * shakeFactor),
                Keyframe.ofFloat(.6f, -3f * shakeFactor),
                Keyframe.ofFloat(.7f, 3f * shakeFactor),
                Keyframe.ofFloat(.8f, -3f * shakeFactor),
                Keyframe.ofFloat(.9f, 3f * shakeFactor),
                Keyframe.ofFloat(1f, 0));

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX,
                pvhScaleY, pvhRotate).setDuration(1000);
    }
}
