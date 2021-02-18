package Task_3_1.Calendar;

/**
 * Class Month contains id of month "month" and methods for working with months
 */
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

    /**
     * Set next month after current
     * @return True, if there is a new year (when month 12 is setted to month 1)
     */
    public boolean nextMonth() {
        if (month < 12) {
            month++;
            return false;
        } else {
            month = 1;
            return true;
        }
    }

    /**
     * Rwturns the number of the days in current month for given year
     * @param year - year when this month is checking
     * @return number of days in the month
     */
    public int getMonthDayCount(Year year) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> year.isYearIntercalary() ? 29 : 28;
            default -> throw new IllegalStateException("Unexpected value: " + month);
        };
    }

    /**
     * The function returns the number of the days to the end of the month
     * @param currentDay - current day of the month
     * @param year - year when this month is checking
     * @return the number of the days to the end of the month
     */
    public int getDaysLeftInMonth(int currentDay, Year year) {
        int daysInMonth = getMonthDayCount(year);
        if (currentDay > daysInMonth || currentDay < 1) {
            throw new IllegalArgumentException("Unexpected value: " + currentDay + " in month " + month);
        }
        return daysInMonth - currentDay + 1;
    }


}
