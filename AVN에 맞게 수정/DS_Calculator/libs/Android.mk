LOCAL_PATH:=$(call my-dir)



include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES :=  \
	MPAndroidChart-v3.0.2:MPAndroidChart-v3.0.2.jar
include $(BUILD_MULTI_PREBUILT)

