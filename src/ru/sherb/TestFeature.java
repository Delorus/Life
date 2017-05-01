package ru.sherb;

public final class TestFeature {

    static int torusMotion(int expectedPos, int torusSize) {
        return (((expectedPos % torusSize) + torusSize) % torusSize);
    }

    public static void main(String[] args) {
        final int n = 10;
        final int posX = 8;
        final int posY = 9;

        for (int i = torusMotion(posX - 1, n); i != torusMotion(posX + 2, n); i = torusMotion(++i, n)) {
            for (int j = torusMotion(posY - 1, n); j != torusMotion(posY + 2, n); j = torusMotion(++j, n)) {

                System.out.println("[" + i + ", " + j + "]");
            }
        }
        System.out.println("--------------------------------------------------");
//        posX = posX < 0 ? n + posX : posX > n ? n - posX : posX;
//        posY = posY < 0 ? n + posY : posY > n ? n - posY : posY;
        for (int i = posX - 1; i <= posX + 1; i++) {
            for (int j = posY - 1; j <= posY + 1; j++) {
                final int x = i < 0 ? n + i : i >= n ? n - i : i;
                final int y = j < 0 ? n + j : j >= n ? n - j : j;
                System.out.println("[" + x + ", " + y + "]");
            }
        }
    }
}