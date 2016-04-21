package genetics;

import olseng.ea.genetics.Phenotype;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Olav on 02.03.2016.
 */
public class MusicPhenotype extends Phenotype<MusicalContainer, MusicGenotype> {

    public ArrayList<ArrayList<Integer>> melodyIntervals = new ArrayList<>();
    public ArrayList<ArrayList<Byte>> melodyPitches = new ArrayList<>();

    public Map<Integer, Integer> wholeMeasureRhythmicPatterns;
    public Map<Integer, Integer> halfMeasureRhythmicPatterns;
    public int[] sequentialMeasurePatterns;
    public int[] sequentialHalfMeasurePatterns;

    //Sorted from shorthest to longest duration.
    public final int[] pitchDurations = new int[16];
    public final int[] restDurations = new int[16];

    public ArrayList<Integer> restPositions = new ArrayList<>();
    public ArrayList<Integer> pitchPositions = new ArrayList<>();
    public ArrayList<Integer> sequentialPitchesDurations = new ArrayList<>();

    public MusicPhenotype(MusicGenotype genotype) {
        super(genotype);
        for (int i = 0; i < genotype.getData().bars; i++) {
            melodyIntervals.add(new ArrayList<>());
            melodyPitches.add(new ArrayList<>());
        }
        this.sequentialMeasurePatterns = new int[genotype.getData().bars];
        this.sequentialHalfMeasurePatterns = new int[genotype.getData().bars * 2];
    }

    @Override
    public String toString() {
        return /**getRepresentation().toString() +**/ " -> " + Arrays.toString(this.fitnessValues) + ", Rank: " + getRank() + ", CD: " + crowdingDistance;
    }

}
