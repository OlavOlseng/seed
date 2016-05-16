package fitness;

import genetics.MusicPhenotype;
import olseng.ea.fitness.FitnessObjective;
import util.MusicalKey;

import java.util.Arrays;

/**
 * Created by olavo on 2016-05-13.
 */
public class HarmonicProgressionObjective implements FitnessObjective<MusicPhenotype> {

    public double missingFirstTonic = -30;
    public double noDominant = -20;
    public double dominantUnresolved = -20;
    public double diminshedUnresolved = -10;
    public double quintMovement = 1;
    public double chordRepetition = -20;
    public double chordPattern = 1;
    public double zipfValue = 1;

    @Override
    public float evaluate(MusicPhenotype phenotype) {
        double fitness = 0;

        fitness += getMssingTonics(phenotype);
        fitness += getNoDominant(phenotype);
        fitness += getDominantResolutions(phenotype);
        fitness += getDiminshedResolutions(phenotype);
        fitness += getChordRepetition(phenotype);
        fitness += getM7Unresolved(phenotype);
        fitness += getPositionalChordRepetitions(phenotype);
        fitness += getZipfsValue(phenotype);
        //fitness += getQuintMovement(phenotype);

        return (float)fitness;
    }

    private double getMssingTonics(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i += 8) {
            if (p.getRepresentation().chordContainer.getChord(i)[0] != p.getRepresentation().key.scale[0]) {
                fitness += missingFirstTonic;
            }
        }
        return fitness;
    }

    private double getNoDominant(MusicPhenotype p) {
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            if (chord[0] == p.getRepresentation().key.scale[4]) {
                return  0;
            }
        }
        return noDominant;
    }

    private double getDominantResolutions(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            //Check if dominant
            if (chord[0] == p.getRepresentation().key.scale[4]) {
                byte[] nextChord = p.getRepresentation().chordContainer.getChord(p.getRepresentation().chordContainer.getNextChord(i));
                //If not resolved to tonic
                if (nextChord[0] == p.getRepresentation().key.scale[0]) {
                    continue;
                }
                fitness += dominantUnresolved;
            }
        }
        return fitness;
    }

    private double getDiminshedResolutions(MusicPhenotype p) {
        double fitness = 0;
        int diminshedStep = p.getRepresentation().key.mode == MusicalKey.Mode.MAJOR ? 6 : 1;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            //Check if dominant
            if (chord[0] == p.getRepresentation().key.scale[diminshedStep]) {
                byte[] nextChord = p.getRepresentation().chordContainer.getChord(p.getRepresentation().chordContainer.getNextChord(i));
                //Resolutions
                if (nextChord[0] == p.getRepresentation().key.scale[(diminshedStep + 1) % 7]) {
                    continue;
                }
                //If next chord is mediant with a D7.
                else if (nextChord[0] == p.getRepresentation().key.scale[(diminshedStep + 5) % 7] && nextChord[3] != -1) {
                    continue;
                }
                fitness += diminshedUnresolved;
            }
        }
        return fitness;
    }

    private double getChordRepetition(MusicPhenotype p) {
        double fitness = 0;
        boolean lastRepeated = false;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            byte[] nextChord = p.getRepresentation().chordContainer.getChord(p.getRepresentation().chordContainer.getNextChord(i));
            if (nextChord[0] == chord[0]) {
                if (lastRepeated) {
                    fitness += chordRepetition;
                }
                lastRepeated = true;
            }
            else {
                lastRepeated = false;
            }
        }
        return fitness;
    }

    private double getM7Unresolved(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            //Check if MajMin7 chord is resolved
            int thirdInterval = chord[1] < chord[0] ? chord[1] - chord[0] + 12 : chord[1] - chord[0];
            int seventhInterval = chord[3] < chord[0] ? chord[3] - chord[0] + 12 : chord[3] - chord[0];

            if (thirdInterval == 4 && seventhInterval == 10) {
                byte[] nextChord = p.getRepresentation().chordContainer.getChord(p.getRepresentation().chordContainer.getNextChord(i));
                //If not resolved to tonic
                int chordLeap = p.getRepresentation().key.pitchInKey(nextChord[0]) - p.getRepresentation().key.pitchInKey(chord[0]);
                if (chordLeap == -3 || chordLeap == 4) {
                    continue;
                }
                fitness += dominantUnresolved;
            }
        }
        return fitness;
    }

    private double getPositionalChordRepetitions(MusicPhenotype p) {
        double positionalChordRepetitions = 0;

        for (int i = 0; i < p.getRepresentation().bars - 8; i++) {
            if (i + 8 < p.getRepresentation().bars) {
                if (p.getRepresentation().chordContainer.getChord(i)[0] == p.getRepresentation().chordContainer.getChord(i + 8)[0]) {
                    positionalChordRepetitions++;
                }
            }
        }
        return positionalChordRepetitions * this.chordPattern;
    }

    private double getQuintMovement(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            byte[] nextChord = p.getRepresentation().chordContainer.getChord(p.getRepresentation().chordContainer.getNextChord(i));
            int interval = nextChord[0] > chord[0] ? nextChord[0] - chord[0] : nextChord[0] + 12 - chord[0];
            if (interval == 5) {
                fitness += quintMovement;
            }
        }
        return fitness;
    }

    private double getZipfsValue(MusicPhenotype p) {
        double[] chords = new double[12];
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            chords[p.getRepresentation().chordContainer.getChord(i)[0]]++;
        }

        int zipfScore = 0;
        Arrays.sort(chords);
        double lastValue = chords[chords.length - 1];
        for (int i = 1; i < chords.length && lastValue != 0; i++) {
            double nextValue = chords[chords.length - 1 - i];
            if (getApproximatedHalf(lastValue, nextValue)) {
                zipfScore++;
            }
            lastValue = nextValue;
        }
        return zipfScore * zipfValue;

    }

    private boolean getApproximatedHalf(double bigNum, double smallNum) {
        if  (Math.abs(bigNum / 2.0 - smallNum) <= 0.5) {
            return true;
        }
        return false;
    }

}
