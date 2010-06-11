package org.jbehave.core;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.configuration.EmbedderConfiguration;
import org.jbehave.core.configuration.MostUsefulStoryConfiguration;
import org.jbehave.core.configuration.StoryConfiguration;
import org.jbehave.core.configuration.UnmodifiableEmbedderConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.steps.CandidateSteps;

/**
 * <p>
 * Abstract implementation of {@link RunnableStory} which is intended as a base
 * class with no explicit support for any test framework. It provides the
 * {@link Embedder} used to run the story or stories, using the
 * {@link StoryConfiguration} and the {@link CandidateSteps} specified. By
 * default, {@link MostUsefulStoryConfiguration}) and
 * {@link Embedder#Embedder()} are used, but these can overridden via
 * the {@link RunnableStory#useConfiguration(StoryConfiguration)} and
 * {@link RunnableStory#useEmbedder(Embedder)} methods respectively.
 * </p>
 * <p>
 * Users need to add the {@link CandidateSteps} instances, via the
 * {@link RunnableStory#addSteps(CandidateSteps...)} method.
 * </p>
 * <p>
 * Typically, users that use JUnit will find it easier to extend other
 * implementations, such as {@link JUnitStory} or {@link JUnitStories}, which
 * implement the {@link RunnableStory#run()} via JUnit's annotations.
 * </p>
 */
public abstract class AbstractStory implements RunnableStory {

	private StoryConfiguration configuration = new MostUsefulStoryConfiguration();
	private List<CandidateSteps> candidateSteps = new ArrayList<CandidateSteps>();
	private Embedder embedder = new Embedder();

	public void useConfiguration(StoryConfiguration configuration) {
		this.configuration = configuration;
	}

	public void addSteps(CandidateSteps... steps) {
		this.candidateSteps.addAll(asList(steps));
	}

	public void useEmbedder(Embedder embedder) {
		this.embedder = embedder;
	}

	protected Embedder configuredEmbedder() {
		EmbedderConfiguration embedderConfiguration = embedder.embedderConfiguration();
		if ( embedderConfiguration instanceof UnmodifiableEmbedderConfiguration ){
			configuration.useEmbedderConfiguration(embedderConfiguration);
		}
		embedder.useConfiguration(configuration);
		embedder.useCandidateSteps(candidateSteps);
		return embedder;
	}

}
