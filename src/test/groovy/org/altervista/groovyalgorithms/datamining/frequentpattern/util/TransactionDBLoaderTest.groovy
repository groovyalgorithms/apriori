package org.altervista.groovyalgorithms.datamining.frequentpattern.util

import spock.lang.Specification

/**
 * Created by daniele on 24/04/15.
 */
class TransactionDBLoaderTest extends Specification {
    def "LoadFromCsv"() {
        given:
        def file = this.getClass().getResource('/shop.csv').getFile()

        when:
        def db = TransactionDBLoader.loadFromCsv(file, /,/)

        then:
        db.size() == 6
        db.first() == ['Beer', 'Nuts', 'Diaper'] as Set
        db.count { it.contains('Beer') } == 4
    }
}
