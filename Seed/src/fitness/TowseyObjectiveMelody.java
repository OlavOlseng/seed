package fitness;

import genetics.MelodyContainer;
import genetics.MusicPhenotype;
import olseng.ea.fitness.FitnessObjective;

import java.util.ArrayList;

/**
 * Created by Olav on 03.03.2016.
 */
public class TowseyObjectiveMelody implements FitnessObjective<MusicPhenotype> {

    //Tonality Features
    public double pitchVarietyValue = 0.3;
    public double pitchRangeValue = 0.6;
    public double keyCenteredValue = 0.4; //
    public double nonScalePitchQuantaValue = 0.01;
    public double dissonantIntervalsValue = 0.05;

    //Contour features
    public double contourDirection = 0.5;
    public double contourStability = 0.6;
    public double stepMovement = 0.5;
    public double leapReturns = 0.8; //
    public double climaxStrength = 0.1; //

    //Rhythmic features
    public double noteDensity = 0.25;
    public double restDensity = 0.1; //
    public double rhythmicVariety = 0.5;
    public double rhythmicRange = 0.4;
    public double syncopation = 0.05;

    //Pattern features
    public double repeatedPitches = .2;
    public double repeatedTimings = 0.5;
    public double rhythmicWholeBarRepetitions = 0.5;
    public double rhythmicHalfBarUniqueness = 0.8;
    public double rhythmicWholeBarSequenceValue = 1.;
    public double onBeatPitchCoverage = 0.8;

    @Override
    public float evaluate(MusicPhenotype p) {
        double fitness = 0;
        /*

        fitness += proximity(keyCenteredValue, getKeyCenteredValue(p));
        fitness += proximity(dissonantIntervalsValue, getDissonantIntervalsValue(p));

        fitness += proximity(contourDirection, getContourDirection(p));
        //fitness += proximity(climaxStrength, getClimaxStrength(p));

        */
        fitness += proximity(pitchVarietyValue, getPitchVarietyValue(p));
        fitness += proximity(pitchRangeValue, getPitchRangeValue(p));

        //fitness += proximity(stepMovement, getStepMovement(p));
        fitness += proximity(nonScalePitchQuantaValue, getNonScalePitchQuantaValue(p));
        fitness += proximity(contourStability, getContourStability(p));

        fitness += proximity(noteDensity, getNoteDensity(p)) * 2.0;
        fitness += proximity(restDensity, getRestDensity(p)) * 2.0;
        fitness += proximity(rhythmicVariety, getRhythmicVariety(p));
        fitness += proximity(rhythmicRange, getRhythmicRange(p));
        fitness += proximity(syncopation, getSyncopation(p));

        fitness += proximity(repeatedPitches, getRepeatedPitches(p));
        fitness += proximity(repeatedTimings, getRepeatedTimings(p));
        fitness += proximity(onBeatPitchCoverage, getOnBeatPitchCoverage(p));


        //fitness += proximity(rhythmicWholeBarRepetitions, getRhythmicWholeMeasureRepetitions(p));
        fitness += proximity(rhythmicHalfBarUniqueness, getRhythmicHalfMeasureUniqueness(p));
        fitness += proximity(rhythmicWholeBarSequenceValue, getRhythmicWholeBarSequenceRepetitions(p));
        fitness += proximity(rhythmicWholeBarSequenceValue, getRestWholeBarSequenceRepetitions(p));

        return (float) fitness;
    }

    private double getPitchVarietyValue(MusicPhenotype p) {
        ArrayList<Byte> distinctPitches = new ArrayList<>();
        double pitchCount = 0;
        for (ArrayList<Byte> measure : p.melodyPitches) {
            for (byte pitch : measure) {
                pitchCount++;
                if (!distinctPitches.contains(pitch)) {
                    distinctPitches.add(pitch);
                }
            }
        }
        if (pitchCount == 0) {
            return 0;
        }
        return (double) distinctPitches.size() / pitchCount;
    }

    private double getPitchRangeValue(MusicPhenotype p) {
        double pitchMin = MelodyContainer.MELODY_RANGE_MAX + 1;
        double pitchMax = MelodyContainer.MELODY_RANGE_MIN - 1;
        double pitchCount = 0;
        for (ArrayList<Byte> measure : p.melodyPitches) {
            for (byte pitch : measure) {
                pitchCount++;
                if (pitch < pitchMin) {
                    pitchMin = pitch;
                }
                if (pitch > pitchMax) {
                    pitchMax = pitch;
                }
            }
        }
        return pitchCount > 0 ? (pitchMax - pitchMin) / (double) MelodyContainer.MELODY_RANGE : 0;
    }

    private double getKeyCenteredValue(MusicPhenotype p) {
        double primaryPitchQuanta = 0;
        double pitchCount = 0;
        for (ArrayList<Byte> measure : p.melodyPitches) {
            for (int pitch : measure) {
                pitch = pitch % 12;
                pitchCount++;
                if (pitch == p.getRepresentation().key.scale[0] || pitch == p.getRepresentation().key.scale[4]) {
                    primaryPitchQuanta++;
                }
            }
        }
        if (pitchCount == 0) {
            return 0;
        }
        return primaryPitchQuanta / pitchCount;
    }

    private double getNonScalePitchQuantaValue(MusicPhenotype p) {
        double nonScalePitchQuanta = 0;
        double pitchCount = 0;
        for (ArrayList<Byte> measure : p.melodyPitches) {
            for (int pitch : measure) {
                pitch = pitch % 12;
                pitchCount++;
                if (p.getRepresentation().key.pitchInKey(pitch) == -1) {
                    nonScalePitchQuanta++;
                }
            }
        }
        if (pitchCount == 0) {
            return 0;
        }
        return nonScalePitchQuanta / pitchCount;
    }

    private double getDissonantIntervalsValue(MusicPhenotype p) {
        double dissonanceRating = 0;
        double intervalCount = 0;
        for (ArrayList<Integer> measure : p.melodyIntervals) {
            for (int interval : measure) {
                intervalCount++;
                if (interval == 6 || interval == 11 || interval >= 13) {
                    dissonanceRating += 1;
                }
                else if (interval == 10) {
                    dissonanceRating += 0.5;
                }
            }
        }
        if (intervalCount == 0) {
            return 0;
        }
        return dissonanceRating / intervalCount;
    }

    private double getContourDirection(MusicPhenotype p) {
        double contourDirection = 0;
        double intervalCount = 0;
        for (ArrayList<Integer> measure : p.melodyIntervals) {
            for (int interval : measure) {
                intervalCount++;
                if (interval >= 0) {
                    contourDirection += 1;
                }
            }
        }
        if (intervalCount == 0) {
            return 0;
        }
        return contourDirection / intervalCount;
    }

    private double getContourStability(MusicPhenotype p) {
        double contourStability = 0;
        double intervalCount = 0;
        for (ArrayList<Integer> measure : p.melodyIntervals) {
            int previousInterval = 0;
            for (int interval : measure) {
                intervalCount++;
                if(intervalCount == 0) {
                    previousInterval = interval;
                    continue;
                }
                if (Math.signum(interval) == Math.signum(previousInterval) || interval == 0) {
                    contourStability += 1.0;
                }
                previousInterval = interval;
            }
        }
        if (intervalCount - 1.0 == 0) {
            return 0;
        }
        return contourStability / (intervalCount - 1.0);
    }

    private double getStepMovement(MusicPhenotype p) {
        double steps = 0;
        double intervalCount = 0;
        for (ArrayList<Integer> measure : p.melodyIntervals) {
            for (int interval : measure) {
                interval = Math.abs(interval);
                intervalCount++;
                if (interval >= 1 && interval <= 2) {
                    steps += 1;
                }
            }
        }
        if (intervalCount == 0) {
            return 0;
        }
        return steps / intervalCount;
    }

    private double getLeapReturns(MusicPhenotype p) {
        double leapReturns = 0;
        double leaps = 0;
        for (ArrayList<Integer> measure : p.melodyIntervals) {
            int previousIntervalLeap = 0;
            for (int interval : measure) {
                leaps++;
                if (previousIntervalLeap != 0 && Math.signum(previousIntervalLeap) != Math.signum(interval) && Math.abs(interval) >= 1 && Math.abs(previousIntervalLeap) > Math.abs(interval)) {
                    leapReturns++;
                }
                if (Math.abs(interval) >= 8) {
                    previousIntervalLeap = interval;
                    leaps++;
                }
                else {
                    previousIntervalLeap = 0;
                }
            }
        }
        if (leaps == 0) {
            return 0;
        }
        return 1.0 - leapReturns / leaps;
    }

    private double getClimaxStrength(MusicPhenotype p) {
        double climax = 0;
        for (ArrayList<Byte> measure : p.melodyPitches) {
            for (byte pitch : measure) {
                climax++;
                if (pitch % 12 == p.getRepresentation().key.scale[4]) {
                    climax++;
                }
            }
        }
        if (climax == 0) {
            return 0.0;
        }
        return 1.0 / climax;
    }

    private double getNoteDensity(MusicPhenotype p) {
        double divisor = p.getRepresentation().melodyContainer.melody.length;
        if (divisor == 0) {
            return 0.0;
        }
        return (double) p.pitchPositions.size() / divisor;
    }

    private double getRestDensity(MusicPhenotype p) {
        double divisor = p.getRepresentation().melodyContainer.melody.length;
        byte[] melody = p.getRepresentation().melodyContainer.melody;
        double restQuantaCount = 0;
        boolean isRest = false;
        for (int i = 0; i < divisor; i++) {
            if (melody[i] == MelodyContainer.MELODY_REST) {
                isRest = true;
                restQuantaCount++;
            }
            else if (melody[i] >= MelodyContainer.MELODY_RANGE_MIN) {
                isRest = false;
            }
            if (melody[i] == MelodyContainer.MELODY_HOLD && isRest) {
                restQuantaCount++;
            }
        }
        if (divisor == 0) {
            return 0.0;
        }
        return restQuantaCount / divisor;
    }

    public double getRhythmicVariety(MusicPhenotype p) {
        //This piece of code breaks down if the melody model supports something else than 16ths as the main subdivision, i.e 8ths, triplets, e.t.c..
        double distinctDurations = 0;
        for (int i = 0; i < 16; i++) {
            if (p.pitchDurations[i] > 0 || p.restDurations[i] > 0) {
                distinctDurations++;
            }
        }
        return distinctDurations / 16.0;
    }

    public double getRhythmicRange(MusicPhenotype p) {
        //This piece of code breaks down if the melody model supports something else than 16ths as the main subdivision, i.e 8ths, triplets, e.t.c..
        double shortestDuration = -1;
        double longestDuration = -1;
        for (int i = 0; i < 16; i++) {
            if(p.restDurations[i] > 0 || p.pitchDurations[i] > 0) {
                if (shortestDuration == -1) {
                    shortestDuration = i + 1;
                }
                longestDuration = i + 1;
            }
        }
        if (shortestDuration == -1) {
            return 0;
        }
        return (longestDuration - shortestDuration) / 16.0;
    }

    public double getSyncopation(MusicPhenotype p) {
        if (p.pitchPositions.size() < 1) {
            return 0;
        }
        double syncopations = 0;
        for (int i = 0; i < p.pitchPositions.size(); i++) {
            if (p.pitchPositions.get(i) % 4 == 0) {
                continue;
            }
            int duration = p.getRepresentation().melodyContainer.getNextNoteIndex(p.pitchPositions.get(i)) - p.pitchPositions.get(i);
            if (duration < 4) {
                continue;
            }
            syncopations++;
        }
        return syncopations / (double) p.pitchPositions.size();
    }

    //#################### PATTERN FEATURES ####################

    public double getRepeatedPitches(MusicPhenotype p) {
        double intervalCount = 0;
        double repeatedPitches = 0;
        for (int i = 0; i < p.melodyIntervals.size(); i++) {
            ArrayList<Integer> intervals = p.melodyIntervals.get(i);
            for (int j = 0; j < intervals.size(); j++) {
                intervalCount++;
                if (intervals.get(j) == 0) {
                    repeatedPitches++;
                }
            }
        }
        if (intervalCount == 0) {
            return 0;
        }
        return repeatedPitches / intervalCount;
    }

    public double getRepeatedTimings(MusicPhenotype p) {
        double sequentialPitches = 0;
        double repeatedSequentialPitches = 0;
        for (int i = 0; i < p.sequentialPitchesDurations.size() - 1; i++) {
            if (p.sequentialPitchesDurations.get(i) == p.sequentialPitchesDurations.get(i + 1)) {
                repeatedSequentialPitches++;
            }
            sequentialPitches++;
        }
        if (sequentialPitches == 0) {
            return 0;
        }
        return repeatedSequentialPitches / sequentialPitches;
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

    private double getOnBeatPitchCoverage(MusicPhenotype p) {
        double count = 0;
        byte[] melody = p.getRepresentation().melodyContainer.melody;
        for (int i = 0; i < melody.length; i += MelodyContainer.MELODY_FOURTH_SUBDIVISION) {
            if (melody[i] >= MelodyContainer.MELODY_RANGE_MIN) {
                count++;
            }
        }
        if (p.pitchPositions.size() == 0) {
            return 0;
        }
        return count / (double)p.pitchPositions.size();
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

    public String getEvaluationString(MusicPhenotype p) {
        String s ="";
        s += "pitchVarietyValue: " + pitchVarietyValue + "->" + getPitchVarietyValue(p);
        s += "\npitchRangeValue: " + pitchRangeValue + "->" + getPitchRangeValue(p);
        s += "\nkeyCenteredValue: " + keyCenteredValue + "->" + getKeyCenteredValue(p);
        s += "\nnonScalePitchQuantaValue: " + nonScalePitchQuantaValue + "->" + getNonScalePitchQuantaValue(p);
        s += "\ndissonantIntervalsValue: " + dissonantIntervalsValue + "->" + getDissonantIntervalsValue(p);

        //Contour features
        s += "\ncontourDirection: " + contourDirection + "->" + getContourDirection(p);
        s += "\ncontourStability: " + contourStability + "->" + getContourStability(p);
        s += "\nstepMovement: " + stepMovement + "->" + getStepMovement(p);
        s += "\nleapReturns: " + leapReturns + "->" + getLeapReturns(p); //
        s += "\nclimaxStrength: " + climaxStrength + "->" + getClimaxStrength(p); //

        //Rhythmic features
        s += "\nnoteDensity: " + noteDensity + "->" + getNoteDensity(p);
        s += "\nrestDensity: " + restDensity + "->" + getRestDensity(p); //
        s += "\nrhythmicVariety: " + rhythmicVariety + "->" + getRhythmicVariety(p);
        s += "\nrhythmicRange: " + rhythmicRange + "->" + getRhythmicRange(p);
        s += "\nsyncopation: " + syncopation + "->" + getSyncopation(p);

        //Pattern features
        s += "\nrepeatedPitches: " + repeatedPitches + "->" + getRepeatedPitches(p);
        s += "\nrepeatedTimings: " + repeatedTimings + "->" + getRepeatedTimings(p);
        s += "\nrhythmicWholeBarRepetitions: " + rhythmicWholeBarRepetitions + "->" + getRhythmicWholeMeasureRepetitions(p);
        s += "\nrhythmicHalfBarUniqueness: " + rhythmicHalfBarUniqueness + "->" + getRhythmicHalfMeasureUniqueness(p);
        s += "\nrhythmicWholeBarSequenceValue: " + rhythmicWholeBarSequenceValue + "->" + getRhythmicWholeBarSequenceRepetitions(p);
        s += "\nrestWholeBarSequenceValue: " + rhythmicWholeBarSequenceValue + "->" + getRestWholeBarSequenceRepetitions(p);
        s += "\nonBeatPitchCoverage: " + onBeatPitchCoverage + "->" + getOnBeatPitchCoverage(p);
        return s;
    }
}
