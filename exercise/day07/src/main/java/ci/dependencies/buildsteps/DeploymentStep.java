package ci.dependencies.buildsteps;

import ci.dependencies.BuildStep;
import ci.dependencies.Logger;
import ci.dependencies.Project;

public class DeploymentStep implements BuildStep {

    private final Logger log;

    public DeploymentStep(Logger log) {
        this.log = log;
    }

    @Override
    public String getMailMessage() {
        return "Deployment failed";
    }

    @Override
    public boolean run(Project project) {
        if (!project.deploy()) {
            log.error("Deployment failed");
            return false;
        }

        log.info("Deployment successful");
        return true;
    }
}
