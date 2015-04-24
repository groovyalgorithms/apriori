package org.altervista.groovyalgorithms.datamining.frequentpattern.apriori

import org.altervista.groovyalgorithms.datamining.frequentpattern.model.FrequentPattern
import org.altervista.groovyalgorithms.datamining.frequentpattern.util.TransactionDBLoader
import spock.lang.Specification

class AprioriTest extends Specification {
    def "mineFrequentPatterns"() {
        given:
        def path = this.getClass().getResource('/test.csv').file
        def db = TransactionDBLoader.loadFromCsv(path, /,/, String.class)

        when:
        def frequent = Apriori.mineFrequentPattern(db,2/db.size())

        then:
        frequent.size() == 13
        frequent.containsAll([
                new FrequentPattern(['I1'] as Set, 6),
                new FrequentPattern(['I2'] as Set, 7),
                new FrequentPattern(['I3'] as Set, 6),
                new FrequentPattern(['I4'] as Set, 2),
                new FrequentPattern(['I5'] as Set, 2),
                new FrequentPattern(['I1','I2'] as Set, 4),
                new FrequentPattern(['I1','I3'] as Set, 4),
                new FrequentPattern(['I1','I5'] as Set, 2),
                new FrequentPattern(['I2','I3'] as Set, 4),
                new FrequentPattern(['I2','I4'] as Set, 2),
                new FrequentPattern(['I2','I5'] as Set, 2),
                new FrequentPattern(['I1','I2','I3'] as Set, 2),
                new FrequentPattern(['I1','I2','I5'] as Set, 2),

        ])
    }
}
