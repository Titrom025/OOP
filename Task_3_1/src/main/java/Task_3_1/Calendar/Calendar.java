package Task_3_1.Calendar;

public class Calendar {
    private int day;
    private final Month month;
    private final Year year;

    public Calendar(int day, int month, int year) {
        if (year < 1900) {
            throw new IllegalArgumentException("Unexpected value: " + year + " - less than 1900");
        } else {
            this.year = new Year(year);
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Unexpected value: " + day + " - invalid month index");
        } else {
            this.month = new Month(month);
        }
        if (day < 1 || day > this.month.getMonthDayCount(this.year)) {
            throw new IllegalArgumentException("Unexpected value: " + day + " - invalid day of month" + this.month + " of year " + year);
        } else {
            this.day = day;
        }
    }

    public int getDaysInYear() {
       return year.isYearIntercalary() ? 366 : 365;
    }

    public int daysSinceYearBeg() {
        int days = 0;
        for (int i = 1; i < month.getMonth(); i++) {
            days += new Month(i).getMonthDayCount(year);
        }
        days += day;

        return days;
    }

    public int daysBeforeYearEnd() {
        return getDaysInYear() - daysSinceYearBeg() + 1;
    }

    public int daysBeforeMonthEnd() {
        return month.getDaysLeftInMonth(day, year);
    }

    public int getDaysInMonth() {
        return month.getDaysLeftInMonth(1, year);
    }

    public boolean nextMonth() {
        this.day = 1;
        return month.nextMonth();
    }

    public void nextYear() {
        this.day = 1;
        this.month.setMonth(1);
        year.nextYear();
    }

    public Day_Val getDayOfWeek() {
        int dayNumber = 0;
        Calendar startDate = new Calendar(1, 1, 1900);

        while (startDate.year.getYear() < this.year.getYear()) {
            dayNumber += startDate.getDaysInYear();
            startDate.nextYear();
        }

        while (startDate.month.getMonth() < this.month.getMonth()) {
            dayNumber += startDate.getDaysInMonth();
            startDate.nextMonth();
        }

        dayNumber += this.day;

        return Day_Val.values()[dayNumber % 7];
    }

    public Calendar dateAfterDays(int daysToSkip) {
        Calendar date = new Calendar(this.day, this.month.getMonth(), this.year.getYear());

        while (daysToSkip > 0) {
            int monthDaysCount = date.daysBeforeMonthEnd();
            int yearDaysCount = date.daysBeforeYearEnd();

            if (daysToSkip >= yearDaysCount) {
                daysToSkip -= yearDaysCount;
                date.nextYear();
            } else if (daysToSkip >= monthDaysCount) {
                daysToSkip -= monthDaysCount;
                if (date.nextMonth()) {
                    date.nextYear();
                }
            } else {
                date.day += daysToSkip;
                daysToSkip = 0;
            }
        }

        return date;
    }

    public Day_Val weekdayAfter(int daysToSkip) {
        Calendar date = dateAfterDays(daysToSkip);
        return date.getDayOfWeek();
    }

    public Month_Val monthAfterWeeks(int weeksToSkip) {
        int daysToSkip = weeksToSkip * 7;
        Calendar date = dateAfterDays(daysToSkip);

        return Month_Val.values()[date.month.getMonth() - 1];
    }

    @Override
    public String toString() {
        return day + "/" + month.getMonth() + "/" + year.getYear();
    }
}

