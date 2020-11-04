package Task_3_2.RecordBook;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RecordBookTest extends TestCase {

    @Test
    public void testRecordBook1() {
        RecordBook recordBook = new RecordBook();

        recordBook.addSemesterGrades(1, new int[]{5,5,5,5,5,4,5,5});
        recordBook.addSemesterGrades(2, new int[]{5,5,5,5,5,5});

        double averageGrade = recordBook.getAverageGrade();
        Assert.assertTrue(averageGrade >= 4.92 && averageGrade <= 4.93);
        Assert.assertTrue(recordBook.isAbleToGetHonorsDegree());
        Assert.assertTrue(recordBook.isAbleToGetIncreasedStipend());
    }

    @Test
    public void testRecordBook2() {
        Semester[] semesters = new Semester[]{
                new Semester( new Subject[] {
                        new Subject("Введение в алгебру и анализ", 0, true),
                        new Subject("Введение в дискретную математику и математическую логику", 0, true),
                        new Subject("Декларативное программирование", 0, true),
                        new Subject("Императивное программирование", 0, false)},
                        false),
                new Semester( new Subject[] {
                        new Subject("Императивное программирование", 0, true),
                        new Subject("Иностранный язык", 0, true),
                        new Subject("Цифровые платформы", 0, true)},
                        false)};

        RecordBook recordBook = new RecordBook(semesters);

        recordBook.addSemesterGrades(1, new int[]{5,4,5,5});
        recordBook.addSemesterGrades(2, new int[]{5,4,5});

        double averageGrade = recordBook.getAverageGrade();

        Assert.assertTrue(averageGrade >= 4.71 && averageGrade <= 4.72);
        Assert.assertFalse(recordBook.isAbleToGetHonorsDegree());
        Assert.assertFalse(recordBook.isAbleToGetIncreasedStipend());
    }

    @Test
    public void testRecordBook3() {
        RecordBook recordBook = new RecordBook();
        Assert.assertFalse(recordBook.isAbleToGetHonorsDegree());
        recordBook.setFinalWorkGrade(4);
        Assert.assertFalse(recordBook.isAbleToGetHonorsDegree());
    }

    @Test
    public void testRecordBook4() {
        RecordBook recordBook = new RecordBook();

        recordBook.addSemesterGrades(1, new int[]{5,5,5,5,5,4,5,5});
        recordBook.addSemesterGrades(2, new int[]{5,5,5,5,5,5});
        recordBook.addSemesterGrades(3, new int[]{5,5,5,5,5,4,5,5});
        recordBook.addSemesterGrades(4, new int[]{5,5,5,5,5,5,5,5,5});
        recordBook.addSemesterGrades(5, new int[]{5,5,5,5,5,5,4,5,5});
        recordBook.addSemesterGrades(6, new int[]{5,5,5,5,4,4,4,5,5});
        recordBook.addSemesterGrades(7, new int[]{5,5,5,5,5,4,5});
        recordBook.addSemesterGrades(8, new int[]{5,5,5,5,5,5,5});

        recordBook.setFinalWorkGrade(4);
        Assert.assertFalse(recordBook.isAbleToGetHonorsDegree());
        recordBook.setFinalWorkGrade(5);
        
        double averageGrade = recordBook.getAverageGrade();
        Assert.assertTrue(averageGrade >= 4.88 && averageGrade <= 4.89);
        Assert.assertTrue(recordBook.isAbleToGetHonorsDegree());
        Assert.assertFalse(recordBook.isAbleToGetIncreasedStipend());
    }
}