josuFramework - Getting Started
=============

.. toctree::
   :maxdepth: 2
   :caption: Contents:

Setting up josuFramework for making a game.
=========================================
josuFramework is deployed to JitPack.

Gradle
-----------

```gradle
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
Add this to your root settings.gradle at the end of repositories.

Add the dependency:

```gradle
	dependencies {
	        implementation 'com.github.meowcat767:josuframework:Tag'
	}
```

And done! Reload Gradle and josuFramework is ready for use!

Maven
-----------

Add the repository to your pom.xml:

```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

Now add the dependency:

```xml
	<dependency>
	    <groupId>com.github.meowcat767</groupId>
	    <artifactId>josuframework</artifactId>
	    <version>Tag</version>
	</dependency>
```

And done! Reload Gradle and josuFramework is ready for use!

sbt
-----------

Add the repo to your build.sbt at the end of resolvers:

```
resolvers += "jitpack" at "https://jitpack.io"
```

Add the dependency:

```
libraryDependencies += "com.github.meowcat767" % "josuframework" % "Tag"
```




