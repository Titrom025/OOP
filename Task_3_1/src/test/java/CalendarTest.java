import Task_3_1.Calendar.*;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CalendarTest extends TestCase{

    @Test
    public void testCalendar1() {
        Calendar currentDate = new Calendar(28, 10, 2020);
        Assert.assertEquals(Day_Val.FRIDAY, currentDate.weekdayAfter(1024));
    }

    @Test
    public void testCalendar2() {
//        Calendar currentDate = new Calendar(28, 10, 2020);
//        Assert.assertEquals(Day_Val.FRIDAY, currentDate.weekdayAfter(1024));
    }

    @Test
    public void testCalendar3() {
        Calendar currentDate = new Calendar(15, 2, 2001);
        Assert.assertEquals(Day_Val.THURSDAY, currentDate.getDayOfWeek());
    }

    @Test
    public void testCalendar4() {
        Calendar currentDate = new Calendar(28, 10, 2020);
        Assert.assertEquals(Month_Val.FEBRUARY, currentDate.monthAfterWeeks(14));
    }

    @Test
    public void testCalendar5() {
        Calendar currentDate = new Calendar(28, 10, 2020);
        Assert.assertEquals(65, currentDate.daysBeforeYearEnd());
    }

    @Test
    public void testCalendar6() {
//        Calendar currentDate = new Calendar(28, 10, 2020);
//        Assert.assertEquals(65, currentDate.daysBeforeYearEnd());
    }

}