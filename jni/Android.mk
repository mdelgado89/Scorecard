LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := scorecard
LOCAL_SRC_FILES := CStats.cpp
LOCAL_LDLIBS    := -llog

include $(BUILD_SHARED_LIBRARY)