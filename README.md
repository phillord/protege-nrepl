Protege-Nrepl
=============

This is a plugin for the Protege Ontology Editor, which enables
[nrepl](https://github.com/clojure/tools.nrepl) connection to an existing
editor. The [pomegranate](https://github.com/cemerick/pomegranate) library is
included in the bundle, which means that new maven dependencies or classpath
directories can be added dynamically. Access to the Protege internal data
structures is possible, meaning that the Protege instance can be manipulated
remotely.

Although, it is not tied to it, Protege-Nrepl is being developed for use
with the [Tawny-OWL](https://github.com/phillord/tawny-owl) library;
currently, the release version of Protege and Tawny use different versions of
the OWL API, or a specialized build of Protege is needed.

## Usage

Install the plugin as a normal Protege plugin. This adds a single menu item
into "Tools", called NREPL, which can be used to launch a REPL for the current
Protege workspace.

The `protege.model` Clojure namespace provides a number of different vars,
dynamically scoped to the current workspace which you can use to affect the
running Protege instance (if you don't want to do this, there isn't much point
in launching a REPL within Protege!), as well as some utility functions.

As Clojure itself and the nrepl server, protege-nrepl packages
[pomegranate](https://github.com/cemerick/pomegranate) which enables the
addition of new dependencies or directories to the classpath in the existing
JVM. The easiest way to do this is to use
[lein-sync](https://github.com/phillord/lein-sync) which creates the relevant
function calls from a leiningen project.

## Init

protege-nrepl loads an init file when launching clojure (this happens when the
NREPL menu item is clicked and *not* when Protege is launched. This file is
found at `~/.protege-nrepl/init.clj`, or equivalent on different OSes.

I use the following which loads tawny-protege, which links between the core
protege data structures and tawny. It also turns off two dialogs which make
little sense when using Protege as a viewer.

    ;; force loading of tawny
    (cemerick.pomegranate/add-dependencies :coordinates '[[uk.org.russet/tawny-protege "1.0"]]
                                          :repositories (merge cemerick.pomegranate.aether/maven-central
                                              {"clojars" "http://clojars.org/repo"}))
    ;; and monkey patch the thing
    (require 'tawny.protege-nrepl)

    ;; initing the dialog takes ages -- so auto connect
    (dosync (ref-set protege.model/auto-connect-on-default true))


## Use with Tawny-OWL

Currently Tawny uses the OWL API version 3.4.8 and depends on (one!) feature
introduced in it, while Protege uses version 3.4.2. So, if you use tawny
inside Protege errors will occasionally happen (if you use datatypes
essentially). Unfortunately, upgrading Protege to 3.4.8 requires a slight
patch to Protege.

I have built an  **unsupported**, **pre-release** version of Protege with
protege-nrepl (and [protege-tawny](https://github.com/phillord/protege-tawny))
installed, which is accessible from
[here](http://purl.org/ontolink/protege-nrepl). Please don't ask the Protege
team for help with this! This should be a stop-gap solution; we hope that
protege-nrepl will work in a standard Protege install. 


## Mailing List

There is a [mailing list](mailto:tawny-owl@googlegroups.com)

## License

The contents of this file are subject to the LGPL License, Version 3.0.

Copyright (C) 2012, 2013, Newcastle University

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation, either version 3 of the License, or (at your option) any
later version.

This program is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along
with this program. If not, see http://www.gnu.org/licenses/.
