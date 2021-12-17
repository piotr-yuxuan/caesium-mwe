# `piotr-yuxuan/caesium-mwe`

Using caesium with GraalVM native compilation seems to be thorny at the time being.

References:
- https://github.com/clj-easy/graalvm-clojure/blob/master/doc/clojure-graalvm-native-binary.md
- https://github.com/clj-easy/graal-build-time

```zsh
$ sw_vers
ProductName:	macOS
ProductVersion:	12.0.1
BuildVersion:	21A559

$ jenv local
graalvm64-17.0.1

$ java -version
openjdk version "17.0.1" 2021-10-19
OpenJDK Runtime Environment GraalVM CE 21.3.0 (build 17.0.1+12-jvmci-21.3-b05)
OpenJDK 64-Bit Server VM GraalVM CE 21.3.0 (build 17.0.1+12-jvmci-21.3-b05, mixed mode, sharing)

$ which native-image
/Library/Java/JavaVirtualMachines/graalvm-ce-java17-21.3.0/Contents/Home/bin/native-image

$ brew info graalvm-ce-java17
graalvm-ce-java17: 21.3.0
https://www.graalvm.org/
/usr/local/Caskroom/graalvm-ce-java17/21.3.0 (154B)
From: https://github.com/graalvm/homebrew-tap/blob/HEAD/Casks/graalvm-ce-java17.rb
==> Name
GraalVM Community Edition (Java 17)
==> Description
None
==> Artifacts
graalvm-ce-java17-21.3.0 -> /Library/Java/JavaVirtualMachines/graalvm-ce-java17-21.3.0 (Generic Artifact)
```

# Using `clj-easy/graalvm-clojure`

```zsh
$ lein do clean, uberjar-clj-easy, native-clj-easy
Compiling piotr-yuxuan.caesium-mwe.main
Created /Users/piotr-yuxuan/src/github.com/piotr-yuxuan/caesium-mwe/target/caesium-mwe-0.1.0.jar
Created /Users/piotr-yuxuan/src/github.com/piotr-yuxuan/caesium-mwe/target/caesium-mwe-0.1.0-standalone.jar
[./target/caesium-mwe-0.1.0:70006]    classlist:     773.63 ms,  0.96 GB
[./target/caesium-mwe-0.1.0:70006]        (cap):   1,544.56 ms,  0.96 GB
[clj-easy/graal-build-time] WARN: Single segment package found for class: byte_streams__init.class. This class has no package and it will not be added to the result packages.
[clj-easy/graal-build-time] WARN: Single segment package found for class: primitive_math__init.class. This class has no package and it will not be added to the result packages.
[clj-easy/graal-build-time] WARN: Single segment package found for class: clj_tuple__init.class. This class has no package and it will not be added to the result packages.
[clj-easy/graal-build-time] Registering packages for build time initialization: clojure, byte_streams, caesium, clj_easy, manifold, medley, piotr_yuxuan.caesium_mwe, riddley
[./target/caesium-mwe-0.1.0:70006]        setup:   3,020.19 ms,  0.96 GB
To see how the classes got initialized, use --trace-class-initialization=byte_streams$type_descriptor,byte_streams$vector_of,jnr.ffi.provider.jffi.AbstractAsmLibraryInterface,byte_streams$seq_of,byte_streams$convert,caesium.binding.Sodium$jnr$ffi$0,byte_streams$to_byte_array,byte_streams$stream_of
[./target/caesium-mwe-0.1.0:70006]     analysis:   4,136.89 ms,  1.67 GB
Error: Classes that should be initialized at run time got initialized during image building:
 byte_streams$type_descriptor was unintentionally initialized at build time. To see why byte_streams$type_descriptor got initialized use --trace-class-initialization=byte_streams$type_descriptor
byte_streams$vector_of was unintentionally initialized at build time. To see why byte_streams$vector_of got initialized use --trace-class-initialization=byte_streams$vector_of
jnr.ffi.provider.jffi.AbstractAsmLibraryInterface was unintentionally initialized at build time. To see why jnr.ffi.provider.jffi.AbstractAsmLibraryInterface got initialized use --trace-class-initialization=jnr.ffi.provider.jffi.AbstractAsmLibraryInterface
byte_streams$seq_of was unintentionally initialized at build time. To see why byte_streams$seq_of got initialized use --trace-class-initialization=byte_streams$seq_of
byte_streams$convert was unintentionally initialized at build time. To see why byte_streams$convert got initialized use --trace-class-initialization=byte_streams$convert
caesium.binding.Sodium$jnr$ffi$0 the class was requested to be initialized at run time (from feature InitAtBuildTimeFeature.duringSetup with 'caesium'). To see why caesium.binding.Sodium$jnr$ffi$0 got initialized use --trace-class-initialization=caesium.binding.Sodium$jnr$ffi$0
byte_streams$to_byte_array was unintentionally initialized at build time. To see why byte_streams$to_byte_array got initialized use --trace-class-initialization=byte_streams$to_byte_array
byte_streams$stream_of was unintentionally initialized at build time. To see why byte_streams$stream_of got initialized use --trace-class-initialization=byte_streams$stream_of

Error: Use -H:+ReportExceptionStackTraces to print stacktrace of underlying exception
[./target/caesium-mwe-0.1.0:70006]      [total]:   8,117.23 ms,  1.67 GB
# Printing build artifacts to: /Users/piotr-yuxuan/src/github.com/piotr-yuxuan/caesium-mwe/./target/caesium-mwe-0.1.0.build_artifacts.txt
Error: Image build request failed with exit status 1
```

# Using deprecated `--initialize-at-build-time`

```zsh
$ lein do clean, uberjar-initialize, native-initialize
Compiling piotr-yuxuan.caesium-mwe.main
Created /Users/piotr-yuxuan/src/github.com/piotr-yuxuan/caesium-mwe/target/caesium-mwe-0.1.0.jar
Created /Users/piotr-yuxuan/src/github.com/piotr-yuxuan/caesium-mwe/target/caesium-mwe-0.1.0-standalone.jar
[./target/caesium-mwe-0.1.0:69591]    classlist:     777.46 ms,  0.96 GB
--initialize-at-build-time without arguments has been deprecated when not using --diagnostics-mode. With GraalVM 22.0.0 --initialize-at-build-time will only work with --diagnostics-mode for debugging purposes.
The reason for deprecation is that --initalize-at-build-time does not compose, i.e., a single library can make assumptions that the whole classpath can be safely initialized at build time; that assumption is often incorrect.
[./target/caesium-mwe-0.1.0:69591]        (cap):   1,502.18 ms,  0.96 GB
[./target/caesium-mwe-0.1.0:69591]        setup:   2,706.02 ms,  0.96 GB
[./target/caesium-mwe-0.1.0:69591]     (clinit):     214.48 ms,  2.31 GB
[./target/caesium-mwe-0.1.0:69591]   (typeflow):   2,733.71 ms,  2.31 GB
[./target/caesium-mwe-0.1.0:69591]    (objects):   6,235.82 ms,  2.31 GB
[./target/caesium-mwe-0.1.0:69591]   (features):   1,739.07 ms,  2.31 GB
[./target/caesium-mwe-0.1.0:69591]     analysis:  11,614.40 ms,  2.31 GB
[./target/caesium-mwe-0.1.0:69591]     universe:     884.54 ms,  2.31 GB
[./target/caesium-mwe-0.1.0:69591]      (parse):     691.87 ms,  2.31 GB
[./target/caesium-mwe-0.1.0:69591]     (inline):   1,312.52 ms,  3.28 GB
[./target/caesium-mwe-0.1.0:69591]    (compile):   8,556.00 ms,  4.17 GB
[./target/caesium-mwe-0.1.0:69591]      compile:  11,526.17 ms,  4.17 GB
[./target/caesium-mwe-0.1.0:69591]        image:   1,558.72 ms,  4.75 GB
[./target/caesium-mwe-0.1.0:69591]        write:     469.49 ms,  4.75 GB
[./target/caesium-mwe-0.1.0:69591]      [total]:  29,722.63 ms,  4.75 GB
# Printing build artifacts to: /Users/piotr-yuxuan/src/github.com/piotr-yuxuan/caesium-mwe/./target/caesium-mwe-0.1.0.build_artifacts.txt
```

```zsh
$ ./target/caesium-mwe-0.1.0
Exception in thread "main" java.lang.UnsatisfiedLinkError: com.kenai.jffi.Foreign.invokeN4O3(JJJJJJLjava/lang/Object;IIILjava/lang/Object;IIILjava/lang/Object;III)J [symbol: Java_com_kenai_jffi_Foreign_invokeN4O3 or Java_com_kenai_jffi_Foreign_invokeN4O3__JJJJJJLjava_lang_Object_2IIILjava_lang_Object_2IIILjava_lang_Object_2III]
	at com.oracle.svm.jni.access.JNINativeLinkage.getOrFindEntryPoint(JNINativeLinkage.java:153)
	at com.oracle.svm.jni.JNIGeneratedMethodSupport.nativeCallAddress(JNIGeneratedMethodSupport.java:57)
	at com.kenai.jffi.Foreign.invokeN4O3(Foreign.java)
	at com.kenai.jffi.Invoker.invokeN4(Invoker.java:1249)
	at caesium.binding.Sodium$jnr$ffi$0.crypto_box_seal(Unknown Source)
	at caesium.crypto.box$box_seal_to_buf_BANG_.invokeStatic(box.clj:112)
	at caesium.crypto.box$box_seal.invokeStatic(box.clj:187)
	at caesium.crypto.box$anonymous_encrypt.invokeStatic(box.clj:234)
	at piotr_yuxuan.caesium_mwe.main$example.invokeStatic(main.clj:11)
	at piotr_yuxuan.caesium_mwe.main$_main.invokeStatic(main.clj:13)
	at piotr_yuxuan.caesium_mwe.main$_main.doInvoke(main.clj:13)
	at clojure.lang.RestFn.invoke(RestFn.java:397)
	at clojure.lang.AFn.applyToHelper(AFn.java:152)
	at clojure.lang.RestFn.applyTo(RestFn.java:132)
	at piotr_yuxuan.caesium_mwe.main.main(Unknown Source)
```
