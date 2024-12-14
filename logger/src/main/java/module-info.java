import org.interactor.modules.logging.ApplicationLogging;

module logger {
    requires interactor;
    requires org.slf4j;

    opens org.backender.logger;

    provides ApplicationLogging with org.backender.logger.LoggerImplementation;
}