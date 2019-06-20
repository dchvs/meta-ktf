DESCRIPTION = "Kernel Test Framework"
HOMEPAGE = "https://github.com/dchvs/ktfx.git"
LICENSE = "GPLv2"

PR = "r1"
DEPENDS += " libnl gtest"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRCREV = "0f14f0ff11f83b3668ac733f44c73d1fd26b12e3"
SRC_URI = "git://github.com/dchvs/ktfx.git;protocol=git"

S = "${WORKDIR}/git"


# Makefile parameters 
export KERNEL_SRC="${STAGING_KERNEL_BUILDDIR}"
export ARCH="arm"
export CROSS_COMPILE="arm-poky-linux-gnueabi-"


inherit pkgconfig cmake

do_install() {
    ##KTF

    # include
    install -d ${D}${includedir}/ktf
    cp -R ${WORKDIR}/git/kernel/*.h ${D}${includedir}/ktf

    #ktf.ko
    install -d ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers/
    install -m 0755 ${S}/kernel/ktf.ko ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers/

    #lib/libktf.so
    install -d ${D}${libdir}
    cp -R ${WORKDIR}/build/shared/libk*.so* ${D}${libdir}

    # bin/ktfrun
    install -d ${D}${bindir}
    cp -R ${WORKDIR}/build/user/ktfrun ${D}${bindir}
    chrpath -d ${D}${bindir}/ktfrun

}

FILES_${PN} = " \
    ${includedir}/ktf \
    ${libdir}/libktf.so \
    ${bindir}/ktfrun \
    /lib/modules/4.19.30-yocto-standard/kernel/drivers/ktf.ko \
"
FILES_${PN}-dev = " \
"
FILES_${PN}-staticdev = " \
"

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale "

INSANE_SKIP_${PN} = "dev-deps"

#Dependencies
DEPENDS_${PN} = " libnl libnl-genl libktf libgmock libgtest libnl-3 libnl-genl-3"
RDEPENDS_${PN} = " python libnl libnl-genl gtest "
