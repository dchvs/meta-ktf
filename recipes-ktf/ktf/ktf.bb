DESCRIPTION = "Kernel Test Framework"
HOMEPAGE = "https://github.com/dchvs/ktf.git"
LICENSE = "GPLv2"

PR = "r1"
DEPENDS += " libnl gtest"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRCREV = "ca58342ebed698f246ede0b7022f240f9457b202"
SRC_URI = "git://github.com/dchvs/ktf.git;branch=lethani;protocol=git"

S = "${WORKDIR}/git"

# Makefile parameters
export KERNEL_SRC="${STAGING_KERNEL_BUILDDIR}"
export ARCH="${ARCH}"
export CROSS_COMPILE="${CROSS_COMPILE}"


inherit pkgconfig cmake

do_install() {
    ##KTF

    # include
    install -d ${D}${includedir}/ktf
    cp -R ${WORKDIR}/git/kernel/*.h ${D}${includedir}/ktf

    #ktf.ko
#    install -d ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers/
#    install -m 0755 ${S}/kernel/ktf.ko ${D}/lib/modules/4.19.30-yocto-standard/kernel/drivers/

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
"
#    /lib/modules/4.19.30-yocto-standard/kernel/drivers/ktf.ko
FILES_${PN}-dev = " "
FILES_${PN}-staticdev = " "

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-staticdev ${PN}-dev ${PN}-locale "

INSANE_SKIP_${PN} = "dev-deps"

#Dependencies
DEPENDS_${PN} = " libnl libnl-genl libktf libgmock libgtest libnl-3 libnl-genl-3"
RDEPENDS_${PN} = " python libnl libnl-genl gtest "
