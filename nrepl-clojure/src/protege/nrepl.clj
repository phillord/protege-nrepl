(ns protege.nrepl
  (:require [clojure.tools.nrepl.server]
            [clojure.java.io]
            [protege.model]))

;; borrowed from lein
(defn getenv
  "Wrap System/getenv for testing purposes."
  [name]
  (System/getenv name))

(defn protege-nrepl-home
  "Return full path to the user's protege home directory."
  []
  (let [protege-nrepl-home (getenv "PROTEGE_NREPL_HOME")
        protege-nrepl-home (or (and protege-nrepl-home (clojure.java.io/file protege-nrepl-home))
                      (clojure.java.io/file (System/getProperty "user.home") ".protege-nrepl"))]
    (.getAbsolutePath (doto protege-nrepl-home .mkdirs))))

(def init
  "Load the user's ~/.protege-nrepl/init.clj file, if present."
  (memoize (fn []
             (let [init-file (clojure.java.io/file (protege-nrepl-home) "init.clj")]
               (when (.exists init-file)
                 (try (load-file (.getAbsolutePath init-file))
                      (catch Exception e
                        (.printStackTrace e))))))))

;; hook system -- identical to tawny.util -- ah well!
(defn make-hook
  "Make a hook."
  []
  (atom []))

(defn add-hook
  "Add func to hook."
  [hook func]
  (do
    (when-not
        (some #{func} @hook)
      (swap! hook conj func))
    @hook))

(defn remove-hook
  "Remove func from hook."
  [hook func]
  (swap! hook
         (partial
          remove #{func})))

(defn clear-hook
  "Empty the hook."
  [hook]
  (reset! hook []))

(defn run-hook
  "Run the hook with optional arguments. Hook functions are run in the order
that they were added."
  ([hook]
     (doseq [func @hook] (func)))
  ([hook & rest]
     (doseq [func @hook] (apply func rest))))


(def start-server-hook (make-hook))

(def servers (atom {}))


(defn start-server
  ([editorkit port]
     (binding [protege.model/*owl-editor-kit* editorkit
               protege.model/*owl-work-space*
               (.getOWLWorkspace editorkit)
               protege.model/*owl-model-manager*
               (.getOWLModelManager editorkit)]
       (run-hook start-server-hook)
       (let [server
             (clojure.tools.nrepl.server/start-server :port port)]
         (swap! servers assoc editorkit server)))))

(defn stop-server [editorkit server]
  (swap! servers dissoc editorkit)
  (clojure.tools.nrepl.server/stop-server server))
