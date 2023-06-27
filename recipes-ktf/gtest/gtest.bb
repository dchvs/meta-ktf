DESCRIPTION = "Google C++ Testing Framework"
HOMEPAGE = "https://github.com/google/googletest"
LICENSE = "BSD-3-Clause"

BB_STRICT_CHECKSUM = "0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

BRANCH = "main"
SRCREV = "7f1c0f6f8122d7978c9de29d9d468f7fac9ba62c"
SRC_URI = "git://github.com/google/googletest.git;protocol=https;branch=${BRANCH};"

S = "${WORKDIR}/git"

EXTRA_OECMAKE:append = " -DBUILD_SHARED_LIBS=ON -DGTEST_HAS_PTHREAD=0"

inherit pkgconfig cmake

FILES:${PN}:append = "    \
    ${includedir}/gtest \
    ${includedir}/gmock \
    ${libdir}/libgtest.so \
    ${libdir}/libgtest_main.so \
    ${libdir}/libgmock.so \
    ${libdir}/libgmock_main.so \
    /usr/lib/cmake \
    /usr/lib/pkgconfig \
    /usr/lib/cmake/GTest \
"

FILES:${PN}-dev = ""
FILES:${PN}-staticdev = ""

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale"
INSANE_SKIP:${PN} = " dev-deps"

# Dependencies
DEPENDS = "libnl"
RDEPENDS:${PN} = "python3  libnl"
