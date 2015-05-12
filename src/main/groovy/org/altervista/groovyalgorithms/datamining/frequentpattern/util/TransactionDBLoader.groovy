package org.altervista.groovyalgorithms.datamining.frequentpattern.util

import groovy.transform.Canonical

/**
 * This class provides functions to load transaction databases from sources such as files.
 * Transaction Databases are represented as List of Set
 */
@Canonical
class TransactionDBLoader {

    /**
     * Load a transaction database from a csv File. Each lines represent
     * a transaction and items are separated by the separator regex provided
     *
     * @param file The file to parse
     * @param separator The item separator within transactions
     * @return A List of Set representing the loaded transaction database
     */
    static List<Set> loadFromCsv(File file, String separator, Class type = String.class) {
        file.collect {
            it.split(separator).collect{type.newInstance(it.trim())} as Set
        }
    }

    /**
     * A version of loadFromCsv that accept a path instrad of a file
     *
     * @param file The path of the file to parse
     * @param separator The item separator within transactions
     * @return A List of Set representing the loaded transaction database
     */
    static List<Set> loadFromCsv(String path, String separator,Class type = String.class) {
        loadFromCsv new File(path), separator
    }

}
