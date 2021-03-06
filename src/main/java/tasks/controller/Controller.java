package tasks.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import tasks.domain.Task;
import tasks.service.DateService;
import tasks.repository.TaskRepository;
import tasks.service.TasksService;
import tasks.utils.Utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 The Description :
    The main controller of the gui, also contains service logic and the list of objects
 */
public class Controller {
    private static final Logger log = Logger.getLogger(Controller.class.getName());
    static Stage editNewStage;
    static Stage infoStage;
    static TableView<Task> mainTable;
    private ObservableList<Task> tasksList;
    @FXML
    private TableView<Task> tasks;
    private TasksService service;
    private DateService dateService;
    @FXML
    private TableColumn<Task, String> columnTitle;
    @FXML
    private TableColumn<Task, String> columnTime;
    @FXML
    private TableColumn<Task, String> columnRepeated;
    @FXML
    private Label labelCount;
    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private TextField fieldTimeFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private TextField fieldTimeTo;

    void setService(TasksService service){
        this.service = service;
        this.dateService = new DateService(service);
        this.tasksList = service.getObservableList();
        updateCountLabel(tasksList);
        tasks.setItems(tasksList);
        mainTable = tasks;

        tasksList.addListener((ListChangeListener.Change<? extends Task> c) -> {
                    updateCountLabel(tasksList);
                    tasks.setItems(tasksList);
                }
        );
    }

    @FXML
    public void initialize(){
        log.info("Main controller initializing");
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("formattedDateStart"));
        columnRepeated.setCellValueFactory(new PropertyValueFactory<>("formattedRepeated"));
    }

    private void updateCountLabel(ObservableList<Task> list){
        labelCount.setText(list.size()+ " elements");
    }

    @FXML
    public void showTaskDialog(ActionEvent actionEvent){
        Object source = actionEvent.getSource();
        NewEditController.setClickedButton((Button) source);

        try {
            editNewStage = new Stage();
            NewEditController.setCurrentStage(editNewStage);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/new-edit-task.fxml"));
            Parent root = loader.load();
            NewEditController editCtrl = loader.getController();
            editCtrl.setService(service);
            editCtrl.setTasksList(tasksList);
            editCtrl.setCurrentTask(mainTable.getSelectionModel().getSelectedItem());
            editNewStage.setScene(new Scene(root, 600, 350));
            editNewStage.setResizable(false);
            editNewStage.initOwner(Main.primaryStage);
            editNewStage.initModality(Modality.APPLICATION_MODAL);
            editNewStage.show();
        }
        catch (IOException e){
            log.error("Error loading new-edit-task.fxml");
        }
    }

    @FXML
    public void deleteTask(){
        Task toDelete = tasks.getSelectionModel().getSelectedItem();
        tasksList.remove(toDelete);
        TaskRepository.rewriteFile(tasksList);
    }

    @FXML
    public void showDetailedInfo(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/fxml/task-info.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root, 550, 350));
            stage.setResizable(false);
            stage.setTitle("Info");
            stage.initModality(Modality.APPLICATION_MODAL);
            infoStage = stage;
            stage.show();
        }
        catch (IOException e){
            log.error("error loading task-info.fxml");
        }
    }

    @FXML
    public void showFilteredTasks(){
        try{
            Date start = getDateFromFilterField(datePickerFrom.getValue(), fieldTimeFrom.getText());
            Date end = getDateFromFilterField(datePickerTo.getValue(), fieldTimeTo.getText());

            Iterable<Task> filtered =  service.filterTasks(start, end);

            ObservableList<Task> observableTasks = FXCollections.observableList((ArrayList)filtered);
            tasks.setItems(observableTasks);
            updateCountLabel(observableTasks);
        } catch (RuntimeException e) {
            new Utils().showValidationBox(e);
        }
    }

    private Date getDateFromFilterField(LocalDate localDate, String time){
        Date date = dateService.getDateValueFromLocalDate(localDate);
        return dateService.getDateMergedWithTime(time, date);
    }

    @FXML
    public void resetFilteredTasks(){
        tasks.setItems(tasksList);
    }
}
