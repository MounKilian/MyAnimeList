package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private HBox cardLayout;
    @FXML
    private HBox cardLayout1;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Anime> winterAnime = null;
        try {
            winterAnime = new ArrayList<>(Read("dataWinter.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        try {
            for (i = 0; i < winterAnime.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(winterAnime.get(i));
                cardBox.setId((winterAnime.get(i)).getName());
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        i=0;
        List<Anime> emblematicAnime = null;
        try {
            emblematicAnime = new ArrayList<>(Read("dataEmblematic.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            for (i = 0; i < emblematicAnime.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(emblematicAnime.get(i));
                cardLayout1.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Anime> Read(String filename) throws FileNotFoundException {
        List<Anime> card = new ArrayList<>();
        File getCSVFiles = new File(filename);
        Scanner sc = new Scanner(getCSVFiles);
        sc.useDelimiter(";");
        while (sc.hasNext()) {
            Anime anime = new Anime();
            anime.setName(sc.next());
            anime.setType(sc.next());
            anime.setImgSrc(sc.next());
            card.add(anime);
        }
        sc.close();
        return card;
    }
}
