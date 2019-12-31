package org.example.sudoku;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static org.example.sudoku.GameFragment.KEY_DIFFICULTY;

/**
 * @authorwang wei
 * @authorli yilei
 * @authordong ziqi
 * Created on 2017/6/15.
 * Created on 2017/6/14.
 */
public class MainFragment extends Fragment {
    private AlertDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Handle buttons here...
        View newButton = rootView.findViewById(R.id.new_button);
        View continueButton = rootView.findViewById(R.id.continue_button);
        View aboutButton = rootView.findViewById(R.id.about_button);
        View exitButton = rootView.findViewById(R.id.exit_button);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                View choiceview = LayoutInflater.from(getActivity()).inflate(R.layout.diffculty_choose, null);
                builder.setView(choiceview);
                final Button ebutton = (Button) choiceview.findViewById(R.id.easy_button);
                final Button mbutton = (Button) choiceview.findViewById(R.id.medium_button);
                final Button hbutton = (Button) choiceview.findViewById(R.id.hard_button);
                ebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), GameActivity.class);
                        intent.putExtra(KEY_DIFFICULTY, 0);
                        getActivity().startActivity(intent);
                        mDialog.dismiss();
                    }
                });
                mbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), GameActivity.class);
                        intent.putExtra(KEY_DIFFICULTY, 1);
                        getActivity().startActivity(intent);
                        mDialog.dismiss();
                    }
                });
                hbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), GameActivity.class);
                        intent.putExtra(KEY_DIFFICULTY, 2);
                        getActivity().startActivity(intent);
                        mDialog.dismiss();
                    }
                });
                mDialog = builder.show();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra(GameActivity.KEY_RESTORE, true);
                getActivity().startActivity(intent);
            }
        });


        aboutButton.setOnClickListener(new View.OnClickListener() {                //设置按键监听
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.about_title);
                builder.setMessage(R.string.about_text);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                mDialog = builder.show();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });


        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Get rid of the about dialog if it's still up
        if (mDialog != null)
            mDialog.dismiss();
    }
}