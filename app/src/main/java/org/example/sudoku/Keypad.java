package org.example.sudoku;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @authorwang wei
 * @authorli yilei
 * @authordong ziqi
 * Created on 2017/6/15.
 * Created on 2017/6/14.
 */
public class Keypad extends Fragment {
    public int level;
    private AlertDialog mDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =
                inflater.inflate(R.layout.keypad, container, false);
        return rootview;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Get rid of the about dialog if it's still up
        if (mDialog != null)
            mDialog.dismiss();
    }
}