import Task_3_1.Calendar.*;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CalendarTest extends TestCase{

    @Test
    public void testCalendar1() {
        Calendar currentDate = new Calendar(28, 10, 2020);
        Assert.assertEquals(Day_Val.FRIDAY, currentDate.getWeekdayAfterDays(1024));
    }

    @Test
    public void testCalendar2() {
        Calendar currentDate = new Calendar(28, 10, 2020);
        Calendar dateToFindDiff = new Calendar(9, 5, 1945);

        Calendar foundedAnswer = currentDate.getDateDifference(dateToFindDiff);

        Assert.assertEquals(foundedAnswer.getDateDay(), 19);
        Assert.assertEquals(foundedAnswer.getDateMonth(), 5);
        Assert.assertEquals(foundedAnswer.getDateYear(), 75);
    }

    @Test
    public void testCalendar3() {
        Calendar birthday = new Calendar(15, 2, 2001);
        Assert.assertEquals(Day_Val.THURSDAY, birthday.getDayOfWeek());
    }

    @Test
    public void testCalendar4() {
        Calendar currentDate = new Calendar(28, 10, 2020);
        Assert.assertEquals(Month_Val.FEBRUARY, currentDate.getMonthAfterWeeks(14));
    }

    @Test
    public void testCalendar5() {
        Calendar currentDate = new Calendar(28, 10, 2020);
        Assert.assertEquals(65, currentDate.getDaysBeforeYearEnd());
    }

    @Test
    public void testCalendar6() {
        Calendar currentDate = new Calendar(28, 10, 2020);

        Calendar answer = new Calendar(13, 11, 2020);
        Calendar foundedAnswer = currentDate.findNextDateWithDayAndWeekday(13, Day_Val.FRIDAY);

        Assert.assertEquals(answer, foundedAnswer);
    }

}