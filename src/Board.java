//package jp.mitsu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Board {
    private int xStart, yStart, xEnd, yEnd;
    private BaseMatrix bMatrix;

    Board(int x0, int y0, int x1, int y1) {
        xStart = x0; yStart = y0; xEnd = x1; yEnd = y1;
        bMatrix = new BaseMatrix();
    }

    // Determine if the knight walks within the board in a given sequence.
    private boolean isValid(int[] seq) {
        int xCurrent = xStart, yCurrent = yStart;

        for (int i = 0; i < seq.length; i++) {
            xCurrent += bMatrix.b[seq[i]] [0];
            yCurrent += bMatrix.b[seq[i]] [1];

            if (isOnBoard(xCurrent, yCurrent) == false) return false;
        }
        return true;
    }

    private boolean isOnBoard(int x, int y) {
        return ((0 <= x && x <= 7) && (0 <= y && y <= 7))? true: false;
    }

    private String coordinate2chess(int[] coord) {
        int x = coord[0];
        int y = coord[1];
        String alphas = "ABCDEFGH";
        char alpha = alphas.charAt(x);
        return (String.valueOf(alpha) + (y + 1));
    }

    public String[] getShortestPath(){
        return sequence2path(getShortestSequence());
    }

    private String[] sequence2path(int[] seq) {
        int xCurrent = xStart, yCurrent = yStart;
        String[] ret = new String[seq.length];

        for (int i = 0; i < seq.length; i++) {
            xCurrent += bMatrix.b[seq[i]] [0];
            yCurrent += bMatrix.b[seq[i]] [1];

            int[] posCurrent = {xCurrent, yCurrent};
            ret[i] = coordinate2chess(posCurrent);
        }

        return ret;
    }

    private int[] getShortestSequence() {
        int[] param;
        int[] relPos = {xEnd - xStart, yEnd - yStart};

        int distance = 0;
        while(true) {
            Iterator<int[]> params = distribute(distance, bMatrix.getDimension()).iterator();
            while (params.hasNext()) {
                param = params.next();
                if (Arrays.equals(bMatrix.mult(param), relPos)) {
                    Iterator<int[]> sequences = param2sequences(param).iterator();
                    while (sequences.hasNext()) {
                        int[] seq = sequences.next();
                        if (isValid(seq)) return seq;
                    }
                }
            }
            distance++;
        }
    }

    private List<int[]> permutate(int[] src) {
        List<int[]> ret = new ArrayList<int[]>();

        if (src.length == 1) {
            ret.add(src);
            return ret;
        } else {
            for (int i = 0; i < src.length; i++) {
                // Select one element from the src
                // and create a new array which consists of the rest of the elements.
                int[] rest = Arrays.copyOf(src, src.length - 1);
                System.arraycopy(src, i + 1, rest, i, src.length - i - 1);

                Iterator<int[]> it = permutate(rest).iterator();
                while (it.hasNext()) {
                    int[] permutatedRest = it.next();

                    // Concatenate src[i] with rest of the elements already permutated.
                    int[] dest = new int[permutatedRest.length + 1];
                    dest[0] = src[i];
                    System.arraycopy(permutatedRest, 0, dest, 1, permutatedRest.length);
                    if (!ret.contains(dest)) {
                        ret.add(dest);
                    }
                }
            }
            return ret;
        }
    }

    private List<int[]> param2sequences(int[] param) {
        // param describes a relative position of the ending position
        // in combination with the base matrix.
        // If param = (0, 2, 0, 1, 3, 0, 0, 0) then
        // one of the possible sequences would be (1, 1, 3, 4, 4, 4)
        // The length of the sequence is the sum of the elements in param.
        // Sequences are a permutated result of a sequence.
        // This method should be improved as it outputs redundant arrays.
        int distance = 0;
        for (int i = 0; i < param.length; i++) distance += param[i];
        int[] seq = new int[distance];

        int sIdx = 0;
        for (int i = 0; i < param.length; i++) {        // Scan the param
            for (int j = 0; j < param[i]; j++) {
                seq[sIdx++] = i;
            }
        }
        return permutate(seq);
    }

    // Distribute distance 'm', the length of the sequence, in 'n' dimensions.
    private List<int[]> distribute(int m, int n) {
        // Wanted to refactor so that the program outputs C7 B5 D6 B7
        // when given A8 B7 but it is too dangerous.
        List<int[]> ret = new ArrayList<int[]>();

        if (n == 1) {
            int[] uary = {m};
            ret.add(uary);
            return ret;
        } else {
            for (int i = m; i >= 0; i--) {
                Iterator<int[]> it = distribute(m - i, n - 1).iterator();
                while (it.hasNext()) {
                    int[] first = {i};
                    int[] rest  = (int[])it.next().clone();
                    int[] ary       = new int[first.length + rest.length];

                    System.arraycopy(first, 0, ary, 0,                        first.length);
                    System.arraycopy(rest,  0, ary, first.length, rest.length);
                    ret.add(ary);
                }
            }
            return ret;
        }
        // d(2, 3) = (2, d(0, 2)) = (2, (0, d(0, 1))) = (2, (0, (0))) = (2, 0, 0)
        //                 = (1, d(1, 2)) = (1, (1, d(0, 1))) = (1, (1, (0))) = (1, 1, 0)
        //                                                = (1, (0, d(1, 1))) = (1, (0, (1))) = (1, 0, 1)
        //                 = (0, d(2, 2)) = (0, (2, d(0, 1))) = (0, (2, (0))) = (0, 2, 0)
        //                                                = (0, (1, d(1, 1))) = (0, (1, (1))) = (0, 1, 1)
        //                                                = (0, (0, d(2, 1))) = (0, (0, (2))) = (0, 0, 2)
    }
}
