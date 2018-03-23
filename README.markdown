# Logback MainDiscriminator

The logback MainDiscriminator allows logging separation based on the
class name of the main method. This provides easy separation when a
project contains multiple main entry points without manually setting
up separate log configuration.

The MainDiscriminator is similar to the ContextJNDISelector, but does
not require setting separate logging contexts.

## Releases and Dependency Information

Releases are on [Clojars](https://clojars.org/com.grzm/logback-main-discriminator).

### Clojure [CLI/deps.edn][deps] coordinates:

```clojure
{com.grzm/logback-main-discriminator {:mvn/version "0.1.0"}}
```

### [Leiningen][]/[Boot][] dependency information:

```clojure
[com.grzm/logback-main-discriminator "0.1.0"]
```

### [Maven] dependency information:

```xml
<dependency>
  <groupId>com.grzm</groupId>
  <artifactId>logback-main-discriminator</artifactId>
  <version>0.1.0</version>
</dependency>
```

[deps]: https://clojure.org/reference/deps_and_cli
[Leiningen]: http://leiningen.org/
[Boot]: http://boot-clj.com
[Maven]: http://maven.apache.org/

## Usage

To use the Logback MainDiscriminator, require `com.grzm.logback.MainDiscriminator`
and call the `set-value` function in the `-main` function.

```clojure
(ns com.example.server
  (:gen-class)
  (:require
   [com.grzm.logback.MainDiscriminator :as main-discriminator]))

(defn -main
  [& args]
  ;; call set-value
  (main-discriminator/set-value)
  ;; do your thing!
  )
```

You can also set an arbitrary value by passing an argument to
`set-value`. For example

```clojure
(main-discriminator/set-value "someArbitraryValue")
```

In your `logback.xml` file, include a `<discriminator>` element with
the `class` attribute set to `com.grzm.logback.MainDiscriminator`. The
value set by `set-value` will be accessible via the `mainClassName`
property.

```xml
  <appender name="SIFT" class="ch.qos.logback.classic.sift.SiftingAppender">
  <!-- Add a discriminator element specifying the MainDiscriminator -->
    <discriminator class="com.grzm.logback.MainDiscriminator" />
    <sift>
      <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- encoder defaults to ch.qos.logback.classic.encoder.PatternLayoutEncoder -->
        <encoder>
          <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>

        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
          <!-- rollover daily -->
          <!-- Use mainClassName value provided by the MainDiscriminator -->
          <fileNamePattern>logs/${mainClassName}-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
          <!-- or whenever the file size reaches 64 MB -->
          <maxFileSize>64 MB</maxFileSize>
        </rollingPolicy>

        <!-- Safely log to the same file from multiple JVMs. Degrades performance! -->
        <prudent>true</prudent>
      </appender>
    </sift>
  </appender>
```

For more details on logging separation, see the [Logback manual][logging-separation].

[logging-separation]: https://logback.qos.ch/manual/loggingSeparation.html

## License

© 2018 Michael Glaesemann

Released under the MIT License. See [LICENSE](LICENSE) for details.
