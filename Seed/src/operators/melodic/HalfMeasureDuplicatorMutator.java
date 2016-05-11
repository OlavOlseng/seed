package operators.melodic;

import genetics.MelodyContainer;
import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Olav on 21.04.2016.
 */
public class HalfMeasureDuplicatorMutator extends GeneticMutationOperator<MusicGenotype> {

    public HalfMeasureDuplicatorMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer mc = parent.getDeepCopy();
        List<Integer> halfbarsWithPitches = new ArrayList<>();

        int halfBarSize = 16 / 2;
        int index = 0;
        while (index != -1) {
            if (mc.melodyContainer.melody[index] >= MelodyContainer.MELODY_RANGE_MIN) {
                if(!halfbarsWithPitches.contains(index / halfBarSize)) {
                    halfbarsWithPitches.add(index / halfBarSize);
                }
            }
            index = mc.melodyContainer.getNextNoteIndex(index);
        }

        int extractionIndex = halfbarsWithPitches.get(rand.nextInt(halfbarsWithPitches.size()));


        int injectionIndex = rand.nextInt(mc.bars * 2);
        while (injectionIndex == extractionIndex) {
            injectionIndex = rand.nextInt(mc.bars * 2);
        }

        for (int i = 0; i < halfBarSize; i++) {
            mc.melodyContainer.melody[injectionIndex * halfBarSize + i] = mc.melodyContainer.melody[extractionIndex * halfBarSize + i];
        }
        if (injectionIndex == 0 && mc.melodyContainer.melody[mc.melodyContainer.getNoteStartIndex(extractionIndex)] >= MelodyContainer.MELODY_RANGE_MIN) {
            //This chunk of code makes melodies starting with silence less likely to happen.
            mc.melodyContainer.melody[0] = mc.melodyContainer.melody[mc.melodyContainer.getNoteStartIndex(extractionIndex * halfBarSize)];
    }
        if (mc.melodyContainer.melody[0] == MelodyContainer.MELODY_HOLD) {
                mc.melodyContainer.melody[0] = MelodyContainer.MELODY_REST;
        }

        MusicGenotype child = new MusicGenotype(mc);
        mc.melodyContainer.concatenateRests();
        return child;
    }

    @Override
    public boolean isApplicable(MusicGenotype genotype) {
        return genotype.getData().melodyContainer.melodyContainsPitch();
    }
}
