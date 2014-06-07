/**
 *
 */
package com.blizzardtec.plugin.helper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.factory.DefaultArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.junit.Test;

/**
 * @author Barnaby Golden
 *
 */
public final class ArtifactManagerTest {

    /**
     * Test the creation of an artifact manager.
     */
    @Test
    public void artifactManagerTest() {

        // setters

        final ArtifactManager manager = new ArtifactManager();

        final List<ArtifactRepository> remoteRepos =
                        new ArrayList<ArtifactRepository>();

        manager.setRemoteRepos(remoteRepos);

        final ArtifactFactory factory = new DefaultArtifactFactory();

        manager.setFactory(factory);

        // getters

        final ArtifactFactory fact1 = manager.getFactory();

        assertNotNull("factory was null", fact1);

        final List<ArtifactRepository> repos = manager.getRemoteRepos();

        assertNotNull("remote repositories were null", repos);

        final ArtifactRepository repo = manager.getRepo();

        assertNull("repo was not null", repo);

        final ArtifactResolver resolver = manager.getArtifactResolver();

        assertNull("resolver was not null", resolver);
    }
}
