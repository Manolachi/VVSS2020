package tasks.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import tasks.domain.Notificator;
import tasks.repository.ArrayTaskList;
import tasks.repository.TaskList;
import tasks.repository.TaskRepository;
import tasks.service.TasksService;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 The Description :
    The main class that imports data and then starts up the gui of the program
 */
public class Main extends Application {
    public static Stage primaryStage;
    private static final int DEFAULT_WIDTH = 820;
    private static final int DEFAULT_HEIGHT = 520;

    private static final Logger log = Logger.getLogger(Main.class.getName());

    private final TaskList savedTasksList = new ArrayTaskList();

    private static final ClassLoader classLoader = Main.class.getClassLoader();
    public static final File savedTasksFile = new File(Objects.requireNonNull(classLoader.getResource("data/tasks.txt")).getFile());

    private TasksService service;

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("saved data reading");
        if (savedTasksFile.length() != 0) {
            TaskRepository.readBinary(savedTasksList, savedTasksFile);
        }
        try {
            log.info("application start");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent root = loader.load();
            Controller ctrl= loader.getController();
            service = new TasksService(savedTasksList);

            ctrl.setService(service);
            primaryStage.setTitle("Task Manager");
            primaryStage.setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
            primaryStage.setMinWidth(DEFAULT_WIDTH);
            primaryStage.setMinHeight(DEFAULT_HEIGHT);
            primaryStage.show();
        }
        catch (IOException e){
            log.error("error reading main.fxml");
        }
        primaryStage.setOnCloseRequest(we -> System.exit(0));
        new Notificator(FXCollections.observableArrayList(service.getObservableList())).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
