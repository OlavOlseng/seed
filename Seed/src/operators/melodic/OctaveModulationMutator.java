package operators.melodic;

import genetics.HarmonyGenotype;
import genetics.MelodyGenotype;
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
public class OctaveModulationMutator extends GeneticMutationOperator<MusicGenotype> {


    public OctaveModulationMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer ms = parent.getDeepCopy();
        MelodyGenotype mc = ms.melodyGenotype;
        if(mc.melodyContainsPitch()) {

            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < mc.melody.length; i++) {
                if (mc.melody[i] >= MelodyGenotype.MELODY_RANGE_MIN) {
                    indices.add(i);
                }
            }

            int noteIndex = indices.get(rand.nextInt(indices.size()));
            int pitchValue = mc.melody[noteIndex];

            byte toModulate = (byte) 12;

            if (rand.nextDouble() < 0.5) {
                toModulate *= -1;
            }

            pitchValue = ((pitchValue + toModulate) % MelodyGenotype.MELODY_RANGE_MIN) + MelodyGenotype.MELODY_RANGE_MIN;

            mc.melody[noteIndex] = (byte) pitchValue;
        }
        else {
            System.out.println("PitchModulationMutator failed, as there are no pitches in the melody.");
        }
        MusicGenotype mg = new MusicGenotype();
        mg.setData(ms);
        return mg;
    }

    public static void main(String[] args) {
        MusicalContainer container = new MusicalContainer(1, new MusicalKey(0, MusicalKey.Mode.MAJOR));
        container.init();
        container.randomize(new Random());
        container.melodyGenotype.melody[2] = MelodyGenotype.MELODY_RANGE_MIN + 12;

        System.out.println(Arrays.toString(container.melodyGenotype.melody));

        MusicGenotype mg = new MusicGenotype();
        mg.setData(container);
        OctaveModulationMutator omm = new OctaveModulationMutator(1);

        mg = omm.mutate(mg, new Random());
        System.out.println(Arrays.toString(mg.getData().melodyGenotype.melody));
    }
}
