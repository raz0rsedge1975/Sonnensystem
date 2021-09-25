package jfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sol.DateiVerbindung;
import sol.Planet;
import sol.Sonnensystem;

import java.io.File;
import java.io.IOException;

public class SonnenSystemApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws IOException {
        window.setTitle("Unser Sonnensystem");
        //Icon Set
        File imageFile = new File(DateiVerbindung.liesFileVomJARpfad("solar.png").getPath()); //<---
        Image iconImage = new Image(imageFile.toURI().toString());
        window.getIcons().add(iconImage);
        window.isAlwaysOnTop();
        //Earlier Alignment is ignored
        window.setOpacity(0.85);
        window.setResizable(false);

        Sonnensystem sonnensystem = new Sonnensystem(DateiVerbindung.liesFileVomJARpfad("sol.csv").getPath());
        PlanetView planetView = new PlanetView();
        Text spacer0 = new Text(" ");
        Button vorBtn = new Button("Vorheriger");
        Button nachBtn = new Button("Naechster");
        HBox hBox = new HBox(2); //spacing
        ChoiceBox choice = new ChoiceBox<String>(FXCollections.observableArrayList(" Abstand zur Sonne ", " Durchmesser ", " Name ",
                " Gewicht ", " Anzahl Monde ", " Umlaufzeit "));
        Text spacer1 = new Text("\n");


        vorBtn.setOnAction(event -> planetView.setPlanet(sonnensystem.vorheriger()));
        nachBtn.setOnAction(event -> planetView.setPlanet(sonnensystem.naechster()));
        choice.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (t1.intValue()){
                case 0:
                    sonnensystem.sort(Planet.ABSTANDZURSONNE);
                    break;
                case 1:
                    sonnensystem.sort(Planet.DURCHMESSER);
                    break;
                case 2:
                    sonnensystem.sort(Planet.NAME);
                    break;
                case 3:
                    sonnensystem.sort(Planet.GEWICHT);
                    break;
                case 4:
                    sonnensystem.sort(Planet.MONDE);
                    break;
                case 5:
                    sonnensystem.sort(Planet.UMLAUFZEIT);
                    break;
                default:
                    sonnensystem.sort(Planet.ABSTANDZURSONNE);
            }
        });

        //selektiert den ersten Wert im dropDown Menu (Sortierung nach Abstand zur Sonne -> Default Wert)
        choice.getSelectionModel().selectFirst();
        choice.setOnAction(actionEvent -> planetView.setPlanet(sonnensystem.vorheriger()));
        choice.setOnAction(actionEvent -> planetView.setPlanet(sonnensystem.naechster()));
        //Setzt den ersten Planeten des sonnensystems (nach default sortierung) im GUI an
        planetView.setPlanet(sonnensystem.getSonne());

        //planetView.setPlanet(sonnensystem.iterator().next());
        planetView.setBottom(hBox);
        hBox.getChildren().addAll(spacer0, vorBtn, nachBtn, choice, spacer1);
        hBox.setSpacing(5.);
        hBox.setPadding(new Insets(10.));
        hBox.setPadding(Insets.EMPTY);

        Scene scene = new Scene(planetView);
        /*
        BackgroundImage bggraphic = new BackgroundImage(
                new Image("\\src\\res\\marble.jpg"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        planetView.setBackground(new Background(bggraphic));
        */
        window.setScene(scene);
        window.sizeToScene();
        window.isAlwaysOnTop();
        window.show();
    }
}