
package com.example.administrator.test.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by LinPH on 2015-6-17下午12:01:22.
 * <p>Description: 可以嵌套在ScrollView里面的GridView</p>
 * <p>Tips: ListView同理</p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */
public class NOScrollGridView extends GridView {

    public NOScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NOScrollGridView(Context context) {
        super(context);
    }

    public NOScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 该自定义控件只是重写了GridView的onMeasure方法，使其不会出现滚动条，ScrollView嵌套ListView也是同样的道理，不再赘述。
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
