import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {
    private static final String TITLE = "Life";
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private final Canvas area = new Canvas();

    private boolean running = false;

    public static void main(String[] args) {
        Game game = new Game();
        game.init();

        game.area.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JFrame shell = new JFrame(TITLE);
        shell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shell.setLayout(new BorderLayout());
        shell.add(game.area, BorderLayout.CENTER);
        shell.pack();
        shell.setResizable(false);
        shell.setLocationRelativeTo(null);
        shell.setVisible(true);

        // Начало цикла игры
        game.start();

    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    public void init() {

    }

    public void update(float dt) {

    }

    public void render() {

    }


    @Override
    public void run() {
        final float SECONDS_PER_UPDATE = 1 / 30.f; // Фиксированная частота обновления графической и логической части
        float lag = 0.f;
        float frameTime = 0.f;
        int frameDrawn = 0;
        int tick = 0;
        long lastTime = System.nanoTime();
        boolean shouldRender = false;

        while (running) {
            long now = System.nanoTime();
            float elapsed = (now - lastTime) * 1e-9f; // перевод в секунды
            lag += elapsed;
            frameTime += elapsed;
            lastTime = System.nanoTime();

            while (lag >= SECONDS_PER_UPDATE) {
                update(lag);
                lag -= SECONDS_PER_UPDATE;
                tick++;
                shouldRender = true;
            }

            // Блокировка отрисовки на количество логических проходов
            if (shouldRender) {
                render();
                frameDrawn++;
                shouldRender = false;
            }

            if (frameTime >= 1.0) {
                int fps = (int) (frameDrawn / frameTime);
                System.out.println("FPS: " + fps +
                        "\ntick: " + tick);
                frameDrawn = 0;
                frameTime = 0.f;
                tick = 0;
            }

        }
    }
}
