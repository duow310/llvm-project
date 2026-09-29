branchName = 'main'

library identifier: "zorg-shared-lib@dwang/migrate-to-macos-arm64-xcode-27-test",
        retriever: modernSCM([
            $class: 'GitSCMSource',
            remote: "https://github.com/duow310/llvm-zorg.git",
            credentialsId: scm.userRemoteConfigs[0].credentialsId
        ])

clangPipeline(
    jobName: env.JOB_NAME,
    zorgBranch: branchName,
    testSourceBranch: 'main',
    testPollSpec: 'H H/4 * * *',
    buildConfig: [
        stage: 1,
        build_type: 'cmake',
        cmake_type: 'RelWithDebInfo',
        assertions: true,
        projects: 'clang;clang-tools-extra',
        runtimes: 'compiler-rt',
        timeout: 120,
        incremental: false,
        skipTrigger: env.BRANCH_NAME?.startsWith('release/') ?: false
    ],
    testConfig: [
        test_type: 'testlong',
        timeout: 120,
        junit_patterns: [
            "clang-build/**/testresults.xunit.xml"
        ]
    ],
    triggeredJobs: [
        'Green-Dragon-Testing/clang-stage2-cmake-RgSan_relay-apple-silicon',
        'Green-Dragon-Testing/clang-stage2-Rthinlto_relay-apple-silicon',
        'Green-Dragon-Testing/relay-lnt-ctmark-apple-silicon',
        'Green-Dragon-Testing/relay-test-suite-verify-machineinstrs-apple-silicon'
    ]
)
