package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.InputStream;
import java.util.Objects;

public class CardController {
    @FXML
    private Label animeName;

    @FXML
    private Label animeType;

    @FXML
    private HBox box;

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
}
