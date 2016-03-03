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
    public double pitchVarietyValue = 0.5;
    public double pitchRangeValue = 0.2;
    public double keyCenteredValue = 0.1;
    public double nonScalePitchQuantaValue = 0.05;
    public double dissonantIntervalsValue = 0.01;

    //Contour features
    public double contourDirection = 0.5;
    public double contourStability = 0.4;
    public double stepMovement = 0.6;
    public double leapReturns = 0.1;
    public double climaxStrength = 0.1;

    @Override
    public float evaluate(MusicPhenotype p) {
        double fitness = 0;

        fitness += proximity(pitchVarietyValue, getPitchVarietyValue(p));
        fitness += proximity(pitchRangeValue, getPitchRangeValue(p));
        fitness += proximity(keyCenteredValue, getKeyCenteredValue(p));
        fitness += proximity(nonScalePitchQuantaValue, getNonScalePitchQuantaValue(p));
        fitness += proximity(dissonantIntervalsValue, getDissonantIntervalsValue(p));

        fitness += proximity(contourDirection, getContourDirection(p));
        fitness += proximity(contourStability, getContourStability(p));
        fitness += proximity(stepMovement, getStepMovement(p));
        fitness += proximity(leapReturns, getLeapReturns(p));
        fitness += proximity(climaxStrength, getClimaxStrength(p));

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
        double pitchMin = MelodyContainer.MELODY_RANGE_MAX - 1;
        double pitchMax = MelodyContainer.MELODY_RANGE_MIN - 1;
        for (ArrayList<Byte> measure : p.melodyPitches) {
            for (byte pitch : measure) {
                if (pitch < pitchMin) {
                    pitchMin = pitch;
                }
                if (pitch > pitchMax) {
                    pitchMax = pitch;
                }
            }
        }
        return (pitchMax - pitchMin) / (double) MelodyContainer.MELODY_RANGE;
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
                if(intervalCount == 0) {
                    previousInterval = interval;
                    continue;
                }
                intervalCount++;
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
                if (interval >= 1 || interval <= 2) {
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
        return 1 - leapReturns / leaps;
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

    /**
     * Measures the proximity between two clamped doubles.
     * @param d1
     * @param d2
     * @return 1 if the numbers are identical, the difference between them else.
     */
    private double proximity(double d1, double d2) {
        return 1 - Math.abs(d1 - d2);
    }
}