package com.yorhp.library;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.List;

/**
 * @author Tyhj
 * @date 2020-01-30
 * @Description: 模板匹配抽象类，用于匹配模板图象在被识别
 * 图象中的位置
 */

public interface ITemplateMatching {

    /**
     * 匹配模板图象在被识别图象中的位置
     *
     * @param originImg
     * @param template
     * @return 不为null
     */
    List<Rect> matching(Bitmap originImg, Bitmap template);

}
