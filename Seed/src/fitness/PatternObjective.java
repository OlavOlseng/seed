package fitness;

import genetics.MelodyContainer;
import genetics.MusicPhenotype;
import olseng.ea.fitness.FitnessObjective;

import java.util.ArrayList;

/**
 * Created by Olav on 09.05.2016.
 */
public class PatternObjective implements FitnessObjective<MusicPhenotype> {

    public double rhythmicWholeBarRepetitions = 0.5;
    public double rhythmicHalfBarUniqueness = 0.8;
    public double rhythmicWholeBarSequenceValue = .7;

    @Override
    public float evaluate(MusicPhenotype p) {
        double fitness = 0;

        //fitness += proximity(rhythmicWholeBarRepetitions, getRhythmicWholeMeasureRepetitions(p));
        fitness += proximity(rhythmicHalfBarUniqueness, getRhythmicHalfMeasureUniqueness(p));
        fitness += proximity(rhythmicWholeBarSequenceValue, getRhythmicWholeBarSequenceRepetitions(p));
        fitness += proximity(rhythmicWholeBarSequenceValue, getRestWholeBarSequenceRepetitions(p));

        return (float)fitness;
    }

    private double getRhythmicWholeMeasureRepetitions(MusicPhenotype p) {
        double barRepetitions = 0;
        double uniqueBars = p.wholeMeasureRhythmicPatterns.size();
        return 1.0 - uniqueBars /p.getRepresentation().bars;
        /*
        for (int barRepeatCount : p.wholeMeasureRhythmicPatterns.values()) {
            barRepetitions += barRepeatCount - 1;
        }
        if (barRepetitions == 0) {
            return 0;
        }
        return barRepetitions / p.getRepresentation().bars;
        */
        //return barRepetitions / (p.getRepresentation().bars - 1);
    }

    private double getRhythmicHalfMeasureUniqueness(MusicPhenotype p) {
        double repeatedPatterns = 0;
        double uniqueBars = p.halfMeasureRhythmicPatterns.size();
        for (int barRepeatCount : p.halfMeasureRhythmicPatterns.values()) {
            if (barRepeatCount > 1) {
                repeatedPatterns++;
            }
        }
        if (repeatedPatterns == 0) {
            return 0;
        }
        //return 1.0 - uniqueBars / ((double) p.getRepresentation().bars * 2.0);
        return 1.0 - repeatedPatterns / (p.getRepresentation().bars * 2.);
        //return barRepetitions / (p.getRepresentation().bars * 2. - 1);
    }

    private double getRhythmicWholeBarSequenceRepetitions(MusicPhenotype p) {
        double positionalBarRepetitions = 0;

        for (int i = 0; i < p.sequentialMeasurePatterns.length - 4; i++) {
            double barRepetitions = 0;
            if (i + 4 < p.sequentialMeasurePatterns.length) {
                if (p.sequentialMeasurePatterns[i] == p.sequentialMeasurePatterns[i + 4]) {
                    positionalBarRepetitions++;
                    continue;
                }
            } else {
                if (i + 2 < p.sequentialMeasurePatterns.length) {
                    if (p.sequentialMeasurePatterns[i] == p.sequentialMeasurePatterns[i + 2]) {
                        barRepetitions++;
                    }
                }
                if (i + 1 < p.sequentialMeasurePatterns.length) {
                    if (p.sequentialMeasurePatterns[i] == p.sequentialMeasurePatterns[i + 1]) {
                        barRepetitions++;
                    }
                }
                if (barRepetitions > 0 && barRepetitions < 2){
                    positionalBarRepetitions++;
                }
            }
        }
        return positionalBarRepetitions / (double) (p.getRepresentation().bars - 4);
    }

    private double getRestWholeBarSequenceRepetitions(MusicPhenotype p) {
        double positionalBarRepetitions = 0;

        for (int i = 0; i < p.sequentialMeasureRestPatterns.length - 4; i++) {
            double barRepetitions = 0;
            if(p.sequentialMeasureRestPatterns[i] == 0) {
                continue;
            }
            if (i + 4 < p.sequentialMeasureRestPatterns.length) {
                if (p.sequentialMeasureRestPatterns[i] == p.sequentialMeasureRestPatterns[i + 4]) {
                    positionalBarRepetitions++;
                    continue;
                }
            } else {
                if (i + 2 < p.sequentialMeasureRestPatterns.length) {
                    if (p.sequentialMeasureRestPatterns[i] == p.sequentialMeasureRestPatterns[i + 2]) {
                        barRepetitions++;
                    }
                }
                if (i + 1 < p.sequentialMeasureRestPatterns.length) {
                    if (p.sequentialMeasureRestPatterns[i] == p.sequentialMeasureRestPatterns[i + 1]) {
                        barRepetitions++;
                    }
                }
                if (barRepetitions > 0 && barRepetitions < 2){
                    positionalBarRepetitions++;
                }
            }
        }
        return positionalBarRepetitions / (double) (p.getRepresentation().bars - 4);
    }

    private double proximityy(double d1, double d2) {
        return Math.abs(d1 - d2) < 0.05 ? 1.0 : Math.abs(d1 - d2) < 0.1 ? 0.5 : 0;
    }

    /**
     * Measures the proximity between two clamped doubles.
     * @param d1
     * @param d2
     * @return 1 minus the absolute difference between the provided parameters.
     */
    private double proximity(double d1, double d2) {
        return 1 - Math.abs(d1 - d2);
    }
}
