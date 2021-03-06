package tasks.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import tasks.domain.Task;
import tasks.service.DateService;
import tasks.repository.TaskRepository;
import tasks.service.TasksService;
import tasks.utils.Utils;
import java.time.LocalDate;
import java.util.Date;

/**
 The Description :
    The controller of the edit window displayed during the Modifying functionality
 */
public class NewEditController {

    private static Button clickedButton;

    private static final Logger log = Logger.getLogger(NewEditController.class.getName());

    public static void setClickedButton(Button clickedButton) {
        NewEditController.clickedButton = clickedButton;
    }

    public static void setCurrentStage(Stage currentStage) {
        NewEditController.currentStage = currentStage;
    }

    private static Stage currentStage;

    private Task currentTask;
    private ObservableList<Task> tasksList;
    private TasksService service;
    private DateService dateService;

    private boolean incorrectInputMade;
    @FXML
    private TextField fieldTitle;
    @FXML
    private DatePicker datePickerStart;
    @FXML
    private TextField txtFieldTimeStart;
    @FXML
    private DatePicker datePickerEnd;
    @FXML
    private TextField txtFieldTimeEnd;
    @FXML
    private TextField fieldInterval;
    @FXML
    private CheckBox checkBoxActive;
    @FXML
    private CheckBox checkBoxRepeated;

    private static final String DEFAULT_START_TIME = "8:00";
    private static final String DEFAULT_END_TIME = "10:00";
    private static final String DEFAULT_INTERVAL_TIME = "0:30";

    public void setTasksList(ObservableList<Task> tasksList){
        this.tasksList = tasksList;
    }

    public void setService(TasksService service){
        this.service = service;
        this.dateService = new DateService(service);
    }

    public void setCurrentTask(Task task){
        this.currentTask = task;
        switch (clickedButton.getId()){
            case  "btnNew" : initNewWindow();
                break;
            case "btnEdit" : initEditWindow();
                break;
            default:
                break;
        }
    }

    @FXML
    public void initialize(){
        log.info("new/edit window initializing");
    }

    private void initNewWindow(){
        currentStage.setTitle("New Task");
        datePickerStart.setValue(LocalDate.now());
        txtFieldTimeStart.setText(DEFAULT_START_TIME);
    }

    private void initEditWindow(){
        currentStage.setTitle("Edit Task");
        if(currentTask != null) {
            fieldTitle.setText(currentTask.getTitle());
            datePickerStart.setValue(DateService.getLocalDateValueFromDate(currentTask.getStartTime()));
            txtFieldTimeStart.setText(dateService.getTimeOfTheDayFromDate(currentTask.getStartTime()));

            if (currentTask.isRepeated()) {
                checkBoxRepeated.setSelected(true);
                hideRepeatedTaskModule(false);
                datePickerEnd.setValue(DateService.getLocalDateValueFromDate(currentTask.getEndTime()));
                fieldInterval.setText(service.getIntervalInHours(currentTask));
                txtFieldTimeEnd.setText(dateService.getTimeOfTheDayFromDate(currentTask.getEndTime()));
            }
            if (currentTask.isActive()) {
                checkBoxActive.setSelected(true);

            }
        }
    }

    @FXML
    public void switchRepeatedCheckbox(ActionEvent actionEvent){
        CheckBox source = (CheckBox)actionEvent.getSource();
        if (source.isSelected()){
            hideRepeatedTaskModule(false);
        }
        else if (!source.isSelected()){
            hideRepeatedTaskModule(true);
        }
    }

    private void hideRepeatedTaskModule(boolean toShow){
        datePickerEnd.setDisable(toShow);
        fieldInterval.setDisable(toShow);
        txtFieldTimeEnd.setDisable(toShow);

        datePickerEnd.setValue(LocalDate.now());
        txtFieldTimeEnd.setText(DEFAULT_END_TIME);
        fieldInterval.setText(DEFAULT_INTERVAL_TIME);
    }

    @FXML
    public void saveChanges(){
        Task collectedFieldsTask = collectFieldsData();
        if (incorrectInputMade) return;

        if (currentTask == null){
            tasksList.add(collectedFieldsTask);
        }
        else {
            for (int i = 0; i < tasksList.size(); i++){
                if (currentTask.equals(tasksList.get(i))){
                    tasksList.set(i,collectedFieldsTask);
                }
            }
            currentTask = null;
        }
        TaskRepository.rewriteFile(tasksList);
        Controller.editNewStage.close();
    }

    @FXML
    public void closeDialogWindow(){
        Controller.editNewStage.close();
    }

    private Task collectFieldsData(){
        incorrectInputMade = false;
        Task result = null;
        try {
            result = makeTask();
        }
        catch (RuntimeException e){
            incorrectInputMade = true;
            new Utils().showValidationBox(e);
        }
        return result;
    }

    private Task makeTask(){
        Task result;
        String newTitle = fieldTitle.getText();
        Date startDateWithNoTime = dateService.getDateValueFromLocalDate(datePickerStart.getValue());//ONLY date!!without time
        Date newStartDate = dateService.getDateMergedWithTime(txtFieldTimeStart.getText(), startDateWithNoTime);
        if (checkBoxRepeated.isSelected()){
            Date endDateWithNoTime = dateService.getDateValueFromLocalDate(datePickerEnd.getValue());
            Date newEndDate = dateService.getDateMergedWithTime(txtFieldTimeEnd.getText(), endDateWithNoTime);
            int newInterval = service.parseFromStringToSeconds(fieldInterval.getText());
            if (newStartDate.after(newEndDate)) throw new IllegalArgumentException("Start date should be before end");
            result = new Task(newTitle, newStartDate,newEndDate, newInterval);
        }
        else {
            result = new Task(newTitle, newStartDate);
        }
        boolean isActive = checkBoxActive.isSelected();
        result.setActive(isActive);
        log.info(result);
        return result;
    }
}
