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

    /**
     * Pitches with rests between them are not counted as intervals
     */
    public ArrayList<ArrayList<Integer>> melodyIntervals = new ArrayList<>();
    public ArrayList<ArrayList<Byte>> melodyPitches = new ArrayList<>();

    public Map<Integer, Integer> wholeMeasureRhythmicPatterns;
    public Map<Integer, Integer> halfMeasureRhythmicPatterns;
    public Map<Integer, Integer> wholeMeasureRestPatterns;
    public int[] sequentialMeasurePatterns;
    public int[] sequentialHalfMeasurePatterns;
    public int[] sequentialMeasureRestPatterns;


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
        this.sequentialMeasureRestPatterns = new int[genotype.getData().bars];

    }

    @Override
    public boolean isEqualTo(Phenotype p) {
        if (!(p instanceof MusicPhenotype)) {
            return false;
        }
        MusicPhenotype p2 = (MusicPhenotype) p;
        if (pitchPositions.size() != p2.pitchPositions.size()) {
            return false;
        }
        else if (restPositions.size() != p2.restPositions.size()) {
            return false;
        }
        else {
            //Check for rest deviations
            for (int i = 0; i < restPositions.size(); i++) {
                if (restPositions.get(i) != p2.restPositions.get(i)) {
                    return false;
                }
            }
            //Check pitch position deviations
            for (int i = 0; i < pitchPositions.size(); i++) {
                if (pitchPositions.get(i) != p2.pitchPositions.get(i)) {
                    return false;
                }
            }
            //Check for deviations in pitches
            for (int i = 0; i < melodyPitches.size(); i++) {
                ArrayList<Byte> measure = melodyPitches.get(i);
                for (int j = 0; j < measure.size(); j++) {
                    if (measure.get(j) != p2.melodyPitches.get(i).get(j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return /**getRepresentation().toString() +**/ " -> " + Arrays.toString(this.fitnessValues) + ", Rank: " + getRank() + ", CD: " + crowdingDistance;
    }

}
