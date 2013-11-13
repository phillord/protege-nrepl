;; The contents of this file are subject to the LGPL License, Version 3.0.
;;
;; Copyright (C) 2013, Phillip Lord, Newcastle University
;;
;; This program is free software: you can redistribute it and/or modify it
;; under the terms of the GNU Lesser General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or (at your
;; option) any later version.
;;
;; This program is distributed in the hope that it will be useful, but WITHOUT
;; ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
;; FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
;; for more details.
;;
;; You should have received a copy of the GNU Lesser General Public License
;; along with this program. If not, see http://www.gnu.org/licenses/.

(ns protege.model)

(def ^{:dynamic true
       :doc "The OWLModelManager for the Protege Instance from which the REPL
is launched."}
  *owl-model-manager* nil)

(def ^{:dynamic true
       :doc "The OWLEditorKit for the Protege Instance from which the REPL is
launched."}
  *owl-editor-kit* nil)

(def ^{:dynamic true
       :doc "The OWLWorkspace for the Protege Instance from which the REPL is
launched."}
  *owl-work-space* nil)

(def auto-connect-on-default (ref false))

(defn active-ontology
  ([]
     (.getActiveOntology *owl-model-manager*))
  ([o]
     (.setActiveOntology *owl-model-manager* o)))


(defn selected-object
  ([workspace]
     (-> workspace
         .getOWLSelectionModel
         .getSelectedObject))
  ([workspace entity]
     (-> workspace
         .getOWLSelectionModel
         (.setSelectedObject entity))))
