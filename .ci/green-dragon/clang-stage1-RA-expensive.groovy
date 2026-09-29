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
    stages: ['checkout', 'build'],
    buildConfig: [
        stage: 1,
        build_type: 'cmake',
        build_target: 'all',
        cmake_type: 'default',
        assertions: true,
        timeout: 360,
        incremental: false,
        cmake_flags: [
            "-DLLVM_ENABLE_EXPENSIVE_CHECKS=ON",
            "-DLIBCXX_ENABLE_SHARED=OFF",
            "-DLIBCXX_ENABLE_STATIC=OFF",
            "-DLIBCXX_INCLUDE_TESTS=OFF",
            "-DLIBCXX_ENABLE_EXPERIMENTAL_LIBRARY=OFF"
        ],
        // Workaround for rdar://187125524: Do not time out the GPU arch detection
        // tools (e.g. nvptx-arch) under load.
        env_vars: [
            "CLANG_TOOLCHAIN_PROGRAM_TIMEOUT": "0"
        ]
    ],
    testConfig: [
        junit_patterns: [
            "clang-build/**/testresults.xunit.xml"
        ]
    ]
)
