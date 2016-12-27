//package jp.mitsu;

public class BaseMatrix {
    final int[][] b = {
            { 2,-1},
            {-1,-2},
            { 2, 1},
            {-2, 1},
            { 1, 2},
            { 1,-2},
            {-2,-1},
            {-1, 2},
    };

    int[] mult(int[] p) {
        int[] ret = {
                b[0][0] * p[0] +
                        b[1][0] * p[1] +
                        b[2][0] * p[2] +
                        b[3][0] * p[3] +
                        b[4][0] * p[4] +
                        b[5][0] * p[5] +
                        b[6][0] * p[6] +
                        b[7][0] * p[7]
                ,
                b[0][1] * p[0] +
                        b[1][1] * p[1] +
                        b[2][1] * p[2] +
                        b[3][1] * p[3] +
                        b[4][1] * p[4] +
                        b[5][1] * p[5] +
                        b[6][1] * p[6] +
                        b[7][1] * p[7]
        };
        return ret;
    }

    public int getDimension() { return b.length; }
}
