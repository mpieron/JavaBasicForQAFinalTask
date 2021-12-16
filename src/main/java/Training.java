public interface Training {

    static final int workingDays = 5;
    static final int startHour = 10;
    static final int endHour = 18;
    static final int workingHours = 8;

    public String calculateToComplete();
    public String calculateCompleted();
}
