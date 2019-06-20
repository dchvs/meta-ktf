DESCRIPTION = "Google C++ Testing Framework"
HOMEPAGE = "https://github.com/google/googletest"
LICENSE = "BSD-3-Clause"

BB_STRICT_CHECKSUM = "0"
LIC_FILES_CHKSUM = "file:///home/daniel/Downloads/googletest-master/LICENSE;md5=cbbd27594afd089daa160d3a16dd515a"
SRCREV = "7f1c0f6f8122d7978c9de29d9d468f7fac9ba62c"
SRC_URI = "git://github.com/google/googletest.git;protocol=git"

S = "${WORKDIR}/git"

#SRC_URI[md5sum] = "bf75fe658e90245c8f9c5fea4242788b"
#SRC_URI[sha256sum] = "6c42d52c22ac081ee30a7f92ba1f21c6126e6f3d2afc0665b935dfb51fce474c"
#SRC_URI += "file://asdlkfjsdlfj.patch"
#DEPENDS = ""

EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS=ON"

PR = "r2"

inherit pkgconfig cmake

do_install() {
    #GTEST

    # include
    install -d ${D}${includedir}/gtest
    cp -R ${WORKDIR}/git/googletest/include/gtest/* ${D}${includedir}/gtest
    install -d ${D}${includedir}/gmock
    cp -R ${WORKDIR}/git/googlemock/include/gmock/* ${D}${includedir}/gmock

    # lib
    install -d ${D}${libdir}
    cp -R ${WORKDIR}/build/lib/libg*.so* ${D}${libdir}
    chrpath -d ${D}${libdir}/libg*.so*
}

FILES_${PN} += "    \
    ${includedir}/gtest \
    ${includedir}/gmock \
    ${libdir}/libgtest.so \
    ${libdir}/libgtest_main.so \
    ${libdir}/libgmock.so \
    ${libdir}/libgmock_main.so \
"
FILES_${PN}-dev = " \
"
FILES_${PN}-staticdev = " \
"
PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale"
INSANE_SKIP_${PN} = " dev-deps"

#Dependencies
DEPENDS_${PN} = " libnl libnl-genl "
RDEPENDS_${PN} = " libnl python "
