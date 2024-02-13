package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Anime anime = null;
        anime = new Anime();
        try {
            anime.setAnime(Read("dataWinter.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        try {
            for (i = 0; i < anime.getAnime().size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(anime.getAnime().get(i), false);
                cardBox.setId((anime.getAnime().get(i)).getName());
                cardController.affect(cardBox);
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        i=0;
        Anime anime1 = null;
        anime1 = new Anime();
        try {
            anime1.setAnime(Read("dataEmblematic.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            for (i = 0; i < anime1.getAnime().size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(anime1.getAnime().get(i), false);
                cardBox.setId((anime1.getAnime().get(i)).getName());
                cardController.affect(cardBox);
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
            anime.setEpisodeAndSeason(sc.next());
            anime.setRank(sc.next());
            anime.setDirector(sc.next());
            anime.setDescription(sc.next());
            card.add(anime);
        }
        sc.close();
        return card;
    }
}
