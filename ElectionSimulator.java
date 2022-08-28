// Lucas Liu
// The ElectionSimulator class allows the user to calculate and obtain the 
// states with the least number of popular votes to win the election in a given year

import java.util.*;
import java.io.*;

public class ElectionSimulator {
    // List of State objects to be used in calculations
    private final List<State> electionStateList;

    // Map to store calculated values
    private final Map<Arguments, Set<State>> argumentStateMap;

    // pre: takes in a list of states
    // post: creates new ElectionSimulator object using given list
    public ElectionSimulator(List<State> s) {
        this.electionStateList = s;
        this.argumentStateMap = new HashMap<>();
    }

    // post: returns set of State objects with the least number of popular votes  
    //       needed to win election
    public Set<State> simulate() {
        return simulate(minElectoralVotes(electionStateList), 0);
    }

    // helper method for simulate method
    // pre: takes int representing remaining votes and an int 
    //      representing the index
    // post: finds and returns the Set of States with the least number
    //       of popular votes
    private Set<State> simulate(int remainingVotes, int id) {
        Arguments newArguments = new Arguments(remainingVotes, id);

        if (argumentStateMap.containsKey(newArguments)) {
            Set<State> stateSet = argumentStateMap.get(newArguments);
            return stateSet;
        } else if (remainingVotes <= 0) {
            return new HashSet<>();
        } else if (electionStateList.size() == id) {
            argumentStateMap.put(newArguments, null);
            return null;
        }

        int minimumVoters;
        minimumVoters = Integer.MAX_VALUE;
        Set<State> setOfStates = null;
        for (int i = id; i < electionStateList.size(); i++) {
            State currentState = electionStateList.get(i);
            Set<State> remainingStatesRecord;
            remainingStatesRecord = simulate(remainingVotes - currentState.electoralVotes,
                    i + 1);
            if (remainingStatesRecord != null) {
                remainingStatesRecord.add(currentState);
                int newMinimum;
                newMinimum = minPopularVotes(remainingStatesRecord);
                if (minimumVoters > newMinimum) {
                    minimumVoters = newMinimum;
                    setOfStates = new HashSet<>(remainingStatesRecord);
                }
            }
        }
        argumentStateMap.put(newArguments, setOfStates);
        return setOfStates;
    }

    public static int minElectoralVotes(List<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.electoralVotes;
        }
        return total / 2 + 1;
    }

    public static int minPopularVotes(Set<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.popularVotes / 2 + 1;
        }
        return total;
    }

    private static class Arguments implements Comparable<Arguments> {
        public final int electoralVotes;
        public final int index;

        public Arguments(int electoralVotes, int index) {
            this.electoralVotes = electoralVotes;
            this.index = index;
        }

        public int compareTo(Arguments other) {
            int cmp = Integer.compare(this.electoralVotes, other.electoralVotes);
            if (cmp == 0) {
                cmp = Integer.compare(this.index, other.index);
            }
            return cmp;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof Arguments)) {
                return false;
            }
            Arguments other = (Arguments) o;
            return this.electoralVotes == other.electoralVotes && this.index == other.index;
        }

        public int hashCode() {
            return Objects.hash(electoralVotes, index);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<State> states = new ArrayList<>(51);
        try (Scanner input = new Scanner(new File("data/1828.csv"))) {
            while (input.hasNextLine()) {
                states.add(State.fromCsv(input.nextLine()));
            }
        }
        ElectionSimulator temp = new ElectionSimulator(states);
        temp.simulate();
        Set<State> result = new ElectionSimulator(states).simulate();
        System.out.println(result);
        System.out.println(minPopularVotes(result) + " votes");
    }
}
