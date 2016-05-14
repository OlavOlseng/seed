package fitness;

import genetics.MusicPhenotype;
import olseng.ea.fitness.FitnessObjective;
import util.MusicalKey;

/**
 * Created by Olav on 28.04.2016.
 */
public class HarmonicObjective implements FitnessObjective<MusicPhenotype> {

    private double invalidRoot = -50;
    private double invalidPitch = -30;
    private double dissonantPitch = -10;
    private double triadAbscence= -40;
    private double fifthAbscence = -10;
    private double unison = -5;
    private double triadUnison = -10;
    private double semitoneInterval = -20;
    private double meaningfulSeventh = -dissonantPitch;


    @Override
    public float evaluate(MusicPhenotype phenotype) {
        double fitness = 0;
        fitness += checkChordRootsInKey(phenotype);
        fitness += checkChordTriads(phenotype);
        fitness += checkChordFifth(phenotype);
        fitness += checkUnisons(phenotype);
        fitness += checkSemitoneIntervals(phenotype);
        fitness += checkDissonantPitches(phenotype);
        fitness += checkSevenths(phenotype);

        return (float)fitness;
    }

    private double checkChordRootsInKey(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            if (p.getRepresentation().key.pitchInKey(chord[0]) == -1) {
                fitness+= invalidRoot;
            }
        }
        return fitness;
    }

    private double checkChordTriads(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            int pitch2 = chord[1] > chord[0] ? chord[1] : chord[1] + 12;
            int interval = pitch2 - chord[0];
            if (interval != 3 && interval != 4) {
                fitness += triadAbscence;
            }
            else if (p.getRepresentation().key.pitchInKey(chord[0]) == 4 && p.getRepresentation().key.mode == MusicalKey.Mode.MINOR && interval == 3) {
                fitness += invalidPitch;
            }
        }
        return fitness;
    }

    private double checkChordFifth(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            int pitch2 = chord[2] > chord[0] ? chord[2] : chord[2] + 12;
            int interval = pitch2 - chord[0];
            if (interval != 7) {
                //Avoid punishing the diminished fifth found in VII Maj and II min,
                if (p.getRepresentation().key.pitchInKey(chord[2]) != -1 && interval != 6) {
                    fitness += fifthAbscence;
                }
            }
        }
        return fitness;
    }

    private double checkUnisons(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            int len = chord[3] == -1 ? 3 : 4;
            for (int j  = 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    if (chord[j] == chord[k]) {
                        if (j == 1) {
                            fitness += triadUnison;
                        }
                        else {
                            fitness += unison;
                        }
                    }
                }
            }
        }
        return fitness;
    }

    private double checkSemitoneIntervals(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            int len = chord[3] == -1 ? 3 : 4;
            for (int j = 0; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    int interval = Math.abs(chord[j] - chord[k]);
                    if (interval == 1 || interval == 2) {
                        fitness += semitoneInterval;
                    }
                }
            }
        }
        return fitness;
    }

    public double checkDissonantPitches(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);
            //Rootpitches already severely punished.
            //Check invalid thirds.
            if (p.getRepresentation().key.pitchInKey(chord[1]) == -1) {
                //Ensure that V Maj thirds aren't punished in a minor key
                if (chord[0] == p.getRepresentation().key.scale[4] && chord[1] == p.getRepresentation().key.scale[6] + 1 && p.getRepresentation().key.mode == MusicalKey.Mode.MINOR) {
                    //aug triad V in minor key
                    //System.out.println("D found in minor");
                } else {
                    fitness += invalidPitch;
                }
            }
            //Check if "fifth" is in key
            if (p.getRepresentation().key.pitchInKey(chord[2]) == -1 && !melodyContainsPitch(p, i, 2)) {
                fitness += invalidPitch;
            }
            if (chord[3] != -1) {
                //Check 4th pitch is in Triad
                if ((chord[3] != chord[0] && chord[3] != chord[1] && chord[3] != chord[2])) {
                    fitness += dissonantPitch;
                }
                //Check if 4th pitch is in key and seventh
                int rootInterval = chord[3] < chord[0] ? chord[3] - chord[0] + 12 : chord[3] - chord[0];
                //Check if 4th note is a seventh.
                if (rootInterval != 10 && rootInterval != 11) {
                    fitness += invalidPitch;
                }
                else if (p.getRepresentation().key.pitchInKey(chord[3]) == -1 && !melodyContainsPitch(p, i, 3)) {
                    fitness += invalidPitch;
                }
            }
        }
        return fitness;
    }

    private double checkSevenths(MusicPhenotype p) {
        double fitness = 0;
        for (int i = 0; i < p.getRepresentation().bars; i++) {
            byte[] chord = p.getRepresentation().chordContainer.getChord(i);

            int rootInterval = chord[3] < chord[0] ? chord[3] - chord[0] + 12 : chord[3] - chord[0];
            //Check if 4th note is a seventh.
            if (rootInterval != 10 && rootInterval != 11) {
                continue;
            }
            if ( chord[3] != -1) {
                if (isResolvedBySemitone(p, i, 3)) {
                    fitness += meaningfulSeventh;
                }
            }
        }
        return fitness;
    }

    private boolean isResolvedBySemitone(MusicPhenotype p, int chordIndex, int chordPitchIndex) {
        byte[] chord = p.getRepresentation().chordContainer.getChord(chordIndex);
        for (int nextChordPitch : p.getRepresentation().chordContainer.getChord(p.getRepresentation().chordContainer.getNextChord(chordIndex))) {

            int interval = Math.abs(chord[chordPitchIndex] - nextChordPitch);
            if ((interval == 2 || interval == 1 && p.getRepresentation().key.pitchInKey(nextChordPitch) != -1)) {
                //Semitone resolution
                return true;
            }
        }
        return false;
    }

    private boolean melodyContainsPitch(MusicPhenotype p, int chordIndex, int chordPitchIndex) {
        int pitch = p.getRepresentation().chordContainer.getChord(chordIndex)[chordPitchIndex];
        for (int i = 0; i < p.melodyPitches.get(chordIndex).size(); i++) {
            if (p.melodyPitches.get(chordIndex).get(i) % 12 == pitch) {
                return true;
            }
        }
        return false;
    }

}
