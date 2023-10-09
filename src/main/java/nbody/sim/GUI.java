package nbody.sim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUI extends Application {

    private static final int W = 1000;
    private static final int H = 1000;
    private static final int resolution = 2;    // metres per point

    private static Sim sim;
    private static Double cyclePeriod = 1.0;
    private Boolean simShouldRun = false;
    
    GraphicsContext gc;

    @Override
    public void start(Stage stage) {
        //TODO add text prompt for number of bodies
        sim = new Sim(4, W * resolution, H * resolution);

        Group root = new Group();
        Scene scene = new Scene(root, W, H);
        Canvas canvas = new Canvas(W, H);
        this.gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();
        render();
        sim.printBodies();

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case COMMA -> {
                    cyclePeriod -= 0.1;
                    render();
                }
                case PERIOD -> {
                    cyclePeriod += 0.1;
                    render();
                }
                case SPACE -> simShouldRun = !simShouldRun;
            }
        });

        // closing the application
        stage.setOnCloseRequest(event -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                loop();
            }
        };
        timer.start();
    }

    public void loop() {
        if (simShouldRun) {
            simCycle();
        }
        render();
    }

    public void render() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);

        // drawing the axes
        gc.setFill(Color.GRAY);
        gc.fillRect(0, H / 2.0, W, 2);
        gc.fillRect(W / 2.0, 0, 2, H);

        // drawing the bodies
        for (Body body : sim.bodyList) {
            gc.setFill(body.color);
            gc.fillOval(
                    W / 2.0 + body.pos.X / resolution - body.radius / 2.0,
                    H / 2.0 + body.pos.Y / resolution - body.radius / 2.0,
                    body.radius,
                    body.radius
            );

            gc.setFill(Color.LIGHTGRAY);
            gc.fillText(
                    body.printCoords(),
                    W / 2.0 + body.pos.X / resolution,
                    H / 2.0 + body.pos.Y / resolution
            );
        }
        
        // drawing the stats
        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Cycle: " + sim.simCycleNum, 10, 10);
        gc.fillText("Sim speed: " + cyclePeriod, 10, 30);
    }

    public void simCycle() {
        sim.doForces();
        sim.checkCollisions();
        sim.update(cyclePeriod);
    }

    public static void app_main(String[] args) {
        launch(args);
    }
}
