package com.kowksiuwang.everythingaboutandroid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class t {
    @Test
    public void test() {
        char[][] cs = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'}
                , {'6', '.', '.', '1', '9', '5', '.', '.', '.'}
                , {'.', '9', '8', '.', '.', '.', '.', '6', '.'}
                , {'8', '.', '.', '.', '6', '.', '.', '.', '3'}
                , {'4', '.', '.', '8', '.', '3', '.', '.', '1'}
                , {'7', '.', '.', '.', '2', '.', '.', '.', '6'}
                , {'.', '6', '.', '.', '.', '.', '2', '8', '.'}
                , {'.', '.', '.', '4', '1', '9', '.', '.', '5'}
                , {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        solveSudoku(cs);
    }

    private boolean isComplete = false;
    private int[] lines = new int[9];
    private int[] columns = new int[9];
    private int[][] blocks = new int[3][3];

    private ArrayList<int[]> unknownList = new ArrayList();

    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    int[] unknown = new int[2];
                    unknown[0] = i;
                    unknown[1] = j;
                    unknownList.add(unknown);
                } else {
                    int digit = board[i][j] - '1';
                    fill(i, j, digit);
                }
            }
        }
        dfs(0, board);
    }

    private void dfs(int pos, char[][] board) {
        if (pos == unknownList.size()) {
            isComplete = true;
            return;
        }
        int[] unknown = unknownList.get(pos);
        int i = unknown[0];
        int j = unknown[1];
        int tempBit = 0;
        for (int digit = 0; digit < 9; digit++) {
            tempBit = 1 << digit;
            if (((lines[i] & tempBit) == 0)
                    && ((columns[j] & tempBit) == 0)
                    && ((blocks[i / 3][j / 3] & tempBit) == 0)
                    && !isComplete) {
                fill(i, j, digit);
                board[i][j] = (char) ('1' + digit);
                dfs(pos + 1, board);
                fill(i, j, digit);
            }
        }
    }

    private void fill(int i, int j, int digit) {
        lines[i] = lines[i] ^ (1 << digit);
        columns[j] = columns[j] ^ (1 << digit);
        blocks[i / 3][j / 3] = blocks[i / 3][j / 3] ^ (1 << digit);
    }

}
