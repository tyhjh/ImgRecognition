package com.yorhp.library.rgb;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyhj
 * @date 2020-01-30
 * @Description: 专用于蚂蚁森林的识别
 */

public class CrossAntForestImpl extends CrossImpl {

    @Override
    public List<Rect> matching(Bitmap originImg, Bitmap template) {
        List<Rect> matchRectList = new ArrayList<>();
        if (originImg != null && template != null) {
            int width = originImg.getWidth();
            int height = originImg.getHeight();
            //模板必须不大于原图
            if (width >= template.getWidth() && height >= template.getHeight()) {
                for (int y = 0; y < height - 1; y++) {
                    //x坐标起点重新设置
                    for (int x = 950; x < width - 1; x++) {
                        boolean isMatch = matchSingle(originImg, template, x, y);
                        if (isMatch) {
                            matchRectList.add(new Rect(x, y, x + template.getWidth(), y + template.getHeight()));
                            x = x + template.getWidth();
                            y = y + template.getHeight();
                        }
                    }
                }
            }
        }
        return matchRectList;
    }

}
