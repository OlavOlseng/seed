package tests;

import fitness.TowseyObjectiveMelody;
import fitness.WuMelodyObjective;
import genetics.*;
import olseng.ea.EAFactory;
import olseng.ea.adultselection.RankedTournamentSelector;
import olseng.ea.core.EA;
import olseng.ea.core.Population;
import olseng.ea.fitness.ranking.FastNonDominatedSort;
import olseng.ea.fitness.ranking.RankComparator;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;
import operators.crossover.SingleBarCrossover;
import operators.crossover.SinglePointCrossover;
import operators.melodic.NoteModeMutator;
import operators.melodic.NoteSwapMutator;
import operators.melodic.PitchModulationMutator;
import operators.melodic.RandomPitchMutator;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import util.ChordBuilder;
import util.MusicParser;
import util.MusicalKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olav on 02.03.2016.
 */
public class MelodyObjectiveTest {

    public static void main(String[] args) {
        OperatorPool<MusicGenotype> op = new OperatorPool<>();
        op.addOperator(new NoteModeMutator(2));
        op.addOperator(new NoteSwapMutator(1));
        op.addOperator(new RandomPitchMutator(2));
        op.addOperator(new PitchModulationMutator(1));
        op.addOperator(new SingleBarCrossover(1));
        op.addOperator(new SinglePointCrossover(1));
        op.setCrossoverProbability(0.3);

        EAFactory<MusicGenotype, MusicPhenotype> factory = new EAFactory<>();
        factory.addFitnessObjective(new WuMelodyObjective());
        factory.addFitnessObjective(new TowseyObjectiveMelody());
        factory.developmentalMethod = new MusicDevelopmentalMethod();
        factory.operatorPool = op;
        factory.adultSelector = new RankedTournamentSelector(4, 0.5);
        factory.rankingModule = new FastNonDominatedSort();
        factory.sortingModule = new RankComparator();

        EA<MusicGenotype, MusicPhenotype> ea = factory.build();
        ea.setThreadCount(32);
        ea.populationMaxSize = 100;
        ea.populationElitism = 1;
        ea.allowMutationAndCrossover = true;

        MusicalKey key = new MusicalKey(0, MusicalKey.Mode.MAJOR);
        MusicalContainer music = new MusicalContainer(8, key);
        music.init();
        MelodyContainer mc = music.melodyContainer;

        mc.init();
/**
        mc.melody[0] = 60 + 12;
        mc.melody[4] = 62 + 12;
        mc.melody[8] = 64 + 12;
        mc.melody[12] = 65 + 12;
        mc.melody[16] = 67 + 12;
        mc.melody[24] = 67 + 12;
        mc.melody[32] = 69 + 12;
        mc.melody[36] = 69 + 12;
        mc.melody[40] = 69 + 12;
        mc.melody[44] = 69 + 12;
        mc.melody[48] = 67 + 12;
        mc.melody[64] = 65 + 12;
        mc.melody[68] = 65 + 12;
        mc.melody[72] = 65 + 12;
        mc.melody[76] = 65 + 12;
        mc.melody[80] = 64 + 12;
        mc.melody[88] = 64 + 12;
        mc.melody[96] = 62 + 12;
        mc.melody[100] = 62 + 12;
        mc.melody[104] = 62 + 12;
        mc.melody[108] = 62 + 12;
        mc.melody[112] = 60 + 12;

*/
        ChordContainer hg = music.chordContainer;
        hg.init();
        hg.chords[0] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[1] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[2] = ChordBuilder.getChord(3, 3, 1, key);
        hg.chords[3] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[4] = ChordBuilder.getChord(1, 3, 1, key);
        hg.chords[5] = ChordBuilder.getChord(5, 3, 1, key);
        hg.chords[6] = ChordBuilder.getChord(4, 4, 1, key);
        hg.chords[7] = ChordBuilder.getChord(0, 3, 1, key);


        MusicGenotype initialSeed = new MusicGenotype(music);
        List<Phenotype> initialPop = new ArrayList<>();
        MusicPhenotype p = ea.developmentalMethod.develop(initialSeed);
        ea.fitnessEvaluator.evaluate(p);
        System.out.println(p.getFitnessValue(0));
        initialPop.add(p);

        Population pop = new Population(10);
        pop.setPopulation(initialPop);

        ea.initialize(pop);

        for (int i = 0; i < 1000; i++) {
            System.out.println("Running generation: " + i);
            System.out.println("Pop size: " + pop.getPopulationSize());
            ea.step();
        }

        ea.terminateThreads();

        Player player = new Player();

        while(true) {
            int index = 0;
            if (true) {
                for (int i = 0; i < 25; i++) {
                    System.out.println(i + ":" + ea.population.getIndividual(i));
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Select and index to play...");
                try {
                    index = Integer.parseInt(String.valueOf(br.readLine()));
                    if (index == -1) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(ea.population.getIndividual(index).getRepresentation());
                System.out.println(ea.population.getIndividual(index));

                MusicParser parser = new MusicParser();
                music = (MusicalContainer) ea.population.getIndividual(index).getRepresentation();
                String melody = parser.parseMelody(music.melodyContainer);
                System.out.println(melody);
                melody = " Rw | " + melody;
                String chords = "Rw | " + parser.parseChords(music.chordContainer);
                System.out.println(chords);
                System.out.println("Half measure counts: " + ((MusicPhenotype)(ea.population.getIndividual(index))).halfMeasureRhythmicPatterns.values());
                System.out.println("Whole measure counts: " + ((MusicPhenotype)(ea.population.getIndividual(index))).wholeMeasureRhythmicPatterns.values());
                Pattern pMelody = new Pattern(melody).setVoice(0).setInstrument(73);
                Pattern pHarmony = new Pattern(chords).setVoice(1).setInstrument(0);

                player.play(pMelody, pHarmony);
            }
        }
    }
}
