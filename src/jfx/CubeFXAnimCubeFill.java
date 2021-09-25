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

import java.util.Arrays;
import java.util.Random;

public class CubeFXAnimCubeFill extends Application {

    private int rand = (int) (Math.random() * 10);

    private double[][] coords = {
            {100,100,100},{100,100,-100},{-100,100,-100},{-100,100,100},
            {100,-100,100},{100,-100,-100},{-100,-100,-100},{-100,-100,100}
    };
    private int[][] lines = {
            {0,1},{1,2},{2,3},{3,0},
            {4,5},{5,6},{6,7},{7,4},
            {0,4},{1,5},{2,6},{3,7}
    };
    // Vierecke mit Farbe
    private int[][] faces = {
            {0,1,2,3,0},
            {1,5,6,2,1},
            {7,6,5,4,2},
            {2,6,7,3,3},
            {3,7,4,0,4},
            {5,1,0,4,5}
    };
    private Color[] colors = {Color.rgb(00,10,rand,0.2),
            Color.rgb(10,20,rand,0.3),
            Color.rgb(20,30,rand,0.4),
            Color.rgb(30,40,rand,0.5),
            Color.rgb(40,50,rand,0.6),
            Color.rgb(50,60,rand,0.7)
    };

    private double alpha = 0.;
    private double beta = 0.;
    private double gamma = 0.;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        double[] mouse = {0,0};
        int widthF = 400;
        int heightF = 400;

        stage.setTitle("Derpy FX");
        Pane pane = new StackPane();

        Canvas canvas = new Canvas(widthF, heightF);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        /*graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, 400, 300);*/

        new AnimationTimer() {

            double min = 1000;
            double max = 0;

            @Override
            public void handle(long l) {

                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(0, 0, widthF, heightF);

                alpha += 0.02;
                beta += 0.03;
                gamma += 0.01;

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

                    rot[i][0] = (x3+mouse[0]) *1000 / (1000-z3);
                    rot[i][1] = (y3+mouse[1]) *1000 / (1000-z3);
                    rot[i][2] = z3;
                }

                PixelWriter pixelWriter = graphicsContext.getPixelWriter();

                Arrays.sort(faces,
                        (a,b) -> (int)(rot[a[0]][2]+rot[a[1]][2]+rot[a[2]][2]+rot[a[3]][2] -
                                      (rot[b[0]][2]+rot[b[1]][2]+rot[b[2]][2]+rot[b[3]][2])));

                // draw the faces
                for (int i=0; i < faces.length; i++) {
                    int[] face = faces[i];

                    Color c = colors[face[4]];

                    fill(
                            (int)rot[face[0]][0] + widthF/2, (int)rot[face[0]][1] + heightF/2,
                            (int)rot[face[1]][0] + widthF/2, (int)rot[face[1]][1] + heightF/2,
                            (int)rot[face[2]][0] + widthF/2, (int)rot[face[2]][1] + heightF/2,
                            pixelWriter, c);
                    fill(
                            (int)rot[face[0]][0] + widthF/2, (int)rot[face[0]][1] + heightF/2,
                            (int)rot[face[2]][0] + widthF/2, (int)rot[face[2]][1] + heightF/2,
                            (int)rot[face[3]][0] + widthF/2, (int)rot[face[3]][1] + heightF/2,
                            pixelWriter, c);
                }
            }
        }.start();

        canvas.setOnMouseDragged(e -> {
            mouse[0] = e.getX()-400;
            mouse[1] = e.getY()-300;
        });

        canvas.setOnDragExited(dragEvent -> {
            mouse[0] = dragEvent.getX();
            mouse[1] = dragEvent.getY();
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

    private void fill(int x1, int y1, int x2, int y2, int x3, int y3, PixelWriter pixelWriter, Color color) {
        // finde min und max y..
        if (y1 > y2) {
            int a = y1;
            y1 = y2;
            y2 = a;
            a = x1;
            x1 = x2;
            x2 = a;
        }
        if (y1 > y3) {
            int a = y1;
            y1 = y3;
            y3 = a;
            a = x1;
            x1 = x3;
            x3 = a;
        }
        if (y2 > y3) {
            int a = y2;
            y2 = y3;
            y3 = a;
            a = x2;
            x2 = x3;
            x3 = a;
        }
        int[] start = new int[y3-y1+1];
        int[] ende  = new int[y3-y1+1];

        drawLine(x1,0,x3,y3-y1, start);
        drawLine(x1,0,x2,y2-y1, ende);
        drawLine(x2,y2-y1,x3,y3-y1, ende);

        for (int i = 0; i < start.length; i++)
            if (start[i] <= ende[i])
                for (int j = start[i]; j < ende[i]; j++)
                    pixelWriter.setColor(j,i+y1,color);
            else
                for (int j = ende[i]; j < start[i]; j++)
                    pixelWriter.setColor(j,i+y1,color);

    }

    private void drawLine(int x1, int y1, int x2, int y2, int[] coord) {
        int dy = y2-y1;
        int dx = x2-x1;

        coord[y1] = x1;

        if (dy >= 0) {
            if (dx >= 0) {
                // Oktant I     -> Lauf über +dx
                // Oktant II    -> Lauf über +dy
                int fehler = dy >> 1;
                while (y1 < y2) {
                    y1++;
                    fehler -= dx;
                    while (fehler < 0) {
                        x1++;
                        fehler += dy;
                    }
                    coord[y1] = x1;
                }
            }
            else {
                // Oktant IV     -> Lauf über -dx
                // Oktant III    -> Lauf über +dy
                int fehler = dy >> 1;
                while (y1 < y2) {
                    y1++;
                    fehler += dx;
                    while (fehler < 0) {
                        x1--;
                        fehler += dy;
                    }
                    coord[y1] = x1;
                }
            }
        }
        else {
            if (dx >= 0) {
                // Oktant I     -> Lauf über +dx
                // Oktant II    -> Lauf über +dy
                int fehler = -dy >> 1;
                while (y2 < y1) {
                    y1--;
                    fehler -= dx;
                    while (fehler < 0) {
                        x1++;
                        fehler -= dy;
                    }
                    coord[y1] = x1;
                }
            }
            else {
                // Oktant IV     -> Lauf über -dx
                // Oktant III    -> Lauf über +dy
                int fehler = -dy >> 1;
                while (y2 < y1) {
                    y1--;
                    fehler += dx;
                    while (fehler < 0) {
                        x1--;
                        fehler -= dy;
                    }
                    coord[y1] = x1;
                }
            }
        }
    }

}
