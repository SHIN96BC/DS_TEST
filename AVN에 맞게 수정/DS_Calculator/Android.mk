LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res
	
LOCAL_STATIC_ANDROID_LIBRARIES += \
    androidx.appcompat_appcompat \
    androidx-constraintlayout_constraintlayout \
    androidx.recyclerview_recyclerview \
    androidx.annotation_annotation \
	SettingsLib \


LOCAL_USE_AAPT2 := true
LOCAL_PACKAGE_NAME := DS_Calculator
LOCAL_PRIVATE_PLATFORM_APIS := true
LOCAL_CERTIFICATE := platform
LOCAL_JAVA_LIBRARIES := android-support-v4 framework2
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_STATIC_JAVA_LIBRARIES := MPAndroidChart-v3.0.2
#LOCAL_PROGUARD_FLAG_FILES := proguard.flags

include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := \
		androidx_core_jar:libs/android-core-classes.jar \
		glidelib:libs/glide-3.7.0.jar \
		picassolib:libs/picasso-2.5.2.jar \
		frameWork4:libs/frameWork4-classes.jar \