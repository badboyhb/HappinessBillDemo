package com.hb.happnissbilldemo.listener;

import java.util.Calendar;
import java.util.List;

/**
 * Created by HB on 2017/6/2.
 */

public interface OnParamSelectedListener {
    void onParamSelected(String[] members, boolean[] memberSelected
            , String[] types, boolean[] typeSelected
            , Calendar start, Calendar end);
}
