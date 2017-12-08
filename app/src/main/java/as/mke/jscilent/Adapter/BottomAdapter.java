package as.mke.jscilent.Adapter;

import java.util.List;
import android.view.View;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

public class BottomAdapter extends PagerAdapter{
    List<View> views;
    public BottomAdapter(List<View> views) {
        super();
        this.views = views;
    }
    //返回页数
    @Override
    public int getCount() {
        return views.size();
    }
    //当该页被销毁时
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }
    //当该页被创建时
    @Override
    public Object instantiateItem(View container, int position) {
        ( (ViewGroup) container).addView(views.get(position),0);
        return views.get(position);
    }
}


