branchName = 'main'

library identifier: "zorg-shared-lib@dwang/migrate-to-macos-arm64-xcode-27-test",
        retriever: modernSCM([
            $class: 'GitSCMSource',
            remote: "https://github.com/duow310/llvm-zorg.git",
            credentialsId: scm.userRemoteConfigs[0].credentialsId
        ])

clangPipeline([
    jobName: env.JOB_NAME,
    zorgBranch: branchName,
    testSourceBranch: 'main',
    buildConfig: [
        build_type: "clang",
        cmake_type: "RelWithDebInfo",
        thinlto: true,
        projects: "clang",
        runtimes: "libunwind;compiler-rt",
        stage: 2,
        timeout: 1200,
        incremental: false,
        stage1Job: 'clang-stage1-RA-apple-silicon',
        cmake_flags: [
            "-DCMAKE_DSYMUTIL=\${WORKSPACE}/host-compiler/bin/dsymutil"
        ]
    ],
    testConfig: [
        test_command: "clang",
        test_type: "test",
        timeout: 420,
        junit_patterns: [
            "clang-build/**/testresults.xunit.xml"
        ]
    ]
])