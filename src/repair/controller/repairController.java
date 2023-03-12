package repair.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import repair.model.RepairModel;
import repair.model.WidgetModel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;

public class repairController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem clearMenuItem;

    @FXML
    private MenuItem mediumMenuItem;

    @FXML
    private MenuItem bigMenuItem;

    @FXML
    private MenuItem smallMenuItem;

    @FXML
    private MenuItem defaultMenuItem;

    @FXML
    private MenuItem boldMenuItem;

    @FXML
    private MenuItem italicMenuItem;

    @FXML
    private MenuItem blackMenuItem;

    @FXML
    private MenuItem grayMenuItem;

    @FXML
    private MenuItem copyMenuItem;

    @FXML
    private MenuItem findMenuItem;

    @FXML
    private MenuItem wordMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem dateMenuItem;

    @FXML
    private MenuItem timeMenuItem;

    @FXML
    private MenuItem stopwatchMenuItem;

    @FXML
    private MenuItem OSMenuItem;

    @FXML
    private MenuItem LANIPMenuItem;

    @FXML
    private MenuItem WANIPMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private GridPane inputGridPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField cipherTextField;

    @FXML
    private TextField repairTypeTextField;

    @FXML
    private TextField repairCostTextField;

    @FXML
    private TextField repairerTextField;

    @FXML
    private Button addButton;

    @FXML
    private Slider numberSlider;

    @FXML
    private Button editButton;

    @FXML
    private TitledPane outputTitledPane;

    @FXML
    private GridPane outputGridPane;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private void initialize() {
        try {
            OSMenuItem.setText(WidgetModel.getOS());
            LANIPMenuItem.setText(WidgetModel.getLANIP());
            WANIPMenuItem.setText(WidgetModel.getWANIP());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        event -> {
                            dateMenuItem.setText(WidgetModel.getDate());
                            timeMenuItem.setText(WidgetModel.getTime());
                            stopwatchMenuItem.setText(WidgetModel.getStopwatch());
                            outputTitledPane.setText("Output" + " " + "(number of objects: " + RepairModel.repairers.size() + ")");

                            if (RepairModel.repairers.isEmpty() == true) {
                                numberSlider.setMin(0);
                                numberSlider.setMax(0);
                            }
                            else if (RepairModel.repairers.isEmpty() == false) {
                                numberSlider.setMax(RepairModel.repairers.size()-1);
                            }

                            numberSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                                RepairModel.repairers.get((int) numberSlider.getValue());
                                nameTextField.setText(RepairModel.repairers.get((int) numberSlider.getValue()).getName());
                                cipherTextField.setText(Integer.toString((RepairModel.repairers.get((int) numberSlider.getValue()).getCipher())));
                                repairTypeTextField.setText(RepairModel.repairers.get((int) numberSlider.getValue()).getRepairType());
                                repairCostTextField.setText(Double.toString((RepairModel.repairers.get((int) numberSlider.getValue()).getRepairCost())));
                                repairerTextField.setText(RepairModel.repairers.get((int) numberSlider.getValue()).getRepairer());
                            });
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    @FXML
    private void addButtonOnAction(ActionEvent event) {
        try {
            String name = nameTextField.getText();
            int cipher = Integer.parseInt(cipherTextField.getText());
            String repairType = repairTypeTextField.getText();
            double repairCost = Double.parseDouble(repairCostTextField.getText());
            String repairer = repairerTextField.getText();

            RepairModel repair = new RepairModel(name, cipher, repairType, repairCost, repairer);

            RepairModel repairCopy = new RepairModel(repair);

            repairCopy.repairers.add(repairCopy);

            outputTextArea.appendText(RepairModel.repairers.get(RepairModel.repairers.size() - 1).toString2());
        }
        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong." + "\n"
            + "Check input data.");
            alert.showAndWait();
        }
    }

    @FXML
    private void editButtonOnAction(ActionEvent event) {
        try {
            String name = nameTextField.getText();
            int cipher = Integer.parseInt(cipherTextField.getText());
            String repairType = repairTypeTextField.getText();
            double repairCost = Double.parseDouble(repairCostTextField.getText());
            String repairer = repairerTextField.getText();

            RepairModel.repairers.set(((int)numberSlider.getValue()), new RepairModel(name, cipher, repairType, repairCost, repairer));

            outputTextArea.setText("");

            for (RepairModel rm: RepairModel.repairers) {
                outputTextArea.appendText(rm.toString2());
            }
        }
        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong." + "\n"
                    + "Check input data.");
            alert.showAndWait();
        }
    }

    @FXML
    private void openMenuItemOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Extention filter
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("DAT files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extentionFilter);

        //Set to user directory or go to default if cannot access
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);

        //Choose the file
        File chosenFile = fileChooser.showOpenDialog(null);
        //Make sure a file was selected, if not return default
        String path;
        if(chosenFile != null) {
            path = chosenFile.getPath();
            clearMenuItemOnAction(event);
            try {
                BufferedReader br;
                br = new BufferedReader(new FileReader(chosenFile));
                String line = br.readLine();
                while(line != null){
                    String[] splitLine = line.split("\\s+");
                    RepairModel repairModel = new RepairModel(splitLine[0],
                            Integer.parseInt(splitLine[1]),
                            splitLine[2],
                            Double.parseDouble(splitLine[3]),
                            splitLine[4]);
                    outputTextArea.appendText(repairModel.toString2());
                    RepairModel.repairers.add(repairModel);
                    line = br.readLine();
                }
                br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong." + "\n"
                        + "Check input data.");
                alert.showAndWait();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Success!");
            alert.showAndWait();
        }
        else {
            //default return value
            path = null;
        }
    }

    @FXML
    private void saveMenuItemOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Extention filter
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("DAT files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extentionFilter);

        //Set to user directory or go to default if cannot access
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        //Choose the file
        File savedFile = fileChooser.showSaveDialog(null);

        //Make sure a file was saved, if not return default
        String path;
        if(savedFile != null) {
            path = savedFile.getPath();

            try {
                BufferedWriter wr;
                wr = new BufferedWriter(new FileWriter(savedFile));
                for (RepairModel i: RepairModel.repairers) {
                    wr.write(i.toString());
                }
                wr.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong." + "\n"
                        + "Check input data.");
                alert.showAndWait();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Success!");
            alert.showAndWait();
        }
        else {
            //default return value
            path = null;
        }
    }

    @FXML
    private void clearMenuItemOnAction(ActionEvent event) {
        RepairModel.repairers.clear();
        for (Node node : inputGridPane.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
        }
        for (Node node : outputGridPane.getChildren()) {
            if (node instanceof TextArea) {
                ((TextArea) node).setText("");
            }
        }
    }

    @FXML
    private void copyMenuItemOnAction(ActionEvent event) throws Exception {
        if (RepairModel.repairers.isEmpty() == false) {
            String string = RepairModel.repairers.get(RepairModel.repairers.size() - 1).toString2();
            StringSelection stringSelection = new StringSelection(string);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Nothing to copy.");
            alert.showAndWait();
        }

    }

    @FXML
    private void findMenuItemOnAction(ActionEvent event) throws Exception {
        try {
            ChoiceDialog choiceDialog = new ChoiceDialog(RepairModel.categories[0], RepairModel.categories);
            choiceDialog.setTitle("Choice");
            choiceDialog.setHeaderText("Select a Category:");
            choiceDialog.setContentText("Category:");
            choiceDialog.showAndWait();

            if (choiceDialog.getResult() == null) {
                return;
            }
            else {
                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setTitle("Input");
                textInputDialog.setHeaderText("Enter your querry:");
                textInputDialog.setContentText("Querry:");
                textInputDialog.showAndWait();
                if (textInputDialog.getResult() == null) {
                    return;
                }
                else {
                    String category = choiceDialog.getSelectedItem().toString();
                    String querry = textInputDialog.getEditor().getText();
                    StringBuilder result = new StringBuilder();
                    for (RepairModel i: RepairModel.repairers) {
                        if (i.toString2().contains(category) && i.toString2().contains(querry)) {
                            result.append(i.toString2());
                        }
                    }
                    if (result.isEmpty() == false) {
                        Stage stage = new Stage();

                        TextArea textArea = new TextArea();
                        BorderPane.setAlignment(textArea, Pos.CENTER);
                        textArea.appendText(result.toString());
                        textArea.setEditable(false);

                        BorderPane root = new BorderPane(textArea);

                        Scene scene = new Scene(root, 320, 150);

                        stage.setTitle("Result");
                        stage.setScene(scene);
                        stage.show();
                    }
                    else if (result.isEmpty() == true
                            || textInputDialog.getEditor().getText() == ""
                            || textInputDialog.getEditor().getText() == " ") {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Message");
                        alert.setHeaderText(null);
                        alert.setContentText("No results");
                        alert.showAndWait();
                    }
                }
            }
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong." + "\n"
                    + "Check input data.");
            alert.showAndWait();
        }
    }

    @FXML
    private void wordMenuItemOnAction(ActionEvent event) throws Exception {
        try {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Input");
            textInputDialog.setHeaderText("Enter your first word:");
            textInputDialog.setContentText("Querry:");
            textInputDialog.showAndWait();

            if (textInputDialog.getResult() == null) {
                return;
            }
            else {
                TextInputDialog textInputDialog2 = new TextInputDialog();
                textInputDialog2.setTitle("Input");
                textInputDialog2.setHeaderText("Enter your second word:");
                textInputDialog2.setContentText("Querry:");
                textInputDialog2.showAndWait();
                if (textInputDialog2.getResult() == null) {
                    return;
                }
                else {
                    String querry = textInputDialog.getEditor().getText();
                    String querry2 = textInputDialog2.getEditor().getText();
                    Boolean result = false;
                    for (RepairModel i: RepairModel.repairers) {
                        if (i.getName().contains(querry)) {
                            i.setName(querry2);
                            result = true;
                        }
                        else if (i.getRepairType().contains(querry))
                        {
                            i.setRepairType(querry2);
                            result = true;
                        }
                        else if (i.getRepairer().contains(querry))
                        {
                            i.setRepairer(querry2);
                            result = true;
                        }
                    }

                    if (result == true)
                    {
                        outputTextArea.setText("");

                        for (RepairModel rm: RepairModel.repairers) {
                            outputTextArea.appendText(rm.toString2());
                        }

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Success!");
                        alert.showAndWait();
                    }
                    else if (result == false)
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Message");
                        alert.setHeaderText(null);
                        alert.setContentText("No results");
                        alert.showAndWait();
                    }
                }
            }
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong." + "\n"
                    + "Check input data.");
            alert.showAndWait();
        }
    }

    @FXML
    private void exitMenuItemOnAction (ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void mediumMenuItemOnAction(ActionEvent event) {
        outputTextArea.setFont(Font.font(12));
    }

    @FXML
    private void bigMenuItemOnAction(ActionEvent event) {
        outputTextArea.setFont(Font.font(15));
    }

    @FXML
    private void smallMenuItemOnAction(ActionEvent event) {
        outputTextArea.setFont(Font.font(9));
    }

    @FXML
    private void defaultMenuItemOnAction(ActionEvent event) {
        outputTextArea.setStyle("-fx-font-weight: normal");
        outputTextArea.setStyle("-fx-font-style: normal");
    }

    @FXML
    private void boldMenuItemOnAction(ActionEvent event) {
        outputTextArea.setStyle("-fx-font-weight: bold");
    }

    @FXML
    private void italicMenuItemOnAction(ActionEvent event) {
        outputTextArea.setStyle("-fx-font-style: italic");
    }

    @FXML
    private void blackMenuItemOnAction(ActionEvent event) {
        outputTextArea.setStyle("-fx-text-fill: black");
    }

    @FXML
    private void grayMenuItemOnAction(ActionEvent event) {
        outputTextArea.setStyle("-fx-text-fill: gray");
    }

    @FXML
    private void aboutMenuItemOnAction(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("About");
        alert.setContentText("Repair Management" + "\n" +
                "Artur Zhadan" + "\n" +
                "2021");
        alert.showAndWait();
    }

}
