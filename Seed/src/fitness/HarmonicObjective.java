package fitness;

import genetics.MusicPhenotype;
import olseng.ea.fitness.FitnessObjective;

/**
 * Created by Olav on 28.04.2016.
 */
public class HarmonicObjective implements FitnessObjective<MusicPhenotype> {



    @Override
    public float evaluate(MusicPhenotype phenotype) {
        return 0;
    }

    private double containsTriads(MusicPhenotype p) {
        double score = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            
        }
        return score;
    }

}
