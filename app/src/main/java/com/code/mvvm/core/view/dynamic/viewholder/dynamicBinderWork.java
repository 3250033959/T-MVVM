package com.code.mvvm.core.view.dynamic.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.code.mvvm.App;
import com.code.mvvm.R;
import com.code.mvvm.base.BaseViewHolder;
import com.code.mvvm.core.data.pojo.dynamic.DynamicInfoVo;
import com.code.mvvm.glide.GlideCircleTransform;
import com.code.mvvm.util.DisplayUtil;
import com.trecyclerview.multitype.AbsItemView;

/**
 * @author：zhangtianqiu on 18/7/4 15:35
 */
public class dynamicBinderWork extends AbsItemView<DynamicInfoVo, dynamicBinderWork.ViewHolder> {
    int contentWidth;

    private Context mContext;

    public dynamicBinderWork(Context context) {
        mContext = context;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        contentWidth = DisplayUtil.getScreenWidth(App.Instance()) - DisplayUtil.dp2px(App.Instance(), 15 + 40 + 10 + 30);
        return new ViewHolder(inflater.inflate(R.layout.item_dynamic_work, parent, false));
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DynamicInfoVo item) {
        holder.tvUserName.setText(item.userinfo.sname);
        Glide.with(mContext).load(item.userinfo.avatar)
                .transform(new GlideCircleTransform(mContext))
                .into(holder.ivUserPic);
        // 展示用户信息tag
        holder.mUserTag.removeAllViews();
        if (!TextUtils.isEmpty(item.userinfo.province) && !TextUtils.equals("false", item.userinfo.province)) {
            View view = initTag(item.userinfo.province);
            holder.mUserTag.addView(view);
        }
        if (!TextUtils.isEmpty(item.userinfo.profession)
                && !TextUtils.equals("false", item.userinfo.profession)) {
            View view = initTag(item.userinfo.profession);
            holder.mUserTag.addView(view);
        }
        holder.userType.setText(item.title);
        holder.lookNum.setText(item.tweet_info.hits + "人看过");
        holder.dynamicTitle.setText(item.tweet_info.content);
//
        //作品图片展示
//        if (item.tweet_info.imgs_list.size() > 0 && item.tweet_info.imgs_list.size() == 1) {
        holder.dynamicPic.setVisibility(View.VISIBLE);
//            holder.work_gride.setVisibility(View.GONE);

        int commonwidth = (contentWidth - DisplayUtil.dp2px(mContext, 14 * 2)) * 2 / 3;
        float dv = (float) item.tweet_info.imgs_list.get(0).l.h / (float) item.tweet_info.imgs_list.get(0).l.w;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(commonwidth, (int) (dv * commonwidth));
        holder.dynamicPic.setLayoutParams(params);

        Glide.with(App.Instance()).load(item.tweet_info.imgs_list.get(0).l.url)
                .placeholder(R.color.black_333333).into(holder.dynamicPic);
    }


    public static class ViewHolder extends BaseViewHolder {

        public TextView tvUserName, userType, dynamicTitle, publich_time, lookNum;
        public ImageView ivUserPic, dynamicPic;
        public LinearLayout mUserTag;

        public ViewHolder(View itemView) {
            super(itemView);
            ivUserPic = getView(R.id.iv_user_pic);
            dynamicPic = getView(R.id.iv_dynamic_pic);
            tvUserName = getView(R.id.tv_user_name);
            userType = getView(R.id.user_type);
            dynamicTitle = getView(R.id.tv_dynamic_title);
            publich_time = getView(R.id.tv_publich_time);
            lookNum = getView(R.id.tv_look_num);
            mUserTag = getView(R.id.ll_user_tag);
        }
    }

    private View initTag(String text) {
        View view = LayoutInflater.from(App.Instance()).inflate(R.layout.item_user_tag, null);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_tag.setText(text);
        return view;
    }

}
