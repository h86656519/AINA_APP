package com.example.user.aina_app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void main() {
        printstar(5);
    }

    public void printstar(int width) {
        if (width % 2 == 1) {
            System.out.println("是奇數");
            for (int i = 0; i < width; i++) {
                for (int j = width - i; j > 0; j--) {
                    System.out.print("  " + "* " );

                }
                System.out.println(" ");
            }

        } else {
            System.out.println("是偶數，不能為偶數");

        }

    }
}