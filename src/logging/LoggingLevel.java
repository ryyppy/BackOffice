package logging;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 28.03.12
 * Time: 12:51
 * Different Logginglevels in the logging-system.
 *
 * Following abstract rules are considered:
 * DEBUG < WARNING < INFO
 *
 * Hence, if they would be numbers, they would be in this order:
 * 0: DEBUG
 * 1: WARNING
 * 2: INFO
 *
 * Info messages should only be logged with active logginglevel INFO
 * Warning messages should be logged with active logginglevel INFO or WARNING
 * Debug messages should be logged with active logginglevel INFO or WARNING or DEBUG
 */
public enum LoggingLevel{
    DEBUG, WARNING, INFO
}
