package com.mj.animapp.anim;

import android.annotation.SuppressLint;
import android.graphics.PointF;

import com.nineoldandroids.animation.TypeEvaluator;

@SuppressLint("NewApi")
public class BezierEvaluatorBear implements TypeEvaluator<PointF> {

	/**
	 * 二次方贝塞尔曲线 B(t)=(1-t)^2*P0+2*t*(1-t)*P1+t^2*P2,t∈[0,1] P0,是我们的起点, P2是终点,
	 * P1是途径的点 而t则是我们的一个因子,取值范围是0-1
	 */
	private PointF pointF1;// 中间的点

	public BezierEvaluatorBear(PointF pointF1) {
		this.pointF1 = pointF1;
	}

	/**
	 * 开始和结束的点,这个公式是固定
	 */
	@Override
	public PointF evaluate(float fraction, PointF pointF0, PointF pointF2) {
		PointF pointF = new PointF();
		pointF.x = (float) ((pointF0.x * (Math.pow((1 - fraction), 2))) + 2
				* pointF1.x * fraction * (1 - fraction) + pointF2.x
				* Math.pow(fraction, 2));
		pointF.y = (float) ((pointF0.y * (Math.pow((1 - fraction), 2))) + 2
				* pointF1.y * fraction * (1 - fraction) + pointF2.y
				* Math.pow(fraction, 2));

		return pointF;
	}

}
