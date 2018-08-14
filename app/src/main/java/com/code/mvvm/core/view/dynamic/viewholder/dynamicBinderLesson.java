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
public class dynamicBinderLesson extends AbsItemView<DynamicInfoVo, dynamicBinderLesson.ViewHolder> {
    int contentWidth;

    private Context mContext;

    public dynamicBinderLesson(Context context) {
        mContext = context;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        contentWidth = DisplayUtil.getScreenWidth(App.Instance()) - DisplayUtil.dp2px(App.Instance(), 15 + 40 + 10 + 30);

        return new ViewHolder(inflater.inflate(R.layout.item_dynamic_lesson, parent, false));
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
        holder.lookNum.setText(item.course_info.hits + "人看过");
        holder.dynamicTitle.setText(item.course_info.title);
        //课程时间
        if (null != item.course_info.video_legth && !TextUtils.isEmpty(item.course_info.video_legth)) {
            int minute = (int) (Long.valueOf(item.course_info.video_legth) / 60);
            int second = (int) (Long.valueOf(item.course_info.video_legth) % 60);
            holder.video_time.setText(minute + "" + "分" + second + "" + "秒");
        }

        //课程封面展示
        int commonwidth = contentWidth - DisplayUtil.dp2px(App.Instance(), 14 * 2);
        double dv = 0.56;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.img_content_layout.getLayoutParams();
        //                    params.width = commonwidth;
        params.height = (int) (dv * commonwidth);
        if (item.course_info.thumb_url != null) {
            Glide.with(mContext).load(item.course_info.thumb_url)
                    .placeholder(R.color.black_333333)
                    .into(holder.video_img);
        } else {
            holder.video_img.setBackgroundResource(R.drawable.collect_morentupian);
        }
    }


    public static class ViewHolder extends BaseViewHolder {

        public ImageView video_img;
        public ImageView fufei_tag, yifu_tag, zhibo_state;
        public LinearLayout tuanggou_tag_ly;
        public TextView tv_shengyu_time;
        public TextView video_time;

        public TextView tvUserName, userType, dynamicTitle, publich_time, lookNum;
        public ImageView ivUserPic, dynamicPic;
        public LinearLayout mUserTag;

        public View img_content_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            video_img = (ImageView) itemView.findViewById(R.id.video_img);
            img_content_layout = itemView.findViewById(R.id.img_ly);
            tv_shengyu_time = (TextView) itemView.findViewById(R.id.tv_shengyu_time);
            video_time = (TextView) itemView.findViewById(R.id.video_time);
            fufei_tag = (ImageView) itemView.findViewById(R.id.fufei_tag);
            yifu_tag = (ImageView) itemView.findViewById(R.id.yifu_tag);
            zhibo_state = (ImageView) itemView.findViewById(R.id.zhibo_state);
            tuanggou_tag_ly = (LinearLayout) itemView.findViewById(R.id.tuanggou_tag_ly);

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
