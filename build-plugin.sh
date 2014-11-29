## build just the plugin and dump it to protege

## this shouldn't be necessary -- maven should launch lein
# cd ~/src/knowledge/protege-nrepl/nrepl-clojure
# lein pom
# lein install
cd nrepl-plugin/
mvn -DskipTests=true install
cp target/uk.org.russet.protege.nrepl-0.1.0-SNAPSHOT.jar ~/Protege/plugins/

echo [COMPLETE] Dumped to ~/Protege
