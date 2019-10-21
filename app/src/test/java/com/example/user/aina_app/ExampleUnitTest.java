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
        printstar1(11);
    }

    public void printstar(int width) {
        if (width % 2 == 1) {
            System.out.println("是奇數");
            for (int i = 0; i < width; i++) {
                for (int k = -1; k < i - 1; k++) {
                    System.out.print("_");
                }

                for (int j = width - i; j > 0; j--) {
                    System.out.print("*" + "_");

                }
                System.out.println(" ");

//                if (i == width - 1){
//                    System.out.print(" ");
//                }
//                if (i == width - 2){
//                    System.out.print(" ");
//                }
            }

        } else {
            System.out.println("是偶數，不能為偶數");

        }

    }

    public void printstar1(int width) {
        if (width % 2 == 1) {
//            System.out.println("是奇數");
            //倒三角
            for (int i = 0; i < width / 2 + 1; i++) { // 行數
                for (int k = 0; k <= i - 1; k++) {
                    System.out.print("");
                }
                for (int j = width - i * 2 + 2; j > 0; j--) {
                    System.out.print("*");
                }
                System.out.println(" ");
            }
            //正三角
            for (int i = 0; i < width / 2 + 1; i++) {
                for (int k = width - i * 2; k > 0; k--) {
                    System.out.print(" ");
                    k = k - 1;
                }
                for (int j = -2; j < 2 * i - 1; j++) {
                    System.out.print("*");
                }
                System.out.println("");
            }

        } else {
            System.out.println("是偶數，不能為偶數");

        }
    }
}