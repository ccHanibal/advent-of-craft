package ci.dependencies.buildsteps;

import ci.dependencies.BuildStep;
import ci.dependencies.Logger;
import ci.dependencies.Project;

public class RunTestsStep implements BuildStep {

    private final Logger log;

    public RunTestsStep(Logger log) {
        this.log = log;
    }

    @Override
    public String getMailMessage() {
        return "Tests failed";
    }

    @Override
    public boolean run(Project project) {
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
