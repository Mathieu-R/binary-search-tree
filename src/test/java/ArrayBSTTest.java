import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunnerWithParametersFactory;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ArrayBSTTest {


    public static class TestNotParameterized {

        @Test(timeout =  2000)
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your get algorithm, debug first this small example", onFail = true)
        public void readableTestToDebugGetChar() {
            /*
             *  Encoding of the tree below key(value)
             *
             *                12(A)
             *                / \
             *               /   \
             *             5(B) 15(C)
             *             / \
             *           1(D) 8(E)
             *
             *   The state of internal array list after those put operations is
             *
             *    keys:        12, 15, 5, 8, 1
             *    values:       A,  C, B, E, D
             *    idxLeftNode:  2, -1, 4,-1,-1
             *    idxRightNode: 1, -1, 3,-1,-1
             */

            ArrayBST<Integer,Character> bst = new ArrayBST<Integer,Character>();

            bst.values = new ArrayList<Character>(Arrays.asList('A','C','B','E','D'));
            bst.keys   = new ArrayList<Integer>(Arrays.asList(   12, 15,  5,  8,  1));
            bst.idxLeftNode  = new ArrayList<Integer>(Arrays.asList(2,bst.NONE,4,bst.NONE,bst.NONE));
            bst.idxRightNode = new ArrayList<Integer>(Arrays.asList(1,bst.NONE,3,bst.NONE,bst.NONE));

            assertEquals(bst.get(12),new Character('A'));
            assertEquals(bst.get(8),new Character('E'));
            assertEquals(bst.get(20),null);
            assertEquals(bst.get(15),new Character('C'));
            assertEquals(bst.get(1),new Character('D'));

        }


        @Test(timeout =  2000)
        @Grade(value = 1, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your put algorithm, debug first this small example", onFail = true)
        public void readableTestPutInteger() {
            /*
             *  The tree below is the result of putting keyn,value pairs (12,A),(15,B),(5,C),(8,D),(1,E) (in this order)
             *
             *                12(A)
             *                / \
             *               /   \
             *             5(C)  15(B)
             *             / \
             *          1(E)  8(D)
             *
             *   The state of internal array list after those put operations is
             *    values:        A, B, C, D, E
             *    keys:        12, 15, 5, 8, 1
             *    idxLeftNode:  2, -1, 4,-1,-1
             *    idxRightNode: 1, -1, 3,-1,-1
             */
            ArrayBST<Integer,Character> bst = new ArrayBST<>();

            assertTrue(bst.put(12,'A'));
            assertTrue(bst.put(15,'B'));
            assertTrue(bst.put(5,'C'));
            assertTrue(bst.put(8,'D'));
            assertTrue(bst.put(1,'E'));

            assertFalse(bst.put(8,'d')); // replace value D of key 8 by d

            assertTrue(isBST(bst));
            assertArrayEquals(bst.keys.toArray(),new Integer[]{12,15,5,8,1});
            assertArrayEquals(bst.values.toArray(),new Character[]{'A','B','C','d','E'});
            assertArrayEquals(bst.idxLeftNode.toArray(),new Integer[]{2, bst.NONE,4, bst.NONE, bst.NONE});
            assertArrayEquals(bst.idxRightNode.toArray(),new Integer[]{1, bst.NONE,3, bst.NONE, bst.NONE});
            assertArrayEquals(collectIncreasing(bst).toArray(), new Integer[]{1,5,8,12,15});
        }

        @Test(timeout =  2000)
        @Grade(value = 4, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your get algorithm, debug first on small examples", onFail = true)
        public void testGetInteger() {
            ArrayBST<Integer,Integer> bst = new ArrayBST<Integer,Integer>();
            bst.values = new ArrayList<Integer>(Arrays.asList(18, 21, 9, 15, 16, 23, 2, 0, 19, 24, 9, 21, 6, 24, 17, 21, 23, 6, 19, 15, 9, 6, 11, 4, 16, 21, 16, 14, 5, 4, 14, 5, 18, 16, 0, 7, 14, 20, 14, 7, 3, 4, 23, 1, 22, 18, 15, 6, 11, 13, 25, 25, 25, 1, 23, 1, 25, 3, 12, 17, 16, 10, 15, 17, 2, 22, 17, 5, 1, 6, 15, 19, 14, 21, 15, 9, 9, 0, 18, 2, 17, 5, 17, 11, 7, 7, 7, 6, 5, 15, 3, 12, 2, 18));
            bst.keys   = new ArrayList<Integer>(Arrays.asList(  360, 29, 515, 491, 719, 77, 473, 95, 84, 241, 143, 524, 652, 503, 692, 745, 37, 602, 125, 938, 960, 555, 198, 74, 112, 319, 416, 207, 608, 387, 105, 408, 451, 358, 738, 457, 726, 297, 135, 267, 117, 905, 789, 901, 223, 668, 916, 957, 605, 536, 667, 331, 688, 730, 298, 797, 286, 290, 271, 986, 984, 792, 428, 49, 141, 325, 294, 517, 671, 577, 835, 913, 616, 440, 155, 508, 715, 633, 441, 98, 78, 67, 539, 702, 477, 978, 784, 589, 860, 872, 13, 953, 380, 734));
            bst.idxLeftNode  = new ArrayList<Integer>(Arrays.asList(1, 90, 3, 6, 11, 16, 26, 8, 80, 10, 18, 67, 17, -1, 45, 34, -1, 21, 24, 41, 47, 49, 74, 63, 30, 37, 29, -1, 48, 92, 79, -1, 62, 51, 36, -1, -1, 39, -1, -1, -1, 42, 86, 55, -1, 50, 71, 91, -1, -1, -1, 65, 68, -1, -1, 61, 58, -1, -1, 60, 85, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 83, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1));
            bst.idxRightNode = new ArrayList<Integer>(Arrays.asList(2, 5, 4, 13, 15, 7, 84, 9, -1, 25, 22, 12, 14, 75, 76, 19, 23, 28, 38, 20, 59, 69, 27, -1, 40, 33, 32, 44, 72, 31, -1, -1, 35, -1, -1, -1, 53, 54, 64, 56, -1, 46, 43, -1, -1, 52, -1, -1, -1, 82, -1, -1, -1, 93, -1, 70, 57, 66, -1, -1, -1, -1, 73, 81, -1, -1, -1, -1, -1, 87, 88, -1, 77, 78, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 89, -1, -1, -1, -1, -1));

            Random rnd = new Random(0);
            for (int i = 0; i < 2000; i++) {
                int idx = rnd.nextInt(bst.values.size());
                assertEquals(bst.get(bst.keys.get(idx)),bst.values.get(idx));
            }
            assertEquals(bst.get(200),null);
        }


        @Test(timeout =  2000)
        @Grade(value = 4, cpuTimeout = 1000)
        @GradeFeedback(message = "Sorry, something is wrong with your put/get algorithm, debug first on small examples", onFail = true)
        public void testPutInteger() {

            ArrayBST<Integer,Integer> bst = new ArrayBST<Integer,Integer>();
            assertEquals(4,bst.getClass().getFields().length);

            TreeMap<Integer,Integer> rbt = new TreeMap<>(); // java red-black tree
            Random rnd = new Random(0);
            for (int i = 0; i < 100000; i++) {
                // put some random (key, value) in the two bst
                int key = rnd.nextInt(100000);
                int value = rnd.nextInt(100000);
                bst.put(key, value);
                rbt.put(key,value);
            }
            // verify that it is well a bst
            assertTrue(isBST(bst));
            // and that values are present/absent as expected
            for (int i = 0; i < 100000; i++) {
                int key = rnd.nextInt(100000);
                assertEquals(bst.get(key),rbt.get(key));
            }
        }
    }



    // helper methods for the tests

    public static <Key extends Comparable<Key>,Value> boolean  isBST(ArrayBST<Key,Value> bst) {
        return isBST(bst,0, null, null);
    }

    private static <Key extends Comparable<Key>,Value> boolean isBST(ArrayBST<Key,Value> bst, int index, Key min, Key max) {
        if (index == bst.NONE) return true;
        if (min != null && bst.keys.get(index).compareTo(min) <= 0) return false;
        if (max != null && bst.keys.get(index).compareTo(max) >= 0) return false;
        return isBST(bst,bst.idxLeftNode.get(index), min, bst.keys.get(index)) && isBST(bst,bst.idxRightNode.get(index), bst.keys.get(index), max);
    }

    private static <Key extends Comparable<Key>,Value> List<Key>  collectIncreasing(ArrayBST<Key,Value> bst) {
        List<Key> result = new LinkedList<>();
        collectIncreasing(bst,0,result);
        return result;
    }

    private static <Key extends Comparable<Key>,Value> void  collectIncreasing(ArrayBST<Key,Value> bst, int index, List<Key> increasing) {
        if (bst.idxLeftNode.get(index) != bst.NONE) {
            collectIncreasing(bst,bst.idxLeftNode.get(index),increasing);
        }
        increasing.add(bst.keys.get(index));
        if (bst.idxRightNode.get(index) != bst.NONE) {
            collectIncreasing(bst,bst.idxRightNode.get(index),increasing);
        }
    }


}



