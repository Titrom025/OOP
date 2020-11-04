package Task_3_2.RecordBook;

import java.util.Arrays;

public class RecordBook {
    private static final int NUMBER_OF_SEMESTERS_IN_YEAR = 2;

    private final int yearsOfStudy;
    private final int numberOfSemestersWithGrades;

    private final Semester[] semesters;
    private int currentSemesterInput = 0;
    private int finalWorkGrade = -1;

    public RecordBook(int yearsOfStudy, int numberOfSemestersWithGrades) {
        this.yearsOfStudy = yearsOfStudy;
        this.numberOfSemestersWithGrades = numberOfSemestersWithGrades;
        this.semesters = new Semester[yearsOfStudy * NUMBER_OF_SEMESTERS_IN_YEAR];
    }

    public void addSemesterGrades(int[] grades) {
        if (currentSemesterInput < numberOfSemestersWithGrades) {
            semesters[currentSemesterInput++] = new Semester(grades);
        } else {
            throw new IllegalStateException("Unexpected state: " + "All semesters with grades are already full");
        }
    }

    public void addSemesterGrades(int numberOfgrades) {
        if (currentSemesterInput < numberOfSemestersWithGrades) {
            throw new IllegalStateException("Unexpected state: " + "Not all semesters with grades are full");
        } else if (currentSemesterInput < yearsOfStudy * NUMBER_OF_SEMESTERS_IN_YEAR) {
            semesters[currentSemesterInput++] = new Semester(numberOfgrades);
        } else {
            throw new IllegalStateException("Unexpected state: " + "All semesters are already full");
        }
    }

    public void setFinalWorkGrade(int grade) {
        finalWorkGrade = grade;
    }

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

    public boolean isAbleToGetHonorsDegree() {
        return (finalWorkGrade == 5 || finalWorkGrade == -1) && hasOnlyGoodGrades()
                && potentialExcellentGradesPercent() >= 0.75;
    }

    public boolean isAbleToGetIncreasedStipend() {
        return  currentSemesterInput >= numberOfSemestersWithGrades &&
                numberOfSemestersWithGrades < yearsOfStudy * NUMBER_OF_SEMESTERS_IN_YEAR &&
                semesters[numberOfSemestersWithGrades - 1].getAverageGrade() == 5.0;
    }

    private boolean hasOnlyGoodGrades() {
        int semestersCount = yearsOfStudy * NUMBER_OF_SEMESTERS_IN_YEAR;

        for (int i = 0; i < semestersCount; i++) {
            if (semesters[i].getBadGradesCount() > 0) {
                return false;
            }
        }

        return true;
    }

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
