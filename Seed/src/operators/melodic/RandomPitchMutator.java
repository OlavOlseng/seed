package operators.melodic;

import genetics.MelodyContainer;
import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;
import util.MusicalKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Olav on 24.02.2016.
 */
public class RandomPitchMutator extends GeneticMutationOperator<MusicGenotype> {


    public RandomPitchMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer ms = parent.getDeepCopy();
        MelodyContainer mc = ms.melodyContainer;
        if(mc.melodyContainsPitch()) {

            int noteIndex = rand.nextInt(mc.melody.length);
            int pitchValue = rand.nextInt(MelodyContainer.MELODY_RANGE) + MelodyContainer.MELODY_RANGE_MIN;

            mc.melody[noteIndex] = (byte) pitchValue;

        }
        else {
            System.out.println("RandomPitchModulationMutator failed, as there are no pitches in the melody.");
        }
        MusicGenotype mg = new MusicGenotype();
        mg.setData(ms);

        return mg;
    }

    @Override
    public boolean isApplicable(MusicGenotype g) {
        return g.getData().melodyContainer.melodyContainsPitch();
    }

    public static void main(String[] args) {
        MusicalContainer container = new MusicalContainer(1, new MusicalKey(0, MusicalKey.Mode.MAJOR));
        container.init();
        container.randomize(new Random());
        container.melodyContainer.melody[2] = MelodyContainer.MELODY_RANGE_MIN + 12;

        System.out.println(Arrays.toString(container.melodyContainer.melody));

        MusicGenotype mg = new MusicGenotype();
        mg.setData(container);
        RandomPitchMutator omm = new RandomPitchMutator(1);

        mg = omm.mutate(mg, new Random());
        System.out.println(Arrays.toString(mg.getData().melodyContainer.melody));
    }
}
