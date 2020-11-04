package Task_3_2.RecordBook;

public class RecordBook {
    private final Semester[] semesters;
    private final int semestersCount;
    private int finalWorkGrade = -1;

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
    public void addSemesterGrades(int semesterNumber, int[] grades) {
        if (semesters[semesterNumber - 1].numberOfGrades == grades.length) {
            semesters[semesterNumber - 1].setGrades(grades);
        } else {
            throw new IllegalArgumentException("Unexpected grades array: length is incorrect");
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
        double gradesSum = 0;
        int gradesCount = 0;
        for (int i = 0; i < semestersCount; i++) {
            if (semesters[i].isFilled) {
                gradesSum += semesters[i].getGradesSum();
                gradesCount += semesters[i].numberOfGrades;
            }
        }

        return gradesSum / gradesCount;
    }

    /**
     * The function checks the ability to get honors degree
     * @return true, if it is possible to get honors degree
     */
    public boolean isAbleToGetHonorsDegree() {
        return (finalWorkGrade == 5 || finalWorkGrade == -1) && hasOnlyGoodFinalGrades()
                && potentialExcellentGradesPercent() >= 0.75;
    }

    /**
     * The function checks the last known semester with grades for average grade 5.0
     * @return true, if it is possible to receive increased stipend (if all semesters were passed, false returns)
     */
    public boolean isAbleToGetIncreasedStipend() {
        int lastSemester = -1;
        for (int i = 0; i < semestersCount; i++) {
            if (semesters[i].isFilled) {
                lastSemester = i;
            } else {
                break;
            }
        }
        return  lastSemester >= 0 && lastSemester < semestersCount - 1 &&
                (semesters[lastSemester].getGradesSum() / semesters[lastSemester].numberOfGrades) == 5.0;
    }

    /**
     * The function checks, if the student has grades less than 4
     * @return true, if the student has only good grades
     */
    private boolean hasOnlyGoodFinalGrades() {
        for (int i = 0; i < semestersCount; i++) {
            if (semesters[i].getBadFinalGradesCount() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return (a maximum possible percent of excellent grades)/100
     */
    private double potentialExcellentGradesPercent() {
        int excellentGradesCount = 0;
        int totalGradesCount = 0;

        for (int i = 0; i < semestersCount; i++) {
            excellentGradesCount += semesters[i].getExcellentFinalGradesCount();
            totalGradesCount += semesters[i].getFinalGradesCount();
        }

        return (double) excellentGradesCount / totalGradesCount;
    }
}
