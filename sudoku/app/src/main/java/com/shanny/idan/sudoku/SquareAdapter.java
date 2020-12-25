package com.shanny.idan.sudoku;

import android.app.Activity;
import android.graphics.Color;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class SquareAdapter extends ArrayAdapter<Square> {

    public SquareAdapter(Activity context, ArrayList<Square> board) {
        super(context, 0, board);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Square currentSquare = getItem(position);

        View squareItemView = convertView;
        if (squareItemView == null) {
            squareItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.square, parent, false);
        }

        if ((position % 9) / 3 == 1 ^ position / 27 == 1) {
            //squareItemView.setBackgroundColor(Color.LTGRAY);
            squareItemView.setBackgroundResource(R.drawable.back_with_border_grey);

        }
        return squareItemView;
    }


}
