jsonvalidator
=============

A super-simple json validation tool using jackson

#### Usage
Use the included shell script after building:
```
$ jsonvalidator path/to/file.json
```
or
```
$ find . -type f -name "*.json" | jsonvalidator
```

#### Build
Build with maven:
```
$ mvn package
```
