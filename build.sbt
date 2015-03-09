import SonatypeKeys._

sonatypeSettings

name := "snappy-java"

organization := "org.xerial.snappy" 

organizationName := "xerial.org"

description  := "snappy-java: A fast compression/decompression library"

profileName := "org.xerial" 

pomExtra := {
   <url>https://github.comm/xerial/snappy-java</url>
   <licenses>
       <license>
           <name>The Apache Software License, Version 2.0</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
            <distribution>repo</distribution>
        </license>
    </licenses>
    <developers>
        <developer>
            <id>leo</id>
            <name>Taro L. Saito</name>
            <email>leo@xerial.org</email>
            <organization>Xerial Project</organization>
            <roles>
                <role>Architect</role>
                <role>Project Manager</role>
                <role>Chief Developer</role>
            </roles>
            <timezone>+9</timezone>
        </developer>
    </developers>
    <issueManagement>
        <system>GitHub</system>
        <url>http://github.com/xerial/snappy-java/issues/list</url>
    </issueManagement>
    <inceptionYear>2011</inceptionYear>
    <scm>
        <connection>scm:git@github.com:xerial/snappy-java.git</connection>
        <developerConnection>scm:git:git@github.com:xerial/snappy-java.git</developerConnection>
        <url>git@github.com:xerial/snappy-java.git</url>
    </scm>
}

scalaVersion := "2.11.1"

javacOptions in (Compile, compile) ++= Seq("-encoding", "UTF-8", "-Xlint:unchecked", "-Xlint:deprecation", "-source", "1.6", "-target", "1.6")

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

concurrentRestrictions in Global := Seq(Tags.limit(Tags.Test, 1))

autoScalaLibrary := false

crossPaths := false

logBuffered in Test := false

incOptions := incOptions.value.withNameHashing(true)

libraryDependencies ++= Seq(
   "junit" % "junit" % "4.8.2" % "test",
   "org.codehaus.plexus" % "plexus-classworlds" % "2.4" % "test",
   "org.xerial.java" % "xerial-core" % "2.1" % "test",
   "org.xerial" % "xerial-core" % "3.2.3" % "test",
   "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test",
   "org.osgi" % "org.osgi.core" % "4.3.0" % "provided",
   "com.novocode" % "junit-interface" % "0.10" % "test"
)

osgiSettings


OsgiKeys.exportPackage := Seq("org.xerial.snappy", "org.xerial.snappy.buffer")

OsgiKeys.bundleSymbolicName := "org.xerial.snappy.snappy-java"

OsgiKeys.bundleActivator := Option("org.xerial.snappy.SnappyBundleActivator")

OsgiKeys.importPackage := Seq("""org.osgi.framework;version="[1.5,2)"""")

OsgiKeys.additionalHeaders := Map(
  "Bundle-NativeCode" -> Seq(
"com/intel/cryptostream/native/Windows/x86_64/cryptostream.dll;osname=win32;processor=x86-64",
"com/intel/cryptostream/native/Windows/x86/cryptostream.dll;osname=win32;processor=x86",
"com/intel/cryptostream/native/Mac/x86/libcryptostream.jnilib;osname=macosx;processor=x86",
"com/intel/cryptostream/native/Mac/x86_64/libcryptostream.jnilib;osname=macosx;processor=x86-64",
"com/intel/cryptostream/native/Linux/x86_64/libcryptostream.so;osname=linux;processor=x86-64",
"com/intel/cryptostream/native/Linux/x86/libcryptostream.so;osname=linux;processor=x86",
"com/intel/cryptostream/native/Linux/aarch64/libcryptostream.so;osname=linux;processor=aarch64",
"com/intel/cryptostream/native/Linux/arm/libcryptostream.so;osname=linux;processor=arm",
"com/intel/cryptostream/native/Linux/ppc64/libcryptostream.so;osname=linux;processor=ppc64",
"com/intel/cryptostream/native/Linux/ppc64le/libcryptostream.so;osname=linux;processor=ppc64le",
"com/intel/cryptostream/native/AIX/ppc64/libcryptostream.a;osname=aix;processor=ppc64",
"com/intel/cryptostream/native/SunOS/x86/libcryptostream.so;osname=sunos;processor=x86",
"com/intel/cryptostream/native/SunOS/x86_64/libcryptostream.so;osname=sunos;processor=x86-64",
"com/intel/cryptostream/native/SunOS/sparc/libcryptostream.so;osname=sunos;processor=sparc"
).mkString(","),
 "Bundle-DocURL" -> "http://www.xerial.org/",
 "Bundle-License" -> "http://www.apache.org/licenses/LICENSE-2.0.txt",
 "Bundle-ActivationPolicy" -> "lazy",
 "Bundle-Name" -> "snappy-java: A fast compression/decompression library"
)