package org.altervista.groovyalgorithms.datamining.associationrules.model

import groovy.transform.Canonical

/**
 * This class represents an association rules mined from a transaction database.
 * Association rules are in the form {left} => {right} (support,confidence). All of those
 * information are contained in this class
 */
@Canonical
class AssociationRule {

    /** The left-side part of the rule */
    Set left
    /** The right-size of the rule */
    Set right
    /** The absolute support of the rule in the transaction database */
    Integer support
    /** The confidence of the rule */
    double confidence

    String toString() {
        return "$left => $right ($support,$confidence)"
    }
}