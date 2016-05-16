package operators.crossover;

import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticCrossoverOperator;
import util.MusicalKey;

import java.util.Random;

/**
 * Created by Olav on 24.02.2016.
 */
public class SinglePointCrossover extends GeneticCrossoverOperator<MusicGenotype> {

    public SinglePointCrossover(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype crossover(MusicGenotype parent1, MusicGenotype parent2, Random rand) {
        MusicalContainer mc1 = parent1.getDeepCopy();
        MusicalContainer mc2 = parent2.getDeepCopy();

        int splicingBar = rand.nextInt(mc1.bars - 2) + 1;

        //Splice in chords
        for (int i = splicingBar; i < mc1.bars; i++) {
            mc1.chordContainer.chords[i] = mc2.chordContainer.chords[i];
        }


        //Splice in melody
        int startingNoteIndex = splicingBar * mc1.melodyContainer.MELODY_FOURTH_SUBDIVISION * mc1.melodyContainer.MELODY_BAR_SUBDIVISION;
        for (int i = startingNoteIndex; i < mc1.melodyContainer.melody.length; i++) {
            mc1.melodyContainer.melody[i] = mc2.melodyContainer.melody[i];
        }

        mc1.melodyContainer.concatenateRests();

        return new MusicGenotype(mc1);
    }

    public static void main(String[] args) {
        MusicalContainer mc1 = new MusicalContainer(3, new MusicalKey(0, MusicalKey.Mode.MINOR));
        mc1.init();
        mc1.randomize(new Random());
        mc1.melodyContainer.melody[0] = 60;
        mc1.melodyContainer.melody[4] = 62;
        mc1.melodyContainer.melody[8] = 64;
        mc1.melodyContainer.melody[12] = 65;
        mc1.melodyContainer.melody[16] = 67;
        mc1.melodyContainer.melody[24] = 67;
        mc1.melodyContainer.melody[32] = 69;
        mc1.melodyContainer.melody[36] = 69;
        mc1.melodyContainer.melody[40] = 69;
        mc1.melodyContainer.melody[44] = 69;

        MusicalContainer mc2 = new MusicalContainer(3, new MusicalKey(0, MusicalKey.Mode.MINOR));
        mc2.init();
        mc2.randomize(new Random());

        System.out.println(mc1);
        System.out.println();
        System.out.println(mc2);
        System.out.println();

        MusicGenotype mg1 = new MusicGenotype(mc1);
        MusicGenotype mg2 = new MusicGenotype(mc2);

        SinglePointCrossover spc = new SinglePointCrossover(1);
        MusicGenotype mg3 = spc.crossover(mg1, mg2, new Random());

        System.out.println(mg3.getData());
    }
}
