package ru.sherb;

public final class TestFeature {

    public static void main(String[] args) {
        int[][] a = new int[10_000][10_000];

        long start = System.nanoTime();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[j][i] = i + j;
//                System.out.println(a[i][j]);
            }
//            System.out.println();
        }
        System.out.println(System.nanoTime() - start);

    }
}