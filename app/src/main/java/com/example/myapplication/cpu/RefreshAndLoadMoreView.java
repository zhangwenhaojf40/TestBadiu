package com.example.myapplication.cpu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RefreshAndLoadMoreView extends LinearLayout implements AbsListView.OnScrollListener {
    private static final int MAX_LIST_SIZE = 50; // 最多缓存50个广告

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView;
    private LinearLayout mFooterView;
    private LoadAndRefreshListener mListener;

    private int mLastVisibleItem;
    private int mTotalItemCounts;
    private boolean isLoading = false;

    public RefreshAndLoadMoreView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshAndLoadMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshAndLoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        mRefreshLayout = new SwipeRefreshLayout(context);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mListener != null) {
                    mListener.onRefresh();
                }
            }
        });
        LayoutParams rlParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mRefreshLayout, rlParams);
        mListView = new ListView(context);
        LayoutParams lvParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mRefreshLayout.addView(mListView, lvParams);
        mFooterView = new LinearLayout(context);
        mFooterView.setOrientation(HORIZONTAL);
        mFooterView.setGravity(Gravity.CENTER);
        ProgressBar progressBar = new ProgressBar(context);
        TextView textView = new TextView(context);
        textView.setText("加载中");
        LayoutParams pbParams =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mFooterView.addView(progressBar, pbParams);
        LayoutParams tvParams =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mFooterView.addView(textView, tvParams);
        mListView.addFooterView(mFooterView);
        mFooterView.setVisibility(GONE);
        mListView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mTotalItemCounts == mLastVisibleItem && scrollState==SCROLL_STATE_IDLE) {
            if (mListener != null && !isLoading && mTotalItemCounts < MAX_LIST_SIZE) {
                isLoading = true;
                mFooterView.setVisibility(VISIBLE);
                //加载数据
                mListener.onLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.mLastVisibleItem = firstVisibleItem + visibleItemCount;
        this.mTotalItemCounts = totalItemCount;
    }

    public void setRefreshing(boolean refreshing){
        mRefreshLayout.setRefreshing(refreshing);
    }

    public boolean isRefreshing() {
        return mRefreshLayout.isRefreshing();
    }

    public void onLoadFinish() {
        mRefreshLayout.setRefreshing(false);
        isLoading = false;
        mFooterView.setVisibility(GONE);
    }

    public void setLoadAndRefreshListener(LoadAndRefreshListener listener){
        this.mListener = listener;

    }

    public ListView getListView() {
        return mListView;
    }

    public interface LoadAndRefreshListener {
        void onRefresh();
        void onLoadMore();
    }
}
