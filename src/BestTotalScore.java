import java.util.ArrayList;

class BestTotalScore implements Strategy {
    @Override
    public Double getBestScore(ArrayList<Grade> grades) {
        Double bestScore = 0.0;
        for (Grade grade : grades) {
            if (grade.getTotal() > bestScore) {
                bestScore = grade.getTotal();
            }
        }
        return bestScore;
    }
    @Override
    public Double getScore(Grade grade) {
        return grade.getTotal();
    }
}