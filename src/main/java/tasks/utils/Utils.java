package tasks.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.io.IOException;

public class Utils {
    private static final String[] TIME_ENTITY = {" day"," hour", " minute"," second"};
    private static final int SECONDS_IN_DAY = 86400;
    private static final int SECONDS_IN_HOUR = 3600;
    private static final int SECONDS_IN_MIN = 60;
    private static final Logger log = Logger.getLogger(Utils.class);

    public static String getFormattedInterval(int interval){
        if (interval <= 0) throw new IllegalArgumentException("Interval <= 0");
        StringBuilder sb = new StringBuilder();

        int days = interval/ SECONDS_IN_DAY;
        int hours = (interval - SECONDS_IN_DAY *days) / SECONDS_IN_HOUR;
        int minutes = (interval - (SECONDS_IN_DAY *days + SECONDS_IN_HOUR *hours)) / SECONDS_IN_MIN;
        int seconds = (interval - (SECONDS_IN_DAY *days + SECONDS_IN_HOUR *hours + SECONDS_IN_MIN *minutes));

        int[] time = new int[]{days, hours, minutes, seconds};
        int i = 0;
        int j = time.length-1;
        while (time[i] == 0 || time[j] == 0){
            if (time[i] == 0) i++;
            if (time[j] == 0) j--;
        }

        for (int k = i; k <= j; k++){
            sb.append(time[k]);
            sb.append(time[k] > 1 ? TIME_ENTITY[k]+ "s" : TIME_ENTITY[k]);
            sb.append(" ");
        }
        return sb.toString();
    }

    public void showValidationBox(RuntimeException e) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/field-validator.fxml"));
            stage.setScene(new Scene(root, 350, 150));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            Label lblData = (Label) root.lookup("#Label");
            if (lblData != null) lblData.setText(e.getMessage());

            stage.show();
        }
        catch (IOException ioe){
            log.error("error loading field-validator.fxml");
        }
    }
}