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
    stages: ['checkout', 'fetch', 'build'],
    buildConfig: [
        stage: 2,
        stage1Job: 'clang-stage1-RA-apple-silicon',
        build_type: 'cmake',
        cmake_type: 'RelWithDebInfo',
        projects: 'clang;clang-tools-extra',
        runtimes: 'libcxx;libcxxabi;compiler-rt',
        cmake_build_target: 'LTO',
        noinstall: true,
        thinlto: true,
        sanitizer: 'Thread',
        incremental: false,
        cmake_flags: [
            "-DLLVM_BUILD_RUNTIME=OFF"
        ],
        env_vars: [
            "DYLD_LIBRARY_PATH": "\$WORKSPACE/host-compiler/lib/"
        ]
    ]
)
