package Task_3_2.RecordBook;

import java.util.Arrays;

public class RecordBook {
    private final Semester[] semesters;
    private final int semestersCount;
    private Grade finalWorkGrade = Grade.UNDEFINED;
    private int currentSemester = 0;

    /**
     * Standart initializer of NSU FIT student's record book
     */
    RecordBook() {
        semesters = new SubjectsDatabase().semesters;
        this.semestersCount = 8;
    }

    /**
     * Initializer for custom record book
     * @param semesters - custom record book
     */
    RecordBook(Semester[] semesters) {
        this.semesters = semesters;
        this.semestersCount = semesters.length;
    }

    /**
     * The function adds grades to semester if it's number less then the number of semesters,
     * which contain all grades
     * @param grades - grades for semester
     */
    public void addSemesterGrades(Grade[] grades) {
        if (semesters[currentSemester].numberOfGrades == grades.length) {
            semesters[currentSemester++].setGrades(grades);

        } else {
            throw new IllegalArgumentException("Unexpected grades array: length is incorrect");
        }
    }

    /**
     * The function sets the grade for final study work
     * @param grade - grade for final study work
     */
    public void setFinalWorkGrade(Grade grade) {
        finalWorkGrade = grade;
    }

    /**
     * The function calculates the average grade of semesters, which have all grades
     * @return - an average of semesters with grades
     */
    public double getAverageGrade() {
        double gradesSum = Arrays.stream(semesters).map(Semester::getGradesSum).mapToInt(Integer::intValue).sum();
        int gradesCount = Arrays.stream(semesters).map(Semester::getNumberOfGrades).mapToInt(Integer::intValue).sum();

        return gradesSum / gradesCount;
    }

    /**
     * The function checks the ability to get honors degree
     * @return true, if it is possible to get honors degree
     */
    public boolean isAbleToGetHonorsDegree() {
        return (finalWorkGrade.ordinal() == 5 || finalWorkGrade.ordinal() == 0) && hasOnlyGoodFinalGrades()
                && potentialExcellentGradesPercent() >= 0.75;
    }

    /**
     * The function checks the last known semester with grades for average grade 5.0
     * @return true, if it is possible to receive increased stipend (if all semesters were passed, false returns)
     */
    public boolean isAbleToGetIncreasedStipend() {
        return  currentSemester >= 0 && currentSemester < semestersCount - 1 &&
                ((double) semesters[currentSemester - 1].getGradesSum() / semesters[currentSemester - 1].getNumberOfGrades()) == 5.0;
    }

    /**
     * The function checks, if the student has grades less than 4
     * @return true, if the student has only good grades
     */
    private boolean hasOnlyGoodFinalGrades() {
        return Arrays.stream(semesters).map(x -> x.getBadFinalGradesCount() > 0 ? 1 : 0).mapToInt(Integer::intValue).sum() == 0;
    }

    /**
     * @return (a maximum possible percent of excellent grades)/100
     */
    private double potentialExcellentGradesPercent() {
        int excellentGradesCount = Arrays.stream(semesters).map(Semester::getExcellentFinalGradesCount).mapToInt(Integer::intValue).sum();
        int totalGradesCount = Arrays.stream(semesters).map(Semester::getFinalGradesCount).mapToInt(Integer::intValue).sum();

        return (double) excellentGradesCount / totalGradesCount;
    }
}
