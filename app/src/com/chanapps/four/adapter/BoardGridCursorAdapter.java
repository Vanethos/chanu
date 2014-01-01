package com.chanapps.four.adapter;

import android.content.Context;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.chanapps.four.activity.R;
import com.chanapps.four.activity.SettingsActivity;
import com.chanapps.four.data.ChanPost;
import com.chanapps.four.data.ChanThread;
import com.chanapps.four.viewer.BoardGridViewHolder;
import com.chanapps.four.viewer.ThreadViewHolder;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: johnarleyburns
 * Date: 2/4/13
 * Time: 6:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoardGridCursorAdapter extends AbstractBoardCursorAdapter {

    protected static final int TYPE_GRID_ITEM = 0;
    protected static final int TYPE_GRID_ITEM_1 = 1;
    protected static final int TYPE_GRID_ITEM_2 = 2;
    protected static final int TYPE_GRID_ITEM_3 = 3;
    protected static final int TYPE_GRID_ITEM_4 = 4;
    protected static final int TYPE_GRID_ITEM_5 = 5;
    protected static final int TYPE_MAX_COUNT = 6;
    protected static final int TYPE_HIDE_LAST_COUNT = 1;

    protected boolean hideLastReplies;

    public BoardGridCursorAdapter(Context context, ViewBinder viewBinder) {
        super(context,
                R.layout.board_grid_item,
                viewBinder,
                new String[]{
                        ChanThread.THREAD_SUBJECT,
                        ChanThread.THREAD_HEADLINE,
                        ChanThread.THREAD_COUNTRY_FLAG_URL,
                        ChanThread.THREAD_THUMBNAIL_URL
                },
                new int[]{
                        R.id.grid_item_thread_subject,
                        R.id.grid_item_thread_info,
                        R.id.grid_item_country_flag,
                        R.id.grid_item_thread_thumb
                }
        );
        hideLastReplies = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(SettingsActivity.PREF_HIDE_LAST_REPLIES, false);
    }

    @Override
    public int getItemViewType(int position) {
        if (hideLastReplies)
            return TYPE_GRID_ITEM;
        Cursor cursor = getCursor();
        if (cursor == null || !cursor.moveToPosition(position))
            return TYPE_GRID_ITEM;
        int numLastReplies = cursor.getInt(cursor.getColumnIndex(ChanThread.THREAD_NUM_LAST_REPLIES));
        switch (numLastReplies) {
            case 5:
                return TYPE_GRID_ITEM_5;
            case 4:
                return TYPE_GRID_ITEM_4;
            case 3:
                return TYPE_GRID_ITEM_3;
            case 2:
                return TYPE_GRID_ITEM_2;
            case 1:
                return TYPE_GRID_ITEM_1;
            case 0:
            default:
                return TYPE_GRID_ITEM;
        }
    }

    protected int getItemViewLayout(int position) {
        if (hideLastReplies)
            return R.layout.board_grid_item;
        Cursor cursor = getCursor();
        if (cursor == null || !cursor.moveToPosition(position))
            return R.layout.board_grid_item;
        int numLastReplies = cursor.getInt(cursor.getColumnIndex(ChanThread.THREAD_NUM_LAST_REPLIES));
        switch (numLastReplies) {
            case 5:
                return R.layout.board_grid_item_5;
            case 4:
                return R.layout.board_grid_item_4;
            case 3:
                return R.layout.board_grid_item_3;
            case 2:
                return R.layout.board_grid_item_2;
            case 1:
                return R.layout.board_grid_item_1;
            case 0:
            default:
                return R.layout.board_grid_item;
        }
    }

    @Override
    protected View newView(ViewGroup parent, int tag, int position) {
        if (DEBUG) Log.d(TAG, "Creating " + tag + " layout for " + position);
        View v = mInflater.inflate(getItemViewLayout(position), parent, false);
        BoardGridViewHolder viewHolder = new BoardGridViewHolder(v);
        v.setTag(R.id.VIEW_TAG_TYPE, tag);
        v.setTag(R.id.VIEW_HOLDER, viewHolder);
        return v;
    }

    @Override
    public int getViewTypeCount() {
        if (hideLastReplies)
            return TYPE_HIDE_LAST_COUNT;
        else
            return TYPE_MAX_COUNT;
    }

}
