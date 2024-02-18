package org.example;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class CardController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private CheckBox tragedy;

    @FXML
    private CheckBox romance;

    @FXML
    private CheckBox scienceFiction;

    @FXML
    private CheckBox adventure;

    @FXML
    private CheckBox comedy;

    @FXML
    private CheckBox darkFantasy;

    @FXML
    private CheckBox drama;

    @FXML
    private CheckBox fantaisy;

    @FXML
    private CheckBox action;

    @FXML
    private Label animeDescription;

    @FXML
    private Label animeDirector;

    @FXML
    ImageView animeImage;

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

    @FXML
    ListView<String> listView;

    @FXML
    private TextField searchFilter;

    ArrayList<String> nameList = new ArrayList<>();

    private ObservableList<String> statusBoxList = FXCollections.observableArrayList("Watching","ToWatch", "Completed", "On-Hold", "Dropped");

    @FXML
    private ComboBox statusAnime = new ComboBox<>(statusBoxList);

    private ObservableList<String> notationBoxList = FXCollections.observableArrayList("Masterpiece","Good", "Bad", "Horrible");

    @FXML
    private ComboBox notationAnime = new ComboBox(notationBoxList);

    @FXML
    void search(KeyEvent event) throws IOException {
        nameList.clear();
        String selectedGenres = new String();
        if (action.isSelected()) {
            selectedGenres = action.getText();
        }
        if (darkFantasy.isSelected()) {
            selectedGenres = (darkFantasy.getText());
        }
        if (fantaisy.isSelected()) {
            selectedGenres = (fantaisy.getText());
        }
        if (romance.isSelected()) {
            selectedGenres = (romance.getText());
        }
        if (adventure.isSelected()) {
            selectedGenres = (adventure.getText());
        }
        if (tragedy.isSelected()) {
            selectedGenres = (tragedy.getText());
        }
        if (comedy.isSelected()) {
            selectedGenres = (comedy.getText());
        }
        if (scienceFiction.isSelected()) {
            selectedGenres = (scienceFiction.getText());
        }
        if (drama.isSelected()) {
            selectedGenres = (drama.getText());
        }
        FXMLController fxml = new FXMLController();
        ArrayList<Anime> animeList = new ArrayList<>(fxml.Read("dataWinter.csv"));
        animeList.addAll(fxml.Read("dataEmblematic.csv"));
        for (Anime animes : animeList){
            if (!nameList.contains(animes.getName().replace("\r\n", ""))){
                if (selectedGenres.isEmpty() || animes.getType().contains(selectedGenres)){
                    nameList.add(animes.getName().replace("\r\n", ""));
                }
            }
        }
        listView.getItems().clear();
        listView.getItems().addAll(searchList(searchFilter.getText(), nameList));
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Anime anime = null;
                String toSearch = String.valueOf(listView.getSelectionModel().getSelectedItems()).replace("[", "\r\n").replace("]", "");
                try {
                    anime = search(toSearch);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dataAnime.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                CardController cardController = loader.getController();
                try {
                    cardController.setData(anime, true);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
    }


    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream()
                .filter(input -> searchWordsArray.stream()
                        .allMatch(word -> input.toLowerCase().contains(word.toLowerCase())))
                .sorted()
                .collect(Collectors.toList());
    }

    public void affect(HBox cardbox){
        cardButton.setId(cardbox.getId());
    }

    public void setData(Anime anime, boolean state) throws FileNotFoundException {
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
            Anime anime1;
            anime1 = searchStatus(String.valueOf(animeName.getText()));
            statusAnime.setPromptText(anime1.getStatus());
            notationAnime.setPromptText(anime1.getNotation());
        }
    }

    @FXML
    void modify(ActionEvent event) throws IOException {
        Anime anime = search(animeName.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modify.fxml"));
        Parent root = loader.load();

        FXMLController fxmlController = loader.getController();

        fxmlController.animeNameModify.setText(anime.getName());
        fxmlController.animeGenreModify.setText(anime.getType());
        fxmlController.animeDirectoryModify.setText(anime.getDirector());
        fxmlController.animeSeasonModify.setText(anime.getEpisodeAndSeason());
        fxmlController.animeRankedModify.setText(anime.getRank());
        fxmlController.animeDescriptionModify.setText(anime.getDescription());

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        fxmlController.Delete("dataWinter.csv", animeName.getText(), event, true);
        fxmlController.Delete("dataEmblematic.csv", animeName.getText(), event, true);
    }

    @FXML
    void delete(ActionEvent event) throws IOException {
        FXMLController FxmlController = new FXMLController();
        FxmlController.Delete("dataWinter.csv", animeName.getText(), event, false);
        FxmlController.Delete("dataEmblematic.csv", animeName.getText(), event, false);
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
    public void backToHomePage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/primary.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToSearch(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        listView.getItems().addAll(nameList);
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

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusAnime.setItems(statusBoxList);
        notationAnime.setItems(notationBoxList);

        statusAnime.setOnAction(event ->{
            String data = statusAnime.getSelectionModel().getSelectedItem().toString();
            try {
                Anime anime = new Anime();
                anime = searchStatus(String.valueOf(animeName.getText()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                DeleteStatus(String.valueOf(animeName.getText()));
                WriteStatus(data);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        notationAnime.setOnAction(event ->{
            String data = notationAnime.getSelectionModel().getSelectedItem().toString();
            try {
                Anime anime = new Anime();
                anime = searchStatus(String.valueOf(animeName.getText()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                DeleteStatus(String.valueOf(animeName.getText()));
                WriteNotation(data);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<Anime> ReadStatus() throws FileNotFoundException {
        List<Anime> card = new ArrayList<>();
        File getCSVFiles = new File("status.csv");
        Scanner sc = new Scanner(getCSVFiles);
        sc.useDelimiter("<;>");
        while (sc.hasNext()) {
            Anime anime = new Anime();
            anime.setName(sc.next());
            anime.setStatus(sc.next());
            anime.setNotation(sc.next());
            card.add(anime);
        }
        sc.close();
        return card;
    }

    public Anime searchStatus(String id) throws FileNotFoundException {
        try (Scanner sc1 = new Scanner(new File("status.csv"))) {
            sc1.useDelimiter("<;>");
            while (sc1.hasNext()) {
                Anime anime1 = new Anime();
                String name = sc1.next();
                if (Objects.equals(id, name)) {
                    anime1.setName(name);
                    anime1.setStatus(sc1.next());
                    anime1.setNotation(sc1.next());
                    return anime1;
                }
            }
        }
        return new Anime("Solo Leveling", "Action", "/img/sololeveling.jpg");
    }

    public void WriteStatus(String data) throws FileNotFoundException {
        List<Anime> animes;
        animes = ReadStatus();
        try (PrintWriter writer = new PrintWriter("status.csv")) {
            StringBuilder sb = new StringBuilder();
            for (Anime anime : animes) {
                sb.append(anime.getName()).append('<').append(';').append('>').append(anime.getStatus()).append('<').append(';').append('>').append(anime.getNotation()).append('<').append(';').append('>');
            }
            sb.append(animeName.getText()).append('<').append(';').append('>').append(data).append('<').append(';').append('>').append(notationAnime.getPromptText()).append('<').append(';').append('>');
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void DeleteStatus(String animeName) throws IOException {
        List<Anime> animes;
        animes = ReadStatus();
        try (PrintWriter writer = new PrintWriter("status.csv")) {
            StringBuilder sb = new StringBuilder();
            for (Anime anime : animes) {
                if (!Objects.equals(anime.getName(), animeName)) {
                    sb.append(anime.getName()).append('<').append(';').append('>').append(anime.getStatus()).append('<').append(';').append('>').append(anime.getNotation()).append('<').append(';').append('>');
                }
            }
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void WriteNotation(String data) throws FileNotFoundException {
        List<Anime> animes;
        animes = ReadStatus();
        try (PrintWriter writer = new PrintWriter("status.csv")) {
            StringBuilder sb = new StringBuilder();
            for (Anime anime : animes) {
                sb.append(anime.getName()).append('<').append(';').append('>').append(anime.getStatus()).append('<').append(';').append('>').append(anime.getNotation()).append('<').append(';').append('>');
            }
            sb.append(animeName.getText()).append('<').append(';').append('>').append(statusAnime.getPromptText()).append('<').append(';').append('>').append(data).append('<').append(';').append('>');
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}