DESCRIPTION = "The Management Complex (MC) is a key component of DPAA"
SECTION = "mc-utils"
LICENSE = "BSD"

LIC_FILES_CHKSUM = "file://LICENSE;md5=386a6287daa6504b7e7e5014ddfb3987 \
"

DEPENDS += "dtc-native"

inherit deploy

SRC_URI = "git://github.com/nxp-qoriq/mc-utils;nobranch=1"
SRCREV = "eeb8972a3d4137c87a54c2795452b17f254a68c7"

S = "${WORKDIR}/git"

MC_CFG ?= ""
MC_CFG_ls1088a = "ls1088a"
MC_CFG_ls2088a = "ls2088a"
MC_CFG_lx2160a = "lx2160a"

do_install () {
	oe_runmake -C config 

	install -d ${D}/boot/mc-utils
	cp -r ${S}/config/${MC_CFG}/RDB/*.dtb ${D}/boot/mc-utils
        if [ -d ${S}/config/${MC_CFG}/RDB/custom/ ]; then
            install -d ${D}/boot/mc-utils/custom
            cp -r ${S}/config/${MC_CFG}/RDB/custom/*.dtb ${D}/boot/mc-utils/custom
        fi
}

do_deploy () {
	install -d ${DEPLOYDIR}/mc-utils
	cp -r ${S}/config/${MC_CFG}/RDB/*.dtb ${DEPLOYDIR}/mc-utils
        if [ -d ${S}/config/${MC_CFG}/RDB/custom/ ]; then
            install -d ${DEPLOYDIR}/mc-utils/custom
            cp -r ${S}/config/${MC_CFG}/RDB/custom/*.dtb ${DEPLOYDIR}/mc-utils/custom
        fi
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
