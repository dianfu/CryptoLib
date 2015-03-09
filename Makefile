include Makefile.common

MVN:=mvn
SBT:=./sbt

all: cryptostream

CRYPTOSTREAM_OUT:=$(TARGET)/$(cryptostream)-$(os_arch)
CRYPTOSTREAM_OBJ:=$(addprefix $(CRYPTOSTREAM_OUT)/,OpensslSecureRandom.o OpensslCipherNative.o)

ifeq ($(OS_NAME),SunOS)
  TAR:= gtar
else
  TAR:= tar
endif

$(TARGET)/jni-classes/com/intel/cryptostream/OpensslCipherNative.class : $(SRC)/com/intel/cryptostream/OpensslCipherNative.java
	@mkdir -p $(TARGET)/jni-classes
	$(JAVAC) -source 1.6 -target 1.6 -d $(TARGET)/jni-classes -sourcepath $(SRC) $<

$(TARGET)/jni-classes/com/intel/cryptostream/OpensslCipherNative.h: $(TARGET)/jni-classes/com/intel/cryptostream/OpensslCipherNative.class
	$(JAVAH) -force -classpath $(TARGET)/jni-classes -o $@ com.intel.cryptostream.OpensslCipherNative

$(CRYPTOSTREAM_OUT)/OpensslCipherNative.o : $(SRC_NATIVE)/com/intel/cryptostream/OpensslCipherNative.c $(TARGET)/jni-classes/com/intel/cryptostream/OpensslCipherNative.h  
	@mkdir -p $(@D)
	$(CC) $(CFLAGS) -c $< -o $@

$(CRYPTOSTREAM_OUT)/OpensslSecureRandom.o :
	@mkdir -p $(@D)

$(CRYPTOSTREAM_OUT)/$(LIBNAME): $(CRYPTOSTREAM_OBJ)
	$(CXX) $(CXXFLAGS) -o $@ $+ $(LINKFLAGS) 
	$(STRIP) $@

clean-native: 
	rm -rf $(CRYPTOSTREAM_OUT)

clean:
	rm -rf $(TARGET)

NATIVE_DIR:=src/main/resources/com/intel/cryptostream/native/$(OS_NAME)/$(OS_ARCH)
NATIVE_TARGET_DIR:=$(TARGET)/classes/com/intel/cryptostream/native/$(OS_NAME)/$(OS_ARCH)
NATIVE_DLL:=$(NATIVE_DIR)/$(LIBNAME)

cryptostream-jar-version:=cryptostream-$(shell perl -npe "s/version in ThisBuild\s+:=\s+\"(.*)\"/\1/" version.sbt | sed -e "/^$$/d")

native: $(NATIVE_DLL)
	less $^

cryptostream: native $(TARGET)/$(cryptostream-jar-version).jar

$(NATIVE_DLL): $(CRYPTOSTREAM_OUT)/$(LIBNAME) 
	@mkdir -p $(@D)
	cp $< $@
	@mkdir -p $(NATIVE_TARGET_DIR)
	cp $< $(NATIVE_TARGET_DIR)/$(LIBNAME)


package: $(TARGET)/$(cryptostream-jar-version).jar

$(TARGET)/$(cryptostream-jar-version).jar: 
	$(SBT) package 

test: $(NATIVE_DLL)
	$(SBT) test

win32: 
	$(MAKE) native CROSS_PREFIX=i686-w64-mingw32- OS_NAME=Windows OS_ARCH=x86

# for cross-compilation on Ubuntu, install the g++-mingw-w64-x86-64 package
win64:
	$(MAKE) native CROSS_PREFIX=x86_64-w64-mingw32- OS_NAME=Windows OS_ARCH=x86_64

mac32: 
	$(MAKE) native OS_NAME=Mac OS_ARCH=x86

linux32:
	$(MAKE) native OS_NAME=Linux OS_ARCH=x86

freebsd64:
	$(MAKE) native OS_NAME=FreeBSD OS_ARCH=x86_64

# for cross-compilation on Ubuntu, install the g++-arm-linux-gnueabi package
linux-arm:
	$(MAKE) native CROSS_PREFIX=arm-linux-gnueabi- OS_NAME=Linux OS_ARCH=arm

# for cross-compilation on Ubuntu, install the g++-arm-linux-gnueabihf package
linux-armhf:
	$(MAKE) native CROSS_PREFIX=arm-linux-gnueabihf- OS_NAME=Linux OS_ARCH=armhf

# for cross-compilation on Ubuntu, install the g++-aarch64-linux-gnu
linux-aarch64:
	$(MAKE) native CROSS_PREFIX=aarch64-linux-gnu- OS_NAME=Linux OS_ARCH=aarch64

clean-native-linux32:
	$(MAKE) clean-native OS_NAME=Linux OS_ARCH=x86

clean-native-win32:
	$(MAKE) clean-native OS_NAME=Windows OS_ARCH=x86

javadoc:
	$(SBT) doc

install-m2:
	$(SBT) publishM2

