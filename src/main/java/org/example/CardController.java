package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class CardController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label animeDescription;

    @FXML
    private Label animeDirector;

    @FXML
    private ImageView animeImage;

    @FXML
    private Label animeName;

    @FXML
    private Label animeRank;

    @FXML
    private Label animeSeason;

    @FXML
    private Label animeType;

    @FXML
    private Button cardButton;

    public void affect(HBox cardbox){
        cardButton.setId(cardbox.getId());
    }

    public void setData(Anime anime, boolean state){
        InputStream inputStream = getClass().getResourceAsStream(anime.getImgSrc());
        if (inputStream != null) {
            Image image = new Image(inputStream);
            animeImage.setImage(image);
        } else {
            Image defaultImage = new Image(getClass().getResourceAsStream("/img/logo.png"));
            animeImage.setImage(defaultImage);
        }
        animeName.setText(anime.getName());
        animeType.setText(anime.getType());
        if (state){
            animeDescription.setText(anime.getDescription());
            animeDirector.setText(anime.getDirector());
            animeRank.setText(anime.getRank());
            animeSeason.setText(anime.getEpisodeAndSeason());
        }
    }

    @FXML
    void delete(ActionEvent event) throws IOException {
        FXMLController FxmlController = new FXMLController();
        FxmlController.Delete("dataWinter.csv", animeName.getText(), event);
    }

    @FXML
    void goToMyList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backToHomePage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/primary.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchSceneMenu(ActionEvent event) throws IOException {
        String button = ((Button)event.getSource()).getId();
        Anime anime = search(button);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dataAnime.fxml"));
        root = loader.load();
        CardController cardController = loader.getController();
        cardController.setData(anime, true);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public Anime search(String id) throws FileNotFoundException {
        try (Scanner sc1 = new Scanner(new File("dataEmblematic.csv"))) {
            sc1.useDelimiter("<;>");
            while (sc1.hasNext()) {
                Anime anime1 = new Anime();
                String name = sc1.next();
                if (Objects.equals(id, name)) {
                    anime1.setName(name);
                    anime1.setType(sc1.next());
                    anime1.setImgSrc(sc1.next());
                    anime1.setEpisodeAndSeason(sc1.next());
                    anime1.setRank(sc1.next());
                    anime1.setDirector(sc1.next());
                    anime1.setDescription(sc1.next());
                    return anime1;
                }
            }
        }

        try (Scanner sc = new Scanner(new File("dataWinter.csv"))) {
            sc.useDelimiter("<;>");
            while (sc.hasNext()) {
                Anime anime = new Anime();
                String name = sc.next();
                if (Objects.equals(id, name)) {
                    anime.setName(name);
                    anime.setType(sc.next());
                    anime.setImgSrc(sc.next());
                    anime.setEpisodeAndSeason(sc.next());
                    anime.setRank(sc.next());
                    anime.setDirector(sc.next());
                    anime.setDescription(sc.next());
                    return anime;
                }
            }
        }
        return new Anime("Solo Leveling", "Action", "/img/sololeveling.jpg");
    }
}
