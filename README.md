# build-libraries
Build Libraries used in Sunshower's build
```


    post {
        always {
            skipRelease action: 'postProcess'
            sendEmails(
                    build: currentBuild,
                    rootUrl: "$JENKINS_URL",
                    buildUrl: "${JOB_URL}",
                    name: "$JOB_NAME $BUILD_DISPLAY_NAME"
            )
        }

    }

```
