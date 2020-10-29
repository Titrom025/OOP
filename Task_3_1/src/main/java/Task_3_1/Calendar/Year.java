package Task_3_1.Calendar;

/**
 * Class Year contains id of year "year" and methods for working with years
 */
public class Year {
    private int year;

    Year(int year) {
        setYear(year);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void nextYear() {
        this.year++;
    }

    /**
     * The method checks if the current year is intercalary
     * @return true, if the year is intercalary
     */
    public boolean isYearIntercalary() {
        if (year % 400 == 0) {
            return true;
        } else if (year % 4 == 0) {
            return year % 100 != 0;
        }
        return false;
    }
}
