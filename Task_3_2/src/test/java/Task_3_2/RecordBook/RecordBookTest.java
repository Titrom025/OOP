package Task_3_2.RecordBook;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RecordBookTest extends TestCase {

    @Test
    public void testRecordBook1() {
        RecordBook recordBook = new RecordBook();

        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE, Grade.FIVE, Grade.FIVE, Grade.FIVE,
                                                 Grade.FIVE, Grade.FOUR, Grade.FIVE, Grade.FIVE});
        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE, Grade.FIVE, Grade.FIVE,
                                                 Grade.FIVE,Grade.FIVE,Grade.FIVE});

        double averageGrade = recordBook.getAverageGrade();
        Assert.assertTrue(averageGrade >= 4.92 && averageGrade <= 4.93);
        Assert.assertTrue(recordBook.isAbleToGetHonorsDegree());
        Assert.assertTrue(recordBook.isAbleToGetIncreasedStipend());
    }

    @Test
    public void testRecordBook2() {
        Semester[] semesters = new Semester[]{
                new Semester( new Subject[] {
                        new Subject("Введение в алгебру и анализ", Grade.UNDEFINED, true),
                        new Subject("Введение в дискретную математику и математическую логику", Grade.UNDEFINED, true),
                        new Subject("Декларативное программирование", Grade.UNDEFINED, true),
                        new Subject("Императивное программирование", Grade.UNDEFINED, false)},
                        false),
                new Semester( new Subject[] {
                        new Subject("Императивное программирование", Grade.UNDEFINED, true),
                        new Subject("Иностранный язык", Grade.UNDEFINED, true),
                        new Subject("Цифровые платформы", Grade.UNDEFINED, true)},
                        false)};

        RecordBook recordBook = new RecordBook(semesters);

        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FOUR,Grade.FIVE,Grade.FIVE});
        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FOUR,Grade.FIVE});

        double averageGrade = recordBook.getAverageGrade();

        Assert.assertTrue(averageGrade >= 4.71 && averageGrade <= 4.72);
        Assert.assertFalse(recordBook.isAbleToGetHonorsDegree());
        Assert.assertFalse(recordBook.isAbleToGetIncreasedStipend());
    }

    @Test
    public void testRecordBook3() {
        RecordBook recordBook = new RecordBook();
        Assert.assertTrue(recordBook.isAbleToGetHonorsDegree());
        recordBook.setFinalWorkGrade(Grade.FOUR);
        Assert.assertFalse(recordBook.isAbleToGetHonorsDegree());
    }

    @Test
    public void testRecordBook4() {
        RecordBook recordBook = new RecordBook();

        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FIVE,Grade.FIVE,Grade.FIVE,
                                                 Grade.FIVE,Grade.FOUR,Grade.FIVE,Grade.FIVE});
        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FIVE,Grade.FIVE,
                                                 Grade.FIVE,Grade.FIVE,Grade.FIVE});
        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FIVE,Grade.FIVE,Grade.FIVE,
                                                 Grade.FIVE,Grade.FOUR,Grade.FIVE,Grade.FIVE});
        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FIVE,Grade.FIVE,Grade.FIVE,
                                                 Grade.FIVE,Grade.FIVE,Grade.FIVE,Grade.FIVE,Grade.FIVE});
        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FIVE,Grade.FIVE,Grade.FIVE,
                                                 Grade.FIVE,Grade.FIVE,Grade.FOUR,Grade.FIVE,Grade.FIVE});
        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FIVE,Grade.FIVE,Grade.FIVE,
                                                 Grade.FOUR,Grade.FOUR,Grade.FOUR,Grade.FIVE,Grade.FIVE});
        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FIVE,Grade.FIVE,Grade.FIVE,
                                                 Grade.FIVE,Grade.FOUR,Grade.FIVE});
        recordBook.addSemesterGrades(new Grade[]{Grade.FIVE,Grade.FIVE,Grade.FIVE,Grade.FIVE,
                                                 Grade.FIVE,Grade.FIVE,Grade.FIVE});

        recordBook.setFinalWorkGrade(Grade.FOUR);
        Assert.assertFalse(recordBook.isAbleToGetHonorsDegree());
        recordBook.setFinalWorkGrade(Grade.FIVE);

        double averageGrade = recordBook.getAverageGrade();
        Assert.assertTrue(averageGrade >= 4.88 && averageGrade <= 4.89);
        Assert.assertTrue(recordBook.isAbleToGetHonorsDegree());
        Assert.assertFalse(recordBook.isAbleToGetIncreasedStipend());
    }
}