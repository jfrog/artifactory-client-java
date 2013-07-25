//
// Built on Thu Feb 07 01:21:50 CET 2013 by logback-translator
// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

// For assistance related to this tool or configuration files
// in general, please contact the logback user mailing list at
//    http://qos.ch/mailman/listinfo/logback-user

// For professional support please see
//   http://www.qos.ch/shop/products/professionalSupport

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.*

final consoleAppender = 'console'

scan('30 seconds')

appender(consoleAppender, ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = '%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n'
  }
}

root(INFO, [consoleAppender])
//logger('org.apache.http', DEBUG)
//logger('org.apache.http.headers', INFO)
//logger('org.apache.http.wire', DEBUG)