package tasks.repository;

import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import tasks.controller.Main;
import tasks.domain.Task;
import java.io.*;
import java.util.Date;

/**
 The Description :
    Class that is used to save in memory and load from memory the data ( in binary or text form )
    It also contains some service methods for some unknown reason
 */
public class TaskRepository {
    private TaskRepository(){}

    private static final Logger log = Logger.getLogger(TaskRepository.class.getName());

    private static void write(TaskList tasks, OutputStream out) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(out)) {
            dataOutputStream.writeInt(tasks.size());
            for (Task t : tasks) {
                dataOutputStream.writeInt(t.getTitle().length());
                dataOutputStream.writeUTF(t.getTitle());
                dataOutputStream.writeBoolean(t.isActive());
                dataOutputStream.writeInt(t.getRepeatInterval());
                if (t.isRepeated()) {
                    dataOutputStream.writeLong(t.getStartTime().getTime());
                    dataOutputStream.writeLong(t.getEndTime().getTime());
                } else {
                    dataOutputStream.writeLong(t.getTime().getTime());
                }
            }
        }
    }

    private static void read(TaskList tasks, InputStream in)throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(in)) {
            int listLength = dataInputStream.readInt();
            for (int i = 0; i < listLength; i++) {
                dataInputStream.readInt();
                String title = dataInputStream.readUTF();
                boolean isActive = dataInputStream.readBoolean();
                int interval = dataInputStream.readInt();
                Date startTime = new Date(dataInputStream.readLong());
                Task taskToAdd;
                if (interval > 0) {
                    Date endTime = new Date(dataInputStream.readLong());
                    taskToAdd = new Task(title, startTime, endTime, interval);
                } else {
                    taskToAdd = new Task(title, startTime);
                }
                taskToAdd.setActive(isActive);
                tasks.add(taskToAdd);
            }
        }
    }

    private static void writeBinary(TaskList tasks, File file) throws IOException{
        try (FileOutputStream fos = new FileOutputStream(file)) {
            write(tasks, fos);
        }
    }

    public static void readBinary(TaskList tasks, File file) throws IOException{
        try (FileInputStream fis = new FileInputStream(file)) {
            read(tasks, fis);
        }
    }

    public static void rewriteFile(ObservableList<Task> tasksList) {
        LinkedTaskList taskList = new LinkedTaskList();
        for (Task t : tasksList){
            taskList.add(t);
        }
        try {
            TaskRepository.writeBinary(taskList, Main.savedTasksFile);
        }
        catch (IOException e){
            log.error("IO exception reading or writing file");
        }
    }
}