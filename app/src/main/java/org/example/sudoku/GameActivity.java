package org.example.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * @authorwang wei
 * @authorli yilei
 * @authordong ziqi
 * Created on 2017/6/15.
 * Created on 2017/6/14.
 */

public class GameActivity extends Activity {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private GameFragment mGameFragment;
    private String gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
// Restore game here...
/*------------------------------------------------------------------------------*/
        //和continue功能有关的部分，还没有写
        mGameFragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game);
        int restore = getIntent().getIntExtra(KEY_RESTORE, 0);
        if (restore == -1) {
            gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
        }
/*------------------------------------------------------------------------------*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameData = mGameFragment.getState();
        getPreferences(MODE_PRIVATE).edit()
                .putString(PREF_RESTORE, gameData)
                .commit();
    }

    //按下选位置的的按键时，触发这个函数
    public void selectedTile(View view) {
        GameFragment fragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game);
        fragment.showKeypad(view);  //调用GameFragment中的showkyepad（）
    }

    //弹出keypad后，选keypad上数字时触发这个函数
    public void selectedKey(View view) {
        GameFragment fragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game);
        fragment.shownewview(view); //调用GameFragment中的shownewview（）
    }

    public String continueGame() {
        return getPreferences(MODE_PRIVATE)
                .getString(PREF_RESTORE, null);
    }

}
