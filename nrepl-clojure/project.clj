(defproject uk.org.russet/nrepl-clojure "1.1.0-SNAPSHOT"
  :description "Launch a nrepl client inside protege"
  :license {:name "LGPL"
            :url "http://www.gnu.org/licenses/lgpl-3.0.txt"
            :distribution :repo}
  :scm {:url "https://github.com/phillord/protege-nrepl.git"
        :name "git"}
  :url "https://github.com/phillord/protege-nrepl"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.nrepl "0.2.3"]
                 [edu.stanford.protege/org.protege.editor.core.application
                  "5.0.0-beta-16-SNAPSHOT"]
                 [com.cemerick/pomegranate "0.3.0"]]
  ;; this is a hack workaround to
  ;; https://github.com/technomancy/leiningen/issues/1569 which otherwise adds
  ;; nrepl tools as a test dependency in the pom (which means it doesn't get
  ;; included in protege-nrepl.
  ;; This bug has been fixed in 2.4.3
  ;;:profiles {:base {:dependencies ^:replace []}}
  )
