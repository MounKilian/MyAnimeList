package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class CardController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label animeName;

    @FXML
    private Label animeType;

    @FXML
    private ImageView animeImage;

    public void setData(Anime anime){
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
    }

    @FXML
    public void switchSceneMenue(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/card.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
