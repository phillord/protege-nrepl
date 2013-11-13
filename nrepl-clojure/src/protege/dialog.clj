(ns protege.dialog
  (:require [protege nrepl model])
  (:import [java.awt BorderLayout Color]
           [java.awt.event ActionListener]
           [javax.swing BoxLayout JButton JLabel JPanel
            JTextField]))

(def last-port (ref 7827))
;; map between model manager and port
(def servers (ref {}))

(defn action-listener [f]
  (proxy [ActionListener] []
    (actionPerformed [event]
      (f event))))

(defn start-server-action [editorkit connect disconnect status event]
  (dosync
   (let [s (protege.nrepl/start-server
            editorkit
            @last-port)]
     (alter servers merge {editorkit s})
     (.setEnabled disconnect true)
     (.setEnabled connect false)
     (.setText status
                (str "Connected on port: " @last-port) )
     (alter last-port inc))))

(defn stop-server-action [editorkit connect disconnect status event]
  (dosync
   (let [s (get @servers editorkit)]
     (alter servers dissoc editorkit)
     (.setEnabled connect true)
     (.setEnabled disconnect false)
     (.setText status "Disconnected")
     (protege.nrepl/stop-server editorkit s))))

(defn new-dialog-panel [editorkit]
  (let [pn (JPanel.)
        ;; this one takes so a text box with next available port
        ;; and a status bar saying what, er, the status is
        middle (JPanel.)
        ;; takes a set of buttons, "Connect", "Disconnect", "Close"
        ;; greyed as appopriate
        south (JPanel.)
        button (JPanel.)
        port-label (JLabel. "Port")
        port (JTextField. (str @last-port) 20)
        status (JLabel. "Disconnected")
        connect (JButton. "Connect")
        disconnect (JButton. "Disconnect")
        connect-fn
        (partial start-server-action
                 editorkit
                 connect disconnect
                 status)]
    (.addActionListener connect
                        (action-listener connect-fn))
    (doto disconnect
      (.setEnabled false)
      (.addActionListener
       (action-listener
        (partial stop-server-action
                 editorkit connect disconnect
                 status))))

    (when @protege.model/auto-connect-on-default
      (connect-fn nil))

    (doto pn
      (.setLayout (BorderLayout.))
      (.add middle BorderLayout/CENTER)
      (.add south BorderLayout/SOUTH))
    (doto south
      (.setLayout (BorderLayout.))
      (.add button BorderLayout/NORTH)
      (.add status BorderLayout/SOUTH))
    (doto button
      (.setLayout (BoxLayout. button BoxLayout/X_AXIS))
      (.add connect)
      (.add disconnect))
    (doto middle
      (.setLayout (BorderLayout.))
      (.add port-label BorderLayout/WEST)
      (.add port BorderLayout/CENTER))
    pn))



(defn new-dialog[manager]
  (let [fm (javax.swing.JFrame.)
        cp (.getContentPane fm)]
    (.add cp (new-dialog-panel manager))
    (.pack fm)
    (.setVisible fm true)
    fm))
