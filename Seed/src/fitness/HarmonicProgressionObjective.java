package fitness;

import genetics.MusicPhenotype;
import olseng.ea.fitness.FitnessObjective;
import util.MusicalKey;

/**
 * Created by olavo on 2016-05-13.
 */
public class HarmonicProgressionObjective implements FitnessObjective<MusicPhenotype> {

    public double noFirstTonic = -30;
    public double noDominant = -20;
    public double dominantUnresolved = -20;
    public double diminshedUnresolved = -10;
    public double quintMovement = 5;
    public double chordRepetition = -20;

    @Override
    public float evaluate(MusicPhenotype phenotype) {
        double fitness = 0;

        fitness += getNoFirstTonic(phenotype);
        fitness += getNoDominant(phenotype);
        fitness += getDominantResolutions(phenotype);
        fitness += getDiminshedResolutions(phenotype);
        fitness += getChordRepetition(phenotype);
        fitness += getM7Unresolved(phenotype);
        //fitness += getQuintMovement(phenotype);

        return (float)fitness;
    }

    private double getNoFirstTonic(MusicPhenotype p) {
        double fitness = 0;
        if (p.getRepresentation().chordContainer.getChord(0)[0] != p.getRepresentation().key.scale[0]) {
            fitness += noFirstTonic;
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
                else if (nextChord[0] == p.getRepresentation().key.scale[(diminshedStep + 3) % 7]) {
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
            //Check if Major7
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
}
