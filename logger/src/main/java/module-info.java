import org.interactor.modules.logging.ApplicationLogging;

module logger {
    requires interactor;
    requires org.slf4j;

    provides ApplicationLogging with org.backender.logger.LoggerImplementation;
}