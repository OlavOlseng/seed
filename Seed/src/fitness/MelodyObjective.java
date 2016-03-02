package fitness;

import genetics.MusicPhenotype;
import genetics.MusicalContainer;
import olseng.ea.fitness.FitnessObjective;

/**
 * Created by Olav on 02.03.2016.
 */
public class MelodyObjective implements FitnessObjective<MusicPhenotype> {

    @Override
    public float evaluate(MusicPhenotype phenotype) {
        float score = 0;

        MusicalContainer mc = phenotype.getRepresentation();

        for (int measure = 0; measure < mc.bars; measure++) {

            float scalePitches = 0;
            float nonScalePitches = 0;
            float chordPitches = 0;
            float passingTones = 0;
            float lessThanFifth = 0;
            float augNinth = 0;
            boolean rootOrFifth = false;
            byte[] chord = mc.chordContainer.getChord(measure);

            //Calculate pitches
            for (byte pitch : phenotype.melodyPitches.get(measure)) {
                pitch %= 12;

                if (pitch == chord[0] || pitch == chord[1] || chord[2] == pitch || chord[3] == pitch) {
                    chordPitches++;
                }
                else if (mc.key.pitchInKey(pitch) != -1) {
                    scalePitches++;
                }
                else {
                    nonScalePitches++;
                }
                if (pitch == mc.key.rootPitch || pitch == mc.key.scale[4]) {
                    //rootOrFifth = true;
                }
            }

            //Calculate passing tones. This might need to be redone to consider scale steps as well, as currently chromatic movement is encouraged.
            float lastStep = 0;
            for (int interval : phenotype.melodyIntervals.get(measure)) {
                int absInterval = Math.abs(interval);
                if (absInterval > 7) {
                    lessThanFifth += absInterval - 7;
                }
                if (absInterval == 13) {
                    augNinth += 13;
                }
                if (absInterval > 0 && absInterval <= 2) {
                    float newStep = Math.signum(interval);
                    if (lastStep == newStep) {
                        passingTones++;
                    }
                    lastStep = newStep;
                }
            }

            //Processing for measure is done, assign scores:
            if (scalePitches < chordPitches) {
                score++;
            }
            if(nonScalePitches < scalePitches) {
                score++;
            }
            if(passingTones < chordPitches) {
                score++;
            }
            if (passingTones < scalePitches) {
                score++;
            }
            if (rootOrFifth) {
                score++;
            }
            if (nonScalePitches + scalePitches + chordPitches > 1) {
                score += lessThanFifth == 0 ? 1 : -lessThanFifth;
                score += augNinth == 0 ? 1 : -augNinth;
            }
        }
        return score;
    }
}
