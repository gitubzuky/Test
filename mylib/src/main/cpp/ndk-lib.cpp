#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_lib_mylib_util_JniUtil_stringFromJNI1(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "from C++ in NDK：";
    return env->NewStringUTF(hello.c_str());
}