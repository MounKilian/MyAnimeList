package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField animeDescriptionAdd;

    @FXML
    private TextField animeDirectoryAdd;

    @FXML
    private TextField animeGenreAdd;

    @FXML
    private TextField animeNameAdd;

    @FXML
    private TextField animeRankedAdd;

    @FXML
    private TextField animeSeasonAdd;

    @FXML
    TextField animeDescriptionModify;

    @FXML
    TextField animeDirectoryModify;

    @FXML
    TextField animeGenreModify;

    @FXML
    TextField animeNameModify;

    @FXML
    TextField animeRankedModify;

    @FXML
    TextField animeSeasonModify;

    @FXML
    private HBox cardLayout;

    @FXML
    private HBox cardLayout1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Anime anime = new Anime();
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
        i = 0;
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

    @FXML
    void goToSearch(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        CardController card = new CardController();
        card.listView.getItems().addAll(card.nameList);
    }

    @FXML
    void goToMyList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backToHomePage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/primary.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void add(ActionEvent event) throws IOException {
        Write("dataWinter.csv");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/primary.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Write(String filename) throws FileNotFoundException {
        List<Anime> animes;
        animes = Read(filename);
        try (PrintWriter writer = new PrintWriter(filename)) {
            StringBuilder sb = new StringBuilder();
            for (Anime anime : animes) {
                sb.append(anime.getName()).append('<').append(';').append('>').append(anime.getType()).append('<').append(';').append('>').append(anime.getImgSrc()).append('<').append(';').append('>').append(anime.getEpisodeAndSeason()).append('<').append(';').append('>').append(anime.getRank()).append('<').append(';').append('>').append(anime.getDirector()).append('<').append(';').append('>').append(anime.getDescription()).append('<').append(';').append('>');
            }
            sb.append("\r\n");
            sb.append(animeNameAdd.getText()).append('<').append(';').append('>').append(animeGenreAdd.getText()).append('<').append(';').append('>').append("/img/logo.png").append('<').append(';').append('>').append(animeSeasonAdd.getText()).append('<').append(';').append('>').append(animeRankedAdd.getText()).append('<').append(';').append('>').append(animeDirectoryAdd.getText()).append('<').append(';').append('>').append(animeDescriptionAdd.getText()).append('<').append(';').append('>');
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void confirmModify(ActionEvent event) throws IOException {
        WriteModif("dataWinter.csv");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/primary.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void WriteModif(String filename) throws FileNotFoundException {
        List<Anime> animes;
        animes = Read(filename);
        try (PrintWriter writer = new PrintWriter(filename)) {
            StringBuilder sb = new StringBuilder();
            for (Anime anime : animes) {
                sb.append(anime.getName()).append('<').append(';').append('>').append(anime.getType()).append('<').append(';').append('>').append(anime.getImgSrc()).append('<').append(';').append('>').append(anime.getEpisodeAndSeason()).append('<').append(';').append('>').append(anime.getRank()).append('<').append(';').append('>').append(anime.getDirector()).append('<').append(';').append('>').append(anime.getDescription()).append('<').append(';').append('>');
            }
            sb.append("\r\n");
            sb.append(animeNameModify.getText()).append('<').append(';').append('>').append(animeGenreModify.getText()).append('<').append(';').append('>').append("/img/logo.png").append('<').append(';').append('>').append(animeSeasonModify.getText()).append('<').append(';').append('>').append(animeRankedModify.getText()).append('<').append(';').append('>').append(animeDirectoryModify.getText()).append('<').append(';').append('>').append(animeDescriptionModify.getText()).append('<').append(';').append('>');
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void Delete(String filename, String animeName, ActionEvent event, boolean modify) throws IOException {
        List<Anime> animes;
        animes = Read(filename);
        try (PrintWriter writer = new PrintWriter(filename)) {
            StringBuilder sb = new StringBuilder();
            for (Anime anime : animes) {
                if (!Objects.equals(anime.getName(), animeName)) {
                    sb.append(anime.getName()).append('<').append(';').append('>').append(anime.getType()).append('<').append(';').append('>').append(anime.getImgSrc()).append('<').append(';').append('>').append(anime.getEpisodeAndSeason()).append('<').append(';').append('>').append(anime.getRank()).append('<').append(';').append('>').append(anime.getDirector()).append('<').append(';').append('>').append(anime.getDescription()).append('<').append(';').append('>');
                }
            }
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        if (filename.equals("dataEmblematic.csv") && !modify){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/primary.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public List<Anime> Read(String filename) throws FileNotFoundException {
        List<Anime> card = new ArrayList<>();
        File getCSVFiles = new File(filename);
        Scanner sc = new Scanner(getCSVFiles);
        sc.useDelimiter("<;>");
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