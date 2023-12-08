package ci;

import ci.dependencies.Config;
import ci.dependencies.Emailer;
import ci.dependencies.Logger;
import ci.dependencies.Project;

import java.util.function.Consumer;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        var testsPassed = runTests(project);
        if (!testsPassed) {
            sendMail(() -> emailer.sendPipelineFailed("Tests failed"));
            return;
        }

        var deploymentSuccess = deploy(project);
        if (!deploymentSuccess) {
            sendMail(() -> emailer.sendPipelineFailed("Deployment failed"));
            return;
        }

        sendMail(() -> emailer.sendPipelineSucceeded("Deployment completed successfully"));
    }

    private void sendMail(Runnable mailerAction) {
        if (!config.isMailingEnabled()) {
            log.info("Email disabled");
            return;
        }

        mailerAction.run();
    }

    private boolean deploy(Project project) {
        if (!project.deploy()) {
            log.error("Deployment failed");
            return false;
        }

        log.info("Deployment successful");
        return true;
    }

    private boolean runTests(Project project) {
        if (!project.hasTests()) {
            log.info("No tests");
            return true;
        }

        if (project.runTests()) {
            log.info("Tests passed");
            return true;
        }

        log.error("Tests failed");
        return false;
    }
}
