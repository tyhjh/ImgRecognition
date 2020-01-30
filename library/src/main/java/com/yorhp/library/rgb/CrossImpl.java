package com.yorhp.library.rgb;

import android.graphics.Bitmap;

import com.yorhp.library.rgb.util.LabColorLike;

/**
 * @author Tyhj
 * @date 2020-01-30
 * @Description: 十字匹配实现模板匹配，只对比十字线坐标上面的点的颜色
 */

public class CrossImpl extends RgbImpl {

    @Override
    protected boolean matchSingle(Bitmap originImg, Bitmap template, int startX, int startY) {
        //Log.d(TAG, "matchSingle，startX：" + startX+"，startY："+startY);
        //比较部分的原图小于了模板返回失败
        if (originImg.getWidth() - startX < template.getWidth() || originImg.getHeight() - startY < template.getHeight()) {
            return false;
        }
        //被检测的点
        int checkedPoint = 0;
        //检测通过的点
        int checkPassPoint = 0;
        int x = template.getWidth() / 2;
        for (int y = 0; y < template.getHeight(); y = y + getAccuracy(template.getHeight())) {
            int templateColor = template.getPixel(x, y);
            //颜色值为0，是透明区域
            if (templateColor != 0) {
                boolean checkPass = LabColorLike.isLike(templateColor, originImg.getPixel(startX + x, startY + y), COLOR_ABERRATION);
                if (checkPass) {
                    checkPassPoint++;
                }
                checkedPoint++;
            }
        }
        //第一轮对比结束
        float similarityOne = (float) checkPassPoint / checkedPoint;
        //Log.d(TAG, "第一轮对比结束，相似度为：" + similarityOne);
        if (similarityOne < similarity) {
            return false;
        }
        checkedPoint = 0;
        checkPassPoint = 0;
        int y = template.getHeight() / 2;
        for (x = 0; x < template.getWidth(); x = x + getAccuracy(template.getWidth())) {
            int templateColor = template.getPixel(x, y);
            //颜色值为0，是透明区域
            if (templateColor != 0) {
                checkedPoint++;
                boolean checkPass = LabColorLike.isLike(templateColor, originImg.getPixel(startX + x, startY + y), COLOR_ABERRATION);
                if (checkPass) {
                    checkPassPoint++;
                }
            }

        }
        //第二轮对比结束
        similarityOne = (float) checkPassPoint / checkedPoint;
        //Log.d(TAG, "第二轮对比结束，相似度为：" + similarityOne);
        if (similarityOne >= similarity) {
            return true;
        }
        return false;
    }
}
