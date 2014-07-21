package com.chanapps.four.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;
import com.chanapps.four.activity.BoardActivity;
import com.chanapps.four.activity.BoardSelectorActivity;
import com.chanapps.four.activity.R;
import com.chanapps.four.component.ToastRunnable;
import com.chanapps.four.data.ChanBoard;

import java.util.*;

/**
* Created with IntelliJ IDEA.
* User: arley
* Date: 12/14/12
* Time: 12:44 PM
* To change this template use File | Settings | File Templates.
*/
public class GoToBoardDialogFragment extends DialogFragment {

    public static final String TAG = GoToBoardDialogFragment.class.getSimpleName();

    private String[] boards = null;

    private void initBoards(Context context) {
        List<ChanBoard> chanBoards = ChanBoard.getBoardsRespectingNSFW(context);
        int size = chanBoards == null ? 1 : chanBoards.size() + 1;
        boards = new String[size];
        boards[0] = "Watchlist";
        if (chanBoards != null) { // just in case
            int i = 1;
            for (ChanBoard chanBoard : chanBoards) {
                String boardCode = chanBoard.link;
                String boardName = chanBoard.name;
                String boardLine = "/" + boardCode + " " + boardName;
                boards[i] = boardLine;
                i++;
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        initBoards(context);
        return new AlertDialog.Builder(context)
        .setTitle(R.string.go_to_board_menu)
        .setItems(boards, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String boardLine = boards[which];
                if (boardLine.equalsIgnoreCase("watchlist")) {
                    BoardSelectorActivity.startActivity(getActivity(), ChanBoard.Type.WATCHLIST);
                }
                else {
                    String boardCode = boardLine.substring(1, boardLine.indexOf(' '));
                    BoardActivity.startActivity(getActivity(), boardCode);
                }
            }
        })
        .setNegativeButton(R.string.dialog_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
        .create();
    }

}