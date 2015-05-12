package org.altervista.groovyalgorithms.datamining.associationrules

import com.google.common.collect.Sets
import org.altervista.groovyalgorithms.datamining.associationrules.model.AssociationRule
import org.altervista.groovyalgorithms.datamining.frequentpattern.model.FrequentPattern

class AssociationRules {

    /**
     * Mine high-support and high-confidence association rules from a precomputed list
     * of Frequent Patterns.
     *
     * @param frequentPatterns The precomputed list of frequent patterns
     * @param minConfidence The minimum confidence level that an association rules must have to 
     * @return
     */
    static List<AssociationRules> mineAssociationRules(List<FrequentPattern> frequentPatterns, double minConfidence) {
        List<AssociationRule> rules = []
        def supports = frequentPatterns.collectEntries{[(it.pattern),it.support]}
        frequentPatterns.each { frequentPattern ->
            def confNumerator = frequentPattern.support
            def subsets = Sets.powerSet(frequentPattern.pattern).collect{it.toSet()}
            subsets = subsets.findAll{it.size() > 0 && it.size() < frequentPattern.pattern.size()}
            subsets.each { subset ->
                def confDenominator = supports[subset]
                def confidence = confNumerator/confDenominator
                if (confidence >= minConfidence) {
                    rules << new AssociationRule(subset,frequentPattern.pattern - subset,frequentPattern.support,confidence)
                }
            }
        }
        rules
    }
}
