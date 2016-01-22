package olseng.ea.bitvectest;

import olseng.ea.fitness.FitnessObjective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Olav on 22.01.2016.
 */
public class TrailingZeroesObjective implements FitnessObjective<IntVec> {
    @Override
    public float evaluate(IntVec phenotype) {
        List<Integer> values = phenotype.getRepresentation();
        int count = 0;
        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i) == 0) {
                count++;
            }
            else {
                break;
            }
        }
        return count / (float)(values.size());
    }

    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>(Arrays.asList(1,0,0,0,0));
        float count = 0;
        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i) == 0) {
                count++;
            }
            else {
                break;
            }
        }

        float score = count / (float) (values.size());
        System.out.println(score);
    }
}
