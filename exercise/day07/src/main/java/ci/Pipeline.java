package ci;

import ci.dependencies.*;

import java.util.function.Consumer;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    private final Iterable<BuildStep> buildSteps;

    public Pipeline(
                Config config,
                Emailer emailer,
                Logger log,
                Iterable<BuildStep> buildSteps) {

        this.config = config;
        this.emailer = emailer;
        this.log = log;

        this.buildSteps = buildSteps;
    }

    public void run(Project project) {
        for (BuildStep step : buildSteps) {
            var result = step.run(project);
            if (!result) {
                sendMail(() -> emailer.sendPipelineFailed(step.getMailMessage()));
                return;
            }
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
}
