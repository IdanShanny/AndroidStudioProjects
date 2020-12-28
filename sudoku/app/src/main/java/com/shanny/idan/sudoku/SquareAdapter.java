package com.shanny.idan.sudoku;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class SquareAdapter extends ArrayAdapter<Square> {

    private ArrayList<Square> mBoard;

    public SquareAdapter(Activity context, ArrayList<Square> board) {
        super(context, 0, board);

        this.mBoard = board;
    }

    String[] numbers = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View squareItemView = convertView;
        if (squareItemView == null) {
            squareItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.square, parent, false);
        }

        Spinner squareSpinner = (Spinner) squareItemView;
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(squareSpinner.getContext(), android.R.layout.simple_spinner_item, numbers);
        squareSpinner.setAdapter(spinnerAdapter);


        if ((position % 9) / 3 == 1 ^ position / 27 == 1) {
            squareSpinner.setBackgroundResource(R.drawable.back_with_border_grey);
        }

        if (mBoard != null && mBoard.get(position).getNumber() != null) {
            squareSpinner.setSelection(Integer.parseInt(mBoard.get(position).getNumber()));
        }

        return squareSpinner;
    }

}
