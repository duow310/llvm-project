branchName = 'main'

library identifier: "zorg-shared-lib@dwang/migrate-to-macos-arm64-xcode-27-test",
        retriever: modernSCM([
            $class: 'GitSCMSource',
            remote: "https://github.com/duow310/llvm-zorg.git",
            credentialsId: scm.userRemoteConfigs[0].credentialsId
        ])

common.testsuite_pipeline(label: 'macos-arm64-xcode-27') {
    timeout(30) {
        sh """
LNT_FLAGS+=" -C config/tasks/cmake/caches/target-arm64-iphoneos.cmake"
LNT_FLAGS+=" -C config/tasks/cmake/caches/opt-O0-g.cmake"
config/tasks/task jenkinsrun config/tasks/lnt-ctmark.sh -a compiler="${params.ARTIFACT}" -D LNT_FLAGS="\${LNT_FLAGS}"
        """
    }
}