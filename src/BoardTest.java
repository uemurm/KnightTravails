import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

public class BoardTest {
    @Test
    public void integrationTest()
    {
        Board board = new Board(0, 7, 1, 6);

        Iterator<String> itr = Arrays.asList(board.getShortestPath()).iterator();
        String path = "";
        while (itr.hasNext()) {
            path += itr.next() + " ";
        }

        Assert.assertEquals("C7 B5 D6 B7 ", path);
    }
}