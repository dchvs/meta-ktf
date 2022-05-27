DESCRIPTION = "Kernel Test Framework"
HOMEPAGE = "https://github.com/oracle/ktf"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

BRANCH ?= "lethani"

SRCREV = "60b60de3854d72cab1951007e9d01ba32ff2a70a"
SRC_URI = "git://git@github.com/dchvs1/ktf.git;protocol=ssh;branch=${BRANCH} "

S = "${WORKDIR}/git"


# Makefile parameters
export KERNEL_SRC = "${STAGING_KERNEL_BUILDDIR}"

# CMakeLists parameters
EXTRA_OECMAKE += "-DCMAKE_SYSTEM_VERSION=${KERNEL_VERSION}"


inherit pkgconfig module-base kernel-module-split cmake


FILES_${PN} = " \
    ${includedir}/ktf \
    ${libdir}/libktf.so \
    ${bindir}/ktfrun \
    /lib/modules/${KERNEL_VERSION}/kernel/drivers/ktf.ko \
"

FILES_${PN}-dev = " "
FILES_${PN}-staticdev = " "

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale "

INSANE_SKIP_${PN} = "dev-deps"

#Dependencies
DEPENDS += " libnl gtest"
RDEPENDS_${PN} = " python3 libnl gtest "
