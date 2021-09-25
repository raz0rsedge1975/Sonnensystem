package jfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CubeFXAnimCube extends Application {

    private double[][] coords = {
            {100,100,100},{100,100,-100},{-100,100,-100},{-100,100,100}, //0,1,2,3
            {100,-100,100},{100,-100,-100},{-100,-100,-100},{-100,-100,100}, //4,5,6,7
            {0,33,200},{0,-33,-200}, //8Oben,9Unten
            {0,200,33},{0,-200,33},   //10rechts, 11links
            {200,33,33},{-200,-33,-33} //12vorn, 13hinten
    };
    private int[][] lines = {
            {0,1},{1,2},{2,3},{3,0},
            {4,5},{5,6},{6,7},{7,4},
            {0,4},{1,5},{2,6},{3,7},
            {0,8},{3,8},{4,8},{7,8},
            {1,9},{2,9},{5,9},{6,9},
            {0,10},{1,10},{2,10},{3,10},
            {4,11},{5,11},{6,11},{7,11},
            {0,12},{1,12},{4,12},{5,12},
            {2,13},{3,13},{6,13},{7,13}
    };

    private double alpha = 0;
    private double beta = 0;
    private double gamma = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Cube FX");
        Pane pane = new StackPane();

        Canvas canvas = new Canvas(800, 600);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, 800, 600);

        new AnimationTimer() {

            @Override
            public void handle(long l) {

                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillRect(0, 0, 800, 600);

//                alpha += 0.005;
//                beta += 0.007;
//                gamma += 0.003;

                // doing rotations
                double[][] rot = new double[coords.length][3];

                for (int i=0; i<coords.length; i++) {

                    double x1 = coords[i][0];
                    double y1 = coords[i][1]*Math.cos(alpha) - coords[i][2]*Math.sin(alpha);
                    double z1 = coords[i][1]*Math.sin(alpha) + coords[i][2]*Math.cos(alpha);

                    double x2 = z1*Math.sin(beta) + x1*Math.cos(beta);
                    double y2 = y1;
                    double z2 = z1*Math.cos(beta) - x1*Math.sin(beta);

                    double x3 = x2*Math.cos(gamma) - y2*Math.sin(gamma);
                    double y3 = x2*Math.sin(gamma) + y2*Math.cos(gamma);
                    double z3 = z2;

                    rot[i][0] = x3 * 1000 / (1000-z3);
                    rot[i][1] = y3 * ((z3+1000)/1000);
                    rot[i][2] = z3; // wird benötigt um die perspektivische Verzerrung rein zu bringen.
                }

                // draw the lines
                PixelWriter pixelWriter = graphicsContext.getPixelWriter();
                for (int[] line : lines) {
                    int x1 = (int) (rot[ line[0] ][0]) +400;
                    int y1 = (int) (rot[ line[0] ][1]) +300;
                    int x2 = (int) (rot[ line[1] ][0]) +400;
                    int y2 = (int) (rot[ line[1] ][1]) +300;
                    drawLine(x1,y1,x2,y2,pixelWriter, Color.BLACK);
                }
            }
        }.start();

        //Per Mausklick verschieben & Drehen
        canvas.setOnMouseDragged( e -> {
            beta = (e.getX() / 90.);
            alpha = (-e.getY() / 90.);
        });

        pane.getChildren().add(canvas);

        stage.setScene(new Scene(pane));
        stage.show();

    }

    private void drawLine(int x1, int y1, int x2, int y2, PixelWriter pixelWriter, Color color) {
        int dy = y2-y1;
        int dx = x2-x1;

        pixelWriter.setColor(x1,y1,color);

        if (dy >= 0) {
            if (dx >= 0) {
                if (dx >= dy) { // Oktant I     -> Lauf über +dx
                    int fehler = dx >> 1;
                    while (x1 < x2) {
                        x1++;
                        fehler -= dy;
                        if (fehler < 0) {
                            y1++;
                            fehler += dx;
                        }
                        pixelWriter.setColor(x1,y1,color);
                    }
                }
                else {      // Oktant II    -> Lauf über +dy
                    int fehler = dy >> 1;
                    while (y1 < y2) {
                        y1++;
                        fehler -= dx;
                        if (fehler < 0) {
                            x1++;
                            fehler += dy;
                        }
                        pixelWriter.setColor(x1,y1,color);
                    }
                }
            }
            else {
                if (-dx >= dy) { // Oktant IV     -> Lauf über -dx
                    int fehler = -dx >> 1;
                    while (x1 > x2) {
                        x1--;
                        fehler -= dy;
                        if (fehler < 0) {
                            y1++;
                            fehler -= dx;
                        }
                        pixelWriter.setColor(x1,y1,color);
                    }
                }
                else {      // Oktant III    -> Lauf über +dy
                    int fehler = dy >> 1;
                    while (y1 < y2) {
                        y1++;
                        fehler += dx;
                        if (fehler < 0) {
                            x1--;
                            fehler += dy;
                        }
                        pixelWriter.setColor(x1,y1,color);
                    }
                }
            }
        }
        else {
            if (dx < 0) {
                if (dx <= dy) { // Oktant V     -> Lauf über -dx
                    int fehler = -dx >> 1;
                    while (x2 < x1) {
                        x1--;
                        fehler += dy;
                        if (fehler < 0) {
                            y1--;
                            fehler -= dx;
                        }
                        pixelWriter.setColor(x1,y1,color);
                    }
                }
                else {      // Oktant VI    -> Lauf über -dy
                    int fehler = -dy >> 1;
                    while (y2 < y1) {
                        y1--;
                        fehler += dx;
                        if (fehler < 0) {
                            x1--;
                            fehler -= dy;
                        }
                        pixelWriter.setColor(x1,y1,color);
                    }
                }
            }
            else {
                if (dx >= -dy) { // Oktant VIII     -> Lauf über dx
                    int fehler = dx >> 1;
                    while (x1 < x2) {
                        x1++;
                        fehler += dy;
                        if (fehler < 0) {
                            y1--;
                            fehler += dx;
                        }
                        pixelWriter.setColor(x1,y1,color);
                    }
                }
                else {      // Oktant VII    -> Lauf über +dy
                    int fehler = -dy >> 1;
                    while (y2 < y1) {
                        y1--;
                        fehler -= dx;
                        if (fehler < 0) {
                            x1++;
                            fehler -= dy;
                        }
                        pixelWriter.setColor(x1,y1,color);
                    }
                }
            }
        }
    }

}
