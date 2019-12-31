package org.example.sudoku;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;

/**
 * @authorwang wei
 * @authorli yilei
 * @authordong ziqi
 * Created on 2017/6/15.
 * Created on 2017/6/14.
 */
public class Tile {

    public enum Owner {
        X, O /* letter O */, NEITHER, BOTH
    }

    // These levels are defined in the drawable definitions
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2; // letter O
    private static final int LEVEL_3 = 3;
    private static final int LEVEL_4 = 4;
    private static final int LEVEL_5 = 5;
    private static final int LEVEL_6 = 6;
    private static final int LEVEL_7 = 7;
    private static final int LEVEL_8 = 8;
    private static final int LEVEL_9 = 9;
    private static final int LEVEL_BLANK = 0;

    private static final int LEVEL_AVAILABLE = 3;
    private static final int LEVEL_TIE = 3;
    private int i;

    //   private final GameFragment mGame;
    private Owner mOwner = Owner.NEITHER;
    private View mView;
    private Tile mSubTiles[];
    private final GameFragment mGame;

//   public Tile(GameFragment game) {
//      this.mGame = game;
//   }

    public Tile(GameFragment game) {
        this.mGame = game;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public Owner getOwner() {
        return mOwner;
    }

    public void setOwner(Owner owner) {
        this.mOwner = owner;
    }

    public Tile[] getSubTiles() {
        return mSubTiles;
    }

    public void setSubTiles(Tile[] subTiles) {
        this.mSubTiles = subTiles;
    }

    private int getLevel() {
        int level = LEVEL_BLANK;
        switch (i) {
            case 1:
                level = LEVEL_1;
                break;
            case 2: // letter O
                level = LEVEL_2;
                break;
            case 3:
                level = LEVEL_3;
                break;
            case 4:
                level = LEVEL_4;
                break;
            case 5:
                level = LEVEL_5;
                break;
            case 6:
                level = LEVEL_6;
                break;
            case 7:
                level = LEVEL_7;
                break;
            case 8:
                level = LEVEL_8;
                break;
            case 9:
                level = LEVEL_9;
                break;
        }
        return level;
    }

    public boolean ifsub(View view) {
        boolean k = false;
        for (int i = 0; i < 9; i++) {
            if (view == mSubTiles[i].getView()) {
                k = true;
            }
        }
        return k;
    }
}
