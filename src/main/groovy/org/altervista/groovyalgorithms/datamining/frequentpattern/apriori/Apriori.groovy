package org.altervista.groovyalgorithms.datamining.frequentpattern.apriori

import org.altervista.groovyalgorithms.datamining.frequentpattern.model.FrequentPattern

/**
 * This class implements Apriori Algorithm to mine frequent patterns
 * from a Transaction Database.
   */
class Apriori {

    /**
     *
     * Mine frequent patterns (with at least minimumSupport support) from a Transaction Database
     *
     * @param transactionDB the transaction database to mine
     * @param minimumSupport the minimum support that a frequent pattern must have to be mines
     * @return a List of FrequentPattern
     */
    static List<FrequentPattern> mineFrequentPattern(List<Set> transactionDB, double minimumSupport) {
        //initialization
        Double absoluteSupport = minimumSupport * transactionDB.size()
        List<FrequentPattern> frequentPatterns = []

        //first step
        def items = getItems(transactionDB).collect{[it] as Set}
        def l1 = filterFrequentItems(transactionDB, items, absoluteSupport)
        frequentPatterns.addAll(l1.collect{pattern, support -> new FrequentPattern(pattern,support)})

        while(!l1.isEmpty()) {
            def c1 = generateCandidates(l1,absoluteSupport)
            l1 = filterFrequentItems(transactionDB,c1,absoluteSupport)
            frequentPatterns.addAll(l1.collect{pattern, support -> new FrequentPattern(pattern,support)})
        }

        frequentPatterns
    }

    /**
     * Given a transaction database, a list of candidates and a minimum support, count each candidate
     * support and filter out every pattern that is not frequent
     *
     * @param transactionDB the transaction database as a List of Set
     * @param candidates the candidates to evaluate as a List of Set
     * @param minimumSupport the absolute minimum support to use to filter out candidates
     * @return
     */
    protected static Map filterFrequentItems(List<Set> transactionDB, List<Set> candidates, double minimumSupport) {
        def supportCount = [:]
        candidates.each { candidate ->
            transactionDB.each { transaction ->
                if (transaction.containsAll(candidate)) {
                    supportCount[candidate] = (supportCount[candidate] ?: 0) + 1
                }
            }
        }
        supportCount.findAll {candidate, support -> support >= minimumSupport}
    }

    /**
     * Given a Map containing patterns of length N and their supports,
     * generates a List of candidates of length N+1 which satisfy the
     * downward closure property (all its subsets of length N are frequent)
     *
     * @param frequentItems the support counts for frequent pattern of length N
     * @return The list of candidates of length N+1
     */
    protected static List<Set> generateCandidates(Map<Set, Integer> frequentPatterns, double minimumSupport) {
        Set<Set> candidates = []
        // get single items to combine with actual pattern
        def items = getItems(frequentPatterns.keySet() as List)
        frequentPatterns.each { frequentPattern, support ->
            //find items that can be added to the current frequent item
            items.findAll{!frequentPattern.contains(it)}.each { itemToAdd ->
                Set newPattern = frequentPattern + itemToAdd

                //check all subsets: for each item in the original pattern
                //remove it and check if the remaining pattern is frequent
                def notFrequentSubset = frequentPattern.find { itemToRemove ->
                    frequentPatterns[newPattern-itemToRemove] < minimumSupport
                }

                if (!notFrequentSubset) {
                    candidates << newPattern
                }
            }
        }
        candidates as List
    }

    /**
     * Given a transaction database, returns a Set containing all the elements contained
     *
     * @param transactions the transaction database
     * @return a Set containing the items that appears in the transaction database
     */
    protected static Set getItems(List<Set> transactions) {
        transactions.flatten() as Set
    }

}
