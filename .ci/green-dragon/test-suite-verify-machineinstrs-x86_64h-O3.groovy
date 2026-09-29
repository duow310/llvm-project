branchName = 'main'

library identifier: "zorg-shared-lib@dwang/migrate-to-macos-arm64-xcode-27-test",
        retriever: modernSCM([
            $class: 'GitSCMSource',
            remote: "https://github.com/duow310/llvm-zorg.git",
            credentialsId: scm.userRemoteConfigs[0].credentialsId
        ])

common.testsuite_pipeline(label: 'macos-arm64-xcode-27') {
    sh """
CMAKE_FLAGS+=" -C ../config/tasks/cmake/caches/target-x86_64h-macos.cmake"
CMAKE_FLAGS+=" -C ../config/tasks/cmake/caches/opt-O3.cmake"
config/tasks/task jenkinsrun config/tasks/test-suite-verify-machineinstrs.sh -a compiler="${params.ARTIFACT}" -D CMAKE_FLAGS="\${CMAKE_FLAGS}"
    """
}
