@rem batch because gpg has problems when run from git console



./mvnw clean deploy -P ci,release



@echo to drop release:       mvn nexus-staging:drop    -P release
@echo to promote release:    mvn nexus-staging:release -P release
