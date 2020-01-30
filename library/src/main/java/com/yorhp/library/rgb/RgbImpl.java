package com.yorhp.library.rgb;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.yorhp.library.ITemplateMatching;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyhj
 * @date 2020-01-30
 * @Description: rgb模板匹配抽象类
 */
public abstract class RgbImpl implements ITemplateMatching {

    public final static String TAG = "RgbImpl";

    /**
     * 颜色误差
     */
    public final static int COLOR_ABERRATION = 5;

    /**
     * 相似度，所有对比的点的符合度
     */
    protected float similarity = 1;

    /**
     * 准确度，对比的点的间隔的比例
     */
    protected float accuracy = 1;


    @Override
    public List<Rect> matching(Bitmap originImg, Bitmap template) {
        List<Rect> matchRectList = new ArrayList<>();
        if (originImg != null && template != null) {
            int width = originImg.getWidth();
            int height = originImg.getHeight();
            //模板必须不大于原图
            if (width >= template.getWidth() && height >= template.getHeight()) {
                for (int y = 0; y < height - 1; y++) {
                    for (int x = 0; x < width - 1; x++) {
                        boolean isMatch = matchSingle(originImg, template, x, y);
                        if (isMatch) {
                            matchRectList.add(new Rect(x, y, x + template.getWidth(), y + template.getHeight()));
                        }
                    }
                }
            }
        }
        return matchRectList;
    }


    /**
     * 单个区域比较
     *
     * @param originImg
     * @param template
     * @param startX
     * @param startY
     * @return
     */
    protected abstract boolean matchSingle(Bitmap originImg, Bitmap template, int startX, int startY);


    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }


    /**
     * 获取对比间隔
     *
     * @param width
     * @return
     */
    protected int getAccuracy(int width) {
        int interval = (int) (width * (1 - accuracy));
        if (interval < 1) {
            interval = 1;
        }
        return interval;
    }


}
