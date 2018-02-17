

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;

import application.Surah;
import application.SurahDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainController implements Initializable{
	
	Surah surah = new Surah();
	SurahDetails surahDetails = new SurahDetails();

	@FXML
	BorderPane borderPane, detailsBorderPan;
	
	
	ObservableList<String> listt = FXCollections.observableArrayList("Aziz","Kalam","Salam","Tipu");
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(surah.isConnected()) {
			System.out.println("Db is connected");
			createContent();
		}else {
			System.out.println("DB is not connected");
		}
	}
	
	
	
	public void createContent() {
        
        List<Surah> surahList = surah.getAllSurahName();
        List<HBoxCell> vboxlist = new ArrayList<>();
        
        for (Surah item : surahList) {
        	vboxlist.add(new HBoxCell(item)); 
        }

        ListView<HBoxCell> listView = new ListView<HBoxCell>();
        ObservableList<HBoxCell> myObservableList = FXCollections.observableList(vboxlist);
        listView.setItems(myObservableList);
        borderPane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #dc143c, #661a33)");


        borderPane.setCenter(listView);

   }
	
	
	
	public  class HBoxCell extends VBox implements EventHandler<Event>{
        Label nameLabel = new Label();
        Label countLabel = new Label();
        Separator separotor = new Separator(Orientation.HORIZONTAL);
        Surah surah;
        HBoxCell(Surah surah) {
             super();
             this.surah = surah;
             nameLabel.setText(surah.getSurahName());
             nameLabel.setMaxWidth(Double.MAX_VALUE);
             VBox.setVgrow(nameLabel, Priority.ALWAYS);

             countLabel.setText("আয়াত সংখ্যা  ("+surah.getAyahCount()+") টি");

             this.getChildren().addAll(nameLabel, countLabel, separotor);
             
             nameLabel.setOnMousePressed(this);
             countLabel.setOnMousePressed(this);
           
        }
		@Override
		public void handle(Event event) {
			loadSurah(surah);
		}
   }
	
	private void loadSurah(Surah surah) {
		System.out.println("Surah name ="+surah.getSurahId()+" Ayah ="+surah.getAyahCount());
		List<SurahDetails> surahDetailsList = surahDetails.getSurahDetails(surah.getSurahId());
        System.out.println("Surah name ="+surahDetailsList.size());
        List<HBoxDetailsCell> vboxlist = new ArrayList<>();

        for (SurahDetails item : surahDetailsList) {
            vboxlist.add(new HBoxDetailsCell(item));
        }

        ListView<HBoxDetailsCell> listView = new ListView<HBoxDetailsCell>();
        ObservableList<HBoxDetailsCell> myObservableList = FXCollections.observableList(vboxlist);
        listView.setItems(myObservableList);
        //detailsBorderPan.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #dc143c, #661a33)");


        detailsBorderPan.setCenter(listView);
	}


    public  class HBoxDetailsCell extends VBox implements EventHandler<Event>{
        Label nameLabel = new Label();
        Label countLabel = new Label();
        Separator separotor = new Separator(Orientation.HORIZONTAL);
        SurahDetails surah;
        HBoxDetailsCell(SurahDetails surah) {
            super();
            this.surah = surah;
            nameLabel.setText(surah.getArabicText());
            nameLabel.setMaxWidth(Double.MAX_VALUE);
            VBox.setVgrow(nameLabel, Priority.ALWAYS);

            countLabel.setText(surah.getBanglaText());

            this.getChildren().addAll(nameLabel, countLabel, separotor);

            nameLabel.setOnMousePressed(this);
            countLabel.setOnMousePressed(this);

        }
        @Override
        public void handle(Event event) {
            //loadSurah(surah);
        }
    }

}
