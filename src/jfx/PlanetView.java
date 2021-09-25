package jfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.*;

import javafx.scene.control.TextField;
import sol.DateiVerbindung;
import sol.Planet;

public class PlanetView extends BorderPane {

    //benötigte Nodes
    HBox topBox = new HBox();
    VBox vBoxLeft = new VBox();
    VBox vBoxCenter = new VBox();

    ImageView imageView = new ImageView();

    Text txtTop = new Text();
    Text txtDurchmesser = new Text("Durchmesser:");
    Text txtAbstand = new Text("Abstand zur Sonne:");
    Text txtGewicht = new Text("Gewicht (in Erdmassen):");
    Text txtUmlaufzeit = new Text("Umlaufzeit (in Erdjahren):");
    Text txtAnzahl = new Text("Anzahl Monde:");
    Text txtBemerkung = new Text("Bemerkungen:");

    TextField tfDruch = new TextField();
    TextField tfAbstand = new TextField();
    TextField tfGewicht = new TextField();
    TextField tfUmlauf = new TextField();
    TextField tfAnzahl = new TextField();
    TextArea tABemerkung = new TextArea();

    public PlanetView() {
        //Kosmetik
        HBox hBoxDurch = new HBox();
        txtDurchmesser.setWrappingWidth(249.);
        tfDruch.setMinWidth(249.);
        hBoxDurch.getChildren().addAll(txtDurchmesser, tfDruch);
        hBoxDurch.setAlignment(Pos.CENTER_LEFT);

        HBox hBoxAbst = new HBox();
        txtAbstand.setWrappingWidth(249.);
        tfAbstand.setMinWidth(249.);
        hBoxAbst.setAlignment(Pos.CENTER_LEFT);
        hBoxAbst.getChildren().addAll(txtAbstand, tfAbstand);

        HBox hBoxGew = new HBox();
        txtGewicht.setWrappingWidth(249.);
        tfGewicht.setMinWidth(249.);
        hBoxGew.setAlignment(Pos.CENTER_LEFT);
        hBoxGew.getChildren().addAll(txtGewicht, tfGewicht);

        HBox hBoxUmlauf = new HBox();
        txtUmlaufzeit.setWrappingWidth(249.);
        tfUmlauf.setMinWidth(249.);
        hBoxUmlauf.setAlignment(Pos.CENTER_LEFT);
        hBoxUmlauf.getChildren().addAll(txtUmlaufzeit, tfUmlauf);

        HBox hBoxAnz = new HBox();
        txtAnzahl.setWrappingWidth(249.);
        tfAnzahl.setMinWidth(249.);
        hBoxAnz.setAlignment(Pos.CENTER_LEFT);
        hBoxAnz.getChildren().addAll(txtAnzahl, tfAnzahl);

        VBox vBoxBemerk = new VBox();
        vBoxBemerk.getChildren().addAll(txtBemerkung, tABemerkung);
        tABemerkung.setWrapText(true);
        tABemerkung.setPrefColumnCount(1);
        tABemerkung.setPrefRowCount(4);
        topBox.getChildren().add(txtTop);
        topBox.setAlignment(Pos.CENTER);

        this.setTop(topBox);
        txtTop.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        imageView.setSmooth(true);
        vBoxLeft.getChildren().addAll(imageView);
        vBoxLeft.setAlignment(Pos.CENTER);
        vBoxCenter.getChildren().addAll(hBoxDurch, hBoxAbst, hBoxGew, hBoxUmlauf, hBoxAnz, vBoxBemerk);
        vBoxLeft.setPadding(new Insets(10.));
        vBoxLeft.setSpacing(5.);
        this.setCenter(vBoxCenter);
        this.setLeft(vBoxLeft);
        vBoxCenter.setPadding(new Insets(10.));
    }

    public void setPlanet(Planet planet) {
        txtTop.setText(planet.getName());
        tfDruch.setText(planet.getDurchmesser() + "");
        tfAbstand.setText(planet.getAbstandZurSonne() + "");
        tfAnzahl.setText(planet.getMonde() + "");
        tfGewicht.setText(planet.getRelativesGewicht() + "");
        tfUmlauf.setText(planet.getUmrundungszeit() + "");
        tABemerkung.setText(planet.getBemerkung());
        try {
            imageView.setImage(DateiVerbindung.ladeBild(planet.getName()));
            imageView.setFitHeight(200.);
            imageView.setFitWidth(200.);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
