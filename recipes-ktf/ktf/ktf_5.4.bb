DESCRIPTION = "Kernel Test Framework"
HOMEPAGE = "https://github.com/oracle/ktf"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

BRANCH ?= "v5.4/standard/base"
SRCREV = "7a2f77b9a34bbf9c0b0af5975d77d3f7b65e754d"

SRC_URI = "git://git@github.com/dchvs/ktf.git;protocol=https;branch=${BRANCH};"

S = "${WORKDIR}/git"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

# Makefile parameters
KERNEL_SRC = "${STAGING_KERNEL_DIR}/"

inherit pkgconfig module-base kernel-module-split cmake

EXTRA_OECMAKE += " -DCMAKE_SYSTEM_VERSION=${KERNEL_VERSION} -DKERNEL_SRC=${KERNEL_SRC} -DARCH=${ARCH}"

do_install_append () {
    # User Space installs
    install -d ${D}${includedir}/ktf
    install -m 0644 ${S}/lib/*.h ${D}${includedir}/ktf

    # Kernel Space installs
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/ktf/
    install -m 0644 ${B}/kernel/ktf.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/ktf/

    install -d ${D}/usr/src/${PN}/include/
    install -m 0644 ${S}/kernel/*.h ${D}/usr/src/${PN}/include
}

FILES_${PN} = " \
    ${libdir}/libktf.so \
    ${bindir}/ktfrun \
"

FILES_${PN}-kernel = " \
    /lib/modules/${KERNEL_VERSION}/kernel/drivers/ktf/ktf.ko \
"

FILES_${PN}-dev = " \
    ${includedir}/ktf/*.h \
"

FILES_${PN}-kernel-dev = " \
    /usr/src/${PN}/include/*.h \
"

PACKAGES += " ${PN}-kernel ${PN}-kernel-dev"

#Dependencies
DEPENDS = " virtual/kernel kernel-devsrc bison-native elfutils-native bc-native libnl gtest"
RDEPENDS_${PN} = " python3 libnl gtest ${PN}-kernel"
