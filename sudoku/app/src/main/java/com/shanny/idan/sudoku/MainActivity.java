package com.shanny.idan.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Square> board = new ArrayList<>();
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                Square square = new Square();
                board.add(square);
            }
        }

        ArrayAdapter<Square> squareAdapter = new SquareAdapter(this, board);

        GridView boardGridView = findViewById(R.id.BoardGridView);
        boardGridView.setAdapter(squareAdapter);

    }

    /** Called when the user clicks the Send button */
    public void calculateSudoku(View view) {
        Board board = new Board();

        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++) {
                GridView boardGridView = findViewById(R.id.BoardGridView);
                EditText squareView = (EditText) boardGridView.getChildAt(i*9 + j);
                board.setBoardEntry (i, j,
                        Integer.parseInt("".equals(squareView.getText().toString()) ?
                                "0" : squareView.getText().toString()));
            }
        }

        int result = board.solveAndValidate();
        if (result == -1) {
            createPopup ("Invalid Input",
                    "There's no possible solution for this Sudoku board!" +
                            "\nTime to Find that out: " +
                            board.getLastSolveTime() +
                            "\nNumber of recursions: " + board.getCount(),
                    "Oops...");
        }
        else if (result == 1) {
            createPopup ("Board solved!",
                    "Time to Solve: " + board.getLastSolveTime() +
                            "\nTime to Validate: " + board.getLastValidateTime() +
                            "\nNumber of recursions: " + board.getCount(),
                    "Show me...");
            showSolution(board);
        }
        else { //result ==2
            createPopup ("Invalid Input",
                    "There are several possible solutions for this Sudoku board." +
                            " This is not a proper board!\n" +
                            "Nevertheless, I'm generous enough to show you one possible solution." +
                            "\nTime to Solve: " +
                            board.getLastSolveTime() + "\nTime to Validate: " +
                            board.getLastValidateTime() +
                            "\nNumber of recursions: " + board.getCount(),
                    "Thanks. I really appreciate it.");
            showSolution(board);
        }
    }

    private void createPopup (String title, String message, String label) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(label, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showSolution(Board board) {
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++) {
                GridView boardGridView = findViewById(R.id.BoardGridView);
                EditText squareView = (EditText) boardGridView.getChildAt(i*9 + j);
                squareView.setText(Integer.toString(board.getBoardEntry(i, j)));
            }
        }
    }
}