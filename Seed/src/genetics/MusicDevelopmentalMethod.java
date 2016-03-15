package genetics;

import olseng.ea.genetics.DevelopmentalMethod;

/**
 * Created by Olav on 02.03.2016.
 */
public class MusicDevelopmentalMethod implements DevelopmentalMethod<MusicGenotype, MusicPhenotype> {

    @Override
    public MusicPhenotype develop(MusicGenotype genotype) {
        MusicPhenotype p = new MusicPhenotype(genotype);
        p.setRepresentation(genotype.getData());

        processMelodicIntervalsAndPitches(p);
        processMelodicRhythmsAndPatterns(p);

        return p;
    }

    private void processMelodicRhythmsAndPatterns(MusicPhenotype p) {
        //Durations are sorted where 1/16 is in position 0, 1/1 is in position 15.
        int[] durations;

        int pitchIndex = 0;
        int restIndex = 0;
        int lastPosition = 0;
        int currentPosition = 0;
        boolean isRest = false;

        while (pitchIndex < p.pitchPositions.size() || restIndex < p.restPositions.size()) {

            if (p.pitchPositions.size() <= pitchIndex) {
                currentPosition = p.restPositions.get(restIndex);
                durations = p.restDurations;
                isRest = true;
                restIndex++;
            }
            else if (p.restPositions.size() <= restIndex) {
                currentPosition = p.pitchPositions.get(pitchIndex);
                durations = p.pitchDurations;
                isRest = false;
                pitchIndex++;
            }
            else if (p.pitchPositions.get(pitchIndex) > p.restPositions.get(restIndex)) {
                currentPosition = p.pitchPositions.get(pitchIndex);
                durations = p.pitchDurations;
                isRest = false;
                pitchIndex++;
            }
            else {
                currentPosition = p.restPositions.get(restIndex);
                durations = p.restDurations;
                isRest = true;
                restIndex++;
            }

            int duration = currentPosition - lastPosition - 1;
            if (duration <= 0) {
                continue;
            }
            while(duration > 15) {
                durations[15]++;
                duration -= 15;
            }
            durations[duration]++;
            lastPosition = currentPosition;
        }
        //Finish off final note.
        int duration = p.getRepresentation().melodyContainer.melody.length - lastPosition - 1;
        if (isRest) {
            durations = p.restDurations;
        }
        else {
            durations = p.pitchDurations;
        }
        while(duration > 15) {
            durations[15]++;
            duration -= 15;
        }
        durations[duration]++;
    }

    private void processMelodicIntervalsAndPitches(MusicPhenotype p) {
        MelodyContainer mc = p.getRepresentation().melodyContainer;
        //find first pitch
        int barSize = mc.MELODY_BAR_SUBDIVISION * mc.MELODY_FOURTH_SUBDIVISION;
        int lastPitchIndex = mc.melody[0];
        for (int bar = 0; bar < mc.bars; bar++) {
            for (int currentPitchIndex = bar * barSize; currentPitchIndex < (bar + 1) * barSize; currentPitchIndex++) {

                if (mc.melody[currentPitchIndex] < MelodyContainer.MELODY_RANGE_MIN) {
                    if (mc.melody[currentPitchIndex] == MelodyContainer.MELODY_REST) {
                        p.restPositions.add(currentPitchIndex);
                    }
                    continue;
                }
                //New index is a pitch.
                p.melodyPitches.get(bar).add(mc.melody[currentPitchIndex]);
                p.pitchPositions.add(currentPitchIndex);
                if (currentPitchIndex == 0) {
                    currentPitchIndex++;
                }
                if (mc.melody[lastPitchIndex] < MelodyContainer.MELODY_RANGE_MIN) {
                    lastPitchIndex = currentPitchIndex;
                    continue;
                }
                //At this point, both variables must be pitches.
                p.melodyIntervals.get(bar).add(mc.melody[currentPitchIndex] - mc.melody[lastPitchIndex]);
                lastPitchIndex = currentPitchIndex;
            }
        }
    }
}
