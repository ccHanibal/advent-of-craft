package ci;

import ci.dependencies.Config;
import ci.dependencies.Emailer;
import ci.dependencies.Logger;
import ci.dependencies.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static ci.dependencies.TestStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PipelineTest {
    private final Config config = mock(Config.class);
    private final Logger log = mock(Logger.class);
    private final Emailer emailer = mock(Emailer.class);

    private Pipeline pipeline;

    @BeforeEach
    void setUp() {
        pipeline = new Pipeline(config, emailer, log);
    }

    @Test
    void runsTestsWhenAnyExist() {
        var project =
                spy(
                    Project.builder()
                        .setTestStatus(PASSING_TESTS)
                        .build());

        pipeline.run(project);

        verify(project, times(1)).runTests();
    }

    @Test
    void doesNotRunTestsWhenNoneExist() {
        var project =
                spy(
                    Project.builder()
                        .setTestStatus(NO_TESTS)
                        .build());

        pipeline.run(project);

        verify(project, never()).runTests();
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void deploysWithPassingTests(boolean hasTests) {
        var project =
                spy(
                    Project.builder()
                        .setTestStatus(hasTests ? PASSING_TESTS : NO_TESTS)
                        .build());

        pipeline.run(project);

        verify(project, times(1)).deploy();
    }

    @Test
    void doesNotDeployWhenTestsFail() {
        var project =
                spy(
                    Project.builder()
                        .setTestStatus(FAILING_TESTS)
                        .build());

        pipeline.run(project);

        verify(project, never()).deploy();
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void doesNotSendAnyMailWhenDisabled(boolean deploymentSuccess) {
        when(config.isMailingEnabled()).thenReturn(false);

        var project =
                spy(
                    Project.builder()
                        .setTestStatus(PASSING_TESTS)
                        .setDeploysSuccessfully(deploymentSuccess)
                        .build());

        pipeline.run(project);

        verifyNoInteractions(emailer);
    }

    @Test
    void sendsFailureEmailAfterTestsFailedAndMailingEnabled() {
        when(config.isMailingEnabled()).thenReturn(true);

        Project project =
                spy(
                    Project.builder()
                        .setTestStatus(FAILING_TESTS)
                        .build());

        pipeline.run(project);

        verify(emailer, times(1)).sendPipelineFailed(matches("(?i)tests"));
    }

    @Test
    void sendsFailureEmailAfterDeploymentFailedAndMailingEnabled() {
        when(config.isMailingEnabled()).thenReturn(true);

        Project project =
                spy(
                    Project.builder()
                        .setTestStatus(PASSING_TESTS)
                        .setDeploysSuccessfully(false)
                        .build());

        pipeline.run(project);

        verify(emailer, times(1)).sendPipelineFailed(matches("(?i)deployment"));
    }

    @Test
    void sendsSuccessEmailAfterSuccessfulDeployment() {
        when(config.isMailingEnabled()).thenReturn(true);

        var project =
                spy(
                    Project.builder()
                        .setTestStatus(PASSING_TESTS)
                        .setDeploysSuccessfully(true)
                        .build());

        pipeline.run(project);

        verify(emailer).sendPipelineSucceeded(anyString());
    }
}