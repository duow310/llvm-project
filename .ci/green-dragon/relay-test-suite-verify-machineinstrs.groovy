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

jobs = [
    "Green-Dragon-Testing/test-suite-verify-machineinstrs-x86_64-O0-g-apple-silicon",
    "Green-Dragon-Testing/test-suite-verify-machineinstrs-x86_64-O3-apple-silicon",
    "Green-Dragon-Testing/test-suite-verify-machineinstrs-x86_64h-O3-apple-silicon",
    "Green-Dragon-Testing/test-suite-verify-machineinstrs-aarch64-globalisel-O0-g-apple-silicon",
    "Green-Dragon-Testing/test-suite-verify-machineinstrs-aarch64-O0-g-apple-silicon",
    "Green-Dragon-Testing/test-suite-verify-machineinstrs-aarch64-O3-apple-silicon"
]

// TEST ONLY: Read the last good build of the clang-stage1-RA-apple-silicon copy.
relay.pipeline(jobs, 'Green-Dragon-Testing/clang-stage1-RA-apple-silicon/latest',
               'Green-Dragon-Testing/clang-stage1-RA-apple-silicon/last_good_build.properties')
