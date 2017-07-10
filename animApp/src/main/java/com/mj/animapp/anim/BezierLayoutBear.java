package com.mj.animapp.anim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mj.animapp.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.Random;

public class BezierLayoutBear extends RelativeLayout {

    //	// 图片数组
    private Drawable[] loves = new Drawable[3];
    // 图片的宽高
    private int mWidth;
    private int mHeight;
    // 屏幕的宽高
    private int cWidth;
    private int cHeight;
    // 添加到当前view的参数
    private LayoutParams mParams;
    // 随机对象
    private Random mRandom;
    // 渐变动画执行的时间
    private long mPDuration = 500;
    // 贝塞尔执行的时间
    private long mBDuration = 1000;

    private PointF pointStart;//开始
    private PointF pointMid; //中间点
    private PointF pointEnd; //结束点


    // 插补器集 用于随机插补器
    // Interpolator[] interpolators = new Interpolator[3];

    public BezierLayoutBear(Context context) {
        super(context);
        init();
    }

    public BezierLayoutBear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierLayoutBear(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cWidth = MeasureSpec.getSize(widthMeasureSpec);
        cHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    private void init() {
        // 初始化插补器
        // interpolators[0] = new CycleInterpolator(0.3f);
        // interpolators[1] = new CycleInterpolator(1);
        // interpolators[1] = new CycleInterpolator(0.3f);
        // interpolators[2] = new CycleInterpolator(0.3f);

        // interpolators[0] = new AccelerateDecelerateInterpolator(); // 加速减速插补器
        // interpolators[1] = new DecelerateInterpolator(); // 减速插补器
        // interpolators[2] = new AnticipateInterpolator(); // 向前插补器
        // interpolators[3] = new AnticipateOvershootInterpolator(); // 向前向后插补器
        // interpolators[4] = new OvershootInterpolator(); // 超出插补器

        mRandom = new Random();
        loves[0] = getResources().getDrawable(R.drawable.ic_common_anim_bean_0);
        loves[1] = getResources().getDrawable(R.drawable.ic_common_anim_bean_1);
        loves[2] = getResources().getDrawable(R.drawable.ic_common_anim_bean_2);
        // 初始化所添加View的宽高
        Drawable drawable = getResources().getDrawable(R.drawable.ic_common_src_bean);
        mWidth = drawable.getIntrinsicWidth();
        mHeight = drawable.getIntrinsicHeight();
        mParams = new LayoutParams(mWidth, mHeight);
    }

    /**
     * 添加一个View.
     */
    public void addBezierView(PointF pointStart, PointF pointMid, PointF pointEnd) {
        this.pointStart = pointStart;
        this.pointMid = pointMid;
        this.pointEnd = pointEnd;
//        this.pointStart.set(pointStart.x - mWidth / 2, pointStart.y - mHeight / 2);
        this.pointMid.set(pointMid.x + mWidth / 2, pointMid.y - mHeight);
        this.pointEnd.set(pointEnd.x - mWidth / 2, pointEnd.y - mHeight);
        ImageView view = new ImageView(getContext());
        int nextInt = mRandom.nextInt(loves.length - 1);
        view.setImageDrawable(loves[nextInt]);
//        mParams.addRule(CENTER_IN_PARENT);
//        mParams.addRule(ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(mParams);
        addView(view);
        ViewCompat.setX(view, pointStart.x);
        ViewCompat.setY(view, pointStart.y);
        AnimatorSet matorSet = getAnimatorSet(view);
        // 设置插补器.
        matorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        matorSet.start();
    }

    /**
     * 获取一个贝塞尔+平移等动画效果的AnimatorSet;
     *
     * @param view
     * @return
     */
    @SuppressLint("NewApi")
    private AnimatorSet getAnimatorSet(final ImageView view) {
        // 创建动画
        AnimatorSet set = new AnimatorSet();
//        ObjectAnimator trax = ObjectAnimator
//                .ofFloat(view, "scaleX", 0.4f, 0.6f);
//        ObjectAnimator tray = ObjectAnimator
//                .ofFloat(view, "scaleY", 0.4f, 0.6f);
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.4f, 1f);
//
//        // 三个动画一起执行
//        AnimatorSet enterSet = new AnimatorSet();
//        enterSet.setDuration(mPDuration);
//        enterSet.playTogether(trax, tray, alpha);
        // 创建贝塞尔动画
        ValueAnimator bezierAnimator = getBezierAnimator(view);
        // 所有动画一起执行
//        set.playSequentially(enterSet, bezierAnimator);

        set.playSequentially(bezierAnimator);
        set.setTarget(view);

        // 给动画添加一个执行的状态监听,当动画执行结束的时候把view释放掉.
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(view);
                super.onAnimationEnd(animation);
            }

//            @Override
//            public void onAnimationEnd(Animator animation) {
//                removeView(view);
//                super.onAnimationEnd(animation);
//            }
        });
        return set;
    }

    /**
     * 获取贝塞尔动画
     *
     * @param view
     * @return
     */
    private ValueAnimator getBezierAnimator(final ImageView view) {

        // 初始化贝塞尔动画的几个点
//        PointF pointF0 = new PointF((cWidth - mWidth) / 2, cHeight - mHeight);
//        PointF pointF1 = new PointF(cHeight * 1 / 4, cHeight * 1 / 4);
//        PointF pointF3 = new PointF(cWidth / 2, cHeight / 2);

        // 贝塞尔动画的路径由 一个估值器来表示.
        // 获取一个估值器,估值器的点集为pointF1,pointF2;
        // BezierEvaluator bezierEvaluator = new BezierEvaluator(pointF1,
        // pointF2);
        // ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierEvaluator,
        // pointF0, pointF3);

        BezierEvaluatorBear bezierEvaluator = new BezierEvaluatorBear(pointMid);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierEvaluator,
                pointStart, pointEnd);

        valueAnimator.setDuration(mBDuration);

        // 给动画添加一个动画的进度监听;在动画执行的过程中动态的改变view的位置;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                PointF pointF = (PointF) animation.getAnimatedValue();
//                view.setX(pointF.x);
//                view.setY(pointF.y);
                ViewCompat.setX(view, pointF.x);
                ViewCompat.setY(view, pointF.y);

//                Log.i("ddd",
//                        "animation.getAnimatedFraction() is "
//                                + animation.getAnimatedFraction() + " "
//                                + (0.6f + animation.getAnimatedFraction())
//                                + " pointF.x " + pointF.x + " pointF.y "
//                                + pointF.y + " view x is " + view.getScaleX());

                if (animation.getAnimatedFraction() <= 0.6f) {
                    ViewCompat.setScaleX(view,
                            0.6f + animation.getAnimatedFraction());
                    ViewCompat.setScaleY(view,
                            0.6f + animation.getAnimatedFraction());
                } else {
                    ViewCompat.setScaleX(view,
                            1.2f - (animation.getAnimatedFraction() - 0.6f));
                    ViewCompat.setScaleY(view,
                            1.2f - (animation.getAnimatedFraction() - 0.6f));
                }
                // 设置view的透明度,达到动画执行过程view逐渐透明效果;
//                view.setAlpha(1 - animation.getAnimatedFraction());
            }
        });

        return valueAnimator;
    }

}
