package olseng.ea.bitvectest;

import olseng.ea.fitness.FitnessObjective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Olav on 22.01.2016.
 */
public class LeadingOnesObjective implements FitnessObjective<IntVec> {
    @Override
    public float evaluate(IntVec phenotype) {
        List<Integer> values = phenotype.getRepresentation();
        float count = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == 1) {
                count++;
            }
            else {
                break;
            }
        }

        return count / (float) phenotype.getRepresentation().size();
    }

    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>(Arrays.asList(1,1,1,0,0));
        float count = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == 1) {
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
