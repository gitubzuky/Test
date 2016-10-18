package com.example.administrator.test.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 辣鸡版 RvAdapter
 * @param <VH>
 */
public abstract class BaseHolderAdapter<VH extends BaseHolderAdapter<VH>.ViewHolder> extends BaseAdapter {
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getItemCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		VH holder = null;
		if(convertView == null){
			holder = onCreateViewHolder(parent, getItemViewType(position));
			convertView = holder.rootView;
			convertView.setTag(holder);
		} else {
			holder = (VH) convertView.getTag();
		}
		onBindViewHolder(holder, position);
		return convertView;
	}
	
	/**
	 * 创建ViewHolder<br/>
	 * 注意：不要在这里处理数据绑定
	 * @param parent
	 * @param viewType
	 * @return
	 */
	public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);
	
	/**
	 * ViewHolder绑定数据<br/>
	 * 注意：不要在这里创建ViewHolder
	 * @param holder
	 * @param position
	 */
	public abstract void onBindViewHolder(VH holder, int position);
	
	/**
	 * ListView数目
	 * @return
	 */
	public abstract int getItemCount();
	
	/**
	 * 基础ViewHolder
	 * @author zuky
	 *
	 */
	public abstract class ViewHolder{
        View rootView;
        public ViewHolder(View itemView) {
            rootView = itemView;
        }
    }
}
