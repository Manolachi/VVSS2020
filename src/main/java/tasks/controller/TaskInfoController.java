package tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tasks.domain.Task;
import org.apache.log4j.Logger;

/**
 The Description :
    The controller of the information window displayed
 */
public class TaskInfoController {

    private static final Logger log = Logger.getLogger(TaskInfoController.class.getName());
    @FXML
    private Label labelTitle;
    @FXML
    private Label labelStart;
    @FXML
    private Label labelEnd;
    @FXML
    private Label labelInterval;
    @FXML
    private Label labelIsActive;

    @FXML
    public void initialize(){
        log.info("task info window initializing");
        Task currentTask = Controller.mainTable.getSelectionModel().getSelectedItem();
        if(currentTask != null)
        {
            labelTitle.setText("Title: " + currentTask.getTitle());
            labelStart.setText("Start time: " + currentTask.getFormattedDateStart());
            labelEnd.setText("End time: " + currentTask.getFormattedDateEnd());
            labelInterval.setText("Interval: " + currentTask.getFormattedRepeated());
            labelIsActive.setText("Is active: " + (currentTask.isActive() ? "Yes" : "No"));
        }
    }

    @FXML
    public void closeWindow(){
        Controller.infoStage.close();
    }
}