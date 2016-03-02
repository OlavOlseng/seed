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

        processIntervalsAndPitches(p);

        return p;
    }

    private void processIntervalsAndPitches(MusicPhenotype p) {
        MelodyContainer mc = p.getRepresentation().melodyContainer;
        //find first pitch
        int barSize = mc.MELODY_BAR_SUBDIVISION * mc.MELODY_FOURTH_SUBDIVISION;
        int lastPitchIndex = mc.melody[0];
        for (int bar = 0; bar < mc.bars; bar++) {
            for (int currentPitchIndex = bar * barSize; currentPitchIndex < (bar + 1) * barSize; currentPitchIndex++) {
                if (currentPitchIndex == 0) {
                    currentPitchIndex++;
                }
                if (mc.melody[currentPitchIndex] < MelodyContainer.MELODY_RANGE_MIN) {
                    continue;
                }
                //New index is a pitch.
                p.melodyPitches.get(bar).add(mc.melody[currentPitchIndex]);
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
