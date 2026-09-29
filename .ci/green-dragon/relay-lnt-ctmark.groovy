branchName = 'main'

properties([
    disableConcurrentBuilds()
])

library identifier: "zorg-shared-lib@dwang/migrate-to-macos-arm64-xcode-27-test",
        retriever: modernSCM([
            $class: 'GitSCMSource',
            remote: "https://github.com/duow310/llvm-zorg.git",
            credentialsId: scm.userRemoteConfigs[0].credentialsId
        ])

// TEST ONLY: Read the last good build of the clang-stage1-RA-apple-silicon copy.
relay.pipeline([
    "Green-Dragon-Testing/lnt-ctmark-aarch64-O0-g-apple-silicon",
    "Green-Dragon-Testing/lnt-ctmark-aarch64-O3-flto-apple-silicon",
    "Green-Dragon-Testing/lnt-ctmark-aarch64-Os-apple-silicon",
    "Green-Dragon-Testing/lnt-ctmark-aarch64-Oz-apple-silicon"
], 'Green-Dragon-Testing/clang-stage1-RA-apple-silicon/latest',
   'Green-Dragon-Testing/clang-stage1-RA-apple-silicon/last_good_build.properties')