package com.example.tp2cabinet_medical;

import Entities.RendezVous;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class HelloController implements Initializable {

    private TreeMap<String,TreeMap<String, RendezVous>> monPlanning;
    @FXML
    private AnchorPane ap1;
    @FXML
    private TreeView tvCalendrier;
    @FXML
    private TextArea txtxArea;
    @FXML
    private TextField txtPtient;


    @FXML
    private ComboBox cboPatho;
    @FXML
    private DatePicker dateRdv;
    @FXML
    private Spinner spnHeure;
    @FXML
    private Spinner spnMinute;
    @FXML
    private Button btnValider;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboPatho.getItems().addAll("Angine","Grippe","Covid","Gastro");
        cboPatho.getSelectionModel().selectFirst();
        SpinnerValueFactory spinnerValueFactoryHeure=new SpinnerValueFactory.IntegerSpinnerValueFactory(8,19,8,1);
        spnHeure.setValueFactory(spinnerValueFactoryHeure);
        SpinnerValueFactory spinnerValueFactoryMinute=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,45,0,15);
        spnMinute.setValueFactory(spinnerValueFactoryMinute);
        monPlanning=new TreeMap<>();
    }

    @FXML
    public void btnValiderCliked(Event event) {

        if (txtPtient.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("");
            alert.setContentText("Veuillez saisir le nom du patient");
            alert.showAndWait();
        } else if (dateRdv.getValue()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("");
            alert.setContentText("Veuillez choisir une date");
            alert.showAndWait();
        }

        if (!monPlanning.containsKey(dateRdv.getValue().toString())) {
            monPlanning.put(dateRdv.getValue().toString(), new TreeMap<>());
        }

        int heure = (int) spnHeure.getValue();
        int minute = (int) spnMinute.getValue();

        String heureRdv = (heure < 10 ? "0" : "") + heure + ":" + (minute < 10 ? "0" : "") + minute;

        TreeMap<String, RendezVous> rendezVousMap = monPlanning.get(dateRdv.getValue().toString());

        // Vérifier si un rendez-vous existe déjà à la même heure et à la même minute
        if (rendezVousMap.containsKey(heureRdv)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("");
            alert.setContentText("Un rendez-vous existe déjà à cette heure et minute.");
            alert.showAndWait();
            return;
        }

        String patient = txtPtient.getText();
        String patho = cboPatho.getSelectionModel().getSelectedItem().toString();

        RendezVous rendezVous = new RendezVous(heureRdv, patient, patho);

        rendezVousMap.put(heureRdv, rendezVous);


      updateTreeView();

    }
    private void updateTreeView() {
        TreeItem root = new TreeItem("Mon Planning");

        for (String date : monPlanning.keySet()) {
            TreeItem dateItem = new TreeItem(date);
            dateItem.setExpanded(true);

            TreeMap<String, RendezVous> rendezVousMap = monPlanning.get(date);
            for (String heureRdv : rendezVousMap.keySet()) {
                RendezVous rendezVous = rendezVousMap.get(heureRdv);
                TreeItem rendezVousItem = new TreeItem(heureRdv);

                TreeItem patientItem = new TreeItem("Patient: " + rendezVous.getNomPatient());
                TreeItem pathoItem = new TreeItem("Pathologie: " + rendezVous.getNomPathologie());

                rendezVousItem.getChildren().addAll(patientItem, pathoItem);
                dateItem.getChildren().add(rendezVousItem);
            }

            root.getChildren().add(dateItem);
        }

        tvCalendrier.setRoot(root);
        tvCalendrier.getRoot().setExpanded(true);
    }






}