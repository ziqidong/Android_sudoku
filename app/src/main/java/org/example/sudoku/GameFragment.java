package org.example.sudoku;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * @authorwang wei
 * @authorli yilei
 * @authordong ziqi
 * Created on 2017/6/15.
 */

public class GameFragment extends Fragment {
    public static final String KEY_DIFFICULTY = "org.example.sudoku.difficulty";
    private static final String PREF_PUZZLE = "puzzle";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;
    protected static final int DIFFICULTY_CONTINUE = -1;
    private String INFORMATION = "information of last activity";
    private String oldinfo = "default";          //核心字符串，每打开一个新的gameactivity都会根据他来确定每个位置的数字，长度必须是81
    private int puzzle[];
    private AlertDialog mDialog;
    private int diff;                           //记录难度，0简单，1一般，2困难
    private int clikindex = 0;                    //范围是0-80，记录玩家选中按键是这80个中的第几个，第一个大正方形是0-8，第二个大正方形是9-17，第三个是18-26，以此类推
    private int notusednb[];
    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,//存九个大正方形id的数组
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9,};
    static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,//存每个大正方形中九个小button的id的数组
            R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
            R.id.small9,};
    static private int keys[] = {R.id.one, R.id.two, R.id.three, R.id.four,//存keypad中九个按键id的数组
            R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine};
    private Tile mEntireBoard = new Tile(this);
    private Tile mLargeTiles[] = new Tile[9];       //好像没用到
    private Tile mSmallTiles[][] = new Tile[9][9];  //好像也没用到
    private final String easyPuzzle =               //初始化第一个activity
            "360004000000230004000800200" +
                    "070820500460000013003014020" +
                    "001007000900048000000300045";
    private final String mediumPuzzle =             //初始化第一个activity
            "650000014000506000070000005" +
                    "007002000009314700000700800" +
                    "500000030000201000630000097";
    private final String hardPuzzle =               //初始化第一个activity
            "009080501000605078000020000" +
                    "000706004000040000700102000" +
                    "000090000720301000903080600";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        diff = getActivity().getIntent().getIntExtra(KEY_DIFFICULTY,
                -1);
        oldinfo = getActivity().getIntent().getStringExtra(INFORMATION);    //接受上一个activity传进来的81个按键的值，存入oldinfo
        initGame();
        getActivity().getIntent().putExtra(KEY_DIFFICULTY, DIFFICULTY_CONTINUE);    //和continue功能有关的，还没看
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.large_board, container, false);
        initViews(rootView);                                                   //activity启动后初始化界面
        return rootView;
    }

    private void initViews(View rootView) {
        int data[] = getPuzzle(diff);                   //获取这81个按键的值，存入数组data[]
        mEntireBoard.setView(rootView);
        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);
            mLargeTiles[large].setView(outer);
            for (int small = 0; small < 9; small++) {
                ImageButton inner = (ImageButton) outer.findViewById
                        (mSmallIds[small]);
                LevelListDrawable levbt = (LevelListDrawable) inner.getDrawable();
                levbt.setLevel(data[large * 9 + small]);    //根据date[]中81个值为每个按键设置值
                mSmallTiles[large][small].setView(inner);
            }
        }
    }

    public void initGame() {                            //不知道有没有用，可能有用吧
        mEntireBoard = new Tile(this);
        // Create all the tiles
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large] = new Tile(this);
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small] = new Tile(this);
            }
            mLargeTiles[large].setSubTiles(mSmallTiles[large]);
        }
        mEntireBoard.setSubTiles(mLargeTiles);
    }


    public void putState(String gameData) {
        puzzle = fromPuzzleString(gameData);
    }//没用到

    public String getState() {
        return oldinfo;
    }//也没用到

    private int[] getPuzzle(int diff) {                         //根据库难度diff，获取81个按键的值，被initview()调用
        String puz = "";
        if (oldinfo == null) {  //启动第一个activity时oldinfo是null的，需要用自带的值对oldinfo初始化，启动第二个activity时oldinfo不再null，而是存放了第一个activity传递进来的值
            switch (diff) {
                case DIFFICULTY_CONTINUE:
                    puz = ((GameActivity) getActivity()).continueGame();
                    oldinfo = puz;
                    break;
                case DIFFICULTY_HARD:
                    puz = hardPuzzle;   //自带hard的值：hardPuzzle
                    oldinfo = puz;
                    break;
                case DIFFICULTY_MEDIUM:
                    puz = mediumPuzzle;
                    oldinfo = puz;
                    break;
                case DIFFICULTY_EASY:
                    puz = easyPuzzle;
                    oldinfo = puz;
                    break;
                default:
                    break;
            }
        } else {
            puz = oldinfo;    //从第二个activity开始oldinfo不再null，而是存放上一个activity的结果，因此不再使用自带的值
        }
        return fromPuzzleString(puz);   //81个长度的字符串转为数组
    }

    static protected int[] fromPuzzleString(String string) {
        int[] puz = new int[string.length()];
        for (int i = 0; i < puz.length; i++) {
            puz[i] = string.charAt(i) - '0';
        }
        return puz;
    }

    static private String toPuzzleString(int[] puz) {   //数组转字符串，没用到
        StringBuilder buf = new StringBuilder();
        if (puz != null) {
            for (int element : puz) {
                buf.append(element);
            }
        }
        return buf.toString();
    }

    protected void showKeypad(View view) {          //被GameActivity中的selectedTile（）调用，用来启动keypad
        clikindex = findlargelocation(view) * 9 + findsmalllocation(view);    //获取被按的按键在81个按键中的位置，存入clikindex
        if (oldinfo.substring(clikindex, clikindex + 1).equals("0")) {        //必须用equals，不能用==
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View choiceview = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_keypad, null);
            notusednb = getnotusednumber(findlargelocation(view), findsmalllocation(view));
            if (notusednb != null) {
                for (int i = 0; i < notusednb.length; i++) {
                    if (notusednb[i] == 1) {
                        choiceview.findViewById(R.id.one).setVisibility(View.INVISIBLE);
                    } else if ((notusednb[i]) == 2) {
                        choiceview.findViewById(R.id.two).setVisibility(View.INVISIBLE);
                    } else if ((notusednb[i]) == 3) {
                        choiceview.findViewById(R.id.three).setVisibility(View.INVISIBLE);
                    } else if ((notusednb[i]) == 4) {
                        choiceview.findViewById(R.id.four).setVisibility(View.INVISIBLE);
                    } else if ((notusednb[i]) == 5) {
                        choiceview.findViewById(R.id.five).setVisibility(View.INVISIBLE);
                    } else if ((notusednb[i]) == 6) {
                        choiceview.findViewById(R.id.six).setVisibility(View.INVISIBLE);
                    } else if ((notusednb[i]) == 7) {
                        choiceview.findViewById(R.id.seven).setVisibility(View.INVISIBLE);
                    } else if ((notusednb[i]) == 8) {
                        choiceview.findViewById(R.id.eight).setVisibility(View.INVISIBLE);
                    } else if ((notusednb[i]) == 9) {
                        choiceview.findViewById(R.id.nine).setVisibility(View.INVISIBLE);
                    }
                }
            }
            builder.setView(choiceview);
            mDialog = builder.show();
        }
    }

    protected void shownewview(View view) {         //被GameActivity中的selectedKey（）调用，用来启动新的activity
        setinfo(view);                              //将keypad上被选的值存入oldinfo
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra(INFORMATION, oldinfo);      //启动新的activity，当前activity玩家选择后的81个按键值存在oldinfo中，将其作为参数传给新的activity
        getActivity().startActivity(intent);
        getActivity().finish();
        mDialog.dismiss();
    }

    public int findsmalllocation(View view) {   //确定选择的按键是所在小正方型中的第几个，返回0-9
        int index = 0;
        for (int i = 0; i < 9; i++) {
            if (mSmallIds[i] == view.getId()) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int findlargelocation(View view) {   //确定选择的按键是在九个大正方形中的哪一个，返回0-9
        int index = 0;                          //findlargelocation(view)*9+findsmalllocation(view)就能得到按键在81个按键中的位置
        for (int i = 0; i < 9; i++) {
            if (mLargeTiles[i].ifsub(view)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int findkey(View view) {             //确定选择keypad上的哪一个，返回0-9
        int index = 0;
        for (int i = 0; i < 9; i++) {
            if (keys[i] == view.getId()) {
                index = i;
                break;
            }
        }
        return index + 1;
    }

    public void setinfo(View view) {    //将keypad选择的值存入oldinfo
        if (oldinfo != null) {
            StringBuilder sb = new StringBuilder();
            String st = new String(sb.append(oldinfo.substring(0, clikindex)). //根据玩家选择的按键在81个按键中的位置把oldinfo拆成两段，
                    append(findkey(view) + "").                             //把这个位置上旧的值替换为keypad输入的值
                    append(oldinfo.substring(clikindex + 1, 81)));             //第二段
            oldinfo = st;
        }
    }

    public int[] getnotusednumber(int largeindex, int smallindex) {
        StringBuilder sb = new StringBuilder();
        int x = 0;
        int y = 0;
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sb.append(oldinfo.substring(j * 9 + i * 3 + k * 27, j * 9 + i * 3 + 3 + k * 27));
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (i * 3 <= largeindex && largeindex <= i * 3 + 2) {
                y = smallindex / 3 + i * 3;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (largeindex == i || largeindex == i + 3 || largeindex == i + 6) {
                x = smallindex % 3 + i * 3;
            }
        }
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < 9; i++) {
            set.add(sb.substring(9 * y + i, 9 * y + 1 + i));
            set.add(sb.substring(i * 9 + x, i * 9 + x + 1));
        }
        StringBuilder ss = new StringBuilder();
        if (set != null) {
            for (String s : set) {
                ss.append(s);
            }
        }
        notusednb = fromPuzzleString(new String(ss).replaceAll("0", ""));
        return notusednb;
    }
}
