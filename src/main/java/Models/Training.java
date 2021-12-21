package Models;

import java.time.LocalDateTime;

public interface Training {

    int workingDays = 5;
    int startHour = 10;
    int endHour = 18;
    int workingHours = 8;

    int calculateTime(LocalDateTime now);
}
