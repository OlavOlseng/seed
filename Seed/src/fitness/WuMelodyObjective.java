package fitness;

import genetics.MelodyContainer;
import genetics.MusicGenotype;
import genetics.MusicPhenotype;
import genetics.MusicalContainer;
import olseng.ea.fitness.FitnessObjective;

import java.util.ArrayList;

/**
 * Created by Olav on 02.03.2016.
 */
public class WuMelodyObjective implements FitnessObjective<MusicPhenotype> {

    @Override
    public float evaluate(MusicPhenotype phenotype) {
        float fitness = 0;

        MusicalContainer mc = phenotype.getRepresentation();

        for (int measure = 0; measure < mc.bars; measure++) {

            float scalePitches = 0;
            float nonScalePitches = 0;
            float chordPitches = 0;
            float passingTones = 0;
            float neighbourTones = 0;
            float lessThanFifth = 0;
            float augNinth = 0;
            float unresolvedNonScalePitches = 0;
            boolean rootOrFifth = false;
            boolean firstPitchInChord = false;
            byte[] chord = mc.chordContainer.getChord(measure);

            //Calculate pitches
            for (int i = 0; i < phenotype.melodyPitches.get(measure).size(); i++) {
                byte pitch = phenotype.melodyPitches.get(measure).get(i);
                pitch %= 12;

                if (pitch == chord[0] || pitch == chord[1] || chord[2] == pitch || chord[3] == pitch) {
                    chordPitches++;
                    if (i == 0) {
                        firstPitchInChord = true;
                    }
                }
                else {
                    if (mc.key.pitchInKey(pitch) != -1) {
                        scalePitches++;
                    } else {
                        nonScalePitches++;
                        //Check if nonScale pitch is resolved
                        int nextPitchIndex = phenotype.getRepresentation().melodyContainer.getNextNoteIndex(measure * MelodyContainer.MELODY_FOURTH_SUBDIVISION * MelodyContainer.MELODY_BAR_SUBDIVISION + i);
                        if (nextPitchIndex == -1) {
                            //No more pitches
                            unresolvedNonScalePitches++;
                        }
                        else {
                            byte nextPitch = phenotype.getRepresentation().melodyContainer.melody[nextPitchIndex];
                            if (nextPitch < MelodyContainer.MELODY_RANGE_MIN) {
                                //Is a pause
                                unresolvedNonScalePitches++;
                            }
                            else {
                                int interval = phenotype.melodyPitches.get(measure).get(i) - nextPitch;
                                if (Math.abs(interval) > 2) {
                                    unresolvedNonScalePitches++;
                                }
                            }
                        }
                    }
                    if (i - 1 >= 0 && i + 1 < phenotype.melodyPitches.get(measure).size()) {
                        //Possible passing tone.
                        int prevPitch = phenotype.melodyPitches.get(measure).get(i - 1) % 12;
                        int nextPitch = phenotype.melodyPitches.get(measure).get(i + 1) % 12;
                        if (prevPitch == chord[0] || prevPitch == chord[1] || chord[2] == prevPitch || chord[3] == prevPitch) {
                            if (nextPitch == chord[0] || nextPitch == chord[1] || chord[2] == nextPitch || chord[3] == nextPitch) {
                                //Toneone occurs between two stable tones (chord members)
                                int interval1 = phenotype.melodyPitches.get(measure).get(i - 1) - phenotype.melodyPitches.get(measure).get(i);
                                int interval2 = phenotype.melodyPitches.get(measure).get(i) - phenotype.melodyPitches.get(measure).get(i + 1);

                                if (nextPitch == prevPitch) {
                                    if (Math.abs(interval1) > 0 && Math.abs(interval1) <= 2) {
                                        if (Math.abs(interval2) > 0 && Math.abs(interval2) <= 2) {
                                            //NeighbourPitch.
                                            neighbourTones++;
                                        }
                                    }
                                }
                                if (Math.signum(interval1) == Math.signum(interval2)) {
                                    if (Math.abs(interval1) > 0 && Math.abs(interval1) <= 2) {
                                        if (Math.abs(interval2) > 0 && Math.abs(interval2) <= 2) {
                                            //Tone occurs stepwise, and is either a passing tone or a complete neighbour tone.
                                            passingTones++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (pitch == mc.key.rootPitch || pitch == mc.key.scale[4]) {
                    rootOrFifth = true;
                }
            }

            //Check intervals
            float lastStep = 0;
            for (int i = 0; i < phenotype.melodyIntervals.get(measure).size(); i++) {
                int interval = phenotype.melodyIntervals.get(measure).get(i);
                int absInterval = Math.abs(interval);
                if (absInterval > 7) {
                    lessThanFifth += absInterval - 7;
                }
                if (absInterval == 13) {
                    augNinth += 13;
                }
            }

            //Processing for measure is done, assign scores:
            if (scalePitches < chordPitches) {
                fitness++;
            }
            if(nonScalePitches < passingTones + neighbourTones) {
                fitness++;
            }
            if(passingTones <= scalePitches) {
                fitness++;
            }
            if(neighbourTones <= scalePitches) {
                fitness++;
            }
            if (passingTones + neighbourTones <= scalePitches) {
                fitness++;
            }
            if (rootOrFifth) {
                //fitness++;
            }
            if (firstPitchInChord) {
                fitness++;
            }
            if (nonScalePitches + scalePitches + chordPitches > 1) {
                fitness += lessThanFifth == 0 ? 1 : -lessThanFifth;
                fitness += augNinth == 0 ? 1 : -augNinth;
            }
            fitness -= unresolvedNonScalePitches;
        }
        ArrayList<Byte> lastBar = phenotype.melodyPitches.get(phenotype.getRepresentation().bars - 1);
        if (lastBar.size() > 0) {
            int lastPitch = lastBar.get(lastBar.size() - 1) % 12;
            if (lastPitch == phenotype.getRepresentation().key.scale[0]) {
                fitness++;
            }
        }
        return fitness;
    }
}
