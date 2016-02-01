import olseng.ea.genetics.GeneticMutationOperator;

import java.util.Arrays;
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
        MusicalContainer container = parent.getDeepCopy();

        int index = rand.nextInt(container.melody.length);
        byte value = container.melody[index];
        int startIndex = container.getNoteStartIndex(index);
        byte startValue = container.melody[startIndex];

        if (value == MusicalContainer.NOTE_HOLD) {
            if (startValue != MusicalContainer.NOTE_REST && rand.nextDouble() < 0.5) {
                container.melody[index] = MusicalContainer.NOTE_REST;
            }
            else {
                container.melody[index] = (byte)(rand.nextInt(MusicalContainer.NOTE_MAX_RANGE - MusicalContainer.NOTE_MIN_RANGE + 1) + MusicalContainer.NOTE_MIN_RANGE);
            }
        }
        else if (value == MusicalContainer.NOTE_REST) {
            if (rand.nextDouble() < 0.5 && startIndex > 0) {
                container.melody[index] = MusicalContainer.NOTE_HOLD;
            } else {
                container.melody[index] = (byte) (rand.nextInt(MusicalContainer.NOTE_MAX_RANGE - MusicalContainer.NOTE_MIN_RANGE + 1) + MusicalContainer.NOTE_MIN_RANGE);
            }
        }
        else {
            if (rand.nextDouble() < 0.5 && startIndex > 0) {
                container.melody[index] = MusicalContainer.NOTE_HOLD;
            } else {
                container.melody[index] = MusicalContainer.NOTE_REST;
            }
        }
        MusicGenotype child = new MusicGenotype();
        child.setData(container);
        return child;
    }

    public static void main(String[] args) {
        MusicalContainer mc = new MusicalContainer(1);
        mc.init();
        MusicGenotype mg = new MusicGenotype();
        mg.setData(mc);
        NoteModeMutator nmm = new NoteModeMutator(1);
        Random rand = new Random();
        System.out.println(Arrays.toString(mc.melody));
        for (int i = 0; i < 50; i++) {
                mg = nmm.mutate(mg, rand);
                System.out.println(Arrays.toString(mg.getData().melody));
        }
    }
}
