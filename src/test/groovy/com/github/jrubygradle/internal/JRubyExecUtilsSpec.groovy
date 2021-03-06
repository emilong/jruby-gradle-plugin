package com.github.jrubygradle.internal

import spock.lang.*

class JRubyExecUtilsSpec extends Specification {
    def "buildArgs should handle scriptArgs with closures"() {
        given:
        List<Object> scriptArgs = [{'spock'}]

        when:
        def cmdArgs = JRubyExecUtils.buildArgs(['-S'], new File('.'), scriptArgs)

        then:
        cmdArgs instanceof List<String>
        cmdArgs.find { it == 'spock' }
    }

    def "buildArgs should handle scriptArgs with closures for non-JRuby commands"() {
        given:
        List<Object> scriptArgs = [{'spock'}]

        when:
        def cmdArgs = JRubyExecUtils.buildArgs([], new File('.'), scriptArgs)

        then:
        cmdArgs instanceof List<String>
        cmdArgs.find { it == 'spock' }
    }

    def "The version string in a jruby jar filename must be extracted correctly"() {

        expect:
            version == JRubyExecUtils.jrubyJarVersion(new File(jarName))

        where:
            jarName || version
            'jruby-complete-1.7.14.jar' || '1.7.14'
            'jruby-complete-22.999.888.jar' || '22.999.888'
            'jruby-complete.jar' || null
    }

    def "The version information in a jruby jar filename must be extracted correctly"() {

        expect:
           triplet == JRubyExecUtils.jrubyJarVersionTriple(new File(jarName))

        where:
            jarName || triplet
            'jruby-complete-1.7.14.jar'     || [ major : 1, minor : 7, patchlevel : 14]
            'jruby-complete-22.999.888.jar' || [ major : 22, minor : 999, patchlevel : 888 ]
            'jruby-complete.jar'            || null
    }
}
