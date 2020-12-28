package com.shanny.idan.sudoku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LIST_INSTANCE_STATE = "boardInstanceState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Square> board = new ArrayList<>();

        String[] numbersBoard = new String[81];
        if (savedInstanceState != null) {
            numbersBoard = savedInstanceState.getStringArray(LIST_INSTANCE_STATE);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.add(new Square(numbersBoard[i * 9 + j]));
            }
        }

        GridView boardGridView = findViewById(R.id.BoardGridView);
        ArrayAdapter<Square> squareAdapter = new SquareAdapter(this, board);

        boardGridView.setAdapter(squareAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        GridView boardGridView = findViewById(R.id.BoardGridView);
        String[] configChangeBoard = new String[81];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Spinner squareView = (Spinner) boardGridView.getChildAt(i * 9 + j);
                if (squareView != null &&
                        !"".equals(squareView.getSelectedItem().toString())) {
                    configChangeBoard[i * 9 + j] = squareView.getSelectedItem().toString();
                }
            }
        }

        outState.putStringArray(LIST_INSTANCE_STATE, configChangeBoard);
    }

    /**
     * Called when the user clicks the Solve button
     */
    public void calculateSudoku(View view) {

        Board board = new Board();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                GridView boardGridView = findViewById(R.id.BoardGridView);
                Spinner squareView = (Spinner) boardGridView.getChildAt(i * 9 + j);
                board.setBoardEntry(i, j,
                        Integer.parseInt("".equals(squareView.getSelectedItem().toString()) ?
                                "0" : squareView.getSelectedItem().toString()));
            }
        }

        int result = board.solveAndValidate();
        if (result == -1) {
            createPopup("Invalid Input",
                    "There's no possible solution for this Sudoku board!" +
                            "\nTime to Find that out: " +
                            board.getLastSolveTime() +
                            "\nNumber of recursions: " + board.getCount(),
                    "Oops...");
        } else if (result == 1) {
            createPopup("Board solved!",
                    "Time to Solve: " + board.getLastSolveTime() +
                            "\nTime to Validate: " + board.getLastValidateTime() +
                            "\nNumber of recursions: " + board.getCount(),
                    "Show me...");
            showSolution(board);
        } else { //result ==2
            createPopup("Invalid Input",
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

    private void createPopup(String title, String message, String label) {
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                GridView boardGridView = findViewById(R.id.BoardGridView);
                Spinner squareView = (Spinner) boardGridView.getChildAt(i * 9 + j);
                squareView.setSelection(board.getBoardEntry(i, j));
            }
        }
    }
}