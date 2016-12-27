//package jp.mitsu;

import java.io.IOException;
import java.util.*;

public class KnightTravails {
    public static void main(String args[]){
        int[] input = new int[4];
        int xStart, yStart, xEnd, yEnd;

        try {
            input = parseInput(args);
        } catch (IOException e) {
            System.out.println("Input Error: Input should be in algebraic chess notation representing the starting and ending positions");
            System.out.println("Example Input: A8 B7");
            return;
        } finally {
            xStart = input[0];
            yStart = input[1];
            xEnd   = input[2];
            yEnd   = input[3];

        }

        Board b = new Board(xStart, yStart, xEnd, yEnd);

        Iterator<String> it = Arrays.asList(b.getShortestPath()).iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
    }

    private static int[] parseInput(String[] args) throws IOException {
        if (args.length != 2) throw new IOException();

        int xStart = args[0].charAt(0); int yStart = args[0].charAt(1) - '1';
        int xEnd   = args[1].charAt(0); int yEnd   = args[1].charAt(1) - '1';

        if (! ('a' <= xStart && xStart <= 'h' || 'A' <= xStart && xStart <= 'H') )
            throw new IOException();
        if (! ('a' <= xEnd   && xEnd   <= 'h' || 'A' <= xEnd   && xEnd   <= 'H') )
            throw new IOException();
        if (! (0 <= yStart && yStart <= 7 && 0 <= yEnd && yEnd <= 7) ){
            throw new IOException();
        }

        xStart  = xStart - ((xStart >= 'a')? 'a': 'A');
        xEnd    = xEnd   - ((xEnd   >= 'a')? 'a': 'A');

        int[] ret = {xStart, yStart, xEnd, yEnd};
        return ret;
    }
}
