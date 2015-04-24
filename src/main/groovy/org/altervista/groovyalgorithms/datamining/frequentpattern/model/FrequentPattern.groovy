package org.altervista.groovyalgorithms.datamining.frequentpattern.model

import groovy.transform.Canonical

/**
 * This class represent a Frequent Pattern found through
 * some frequent pattern mining algorithm such as Apriori
 * or FPGrowth.
 */
@Canonical
class FrequentPattern {

    /** Elements contained in the frequent pattern */
    Set pattern
    /** The absolute support of the frequent pattern in the dataset from which is mined */
    Integer support

}
