package utkrishtdhankar.projectneptune.TaskStatusPackage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by utkrishtdhankar on 15/11/16.
 */

public class Scheduled implements TaskStatus {
    private static final String name = "Scheduled";

    private Calendar scheduledForDate;

    public Scheduled(Calendar date) {
        scheduledForDate = date;
    }

    public Calendar getScheduledForDate() {
        return scheduledForDate;
    }

    public void setScheduledForDate(Calendar scheduledForDate) {
        this.scheduledForDate = scheduledForDate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSpecial() {
        return scheduledForDate.toString();
    }

    @Override
    public String encode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-mm");
        return name + " " + sdf.format(scheduledForDate);
    }
}
