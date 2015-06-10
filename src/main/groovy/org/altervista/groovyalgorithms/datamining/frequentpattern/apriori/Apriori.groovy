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
    static mineFrequentPattern(transactionDB, minimumSupport) {
        //initialization
        def absoluteSupport = minimumSupport * transactionDB.size()
        def frequentPatterns = []

        //first step
        def items = get1Itemset(transactionDB)
        def survivors = filterFrequentItems(transactionDB, items, absoluteSupport)
        saveFrequentPatterns(frequentPatterns, survivors)

        //repeat until there're no more survivors
        while (!survivors.isEmpty()) {
            def candidates = generateCandidates(survivors, absoluteSupport)
            survivors = filterFrequentItems(transactionDB, candidates, absoluteSupport)
            saveFrequentPatterns(frequentPatterns, survivors)
        }

        frequentPatterns
    }

    static saveFrequentPatterns(frequentPatterns, survivors) {
        frequentPatterns.addAll(survivors.collect {
            pattern, support -> new FrequentPattern(pattern, support)
        })
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
    static filterFrequentItems(transactionDB, candidates, minimumSupport) {
        candidates.collectEntries { candidate ->
            def support = transactionDB.count { transaction ->
                transaction.containsAll(candidate)
            }
            [(candidate) : support]
        }.findAll { candidate, support -> support >= minimumSupport }
    }

    /**
     * Given a Map containing patterns of length N and their supports,
     * generates a List of candidates of length N+1 which satisfy the
     * downward closure property (all its subsets of length N are frequent)
     *
     * @param frequentItems the support counts for frequent pattern of length N
     * @return The list of candidates of length N+1
     */
    static generateCandidates(frequentPatterns, minimumSupport) {
        Set<Set> candidates = []
        // get single items to combine with actual pattern
        def items = get1Itemset(frequentPatterns.keySet() as List)
        frequentPatterns.each { frequentPattern, support ->
            def itemsToAdd = items.findAll { !frequentPattern.contains(it) }
            itemsToAdd.each { itemToAdd ->
                Set newPattern = frequentPattern + itemToAdd
                def notFrequentSubset = frequentPattern.find { itemToRemove ->
                    frequentPatterns[newPattern - itemToRemove] < minimumSupport
                }
                if (!notFrequentSubset) {
                    candidates << newPattern
                }
            }
        }
        candidates
    }

    /**
     * Given a transaction database, returns a Set containing all the elements contained
     *
     * @param transactions the transaction database
     * @return a Set containing the items that appears in the transaction database
     */
    static get1Itemset(transactions) {
        transactions.flatten().collect { [it] as Set } as Set
    }

}
