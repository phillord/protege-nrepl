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

This software is in ALPHA release.


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
