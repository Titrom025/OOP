package Task_3_2.RecordBook;

import java.util.Arrays;

public class RecordBook {
    private static final int NUMBER_OF_SEMESTERS_IN_YEAR = 2;

    private final int yearsOfStudy;
    private final int numberOfSemestersWithGrades;

    private final Semester[] semesters;
    private int currentSemesterInput = 0;
    private int finalWorkGrade = -1;

    /**
     * Class RecordBook initializer
     * @param yearsOfStudy - number of years/courses of student
     * @param numberOfSemestersWithGrades - number of semesters, which have all grades
     */
    public RecordBook(int yearsOfStudy, int numberOfSemestersWithGrades) {
        this.yearsOfStudy = yearsOfStudy;
        this.numberOfSemestersWithGrades = numberOfSemestersWithGrades;
        this.semesters = new Semester[yearsOfStudy * NUMBER_OF_SEMESTERS_IN_YEAR];
    }

    /**
     * The function adds grades to semester if it's number less then the number of semesters,
     * which contain all grades
     * @param grades - grades for semester
     */
    public void addSemesterGrades(int[] grades) {
        if (currentSemesterInput < numberOfSemestersWithGrades) {
            semesters[currentSemesterInput++] = new Semester(grades);
        } else {
            throw new IllegalStateException("Unexpected state: " + "All semesters with grades are already full");
        }
    }

    /**
     * The function sets count of grades to semester, which doesn't have grades yet
     * @param numberOfGrades - number of grades in semester
     */
    public void addSemesterGrades(int numberOfGrades) {
        if (currentSemesterInput < numberOfSemestersWithGrades) {
            throw new IllegalStateException("Unexpected state: " + "Not all semesters with grades are full");
        } else if (currentSemesterInput < yearsOfStudy * NUMBER_OF_SEMESTERS_IN_YEAR) {
            semesters[currentSemesterInput++] = new Semester(numberOfGrades);
        } else {
            throw new IllegalStateException("Unexpected state: " + "All semesters are already full");
        }
    }

    /**
     * The function sets the grade for final study work
     * @param grade - grade for final study work
     */
    public void setFinalWorkGrade(int grade) {
        finalWorkGrade = grade;
    }

    /**
     * The function calculates the average grade of semesters, which have all grades
     * @return - an average of semesters with grades
     */
    public double getAverageGrade() {
        if (currentSemesterInput < numberOfSemestersWithGrades) {
            throw new IllegalStateException("Unexpected state: " + "Not all semesters with grades are full");
        }

        if (numberOfSemestersWithGrades > 0) {
            double gradesSum = 0;
            int gradesCount = 0;
            for (int i = 0; i < numberOfSemestersWithGrades; i++) {
                gradesSum += Arrays.stream(semesters[i].grades).sum();
                gradesCount += semesters[i].numberOfGrades;
            }

            return gradesSum / gradesCount;
        } else {
            return 0;
        }
    }

    /**
     * The function checks the ability to get honors degree
     * @return true, if it is possible to get honors degree
     */
    public boolean isAbleToGetHonorsDegree() {
        return (finalWorkGrade == 5 || finalWorkGrade == -1) && hasOnlyGoodGrades()
                && potentialExcellentGradesPercent() >= 0.75;
    }

    /**
     * The function checks the last known semester with grades for average grade 5.0
     * @return true, if it is possible to receive increased stipend
     */
    public boolean isAbleToGetIncreasedStipend() {
        return  currentSemesterInput >= numberOfSemestersWithGrades &&
                numberOfSemestersWithGrades < yearsOfStudy * NUMBER_OF_SEMESTERS_IN_YEAR &&
                semesters[numberOfSemestersWithGrades - 1].getAverageGrade() == 5.0;
    }

    /**
     * The function checks, if the student has grades less than 4
     * @return true, if the student has only good grades
     */
    private boolean hasOnlyGoodGrades() {
        int semestersCount = yearsOfStudy * NUMBER_OF_SEMESTERS_IN_YEAR;

        for (int i = 0; i < semestersCount; i++) {
            if (semesters[i].getBadGradesCount() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return (a maximum possible percent of excellent grades)/100
     */
    private double potentialExcellentGradesPercent() {
        int semestersCount = yearsOfStudy * NUMBER_OF_SEMESTERS_IN_YEAR;

        int excellentGradesCount = 0;
        int totalGradesCount = 0;

        for (int i = 0; i < semestersCount; i++) {
            excellentGradesCount += semesters[i].getExcellentGradesCount();
            totalGradesCount += semesters[i].numberOfGrades;
        }

        return (double) excellentGradesCount / totalGradesCount;
    }

}
