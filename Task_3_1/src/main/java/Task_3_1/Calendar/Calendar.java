package Task_3_1.Calendar;

import java.util.Objects;

public class Calendar implements Comparable<Calendar>{
    private static final int DAYS_IN_THE_WEEK = 7;
    private static final int DAYS_IN_THE_COMMON_YEAR = 365;
    private static final int DAYS_IN_THE_INTERCALARY_YEAR = 366;

    private static final int MINIMUM_YEAR_IN_DATE = 1900;
    private static final int MINIMUM_MONTH_IN_DATE = 1;
    private static final int MAXIMUM_MONTH_IN_DATE = 12;
    private static final int MINIMUM_DAY_IN_DATE = 1;
    private static final int MAXIMUM_DAY_IN_DATE = 31;

    private int day;
    private final Month month;
    private final Year year;

    /**
     * The main initialization of the Calendar, which checks the correction of fields
     * @param day - current day
     * @param month - current month
     * @param year - current year
     */
    public Calendar(int day, int month, int year) {
        if (year < MINIMUM_YEAR_IN_DATE) {
            throw new IllegalArgumentException("Unexpected value: " + year + " - less than 1900");
        } else {
            this.year = new Year(year);
        }
        if (month < MINIMUM_MONTH_IN_DATE || month > MAXIMUM_MONTH_IN_DATE) {
            throw new IllegalArgumentException("Unexpected value: " + day + " - invalid month index");
        } else {
            this.month = new Month(month);
        }
        if (day < MINIMUM_DAY_IN_DATE || day > this.month.getMonthDayCount(this.year)) {
            throw new IllegalArgumentException("Unexpected value: " + day + " - invalid day of month" + this.month + " of year " + year);
        } else {
            this.day = day;
        }
    }

    public int getDateDay() {
        return day;
    }

    public int getDateMonth() {
        return month.getMonth();
    }

    public int getDateYear() {
        return year.getYear();
    }

    public int getDaysInMonth() {
        return month.getDaysLeftInMonth(1, year);
    }

    public int getDaysInYear() {
        return year.isYearIntercalary() ? DAYS_IN_THE_INTERCALARY_YEAR : DAYS_IN_THE_COMMON_YEAR;
    }

    public int getDaysBeforeMonthEnd() {
        return month.getDaysLeftInMonth(day, year);
    }

    public int getDaysSinceYearBeg() {
        int days = 0;
        for (int i = 1; i < month.getMonth(); i++) {
            days += new Month(i).getMonthDayCount(year);
        }
        days += day;

        return days;
    }

    public int getDaysBeforeYearEnd() {
        return getDaysInYear() - getDaysSinceYearBeg() + 1;
    }

    /**
     * The function gets the day of the week of current date
     * @return the value of type enum Day_Val
     */
    public Weekdays getDayOfWeek() {
        int dayNumber = 0;
        Calendar startDate = new Calendar(MINIMUM_DAY_IN_DATE, MINIMUM_MONTH_IN_DATE, MINIMUM_YEAR_IN_DATE);

        while (startDate.year.getYear() < this.year.getYear()) {
            dayNumber += startDate.getDaysInYear();
            startDate.nextYear();
        }

        while (startDate.month.getMonth() < this.month.getMonth()) {
            dayNumber += startDate.getDaysInMonth();
            startDate.nextMonth();
        }

        dayNumber += this.day;

        return Weekdays.values()[dayNumber % 7];
    }

    /**
     * THe function compares current date and parameter "date" and calls
     * the function "getTimeElapsedFromDate" from bigger of two dates
     * @param date - date to find a difference with the current date
     * @return new Calendar, which is the difference of the two dates
     */
    public Calendar getDateDifference(Calendar date) {
        if (this.compareTo(date) > 0) {
            return this.getTimeElapsedFromDate(date);
        } else {
            return date.getTimeElapsedFromDate(this);
        }
    }

    /**
     * The function retuns the date, which will be after skipping 'daysToSkip" days in the current date
     * @param daysToSkip - days to skip in the current date
     * @return new Calendar, which is the reuslt of skipping "daysToSkip" days from current date
     */
    public Calendar getDateAfterDays(int daysToSkip) {
        Calendar date = new Calendar(this.day, this.month.getMonth(), this.year.getYear());

        while (daysToSkip > 0) {
            int monthDaysCount = date.getDaysBeforeMonthEnd();
            int yearDaysCount = date.getDaysBeforeYearEnd();

            if (daysToSkip >= yearDaysCount) {
                daysToSkip -= yearDaysCount;
                date.nextYear();
            } else if (daysToSkip >= monthDaysCount) {
                daysToSkip -= monthDaysCount;
                date.nextMonth();
            } else {
                date.day += daysToSkip;
                daysToSkip = 0;
            }
        }

        return date;
    }

    /**
     * The function retuns the day of the week, which will be after skipping "daysToSkip" days in the current date
     * @param daysToSkip - days to skip in the current date
     * @return day of the week of type enum Day_Val
     */
    public Weekdays getWeekdayAfterDays(int daysToSkip) {
        Calendar date = getDateAfterDays(daysToSkip);
        return date.getDayOfWeek();
    }

    /**
     * The function retuns the month, which will be after skipping "weeksToSkip" weeks in the current date
     * @param weeksToSkip - weeks to skip in the current date
     * @return month  of type enum Month_Val
     */
    public Months getMonthAfterWeeks(int weeksToSkip) {
        int daysToSkip = weeksToSkip * DAYS_IN_THE_WEEK;
        Calendar date = getDateAfterDays(daysToSkip);

        return Months.values()[date.month.getMonth() - 1];
    }

    /**
     * The function finds the next date, where date.day = parameter "day" and weekday of the date = parameter "weekday"
     * @param day - the day of the required date
     * @param weekday - the weekday of the required date
     * @return new date where day of the date = parameter "day" and weekday of the date = parameter "weekday"
     */
    public Calendar findNextDateWithDayAndWeekday(int day, Weekdays weekday) {
        if (day > MAXIMUM_DAY_IN_DATE || day < MINIMUM_DAY_IN_DATE) {
            throw new IllegalArgumentException("Unexpected value: " + day + " - invalid day of month");
        }

        Calendar date = new Calendar(this.day, this.month.getMonth(), this.year.getYear());

        if (day >= date.day && day <= date.getDaysInMonth()) {
            date.day = day;
            if (date.getDayOfWeek() == weekday) {
                return date;
            }
        }

        Weekdays currentWeekday = date.getDayOfWeek();
        while (currentWeekday != weekday) {
            date.nextMonth();
            date.day = day;
            if (date.day <= date.getDaysInMonth()) {
                currentWeekday = date.getDayOfWeek();
            }
        }

        return date;
    }

    /**
     * The function finds time (in days, months, years) from parameter "date" (always less or equal then current date) to current date
     * @param date - date to find time from
     * @return - new Calendar, which is the difference between dates
     */
    public Calendar getTimeElapsedFromDate(Calendar date) {
        Calendar startDate = new Calendar(date.day, date.month.getMonth(), date.year.getYear());
        Calendar counter = new Calendar();

        if (startDate.day > this.day) {
            counter.day += startDate.getDaysBeforeMonthEnd();
            startDate.nextMonth();
        }
        counter.day += this.day - startDate.day;

        while (startDate.month.getMonth() != this.month.getMonth()) {
            counter.month.setMonth(counter.month.getMonth() + 1);
            if (counter.month.getMonth() == MAXIMUM_MONTH_IN_DATE) {
                counter.year.setYear(counter.year.getYear() + 1);
                counter.month.setMonth(0);
            }
            startDate.nextMonth();
        }

        while (startDate.year.getYear() < this.year.getYear()) {
            counter.year.setYear(counter.year.getYear() + 1);
            startDate.nextYear();
        }

        return counter;
    }

    @Override
    public String toString() {
        return day + "/" + month.getMonth() + "/" + year.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calendar calendar = (Calendar) o;
        return  day == calendar.day &&
                month.getMonth() == calendar.month.getMonth() &&
                year.getYear() == calendar.year.getYear();
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    @Override
    public int compareTo(Calendar date) {
        if (this == date) {
            return 0;
        }

        if (this.year.getYear() < date.year.getYear()) {
            return -1;
        } else if (this.year.getYear() > date.year.getYear()) {
            return 1;
        } else {
            if (this.month.getMonth() < date.month.getMonth()) {
                return -1;
            } else if (this.month.getMonth() > date.month.getMonth()) {
                return 1;
            } else {
                if (this.day < date.day) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

    /**
     * The addition initialization of Calendar (by zero value),
     * which is used in inner realization of the class to support counter
     *
     */
    private Calendar() {
        this.day = 0;
        this.month = new Month(0);
        this.year = new Year(0);
    }

    private void nextMonth() {
        this.day = 1;
        if (month.nextMonth()) {
            this.nextYear();
        }
    }

    private void nextYear() {
        this.day = 1;
        this.month.setMonth(1);
        year.nextYear();
    }
}

