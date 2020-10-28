package Task_3_1.Calendar;

public class Month {
    private int month;

    Month(int month) {
        setMonth(month);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean nextMonth() {
        if (month < 12) {
            month++;
            return false;
        } else {
            month = 1;
            return true;
        }
    }

    public int getMonthDayCount(Year year) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> year.isYearIntercalary() ? 29 : 28;
            default -> throw new IllegalStateException("Unexpected value: " + month);
        };
    }

    public int getDaysLeftInMonth(int currentDay, Year year) {
        int daysInMonth = getMonthDayCount(year);
        if (currentDay > daysInMonth || currentDay < 1) {
            throw new IllegalArgumentException("Unexpected value: " + currentDay + " in month " + month);
        }
        return daysInMonth - currentDay + 1;
    }


}
