/**
 * 
 */
package com.blizzardtec.plugin.helper;

import java.util.List;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;

/**
 * Wrapper class that packages up several attributes
 * associated with Maven artifacts.
 *
 * @author Barnaby Golden
 *
 */
public final class ArtifactManager {

    /**
     * List of remote Maven repositories.
     */
    private List<ArtifactRepository> remoteRepos;
    /**
     * Local Maven repository.
     */
    private ArtifactRepository repo;
    /**
     * Maven artifact resolver.
     */
    private ArtifactResolver artifactResolver;
    /**
     * Maven artifact factory.
     */
    private ArtifactFactory factory;

    /**
     * @return the remoteRepos
     */
    public List<ArtifactRepository> getRemoteRepos() {
        return remoteRepos;
    }
    /**
     * @param remoteRepos the remoteRepos to set
     */
    public void setRemoteRepos(final List<ArtifactRepository> remoteRepos) {
        this.remoteRepos = remoteRepos;
    }
    /**
     * @return the repo
     */
    public ArtifactRepository getRepo() {
        return repo;
    }
    /**
     * @param repo the repo to set
     */
    public void setRepo(final ArtifactRepository repo) {
        this.repo = repo;
    }
    /**
     * @return the artifactResolver
     */
    public ArtifactResolver getArtifactResolver() {
        return artifactResolver;
    }
    /**
     * @param artifactResolver the artifactResolver to set
     */
    public void setArtifactResolver(final ArtifactResolver artifactResolver) {
        this.artifactResolver = artifactResolver;
    }
    /**
     * @return the factory
     */
    public ArtifactFactory getFactory() {
        return factory;
    }
    /**
     * @param factory the factory to set
     */
    public void setFactory(final ArtifactFactory factory) {
        this.factory = factory;
    }
}
