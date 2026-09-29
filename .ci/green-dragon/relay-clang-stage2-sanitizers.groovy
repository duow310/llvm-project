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
    "Green-Dragon-Testing/clang-stage2-cmake-RgSan-apple-silicon",
    "Green-Dragon-Testing/clang-stage2-cmake-RgTSan-apple-silicon"
], 'Green-Dragon-Testing/clang-stage1-RA-apple-silicon/latest',
   'Green-Dragon-Testing/clang-stage1-RA-apple-silicon/last_good_build.properties')