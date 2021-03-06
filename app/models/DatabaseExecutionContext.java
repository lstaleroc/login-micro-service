package models;

import akka.actor.ActorSystem;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;

import javax.inject.Inject;

/**
 * Database execution context
 * Created by Luis Talero on 12 Jun 2020
 */
public class DatabaseExecutionContext implements ExecutionContextExecutor {

    private final ExecutionContext executionContext;

    private static final String NAME = "database.dispatcher";

    @Inject
    public DatabaseExecutionContext(ActorSystem actorSystem) {

        this.executionContext = actorSystem.dispatchers().lookup(NAME);
    }

    @Override
    public ExecutionContext prepare() {

        return executionContext.prepare();
    }

    @Override
    public void execute(Runnable command) {

        executionContext.execute(command);
    }

    @Override
    public void reportFailure(Throwable cause) {

        executionContext.reportFailure(cause);
    }
}
