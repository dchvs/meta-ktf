DESCRIPTION = "Kernel Test Framework"
HOMEPAGE = "https://github.com/oracle/ktf"
LICENSE = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=fed54355545ffd980b814dab4a3b312c"

BRANCH ?= "v5.15/standard/base"
SRCREV = "3b9a3cb84ead3598289e39938110f4aa738b6ab2"

SRC_URI = "git://git@github.com/dchvs/ktf.git;protocol=https;branch=${BRANCH};"

S = "${WORKDIR}/git"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

python () {
    if d.getVar('PV').strip() != '.'.join(d.getVar('KERNEL_VERSION').split('.')[:2]).strip():
        raise bb.parse.SkipPackage("Skipping recipe; incompatible Kernel version")
}

# Makefile parameters
KERNEL_SRC = "${STAGING_KERNEL_DIR}/"

inherit pkgconfig module-base kernel-module-split cmake

EXTRA_OECMAKE:append = " -DCMAKE_SYSTEM_VERSION=${KERNEL_VERSION} -DKERNEL_SRC=${KERNEL_SRC} -DARCH=${ARCH}"

do_install:append () {
    # User Space installs
    install -d ${D}${includedir}/ktf
    install -m 0644 ${S}/lib/*.h ${D}${includedir}/ktf

    # Kernel Space installs
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/ktf/
    install -m 0644 ${B}/kernel/ktf.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/ktf/

    install -d ${D}/usr/src/${PN}/include/
    install -m 0644 ${S}/kernel/*.h ${D}/usr/src/${PN}/include
}

FILES:${PN} = " \
    ${libdir}/libktf.so \
    ${bindir}/ktfrun \
"

FILES:${PN}-kernel = " \
    /lib/modules/${KERNEL_VERSION}/kernel/drivers/ktf/ktf.ko \
"

FILES:${PN}-dev = " \
    ${includedir}/ktf/*.h \
"

FILES:${PN}-kernel-dev = " \
    /usr/src/${PN}/include/*.h \
"

PACKAGES:append = " \
    ${PN}-kernel \
    ${PN}-kernel-dev \
"

DEPENDS = " \
    virtual/kernel \
    kernel-devsrc \
    bison-native \
    elfutils-native \
    bc-native \
    libnl \
    gtest \
"

RDEPENDS:${PN} = " \
    libnl \
    gtest \
    ${PN}-kernel\
"
