# Ltt.rs for Unix

A very, very rudimentary implementation of a TUI email client that uses [jmap-mua](https://github.com/inputmice/jmap). It mostly exists for development purposes and to quickly test new features in `jmap-mua`. Code quality (especially in regards to the TUI) is currently pretty low.

![screenshot of lttrs-cli](https://gultsch.de/files/lttrs-cli.png)

### Build
```
mvn package
```

### Usage
```
java -jar target/lttrs-cli-0.0.1.jar username@domain.tld password
```
Alternatively, if your provider doesn’t have a `.well-know/jmap`, you can specify the URL with
```
java -jar target/lttrs-cli-0.0.1.jar  https://jmap.fastmail.com/.well-known/jmap username@fastmail.com password
```
