package com.example.user.aina_app.common;

import java.util.Calendar;

public interface OnCalendarLongClickListener {

    /**
     * 超出范围越界
     *
     * @param calendar calendar
     */
    void onCalendarLongClickOutOfRange(Calendar calendar);

    /**
     * 日期长按事件
     *
     * @param calendar calendar
     */
    void onCalendarLongClick(Calendar calendar);
}
