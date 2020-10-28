package Task_3_1.Calendar;

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

    public boolean isYearIntercalary() {
        if (year % 400 == 0) {
            return true;
        } else if (year % 4 == 0) {
            return year % 100 != 0;
        }
        return false;
    }
}
