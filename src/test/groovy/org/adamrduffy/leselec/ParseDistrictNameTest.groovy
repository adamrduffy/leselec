package org.adamrduffy.leselec

import org.junit.Test

class ParseDistrictNameTest {
    @Test
    void parse() {
        String district = "LERIBE (13 /13)"
        def pattern = (district =~ /(.+)\s*?\((\d+).*\)/)[0]
        println pattern[1]
        println pattern[2]
    }
}
