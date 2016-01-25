package olseng.ea.adultselection;

import olseng.ea.core.Population;
import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Olav on 11.01.2016.
 */
public class TournamentSelector extends AdultSelector {

    private int tournamentSize = 10;
    private double randomProbability = 0.1;

    /**
     * Initializes a TournamnetSelector with specified parameters.
     * @param tournamentSize - Size of the tournaments.
     * @param randomProbability - Chance that a random member of the population is chosen, instead of the most fit individual.
     */
    public TournamentSelector(int tournamentSize, double randomProbability) {
        this.tournamentSize = tournamentSize;
        this.randomProbability = randomProbability;
    }

    public void setTournamentSize(int size) {
        this.tournamentSize = size;
    }

    public void setRandomProbability(double randomProbability) {
        this.randomProbability = randomProbability;
    }

    private List<Integer> buildTournament(Population population) {
        Random random = new Random();
        List<Integer> selected = new ArrayList<>(tournamentSize);
        int populationSize = population.getPopulationSize();


        int picked = 0;
        while(picked < tournamentSize) {
            int index = (random.nextInt(populationSize));
            if (selected.contains(index)) {
                continue;
            }
            selected.add(index);
            picked++;
        }
        return selected;
    }

    @Override
    public Phenotype getIndividual(Random random) {
        List<Integer> tournament = buildTournament(this.population);

        if (random.nextDouble() > randomProbability) {
            int winner = tournament.get(0);

            for (int i = 1; i < tournament.size(); i++) {
                int opponent = tournament.get(i);
                if(winner > opponent) {
                    winner = opponent;
                }
            }
            return population.getIndividual(winner);
        }
        return population.getIndividual(tournament.get(random.nextInt(tournamentSize)));
    }
}
