
package com.example.administrator.test.model;

/**
 * Created by Administrator on 2017/7/20.
 */

public class WvBean {
    int tag;// 0--怀孕期; 1--育儿期
    int age;
    int month;
    int week;
    int day;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    // 这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        // 这里还可以判断文字超长截断再提供显示
        if (tag == 0) {// 备孕
            return "周期第" + String.valueOf(day) + "天";
        } else if (tag == 1) {// 怀孕
            return "孕" + String.valueOf(week) + "周" + String.valueOf(day) + "天";
        } else if (tag == 2) {// 育儿
            if (age <= 0) {
                return String.valueOf(month) + "个月" + String.valueOf(day) + "天";
            } else {
                return String.valueOf(age) + "岁" + String.valueOf(month) + "个月第"
                        + String.valueOf(day) + "天";
            }
        }
        return "";
    }
}
