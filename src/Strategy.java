import java.util.ArrayList;

interface Strategy {
    Double getBestScore(ArrayList<Grade> grades);
    Double getScore(Grade grade);
}