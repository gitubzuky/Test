
package com.example.administrator.test.model.widgettest;

/**
 * Created by Zuky on 2017/6/26.
 * <p>Description: </p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */

public class MDataBean {
    private boolean isMonthDay;
    private boolean isSafeDay;
    private boolean isMPeriod;
    private boolean isOvipositPeriod;
    private boolean isOvipositDay;
    private int day;

    public boolean isMonthDay() {
        return isMonthDay;
    }

    public void setMonthDay(boolean monthDay) {
        isMonthDay = monthDay;
    }

    public boolean isSafeDay() {
        return isSafeDay;
    }

    public void setSafeDay(boolean safeDay) {
        isSafeDay = safeDay;
    }

    public boolean isMPeriod() {
        return isMPeriod;
    }

    public void setMPeriod(boolean MPeriod) {
        isMPeriod = MPeriod;
    }

    public boolean isOvipositPeriod() {
        return isOvipositPeriod;
    }

    public void setOvipositPeriod(boolean ovipositPeriod) {
        isOvipositPeriod = ovipositPeriod;
    }

    public boolean isOvipositDay() {
        return isOvipositDay;
    }

    public void setOvipositDay(boolean ovipositDay) {
        isOvipositDay = ovipositDay;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
