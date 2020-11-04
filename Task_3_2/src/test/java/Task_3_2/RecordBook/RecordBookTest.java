package Task_3_2.RecordBook;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RecordBookTest extends TestCase {

    @Test
    public void testRecordBook1() {
        RecordBook recordBook = new RecordBook(4, 2);

        recordBook.addSemesterGrades(new int[]{5,5,5,5,4,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,5,5});
        recordBook.addSemesterGrades(8);
        recordBook.addSemesterGrades(9);
        recordBook.addSemesterGrades(9);
        recordBook.addSemesterGrades(8);
        recordBook.addSemesterGrades(8);
        recordBook.addSemesterGrades(9);

        double averageGrade = recordBook.getAverageGrade();
        Assert.assertTrue(averageGrade >= 4.91 && averageGrade <= 4.92);
        Assert.assertTrue(recordBook.isAbleToGetHonorsDegree());
        Assert.assertTrue(recordBook.isAbleToGetIncreasedStipend());

    }

    @Test
    public void testRecordBook2() {
        RecordBook recordBook = new RecordBook(4, 6);

        recordBook.addSemesterGrades(new int[]{5,5,5,5,4,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,5,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,4,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,5,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,4,3});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,5,5});
        recordBook.addSemesterGrades(8);
        recordBook.addSemesterGrades(9);

        double averageGrade = recordBook.getAverageGrade();
        Assert.assertTrue(averageGrade >= 4.86 && averageGrade <= 4.87);
        Assert.assertFalse(recordBook.isAbleToGetHonorsDegree());
        Assert.assertTrue(recordBook.isAbleToGetIncreasedStipend());
    }

    @Test
    public void testRecordBook3() {
        RecordBook recordBook = new RecordBook(4, 8);

        recordBook.addSemesterGrades(new int[]{5,5,5,5,4,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,5,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,4,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,5,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,4,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,5,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,4,5});
        recordBook.addSemesterGrades(new int[]{5,5,5,5,5,5});
        recordBook.setFinalWorkGrade(4);

        double averageGrade = recordBook.getAverageGrade();
        Assert.assertTrue(averageGrade >= 4.91 && averageGrade <= 4.92);
        Assert.assertFalse(recordBook.isAbleToGetHonorsDegree());
        Assert.assertFalse(recordBook.isAbleToGetIncreasedStipend());

        recordBook.setFinalWorkGrade(5);
        Assert.assertTrue(recordBook.isAbleToGetHonorsDegree());
    }
}