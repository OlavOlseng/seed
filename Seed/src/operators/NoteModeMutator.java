package operators;

import genetics.MusicGenotype;
import genetics.MelodyGenotype;
import genetics.MusicalStruct;
import olseng.ea.genetics.GeneticMutationOperator;
import java.util.Random;

/**
 * Created by Olav on 01.02.2016.
 */
public class NoteModeMutator extends GeneticMutationOperator<MusicGenotype> {

    public NoteModeMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalStruct ms = parent.getDeepCopy();
        MelodyGenotype container = ms.mg;

        int index = rand.nextInt(container.melody.length);
        byte value = container.melody[index];
        int startIndex = container.getNoteStartIndex(index);
        byte startValue = container.melody[startIndex];

        if (value == MelodyGenotype.MELODY_HOLD) {
            if (startValue != MelodyGenotype.MELODY_REST && rand.nextDouble() < 0.5) {
                container.melody[index] = MelodyGenotype.MELODY_REST;
            }
            else {
                int previousNoteIndex = container.getPreviousNoteIndex(startIndex);
                if (previousNoteIndex > -1 && container.melody[previousNoteIndex] >= MelodyGenotype.MELODY_RANGE_MIN) {
                    container.melody[index] = container.melody[previousNoteIndex];
                }
                else {
                    container.melody[index] = (byte) (rand.nextInt(MelodyGenotype.MELODY_RANGE + 1) + MelodyGenotype.MELODY_RANGE_MIN);
                }
            }
        }
        else if (value == MelodyGenotype.MELODY_REST) {
            if (rand.nextDouble() < 0.5 && startIndex > 0) {
                container.melody[index] = MelodyGenotype.MELODY_HOLD;
            } else {
                int previousNoteIndex = container.getPreviousNoteIndex(startIndex);
                if (previousNoteIndex > -1 && container.melody[previousNoteIndex] >= MelodyGenotype.MELODY_RANGE_MIN) {
                    container.melody[index] = container.melody[previousNoteIndex];
                }
                else {
                    container.melody[index] = (byte) (rand.nextInt(MelodyGenotype.MELODY_RANGE + 1) + MelodyGenotype.MELODY_RANGE_MIN);
                }
            }
        }
        else {
            if (rand.nextDouble() < 0.5 && startIndex > 0) {
                container.melody[index] = MelodyGenotype.MELODY_HOLD;
            } else {
                container.melody[index] = MelodyGenotype.MELODY_REST;
            }
        }
        MusicGenotype child = new MusicGenotype();
        child.setData(ms);
        return child;
    }
}
