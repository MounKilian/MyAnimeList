package org.example;
/*
Put header here


 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class FXMLController implements Initializable {

    @FXML
    private HBox cardLayout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Anime> winterAnime = new ArrayList<>(winterAnime());
        int i = 0;
        try {
            for (i = 0; i < winterAnime.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(winterAnime.get(i));
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Anime> winterAnime() {
        List<Anime> card = new ArrayList<>();
        Anime anime = new Anime();
        anime.setName("Solo Leveling");
        anime.setType("Action, Dark Fantasy");
        anime.setImgSrc("/img/sololeveling.jpg");
        card.add(anime);

        anime = new Anime();
        anime.setName("Bucchigiri?!");
        anime.setType("Action");
        anime.setImgSrc("/img/bucchigiri.jpg");
        card.add(anime);

        anime = new Anime();
        anime.setName("The Weakest Tamer Began a \n" +
                "Journey to Pick Up Trash");
        anime.setType("Action, Adventure, Comedy");
        anime.setImgSrc("/img/theWeakestTamerBeganaJourneytoPickUpTrash.jpg");
        card.add(anime);

        anime = new Anime();
        anime.setName("Metallic Rouge");
        anime.setType("Action, Science-fiction");
        anime.setImgSrc("/img/metallicRouge.jpg");
        card.add(anime);

        anime = new Anime();
        anime.setName("The Demon Prince of Momochi \n" +
                "House");
        anime.setType("Fantasy, Romance");
        anime.setImgSrc("/img/theDemonPrinceofMomochiHouse.jpg");
        card.add(anime);

        return card;
    }
}
