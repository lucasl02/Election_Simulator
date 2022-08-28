# Election_Simulator
The program finds, through a recursive process, the list of states required in a given year to win the election. 
The final list is the combination of states with the least amount of popular votes but still has enough electoral votes to win the election.
An Arguments class is created to record/ save previously calculated sub-problems to improve run time. 

Simplifications:
A simple majority of the popular votes in a state will win all of the state’s electors. For example, in a small state with 999,999 people, 500,000 votes will win all its electors.

A majority of the electoral votes is needed to become president. In the 2008 election, 270 votes were needed because there were 538 electors. In the 1804 election, 89 votes were needed because there were only 176 electors.

Electors never defect.
